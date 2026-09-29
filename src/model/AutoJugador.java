package model;

import java.awt.Color;

public class AutoJugador extends Auto {

    private int limiteIzquierdo;
    private int limiteDerecho;
    private boolean movimientoIzquierda = false;
    private boolean movimientoDerecha = false;

    public AutoJugador(double x, double y, int ancho, int alto, double velocidad,
            int limiteIzquierdo, int limiteDerecho) {
        super(x, y, ancho, alto, velocidad, Color.BLUE);
        this.limiteIzquierdo = limiteIzquierdo;
        this.limiteDerecho = limiteDerecho;
    }

    @Override
    public void mover() {
        if (movimientoIzquierda) {
            x -= velocidad;
        }
        if (movimientoDerecha) {
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

    public void setMovimientoIzquierda(boolean valor) {
        this.movimientoIzquierda = valor;
    }

    public void setMovimientoDerecha(boolean valor) {
        this.movimientoDerecha = valor;
    }
}
