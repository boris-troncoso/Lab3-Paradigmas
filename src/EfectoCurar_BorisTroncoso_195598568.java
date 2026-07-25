import java.util.List;

/**
 * Efecto que cura dano a un Pokemon en juego. Si en los argumentos viene una
 * CartaEnJuego se cura esa, si no se cura el activo del jugador actual
 * (RF16 y RF17).
 *
 * @author Boris Troncoso 195598568
 */
public class EfectoCurar_BorisTroncoso_195598568
        implements Efecto_BorisTroncoso_195598568 {

    /** puntos de dano que quita el efecto */
    private final int puntos;

    /**
     * Constructor del efecto.
     *
     * @param puntos dano que se cura (mayor que 0)
     */
    public EfectoCurar_BorisTroncoso_195598568(int puntos) {
        if (puntos <= 0) {
            throw new IllegalArgumentException("Los puntos a curar deben ser positivos");
        }
        this.puntos = puntos;
    }

    /**
     * Cura al Pokemon objetivo (el del argumento o el activo).
     *
     * @param juego partida actual
     * @param args puede traer la CartaEnJuego objetivo en la posicion 0
     * @return la partida con el Pokemon curado
     */
    @Override
    public Juego_BorisTroncoso_195598568 ejecutar(Juego_BorisTroncoso_195598568 juego,
                                                  List<Object> args) {
        CartaEnJuego_BorisTroncoso_195598568 objetivo = null;
        if (args != null && !args.isEmpty()
                && args.get(0) instanceof CartaEnJuego_BorisTroncoso_195598568) {
            objetivo = (CartaEnJuego_BorisTroncoso_195598568) args.get(0);
        }
        if (objetivo == null) {
            objetivo = juego.getJugadorActual().getActivo();
        }
        if (objetivo == null) {
            juego.registrarMensaje("No hay Pokemon objetivo que curar: el efecto no se aplica.");
            return juego;
        }
        objetivo.curar(puntos);
        juego.registrarMensaje(objetivo.getCarta().getNombre() + " (instancia "
                + objetivo.getIdInstancia() + ") es curado en " + puntos
                + " (PS: " + objetivo.psRestantes() + "/" + objetivo.getCarta().getPs() + ").");
        return juego;
    }

    /**
     * Descripcion para el menu.
     *
     * @return texto del efecto
     */
    @Override
    public String getDescripcion() {
        return "Cura " + puntos + " puntos de dano a un Pokemon en juego";
    }
}
