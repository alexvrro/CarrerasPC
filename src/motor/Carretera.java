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
    private int numCarriles;
    private double anchoPorCarril;

    public Carretera(int offsetX, int anchoCarril, int alto, double velocidadScroll, int numCarriles) {
        this.offsetX = offsetX;
        this.anchoCarril = anchoCarril;
        this.alto = alto;
        this.velocidadScroll = velocidadScroll;
        this.carreteraIzquierda = offsetX + anchoCarril / 6;
        this.carreteraDerecha = offsetX + anchoCarril - anchoCarril / 6;
        this.numCarriles = Math.max(1, numCarriles);
        this.anchoPorCarril = (carreteraDerecha - carreteraIzquierda) / (double) this.numCarriles;
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

        // Lineas discontinuas entre cada carril interno
        g.setColor(Color.YELLOW);
        int altoLinea = 40;
        int espacio = 30;
        for (int i = 1; i < numCarriles; i++) {
            int xLinea = (int) (carreteraIzquierda + anchoPorCarril * i) - 4;
            for (int y = (int) offsetLineas - altoLinea; y < alto; y += altoLinea + espacio) {
                g.fillRect(xLinea, y, 8, altoLinea);
            }
        }
    }

    /**
     * Devuelve la posicion X del CENTRO del carril interno indicado (0 = el mas
     * a la izquierda). Util para alinear el trafico (o el auto) a un carril.
     */
    public double getCentroCarril(int indiceCarril) {
        int i = Math.max(0, Math.min(indiceCarril, numCarriles - 1));
        return carreteraIzquierda + anchoPorCarril * i + anchoPorCarril / 2.0;
    }

    public int getNumCarriles() {
        return numCarriles;
    }

    public int getCarreteraIzquierda() {
        return carreteraIzquierda;
    }

    public int getCarreteraDerecha() {
        return carreteraDerecha;
    }
}