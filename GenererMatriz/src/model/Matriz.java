/*
     * Codigo que hace busqueda binaria,secuencia e interpolar
     * Ordena  la matriz utilizando los método
     * bubble sort, insertion sort y mege sort, shell, counting sort y radix sort.
 */
package model;

import java.util.Random;

/**
 *
 * @author leydi
 */
public class Matriz {

    private int[][] matriz;
    

    public Matriz() {
        matriz = new int[1000][1000];
        generarMatriz();
    }

    private void generarMatriz() {
        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                matriz[i][j] = r.nextInt(2001) - 1000;
            }
        }
    }
    
    public int[][] getMatriz() {
        return matriz;
    }

}
