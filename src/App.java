
import java.util.stream.Stream;

public class App {
    public static void main(String[] args) {
        // get an array of random numbers
        int[] array = getRandNums(50000, 1, 100);

        // print header
        System.out.println("Random Number Array Raw Results:");
        System.out.printf("%-25s %-15s %-15s %-10s%n",
            "Algorithm", "Comparisons", "Movements", "Time Elapsed");
        
        // run insertion sort on a copy of random number array
        int[] array0 = array.clone();
        Metrics insertionMetricsR = InsertionSort.insertionSort(array0);
        printMetricEntry(insertionMetricsR);

        // run quick sort on a copy of random number array
        int [] array1 = array.clone();
        Metrics quickMetricsR = QuickSort.quickSort(array1, 0, array1.length - 1);
        printMetricEntry(quickMetricsR);

        // run heap sort on a copy of random number array
        int[] array2 = array.clone();
        Metrics heapMetricsR = HeapSort.heapSort(array2);
        printMetricEntry(heapMetricsR);

        // run merge sort on a copy of random number array
        int[] array3 = array.clone();
        Metrics mergeMetricsR = MergeSort.mergeSort(array3, 0, array3.length - 1);
        printMetricEntry(mergeMetricsR);

        // print header
        System.out.println();
        System.out.println("Sorted Number Array Raw Results:");
        System.out.printf("%-25s %-15s %-15s %-10s%n",
            "Algorithm", "Comparisons", "Movements", "Time Elapsed");

        // run insertion sort on sorted number array
        Metrics insertionMetricsS = InsertionSort.insertionSort(array0);
        printMetricEntry(insertionMetricsS);

        // run quick sort on sorted number array
        Metrics quickMetricsS = QuickSort.quickSort(array1, 0, array1.length - 1);
        printMetricEntry(quickMetricsS);

        // run heap sort on sorted number array
        Metrics heapMetricsS = HeapSort.heapSort(array2);
        printMetricEntry(heapMetricsS);

        // run merge sort on sorted number array
        Metrics mergeMetricsS = MergeSort.mergeSort(array3, 0, array3.length - 1);
        printMetricEntry(mergeMetricsS);

        // print header
        System.out.println();
        System.out.println("Ranked Random Number Array Results:");

        // ranked sorts on unsorted array per metric
        System.out.println("Comparisons-----------------------------------------------------------");
        Metrics[] unrankedMetricsR = {insertionMetricsR, quickMetricsR, heapMetricsR, mergeMetricsR};
        Metrics[] rankedMetricsRC = rankComparison(unrankedMetricsR);
        printMetricEntry(rankedMetricsRC);

        System.out.println("Movements-------------------------------------------------------------");
        Metrics[] rankedMetricsRM = rankMovement(unrankedMetricsR);
        printMetricEntry(rankedMetricsRM);

        System.out.println("Time Elapsed----------------------------------------------------------");
        Metrics[] rankedMetricsRT = rankTimeElapsed(unrankedMetricsR);
        printMetricEntry(rankedMetricsRT);

        // print header
        System.out.println();
        System.out.println("Ranked Sorted Number Array Results:");

        // ranked sorts on sorted array per metric
        System.out.println("Comparisons-----------------------------------------------------------");
        Metrics[] unrankedMetricsS = {insertionMetricsS, quickMetricsS, heapMetricsS, mergeMetricsS};
        Metrics[] rankedMetricsSC = rankComparison(unrankedMetricsS);
        printMetricEntry(rankedMetricsSC);

        System.out.println("Movements-------------------------------------------------------------");
        Metrics[] rankedMetricsSM = rankMovement(unrankedMetricsS);
        printMetricEntry(rankedMetricsSM);

        System.out.println("Time Elapsed----------------------------------------------------------");
        Metrics[] rankedMetricsST = rankTimeElapsed(unrankedMetricsS);
        printMetricEntry(rankedMetricsST);
    }

    // return an array of random numbers given the size and lower/upper bound specifications
    public static int[] getRandNums(int size, int lowerBound, int upperBound) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            int range = upperBound - lowerBound;
            array[i] = (int) (Math.random() * range + lowerBound);
        }

        return array;
    }

    // print array contents
    /*
    static void printArray(int[] array) {
        for (int num : array) {
            System.out.print(num + " ");
        }
    }
    */

    // print metric result row
    static void printMetricEntry(Metrics metrics) {
        System.out.printf("%-25s %-15d %-15d %-10dms%n",
            metrics.algName, metrics.comparisons, metrics.movements, metrics.timeElapsed);
    }
    
    static void printMetricEntry(Metrics[] rankedMetrics) {
        for(Metrics metrics : rankedMetrics) {
            System.out.printf("%-25s %-15d %-15d %-10dms%n",
                metrics.algName, metrics.comparisons, metrics.movements, metrics.timeElapsed);
        }
    }

    static Metrics[] rankComparison(Metrics[] unrankedMetrics) {
        return Stream.of(unrankedMetrics).sorted((metrics1, metrics2) ->
            (metrics1.comparisons >= metrics2.comparisons) ? 1 : -1).toArray(size -> new Metrics[size]);
    }

    static Metrics[] rankMovement(Metrics[] unrankedMetrics) {
        return Stream.of(unrankedMetrics).sorted((metrics1, metrics2) ->
            (metrics1.movements >= metrics2.movements) ? 1 : -1).toArray(size -> new Metrics[size]);
    }

    static Metrics[] rankTimeElapsed(Metrics[] unrankedMetrics) {
        return Stream.of(unrankedMetrics).sorted((metrics1, metrics2) ->
            (metrics1.timeElapsed >= metrics2.timeElapsed) ? 1 : -1).toArray(size -> new Metrics[size]);
    }
}

