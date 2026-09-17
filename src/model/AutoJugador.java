package model;

import java.awt.Color;

/**
 * Auto controlado por el jugador. Se mueve horizontalmente segun el teclado.
 * El motor de juego es quien le avisa hacia donde moverse (ver moverIzquierda/moverDerecha).
 */
public class AutoJugador extends Auto {

    private int limiteIzquierdo;
    private int limiteDerecho;
    private boolean moviendoIzquierda = false;
    private boolean moviendoDerecha = false;

    public AutoJugador(double x, double y, int ancho, int alto, double velocidad,
                        int limiteIzquierdo, int limiteDerecho) {
        super(x, y, ancho, alto, velocidad, Color.BLUE);
        this.limiteIzquierdo = limiteIzquierdo;
        this.limiteDerecho = limiteDerecho;
    }

    @Override
    public void mover() {
        if (moviendoIzquierda) {
            x -= velocidad;
        }
        if (moviendoDerecha) {
            x += velocidad;
        }
        // No dejar que el auto se salga de la carretera
        if (x < limiteIzquierdo) {
            x = limiteIzquierdo;
        }
        if (x + ancho > limiteDerecho) {
            x = limiteDerecho - ancho;
        }
    }

    public void setMoviendoIzquierda(boolean valor) {
        this.moviendoIzquierda = valor;
    }

    public void setMoviendoDerecha(boolean valor) {
        this.moviendoDerecha = valor;
    }
}
