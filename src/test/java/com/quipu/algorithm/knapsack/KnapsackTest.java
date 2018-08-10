package com.quipu.algorithm.knapsack;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

public class KnapsackTest {

    private List<KnapsackTestCase> testCases;
    private KnapsackAlgorithm knapsackAlgorithm = new KnapsackDynamic();

    @Before public void initTest() throws IOException {
        testCases = new ArrayList<>(readTestCases());
    }

    private Collection<KnapsackTestCase> readTestCases() throws IOException {
        Collection<KnapsackTestCase> testCases = new ArrayList<>();
        File inputs = new File(getClass().getClassLoader().getResource("test_sets/input").getFile());
        Assert.assertTrue(inputs.exists());
        for (File file : inputs.listFiles()) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file.getAbsolutePath()));
            String line;
            List<Float> weights = new ArrayList<>();
            List<Float> values = new ArrayList<>();
            String[] firstLine = bufferedReader.readLine().split(" ");
            float capacity = Float.valueOf(firstLine[1]);
            while ((line = bufferedReader.readLine()) != null) {
                String[] splitedLine = line.split(" ");
                if (splitedLine.length == 2) {
                    weights.add(Float.valueOf(splitedLine[1]));
                    values.add(Float.valueOf(splitedLine[0]));
                }
            }
            String optiumumFile = getClass().getClassLoader().getResource("test_sets/optimum/" + file.getName()).getFile();
            float optimalValue = Float.valueOf(new BufferedReader(new FileReader(optiumumFile)).readLine());
            KnapsackTestCase knapsackTestCase = new KnapsackTestCase(toArray(weights), toArray(values), optimalValue, capacity, file.getName());
            testCases.add(knapsackTestCase);
        }
        return testCases;
    }

    @Test public void test() {
        boolean failure = false;
        for (KnapsackTestCase tc : testCases) {
            double result = knapsackAlgorithm.calculate(tc.capacity, tc.weights, tc.values);
            boolean areEqualDouble = areEqualDouble(tc.optimalValue, result, 4);
            if (!areEqualDouble) {
                System.err.println(String.format("Test %s failure expected[%f] to be equal to[%f] ", tc.testName, tc.optimalValue, result));
                failure = true;
            } else {
                System.out.println(String.format("Test %s passed.", tc.testName));
            }
        }
        if (failure) {
            Assert.fail();
        }
    }

    private class KnapsackTestCase {

        KnapsackTestCase(float[] weights, float[] values, float optimalValue, float capacity, String testName) {
            this.weights = weights;
            this.values = values;
            this.optimalValue = optimalValue;
            this.capacity = capacity;
            this.testName = testName;
        }

        private float[] weights;
        private float[] values;
        private float optimalValue;
        private float capacity;
        private String testName;
    }

    static boolean areEqualDouble(double a, double b, int precision) {
        return Math.abs(a - b) <= Math.pow(10, -precision);
    }

    private float[] toArray(List<Float> collection) {
        float[] result = new float[collection.size()];
        for (int i = 0; i < collection.size(); i++) {
            result[i] = collection.get(i);
        }
        return result;
    }
}