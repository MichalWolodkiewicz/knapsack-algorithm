package com.quipu.algorithm.knapsack;

public class KnapsackDynamic implements KnapsackAlgorithm {
    @Override

    public float calculate(float capacity, float[] weights, float[] values) {
        int i;
        int w;
        float K[][] = new float[weights.length + 1][(int) capacity + 1];

        // Build table K[][] in bottom up manner
        for (i = 0; i <= weights.length; i++) {
            for (w = 0; w <= capacity; w++) {
                if (i == 0 || w == 0)
                    K[i][w] = 0;
                else if (weights[i - 1] <= w)
                    K[i][w] = Math.max(values[i - 1] + K[i - 1][w - (int) weights[i - 1]], K[i - 1][w]);
                else
                    K[i][w] = K[i - 1][w];
            }
        }

        return K[values.length][(int) capacity];
    }
}
