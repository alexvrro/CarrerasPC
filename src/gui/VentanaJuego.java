package gui;

import javax.swing.JFrame;

public class VentanaJuego extends JFrame {

    public VentanaJuego() throws RecursoJuegoException {
        setTitle("Traffic Dash");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        PanelJuego panelJuego = new PanelJuego(400, 650);
        add(panelJuego);

        pack();
        setLocationRelativeTo(null);

        // El panel necesita el foco para recibir el teclado
        panelJuego.requestFocusInWindow();
    }
}
