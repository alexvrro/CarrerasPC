package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

public abstract class Auto {

    protected double x;
    protected double y;
    protected int ancho;
    protected int alto;
    protected double velocidad;
    protected Color color;
    protected BufferedImage imagen;

    public Auto(double x, double y, int ancho, int alto, double velocidad, Color color) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.velocidad = velocidad;
        this.color = color;
    }

    public abstract void mover();

    public void entorno(List<AutoTrafico> traficoCercano){
    }

    public Rectangle getHitbox() {
        return new Rectangle((int) x, (int) y, ancho, alto);
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
    
    public void setImagen(BufferedImage imagen) {
    this.imagen = imagen;
    }

    public void dibujar(Graphics g) {
        if (imagen != null) {
            g.drawImage(imagen, (int) x, (int) y, ancho, alto, null);
        } else {
            g.setColor(color);
            g.fillRoundRect((int) x, (int) y, ancho, alto, 12, 12);
        }
    }
}
