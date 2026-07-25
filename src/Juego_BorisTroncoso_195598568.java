import java.util.List;

/**
 * TDA Juego: representa una partida completa (RF01 y RF09 a RF18).
 * Guarda a los dos jugadores, de quien es el turno, el estado del generador
 * pseudoaleatorio (para las monedas y rebarajados) y si la partida termino.
 * Toda la logica de los RF de partida esta aca y en los otros TDA, no en el
 * menu ni en Main.
 *
 * Representacion: jugador1, jugador2, numeroJugadorActual (1 o 2),
 * estadoGenerador, terminada, ganador, banderas del turno (accion realizada,
 * energia jugada, partidario jugado), danoAtaqueActual y un registro de
 * mensajes para darle retroalimentacion al menu (RF02).
 *
 * @author Boris Troncoso 195598568
 */
public class Juego_BorisTroncoso_195598568 {

    /** jugador 1 */
    private final Jugador_BorisTroncoso_195598568 jugador1;

    /** jugador 2 */
    private final Jugador_BorisTroncoso_195598568 jugador2;

    /** numero del jugador que tiene el turno (1 o 2) */
    private int numeroJugadorActual;

    /** estado actual del generador pseudoaleatorio */
    private long estadoGenerador;

    /** true cuando la partida ya termino */
    private boolean terminada = false;

    /** ganador de la partida, null mientras siga en curso */
    private Jugador_BorisTroncoso_195598568 ganador = null;

    /** true si ya se hizo alguna accion en el turno (bloquea robar, RF13) */
    private boolean accionRealizadaEsteTurno = false;

    /** true si ya se unio una energia en el turno (regla 14) */
    private boolean energiaJugadaEsteTurno = false;

    /** true si ya se uso una carta partidario en el turno (regla 13) */
    private boolean partidarioJugadoEsteTurno = false;

    /** dano del ataque que se esta ejecutando, los efectos lo pueden cambiar */
    private int danoAtaqueActual = 0;

    /** mensajes de retroalimentacion que el menu va imprimiendo */
    private final StringBuilder mensajes = new StringBuilder();

    /**
     * Constructor (RF09): deja la partida lista a partir de los dos mazos y
     * una semilla. Baraja los dos mazos con el generador (encadenando el
     * estado), roba las manos de 7 cartas repitiendo si no hay Pokemon
     * basico (mulligan), coloca 6 premios por lado y lanza la moneda
     * inicial: par comienza el Jugador 1, impar el Jugador 2.
     *
     * @param mazo1 mazo del Jugador 1
     * @param mazo2 mazo del Jugador 2
     * @param semilla semilla del generador
     */
    public Juego_BorisTroncoso_195598568(Mazo_BorisTroncoso_195598568 mazo1,
                                         Mazo_BorisTroncoso_195598568 mazo2,
                                         long semilla) {
        long xn = mazo1.barajar(semilla);
        xn = mazo2.barajar(xn);
        this.jugador1 = new Jugador_BorisTroncoso_195598568("Jugador 1", mazo1.copiaCartas());
        this.jugador2 = new Jugador_BorisTroncoso_195598568("Jugador 2", mazo2.copiaCartas());
        xn = prepararMano(jugador1, xn);
        xn = prepararMano(jugador2, xn);
        jugador1.colocarPremios(6);
        jugador2.colocarPremios(6);
        this.estadoGenerador = xn;
        boolean salioPar = lanzarMoneda();
        if (salioPar) {
            this.numeroJugadorActual = 1;
        } else {
            this.numeroJugadorActual = 2;
        }
        registrarMensaje("La moneda decidio que comienza el "
                + getJugadorActual().getNombre() + ".");
    }

