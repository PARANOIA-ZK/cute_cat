/*
 * Copyright (c) 2000, 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package com.paranoia.sourcecode.collection;

/**
 * List实现所使用的标记接口，用来表明实现了这些接口的list支持快速（通常是常数时间）随机访问。
 * 这个接口的主要目的是允许一般的算法更改它们的行为，以便在随机或者顺序存取列表时能提供更好的性能
 * <p>
 * 操作随机访问列表（如ArrayList）的最佳算法在应用于顺序存取列表时，有可能产生二次项行为。
 * 泛型算法列表鼓励在将某个算法应用于顺序存取列表可能导致差的性能之前，先检查给定的列表是
 * 否是这个接口的一个实例，并在需要时去改变这些算法的行为以保证性能。
 * <p>
 * 随机访问和顺序存取之间的界限通常是模糊的。例如，一些List实现在变得很大时会导致渐进的非
 * 线性访问时间，但实际上是常量访问时间。这样的List实现通常都应该实现该接口。一般来说，某
 * 个List实现如果（对某些典型的类的实例来说）满足下面的条件，就应该实现这个接口：循环
 * <pre>
 *     for (int i=0, n=list.size(); i < n; i++)
 *         list.get(i);
 * </pre>
 * runs faster than this loop:
 * 比下面的循环运行速度快。
 * <pre>
 *     for (Iterator i=list.iterator(); i.hasNext(); )
 *         i.next();
 * </pre>
 *
 * @since 1.4
 */
public interface RandomAccess {
}
