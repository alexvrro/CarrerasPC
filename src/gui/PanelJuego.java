package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.Timer;
import model.Auto;
import model.AutoTrafico;
import motor.MotorJuego;

/**
 * Panel donde se dibuja y se juega la carrera. Se puede colocar dentro de una
 * ventana hecha en NetBeans como un JPanel personalizado.
 */
public class PanelJuego extends JPanel {

    private MotorJuego motor;
    private Timer timer;

    public PanelJuego(int ancho, int alto) {
        setPreferredSize(new java.awt.Dimension(ancho, alto));
        setFocusable(true);

        this.motor = new MotorJuego(ancho, alto);

        // Bucle del juego: se ejecuta cada 16 ms (~60 fps)
        this.timer = new Timer(9, e -> {
            motor.actualizar();
            repaint();
        });

        configurarTeclado();
        timer.start();
    }

    private void configurarTeclado() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
                    motor.moverIzquierda(true);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
                    motor.moverDerecha(true);
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER && motor.isJuegoTerminado()) {
                    motor.reiniciar();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
                    motor.moverIzquierda(false);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
                    motor.moverDerecha(false);
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        motor.getCarretera().dibujar(g);

        for (AutoTrafico auto : motor.getTrafico()) {
            auto.dibujar(g);
        }

        Auto jugador = motor.getJugador();
        jugador.dibujar(g);

        dibujarPuntaje(g);

        if (motor.isJuegoTerminado()) {
            dibujarFinDeJuego(g);
        }
    }

    private void dibujarPuntaje(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 22));
        g.drawString("Puntaje: " + motor.getPuntaje(), 20, 35);
    }

    private void dibujarFinDeJuego(Graphics g) {
        g.setColor(new Color(0, 0, 0, 160));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 32));
        String msg = "¡Chocaste!";
        int msgAncho = g.getFontMetrics().stringWidth(msg);
        g.drawString(msg, (getWidth() - msgAncho) / 2, getHeight() / 2 - 20);

        g.setFont(new Font("Arial", Font.PLAIN, 18));
        String msg2 = "Presiona ENTER para reintentar";
        int msg2Ancho = g.getFontMetrics().stringWidth(msg2);
        g.drawString(msg2, (getWidth() - msg2Ancho) / 2, getHeight() / 2 + 15);
    }
}
