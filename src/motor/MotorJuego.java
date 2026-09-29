package motor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.Auto;
import model.AutoJugador;
import model.AutoTrafico;

/**
 * El "cerebro" del juego: mueve todo, detecta colisiones,
 * genera trafico nuevo y lleva el puntaje.
 * No sabe nada de Swing ni de como se dibuja en pantalla.
 */
public class MotorJuego {

    private Carretera carretera;
    private AutoJugador jugador;
    private List<AutoTrafico> trafico = new ArrayList<>();
    private int puntaje = 0;
    private boolean juegoTerminado = false;

    private int anchoPantalla;
    private int altoPantalla;
    private Random random = new Random();
    private int contadorSpawn = 0;
    private int frecuenciaSpawn = 60;

    public MotorJuego(int anchoPantalla, int altoPantalla) {
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        this.carretera = new Carretera(anchoPantalla, altoPantalla, 6.0);

        int anchoAuto = 50;
        int altoAuto = 90;
        double xInicial = anchoPantalla / 2.0 - anchoAuto / 2.0;
        this.jugador = new AutoJugador(xInicial, altoPantalla - 150, anchoAuto, altoAuto, 7.0,
                carretera.getCarreteraIzquierda() + 10,
                carretera.getCarreteraDerecha() - 10);
    }

    /**
     * Se llama en cada "tick" del Timer de Swing.
     */
    public void actualizar() {
        if (juegoTerminado) {
            return;
        }

        carretera.actualizar();
        jugador.mover();

        for (AutoTrafico auto : trafico) {
            auto.mover();
        }

        generarTraficoSiToca();
        eliminarTraficoFueraDePantalla();
        detectarColisiones();

        puntaje++;
    }

    private void generarTraficoSiToca() {
        contadorSpawn++;
        if (contadorSpawn >= frecuenciaSpawn) {
            contadorSpawn = 0;
            int anchoAuto = 50;
            int altoAuto = 90;
            int minX = carretera.getCarreteraIzquierda() + 10;
            int maxX = carretera.getCarreteraDerecha() - 10 - anchoAuto;
            double x = minX + random.nextDouble() * (maxX - minX);
            double velocidad = 4.0 + random.nextDouble() * 3.0;
            Color color = coloresTrafico[random.nextInt(coloresTrafico.length)];
            trafico.add(new AutoTrafico(x, -altoAuto, anchoAuto, altoAuto, velocidad, color));
        }
    }

    private static final Color[] coloresTrafico = {
            Color.RED, Color.ORANGE, new Color(150, 0, 150), Color.DARK_GRAY
    };

    private void eliminarTraficoFueraDePantalla() {
        trafico.removeIf(auto -> auto.outOfPantalla(altoPantalla));
    }

    private void detectarColisiones() {
        for (AutoTrafico auto : trafico) {
            if (jugador.getHitbox().intersects(auto.getHitbox())) {
                juegoTerminado = true;
                return;
            }
        }
    }

    // ---- Metodos que llama la GUI (teclado) ----

    public void moverIzquierda(boolean activo) {
        jugador.setMovimientoIzquierda(activo);
    }

    public void moverDerecha(boolean activo) {
        jugador.setMovimientoDerecha(activo);
    }

    public void reiniciar() {
        this.trafico.clear();
        this.puntaje = 0;
        this.juegoTerminado = false;
        int anchoAuto = jugador.getAncho();
        int altoAuto = jugador.getAlto();
        double xInicial = anchoPantalla / 2.0 - anchoAuto / 2.0;
        this.jugador = new AutoJugador(xInicial, altoPantalla - 150, anchoAuto, altoAuto, 7.0,
                carretera.getCarreteraIzquierda() + 10,
                carretera.getCarreteraDerecha() - 10);
    }

    // ---- Getters para que la GUI pueda dibujar ----

    public Carretera getCarretera() {
        return carretera;
    }

    public Auto getJugador() {
        return jugador;
    }

    public List<AutoTrafico> getTrafico() {
        return trafico;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }
}
