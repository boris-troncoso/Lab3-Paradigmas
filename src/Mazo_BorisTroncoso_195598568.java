import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TDA Mazo: baraja de exactamente 60 cartas (RF07), con el barajado
 * reproducible del RF08 usando el generador pseudoaleatorio del enunciado.
 *
 * Restricciones que valida el constructor (regla 1): 60 cartas exactas,
 * maximo 4 cartas con el mismo nombre (las energias basicas no tienen
 * limite) y al menos un Pokemon basico. Si no se cumplen, se lanza una
 * excepcion y el mazo no se construye.
 *
 * Representacion: nombre (String) y cartas (lista ordenada de 60 cartas).
 *
 * @author Boris Troncoso 195598568
 */
public class Mazo_BorisTroncoso_195598568 {

    /** cantidad exacta de cartas de un mazo */
    public static final int TAMANO = 60;

    /** maximo de copias con el mismo nombre (menos las energias basicas) */
    public static final int MAX_COPIAS = 4;

    /** nombre del mazo para identificarlo en el menu */
    private final String nombre;

    /** cartas del mazo en su orden actual */
    private final List<Carta_BorisTroncoso_195598568> cartas;

    /**
     * Constructor del mazo. Valida las restricciones del RF07.
     *
     * @param nombre nombre del mazo
     * @param lista cartas que van a formar el mazo
     */
    public Mazo_BorisTroncoso_195598568(String nombre, List<Carta_BorisTroncoso_195598568> lista) {
        if (lista == null || lista.size() != TAMANO) {
            int recibidas = 0;
            if (lista != null) {
                recibidas = lista.size();
            }
            throw new IllegalArgumentException("El mazo debe tener exactamente " + TAMANO
                    + " cartas (recibidas: " + recibidas + ")");
        }
        Map<String, Integer> conteo = new HashMap<String, Integer>();
        boolean tieneBasico = false;
        for (int i = 0; i < lista.size(); i++) {
            Carta_BorisTroncoso_195598568 carta = lista.get(i);
            if (!carta.esEnergiaBasica()) {
                int copias = 1;
                if (conteo.containsKey(carta.getNombre())) {
                    copias = conteo.get(carta.getNombre()) + 1;
                }
                conteo.put(carta.getNombre(), copias);
                if (copias > MAX_COPIAS) {
                    throw new IllegalArgumentException("Maximo " + MAX_COPIAS
                            + " copias de '" + carta.getNombre()
                            + "' (las energias basicas no tienen limite)");
                }
            }
            if (carta.esPokemonBasico()) {
                tieneBasico = true;
            }
        }
        if (!tieneBasico) {
            throw new IllegalArgumentException("El mazo debe contener al menos un Pokemon basico");
        }
        this.nombre = nombre;
        this.cartas = new ArrayList<Carta_BorisTroncoso_195598568>(lista);
    }

    /**
     * Retorna el nombre del mazo.
     *
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Retorna las cartas del mazo en su orden actual.
     *
     * @return lista de cartas
     */
    public List<Carta_BorisTroncoso_195598568> getCartas() {
        return cartas;
    }

    /**
     * Entrega una copia de la lista de cartas, para que la partida pueda
     * robar cartas sin modificar el mazo original (RF09).
     *
     * @return copia de la lista de cartas
     */
    public List<Carta_BorisTroncoso_195598568> copiaCartas() {
        return new ArrayList<Carta_BorisTroncoso_195598568>(cartas);
    }

    /**
     * Generador pseudoaleatorio del laboratorio (equivalente en Java del
     * predicado randomPuro del Lab 2). No se usa Math.random() ni
     * java.util.Random para que el barajado sea reproducible con la semilla.
     *
     * @param xn valor actual de la secuencia (o la semilla al principio)
     * @return el siguiente numero de la secuencia
     */
    public static long siguiente(long xn) {
        long xn1 = (1103515245L * xn + 12345L) % 2147483648L;
        return xn1;
    }

    /**
     * Baraja una lista de cartas con el generador del laboratorio. Se
     * recorre la lista desde el final intercambiando cada carta con una
     * posicion al azar. Retorna el estado final del generador para poder
     * seguir usando la misma secuencia (RF09).
     *
     * @param lista lista de cartas que se revuelve
     * @param xn estado actual del generador (o semilla)
     * @return estado del generador despues de barajar
     */
    public static long barajarLista(List<Carta_BorisTroncoso_195598568> lista, long xn) {
        for (int i = lista.size() - 1; i > 0; i--) {
            xn = siguiente(xn);
            int j = (int) (xn % (i + 1));
            Carta_BorisTroncoso_195598568 temporal = lista.get(i);
            lista.set(i, lista.get(j));
            lista.set(j, temporal);
        }
        return xn;
    }

    /**
     * RF08: revuelve este mazo a partir de una semilla.
     *
     * @param semilla semilla del generador
     * @return estado final del generador (sirve para encadenar barajados)
     */
    public long barajar(long semilla) {
        return barajarLista(cartas, semilla);
    }

    /**
     * Arma un resumen del mazo para el menu, contando cuantas cartas hay
     * de cada familia.
     *
     * @return texto con el nombre y la composicion del mazo
     */
    public String resumen() {
        int pokemon = 0;
        int energia = 0;
        int entrenador = 0;
        for (int i = 0; i < cartas.size(); i++) {
            Carta_BorisTroncoso_195598568 carta = cartas.get(i);
            if (carta instanceof CartaPokemon_BorisTroncoso_195598568) {
                pokemon = pokemon + 1;
            } else if (carta instanceof CartaEnergia_BorisTroncoso_195598568) {
                energia = energia + 1;
            } else if (carta instanceof CartaEntrenador_BorisTroncoso_195598568) {
                entrenador = entrenador + 1;
            }
        }
        return "'" + nombre + "' (" + cartas.size() + " cartas: " + pokemon + " Pokemon, "
                + energia + " Energia, " + entrenador + " Entrenador)";
    }
}
