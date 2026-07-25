import java.util.ArrayList;
import java.util.List;

/**
 * TDA CartaPokemon: carta de Pokemon (RF05).
 *
 * Representacion: hereda id, expansion, numero y nombre de Carta, y agrega
 * evolucionaDe (null si es basico), ps, tipo, debilidad (puede ser null),
 * resistencia (puede ser null), costoRetirada, esEX, habilidad (un Ataque
 * con costo vacio, puede ser null) y la lista de ataques (maximo 3, o 2 si
 * tiene habilidad).
 *
 * @author Boris Troncoso 195598568
 */
public class CartaPokemon_BorisTroncoso_195598568 extends Carta_BorisTroncoso_195598568 {

    /** nombre del Pokemon del que evoluciona, null si es basico */
    private final String evolucionaDe;

    /** puntos de salud, siempre mayores que 0 */
    private final int ps;

    /** tipo del Pokemon */
    private final String tipo;

    /** tipo al que es debil (dano x2), null si no tiene */
    private final String debilidad;

    /** tipo al que es resistente (dano -30 con minimo 0), null si no tiene */
    private final String resistencia;

    /** costo de retirada, siempre en energias incoloras asi que basta la cantidad */
    private final int costoRetirada;

    /** true si es un Pokemon EX (al vencerlo el rival toma 2 premios) */
    private final boolean esEX;

    /** habilidad opcional, se modela con un Ataque de costo vacio */
    private final Ataque_BorisTroncoso_195598568 habilidad;

    /** ataques del Pokemon */
    private final List<Ataque_BorisTroncoso_195598568> ataques;

    /**
     * Constructor de la carta Pokemon. Valida las reglas del RF05.
     *
     * @param expansion expansion de la carta
     * @param numero numero dentro de la expansion
     * @param nombre nombre del Pokemon
     * @param evolucionaDe nombre del que evoluciona, null si es basico
     * @param ps puntos de salud (mayores que 0)
     * @param tipo tipo del Pokemon
     * @param debilidad tipo de la debilidad o null
     * @param resistencia tipo de la resistencia o null
     * @param costoRetirada cantidad de energias para retirarse (0 si no tiene)
     * @param esEX true si es un Pokemon EX
     * @param habilidad habilidad opcional o null
     * @param ataques lista de ataques (maximo 3, o 2 con habilidad)
     */
    public CartaPokemon_BorisTroncoso_195598568(String expansion, int numero, String nombre,
                                                String evolucionaDe, int ps, String tipo,
                                                String debilidad, String resistencia,
                                                int costoRetirada, boolean esEX,
                                                Ataque_BorisTroncoso_195598568 habilidad,
                                                List<Ataque_BorisTroncoso_195598568> ataques) {
        super(expansion, numero, nombre);
        if (ps <= 0) {
            throw new IllegalArgumentException("Los PS no pueden ser 0 ni negativos");
        }
        validarTipo(tipo, false);
        validarTipo(debilidad, true);
        validarTipo(resistencia, true);
        if (costoRetirada < 0) {
            throw new IllegalArgumentException("El costo de retirada no puede ser negativo");
        }
        List<Ataque_BorisTroncoso_195598568> lista;
        if (ataques == null) {
            lista = new ArrayList<Ataque_BorisTroncoso_195598568>();
        } else {
            lista = ataques;
        }
        int maximo = 3;
        if (habilidad != null) {
            maximo = 2;
        }
        if (lista.size() > maximo) {
            throw new IllegalArgumentException("El Pokemon puede tener maximo " + maximo
                    + " ataques" + (habilidad != null ? " porque tiene habilidad" : ""));
        }
        if (habilidad != null && !habilidad.sinCosto()) {
            throw new IllegalArgumentException(
                    "La habilidad debe tener costo vacio (sin energias)");
        }
        this.evolucionaDe = evolucionaDe;
        this.ps = ps;
        this.tipo = tipo;
        this.debilidad = debilidad;
        this.resistencia = resistencia;
        this.costoRetirada = costoRetirada;
        this.esEX = esEX;
        this.habilidad = habilidad;
        this.ataques = lista;
    }

