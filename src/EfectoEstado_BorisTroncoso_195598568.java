import java.util.List;

/**
 * Efecto que deja al Pokemon activo rival con un estado especial (dormido,
 * paralizado, confundido o envenenado). Puede ser directo o depender de una
 * moneda (RF04, regla 12).
 *
 * @author Boris Troncoso 195598568
 */
public class EfectoEstado_BorisTroncoso_195598568
        implements Efecto_BorisTroncoso_195598568 {

    /** estado que aplica el efecto */
    private final String estado;

    /** true si el efecto solo se aplica cuando la moneda sale cara */
    private final boolean conMoneda;

    /**
     * Constructor del efecto.
     *
     * @param estado estado especial a aplicar (constantes de CartaEnJuego)
     * @param conMoneda true para que dependa de una moneda
     */
    public EfectoEstado_BorisTroncoso_195598568(String estado, boolean conMoneda) {
        if (!CartaEnJuego_BorisTroncoso_195598568.esEstadoValido(estado)) {
            throw new IllegalArgumentException("Estado invalido: " + estado);
        }
        this.estado = estado;
        this.conMoneda = conMoneda;
    }

    /**
     * Aplica el estado al activo rival, si hay y si la moneda lo permite.
     *
     * @param juego partida actual
     * @param args no se usan
     * @return la partida con el efecto aplicado
     */
    @Override
    public Juego_BorisTroncoso_195598568 ejecutar(Juego_BorisTroncoso_195598568 juego,
                                                  List<Object> args) {
        CartaEnJuego_BorisTroncoso_195598568 objetivo = juego.getJugadorRival().getActivo();
        if (objetivo == null) {
            juego.registrarMensaje("No hay Pokemon activo rival: el efecto no se aplica.");
            return juego;
        }
        if (conMoneda) {
            boolean cara = juego.lanzarMoneda();
            if (cara) {
                juego.registrarMensaje("Moneda del efecto: cara.");
            } else {
                juego.registrarMensaje("Moneda del efecto: sello.");
                juego.registrarMensaje("El efecto no se aplica.");
                return juego;
            }
        }
        objetivo.setEstado(estado);
        juego.registrarMensaje(objetivo.getCarta().getNombre()
                + " rival queda " + estado + ".");
        return juego;
    }

    /**
     * Descripcion para el menu.
     *
     * @return texto del efecto
     */
    @Override
    public String getDescripcion() {
        if (conMoneda) {
            return "Lanza 1 moneda; si sale cara, deja al Pokemon activo rival " + estado;
        }
        return "Deja al Pokemon activo rival " + estado;
    }
}
