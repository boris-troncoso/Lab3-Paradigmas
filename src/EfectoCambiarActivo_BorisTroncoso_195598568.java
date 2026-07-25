import java.util.List;

/**
 * Efecto que cambia el Pokemon activo del jugador actual por uno de su banca
 * sin pagar el costo de retirada (RF16, parecido a la carta Interruptor).
 * Necesita que en los argumentos venga la CartaEnJuego de la banca elegida.
 *
 * @author Boris Troncoso 195598568
 */
public class EfectoCambiarActivo_BorisTroncoso_195598568
        implements Efecto_BorisTroncoso_195598568 {

    /**
     * Constructor vacio, este efecto no necesita parametros.
     */
    public EfectoCambiarActivo_BorisTroncoso_195598568() {
    }

    /**
     * Hace el cambio de activo sin costo.
     *
     * @param juego partida actual
     * @param args debe traer en la posicion 0 el Pokemon de la banca
     * @return la partida con el activo cambiado
     */
    @Override
    public Juego_BorisTroncoso_195598568 ejecutar(Juego_BorisTroncoso_195598568 juego,
                                                  List<Object> args) {
        if (args == null || args.isEmpty()
                || !(args.get(0) instanceof CartaEnJuego_BorisTroncoso_195598568)) {
            throw new IllegalStateException(
                    "Este efecto requiere indicar un Pokemon de la banca como argumento");
        }
        CartaEnJuego_BorisTroncoso_195598568 elegido =
                (CartaEnJuego_BorisTroncoso_195598568) args.get(0);
        juego.getJugadorActual().cambiarActivoSinCosto(elegido);
        juego.registrarMensaje(elegido.getCarta().getNombre() + " (instancia "
                + elegido.getIdInstancia() + ") pasa a ser el Pokemon activo sin costo.");
        return juego;
    }

    /**
     * Descripcion para el menu.
     *
     * @return texto del efecto
     */
    @Override
    public String getDescripcion() {
        return "Cambia tu Pokemon activo por uno de tu banca (sin costo de retirada)";
    }
}
