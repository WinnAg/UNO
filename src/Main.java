public class Main {
    public static void main(String[] args) {
        // Define los jugadores
        String[] jugadores = {"Jugador1", "Jugador2", "Jugador3", "Jugador4"};

        // Crea el juego con esos jugadores
        Juego juego = new Juego(jugadores);

        // Inicia el juego (elige carta válida inicial y aplica efectos si corresponde)
        juego.start(juego);

        // Imprime el jugador que empieza
        System.out.println("El juego ha comenzado.");
        System.out.println("Turno actual: " + juego.getCurrentPlayer());

        // Muestra la carta en la cima del montón
        System.out.println("Carta actual: " + juego.getTopCard());
    }
}

