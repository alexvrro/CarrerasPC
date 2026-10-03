package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.Timer;
import model.Auto;
import model.AutoJugador;
import motor.CarrilJugadorExtra;
import motor.MotorJuego;

public class PanelJuego extends JPanel {

    private MotorJuego motorJuego;
    private Timer temporizador;
    private boolean pausado = false;
    private Runnable alVolverAlMenu;

    public PanelJuego(int ancho, int alto, boolean dosJugadores) throws RecursoJuegoException {
        setPreferredSize(new java.awt.Dimension(ancho, alto));
        setFocusable(true);

        this.motorJuego = new MotorJuego(ancho, alto, dosJugadores);

        this.temporizador = new Timer(9, e -> {
            if (!pausado) {
                motorJuego.actualizar();
            }
            repaint();
        });

        configurarTeclado();
        temporizador.start();
    }

    /**
     * Quien crea el panel (la ventana) indica qué hacer cuando el jugador
     * pide volver al menú principal.
     */
    public void setAlVolverAlMenu(Runnable alVolverAlMenu) {
        this.alVolverAlMenu = alVolverAlMenu;
    }

    /**
     * Detiene el reloj del juego. Se llama al salir de la partida.
     */
    public void detener() {
        temporizador.stop();
    }

    private void volverAlMenu() {
        if (alVolverAlMenu != null) {
            alVolverAlMenu.run();
        }
    }

    private void configurarTeclado() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int tecla = e.getKeyCode();
                boolean terminado = motorJuego.isJuegoTerminado();

                // ESC: pausa / continuar (no aplica cuando la partida ya terminó)
                if (tecla == KeyEvent.VK_ESCAPE && !terminado) {
                    pausado = !pausado;
                    repaint();
                    return;
                }

                // M: volver al menú principal (desde la pausa o al terminar)
                if (tecla == KeyEvent.VK_M && (pausado || terminado)) {
                    volverAlMenu();
                    return;
                }

                // En pausa se ignoran los controles de los autos
                if (pausado) {
                    return;
                }

                manejarTecla(tecla, true);
                if (tecla == KeyEvent.VK_ENTER && terminado) {
                    motorJuego.reiniciar();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                manejarTecla(e.getKeyCode(), false);
            }
        });
    }

    private void manejarTecla(int codigo, boolean activo) {
        // Jugador 1 (pista izquierda): A / D
        if (codigo == KeyEvent.VK_A) {
            moverSiEsHumano(motorJuego.getcarrilIzquierdo(), true, activo);
        } else if (codigo == KeyEvent.VK_D) {
            moverSiEsHumano(motorJuego.getcarrilIzquierdo(), false, activo);
        } // Jugador 2 (pista derecha): flechas Izquierda / Derecha
        // (si la pista derecha es Computadora, esto simplemente no tiene efecto)
        else if (codigo == KeyEvent.VK_LEFT) {
            moverSiEsHumano(motorJuego.getcarrilDerecho(), true, activo);
        } else if (codigo == KeyEvent.VK_RIGHT) {
            moverSiEsHumano(motorJuego.getcarrilDerecho(), false, activo);
        }
    }

    private void moverSiEsHumano(CarrilJugadorExtra pista, boolean izquierda, boolean activo) {
        Auto auto = pista.getAutoJugador();
        if (auto instanceof AutoJugador humano) {
            if (izquierda) {
                humano.setMovimientoIzquierda(activo);
            } else {
                humano.setMovimientoDerecha(activo);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        motorJuego.getcarrilIzquierdo().dibujar(g);
        motorJuego.getcarrilDerecho().dibujar(g);

        dibujarDivisor(g);
        dibujarPuntajes(g);

        if (motorJuego.getcarrilIzquierdo().isChocado() && !motorJuego.isJuegoTerminado()) {
            dibujarEtiquetaChocado(g, motorJuego.getcarrilIzquierdo());
        }
        if (motorJuego.getcarrilDerecho().isChocado() && !motorJuego.isJuegoTerminado()) {
            dibujarEtiquetaChocado(g, motorJuego.getcarrilDerecho());
        }

        if (motorJuego.isJuegoTerminado()) {
            dibujarFinDeJuego(g);
        } else if (pausado) {
            dibujarPausa(g);
        }
    }

    private void dibujarDivisor(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(getWidth() / 2 - 2, 0, 4, getHeight());
    }

    private void dibujarPuntajes(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.setColor(Color.WHITE);

        CarrilJugadorExtra izq = motorJuego.getcarrilIzquierdo();
        CarrilJugadorExtra der = motorJuego.getcarrilDerecho();

        g.drawString(izq.getNombre() + ": " + izq.getPuntaje(), 16, 28);

        String texto2 = der.getNombre() + ": " + der.getPuntaje();
        int ancho2 = g.getFontMetrics().stringWidth(texto2);
        g.drawString(texto2, getWidth() - ancho2 - 16, 28);
    }

    private void dibujarEtiquetaChocado(Graphics g, CarrilJugadorExtra pista) {
        int centroX = pista == motorJuego.getcarrilIzquierdo() ? getWidth() / 4 : getWidth() * 3 / 4;
        g.setColor(new Color(0, 0, 0, 140));
        g.fillRect(centroX - getWidth() / 4, 0, getWidth() / 2, getHeight());

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        String msg = "Fuera";
        int msgAncho = g.getFontMetrics().stringWidth(msg);
        g.drawString(msg, centroX - msgAncho / 2, getHeight() / 2);
    }

    private void dibujarPausa(Graphics g) {
        g.setColor(new Color(0, 0, 0, 170));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        centrarTexto(g, "PAUSA", getHeight() / 2 - 30);

        g.setFont(new Font("Arial", Font.PLAIN, 18));
        centrarTexto(g, "ESC - Continuar", getHeight() / 2 + 10);
        centrarTexto(g, "M - Volver al menú principal", getHeight() / 2 + 40);
    }

    private void dibujarFinDeJuego(Graphics g) {
        g.setColor(new Color(0, 0, 0, 170));
        g.fillRect(0, 0, getWidth(), getHeight());

        CarrilJugadorExtra izq = motorJuego.getcarrilIzquierdo();
        CarrilJugadorExtra der = motorJuego.getcarrilDerecho();
        String ganador;
        if (izq.getPuntaje() > der.getPuntaje()) {
            ganador = "¡Gana " + izq.getNombre() + "!";
        } else if (der.getPuntaje() > izq.getPuntaje()) {
            ganador = "¡Gana " + der.getNombre() + "!";
        } else {
            ganador = "¡Empate!";
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        centrarTexto(g, ganador, getHeight() / 2 - 30);

        g.setFont(new Font("Arial", Font.PLAIN, 18));
        centrarTexto(g, izq.getNombre() + ": " + izq.getPuntaje()
                + "   |   " + der.getNombre() + ": " + der.getPuntaje(), getHeight() / 2 + 5);

        centrarTexto(g, "ENTER - Reintentar", getHeight() / 2 + 40);
        centrarTexto(g, "M - Volver al menú principal", getHeight() / 2 + 68);
    }

    private void centrarTexto(Graphics g, String texto, int y) {
        int ancho = g.getFontMetrics().stringWidth(texto);
        g.drawString(texto, (getWidth() - ancho) / 2, y);
    }
}