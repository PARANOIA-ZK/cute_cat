package com.paranoia.jdk8.functioninterface;

import java.text.DecimalFormat;
import java.util.function.Function;

/**
 * @author PARANOIA_ZK
 * @date 2019/11/9 16:06
 */
public class InterfaceDemo2 {
    public static void main(String[] args) {
        MoneyPrint moneyPrint = new MoneyPrint(1234566789);

        Function<Integer, String> integerStringFunction = integer -> new DecimalFormat("#,###").format(integer);
        moneyPrint.printMoneyCount(integerStringFunction.andThen(s -> "人民币" + s + " 万"));
    }
}


class MoneyPrint {
    int count;

    public MoneyPrint(int count) {
        this.count = count;
    }

    public void printMoneyCount(Function<Integer, String> function) {
        System.out.println("我的存款：" + function.apply(count));
    }
}
