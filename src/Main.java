import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static int rows;
    private static int cols;
    private static int radius;
    private static final int MAX_VALUE = 99000;
    private static final int PEAK_HEIGHT = 98480; //90 for exercise, 98480 for assignment

    public static void main(String[] args) {
        long timer = System.currentTimeMillis();


        int[][] elevation = loadFile("src/ELEVATIONS.TXT"); // TEST.TXT

        if (elevation != null) {

            int[] result1 = findLowest(elevation);
            Peak[] result2 = findLocalPeaks(elevation, radius);
            PeakDistance result3 = findClosestPeaks(result2);
            int[] result4 = mostCommonElevation(elevation);

            System.out.println("Time to execute: " + (System.currentTimeMillis() - timer) + " ms");
            
            // Print out question 1
            System.out.println("The lowest elevation is " + result1[0] + ". It occurs " + result1[1] + " times in the map");

            // Print out question 2
            System.out.println("The total number of peaks is " + result2.length);

            // Print out question 3
            if (result3 != null) {
                System.out.println("The minimum distance between two peaks is " + result3.getDistance() + " m");
                System.out.println(result3);
            } else {
                System.out.println("Error finding minimum distance between two peaks");
            }
            

            // Print out question 4
            System.out.println("The most common height in the terrain is " + 
                                result4[0] + " it occurs " + result4[1] + " times");

        }  
    }

    /**
     * Q1: Find the lowest value in the elevations and how
     *      many times that value occurs
     * @param elevation array of elevations
     * @return {lowest, count}
     */
    private static int[] findLowest(int[][] elevation) {

        int lowest = MAX_VALUE;
        int count = 0;

        for (int[] x : elevation) {
            for (int y : x) {
                if (y < lowest) {
                    lowest = y;
                    count = 1;
                } else if (y == lowest) {
                    count++;
                }
            }
        }

        return new int[] {lowest, count};
    }

    /**
     * Q2: Finds the local peaks in the map
     * @param elevation array of elevations
     * @param radius radius to check arround the peak
     * @return array of local peaks
     */
    private static Peak[] findLocalPeaks(int[][] elevation, int radius) {

        Peak[] peaks = new Peak[rows*cols];
        int peakCount = 0;

        for (int row = radius; row < rows-radius; row++) {
            for (int col = radius; col < cols-radius; col++) {
                if (elevation[row][col] >= PEAK_HEIGHT && isPeak(elevation, row, col)) {
                    peaks[peakCount] = new Peak(row, col, elevation[row][col]);
                    peakCount++;
                }
            }
        }

        return Arrays.copyOf(peaks, peakCount);
    }

    /**
     * Q3: Finds the 2 closest peaks in the peak array from Q2
     * @param peaks array of local peaks
     * @return PeakDistance of local peaks
     */
    private static PeakDistance findClosestPeaks(Peak[] peaks) {

        PeakDistance closestPeak = null;

        for (int i = 0; i < peaks.length; i++) {
            for (int j = 0; j < peaks.length; j++) {
                if (i != j && i < j) {
                    
                    double distance = Math.sqrt(
                        ((peaks[i].getX() - peaks[j].getX()) * (peaks[i].getX() - peaks[j].getX())) +
                        ((peaks[i].getY() - peaks[j].getY()) * (peaks[i].getY() - peaks[j].getY()))
                    );

                    if (closestPeak == null || distance > closestPeak.getDistance()) {
                        closestPeak = new PeakDistance(peaks[i], peaks[j], distance);
                    }
                } 
            }
        }

        return closestPeak;
    }

    /**
     * Q4: Finds the most common elevation in the elevation array
     * @param elevation array of elevations
     * @return {value, frequency}
     */
    private static int[] mostCommonElevation(int[][] elevation) {
        int[] histogram = new int[MAX_VALUE];

        for (int[] i : elevation) {
            for (int j : i) {
                histogram[j]++;
            }
        }

        int value = 0;
        int frequency = 0;

        for (int i = 0; i < histogram.length; i++) {
            if (histogram[i] > frequency) {
                value = i;
                frequency = histogram[i];
            }
        }

        return new int[] {value, frequency};
    }

    /**
     * Checks to see if the peak of (row,col) is a local peak within the radius
     * @param elevation array of elevations to check
     * @param row row of the peak to check
     * @param col col of the peak to check
     * @return true if is a local peak
     */
    private static boolean isPeak(int[][] elevation, int row, int col) {
        for (int x = row-radius; x <= row+radius; x++) {
            for (int y = col-radius; y <= col+radius; y++) {
                if (row != x && col != y && elevation[x][y] >= elevation[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Loads the array from elevations.txt 
     * @param fileName Path to elevations.txt
     * @return array of elevations
     */
    private static int[][] loadFile(final String fileName) {
        File file = new File(fileName);
        Scanner fileInput = null;
        try {
            fileInput = new Scanner(file);

            rows = fileInput.nextInt();
            cols = fileInput.nextInt();
            radius = fileInput.nextInt();

            int[][] output = new int[rows][cols];

            int currentRow = 0;
            int currentCol = 0;
            while (fileInput.hasNextInt()) {

                output[currentRow][currentCol] = fileInput.nextInt();

                if (currentCol == cols - 1) {
                    currentCol = 0;
                    currentRow++;
                } else {
                    currentCol++;
                }
            }

            fileInput.close();
            return output;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fileInput != null)
                fileInput.close();
        }
        return null;
    }
}
