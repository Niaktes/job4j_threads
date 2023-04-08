package ru.job4j.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        int[] rowSums = new int[matrix.length];
        int[] colSums = new int[matrix.length];
        for (int i = 0; i < matrix.length; i ++) {
            for(int j = 0; j < matrix[i].length; j++) {
                rowSums[i] += matrix[i][j];
                colSums[j] += matrix[i][j];
            }
        }
        for (int k = 0; k < sums.length; k++) {
            sums[k] = new Sums(rowSums[k], colSums[k]);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        try {
            for (int n = 0; n < sums.length; n++) {
                sums[n] = getRowColSumsTask(matrix, n).get();
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        return sums;
    }

    private static CompletableFuture<Sums> getRowColSumsTask(int[][] data, int rowColNumber) {
        return CompletableFuture.supplyAsync(() -> {
            int rowSum = 0;
            int colSum = 0;
            for (int i = 0; i < data.length; i++) {
                rowSum += data[rowColNumber][i];
                colSum += data[i][rowColNumber];
            }
            return new Sums(rowSum, colSum);
        });
    }

}