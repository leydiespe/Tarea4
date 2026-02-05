package controler;

import model.*;

/**
 *
 * @author leydi
 */
public class BusquedaMatriz {
    private Matriz datos;
    private OrdenarMatriz ordenar;

    public BusquedaMatriz(Matriz datos) {
        this.datos = datos;
        this.ordenar = new OrdenarMatriz(datos);

        // ordenar una sola vez
        ordenar.mergeSort();
    }
    //SECUENCIAL 
    //RECORRE TODO LOS ELEMENTOS UNO POR UNO POR LO CUAL NO REQUIERE DATOS 
    //ORDENADOS, LO MALO ES QUE NO ES EFICIENTE PARA GRNADES VOLUMENES DE DATOS
    public boolean [] buscarSecuencial(Busqueda numero) {

        int x = numero.getX();
        int negX = numero.getNegativo();

        boolean encontradoX = false;
        boolean encontradoNegX = false;

        int[][] matriz = datos.getMatriz();

        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                if (matriz[i][j] == x) {
                    encontradoX = true;
                }
                if (matriz[i][j] == negX) {
                    encontradoNegX = true;
                }
            }
        }

        return new boolean[]{encontradoX, encontradoNegX};
    }
    
    //BINARIA
    //REQUIERE QUE LA MATRIZ ESTE PREVIAMENTE ORDENADA Y CONVERIDA EN UN
    //ARREGLO UNIDIMENCIONAL ES MUY EFICIENTE CON UNA COMLEJIDAD O(log n)
    //LO MALO ES QUE SIEMPRE TIENE QUE ESTAR ORDENADA LA MATRIZ 
    public boolean[] buscarBinaria(Busqueda numero) {

        int x = numero.getX();
        int negX = numero.getNegativo();

        int[] arr = ordenar.getArreglo();

        return new boolean[]{
            binaria(arr, x),
            binaria(arr, negX)
        };
    }

    private boolean binaria(int[] arr, int valor) {

        int inicio = 0;
        int fin = arr.length - 1;

        while (inicio <= fin) {
            int medio = (inicio + fin) / 2;

            if (arr[medio] == valor) return true;

            if (arr[medio] < valor)
                inicio = medio + 1;
            else
                fin = medio - 1;
        }

        return false;
    }

    // INTERPOLACION
    // ES IMILAR A LA BINARIA PERO ESTIMA LA POSICION DEL VALOR, ES MAS EFICIENTE
    // CUANTO LOS DATOS ESTAN UNIFORMM¿ENTE DISTRIBUIDOS 
    public boolean[] buscarInterpolacion(Busqueda numero) {

        int x = numero.getX();
        int negX = numero.getNegativo();

        int[] arr = ordenar.getArreglo();

        return new boolean[]{
            interpolacion(arr, x),
            interpolacion(arr, negX)
        };
    }

    private boolean interpolacion(int[] arr, int valor) {

        int inicio = 0;
        int fin = arr.length - 1;

        while (inicio <= fin &&
                valor >= arr[inicio] &&
                valor <= arr[fin]) {

            if (inicio == fin)
                return arr[inicio] == valor;

            int pos = inicio + ((valor - arr[inicio]) * (fin - inicio))
                    / (arr[fin] - arr[inicio]);

            if (arr[pos] == valor) return true;

            if (arr[pos] < valor)
                inicio = pos + 1;
            else
                fin = pos - 1;
        }

        return false;
    }
}
