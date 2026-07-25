import java.util.Arrays;
import java.util.List;

/**
 * TDA CartaEnergia: carta de energia basica (RF03). El nombre de la carta
 * indica el tipo de energia, segun el listado oficial de Wikidex.
 * La energia Incolora no es una carta, solo es un costo que se puede pagar
 * con cualquier tipo de energia.
 *
 * Representacion: hereda id, expansion, numero y nombre de Carta.
 *
 * @author Boris Troncoso 195598568
 */
public class CartaEnergia_BorisTroncoso_195598568 extends Carta_BorisTroncoso_195598568 {

    /** tipos de energia basica oficiales (sin acentos para la consola) */
    public static final List<String> TIPOS_BASICOS = Arrays.asList(
            "Planta", "Fuego", "Agua", "Rayo", "Psiquica", "Lucha", "Oscura", "Metalica");

    /** tipo Incolora, solo se usa como costo de ataques */
    public static final String TIPO_INCOLORA = "Incolora";

    /**
     * Constructor de la carta de energia. Valida que el nombre sea un tipo
     * de energia basica valido.
     *
     * @param expansion expansion de la carta
     * @param numero numero dentro de la expansion
     * @param nombre tipo de energia basica (debe estar en TIPOS_BASICOS)
     */
    public CartaEnergia_BorisTroncoso_195598568(String expansion, int numero, String nombre) {
        super(expansion, numero, nombre);
        if (!TIPOS_BASICOS.contains(nombre)) {
            throw new IllegalArgumentException(
                    "Tipo de energia basica invalido: " + nombre + ". Validos: " + TIPOS_BASICOS);
        }
    }

    /**
     * Retorna el tipo de energia que entrega esta carta.
     *
     * @return tipo de energia (es el mismo nombre de la carta)
     */
    public String getTipoEnergia() {
        return getNombre();
    }

    /**
     * Redefinicion: una carta de energia siempre es energia basica.
     *
     * @return true
     */
    @Override
    public boolean esEnergiaBasica() {
        return true;
    }

    /**
     * Descripcion de la carta para el menu.
     *
     * @return texto con los datos de la energia
     */
    @Override
    public String descripcion() {
        return "[id " + getId() + "] Energia " + getNombre()
                + " (" + getExpansion() + " #" + getNumero() + ")";
    }
}
