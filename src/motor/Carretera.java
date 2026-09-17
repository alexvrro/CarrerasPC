package motor;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Se encarga del fondo de la carretera: el pasto a los lados,
 * el asfalto y las lineas discontinuas que se mueven para dar
 * sensacion de velocidad.
 */
public class Carretera {

    private int ancho;
    private int alto;
    private int carreteraIzquierda;
    private int carreteraDerecha;
    private double offsetLineas = 0;
    private double velocidadScroll;

    public Carretera(int ancho, int alto, double velocidadScroll) {
        this.ancho = ancho;
        this.alto = alto;
        this.velocidadScroll = velocidadScroll;
        this.carreteraIzquierda = ancho / 6;
        this.carreteraDerecha = ancho - ancho / 6;
    }

    public void actualizar() {
        offsetLineas += velocidadScroll;
        int altoLinea = 40;
        int espacio = 30;
        if (offsetLineas >= altoLinea + espacio) {
            offsetLineas = 0;
        }
    }

    public void dibujar(Graphics g) {
        // Pasto
        g.setColor(new Color(60, 150, 60));
        g.fillRect(0, 0, ancho, alto);

        // Asfalto
        g.setColor(new Color(50, 50, 50));
        g.fillRect(carreteraIzquierda, 0, carreteraDerecha - carreteraIzquierda, alto);

        // Linea central discontinua
        g.setColor(Color.YELLOW);
        int centroX = (carreteraIzquierda + carreteraDerecha) / 2 - 4;
        int altoLinea = 40;
        int espacio = 30;
        for (int y = (int) offsetLineas - altoLinea; y < alto; y += altoLinea + espacio) {
            g.fillRect(centroX, y, 8, altoLinea);
        }
    }

    public int getCarreteraIzquierda() {
        return carreteraIzquierda;
    }

    public int getCarreteraDerecha() {
        return carreteraDerecha;
    }
}
