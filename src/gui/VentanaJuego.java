package gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class VentanaJuego extends JFrame {

    public VentanaJuego() throws RecursoJuegoException {
        setTitle("Traffic Dash");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        boolean dosJugadores = preguntarModoDeJuego();

        int anchoTotal = 700; // dos carriles de 350px cada uno
        int alto = 650;
        PanelJuego panelJuego = new PanelJuego(anchoTotal, alto, dosJugadores);
        add(panelJuego);

        pack();
        setLocationRelativeTo(null);

        panelJuego.requestFocusInWindow();
    }

    private boolean preguntarModoDeJuego() {
        Object[] opciones = {"2 Jugadores", "1 Jugador vs Computadora"};
        int seleccion = JOptionPane.showOptionDialog(
                null,
                "¿Cómo quieres jugar?\n\nJugador 1: teclas A / D\nJugador 2 (si aplica): flechas ← / →",
                "Traffic Dash",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[1]
        );
        // Si cierran el dialogo sin elegir, por defecto: contra la computadora
        return seleccion == 0;
    }
}