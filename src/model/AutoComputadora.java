package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class AutoComputadora extends Auto {

    private int limiteIzquierdo;
    private int limiteDerecho;
    private List<AutoTrafico> traficoPercibido = new ArrayList<>();
    private static final double DISTANCIA_REACCION = 170;

    public AutoComputadora(double x, double y, int ancho, int alto, double velocidad, int limiteIzquierdo,
            int limiteDerecho) {
        super(x, y, ancho, alto, velocidad, new Color(30, 150, 60));
        this.limiteIzquierdo = limiteIzquierdo;
        this.limiteDerecho = limiteDerecho;
    }

    @Override
    public void entorno(List<AutoTrafico> traficoCercano) {
        this.traficoPercibido = traficoCercano;
    }

    @Override
    public void mover() {
        AutoTrafico peligro = autoMasCercano();
        if (peligro != null) {
            double centroPeligro = peligro.getX() + peligro.getAncho() / 2.0;
            double centroAuto = x + ancho / 2.0;
            if (centroPeligro < centroAuto) {
                x += velocidad;
            } else {
                x -= velocidad;
            }
        }
        if (x < limiteIzquierdo) {
            x = limiteIzquierdo;
        }
        if (x + ancho > limiteDerecho) {
            x = limiteDerecho - ancho;
        }

    }

    private AutoTrafico autoMasCercano() {
        AutoTrafico masCercano = null;
        double menorDistancia = DISTANCIA_REACCION;

        for (AutoTrafico t : traficoPercibido) {
            boolean seSolapaEnX = t.getX() + t.getAncho() > x - 15 && t.getX() < x + ancho + 15;
            double distanciaVertical = y - (t.getY() + t.getAlto());
            if (seSolapaEnX && distanciaVertical > - 20 && distanciaVertical < menorDistancia) {
                menorDistancia = distanciaVertical;
                masCercano = t;
            }
        }
        return masCercano;
    }
}
