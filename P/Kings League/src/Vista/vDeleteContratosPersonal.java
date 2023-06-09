package Vista;

import Controlador.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Generar la clase vDeleteContratosPersonal.
 * Esta clase tiene el contenido y los métodos necesarios para ejecutar la ventana destinada a eliminar los contratos de los miembros del personal.
 */
public class vDeleteContratosPersonal {
    private JPanel pDegradado;
    private JPanel pDatos;
    private JPanel pIniciarSesion;
    private JLabel jlNombre;
    private JPanel pFooter;
    private JPanel pBotones;
    private JButton bAceptar;
    private JButton bCancelar;
    private JPanel pPrincipal;
    private JComboBox cbID;

    public vDeleteContratosPersonal() {
        // Poner fondo degradado
        pPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g;

                Color colorInicio = new Color(239, 122, 14);
                Color colorFin = new Color(253, 214, 44);

                GradientPaint gradient = new GradientPaint(
                        0, 0, colorInicio,
                        0, getHeight(), colorFin);

                g2d.setPaint(gradient);

                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        pPrincipal.add(pDegradado, BorderLayout.CENTER);

        generarCombo();
        bCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.PrincipalAdmin();
            }
        });
        bAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean delete;
                    delete=Main.deleteContratosPersonales(cbID.getSelectedItem().toString());
                    if (delete){
                        JOptionPane.showMessageDialog(null, "Contrato eliminado");
                        cbID.removeAllItems();
                        generarCombo();
                    }else JOptionPane.showMessageDialog(null, "Problemas eliminando");
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("vDeleteContratosPersonal");
        frame.setContentPane(new vDeleteContratosPersonal().pPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    public void generarCombo(){
        ArrayList<String> id= Main.getIDContratosPersonales();
        for (int x=0; x<id.size();x++){
            cbID.addItem(id.get(x));
        }
    }

    public JPanel getpPrincipal() {
        return pPrincipal;
    }
}
