package complejidad;

/*Se desea desarrollar un sistema que permita validar el número de cédula ecuatoriana ingresado por un usuario.
El sistema debe comparar dos soluciones algorítmicas para resolver el mismo problema:

Una solución genérica

Una solución optimizada
*/
import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

public class vistaDatos extends JFrame {

    private JTextField txtNombre, txtCedula;
    private JButton btnValidar;
    private DefaultListModel<String> modeloLista;
    private JList<String> listaUsuarios;

    // HASHSET PARA VALIDACIÓN OPTIMIZADA
    private HashSet<String> cedulasRegistradas = new HashSet<>();

    public vistaDatos() {
        setTitle("Validación de Cédula");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Nombre:"));
        txtNombre = new JTextField(20);
        add(txtNombre);

        add(new JLabel("Cédula:"));
        txtCedula = new JTextField(20);
        add(txtCedula);

        btnValidar = new JButton("Validar");
        add(btnValidar);

        modeloLista = new DefaultListModel<>();
        listaUsuarios = new JList<>(modeloLista);
        add(new JScrollPane(listaUsuarios));

        btnValidar.addActionListener(e -> validarUsuario());
    }

    private void validarUsuario() {
        String nombre = txtNombre.getText();
        String cedula = txtCedula.getText();

        if (nombre.isEmpty() || cedula.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese nombre y cédula");
            return;
        }

        long inicio1 = System.nanoTime();
        boolean generico = validarCedulaGenerica(cedula);
        long fin1 = System.nanoTime();

        long inicio2 = System.nanoTime();
        boolean optimizado = validarCedulaHashSet(cedula);
        long fin2 = System.nanoTime();

        if (generico && optimizado) {
            modeloLista.addElement(nombre + " - " + cedula);
            JOptionPane.showMessageDialog(this,
                    "Registro correcto\nTiempo genérico: " + (fin1 - inicio1) + " ns\nTiempo optimizado: " + (fin2 - inicio2) + " ns");
        } else {
            JOptionPane.showMessageDialog(this, "Cédula inválida o repetida");
        }
    }

    // VALIDACIÓN GENÉRICA
    private boolean validarCedulaGenerica(String cedula) {
        if (cedula.length() != 10) return false;

        int suma = 0;
        for (int i = 0; i < 9; i++) {
            int dig = cedula.charAt(i) - '0';
            if (i % 2 == 0) {
                dig *= 2;
                if (dig > 9) dig -= 9;
            }
            suma += dig;
        }

        int verificador = (10 - (suma % 10)) % 10;
        return verificador == (cedula.charAt(9) - '0');
    }

    // VALIDACIÓN OPTIMIZADA CON HASHSET
    private boolean validarCedulaHashSet(String cedula) {

        if (!validarCedulaGenerica(cedula)) {
            return false;
        }

        // HashSet detecta duplicados en O(1)
        if (!cedulasRegistradas.add(cedula)) {
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new vistaDatos().setVisible(true));
    }
}
