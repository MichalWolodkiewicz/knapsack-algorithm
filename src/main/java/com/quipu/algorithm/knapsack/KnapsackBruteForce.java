package com.quipu.algorithm.knapsack;

import java.util.Arrays;

public class KnapsackBruteForce implements KnapsackAlgorithm {
    @Override public float calculate(float knapsackCapacity, float[] weights, float[] values) {
        int[] bits = new int[weights.length];
        float bestValue = 0;
        Arrays.fill(bits, 0);
        for (int i = 0; i < Math.pow(2, weights.length); i++) {
            int n = weights.length - 1;
            float tempWeight = 0;
            float tempValue = 0;
            while (bits[n] != 0 && n > 0) {
                bits[n] = 0;
                --n;
            }
            bits[n] = 1;
            for (int k = 0; k <= n; k++) {
                if (bits[k] == 1) {
                    tempWeight += weights[k];
                    tempValue += values[k];
                }
                if (tempValue > bestValue && tempWeight <= knapsackCapacity) {
                    bestValue = tempValue;
                }
            }
        }
        return bestValue;
    }
}