    /**
     * Roba la mano inicial de 7 cartas con el mulligan del RF09: si no hay
     * Pokemon basico se devuelve la mano, se rebaraja y se roba de nuevo.
     *
     * @param jugador jugador que roba su mano
     * @param xn estado actual del generador
     * @return estado del generador despues de los rebarajados
     */
    private long prepararMano(Jugador_BorisTroncoso_195598568 jugador, long xn) {
        jugador.robarMano(7);
        while (!jugador.tienePokemonBasicoEnMano()) {
            registrarMensaje(jugador.getNombre()
                    + " no tiene Pokemon basico en la mano: se rebaraja y roba de nuevo.");
            jugador.devolverManoAlMazo();
            xn = Mazo_BorisTroncoso_195598568.barajarLista(jugador.getMazo(), xn);
            jugador.robarMano(7);
        }
        return xn;
    }

    /**
     * Lanza una moneda usando el generador del laboratorio: se avanza el
     * estado y se mira la paridad (par = cara). Asi las monedas tambien son
     * reproducibles con la semilla.
     *
     * @return true si salio cara
     */
    public boolean lanzarMoneda() {
        estadoGenerador = Mazo_BorisTroncoso_195598568.siguiente(estadoGenerador);
        return estadoGenerador % 2 == 0;
    }

    /**
     * Guarda un mensaje de retroalimentacion para que el menu lo muestre.
     *
     * @param mensaje texto del mensaje
     */
    public void registrarMensaje(String mensaje) {
        mensajes.append(mensaje).append('\n');
    }

    /**
     * Entrega los mensajes acumulados y los borra.
     *
     * @return mensajes pendientes (puede ser vacio)
     */
    public String consumirMensajes() {
        String texto = mensajes.toString();
        mensajes.setLength(0);
        return texto;
    }

    /**
     * Retorna el jugador que tiene el turno.
     *
     * @return jugador actual
     */
    public Jugador_BorisTroncoso_195598568 getJugadorActual() {
        if (numeroJugadorActual == 1) {
            return jugador1;
        }
        return jugador2;
    }

    /**
     * Retorna el jugador que no tiene el turno.
     *
     * @return jugador rival
     */
    public Jugador_BorisTroncoso_195598568 getJugadorRival() {
        if (numeroJugadorActual == 1) {
            return jugador2;
        }
        return jugador1;
    }

    /**
     * Retorna el numero del jugador actual.
     *
     * @return 1 o 2
     */
    public int getNumeroJugadorActual() {
        return numeroJugadorActual;
    }

    /**
     * Indica si la partida ya termino.
     *
     * @return true si hay ganador
     */
    public boolean estaTerminada() {
        return terminada;
    }

    /**
     * Revisa que la partida siga en curso antes de cada accion.
     */
    private void verificarEnCurso() {
        if (terminada) {
            String nombreGanador = "-";
            if (ganador != null) {
                nombreGanador = ganador.getNombre();
            }
            throw new IllegalStateException("La partida ya termino. Ganador: " + nombreGanador);
        }
    }

    /**
     * Termina la partida dejando registrado el ganador.
     *
     * @param quien jugador que gano
     * @param motivo por que gano
     */
    private void finalizar(Jugador_BorisTroncoso_195598568 quien, String motivo) {
        terminada = true;
        ganador = quien;
        registrarMensaje("PARTIDA TERMINADA: gana " + quien.getNombre() + " (" + motivo + ").");
    }

    /**
     * RF11: pone un Pokemon basico de la mano del jugador actual en su banca.
     *
     * @param carta carta Pokemon basica de la mano
     */
    public void jugarABanca(CartaPokemon_BorisTroncoso_195598568 carta) {
        verificarEnCurso();
        CartaEnJuego_BorisTroncoso_195598568 enJuego = getJugadorActual().jugarABanca(carta);
        accionRealizadaEsteTurno = true;
        registrarMensaje(carta.getNombre() + " entra a la banca de "
                + getJugadorActual().getNombre() + " (instancia " + enJuego.getIdInstancia() + ").");
    }

