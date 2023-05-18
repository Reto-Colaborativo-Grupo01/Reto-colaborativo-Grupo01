package Vista;

import Controlador.Main;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class vInsertarPersonal {
    private JPanel pPrincipal;
    private JPanel pDegradado;
    private JPanel pDatos;
    private JPanel pIniciarSesion;
    private JLabel jlNombre;
    private JLabel jlTelefono;
    private JTextField tfNombre;
    private JLabel JLThegrefg;
    private JLabel JLIbai;
    private JPanel pHeader;
    private JLabel fLogoKingsLeague;
    private JLabel jlApellidos;
    private JLabel jlDni;
    private JLabel jlOficio;
    private JLabel jlImagen;
    private JTextField tfApellidos;
    private JFormattedTextField ftfDni;
    private JFormattedTextField ftfTelefono;
    private JComboBox cbOficio;
    private JTextField tfImagen;
    private JButton bCrear;
    private JButton bAtras;
    private JPanel pBotones;
    private ImageIcon LogoKingsLeague;
    private static boolean correcto;


    public vInsertarPersonal() throws MalformedURLException {

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


        // Poner la imagen del logo oficial de la Kings League
        LogoKingsLeague = new ImageIcon(new URL("https://seeklogo.com/images/K/kings-league-logo-CEDD6AED72-seeklogo.com.png"));
        Image LogoNuevo = LogoKingsLeague.getImage().getScaledInstance(300, 122, Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(LogoNuevo);
        fLogoKingsLeague.setIcon(newIcon);


        bCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                validarNombre(tfNombre.getText());
                validarApellido(tfApellidos.getText());
                validarImagen(tfImagen.getText());
                // TODO: si valida insertar personal

                if (correcto){
                    tfNombre.setText("");
                    tfApellidos.setText("");
                    ftfDni.setText("");
                    ftfTelefono.setText("");
                    cbOficio.setSelectedIndex(1);
                    tfImagen.setText("");

                }
            }
        });
        bAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.PrincipalAdmin();
            }
        });

    }

    private void createUIComponents() throws Exception {
        try {
            ftfDni = new JFormattedTextField(new MaskFormatter("########U"));
            ftfTelefono = new JFormattedTextField((new MaskFormatter("#########")));
        }
        catch (ParseException e){
            throw new Exception("Algún campo no cumple con el formato establecido.");
        }
    }
    public static boolean validarNombre(String nombre){
        Matcher encaja;
        Pattern pat = Pattern.compile("^[A-Z][a-z]+( [A-Z][a-z]+)*$");
        encaja = pat.matcher(nombre);
        if (!encaja.matches()){
            JOptionPane.showMessageDialog(null,"El nombre debe empezar por una mayúscula y seguir con minúsculas.");
            correcto=false;
        }
        else
            correcto=true;
        return correcto;
    }
    public static boolean validarApellido(String apellidos){
        Matcher encaja;
        Pattern pat = Pattern.compile("^[A-Z][a-z]+( [A-Z][a-z]+)*$");
        encaja = pat.matcher(apellidos);
        if (!encaja.matches()){
            JOptionPane.showMessageDialog(null,"Los apellidos deben empezar por una mayúscula y seguir con minúsculas.");
            correcto=false;
        }
        else
            correcto=true;
        return correcto;
    }
    public static boolean validarImagen(String imagen){

        Matcher encaja;
        // TODO: poner patrón correcto
        Pattern pat = Pattern.compile("^$");
        encaja = pat.matcher(imagen);
        if (!encaja.matches()){
            JOptionPane.showMessageDialog(null,"La imagen debe empezar por 'https' y terminar con '.png'.");
            correcto=false;
        }
        else
            correcto=true;
        return correcto;
    }


    public JPanel getpPrincipal() {
        return pPrincipal;
    }

    public static void main(String[] args) throws MalformedURLException {
        JFrame frame = new JFrame("vInsertarPersonal");
        frame.setContentPane(new vInsertarPersonal().pPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
    }

}
