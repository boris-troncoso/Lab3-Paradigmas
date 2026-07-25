import java.util.ArrayList;
import java.util.List;

/**
 * TDA Jugador: representa a un jugador de la partida (RF01).
 *
 * Representacion: nombre, mazo (las cartas que le van quedando durante la
 * partida), mano, banca (maximo 5 Pokemon), activo (puede ser null), premios
 * y pila de descarte.
 *
 * @author Boris Troncoso 195598568
 */
public class Jugador_BorisTroncoso_195598568 {

    /** maximo de Pokemon en banca segun las reglas oficiales (regla 8) */
    public static final int MAX_BANCA = 5;

    /** nombre del jugador */
    private final String nombre;

    /** cartas que le quedan en el mazo durante la partida, se roba desde el inicio */
    private final List<Carta_BorisTroncoso_195598568> mazo;

    /** cartas en la mano */
    private final List<Carta_BorisTroncoso_195598568> mano =
            new ArrayList<Carta_BorisTroncoso_195598568>();

    /** Pokemon en la banca */
    private final List<CartaEnJuego_BorisTroncoso_195598568> banca =
            new ArrayList<CartaEnJuego_BorisTroncoso_195598568>();

    /** Pokemon activo, null si no hay (antes del primer turno o despues de un Knock Out) */
    private CartaEnJuego_BorisTroncoso_195598568 activo = null;

    /** cartas de premio boca abajo */
    private final List<Carta_BorisTroncoso_195598568> premios =
            new ArrayList<Carta_BorisTroncoso_195598568>();

    /** pila de descarte */
    private final List<Carta_BorisTroncoso_195598568> descarte =
            new ArrayList<Carta_BorisTroncoso_195598568>();

    /**
     * Constructor del jugador.
     *
     * @param nombre nombre del jugador
     * @param cartasMazo cartas (ya barajadas) con las que va a jugar
     */
    public Jugador_BorisTroncoso_195598568(String nombre,
                                           List<Carta_BorisTroncoso_195598568> cartasMazo) {
        this.nombre = nombre;
        this.mazo = cartasMazo;
    }

    /**
     * Retorna el nombre del jugador.
     *
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Retorna el mazo del jugador durante la partida.
     *
     * @return lista de cartas que quedan en el mazo
     */
    public List<Carta_BorisTroncoso_195598568> getMazo() {
        return mazo;
    }

    /**
     * Retorna la mano del jugador.
     *
     * @return lista de cartas en la mano
     */
    public List<Carta_BorisTroncoso_195598568> getMano() {
        return mano;
    }

    /**
     * Retorna la banca del jugador.
     *
     * @return lista de Pokemon en banca
     */
    public List<CartaEnJuego_BorisTroncoso_195598568> getBanca() {
        return banca;
    }

    /**
     * Retorna el Pokemon activo.
     *
     * @return Pokemon activo o null si no hay
     */
    public CartaEnJuego_BorisTroncoso_195598568 getActivo() {
        return activo;
    }

    /**
     * Retorna las cartas de premio que quedan.
     *
     * @return lista de premios
     */
    public List<Carta_BorisTroncoso_195598568> getPremios() {
        return premios;
    }

    /**
     * Retorna la pila de descarte.
     *
     * @return lista de cartas descartadas
     */
    public List<Carta_BorisTroncoso_195598568> getDescarte() {
        return descarte;
    }

    /**
     * Agrega una carta a la pila de descarte (se usa en RF16).
     *
     * @param carta carta que se descarta
     */
    public void agregarADescarte(Carta_BorisTroncoso_195598568 carta) {
        descarte.add(carta);
    }

    /**
     * Roba la carta de arriba del mazo y la deja en la mano (RF13).
     *
     * @return la carta robada, o null si el mazo esta vacio
     */
    public Carta_BorisTroncoso_195598568 robarCarta() {
        if (mazo.isEmpty()) {
            return null;
        }
        Carta_BorisTroncoso_195598568 carta = mazo.remove(0);
        mano.add(carta);
        return carta;
    }

