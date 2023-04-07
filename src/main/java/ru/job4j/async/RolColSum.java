package ru.job4j.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int n = 0; n < sums.length; n++) {
            sums[n] = new Sums();
        }
        int[] rowSums = new int[matrix.length];
        int[] colSums = new int[matrix.length];
        for (int i = 0; i < matrix.length; i ++) {
            for(int j = 0; j < matrix[i].length; j++) {
                rowSums[i] += matrix[i][j];
                colSums[j] += matrix[i][j];
            }
        }
        for (int k = 0; k < sums.length; k++) {
            sums[k].setColSum(colSums[k]);
            sums[k].setRowSum(rowSums[k]);
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
            Sums sums = new Sums();
            int rowSum = 0;
            int colSum = 0;
            for (int i = 0; i < data.length; i++) {
                rowSum += data[rowColNumber][i];
                colSum += data[i][rowColNumber];
            }
            sums.setColSum(colSum);
            sums.setRowSum(rowSum);
            return sums;
        });
    }


    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

    }

}