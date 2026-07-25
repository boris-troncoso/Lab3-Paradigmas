/**
 * TDA CartaEntrenador: carta de entrenador (RF06). Tiene un efecto asociado
 * que se ejecuta de forma polimorfica en RF16, igual que en los ataques.
 * Las cartas "partidario" se pueden usar una vez por turno y las "objeto"
 * las veces que se quiera (regla 13).
 *
 * Representacion: hereda id, expansion, numero y nombre de Carta, y agrega
 * el tipo ("partidario" u "objeto"), el texto y el efecto asociado.
 *
 * @author Boris Troncoso 195598568
 */
public class CartaEntrenador_BorisTroncoso_195598568 extends Carta_BorisTroncoso_195598568 {

    /** tipo de entrenador que solo se puede usar una vez por turno */
    public static final String TIPO_PARTIDARIO = "partidario";

    /** tipo de entrenador que se puede usar sin limite en el turno */
    public static final String TIPO_OBJETO = "objeto";

    /** tipo de la carta: partidario u objeto */
    private final String tipoEntrenador;

    /** texto que describe lo que hace la carta */
    private final String texto;

    /** efecto asociado a la carta */
    private final Efecto_BorisTroncoso_195598568 efecto;

    /**
     * Constructor de la carta de entrenador.
     *
     * @param expansion expansion de la carta
     * @param numero numero dentro de la expansion
     * @param nombre nombre de la carta
     * @param tipo "partidario" u "objeto"
     * @param texto descripcion de lo que hace
     * @param efecto efecto asociado (no puede ser null)
     */
    public CartaEntrenador_BorisTroncoso_195598568(String expansion, int numero, String nombre,
                                                   String tipo, String texto,
                                                   Efecto_BorisTroncoso_195598568 efecto) {
        super(expansion, numero, nombre);
        if (!TIPO_PARTIDARIO.equals(tipo) && !TIPO_OBJETO.equals(tipo)) {
            throw new IllegalArgumentException("El tipo de entrenador debe ser '"
                    + TIPO_PARTIDARIO + "' u '" + TIPO_OBJETO + "'");
        }
        if (efecto == null) {
            throw new IllegalArgumentException("La carta de entrenador debe tener un efecto");
        }
        this.tipoEntrenador = tipo;
        this.texto = texto;
        this.efecto = efecto;
    }

    /**
     * Retorna el tipo de entrenador.
     *
     * @return "partidario" u "objeto"
     */
    public String getTipoEntrenador() {
        return tipoEntrenador;
    }

    /**
     * Retorna el texto de la carta.
     *
     * @return descripcion de la carta
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Retorna el efecto asociado.
     *
     * @return efecto de la carta
     */
    public Efecto_BorisTroncoso_195598568 getEfecto() {
        return efecto;
    }

    /**
     * Indica si la carta es de tipo partidario (se usa en RF16 para el
     * limite de una por turno).
     *
     * @return true si es partidario
     */
    public boolean esPartidario() {
        return TIPO_PARTIDARIO.equals(tipoEntrenador);
    }

    /**
     * Descripcion de la carta para el menu.
     *
     * @return texto con los datos de la carta
     */
    @Override
    public String descripcion() {
        return "[id " + getId() + "] " + getNombre() + " (" + getExpansion() + " #" + getNumero()
                + ") Entrenador/" + tipoEntrenador + " - " + texto;
    }
}
