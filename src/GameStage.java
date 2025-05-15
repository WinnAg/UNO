import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class GameStage extends JFrame {
    private AgregarJugadores aggJugadores = new AgregarJugadores();
    ArrayList<String> temp = new ArrayList<>();
    String[] pids;
    Juego juego;
    ArrayList<JButton> cardButtons = new ArrayList<>();
    ArrayList<String> cardIds;
    //PopUp window;

    private JButton jButton1, jButton2, jButton3, jButton4, jButton5,
            jButton6, jButton7, jButton8, jButton9, jButton10,
            jButton11, jButton12, jButton13, jButton14, jButton15,
            drawCardButton, topCardButton;

    private JLabel pidNameLabel;

    public GameStage() {}

    public GameStage(ArrayList<String> playerIds) {
        this.temp = playerIds;
        this.pids = temp.toArray(new String[0]);
        this.juego = new Juego(pids);
        initComponents();
        populateArrayList();
        juego.start(juego);
        setPidName();
        topCardButton.setIcon(new ImageIcon("C:/Users/Rodas/OneDrive/Desktop/sprites/small/" + juego.getTopCardImage() + ".png"));
        setButtonIcons();
    }

    private void initComponents() {
        jButton1 = new JButton(); jButton2 = new JButton(); jButton3 = new JButton();
        jButton4 = new JButton(); jButton5 = new JButton(); jButton6 = new JButton();
        jButton7 = new JButton(); jButton8 = new JButton(); jButton9 = new JButton();
        jButton10 = new JButton(); jButton11 = new JButton(); jButton12 = new JButton();
        jButton13 = new JButton(); jButton14 = new JButton(); jButton15 = new JButton();
        drawCardButton = new JButton("Robar carta");
        topCardButton = new JButton();
        pidNameLabel = new JLabel();

        JButton[] buttons = { jButton1, jButton2, jButton3, jButton4, jButton5, jButton6,
                jButton7, jButton8, jButton9, jButton10, jButton11, jButton12,
                jButton13, jButton14, jButton15 };

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setBounds(20 + i * 50, 400, 48, 70);
            add(buttons[i]);
            final int idx = i;
            buttons[i].addActionListener(e -> cardButtonActionPerformed(idx));
        }

        drawCardButton.setBounds(600, 300, 120, 40);
        drawCardButton.addActionListener(this::drawCardButtonActionPerformed);
        add(drawCardButton);

        topCardButton.setBounds(300, 100, 100, 140);
        add(topCardButton);

        pidNameLabel.setBounds(50, 20, 300, 30);
        pidNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(pidNameLabel);

        setLayout(null);
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void setButtonIcons() {
        String listString = juego.getPlayerHand(juego.getCurrentPlayer()).stream().map(Object::toString).collect(Collectors.joining("."));
        String[] cardNames = listString.split("\\.");
        cardIds = new ArrayList<>(Arrays.asList(cardNames));
        for (int i = 0; i < cardIds.size(); i++) {
            cardButtons.get(i).setIcon(new ImageIcon("C:/Users/Rodas/OneDrive/Desktop/sprites/small/" + cardIds.get(i) + ".png"));
        }
        for (int i = cardIds.size(); i < cardButtons.size(); i++) {
            cardButtons.get(i).setIcon(null);
        }
    }

    public void populateArrayList() {
        cardButtons.addAll(Arrays.asList(
                jButton1, jButton2, jButton3, jButton4, jButton5,
                jButton6, jButton7, jButton8, jButton9, jButton10,
                jButton11, jButton12, jButton13, jButton14, jButton15
        ));
    }

    public void setPidName() {
        String currentPlayer = juego.getCurrentPlayer();
        pidNameLabel.setText(currentPlayer + "'s Cards");
    }

    public void setPidName(String currentPlayer) {
        pidNameLabel.setText(currentPlayer + "'s Cards");
    }

    private void drawCardButtonActionPerformed(java.awt.event.ActionEvent evt) {
        JLabel message = new JLabel(juego.getCurrentPlayer() + " drew a card! ");
        message.setFont(new Font("Arial", Font.BOLD, 48));
        JOptionPane.showMessageDialog(null, message);
        try {
            juego.submitDraw(juego.getCurrentPlayer());
        } catch (Juego.InvalidPlayerTurnException ex) {
            Logger.getLogger(GameStage.class.getName()).log(Level.SEVERE, null, ex);
        }
        setPidName(juego.getCurrentPlayer());
        setButtonIcons();
    }

    private void cardButtonActionPerformed(int index) {
        if (index < cardIds.size() && cardIds.get(index) != null) {
            String cardId = cardIds.get(index);
            //window = new PopUp(cardId, juego, index, cardButtons, this, topCardButton);
            //window.setBounds(750, 40, 700, 800);
           // window.setVisible(true);
            //window.setResizable(false);
            //window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
    }
}
