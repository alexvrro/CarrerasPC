package motor;

import java.awt.Color;
import java.awt.Graphics;

public class Carretera {

    private int offsetX;
    private int anchoCarril;
    private int alto;
    private int carreteraIzquierda;
    private int carreteraDerecha;
    private double offsetLineas = 0;
    private double velocidadScroll;

    public Carretera(int offsetX, int anchoCarril, int alto, double velocidadScroll) {
        this.offsetX = offsetX;
        this.anchoCarril = anchoCarril;
        this.alto = alto;
        this.velocidadScroll = velocidadScroll;
        this.carreteraIzquierda = offsetX + anchoCarril / 6;
        this.carreteraIzquierda = offsetX + anchoCarril - anchoCarril / 6;
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
        g.fillRect(offsetX, 0, anchoCarril, alto);

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
