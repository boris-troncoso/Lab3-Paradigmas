import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TDA CartaEnJuego: representa un Pokemon que esta en juego (RF01).
 * No es lo mismo que la carta de mazo: aca se guardan ademas las energias
 * unidas, el dano acumulado, el estado especial, las cartas de evolucion
 * anteriores, los turnos en juego y un id de instancia unico que permite
 * diferenciar dos cartas iguales en juego (por ejemplo dos Voltorb). Ese id
 * es distinto del id autoincremental de las cartas de mazo.
 *
 * Representacion: idInstancia, carta (la CartaPokemon de arriba), energias,
 * evoluciones, dano, estado y turnosEnJuego.
 *
 * @author Boris Troncoso 195598568
 */
public class CartaEnJuego_BorisTroncoso_195598568 {

    /** estado normal, sin condicion especial */
    public static final String ESTADO_NINGUNO = "Ninguno";

    /** dormido: no ataca ni se retira, al final del turno del dueno lanza moneda para despertar */
    public static final String ESTADO_DORMIDO = "Dormido";

    /** paralizado: no ataca ni se retira, se cura al final del turno del dueno */
    public static final String ESTADO_PARALIZADO = "Paralizado";

    /** confundido: al atacar lanza moneda, si sale sello el ataque falla */
    public static final String ESTADO_CONFUNDIDO = "Confundido";

    /** envenenado: recibe 10 de dano al final del turno del dueno */
    public static final String ESTADO_ENVENENADO = "Envenenado";

    /** lista con los estados validos (regla 12) */
    private static final List<String> ESTADOS_VALIDOS = Arrays.asList(
            ESTADO_NINGUNO, ESTADO_DORMIDO, ESTADO_PARALIZADO, ESTADO_CONFUNDIDO, ESTADO_ENVENENADO);

    /** contador para los id de instancia */
    private static int contadorInstancias = 0;

    /** id unico de esta instancia en juego */
    private final int idInstancia;

    /** carta Pokemon actual (cambia cuando evoluciona) */
    private CartaPokemon_BorisTroncoso_195598568 carta;

    /** energias unidas a este Pokemon */
    private final List<CartaEnergia_BorisTroncoso_195598568> energias =
            new ArrayList<CartaEnergia_BorisTroncoso_195598568>();

    /** cartas de las etapas anteriores, quedan abajo al evolucionar */
    private final List<CartaPokemon_BorisTroncoso_195598568> evoluciones =
            new ArrayList<CartaPokemon_BorisTroncoso_195598568>();

    /** dano acumulado */
    private int dano = 0;

    /** estado especial actual */
    private String estado = ESTADO_NINGUNO;

    /** turnos completos que lleva en juego (sirve para poder evolucionar) */
    private int turnosEnJuego = 0;

    /**
     * Constructor: pone una carta Pokemon en juego.
     *
     * @param carta carta Pokemon que entra en juego
     */
    public CartaEnJuego_BorisTroncoso_195598568(CartaPokemon_BorisTroncoso_195598568 carta) {
        if (carta == null) {
            throw new IllegalArgumentException("La carta Pokemon no puede ser nula");
        }
        this.carta = carta;
        this.idInstancia = contadorInstancias;
        contadorInstancias = contadorInstancias + 1;
    }

    /**
     * Revisa si un estado es valido.
     *
     * @param valor estado a revisar
     * @return true si esta en la lista de estados validos
     */
    public static boolean esEstadoValido(String valor) {
        return ESTADOS_VALIDOS.contains(valor);
    }

    /**
     * Retorna el id de instancia.
     *
     * @return id unico de la instancia en juego
     */
    public int getIdInstancia() {
        return idInstancia;
    }

    /**
     * Retorna la carta Pokemon actual.
     *
     * @return carta de arriba de la pila
     */
    public CartaPokemon_BorisTroncoso_195598568 getCarta() {
        return carta;
    }

    /**
     * Retorna las energias unidas.
     *
     * @return lista de energias
     */
    public List<CartaEnergia_BorisTroncoso_195598568> getEnergias() {
        return energias;
    }

    /**
     * Retorna el estado especial actual.
     *
     * @return estado del Pokemon
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Cambia el estado especial del Pokemon.
     *
     * @param nuevoEstado nuevo estado (debe ser valido)
     */
    public void setEstado(String nuevoEstado) {
        if (!esEstadoValido(nuevoEstado)) {
            throw new IllegalArgumentException("Estado invalido: " + nuevoEstado);
        }
        this.estado = nuevoEstado;
    }

    /**
     * Une una carta de energia a este Pokemon (RF14).
     *
     * @param energia energia que se une
     */
    public void unirEnergia(CartaEnergia_BorisTroncoso_195598568 energia) {
        energias.add(energia);
    }

    /**
     * Evoluciona el Pokemon (RF15). La carta anterior queda guardada abajo,
     * se mantienen las energias y el dano, se limpia el estado y se reinicia
     * el contador de turnos.
     *
     * @param evolucion carta de la evolucion que se pone encima
     */
    public void evolucionarA(CartaPokemon_BorisTroncoso_195598568 evolucion) {
        evoluciones.add(carta);
        carta = evolucion;
        estado = ESTADO_NINGUNO;
        turnosEnJuego = 0;
    }

    /**
     * Indica si el Pokemon ya puede evolucionar (regla 6).
     *
     * @return true si lleva al menos 1 turno en juego
     */
    public boolean puedeEvolucionar() {
        return turnosEnJuego >= 1;
    }

    /**
     * Suma un turno en juego (se llama al terminar el turno del dueno).
     */
    public void incrementarTurnosEnJuego() {
        turnosEnJuego = turnosEnJuego + 1;
    }

