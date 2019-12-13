package com.paranoia.sourcecode.java.util.concurrent;

import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import com.paranoia.sourcecode.java.lang.Thread;

/**
 * 该类提供线程本地变量。
 * 这些变量与普通的对应变量的不同之处在于，每个访问一个变量的线程(通过它的{@code get}或{@code set}方法)都有自己独立初始化的变量副本。
 * {@code ThreadLocal}实例通常是希望将状态与线程(例如，用户ID或事务ID)关联的类中的私有静态字段。
 * <p>
 * 例如，下面的类为每个线程生成唯一的本地标识符。
 * 线程的id在第一次调用{@code ThreadId.get()} 时被分配，并且在随后的调用中保持不变。
 * * public class ThreadId {
 * *     // Atomic integer containing the next thread ID to be assigned
 * *     private static final AtomicInteger nextId = new AtomicInteger(0);
 * *
 * *     // Thread local variable containing each thread's ID
 * *     private static final ThreadLocal<Integer> threadId = new ThreadLocal<Integer>() {
 * *             @Override
 * *             protected Integer initialValue() {
 * *                 return nextId.getAndIncrement();
 * *         }
 * *     };
 * *
 * *     // Returns the current thread's unique ID, assigning it if necessary
 * *     public static int get() {
 * *         return threadId.get();
 * *     }
 * <p>
 * <p>
 * 只要线程是活动的，且{@code ThreadLocal}实例是可访问的，每个线程都持有对其线程本地变量副本的隐式引用;
 * 在线程消失后，它的所有线程本地实例副本都要进行垃圾收集(除非存在对这些副本的其他引用)。
 *
 * @author Josh Bloch and Doug Lea
 * @since 1.2
 */
public class ThreadLocal<T> {
    /**
     * threadlocal依赖于附加到每个线程(线程)上的每个线程的线性探测哈希映射。threadlocal和inheritablethreadlocal)。
     * ThreadLocal对象充当键，通过threadLocalHashCode进行搜索。
     * 这是一个自定义哈希代码(仅在threadlocalmap中有用)，它消除了常见情况下的冲突，
     * 即连续构造的threadlocal由相同的线程使用，而在不太常见的情况下保持良好的行为。
     */
    private final int threadLocalHashCode = nextHashCode();

    /**
     * 下一个要给出的哈希码。自动更新。从0开始。
     */
    private static AtomicInteger nextHashCode = new AtomicInteger();

    /**
     * 连续生成的哈希码之间的差异——将隐式顺序线程本地id转换为接近最优扩散的乘法哈希值，用于大小为2的幂的表。
     * The difference between successively generated hash codes - turns implicit sequential thread-local IDs into near-optimally spread multiplicative hash values for power-of-two-sized tables.
     */
    private static final int HASH_INCREMENT = 0x61c88647;

    /**
     * Returns the next hash code.
     */
    private static int nextHashCode() {
        return nextHashCode.getAndAdd(HASH_INCREMENT);
    }

    /**
     * 返回当前线程的初始值。
     * 这个方法将在线程第一次使用{@link #get}方法访问变量时被调用，除非线程之前调用了{@link #set}方法，在这种情况下不会为线程调用{@code initialValue}方法。
     * 通常，每个线程最多调用该方法一次，但是在随后调用{@link #remove}和{@link #get}时，可能会再次调用该方法。
     * <p>
     * 这个实现只返回{@code null};
     * 如果程序员希望线程局部变量的初始值不是{@code null}，则必须子类化{@code ThreadLocal}，并重写此方法。
     * 通常，将使用匿名内部类。 比如说 SuppliedThreadLocal
     *
     * @return the initial value for this thread-local
     */
    protected T initialValue() {
        return null;
    }

    /**
     * 创建线程本地变量。变量的初始值是通过调用{@code Supplier}上的{@code get}方法确定的。
     *
     * @param <S>      the type of the thread local's value
     * @param supplier 用于确定初始值的供应商
     * @return 一个新的线程局部变量
     * @throws NullPointerException if the specified supplier is null
     * @since 1.8
     */
    public static <S> ThreadLocal<S> withInitial(Supplier<? extends S> supplier) {
        return new SuppliedThreadLocal<>(supplier);
    }

    /**
     * Creates a thread local variable.
     *
     * @see #withInitial(java.util.function.Supplier)
     */
    public ThreadLocal() {
    }

