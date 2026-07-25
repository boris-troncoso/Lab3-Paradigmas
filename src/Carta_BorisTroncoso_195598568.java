/**
 * TDA Carta: clase abstracta que representa una carta generica del juego (RF01).
 * De ella heredan las tres familias de cartas: CartaEnergia, CartaPokemon y
 * CartaEntrenador. Asi el comportamiento de cada familia se resuelve con
 * polimorfismo y no con un campo tipo y switch.
 *
 * Representacion: id (int autoincremental), expansion (String), numero (int),
 * nombre (String).
 *
 * @author Boris Troncoso 195598568
 */
public abstract class Carta_BorisTroncoso_195598568 {

    /** contador para ir generando los id autoincrementales (parte en 0) */
    private static int contadorId = 0;

    /** id unico de la carta, lo genera el sistema y no el usuario */
    private final int id;

    /** expansion a la que pertenece la carta */
    private final String expansion;

    /** numero de la carta dentro de la expansion */
    private final int numero;

    /** nombre de la carta */
    private final String nombre;

    /**
     * Constructor comun para todas las cartas.
     *
     * @param expansion expansion de la carta
     * @param numero numero dentro de la expansion
     * @param nombre nombre de la carta (no puede ser vacio)
     */
    protected Carta_BorisTroncoso_195598568(String expansion, int numero, String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la carta no puede estar vacio");
        }
        this.expansion = expansion;
        this.numero = numero;
        this.nombre = nombre;
        this.id = contadorId;
        contadorId = contadorId + 1;
    }

    /**
     * Retorna el id de la carta.
     *
     * @return id autoincremental
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna la expansion.
     *
     * @return expansion de la carta
     */
    public String getExpansion() {
        return expansion;
    }

    /**
     * Retorna el numero de la carta.
     *
     * @return numero dentro de la expansion
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Retorna el nombre de la carta.
     *
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Descripcion de la carta para mostrarla en el menu. Cada familia de
     * cartas la implementa a su manera (metodo polimorfico).
     *
     * @return texto con los datos de la carta
     */
    public abstract String descripcion();

    /**
     * Indica si la carta es un Pokemon basico. Por defecto retorna false,
     * la clase CartaPokemon lo redefine (se usa en RF07, RF09 y RF11).
     *
     * @return true si es un Pokemon basico
     */
    public boolean esPokemonBasico() {
        return false;
    }

    /**
     * Indica si la carta es una energia basica. Por defecto retorna false,
     * la clase CartaEnergia lo redefine (se usa en RF07 para el limite de copias).
     *
     * @return true si es una energia basica
     */
    public boolean esEnergiaBasica() {
        return false;
    }
}