class Metrics {
    // declare metric variables
    String algName;
    int comparisons;
    int movements;
    long timeElapsed;

    // constructor for metrics
    Metrics(String algName, int comparisons, int movements, long timeElapsed) {
        // initialize metric variables
        this.algName = algName;
        this.comparisons = comparisons;
        this.movements = movements;
        this.timeElapsed = timeElapsed;
    }

    void add(Metrics other) {
        this.comparisons += other.comparisons;
        this.movements += other.movements;
        this.timeElapsed += other.timeElapsed;
    }
}

class InsertionSort {
    public static Metrics insertionSort(int[] arr) {
        // declare variables to record metrics
        int comparison = 0, movement = 0;
        long startTime = System.currentTimeMillis();

        // insertion sort from instructions with added comparison and movement recorders
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
                comparison++;                                                   // comparison made
                movement++;                                                     // movement completed
            }
            comparison++;                                                       // comparison made
            movement++;                                                         // movement completed
            arr[j + 1] = key;
        }

        long endTime = System.currentTimeMillis();

        return new Metrics("InsertionSort", comparison, movement, endTime - startTime);
    }
}

class QuickSort {
    // quick sort from instructions with added comparison and movement recorders
    public static Metrics quickSort(int[] arr, int low, int high) {
        // declare metric to record data
        Metrics metrics = new Metrics("QuickSort", 0, 0, 0);

        if (low < high) {
            int pi = partition(arr, low, high, metrics);
            metrics.add(quickSort(arr, low, pi - 1));
            metrics.add(quickSort(arr, pi + 1, high));
        }

        return metrics;
    }

    private static int partition(int[] arr, int low, int high, Metrics metrics) {
        // declare variables to record metrics
        int comparison = 0, movement = 0;
        long startTime = System.currentTimeMillis();

        int pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                movement++;                                                     // movement completed
            }
            comparison++;                                                       // comparison made
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        movement++;                                                             // movement completed

        long endTime = System.currentTimeMillis();

        // update referenced metrics with values added in partition method
        Metrics currMetrics = new Metrics("QuickSort", comparison, movement, endTime - startTime);
        metrics.add(currMetrics);

        return i + 1;
    }
}

class HeapSort {
    // heap sort from instructions with added comparison and movement recorders
    public static Metrics heapSort(int[] arr) {
        // declare metric to record data
        Metrics metrics = new Metrics("HeapSort", 0, 0, 0);

        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            metrics.add(heapify(arr, n, i));
        }
        for (int i = n - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            metrics.add(heapify(arr, i, 0));
            metrics.comparisons++;                                              // comparison made
        }

        return metrics;
    }

    private static Metrics heapify(int[] arr, int n, int i) {
        // declare variables to record metrics
        long startTime = System.currentTimeMillis();
        Metrics currMetrics = new Metrics("HeapSort", 0, 0, 0);

        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (left < n && arr[left] > arr[largest]) largest = left;
        currMetrics.comparisons++;                                              // comparison made
        if (right < n && arr[right] > arr[largest]) largest = right;
        currMetrics.comparisons++;                                              // comparison made
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            currMetrics.add(heapify(arr, n, largest));
            currMetrics.movements++;                                            // movement completed
        }

        // return metrics with values added in heapify method
        long endTime = System.currentTimeMillis();
        currMetrics.timeElapsed += (endTime - startTime);
        return currMetrics;
    }
}

class MergeSort {
    // merge sort from instructions with added comparison and movement recorders
    public static Metrics mergeSort(int[] arr, int left, int right) {
        // declare metric to record data
        Metrics metrics = new Metrics("MergeSort", 0, 0, 0);

        if (left < right) {
            int mid = (left + right) / 2;
            metrics.add(mergeSort(arr, left, mid));
            metrics.add(mergeSort(arr, mid + 1, right));
            metrics.add(merge(arr, left, mid, right));
        }

        return metrics;
    }

    private static Metrics merge(int[] arr, int left, int mid, int right) {
        // declare variables to record metrics
        long startTime = System.currentTimeMillis();
        Metrics currMetrics = new Metrics("MergeSort", 0, 0, 0);

        int n1 = mid - left + 1;
        int n2 = right - mid;
        int[] L = new int[n1];
        int[] R = new int[n2];
        for (int i = 0; i < n1; i++) L[i] = arr[left + i];
        for (int j = 0; j < n2; j++) R[j] = arr[mid + 1 + j];
        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
            currMetrics.comparisons++;                                          // comparison made
            currMetrics.movements++;                                            // movement completed
        }
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }

        // return metrics with values added in heapify method
        long endTime = System.currentTimeMillis();
        currMetrics.timeElapsed += (endTime - startTime);
        return currMetrics;
    }
}