    /**
     * Roba varias cartas seguidas (se usa en RF09 para la mano inicial).
     *
     * @param cantidad cuantas cartas robar
     */
    public void robarMano(int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            robarCarta();
        }
    }

    /**
     * Revisa si hay algun Pokemon basico en la mano (para el mulligan de RF09).
     *
     * @return true si hay al menos un Pokemon basico
     */
    public boolean tienePokemonBasicoEnMano() {
        for (int i = 0; i < mano.size(); i++) {
            if (mano.get(i).esPokemonBasico()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Devuelve toda la mano al mazo (para rebarajar en el mulligan de RF09).
     */
    public void devolverManoAlMazo() {
        mazo.addAll(mano);
        mano.clear();
    }

    /**
     * Pasa las cartas de arriba del mazo a la zona de premios (RF09).
     *
     * @param cantidad cuantos premios colocar
     */
    public void colocarPremios(int cantidad) {
        for (int i = 0; i < cantidad && !mazo.isEmpty(); i++) {
            premios.add(mazo.remove(0));
        }
    }

    /**
     * Pone un Pokemon basico de la mano en la banca (RF11). Valida que la
     * carta este en la mano, que sea un basico y que la banca no este llena.
     *
     * @param carta carta Pokemon basica de la mano
     * @return la CartaEnJuego que se creo en la banca
     */
    public CartaEnJuego_BorisTroncoso_195598568 jugarABanca(
            CartaPokemon_BorisTroncoso_195598568 carta) {
        if (!mano.contains(carta)) {
            throw new IllegalStateException("La carta no esta en la mano de " + nombre);
        }
        if (!carta.esBasico()) {
            throw new IllegalStateException("Solo se pueden jugar Pokemon basicos a la banca");
        }
        if (banca.size() >= MAX_BANCA) {
            throw new IllegalStateException("La banca ya tiene " + MAX_BANCA + " Pokemon");
        }
        mano.remove(carta);
        CartaEnJuego_BorisTroncoso_195598568 enJuego =
                new CartaEnJuego_BorisTroncoso_195598568(carta);
        banca.add(enJuego);
        return enJuego;
    }

    /**
     * Cambia el Pokemon activo por uno de la banca pagando el costo de
     * retirada del activo actual, si es que habia (RF12, regla 9). Un
     * Pokemon dormido o paralizado no se puede retirar.
     *
     * @param enBanca Pokemon de la banca que pasa a ser el activo
     */
    public void cambiarActivo(CartaEnJuego_BorisTroncoso_195598568 enBanca) {
        if (!banca.contains(enBanca)) {
            throw new IllegalStateException("Ese Pokemon no esta en la banca de " + nombre);
        }
        if (activo != null) {
            String estado = activo.getEstado();
            if (CartaEnJuego_BorisTroncoso_195598568.ESTADO_DORMIDO.equals(estado)
                    || CartaEnJuego_BorisTroncoso_195598568.ESTADO_PARALIZADO.equals(estado)) {
                throw new IllegalStateException(
                        "El Pokemon activo no puede retirarse estando " + estado);
            }
            int costo = activo.getCarta().getCostoRetirada();
            if (activo.getEnergias().size() < costo) {
                throw new IllegalStateException(
                        "Energias insuficientes para pagar el costo de retirada (" + costo + ")");
            }
            descarte.addAll(activo.descartarEnergias(costo));
        }
        banca.remove(enBanca);
        if (activo != null) {
            banca.add(activo);
        }
        activo = enBanca;
    }

    /**
     * Cambia o promueve el activo sin pagar costo de retirada. Se usa al
     * promover despues de un Knock Out y en el efecto de cambiar activo.
     *
     * @param enBanca Pokemon de la banca que pasa a ser el activo
     */
    public void cambiarActivoSinCosto(CartaEnJuego_BorisTroncoso_195598568 enBanca) {
        if (!banca.contains(enBanca)) {
            throw new IllegalStateException("Ese Pokemon no esta en la banca de " + nombre);
        }
        banca.remove(enBanca);
        if (activo != null) {
            banca.add(activo);
        }
        activo = enBanca;
    }

    /**
     * Toma cartas de premio y las pasa a la mano (regla 11).
     *
     * @param cantidad cuantos premios tomar
     * @return cuantos se tomaron en realidad (pueden quedar menos)
     */
    public int tomarPremios(int cantidad) {
        int tomados = 0;
        while (tomados < cantidad && !premios.isEmpty()) {
            mano.add(premios.remove(0));
            tomados = tomados + 1;
        }
        return tomados;
    }

    /**
     * Manda un Pokemon vencido con todas sus cartas al descarte (regla 11).
     *
     * @param vencido Pokemon en juego que fue vencido
     */
    public void descartarPokemonVencido(CartaEnJuego_BorisTroncoso_195598568 vencido) {
        descarte.addAll(vencido.desmontar());
        if (vencido == activo) {
            activo = null;
        } else {
            banca.remove(vencido);
        }
    }

    /**
     * Indica si el jugador se quedo sin Pokemon en juego (pierde, regla 11).
     *
     * @return true si no tiene activo ni banca
     */
    public boolean sinPokemonEnJuego() {
        return activo == null && banca.isEmpty();
    }

    /**
     * Junta todos los Pokemon en juego del jugador (activo mas banca).
     *
     * @return lista con el activo (si hay) y la banca
     */
    public List<CartaEnJuego_BorisTroncoso_195598568> pokemonsEnJuego() {
        List<CartaEnJuego_BorisTroncoso_195598568> enJuego =
                new ArrayList<CartaEnJuego_BorisTroncoso_195598568>();
        if (activo != null) {
            enJuego.add(activo);
        }
        enJuego.addAll(banca);
        return enJuego;
    }

    /**
     * Suma un turno en juego a todos los Pokemon del jugador (se llama al
     * terminar su turno, sirve para la evolucion).
     */
    public void incrementarTurnosPokemon() {
        List<CartaEnJuego_BorisTroncoso_195598568> enJuego = pokemonsEnJuego();
        for (int i = 0; i < enJuego.size(); i++) {
            enJuego.get(i).incrementarTurnosEnJuego();
        }
    }
}
