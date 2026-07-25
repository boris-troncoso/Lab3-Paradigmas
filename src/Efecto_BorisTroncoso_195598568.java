import java.util.List;

/**
 * Interfaz Efecto (RF04 y RF06). Cada efecto concreto (dormir al rival,
 * robar cartas, curar, etc.) implementa esta interfaz. Los ataques, las
 * habilidades y las cartas de entrenador guardan una referencia a un Efecto
 * y lo ejecutan de forma polimorfica, sin hacer switch sobre el nombre de la
 * carta. Es el reemplazo del PredicadoAsociado del Laboratorio 2.
 *
 * @author Boris Troncoso 195598568
 */
public interface Efecto_BorisTroncoso_195598568 {

    /**
     * Ejecuta las acciones del efecto sobre la partida.
     *
     * @param juego partida sobre la que actua el efecto
     * @param args argumentos adicionales que pueda necesitar el efecto
     * @return la misma partida ya con el efecto aplicado
     */
    Juego_BorisTroncoso_195598568 ejecutar(Juego_BorisTroncoso_195598568 juego, List<Object> args);

    /**
     * Descripcion del efecto, se usa en el menu para elegirlo al crear
     * ataques y cartas de entrenador.
     *
     * @return texto que describe el efecto
     */
    String getDescripcion();
}
