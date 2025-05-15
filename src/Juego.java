import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Juego {
    private int currentPlayer;
    private String[] playerIds;

    private UnoDeck deck;
    private ArrayList<ArrayList<UnoCard>> playerHand;
    private ArrayList<UnoCard> stockpile;

    private UnoCard.Color validColor;
    private UnoCard.Value validValue;

    boolean gameDirection;

    public Juego(String[] pids) {
        deck = new UnoDeck();
        deck.shuffle();
        stockpile = new ArrayList<UnoCard>();

        playerIds = pids;
        currentPlayer = 0;
        gameDirection = false;

        playerHand = new ArrayList<ArrayList<UnoCard>>();

        for (int i = 0; i < pids.length; i++) {
            ArrayList<UnoCard> hand = new ArrayList<UnoCard>(Arrays.asList(deck.drawCard(7)));
            playerHand.add(hand);
        }
    }

    public void start(Juego juego) {
        UnoCard card = deck.drawCard();
        validColor = card.getColor();
        validValue = card.getValue();

        if (card.getValue() == UnoCard.Value.Wild) {
            start(juego);
            return;
        }

        if (card.getValue() == UnoCard.Value.Wild_Four || card.getValue() == UnoCard.Value.DrawTwo) {
            start(juego);
            return;
        }

        if (card.getValue() == UnoCard.Value.Skip) {
            JLabel message = new JLabel(playerIds[currentPlayer] + " was skipped!");
            message.setFont(new Font("Arial", Font.BOLD, 48));
            JOptionPane.showMessageDialog(null, message);

            if (gameDirection == false) {
                currentPlayer = (currentPlayer + 1) % playerIds.length;
            } else {
                currentPlayer = (currentPlayer - 1) % playerIds.length;
                if (currentPlayer == -1) {
                    currentPlayer = playerIds.length - 1;
                }
            }
        }

        if (card.getValue() == UnoCard.Value.Reverse) {
            JLabel message = new JLabel(playerIds[currentPlayer] + " The game direction changed!");
            message.setFont(new Font("Arial", Font.BOLD, 48));
            JOptionPane.showMessageDialog(null, message);
            gameDirection ^= true;
            currentPlayer = playerIds.length - 1;
        }

        stockpile.add(card);
    }

    public UnoCard getTopCard() {
        return new UnoCard(validColor, validValue);
    }

    public ImageIcon getTopCardImage() {
        return new ImageIcon(validColor + "-" + validValue + ".png");
    }

    public boolean isGameOver() {
        for (String player : this.playerIds) {
            if (hasEmptyHand(player)) {
                return true;
            }
        }
        return false;
    }

    public String getCurrentPlayer() {
        return this.playerIds[this.currentPlayer];
    }

    public String getPreviousPlayer(int i) {
        int index = this.currentPlayer - i;
        if (index == -1) {
            index = playerIds.length - 1;
        }
        return this.playerIds[index];
    }

    public String[] getPlayers() {
        return playerIds;
    }

    public ArrayList<UnoCard> getPlayerHand(String pid) {
        int index = Arrays.asList(playerIds).indexOf(pid);
        return playerHand.get(index);
    }

    public int getPlayerHandSize(String pid) {
        return getPlayerHand(pid).size();
    }

    public UnoCard getPlayerCard(String pid, int choice) {
        ArrayList<UnoCard> hand = getPlayerHand(pid);
        return hand.get(choice);
    }

    public boolean hasEmptyHand(String pid) {
        return getPlayerHand(pid).isEmpty();
    }

    public boolean validCardPlay(UnoCard card) {
        return card.getColor() == validColor || card.getValue() == validValue || card.getColor() == UnoCard.Color.Wild;
    }

    public void checkPlayerTurn(String pid) throws InvalidPlayerTurnException {
        if (!this.playerIds[this.currentPlayer].equals(pid)) {
            throw new InvalidPlayerTurnException("It is not " + pid + "'s turn", pid);
        }
    }

    public void submitDraw(String pid) throws InvalidPlayerTurnException {
        checkPlayerTurn(pid);

        if (deck.isEmpty()) {
            deck.replaceDeckWith(stockpile);
            deck.shuffle();
        }
        getPlayerHand(pid).add(deck.drawCard());

        if (gameDirection == false) {
            currentPlayer = (currentPlayer + 1) % playerIds.length;
        } else {
            currentPlayer = (currentPlayer - 1) % playerIds.length;
            if (currentPlayer == -1) {
                currentPlayer = playerIds.length - 1;
            }
        }
    }

    public void setCardColor(UnoCard.Color color) {
        validColor = color;
    }

    public void submitPlayerCard(String pid, UnoCard card, UnoCard.Color declaredColor)
            throws InvalidColorSubmissionException, InvalidValueSubmissionException, InvalidPlayerTurnException {

        checkPlayerTurn(pid);
        ArrayList<UnoCard> pHand = getPlayerHand(pid);

        if (!validCardPlay(card)) {
            if (card.getColor() == UnoCard.Color.Wild) {
                validColor = declaredColor;
                validValue = card.getValue();
            } else {
                if (card.getColor() != validColor) {
                    JLabel message = new JLabel("Invalid player move - expected color " + validColor + " but got " + card.getColor());
                    message.setFont(new Font("Arial", Font.BOLD, 48));
                    JOptionPane.showMessageDialog(null, message);
                    throw new InvalidColorSubmissionException("Invalid color", card.getColor(), validColor);
                } else if (card.getValue() != validValue) {
                    JLabel message2 = new JLabel("Invalid player move - expected value " + validValue + " but got " + card.getValue());
                    message2.setFont(new Font("Arial", Font.BOLD, 48));
                    JOptionPane.showMessageDialog(null, message2);
                    throw new InvalidValueSubmissionException("Invalid value", card.getValue(), validValue);
                }
            }
        }

        pHand.remove(card);
        if (hasEmptyHand(this.playerIds[currentPlayer])) {
            JLabel message3 = new JLabel(this.playerIds[currentPlayer] + " won the game!");
            message3.setFont(new Font("Arial", Font.BOLD, 48));
            JOptionPane.showMessageDialog(null, message3);
            System.exit(0);
        }

        validColor = card.getColor();
        validValue = card.getValue();
        stockpile.add(card);

        // Handle special cards
        if (card.getColor() == UnoCard.Color.Wild) {
            validColor = declaredColor;
        }

        if (card.getValue() == UnoCard.Value.DrawTwo) {
            String nextPlayer = playerIds[(currentPlayer + (gameDirection ? -1 : 1)) % playerIds.length];
            getPlayerHand(nextPlayer).add(deck.drawCard());
            getPlayerHand(nextPlayer).add(deck.drawCard());
            JLabel message = new JLabel(nextPlayer + " draws 2 cards!");
            message.setFont(new Font("Arial", Font.BOLD, 24));
            JOptionPane.showMessageDialog(null, message);
        }

        if (card.getValue() == UnoCard.Value.Wild_Four) {
            String nextPlayer = playerIds[(currentPlayer + (gameDirection ? -1 : 1)) % playerIds.length];
            getPlayerHand(nextPlayer).add(deck.drawCard());
            getPlayerHand(nextPlayer).add(deck.drawCard());
            getPlayerHand(nextPlayer).add(deck.drawCard());
            getPlayerHand(nextPlayer).add(deck.drawCard());
            JLabel message = new JLabel(nextPlayer + " draws 4 cards!");
            message.setFont(new Font("Arial", Font.BOLD, 24));
            JOptionPane.showMessageDialog(null, message);
        }

        if (card.getValue() == UnoCard.Value.Skip) {
            JLabel message = new JLabel(playerIds[currentPlayer] + " was skipped!");
            message.setFont(new Font("Arial", Font.BOLD, 48));
            JOptionPane.showMessageDialog(null, message);
        }

        if (card.getValue() == UnoCard.Value.Reverse) {
            JLabel message = new JLabel("Game direction reversed!");
            message.setFont(new Font("Arial", Font.BOLD, 48));
            JOptionPane.showMessageDialog(null, message);
            gameDirection = !gameDirection;
        }

        // Move to next player
        if (gameDirection == false) {
            currentPlayer = (currentPlayer + 1) % playerIds.length;
        } else {
            currentPlayer = (currentPlayer - 1) % playerIds.length;
            if (currentPlayer == -1) {
                currentPlayer = playerIds.length - 1;
            }
        }
    }

    class InvalidPlayerTurnException extends Exception {
        String playerId;

        public InvalidPlayerTurnException(String message, String pid) {
            super(message);
            playerId = pid;
        }

        public String getPid() {
            return playerId;
        }
    }

    class InvalidColorSubmissionException extends Exception {
        private UnoCard.Color expected;
        private UnoCard.Color actual;

        public InvalidColorSubmissionException(String message, UnoCard.Color actual, UnoCard.Color expected) {
            super(message);
            this.actual = actual;
            this.expected = expected;
        }
    }

    class InvalidValueSubmissionException extends Exception {
        private UnoCard.Value expected;
        private UnoCard.Value actual;

        public InvalidValueSubmissionException(String message, UnoCard.Value actual, UnoCard.Value expected) {
            super(message);
            this.actual = actual;
            this.expected = expected;
        }
    }
}


