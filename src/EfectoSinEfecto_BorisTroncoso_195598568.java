import java.util.List;

/**
 * Efecto que no hace nada extra. Se usa para los ataques y cartas que no
 * tienen acciones adicionales (RF04).
 *
 * @author Boris Troncoso 195598568
 */
public class EfectoSinEfecto_BorisTroncoso_195598568
        implements Efecto_BorisTroncoso_195598568 {

    /**
     * Constructor vacio, este efecto no necesita parametros.
     */
    public EfectoSinEfecto_BorisTroncoso_195598568() {
    }

    /**
     * No hace nada, retorna la partida tal cual.
     *
     * @param juego partida actual
     * @param args no se usan
     * @return la misma partida
     */
    @Override
    public Juego_BorisTroncoso_195598568 ejecutar(Juego_BorisTroncoso_195598568 juego,
                                                  List<Object> args) {
        return juego;
    }

    /**
     * Descripcion para el menu.
     *
     * @return texto del efecto
     */
    @Override
    public String getDescripcion() {
        return "Sin efecto adicional";
    }
}
