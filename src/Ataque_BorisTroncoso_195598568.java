import java.util.ArrayList;
import java.util.List;

/**
 * TDA Ataque: representa un ataque de un Pokemon (RF04). Tambien se reutiliza
 * para las habil
 * idades (RF17), en ese caso el costo debe ser una lista vacia.
 * El efecto asociado se guarda como un objeto Efecto y se invoca de forma
 * polimorfica, asi distintos ataques pueden tener distintos efectos sin
 * llenar esta clase de condicionales.
 *
 * Representacion: costo (lista de pares [cantidad, tipoEnergia]), nombre,
 * descripcion, dano y efecto asociado.
 *
 * @author Boris Troncoso 195598568
 */
public class Ataque_BorisTroncoso_195598568 {

    /**
     * Clase interna que representa un par [cantidad, tipoEnergia] del costo
     * de un ataque (regla 3 del enunciado).
     */
    public static class ParCosto {

        /** cantidad de energias que se piden de este tipo */
        private final int cantidad;

        /** tipo de energia que se pide (basico o Incolora) */
        private final String tipo;

        /**
         * Constructor del par de costo.
         *
         * @param cantidad cantidad de energias (mayor que 0)
         * @param tipo tipo de energia basica o Incolora
         */
        public ParCosto(int cantidad, String tipo) {
            if (cantidad <= 0) {
                throw new IllegalArgumentException("La cantidad del costo debe ser mayor que 0");
            }
            if (!CartaEnergia_BorisTroncoso_195598568.TIPOS_BASICOS.contains(tipo)
                    && !CartaEnergia_BorisTroncoso_195598568.TIPO_INCOLORA.equals(tipo)) {
                throw new IllegalArgumentException("Tipo de energia invalido en el costo: " + tipo);
            }
            this.cantidad = cantidad;
            this.tipo = tipo;
        }

        /**
         * Retorna la cantidad de energias del par.
         *
         * @return cantidad
         */
        public int getCantidad() {
            return cantidad;
        }

        /**
         * Retorna el tipo de energia del par.
         *
         * @return tipo de energia
         */
        public String getTipo() {
            return tipo;
        }
    }

    /** costo del ataque, si es una habilidad la lista queda vacia */
    private final List<ParCosto> costo;

    /** nombre del ataque */
    private final String nombre;

    /** descripcion del ataque */
    private final String descripcion;

    /** dano base del ataque (algunos efectos lo pueden cambiar, ver RF18) */
    private final int dano;

    /** efecto asociado al ataque */
    private final Efecto_BorisTroncoso_195598568 efecto;

    /**
     * Constructor del ataque.
     *
     * @param costo lista de pares [cantidad, tipoEnergia], null se toma como lista vacia
     * @param nombre nombre del ataque
     * @param descripcion descripcion del ataque
     * @param dano dano base (no puede ser negativo)
     * @param efecto efecto asociado (usar EfectoSinEfecto si no hace nada extra)
     */
    public Ataque_BorisTroncoso_195598568(List<ParCosto> costo, String nombre, String descripcion,
                                          int dano, Efecto_BorisTroncoso_195598568 efecto) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del ataque no puede estar vacio");
        }
        if (dano < 0) {
            throw new IllegalArgumentException("El dano del ataque no puede ser negativo");
        }
        if (efecto == null) {
            throw new IllegalArgumentException("El ataque debe tener un efecto asociado");
        }
        if (costo == null) {
            this.costo = new ArrayList<ParCosto>();
        } else {
            this.costo = costo;
        }
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.dano = dano;
        this.efecto = efecto;
    }

    /**
     * Retorna el costo del ataque.
     *
     * @return lista de pares [cantidad, tipoEnergia]
     */
    public List<ParCosto> getCosto() {
        return costo;
    }

    /**
     * Retorna el nombre del ataque.
     *
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Retorna el dano base del ataque.
     *
     * @return dano base
     */
    public int getDano() {
        return dano;
    }

    /**
     * Retorna el efecto asociado.
     *
     * @return efecto del ataque
     */
    public Efecto_BorisTroncoso_195598568 getEfecto() {
        return efecto;
    }

    /**
     * Indica si el ataque no tiene costo. Solo los ataques sin costo se
     * pueden usar como habilidad (RF05).
     *
     * @return true si el costo esta vacio
     */
    public boolean sinCosto() {
        return costo.isEmpty();
    }

    /**
     * Arma un resumen del ataque para mostrarlo en el menu.
     *
     * @return texto con nombre, costo, dano y descripcion
     */
    public String resumen() {
        String textoCosto = "";
        if (costo.isEmpty()) {
            textoCosto = "sin costo";
        } else {
            for (int i = 0; i < costo.size(); i++) {
                if (i > 0) {
                    textoCosto = textoCosto + ", ";
                }
                textoCosto = textoCosto + costo.get(i).getCantidad() + "x" + costo.get(i).getTipo();
            }
        }
        return nombre + " [" + textoCosto + "] dano " + dano + " - " + descripcion;
    }
}