    /**
     * RF12: pone un Pokemon de la banca como activo. Si ya habia un activo
     * se paga su costo de retirada. Cuando no habia activo (al principio o
     * despues de un Knock Out) la promocion no cuenta como accion del turno,
     * para que despues igual se pueda robar.
     *
     * @param enBanca Pokemon de la banca del jugador actual
     */
    public void cambiarPokemonActivo(CartaEnJuego_BorisTroncoso_195598568 enBanca) {
        verificarEnCurso();
        Jugador_BorisTroncoso_195598568 jugador = getJugadorActual();
        boolean habiaActivo = jugador.getActivo() != null;
        jugador.cambiarActivo(enBanca);
        if (habiaActivo) {
            accionRealizadaEsteTurno = true;
        }
        registrarMensaje(enBanca.getCarta().getNombre() + " (instancia "
                + enBanca.getIdInstancia() + ") es ahora el Pokemon activo de "
                + jugador.getNombre() + ".");
    }

    /**
     * RF13: roba una carta del mazo del jugador actual. Solo puede ser la
     * primera accion del turno. Si el mazo esta vacio, gana el oponente.
     *
     * @return la carta robada, o null si no quedaban cartas
     */
    public Carta_BorisTroncoso_195598568 robarCarta() {
        verificarEnCurso();
        if (accionRealizadaEsteTurno) {
            throw new IllegalStateException("Solo puede robar como primera accion del turno");
        }
        Jugador_BorisTroncoso_195598568 jugador = getJugadorActual();
        Carta_BorisTroncoso_195598568 carta = jugador.robarCarta();
        if (carta == null) {
            finalizar(getJugadorRival(), jugador.getNombre() + " se quedo sin cartas en el mazo");
            return null;
        }
        accionRealizadaEsteTurno = true;
        registrarMensaje(jugador.getNombre() + " roba: " + carta.descripcion());
        return carta;
    }

    /**
     * RF14: une una carta de energia de la mano a un Pokemon en juego del
     * jugador actual. Solo se puede una vez por turno (regla 14).
     *
     * @param objetivo Pokemon que recibe la energia
     * @param energia carta de energia de la mano
     */
    public void usarCartaEnergia(CartaEnJuego_BorisTroncoso_195598568 objetivo,
                                 CartaEnergia_BorisTroncoso_195598568 energia) {
        verificarEnCurso();
        if (energiaJugadaEsteTurno) {
            throw new IllegalStateException("Ya se unio una carta de energia en este turno");
        }
        Jugador_BorisTroncoso_195598568 jugador = getJugadorActual();
        if (!jugador.getMano().contains(energia)) {
            throw new IllegalStateException("La carta de energia no esta en la mano");
        }
        if (!jugador.pokemonsEnJuego().contains(objetivo)) {
            throw new IllegalStateException("El Pokemon objetivo no esta en juego");
        }
        jugador.getMano().remove(energia);
        objetivo.unirEnergia(energia);
        energiaJugadaEsteTurno = true;
        accionRealizadaEsteTurno = true;
        registrarMensaje("Energia " + energia.getTipoEnergia() + " unida a "
                + objetivo.getCarta().getNombre() + " (instancia "
                + objetivo.getIdInstancia() + ").");
    }

    /**
     * RF15: pone una carta evolucion de la mano sobre un Pokemon en juego.
     * La carta tiene que ser compatible (su evolucionaDe debe coincidir con
     * el nombre del Pokemon) y el Pokemon debe llevar al menos 1 turno en
     * juego (regla 6).
     *
     * @param objetivo Pokemon en juego que va a evolucionar
     * @param evolucion carta evolucion de la mano
     */
    public void evolucionarPokemon(CartaEnJuego_BorisTroncoso_195598568 objetivo,
                                   CartaPokemon_BorisTroncoso_195598568 evolucion) {
        verificarEnCurso();
        Jugador_BorisTroncoso_195598568 jugador = getJugadorActual();
        if (!jugador.getMano().contains(evolucion)) {
            throw new IllegalStateException("La carta evolucion no esta en la mano");
        }
        if (!jugador.pokemonsEnJuego().contains(objetivo)) {
            throw new IllegalStateException("El Pokemon a evolucionar no esta en juego");
        }
        if (evolucion.getEvolucionaDe() == null
                || !evolucion.getEvolucionaDe().equals(objetivo.getCarta().getNombre())) {
            throw new IllegalStateException(evolucion.getNombre() + " no evoluciona de "
                    + objetivo.getCarta().getNombre());
        }
        if (!objetivo.puedeEvolucionar()) {
            throw new IllegalStateException(
                    "El Pokemon debe llevar al menos 1 turno en juego para evolucionar");
        }
        jugador.getMano().remove(evolucion);
        objetivo.evolucionarA(evolucion);
        accionRealizadaEsteTurno = true;
        registrarMensaje(evolucion.getEvolucionaDe() + " evoluciona a " + evolucion.getNombre()
                + " (instancia " + objetivo.getIdInstancia() + ").");
    }

