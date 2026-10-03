package motor;
 
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.Auto;
import model.AutoTrafico;
 
public class CarrilJugadorExtra {
 
    private String nombre;
    private Carretera carretera;
    private Auto autoJugador;
    private List<AutoTrafico> trafico = new ArrayList<>();
    private int puntaje = 0;
    private boolean chocado = false;
 
    private int carrilIzquierdo;
    private int carrilDerecho;
    private int altoPantalla;
    private Random random = new Random();
    private int contadorSpawn = 0;
    private int frecuenciaSpawn = 60;
    private BufferedImage[] imagenesTrafico;
    private static final Color[] COLORES_TRAFICO = {
        Color.red, Color.orange, new Color(150, 0, 150), Color.DARK_GRAY
    };
 
    public CarrilJugadorExtra(String nombre, Carretera carretera, Auto autoJugador, int altoPantalla,
            BufferedImage[] imagenesTrafico) {
        this.nombre = nombre;
        this.carretera = carretera;
        this.autoJugador = autoJugador;
        this.altoPantalla = altoPantalla;
        this.imagenesTrafico = imagenesTrafico;
        this.carrilIzquierdo = carretera.getCarreteraIzquierda() + 10;
        this.carrilDerecho = carretera.getCarreteraDerecha() - 10;
    }
 
    public void actualizar() {
        if (chocado) {
            return;
        }
 
        carretera.actualizar();
 
        autoJugador.entorno(trafico);
        autoJugador.mover();
 
        for (AutoTrafico auto : trafico) {
            auto.mover();
        }
 
        siSeNecesitaMasTraficoSeGeneraParaMasDificultadOParaLoQueSeaQueHagaPerderAlJugador();
        trafico.removeIf(auto -> auto.outOfPantalla(altoPantalla));
        detectarColision();
 
        puntaje++;
 
    }
 
    private void siSeNecesitaMasTraficoSeGeneraParaMasDificultadOParaLoQueSeaQueHagaPerderAlJugador() {
        contadorSpawn++;
        if (contadorSpawn >= frecuenciaSpawn) {
            contadorSpawn = 0;
            int anchoAuto = 50;
            int altoAuto = 90;
            int maxX = carrilDerecho - anchoAuto;
            if (maxX <= carrilIzquierdo) {
                return;
            }
            double x = carrilIzquierdo + random.nextDouble() * (maxX - carrilIzquierdo);
            double velocidad = 4.0 + random.nextDouble() * 3.0;
            Color color = COLORES_TRAFICO[random.nextInt(COLORES_TRAFICO.length)];
            AutoTrafico nuevo = new AutoTrafico(x, -altoAuto, anchoAuto, altoAuto, velocidad, color);
            // Imagen al azar para que el tráfico no se vea todo igual
            nuevo.setImagen(imagenesTrafico[random.nextInt(imagenesTrafico.length)]);
            trafico.add(nuevo);
        }
    }
 
    private void detectarColision() {
        for (AutoTrafico auto : trafico) {
            if (autoJugador.getHitbox().intersects(auto.getHitbox())) {
                chocado = true;
                return;
            }
        }
    }
 
    public void dibujar(Graphics graphics) {
        carretera.dibujar(graphics);
        for (AutoTrafico auto : trafico) {
            auto.dibujar(graphics);
        }
        autoJugador.dibujar(graphics);
    }
 
    public void reiniciar(Auto nuevoAuto) {
        this.autoJugador = nuevoAuto;
        this.trafico.clear();
        this.puntaje = 0;
        this.chocado = false;
        this.contadorSpawn = 0;
    }
 
    public String getNombre() {
        return nombre;
    }
 
    public Auto getAutoJugador() {
        return autoJugador;
    }
 
    public int getPuntaje() {
        return puntaje;
    }
 
    public boolean isChocado() {
        return chocado;
    }
 
    public int getCarrilIzquierdo() {
        return carrilIzquierdo;
    }
 
    public int getCarrilDerecho() {
        return carrilDerecho;
    }
}
 