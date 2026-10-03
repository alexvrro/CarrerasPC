package gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class VentanaJuego extends JFrame {

    private static final int ANCHO_TOTAL = 1200; // dos carriles de 350px cada uno
    private static final int ALTO = 750;

    private PanelJuego panelJuego;

    public VentanaJuego() throws RecursoJuegoException {
        setTitle("Traffic Dash");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        mostrarMenuPrincipal();
    }

    /**
     * Muestra el menú principal y arranca la partida con el modo elegido. Si
     * el usuario elige "Salir" o cierra el cuadro, se termina el programa.
     */
    private void mostrarMenuPrincipal() throws RecursoJuegoException {
        Object[] opciones = {"2 Jugadores", "1 Jugador vs Computadora", "Salir"};
        int seleccion = JOptionPane.showOptionDialog(
                null,
                "¿Cómo quieres jugar?\n\n"
                + "Jugador 1: teclas A / D\n"
                + "Jugador 2 (si aplica): flechas ← / →\n"
                + "ESC: pausa y menú",
                "Traffic Dash - Menú principal",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[1]
        );

        if (seleccion != 0 && seleccion != 1) {
            System.exit(0); // "Salir" o cerró el cuadro
        }
        iniciarJuego(seleccion == 0);
    }

    private void iniciarJuego(boolean dosJugadores) throws RecursoJuegoException {
        panelJuego = new PanelJuego(ANCHO_TOTAL, ALTO, dosJugadores);
        panelJuego.setAlVolverAlMenu(this::volverAlMenuPrincipal);
        add(panelJuego);

        pack();
        setLocationRelativeTo(null);

        panelJuego.requestFocusInWindow();
    }

    /**
     * Cierra la partida actual y vuelve a mostrar el menú principal.
     */
    private void volverAlMenuPrincipal() {
        panelJuego.detener();
        remove(panelJuego);
        setVisible(false);

        try {
            mostrarMenuPrincipal();
            setVisible(true);
            panelJuego.requestFocusInWindow();
        } catch (RecursoJuegoException e) {
            JOptionPane.showMessageDialog(null,
                    "Ocurrió un problema al cargar el juego:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
}