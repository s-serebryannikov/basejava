package com.urise.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MainHW12 {

    public static void main(String[] args) {
        int[] arrayNumber = {1, 2, 7, 3, 3, 5, 3, 3, 4, 3, 9};
        System.out.println(minValue(arrayNumber));

        List<Integer> list = new ArrayList<>();
        Collections.addAll(list, 1, 5, 6, 11, 3, 15, 7, 8);
        System.out.println(oddOrEven(list));

    }

    private static int minValue(int[] arrayNumber1) {
        return Arrays.stream(arrayNumber1).distinct().sorted().reduce(0, (summ, i) -> summ = 10 * summ + i);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        return integers
                .stream()
                .filter(integers.stream().mapToInt(Integer::intValue)
                        .sum() % 2 != 0 ? n -> n % 2 == 0 : n -> n % 2 != 0)
                .collect(Collectors.toList());
    }

}