    /**
     * RF16: usa una carta de entrenador desde la mano. El efecto se ejecuta
     * de forma polimorfica y la carta queda en el descarte. Las partidario
     * solo una vez por turno, las objeto sin limite.
     *
     * @param carta carta de entrenador de la mano
     * @param args argumentos adicionales para el efecto
     */
    public void usarCartaEntrenador(CartaEntrenador_BorisTroncoso_195598568 carta,
                                    List<Object> args) {
        verificarEnCurso();
        Jugador_BorisTroncoso_195598568 jugador = getJugadorActual();
        if (!jugador.getMano().contains(carta)) {
            throw new IllegalStateException("La carta de entrenador no esta en la mano");
        }
        if (carta.esPartidario() && partidarioJugadoEsteTurno) {
            throw new IllegalStateException("Solo se puede usar un partidario por turno");
        }
        registrarMensaje(jugador.getNombre() + " usa " + carta.getNombre()
                + ": " + carta.getTexto());
        carta.getEfecto().ejecutar(this, args);
        jugador.getMano().remove(carta);
        jugador.agregarADescarte(carta);
        if (carta.esPartidario()) {
            partidarioJugadoEsteTurno = true;
        }
        accionRealizadaEsteTurno = true;
    }

    /**
     * RF17: usa la habilidad de un Pokemon en juego del jugador actual.
     * Las habilidades no tienen costo en energias y su efecto se ejecuta de
     * forma polimorfica igual que los ataques.
     *
     * @param pokemon Pokemon en juego que tiene la habilidad
     * @param args argumentos adicionales para el efecto
     */
    public void usarHabilidadPokemon(CartaEnJuego_BorisTroncoso_195598568 pokemon,
                                     List<Object> args) {
        verificarEnCurso();
        Jugador_BorisTroncoso_195598568 jugador = getJugadorActual();
        if (!jugador.pokemonsEnJuego().contains(pokemon)) {
            throw new IllegalStateException("Ese Pokemon no esta en juego");
        }
        Ataque_BorisTroncoso_195598568 habilidad = pokemon.getCarta().getHabilidad();
        if (habilidad == null) {
            throw new IllegalStateException(pokemon.getCarta().getNombre() + " no tiene habilidad");
        }
        registrarMensaje(pokemon.getCarta().getNombre() + " usa la habilidad "
                + habilidad.getNombre() + ".");
        habilidad.getEfecto().ejecutar(this, args);
        accionRealizadaEsteTurno = true;
    }

