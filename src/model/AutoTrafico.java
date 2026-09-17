package model;

import java.awt.Color;

/**
 * Auto de trafico que viene "de frente": avanza solo hacia abajo en la pantalla,
 * dando la sensacion de que el jugador se mueve hacia adelante en la carretera.
 */
public class AutoTrafico extends Auto {

    public AutoTrafico(double x, double y, int ancho, int alto, double velocidad, Color color) {
        super(x, y, ancho, alto, velocidad, color);
    }

    @Override
    public void mover() {
        y += velocidad;
    }

    public boolean saliDePantalla(int altoPantalla) {
        return y > altoPantalla;
    }
}
