package com.neko233.toolchain.commons.core.base;

import java.util.List;

public class BitStatePrinter {

    private static void bitOutput(Integer nPowers) {
        bitOutput(ListUtils233.of(nPowers));
    }

    public static void bitOutput(List<Integer> nPowers) {
        System.out.println("-------------- bit output -------------------");
        for (int n = 0; n < nPowers.size(); n++) {
            String format = String.format("%8s", Integer.toBinaryString(nPowers.get(n)));
            String binaryString = format.replace(' ', '0');
            String nPower = String.format("%3s", nPowers.get(n));
            System.out.println("n" + n + ":" + nPower + "=" + binaryString);
        }
        System.out.println("-------------- /bit output -------------------");
    }

    public static void printBitState(int number) {
        int i = Integer.bitCount(number);
        System.out.println(i);
    }
}
