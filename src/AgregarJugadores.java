import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

public class AgregarJugadores extends JFrame {
    private List<String> nombres = new ArrayList<>();

    public AgregarJugadores() {
        setTitle("Agregar Jugadores");
        setSize(400, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setContentPane(new PanelConBordeYFondo());
    }

    class PanelConBordeYFondo extends JPanel {
        private Image imagen;
        private JButton listoBtn; // Botón declarado como atributo

        public PanelConBordeYFondo() {
            imagen = new ImageIcon("C:/Users/Admin/Desktop/M/UNOMain/Imagenes/Reverse Logo.jpeg").getImage();
            setLayout(null);

            TituloConContorno titulo = new TituloConContorno();
            titulo.setBounds(0, 100, 400, 130);
            add(titulo);

            JComponent usernameLabel = new TextoConContorno("Username", new Font("Arial", Font.BOLD, 20));
            usernameLabel.setBounds(50, 260, 300, 30);
            add(usernameLabel);

            JTextField usernameField = new JTextField();
            usernameField.setBounds(50, 300, 300, 30);
            usernameField.setOpaque(false);
            usernameField.setForeground(Color.BLACK);
            usernameField.setFont(new Font("Arial", Font.PLAIN, 18));
            usernameField.setBorder(null);
            add(usernameField);

            JComponent underline = new JComponent() {
                Color currentColor = Color.BLACK;

                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setColor(currentColor);
                    g2.setStroke(new BasicStroke(2));
                    g2.drawLine(0, 0, getWidth(), 0);
                }

                public void setLineColor(Color color) {
                    currentColor = color;
                    repaint();
                }
            };
            underline.setBounds(50, 330, 300, 2);
            add(underline);

            JButton guardarBtn = new JButton("Guardar");
            guardarBtn.setFont(new Font("Arial", Font.BOLD, 18));
            guardarBtn.setBackground(new Color(74, 128, 0));
            guardarBtn.setForeground(Color.WHITE);
            guardarBtn.setBounds(50, 380, 140, 40);
            guardarBtn.setFocusPainted(false);

            guardarBtn.addActionListener(e -> {
                String nombre = usernameField.getText().trim();
                if (!nombre.isEmpty()) {
                    if (nombres.size() < 4) {
                        nombres.add(nombre);
                        usernameField.setText("");
                        underline.repaint();
                        checkListoBtnVisibility(); // Mostrar u ocultar el botón según la cantidad
                    } else {
                        JLabel message = new JLabel("¡Solo puede haber entre 2 y 4 jugadores!");
                        message.setFont(new Font("Arial", Font.BOLD, 16));
                        JOptionPane.showMessageDialog(null, message, "Límite alcanzado", JOptionPane.WARNING_MESSAGE);
                        usernameField.setText("");
                        checkListoBtnVisibility(); // Mostrar u ocultar el botón según la cantidad
                    }
                }
            });
            add(guardarBtn);

            listoBtn = new JButton("Listo");
            listoBtn.setFont(new Font("Arial", Font.BOLD, 18));
            listoBtn.setBackground(new Color(204, 0, 37));
            listoBtn.setForeground(Color.WHITE);
            listoBtn.setBounds(210, 380, 140, 40);
            listoBtn.setFocusPainted(false);
            listoBtn.setVisible(false); // Oculto inicialmente

            listoBtn.addActionListener(e -> {
                if (!nombres.isEmpty()) {
                    SwingUtilities.getWindowAncestor(this).dispose();
                    new GameStage().setVisible(true);
                }
            });
            add(listoBtn);

            usernameField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                public void insertUpdate(javax.swing.event.DocumentEvent e) {
                    checkField(usernameField);
                }

                public void removeUpdate(javax.swing.event.DocumentEvent e) {
                    checkField(usernameField);
                }

                public void changedUpdate(javax.swing.event.DocumentEvent e) {
                    checkField(usernameField);
                }

                public void checkField(JTextField field) {
                    String text = field.getText().trim();
                    if (text.isEmpty()) {
                        listoBtn.setVisible(false); // se oculta si el campo está vacío
                    }
                }
            });
        }

        private void checkListoBtnVisibility() {
            if (nombres.size() >= 2) {
                listoBtn.setVisible(true);
            } else {
                listoBtn.setVisible(false);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int arc = 60;
            g2.setColor(Color.BLACK);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arc, arc));

            Shape clip = new RoundRectangle2D.Double(5, 5, getWidth() - 10, getHeight() - 10, arc, arc);
            g2.setClip(clip);
            g2.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }
    }

    class TextoConContorno extends JComponent {
        private String texto;
        private Font fuente;

        public TextoConContorno(String texto, Font fuente) {
            this.texto = texto;
            this.fuente = fuente;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setFont(fuente);

            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(texto)) / 2;
            int y = (getHeight() + fm.getAscent()) / 2 - 4;

            g2.setColor(Color.BLACK);
            int thickness = 2;
            for (int dx = -thickness; dx <= thickness; dx++) {
                for (int dy = -thickness; dy <= thickness; dy++) {
                    if (dx != 0 || dy != 0) {
                        g2.drawString(texto, x + dx, y + dy);
                    }
                }
            }

            g2.setColor(Color.WHITE);
            g2.drawString(texto, x, y);
            g2.dispose();
        }
    }

    class TituloConContorno extends JComponent {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Font font = new Font("Arial", Font.BOLD, 40);
            g2.setFont(font);

            String[] lineas = {"Agregar", "Jugadores"};

            int y = 40;
            for (String texto : lineas) {
                drawTextWithOutline(g2, texto, getWidth() / 2, y);
                y += 50;
            }

            g2.dispose();
        }

        private void drawTextWithOutline(Graphics2D g2, String text, int centerX, int y) {
            FontMetrics fm = g2.getFontMetrics();
            int x = centerX - fm.stringWidth(text) / 2;

            g2.setColor(Color.BLACK);
            int thickness = 3;
            for (int dx = -thickness; dx <= thickness; dx++) {
                for (int dy = -thickness; dy <= thickness; dy++) {
                    if (dx != 0 || dy != 0) {
                        g2.drawString(text, x + dx, y + dy);
                    }
                }
            }

            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AgregarJugadores().setVisible(true));
    }
}