    /**
     * Aplica dano al Pokemon.
     *
     * @param puntos dano que recibe
     */
    public void recibirDano(int puntos) {
        if (puntos > 0) {
            dano = dano + puntos;
        }
    }

    /**
     * Cura dano del Pokemon. El dano acumulado no baja de 0.
     *
     * @param puntos dano que se quita
     */
    public void curar(int puntos) {
        dano = dano - puntos;
        if (dano < 0) {
            dano = 0;
        }
    }

    /**
     * Calcula los PS que le quedan al Pokemon.
     *
     * @return PS de la carta menos el dano acumulado
     */
    public int psRestantes() {
        return carta.getPs() - dano;
    }

    /**
     * Indica si el Pokemon fue vencido (regla 11).
     *
     * @return true si los PS restantes son 0 o menos
     */
    public boolean estaVencido() {
        return psRestantes() <= 0;
    }

    /**
     * Revisa si las energias unidas alcanzan para pagar un costo (RF18).
     * Primero se revisan los tipos concretos y despues los costos Incolora,
     * que se pueden pagar con las energias que sobren de cualquier tipo.
     *
     * @param costo lista de pares [cantidad, tipoEnergia] del ataque
     * @return true si alcanzan las energias
     */
    public boolean tieneEnergiasPara(List<Ataque_BorisTroncoso_195598568.ParCosto> costo) {
        Map<String, Integer> disponibles = new HashMap<String, Integer>();
        for (int i = 0; i < energias.size(); i++) {
            String tipo = energias.get(i).getTipoEnergia();
            if (disponibles.containsKey(tipo)) {
                disponibles.put(tipo, disponibles.get(tipo) + 1);
            } else {
                disponibles.put(tipo, 1);
            }
        }
        int incolorasRequeridas = 0;
        for (int i = 0; i < costo.size(); i++) {
            Ataque_BorisTroncoso_195598568.ParCosto par = costo.get(i);
            if (CartaEnergia_BorisTroncoso_195598568.TIPO_INCOLORA.equals(par.getTipo())) {
                incolorasRequeridas = incolorasRequeridas + par.getCantidad();
            } else {
                int disponible = 0;
                if (disponibles.containsKey(par.getTipo())) {
                    disponible = disponibles.get(par.getTipo());
                }
                if (disponible < par.getCantidad()) {
                    return false;
                }
                disponibles.put(par.getTipo(), disponible - par.getCantidad());
            }
        }
        int restantes = 0;
        for (Integer cantidad : disponibles.values()) {
            restantes = restantes + cantidad;
        }
        return restantes >= incolorasRequeridas;
    }

    /**
     * Descarta energias unidas, se usa para pagar el costo de retirada (RF12).
     *
     * @param cantidad cuantas energias descartar
     * @return las energias que se sacaron, para mandarlas al descarte
     */
    public List<CartaEnergia_BorisTroncoso_195598568> descartarEnergias(int cantidad) {
        if (energias.size() < cantidad) {
            throw new IllegalStateException("No hay energias suficientes para descartar " + cantidad);
        }
        List<CartaEnergia_BorisTroncoso_195598568> removidas =
                new ArrayList<CartaEnergia_BorisTroncoso_195598568>();
        for (int i = 0; i < cantidad; i++) {
            removidas.add(energias.remove(energias.size() - 1));
        }
        return removidas;
    }

    /**
     * Junta todas las cartas de la pila cuando el Pokemon es vencido
     * (regla 11): evoluciones anteriores, carta actual y energias.
     *
     * @return todas las cartas asociadas, para la pila de descarte
     */
    public List<Carta_BorisTroncoso_195598568> desmontar() {
        List<Carta_BorisTroncoso_195598568> todas = new ArrayList<Carta_BorisTroncoso_195598568>();
        todas.addAll(evoluciones);
        todas.add(carta);
        todas.addAll(energias);
        evoluciones.clear();
        energias.clear();
        return todas;
    }

    /**
     * Arma la descripcion detallada del Pokemon en juego para RF10.
     *
     * @return texto con nombre, tipo, PS, etapa, energias, dano, debilidad,
     *         resistencia y estado
     */
    public String descripcion() {
        StringBuilder sb = new StringBuilder();
        sb.append(carta.getNombre());
        if (carta.esEX()) {
            sb.append(" EX");
        }
        sb.append(" (instancia ").append(idInstancia).append(")");
        sb.append(" | Tipo: ").append(carta.getTipo());
        sb.append(" | PS: ").append(psRestantes()).append('/').append(carta.getPs());
        if (carta.esBasico()) {
            sb.append(" | Basico");
        } else {
            sb.append(" | Evolucion");
        }
        sb.append(" | Estado: ").append(estado);
        sb.append("\n\t  Dano acumulado: ").append(dano);
        sb.append(" | Debilidad: ").append(carta.getDebilidad() == null ? "-" : carta.getDebilidad());
        sb.append(" | Resistencia: ").append(carta.getResistencia() == null ? "-" : carta.getResistencia());
        sb.append("\n\t  Energias: ");
        if (energias.isEmpty()) {
            sb.append("ninguna");
        } else {
            List<String> tiposVistos = new ArrayList<String>();
            for (int i = 0; i < energias.size(); i++) {
                String tipo = energias.get(i).getTipoEnergia();
                if (!tiposVistos.contains(tipo)) {
                    tiposVistos.add(tipo);
                }
            }
            for (int i = 0; i < tiposVistos.size(); i++) {
                int cuenta = 0;
                for (int j = 0; j < energias.size(); j++) {
                    if (energias.get(j).getTipoEnergia().equals(tiposVistos.get(i))) {
                        cuenta = cuenta + 1;
                    }
                }
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(cuenta).append('x').append(tiposVistos.get(i));
            }
        }
        return sb.toString();
    }
}
