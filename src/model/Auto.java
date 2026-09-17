package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Clase base para cualquier auto en la pista (jugador o trafico).
 */
public abstract class Auto {

    protected double x;
    protected double y;
    protected int ancho;
    protected int alto;
    protected double velocidad;
    protected Color color;

    public Auto(double x, double y, int ancho, int alto, double velocidad, Color color) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.velocidad = velocidad;
        this.color = color;
    }

    /**
     * Cada tipo de auto decide cómo se mueve en cada "tick" del juego.
     * AutoJugador: se mueve según el teclado.
     * AutoTrafico: avanza solo hacia abajo.
     */
    public abstract void mover();

    public Rectangle getHitbox() {
        return new Rectangle((int) x, (int) y, ancho, alto);
    }

    public void dibujar(Graphics g) {
        g.setColor(color);
        g.fillRoundRect((int) x, (int) y, ancho, alto, 12, 12);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public double getVelocidad() {
        return velocidad;
    }

    public void setY(double y) {
        this.y = y;
    }
}
