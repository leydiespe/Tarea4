package controler;

import model.Matriz;

/**
 *
 * @author leydi
 */
public class OrdenarMatriz {

    private int[] arreglo;

    public OrdenarMatriz(Matriz datos) {
        arreglo = convertirMatriz(datos.getMatriz());
    }

    private int[] convertirMatriz(int[][] matriz) {
        int[] arr = new int[1000000];
        int k = 0;

        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                arr[k++] = matriz[i][j];
            }
        }
        return arr;
    }

    public int[] getArreglo() {
        return arreglo;
    }

    // BUBBLE SORT
    //Conplejidad O(n^2)
    public void bubbleSort() {
        int n = arreglo.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arreglo[j] > arreglo[j + 1]) {
                    int temp = arreglo[j];
                    arreglo[j] = arreglo[j + 1];
                    arreglo[j + 1] = temp;
                }
            }
        }
    }

    // INSERTION SORT
    //complejidad O(n^2)
    public void insertionSort() {
        for (int i = 1; i < arreglo.length; i++) {
            int key = arreglo[i];
            int j = i - 1;

            while (j >= 0 && arreglo[j] > key) {
                arreglo[j + 1] = arreglo[j];
                j--;
            }
            arreglo[j + 1] = key;
        }
    }

    // SHELL SORT
    //complejidad O(n log n)
    public void shellSort() {
        int n = arreglo.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = arreglo[i];
                int j;
                for (j = i; j >= gap && arreglo[j - gap] > temp; j -= gap) {
                    arreglo[j] = arreglo[j - gap];
                }
                arreglo[j] = temp;
            }
        }
    }

    // MERGE SORT
    //cmplejidad O(n log n)
    public void mergeSort() {
        mergeSortRec(0, arreglo.length - 1);
    }

    private void mergeSortRec(int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSortRec(left, mid);
            mergeSortRec(mid + 1, right);
            merge(left, mid, right);
        }
    }

    private void merge(int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;

        while (i <= mid && j <= right) {
            if (arreglo[i] <= arreglo[j]) {
                temp[k++] = arreglo[i++];
            } else {
                temp[k++] = arreglo[j++];
            }
        }

        while (i <= mid) temp[k++] = arreglo[i++];
        while (j <= right) temp[k++] = arreglo[j++];

        System.arraycopy(temp, 0, arreglo, left, temp.length);
    }

    // COUNTING SORT 
    // complejidad O(n + k)
    public void countingSort() {
        int max = 1000;
        int min = -1000;
        int range = max - min + 1;

        int[] count = new int[range];
        int[] output = new int[arreglo.length];

        for (int num : arreglo) {
            count[num - min]++;
        }

        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        for (int i = arreglo.length - 1; i >= 0; i--) {
            output[count[arreglo[i] - min] - 1] = arreglo[i];
            count[arreglo[i] - min]--;
        }

        arreglo = output;
    }

    // RADIX SORT 
    //complejidad O(d·n)
    public void radixSort() {
        int offset = 1000;
        for (int i = 0; i < arreglo.length; i++) {
            arreglo[i] += offset;
        }

        int max = arreglo[0];
        for (int num : arreglo) {
            if (num > max) max = num;
        }

        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingRadix(exp);
        }

        for (int i = 0; i < arreglo.length; i++) {
            arreglo[i] -= offset;
        }
    }

    private void countingRadix(int exp) {
        int[] output = new int[arreglo.length];
        int[] count = new int[10];

        for (int num : arreglo) {
            count[(num / exp) % 10]++;
        }

        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        for (int i = arreglo.length - 1; i >= 0; i--) {
            output[count[(arreglo[i] / exp) % 10] - 1] = arreglo[i];
            count[(arreglo[i] / exp) % 10]--;
        }

        arreglo = output;
    }
}
