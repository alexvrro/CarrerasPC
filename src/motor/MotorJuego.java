package motor;

import model.Auto;
import model.AutoComputadora;
import model.AutoJugador;

public class MotorJuego {

    private Carretera carreteraIzquierda;
    private Carretera carreteraDerecha;
    private CarrilJugadorExtra carrilIzquierdo;
    private CarrilJugadorExtra carrilDerecho;
    private boolean dosJugadores;

    private int altoPantalla;
    private int anchoCarril;

    public MotorJuego(int anchoPantalla, int altoPantalla, boolean dosJugadores) {
        this.altoPantalla = altoPantalla;
        this.dosJugadores = dosJugadores;
        this.anchoCarril = anchoPantalla / 2;

        this.carreteraIzquierda = new Carretera(0, anchoCarril, altoPantalla, 6.0);
        this.carreteraDerecha = new Carretera(anchoCarril, anchoCarril, altoPantalla, 6.0);

        Auto auto1 = segundoJugador(carreteraIzquierda, false);
        Auto auto2 = segundoJugador(carreteraDerecha, !dosJugadores);

        this.carrilIzquierdo = new CarrilJugadorExtra("Jugador 1", carreteraIzquierda, auto1, altoPantalla);
        this.carrilDerecho = new CarrilJugadorExtra(dosJugadores ? "Jugador 2" : "Computadora",
                carreteraDerecha, auto2, altoPantalla);
    }

    private Auto segundoJugador(Carretera carretera, boolean esComputadora) {
        int anchoAuto = 46;
        int altoAuto = 80;
        int limiteIzquierdo = carretera.getCarreteraIzquierda() + 10;
        int limiteDerecho = carretera.getCarreteraDerecha() - 10;
        double xInicial = (limiteIzquierdo + limiteDerecho) / 2.0 - anchoAuto / 2.0;
        double yInicial = altoPantalla - 150;

        if (esComputadora) {
            return new AutoComputadora(xInicial, yInicial, anchoAuto, altoAuto, 6.5,
                    limiteIzquierdo, limiteDerecho);
        } else {
            return new AutoJugador(xInicial, yInicial, anchoAuto, altoAuto, 7.0,
                    limiteIzquierdo, limiteDerecho);
        }
    }

    public void actualizar() {
        carrilIzquierdo.actualizar();
        carrilDerecho.actualizar();
    }

    public void reiniciar() {
        carrilIzquierdo.reiniciar(segundoJugador(carreteraIzquierda, false));
        carrilDerecho.reiniciar(segundoJugador(carreteraDerecha, !dosJugadores));
    }

    public boolean isJuegoTerminado() {
        return carrilIzquierdo.isChocado() && carrilDerecho.isChocado();
    }

    public CarrilJugadorExtra getcarrilIzquierdo() {
        return carrilIzquierdo;
    }

    public CarrilJugadorExtra getcarrilDerecho() {
        return carrilDerecho;
    }

    public boolean isDosJugadores() {
        return dosJugadores;
    }
}