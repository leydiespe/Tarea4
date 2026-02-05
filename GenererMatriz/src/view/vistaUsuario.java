package view;

import controler.*;
import model.*;

import javax.swing.*;
/**
 *
 * @author leydi
 */

public class vistaUsuario extends JFrame {

    private JTextField txtNumero;
    private JTextArea txtResultado;
    private JButton btnOrdenar;

    private JTable tabla;
    private JScrollPane scrollTabla;

    private Busqueda modelo;
    private BusquedaMatriz controlador;
    private Matriz matriz;
    private JComboBox<String> cmbBusqueda;
    private boolean matrizOrdenada = false;

    public vistaUsuario() {

        // ===== MVC =====
        matriz = new Matriz();
        modelo = new Busqueda();
        controlador = new BusquedaMatriz(matriz);

        // ===== JFrame =====
        setTitle("Búsqueda y Ordenamiento");
        setSize(450, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lblNumero = new JLabel("Ingrese el número x:");
        lblNumero.setBounds(30, 25, 150, 25);
        add(lblNumero);

        txtNumero = new JTextField();
        txtNumero.setBounds(170, 25, 120, 25);
        add(txtNumero);

        cmbBusqueda = new JComboBox<>(new String[]{"Buscar",
            "Secuencial",
            "Binaria",
            "Interpolación"
        });
        cmbBusqueda.setBounds(310, 25, 90, 25);
        add(cmbBusqueda);

        btnOrdenar = new JButton("Ordenar");
        btnOrdenar.setBounds(310, 60, 90, 25);
        add(btnOrdenar);

        txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtResultado);
        scroll.setBounds(30, 90, 370, 160);
        add(scroll);

        crearTabla();

        cmbBusqueda.addActionListener(e -> buscar());
        btnOrdenar.addActionListener(e -> ordenar());
    }

    private void crearTabla() {
        int[][] datosMatriz = matriz.getMatriz();

        String[] columnas = new String[20];
        for (int i = 0; i < 20; i++) {
            columnas[i] = "C" + i;
        }

        String[][] datos = new String[20][20];

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                datos[i][j] = String.valueOf(datosMatriz[i][j]);
            }
        }

        tabla = new JTable(datos, columnas);
        scrollTabla = new JScrollPane(tabla);
        scrollTabla.setBounds(30, 270, 370, 150);
        add(scrollTabla);
    }

    private void actualizarTabla() {
        int[][] datosMatriz = matriz.getMatriz();

        String[][] datos = new String[20][20];

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                datos[i][j] = String.valueOf(datosMatriz[i][j]);
            }
        }

        tabla.setModel(new javax.swing.table.DefaultTableModel(
                datos,
                new String[]{
                    "C0", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9",
                    "C10", "C11", "C12", "C13", "C14", "C15", "C16", "C17", "C18", "C19"
                }
        ));
    }

    private void buscar() {

        if (txtNumero.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Ingrese un número",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int x;

        try {
            x = Integer.parseInt(txtNumero.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Debe ingresar un número entero",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String tipo = cmbBusqueda.getSelectedItem().toString();

        if ((tipo.equals("Binaria") || tipo.equals("Interpolación")) && !matrizOrdenada) {
            JOptionPane.showMessageDialog(this,
                    "Primero debe ordenar la matriz",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        modelo.setX(x);

        SwingWorker<Void, Void> worker = new SwingWorker<>() {

            long tiempo;
            boolean[] resultado;

            @Override
            protected Void doInBackground() {

                long inicio = System.nanoTime();

                if (tipo.equals("Secuencial")) {
                    resultado = controlador.buscarSecuencial(modelo);
                } else if (tipo.equals("Binaria")) {
                    resultado = controlador.buscarBinaria(modelo);
                } else {
                    resultado = controlador.buscarInterpolacion(modelo);
                }

                long fin = System.nanoTime();
                tiempo = fin - inicio;

                return null;
            }

            @Override
            protected void done() {

                txtResultado.setText("Resultado de la búsqueda\n");
                txtResultado.append("-------------------------\n");

                txtResultado.append(resultado[0]
                        ? "Se encontró " + x + "\n"
                        : "No se encontró " + x + "\n");

                txtResultado.append(resultado[1]
                        ? "Se encontró " + (-x) + "\n"
                        : "No se encontró " + (-x) + "\n");

                txtResultado.append("\nMétodo: " + tipo);
                txtResultado.append("\nTiempo: " + tiempo + " ns");
            }
        };

        worker.execute();
    }

    private void ordenar() {

        SwingWorker<Void, Void> worker = new SwingWorker<>() {

            long tiempo;

            @Override
            protected Void doInBackground() {

                OrdenarMatriz ordenar = new OrdenarMatriz(matriz);

                long inicio = System.nanoTime();
                ordenar.mergeSort();
                long fin = System.nanoTime();

                tiempo = fin - inicio;
                matrizOrdenada = true;

                return null;
            }

            @Override
            protected void done() {
                txtResultado.setText("Matriz ordenada correctamente\n");
                txtResultado.append("Tiempo: " + tiempo + " ns");
                actualizarTabla();
            }
        };

        actualizarTabla();
        worker.execute();
    }

    public static void main(String[] args) {
        new vistaUsuario().setVisible(true);
    }
}