    /**
     * RF18: usa un ataque del Pokemon activo del jugador actual. Se validan
     * las energias (los costos Incolora se pagan con cualquier tipo), se
     * ejecuta el efecto de forma polimorfica, se aplica el dano con
     * debilidad y resistencia, se procesa el Knock Out si corresponde y se
     * pasa el turno. Si el nombre del ataque es null no se ataca y el turno
     * termina igual (regla 15).
     *
     * @param nombreAtaque nombre del ataque, o null para pasar el turno
     * @param args argumentos adicionales para el efecto
     */
    public void usarAtaquePokemon(String nombreAtaque, List<Object> args) {
        verificarEnCurso();
        if (nombreAtaque == null) {
            registrarMensaje(getJugadorActual().getNombre() + " decide no atacar.");
            pasarTurno();
            return;
        }
        Jugador_BorisTroncoso_195598568 atacante = getJugadorActual();
        Jugador_BorisTroncoso_195598568 rival = getJugadorRival();
        CartaEnJuego_BorisTroncoso_195598568 activo = atacante.getActivo();
        if (activo == null) {
            throw new IllegalStateException("No hay Pokemon activo para atacar");
        }
        String estado = activo.getEstado();
        if (CartaEnJuego_BorisTroncoso_195598568.ESTADO_DORMIDO.equals(estado)
                || CartaEnJuego_BorisTroncoso_195598568.ESTADO_PARALIZADO.equals(estado)) {
            throw new IllegalStateException("El Pokemon activo no puede atacar estando " + estado);
        }
        Ataque_BorisTroncoso_195598568 ataque = activo.getCarta().buscarAtaque(nombreAtaque);
        if (ataque == null) {
            throw new IllegalStateException("El Pokemon activo no tiene el ataque '"
                    + nombreAtaque + "'");
        }
        if (!activo.tieneEnergiasPara(ataque.getCosto())) {
            throw new IllegalStateException("Energias insuficientes para usar " + nombreAtaque);
        }
        if (CartaEnJuego_BorisTroncoso_195598568.ESTADO_CONFUNDIDO.equals(estado)) {
            boolean cara = lanzarMoneda();
            if (cara) {
                registrarMensaje("Confundido: la moneda salio cara.");
            } else {
                registrarMensaje("Confundido: la moneda salio sello.");
            }
            if (!cara) {
                registrarMensaje("El ataque falla por la confusion.");
                pasarTurno();
                return;
            }
        }
        danoAtaqueActual = ataque.getDano();
        ataque.getEfecto().ejecutar(this, args);
        CartaEnJuego_BorisTroncoso_195598568 objetivo = rival.getActivo();
        if (objetivo == null) {
            registrarMensaje("El rival no tiene Pokemon activo: el ataque no causa dano.");
        } else if (danoAtaqueActual > 0) {
            int total = calcularDano(danoAtaqueActual, activo.getCarta(), objetivo.getCarta());
            objetivo.recibirDano(total);
            int psQueQuedan = objetivo.psRestantes();
            if (psQueQuedan < 0) {
                psQueQuedan = 0;
            }
            registrarMensaje(activo.getCarta().getNombre() + " usa " + nombreAtaque
                    + " y causa " + total + " de dano a " + objetivo.getCarta().getNombre()
                    + " (PS restantes: " + psQueQuedan + ").");
            if (objetivo.estaVencido()) {
                procesarKnockOut(rival, objetivo, atacante);
            }
        }
        if (!terminada) {
            pasarTurno();
        }
    }

    /**
     * Aplica la debilidad (dano x2) y la resistencia (dano -30 con minimo 0)
     * al dano base (regla 10).
     *
     * @param base dano base del ataque
     * @param atacante carta del Pokemon que ataca
     * @param defensor carta del Pokemon que recibe
     * @return dano final
     */
    private int calcularDano(int base, CartaPokemon_BorisTroncoso_195598568 atacante,
                             CartaPokemon_BorisTroncoso_195598568 defensor) {
        int dano = base;
        if (defensor.getDebilidad() != null && defensor.getDebilidad().equals(atacante.getTipo())) {
            dano = dano * 2;
            registrarMensaje("Debilidad al tipo " + atacante.getTipo() + ": dano x2 (" + dano + ").");
        }
        if (defensor.getResistencia() != null
                && defensor.getResistencia().equals(atacante.getTipo())) {
            dano = dano - 30;
            if (dano < 0) {
                dano = 0;
            }
            registrarMensaje("Resistencia al tipo " + atacante.getTipo()
                    + ": dano -30 (" + dano + ").");
        }
        return dano;
    }