    /**
     * Valida que un tipo sea un tipo de energia basico o Incolora.
     *
     * @param valor tipo a validar
     * @param puedeSerNulo true si se acepta null (debilidad y resistencia)
     */
    private static void validarTipo(String valor, boolean puedeSerNulo) {
        if (valor == null) {
            if (!puedeSerNulo) {
                throw new IllegalArgumentException("El tipo del Pokemon es obligatorio");
            }
            return;
        }
        if (!CartaEnergia_BorisTroncoso_195598568.TIPOS_BASICOS.contains(valor)
                && !CartaEnergia_BorisTroncoso_195598568.TIPO_INCOLORA.equals(valor)) {
            throw new IllegalArgumentException("Tipo invalido: " + valor
                    + ". Validos: " + CartaEnergia_BorisTroncoso_195598568.TIPOS_BASICOS
                    + " o " + CartaEnergia_BorisTroncoso_195598568.TIPO_INCOLORA);
        }
    }

    /**
     * Retorna de quien evoluciona este Pokemon.
     *
     * @return nombre del pre evolucionado o null si es basico
     */
    public String getEvolucionaDe() {
        return evolucionaDe;
    }

    /**
     * Retorna los puntos de salud.
     *
     * @return PS de la carta
     */
    public int getPs() {
        return ps;
    }

    /**
     * Retorna el tipo del Pokemon.
     *
     * @return tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Retorna la debilidad.
     *
     * @return tipo de la debilidad o null
     */
    public String getDebilidad() {
        return debilidad;
    }

    /**
     * Retorna la resistencia.
     *
     * @return tipo de la resistencia o null
     */
    public String getResistencia() {
        return resistencia;
    }

    /**
     * Retorna el costo de retirada.
     *
     * @return cantidad de energias que hay que descartar para retirarse
     */
    public int getCostoRetirada() {
        return costoRetirada;
    }

    /**
     * Indica si el Pokemon es EX (regla 7).
     *
     * @return true si es EX
     */
    public boolean esEX() {
        return esEX;
    }

    /**
     * Retorna la habilidad del Pokemon.
     *
     * @return habilidad o null si no tiene
     */
    public Ataque_BorisTroncoso_195598568 getHabilidad() {
        return habilidad;
    }

    /**
     * Retorna los ataques del Pokemon.
     *
     * @return lista de ataques
     */
    public List<Ataque_BorisTroncoso_195598568> getAtaques() {
        return ataques;
    }

    /**
     * Indica si el Pokemon es basico (no evoluciona de nadie).
     *
     * @return true si evolucionaDe es null
     */
    public boolean esBasico() {
        return evolucionaDe == null;
    }

    /**
     * Busca un ataque por su nombre (se usa en RF18).
     *
     * @param nombreAtaque nombre del ataque que se busca
     * @return el ataque si lo tiene, o null si no
     */
    public Ataque_BorisTroncoso_195598568 buscarAtaque(String nombreAtaque) {
        for (int i = 0; i < ataques.size(); i++) {
            if (ataques.get(i).getNombre().equals(nombreAtaque)) {
                return ataques.get(i);
            }
        }
        return null;
    }

    /**
     * Redefinicion: es Pokemon basico si no tiene pre evolucion.
     *
     * @return true si es basico
     */
    @Override
    public boolean esPokemonBasico() {
        return esBasico();
    }

    /**
     * Descripcion de la carta para el menu.
     *
     * @return texto con todos los datos del Pokemon
     */
    @Override
    public String descripcion() {
        StringBuilder sb = new StringBuilder();
        sb.append("[id ").append(getId()).append("] ").append(getNombre());
        if (esEX) {
            sb.append(" EX");
        }
        sb.append(" (").append(getExpansion()).append(" #").append(getNumero()).append(") ");
        if (esBasico()) {
            sb.append("Basico");
        } else {
            sb.append("Evoluciona de ").append(evolucionaDe);
        }
        sb.append(" | Tipo: ").append(tipo).append(" | PS: ").append(ps);
        sb.append(" | Debilidad: ").append(debilidad == null ? "-" : debilidad);
        sb.append(" | Resistencia: ").append(resistencia == null ? "-" : resistencia);
        sb.append(" | Retirada: ").append(costoRetirada);
        if (habilidad != null) {
            sb.append("\n\tHabilidad: ").append(habilidad.resumen());
        }
        for (int i = 0; i < ataques.size(); i++) {
            sb.append("\n\tAtaque: ").append(ataques.get(i).resumen());
        }
        return sb.toString();
    }
}