    /**
     * 返回此线程本地变量的当前线程副本中的值。
     * 如果变量没有当前线程的值，则首先将其初始化为调用{@link #initialValue}方法返回的值。
     *
     * @return the current thread's value of this thread-local
     */
    public T get() {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null) {
            ThreadLocalMap.Entry e = map.getEntry(this);
            if (e != null) {
                @SuppressWarnings("unchecked")
                T result = (T) e.value;
                return result;
            }
        }
        //map为null也就是Thread中的threadLocals属性没有初始化
        return setInitialValue();
    }

    /**
     * 如果用户覆盖了set()方法，则使用set()方法的变体来创建initialValue
     *
     * @return the initial value
     */
    private T setInitialValue() {
        //注意initialValue这个方法，默认返回的是null。
        T value = initialValue();
        //这里的逻辑其实就是和set方法中的一致了，注意这里的校验逻辑不是没有必要的
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null)
            //如果重复调用get()，其实这里最少会执行N-1次。
            map.set(this, value);
        else
            //createMap()方法只有两处调用，一个get  一个set
            //在get中被调用的情况是 ThreadLocal对象创建之后没有调用set()直接调用get()
            createMap(t, value);
        //如果子类没有自己实现initialValue(),那么这里返回的其实就是null
        return value;
    }

    /**
     * 将当前线程的线程本地变量副本设置为指定的值。
     * 大多数子类将不需要重写这个方法，只依赖于{@link #initialValue}方法来设置线程局部变量的值。
     *
     * @param value the value to be stored in the current thread's copy of
     *              this thread-local.
     */
    public void set(T value) {
        //获取当前线程
        Thread t = Thread.currentThread();
        //获取当前线程中的threadLocals属性的对象 -> t.threadLocals
        ThreadLocalMap map = getMap(t);
        if (map != null)
            //如果不为空，说明这个属性初始化过，直接赋值
            map.set(this, value);
        else
            //note :  Thread中ThreadLocal属性的初始化是这个这个方法被被第一次调用的时候进行的
            //t.threadLocals = new ThreadLocalMap(this, firstValue);
            createMap(t, value);
    }

    /**
     * 移除此线程本地变量的当前线程值。
     * 如果这个线程本地变量remove()后重新调用get()方法，那么它的值将通过调用它的initialValue()方法重新初始化，
     * --除非它的值是当前线程在此调用get()之前调用了set()。
     * 这可能导致在当前线程中多次调用{@code initialValue}方法。
     *
     * @since 1.5  这个方法是1.5新增的。但是需要注意的是线程结束之后，这个线程的局部变量也就是 Thread.threadLocals等都会
     * 自动被垃圾回收，所以显式的调用此方法并不是必须的，只是这么做会加快内存回收的速度。
     */
    public void remove() {
        ThreadLocalMap m = getMap(Thread.currentThread());
        if (m != null)
            m.remove(this);
    }

    /**
     * 获取与ThreadLocal关联的映射。InheritableThreadLocal中覆盖了这个方法。
     *
     * @param t the current thread
     * @return the map
     */
    ThreadLocalMap getMap(Thread t) {
        return t.threadLocals;
    }

    /**
     * Create the map associated with a ThreadLocal. Overridden in
     * InheritableThreadLocal.
     *
     * @param t          the current thread
     * @param firstValue value for the initial entry of the map
     */
    void createMap(Thread t, T firstValue) {
        //这里初始化Thread的threadLocal属性的值
        t.threadLocals = new ThreadLocalMap(this, firstValue);
    }

    /**
     * Factory method to create map of inherited thread locals.
     * Designed to be called only from Thread constructor.
     *
     * @param parentMap the map associated with parent thread
     * @return a map containing the parent's inheritable bindings
     */
    static ThreadLocalMap createInheritedMap(ThreadLocalMap parentMap) {
        return new ThreadLocalMap(parentMap);
    }

    /**
     * 方法childValue显然是在子类InheritableThreadLocal中定义的，但是这里是在内部定义的，目的是提供createInheritedMap工厂方法，
     * --而不需要在InheritableThreadLocal中子类化map类。
     * 这种技术比在方法中嵌入instanceof测试更好。
     */
    T childValue(T parentValue) {
        throw new UnsupportedOperationException();
    }

    /**
     * ThreadLocal的一个扩展，它从指定的{@code Supplier}获取初始值。
     */
    static final class SuppliedThreadLocal<T> extends ThreadLocal<T> {

        private final Supplier<? extends T> supplier;

        SuppliedThreadLocal(Supplier<? extends T> supplier) {
            this.supplier = Objects.requireNonNull(supplier);
        }

        /**
         * 注意这里实现了ThreadLocal的protected方法 initialValue
         *
         * @return
         */
        @Override
        protected T initialValue() {
            return supplier.get();
        }
    }

    /**
     * ThreadLocalMap是一个定制的散列映射，只适合维护线程本地值。
     * 在ThreadLocal类之外不导出任何操作。
     * The class is package private to allow declaration of fields in class Thread.
     * 类是包私有的，允许在类线程中声明字段。-> Thread.java 中含有 ThreadLocalMap属性
     * 为了帮助处理非常大且长期存在的使用，哈希表条目使用WeakReferences作为键。
     * 但是，由于不使用引用队列，所以只有当表开始耗尽空间时，才保证删除陈旧的条目。
     */
    static class ThreadLocalMap {

        /**
         * The entries in this hash map extend WeakReference
         * 这个散列映射中的条目扩展了WeakReference，使用它的ref字段作为键(它始终是一个ThreadLocal对象)。
         * 注意，null键(即entry.get() == null)表示不再引用该键，因此可以从表中删除该条目。
         * 在下面的代码中，这些条目被称为“陈旧条目”。
         */
        static class Entry extends WeakReference<ThreadLocal<?>> {
            /**
             * 和ThreadLocal关联的值
             */
            Object value;

            Entry(ThreadLocal<?> k, Object v) {
                super(k);
                value = v;
            }
        }

        /**
         * The initial capacity -- MUST be a power of two.
         */
        private static final int INITIAL_CAPACITY = 16;

        /**
         * The table, resized as necessary.
         * table.length MUST always be a power of two.
         */
        private Entry[] table;

        /**
         * The number of entries in the table.
         */
        private int size = 0;

        /**
         * The next size value at which to resize.
         */
        private int threshold; // Default to 0

        /**
         * Set the resize threshold to maintain at worst a 2/3 load factor.
         */
        private void setThreshold(int len) {
            threshold = len * 2 / 3;
        }

        /**
         * Increment i modulo len.
         */
        private static int nextIndex(int i, int len) {
            return ((i + 1 < len) ? i + 1 : 0);
        }

        /**
         * Decrement i modulo len.
         */
        private static int prevIndex(int i, int len) {
            return ((i - 1 >= 0) ? i - 1 : len - 1);
        }

        /**
         * Construct a new map initially containing (firstKey, firstValue).
         * ThreadLocalMaps are constructed lazily, so we only create
         * one when we have at least one entry to put in it.
         */
        ThreadLocalMap(ThreadLocal<?> firstKey, Object firstValue) {
            table = new Entry[INITIAL_CAPACITY];
            int i = firstKey.threadLocalHashCode & (INITIAL_CAPACITY - 1);
            table[i] = new Entry(firstKey, firstValue);
            size = 1;
            setThreshold(INITIAL_CAPACITY);
        }

        /**
         * 构造一个新的映射，包括来自给定父映射的所有可继承threadlocal。仅由{@linkplain #createInheritedMap}调用。
         *
         * @param parentMap the map associated with parent thread.
         */
        private ThreadLocalMap(ThreadLocalMap parentMap) {
            Entry[] parentTable = parentMap.table;
            int len = parentTable.length;
            setThreshold(len);
            table = new Entry[len];

            for (int j = 0; j < len; j++) {
                Entry e = parentTable[j];
                if (e != null) {
                    @SuppressWarnings("unchecked")
                    ThreadLocal<Object> key = (ThreadLocal<Object>) e.get();
                    if (key != null) {
                        Object value = key.childValue(e.value);
                        Entry c = new Entry(key, value);
                        int h = key.threadLocalHashCode & (len - 1);
                        while (table[h] != null)
                            h = nextIndex(h, len);
                        table[h] = c;
                        size++;
                    }
                }
            }
        }

        /**
         * Get the entry associated with key.  This method
         * itself handles only the fast path: a direct hit of existing
         * key. It otherwise relays to getEntryAfterMiss.  This is
         * designed to maximize performance for direct hits, in part
         * by making this method readily inlinable.
         *
         * @param key the thread local object
         * @return the entry associated with key, or null if no such
         */
        private Entry getEntry(ThreadLocal<?> key) {
            int i = key.threadLocalHashCode & (table.length - 1);
            Entry e = table[i];
            if (e != null && e.get() == key)
                return e;
            else
                return getEntryAfterMiss(key, i, e);
        }

        /**
         * Version of getEntry method for use when key is not found in
         * its direct hash slot.
         *
         * @param key the thread local object
         * @param i   the table index for key's hash code
         * @param e   the entry at table[i]
         * @return the entry associated with key, or null if no such
         */
        private Entry getEntryAfterMiss(ThreadLocal<?> key, int i, Entry e) {
            Entry[] tab = table;
            int len = tab.length;

            while (e != null) {
                ThreadLocal<?> k = e.get();
                if (k == key)
                    return e;
                if (k == null)
                    expungeStaleEntry(i);
                else
                    i = nextIndex(i, len);
                e = tab[i];
            }
            return null;
        }

        /**
         * Set the value associated with key.
         *
         * @param key   the thread local object
         * @param value the value to be set
         */
        private void set(ThreadLocal<?> key, Object value) {

            // We don't use a fast path as with get() because it is at
            // least as common to use set() to create new entries as
            // it is to replace existing ones, in which case, a fast
            // path would fail more often than not.

            Entry[] tab = table;
            int len = tab.length;
            int i = key.threadLocalHashCode & (len - 1);

            for (Entry e = tab[i];
                 e != null;
                 e = tab[i = nextIndex(i, len)]) {
                ThreadLocal<?> k = e.get();

                if (k == key) {
                    e.value = value;
                    return;
                }

                if (k == null) {
                    replaceStaleEntry(key, value, i);
                    return;
                }
            }

            tab[i] = new Entry(key, value);
            int sz = ++size;
            if (!cleanSomeSlots(i, sz) && sz >= threshold)
                rehash();
        }

        /**
         * Remove the entry for key.
         */
        private void remove(ThreadLocal<?> key) {
            Entry[] tab = table;
            int len = tab.length;
            int i = key.threadLocalHashCode & (len - 1);
            for (Entry e = tab[i];
                 e != null;
                 e = tab[i = nextIndex(i, len)]) {
                if (e.get() == key) {
                    e.clear();
                    expungeStaleEntry(i);
                    return;
                }
            }
        }

        /**
         * Replace a stale entry encountered during a set operation
         * with an entry for the specified key.  The value passed in
         * the value parameter is stored in the entry, whether or not
         * an entry already exists for the specified key.
         * <p>
         * As a side effect, this method expunges all stale entries in the
         * "run" containing the stale entry.  (A run is a sequence of entries
         * between two null slots.)
         *
         * @param key       the key
         * @param value     the value to be associated with key
         * @param staleSlot index of the first stale entry encountered while
         *                  searching for key.
         */
        private void replaceStaleEntry(ThreadLocal<?> key, Object value,
                                       int staleSlot) {
            Entry[] tab = table;
            int len = tab.length;
            Entry e;

            // Back up to check for prior stale entry in current run.
            // We clean out whole runs at a time to avoid continual
            // incremental rehashing due to garbage collector freeing
            // up refs in bunches (i.e., whenever the collector runs).
            int slotToExpunge = staleSlot;
            for (int i = prevIndex(staleSlot, len);
                 (e = tab[i]) != null;
                 i = prevIndex(i, len))
                if (e.get() == null)
                    slotToExpunge = i;

            // Find either the key or trailing null slot of run, whichever
            // occurs first
            for (int i = nextIndex(staleSlot, len);
                 (e = tab[i]) != null;
                 i = nextIndex(i, len)) {
                ThreadLocal<?> k = e.get();

                // If we find key, then we need to swap it
                // with the stale entry to maintain hash table order.
                // The newly stale slot, or any other stale slot
                // encountered above it, can then be sent to expungeStaleEntry
                // to remove or rehash all of the other entries in run.
                if (k == key) {
                    e.value = value;

                    tab[i] = tab[staleSlot];
                    tab[staleSlot] = e;

                    // Start expunge at preceding stale entry if it exists
                    if (slotToExpunge == staleSlot)
                        slotToExpunge = i;
                    cleanSomeSlots(expungeStaleEntry(slotToExpunge), len);
                    return;
                }

                // If we didn't find stale entry on backward scan, the
                // first stale entry seen while scanning for key is the
                // first still present in the run.
                if (k == null && slotToExpunge == staleSlot)
                    slotToExpunge = i;
            }

            // If key not found, put new entry in stale slot
            tab[staleSlot].value = null;
            tab[staleSlot] = new Entry(key, value);

            // If there are any other stale entries in run, expunge them
            if (slotToExpunge != staleSlot)
                cleanSomeSlots(expungeStaleEntry(slotToExpunge), len);
        }

        /**
         * Expunge a stale entry by rehashing any possibly colliding entries
         * lying between staleSlot and the next null slot.  This also expunges
         * any other stale entries encountered before the trailing null.  See
         * Knuth, Section 6.4
         *
         * @param staleSlot index of slot known to have null key
         * @return the index of the next null slot after staleSlot
         * (all between staleSlot and this slot will have been checked
         * for expunging).
         */
        private int expungeStaleEntry(int staleSlot) {
            Entry[] tab = table;
            int len = tab.length;

            // expunge entry at staleSlot
            tab[staleSlot].value = null;
            tab[staleSlot] = null;
            size--;

            // Rehash until we encounter null
            Entry e;
            int i;
            for (i = nextIndex(staleSlot, len);
                 (e = tab[i]) != null;
                 i = nextIndex(i, len)) {
                ThreadLocal<?> k = e.get();
                if (k == null) {
                    e.value = null;
                    tab[i] = null;
                    size--;
                } else {
                    int h = k.threadLocalHashCode & (len - 1);
                    if (h != i) {
                        tab[i] = null;

                        // Unlike Knuth 6.4 Algorithm R, we must scan until
                        // null because multiple entries could have been stale.
                        while (tab[h] != null)
                            h = nextIndex(h, len);
                        tab[h] = e;
                    }
                }
            }
            return i;
        }

        /**
         * Heuristically scan some cells looking for stale entries.
         * This is invoked when either a new element is added, or
         * another stale one has been expunged. It performs a
         * logarithmic number of scans, as a balance between no
         * scanning (fast but retains garbage) and a number of scans
         * proportional to number of elements, that would find all
         * garbage but would cause some insertions to take O(n) time.
         *
         * @param i a position known NOT to hold a stale entry. The
         *          scan starts at the element after i.
         * @param n scan control: {@code log2(n)} cells are scanned,
         *          unless a stale entry is found, in which case
         *          {@code log2(table.length)-1} additional cells are scanned.
         *          When called from insertions, this parameter is the number
         *          of elements, but when from replaceStaleEntry, it is the
         *          table length. (Note: all this could be changed to be either
         *          more or less aggressive by weighting n instead of just
         *          using straight log n. But this version is simple, fast, and
         *          seems to work well.)
         * @return true if any stale entries have been removed.
         */
        private boolean cleanSomeSlots(int i, int n) {
            boolean removed = false;
            Entry[] tab = table;
            int len = tab.length;
            do {
                i = nextIndex(i, len);
                Entry e = tab[i];
                if (e != null && e.get() == null) {
                    n = len;
                    removed = true;
                    i = expungeStaleEntry(i);
                }
            } while ((n >>>= 1) != 0);
            return removed;
        }

        /**
         * Re-pack and/or re-size the table. First scan the entire
         * table removing stale entries. If this doesn't sufficiently
         * shrink the size of the table, double the table size.
         */
        private void rehash() {
            expungeStaleEntries();

            // Use lower threshold for doubling to avoid hysteresis
            if (size >= threshold - threshold / 4)
                resize();
        }

        /**
         * Double the capacity of the table.
         */
        private void resize() {
            Entry[] oldTab = table;
            int oldLen = oldTab.length;
            int newLen = oldLen * 2;
            Entry[] newTab = new Entry[newLen];
            int count = 0;

            for (int j = 0; j < oldLen; ++j) {
                Entry e = oldTab[j];
                if (e != null) {
                    ThreadLocal<?> k = e.get();
                    if (k == null) {
                        e.value = null; // Help the GC
                    } else {
                        int h = k.threadLocalHashCode & (newLen - 1);
                        while (newTab[h] != null)
                            h = nextIndex(h, newLen);
                        newTab[h] = e;
                        count++;
                    }
                }
            }

            setThreshold(newLen);
            size = count;
            table = newTab;
        }

        /**
         * Expunge all stale entries in the table.
         */
        private void expungeStaleEntries() {
            Entry[] tab = table;
            int len = tab.length;
            for (int j = 0; j < len; j++) {
                Entry e = tab[j];
                if (e != null && e.get() == null)
                    expungeStaleEntry(j);
            }
        }
    }
}
