package motor;

import gui.RecursoJuegoException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import model.Auto;
import model.AutoComputadora;
import model.AutoJugador;

public class MotorJuego {

    private Carretera carreteraIzquierda;
    private Carretera carreteraDerecha;
    private CarrilJugadorExtra carrilIzquierdo;
    private CarrilJugadorExtra carrilDerecho;
    private boolean dosJugadores;

    private int altoPantalla;
    private int anchoCarril;

    private BufferedImage imagenAuto;
    private BufferedImage imagenComputadora;
    private BufferedImage[] imagenesTrafico;

    public MotorJuego(int anchoPantalla, int altoPantalla, boolean dosJugadores) throws RecursoJuegoException {
        this.altoPantalla = altoPantalla;
        this.dosJugadores = dosJugadores;
        this.anchoCarril = anchoPantalla / 2;

        int numCarrilesPorJugador = 5; // <-- aqui ajustan cuantos carriles internos quieren

        // Las imágenes se cargan UNA vez, antes de crear los autos
        this.imagenAuto = cargarImagen("/recursos/auto.png");
        this.imagenComputadora = cargarImagen("/recursos/auto_computadora.png");
        this.imagenesTrafico = new BufferedImage[]{
            cargarImagen("/recursos/trafico1.png"),
            cargarImagen("/recursos/trafico2.png"),
            cargarImagen("/recursos/trafico3.png"),
            cargarImagen("/recursos/trafico4.png"),
            cargarImagen("/recursos/trafico5.png"),
            cargarImagen("/recursos/trafico6.png"),
        };

        this.carreteraIzquierda = new Carretera(0, anchoCarril, altoPantalla, 6.0, numCarrilesPorJugador);
        this.carreteraDerecha = new Carretera(anchoCarril, anchoCarril, altoPantalla, 6.0, numCarrilesPorJugador);

        Auto auto1 = segundoJugador(carreteraIzquierda, false);
        Auto auto2 = segundoJugador(carreteraDerecha, !dosJugadores);

        this.carrilIzquierdo = new CarrilJugadorExtra("Jugador 1", carreteraIzquierda, auto1, altoPantalla,
                imagenesTrafico);
        this.carrilDerecho = new CarrilJugadorExtra(dosJugadores ? "Jugador 2" : "Computadora",
                carreteraDerecha, auto2, altoPantalla, imagenesTrafico);
    }

    private BufferedImage cargarImagen(String ruta) throws RecursoJuegoException {
        try (InputStream in = MotorJuego.class.getResourceAsStream(ruta)) {
            if (in == null) {
                throw new RecursoJuegoException("No se encontró la imagen: " + ruta);
            }
            return ImageIO.read(in);
        } catch (IOException e) {
            throw new RecursoJuegoException("Error al leer la imagen: " + ruta, e);
        }
    }

    private Auto segundoJugador(Carretera carretera, boolean esComputadora) {
        int anchoAuto = 46;
        int altoAuto = 80;
        int limiteIzquierdo = carretera.getCarreteraIzquierda() + 10;
        int limiteDerecho = carretera.getCarreteraDerecha() - 10;
        double xInicial = (limiteIzquierdo + limiteDerecho) / 2.0 - anchoAuto / 2.0;
        double yInicial = altoPantalla - 150;

        Auto auto;
        if (esComputadora) {
            auto = new AutoComputadora(xInicial, yInicial, anchoAuto, altoAuto, 6.5,
                    limiteIzquierdo, limiteDerecho);
            auto.setImagen(imagenComputadora);
        } else {
            auto = new AutoJugador(xInicial, yInicial, anchoAuto, altoAuto, 7.0,
                    limiteIzquierdo, limiteDerecho);
            auto.setImagen(imagenAuto);
        }
        return auto;
    }

    public void actualizar() {
        carrilIzquierdo.actualizar();
        carrilDerecho.actualizar();
    }

    public void reiniciar() {
        carrilIzquierdo.reiniciar(segundoJugador(carreteraIzquierda, false));
        carrilDerecho.reiniciar(segundoJugador(carreteraDerecha, !dosJugadores));
    }

    public boolean isJuegoTerminado() {
        return carrilIzquierdo.isChocado() && carrilDerecho.isChocado();
    }

    public CarrilJugadorExtra getcarrilIzquierdo() {
        return carrilIzquierdo;
    }

    public CarrilJugadorExtra getcarrilDerecho() {
        return carrilDerecho;
    }

    public boolean isDosJugadores() {
        return dosJugadores;
    }
}