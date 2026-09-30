import gui.RecursoJuegoException;
import gui.VentanaJuego;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                VentanaJuego ventana = new VentanaJuego();
                ventana.setVisible(true);

            } catch (RecursoJuegoException e) {
                System.err.println("No se pudo cargar un recurso del juego: " + e.getMessage());
                JOptionPane.showMessageDialog(null,
                        "Ocurrió un problema al cargar el juego:\n" + e.getMessage(),
                        "Error al iniciar", JOptionPane.ERROR_MESSAGE);

            } catch (Exception e) {
                System.err.println("Error inesperado al iniciar el juego: " + e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Ocurrió un error inesperado al iniciar el juego.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}