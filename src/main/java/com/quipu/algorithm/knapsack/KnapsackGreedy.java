package com.quipu.algorithm.knapsack;

import java.util.TreeSet;

public class KnapsackGreedy implements KnapsackAlgorithm {

    @Override public float calculate(float knapsackCapacity, float[] weights, float[] values) {
        TreeSet<Pair> pairs = new TreeSet<>();
        for (int i = 0; i < weights.length; i++) {
            pairs.add(new Pair(weights[i], values[i]));
        }
        double remainingCapacity = knapsackCapacity;
        int collectedValue = 0;
        for (Pair pair : pairs) {
            if (pair.weight <= remainingCapacity) {
                remainingCapacity -= pair.weight;
                collectedValue += pair.value;
            }
        }
        return collectedValue;
    }

    private class Pair implements Comparable<Pair> {

        private float weight;
        private float value;

        Pair(float weight, float value) {
            this.weight = weight;
            this.value = value;
        }

        float averageValue() {
            return  value / weight;
        }

        @Override public int compareTo(Pair o) {
            return Double.compare(o.averageValue(), this.averageValue());
        }
    }

}
