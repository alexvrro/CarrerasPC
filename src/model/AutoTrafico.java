package model;

import java.awt.Color;

public class AutoTrafico extends Auto {

    public AutoTrafico(double x, double y, int ancho, int alto, double velocidad, Color color) {
        super(x, y, ancho, alto, velocidad, color);
    }

    @Override
    public void mover() {
        y += velocidad;
    }

    public boolean outOfPantalla(int altoPantalla) {
        return y > altoPantalla;
    }
}