    /**
     * Procesa un Knock Out (regla 11): las cartas del vencido van al
     * descarte, el otro jugador toma premios (2 si era EX) y se revisan las
     * condiciones de victoria.
     *
     * @param propietario dueno del Pokemon vencido
     * @param vencido Pokemon que fue vencido
     * @param beneficiario jugador que toma los premios
     */
    private void procesarKnockOut(Jugador_BorisTroncoso_195598568 propietario,
                                  CartaEnJuego_BorisTroncoso_195598568 vencido,
                                  Jugador_BorisTroncoso_195598568 beneficiario) {
        boolean eraEX = vencido.getCarta().esEX();
        String extra = "";
        if (eraEX) {
            extra = " (era EX: 2 premios)";
        }
        registrarMensaje(vencido.getCarta().getNombre() + " (instancia "
                + vencido.getIdInstancia() + ") es vencido" + extra + ".");
        propietario.descartarPokemonVencido(vencido);
        int aTomar = 1;
        if (eraEX) {
            aTomar = 2;
        }
        int tomados = beneficiario.tomarPremios(aTomar);
        registrarMensaje(beneficiario.getNombre() + " toma " + tomados + " carta(s) de premio ("
                + beneficiario.getPremios().size() + " restantes).");
        if (beneficiario.getPremios().isEmpty()) {
            finalizar(beneficiario, "tomo todas sus cartas de premio");
            return;
        }
        if (propietario.sinPokemonEnJuego()) {
            finalizar(beneficiario, propietario.getNombre() + " se quedo sin Pokemon en juego");
        }
    }

    /**
     * Termina el turno del jugador actual (regla 15): aplica los estados de
     * fin de turno, suma los turnos en juego de sus Pokemon, limpia las
     * banderas y le pasa el turno al rival.
     */
    private void pasarTurno() {
        Jugador_BorisTroncoso_195598568 saliente = getJugadorActual();
        aplicarEstadosFinDeTurno(saliente);
        if (terminada) {
            return;
        }
        saliente.incrementarTurnosPokemon();
        accionRealizadaEsteTurno = false;
        energiaJugadaEsteTurno = false;
        partidarioJugadoEsteTurno = false;
        if (numeroJugadorActual == 1) {
            numeroJugadorActual = 2;
        } else {
            numeroJugadorActual = 1;
        }
        registrarMensaje("Turno de " + getJugadorActual().getNombre() + ".");
    }

    /**
     * Aplica la mecanica de los estados especiales al terminar el turno del
     * dueno (regla 12, la mecanica quedo a criterio del estudiante):
     * envenenado recibe 10 de dano, dormido lanza moneda para despertar y
     * paralizado se cura solo.
     *
     * @param jugador jugador cuyo turno esta terminando
     */
    private void aplicarEstadosFinDeTurno(Jugador_BorisTroncoso_195598568 jugador) {
        CartaEnJuego_BorisTroncoso_195598568 activo = jugador.getActivo();
        if (activo == null) {
            return;
        }
        String estado = activo.getEstado();
        if (CartaEnJuego_BorisTroncoso_195598568.ESTADO_ENVENENADO.equals(estado)) {
            activo.recibirDano(10);
            int psQueQuedan = activo.psRestantes();
            if (psQueQuedan < 0) {
                psQueQuedan = 0;
            }
            registrarMensaje(activo.getCarta().getNombre()
                    + " esta envenenado y recibe 10 de dano (PS restantes: " + psQueQuedan + ").");
            if (activo.estaVencido()) {
                procesarKnockOut(jugador, activo, otroJugador(jugador));
            }
        } else if (CartaEnJuego_BorisTroncoso_195598568.ESTADO_DORMIDO.equals(estado)) {
            boolean cara = lanzarMoneda();
            if (cara) {
                activo.setEstado(CartaEnJuego_BorisTroncoso_195598568.ESTADO_NINGUNO);
                registrarMensaje(activo.getCarta().getNombre() + " despierta (moneda: cara).");
            } else {
                registrarMensaje(activo.getCarta().getNombre() + " sigue dormido (moneda: sello).");
            }
        } else if (CartaEnJuego_BorisTroncoso_195598568.ESTADO_PARALIZADO.equals(estado)) {
            activo.setEstado(CartaEnJuego_BorisTroncoso_195598568.ESTADO_NINGUNO);
            registrarMensaje(activo.getCarta().getNombre() + " ya no esta paralizado.");
        }
    }

    /**
     * Retorna el jugador contrario al que se le pasa.
     *
     * @param jugador un jugador de la partida
     * @return el otro jugador
     */
    private Jugador_BorisTroncoso_195598568 otroJugador(Jugador_BorisTroncoso_195598568 jugador) {
        if (jugador == jugador1) {
            return jugador2;
        }
        return jugador1;
    }

