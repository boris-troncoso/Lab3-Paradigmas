import java.util.List;

/**
 * Efecto que hace que el jugador actual robe cartas de su mazo. Se usa en
 * cartas de entrenador (RF16) y en habilidades (RF17).
 *
 * @author Boris Troncoso 195598568
 */
public class EfectoRobarCartas_BorisTroncoso_195598568
        implements Efecto_BorisTroncoso_195598568 {

    /** cantidad de cartas que se roban */
    private final int cantidad;

    /**
     * Constructor del efecto.
     *
     * @param cantidad cuantas cartas se roban (mayor que 0)
     */
    public EfectoRobarCartas_BorisTroncoso_195598568(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a robar debe ser positiva");
        }
        this.cantidad = cantidad;
    }

    /**
     * Roba las cartas para el jugador actual.
     *
     * @param juego partida actual
     * @param args no se usan
     * @return la partida con las cartas robadas
     */
    @Override
    public Juego_BorisTroncoso_195598568 ejecutar(Juego_BorisTroncoso_195598568 juego,
                                                  List<Object> args) {
        int robadas = juego.robarCartasJugadorActual(cantidad);
        juego.registrarMensaje(juego.getJugadorActual().getNombre() + " roba "
                + robadas + " carta(s) por el efecto.");
        return juego;
    }

    /**
     * Descripcion para el menu.
     *
     * @return texto del efecto
     */
    @Override
    public String getDescripcion() {
        return "Roba " + cantidad + " carta(s) del mazo";
    }
}
