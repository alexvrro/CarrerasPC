package gui;

/**
 * Se lanza cuando falla la carga de un recurso del juego (una imagen, un
 * sonido, etc.). Se captura en Main con try-catch.
 */
public class RecursoJuegoException extends Exception {

    public RecursoJuegoException(String mensaje) {
        super(mensaje);
    }

    public RecursoJuegoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