    /**
     * Cambia el dano del ataque que se esta ejecutando. Lo usan los efectos
     * que calculan el dano con monedas (RF18).
     *
     * @param dano nuevo dano del ataque
     */
    public void setDanoAtaqueActual(int dano) {
        this.danoAtaqueActual = dano;
    }

    /**
     * Roba varias cartas para el jugador actual. Lo usan los efectos de
     * robar cartas (aca no aplica la restriccion de primera accion del RF13).
     *
     * @param cantidad cuantas cartas robar
     * @return cuantas se robaron en realidad
     */
    public int robarCartasJugadorActual(int cantidad) {
        int robadas = 0;
        for (int i = 0; i < cantidad; i++) {
            if (getJugadorActual().robarCarta() == null) {
                break;
            }
            robadas = robadas + 1;
        }
        return robadas;
    }

    /**
     * RF10: arma y retorna un unico gran string con el estado de la partida.
     * El menu es el que lo imprime (RF02).
     *
     * @param numeroJugador jugador del que se muestra la mano completa (1 o 2)
     * @return texto con todo el estado del juego
     */
    public String mostrarJuego(int numeroJugador) {
        StringBuilder sb = new StringBuilder();
        sb.append("==================== ESTADO DEL JUEGO ====================\n");
        sb.append("Turno actual: ").append(getJugadorActual().getNombre()).append('\n');
        if (terminada) {
            String nombreGanador = "-";
            if (ganador != null) {
                nombreGanador = ganador.getNombre();
            }
            sb.append("PARTIDA TERMINADA - Ganador: ").append(nombreGanador).append('\n');
        }
        agregarSeccionJugador(sb, jugador1, numeroJugador == 1);
        agregarSeccionJugador(sb, jugador2, numeroJugador == 2);
        sb.append("==========================================================");
        return sb.toString();
    }

    /**
     * Agrega al string de RF10 la seccion de un jugador: activo, banca,
     * mano (completa o solo la cantidad), premios, mazo y descarte.
     *
     * @param sb string que se esta armando
     * @param jugador jugador que se muestra
     * @param mostrarMano true para listar las cartas de la mano
     */
    private void agregarSeccionJugador(StringBuilder sb,
                                       Jugador_BorisTroncoso_195598568 jugador,
                                       boolean mostrarMano) {
        sb.append("---------------- ").append(jugador.getNombre()).append(" ----------------\n");
        sb.append("Pokemon activo:\n");
        if (jugador.getActivo() == null) {
            sb.append("\t(ninguno)\n");
        } else {
            sb.append('\t').append(jugador.getActivo().descripcion()).append('\n');
        }
        sb.append("Banca (").append(jugador.getBanca().size()).append('/')
                .append(Jugador_BorisTroncoso_195598568.MAX_BANCA).append("):\n");
        if (jugador.getBanca().isEmpty()) {
            sb.append("\t(vacia)\n");
        } else {
            for (int i = 0; i < jugador.getBanca().size(); i++) {
                sb.append('\t').append(jugador.getBanca().get(i).descripcion()).append('\n');
            }
        }
        if (mostrarMano) {
            sb.append("Mano (").append(jugador.getMano().size()).append(" cartas):\n");
            for (int i = 0; i < jugador.getMano().size(); i++) {
                sb.append('\t').append(jugador.getMano().get(i).descripcion()).append('\n');
            }
        } else {
            sb.append("Cartas en mano: ").append(jugador.getMano().size()).append('\n');
        }
        sb.append("Cartas de premio restantes: ").append(jugador.getPremios().size()).append('\n');
        sb.append("Cartas en el mazo: ").append(jugador.getMazo().size()).append('\n');
        sb.append("Pila de descarte (").append(jugador.getDescarte().size()).append("): ");
        if (jugador.getDescarte().isEmpty()) {
            sb.append("(vacia)");
        } else {
            for (int i = 0; i < jugador.getDescarte().size(); i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(jugador.getDescarte().get(i).getNombre());
            }
        }
        sb.append('\n');
    }
}
