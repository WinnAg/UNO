import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Uno extends JFrame {
    private JLabel imageLabel;

    //Título, tamaño, posición en pantalla y color de fondo
    public Uno() {
        setTitle("UNO Menu");
        setSize(390, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false); //Quita el boton de maximizar
        getContentPane().setBackground(Color.BLACK);

        //Panel del borde blanco
        RoundedPanel borderPanel = new RoundedPanel(30);
        borderPanel.setBounds(40, 50, 300, 400);
        borderPanel.setBackground(Color.WHITE);
        borderPanel.setLayout(null);
        add(borderPanel);

        //Panel del fondo negro
        RoundedPanel innerPanel = new RoundedPanel(25);
        innerPanel.setBounds(7, 7, 286, 386);
        innerPanel.setBackground(Color.BLACK);
        innerPanel.setLayout(null);
        borderPanel.add(innerPanel);

        //Espacio para la imagen
        imageLabel = new JLabel();
        imageLabel.setBounds(6, 100, 275, 175);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        innerPanel.add(imageLabel);

        //Botón de jugar
        JButton playButton = new JButton("JUGAR");
        playButton.setBounds(90, 30, 100, 40);
        playButton.setBackground(Color.RED);
        playButton.setForeground(Color.WHITE);
        playButton.setFocusPainted(false); //Este codigo le quita un borde que se crea alrededor de las
        //letras al presionar el boton
        playButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        innerPanel.add(playButton);

        //Siguiente ventana y cerrar esta
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AgregarJugadores().setVisible(true);
                dispose(); //Sirve para cerrar la ventana
            }
        });

        //Accion del cambio de color del boton con el cursos
        playButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                playButton.setForeground(Color.YELLOW);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                playButton.setForeground(Color.WHITE);
            }
        });

        //Botón de salir
        JButton exitButton = new JButton("SALIR");
        exitButton.setBounds(90, 300, 100, 40);
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false); //Este codigo le quita un borde que se crea alrededor de las
        //letras al presionar el boton
        exitButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        innerPanel.add(exitButton);

        //Accion del cambio de color del boton con el cursos
        exitButton.addMouseListener(new java.awt.event.MouseAdapter(){
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt){
                exitButton.setForeground(Color.YELLOW);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt){
                exitButton.setForeground(Color.WHITE);
            }
        });

        //Accion del boton salir
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    //Muestra la imagen en panel
    public void setImageIcon(ImageIcon icon) {
        imageLabel.setIcon(icon);
    }

    public static void main(String[] args) {
        Uno menu = new Uno();
        //Imagen
        ImageIcon originalIcon = new ImageIcon("C:/Users/Admin/Desktop/M/UNOMain/Imagenes/UNO.jpeg");
        Image scaledImage = originalIcon.getImage().getScaledInstance(270, 170, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        menu.setImageIcon(scaledIcon);

        menu.setVisible(true);
    }

}

