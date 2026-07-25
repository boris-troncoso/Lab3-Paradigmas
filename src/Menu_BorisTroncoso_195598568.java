import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Menu interactivo por terminal (RF02). Muestra las opciones, pide los datos
 * y llama a los metodos de los TDA. La logica del juego NO esta aca, esta en
 * las otras clases; el menu solo lee, invoca y muestra la retroalimentacion.
 *
 * Guarda los catalogos de cartas, ataques y mazos creados, la partida actual
 * y el recomendador IA.
 *
 * @author Boris Troncoso 195598568
 */
public class Menu_BorisTroncoso_195598568 {

    /** scanner para leer la entrada del usuario */
    private final Scanner scanner = new Scanner(System.in);

    /** cartas creadas hasta el momento (RF03, RF05 y RF06) */
    private final List<Carta_BorisTroncoso_195598568> cartas =
            new ArrayList<Carta_BorisTroncoso_195598568>();

    /** ataques creados hasta el momento (RF04) */
    private final List<Ataque_BorisTroncoso_195598568> ataques =
            new ArrayList<Ataque_BorisTroncoso_195598568>();

    /** mazos creados hasta el momento (RF07) */
    private final List<Mazo_BorisTroncoso_195598568> mazos =
            new ArrayList<Mazo_BorisTroncoso_195598568>();

    /** efectos disponibles para asociar a ataques y entrenadores */
    private final List<Efecto_BorisTroncoso_195598568> efectos =
            new ArrayList<Efecto_BorisTroncoso_195598568>();

    /** partida actual, null mientras no se inicie (RF09) */
    private Juego_BorisTroncoso_195598568 juego = null;

    /** recomendador con Gemini (RF opcional) */
    private final RecomendadorIA_BorisTroncoso_195598568 recomendador =
            new RecomendadorIA_BorisTroncoso_195598568();

    /**
     * Constructor: deja listo el catalogo de efectos que se pueden elegir.
     */
    public Menu_BorisTroncoso_195598568() {
        efectos.add(new EfectoSinEfecto_BorisTroncoso_195598568());
        efectos.add(new EfectoEstado_BorisTroncoso_195598568(
                CartaEnJuego_BorisTroncoso_195598568.ESTADO_DORMIDO, true));
        efectos.add(new EfectoEstado_BorisTroncoso_195598568(
                CartaEnJuego_BorisTroncoso_195598568.ESTADO_PARALIZADO, false));
        efectos.add(new EfectoEstado_BorisTroncoso_195598568(
                CartaEnJuego_BorisTroncoso_195598568.ESTADO_ENVENENADO, false));
        efectos.add(new EfectoEstado_BorisTroncoso_195598568(
                CartaEnJuego_BorisTroncoso_195598568.ESTADO_CONFUNDIDO, true));
        efectos.add(new EfectoDanoPorMoneda_BorisTroncoso_195598568(2, 50));
        efectos.add(new EfectoRobarCartas_BorisTroncoso_195598568(2));
        efectos.add(new EfectoCurar_BorisTroncoso_195598568(30));
        efectos.add(new EfectoCambiarActivo_BorisTroncoso_195598568());
    }

    /**
     * Ciclo principal del menu: muestra las opciones, lee la eleccion y
     * ejecuta la accion, avisando si resulto bien o si hubo un error.
     */
    public void iniciar() {
        System.out.println("######## POKEMON TCG - Paradigma OO (Java) ########");
        System.out.println("Bienvenido. Boris Troncoso - 195598568");
        boolean salir = false;
        while (!salir) {
            imprimirMenu();
            int opcion = leerEntero("Ingrese su opcion: ", 0, 19);
            try {
                switch (opcion) {
                    case 1: opcionCrearCartaEnergia(); break;
                    case 2: opcionCrearAtaque(); break;
                    case 3: opcionCrearCartaPokemon(); break;
                    case 4: opcionCrearCartaEntrenador(); break;
                    case 5: opcionCrearMazo(); break;
                    case 6: opcionBarajarMazo(); break;
                    case 7: opcionIniciarJuego(); break;
                    case 8: opcionCargarDemostracion(); break;
                    case 9: opcionMostrarJuego(); break;
                    case 10: opcionJugarABanca(); break;
                    case 11: opcionCambiarActivo(); break;
                    case 12: opcionRobarCarta(); break;
                    case 13: opcionUsarEnergia(); break;
                    case 14: opcionEvolucionar(); break;
                    case 15: opcionUsarEntrenador(); break;
                    case 16: opcionUsarHabilidad(); break;
                    case 17: opcionUsarAtaque(); break;
                    case 18: opcionObtenerRecomendaciones(); break;
                    case 19: opcionVerRecomendaciones(); break;
                    case 0:
                        salir = true;
                        System.out.println("Hasta pronto!");
                        break;
                    default:
                        break;
                }
            } catch (RuntimeException e) {
                System.out.println("[Error] " + e.getMessage());
            }
            imprimirMensajes();
            System.out.println();
        }
    }

    /**
     * Imprime las opciones del menu (basado en el ejemplo del RF02).
     */
    private void imprimirMenu() {
        System.out.println();
        System.out.println("--- Construccion / Setup ---");
        System.out.println(" 1. Crear carta de energia            (RF03)");
        System.out.println(" 2. Crear ataque                      (RF04)");
        System.out.println(" 3. Crear carta Pokemon               (RF05)");
        System.out.println(" 4. Crear carta de entrenador         (RF06)");
        System.out.println(" 5. Crear mazo (a partir de cartas)   (RF07)");
        System.out.println(" 6. Barajar un mazo (con semilla)     (RF08)");
        System.out.println(" 7. Iniciar juego (2 mazos + semilla) (RF09)");
        System.out.println(" 8. Cargar partida de demostracion    (conveniencia)");
        System.out.println("--- Durante la partida ---");
        System.out.println(" 9. Mostrar estado del juego          (RF10)");
        System.out.println("10. Jugar Pokemon a la banca          (RF11)");
        System.out.println("11. Cambiar Pokemon activo            (RF12)");
        System.out.println("12. Robar carta del mazo              (RF13)");
        System.out.println("13. Unir energia a un Pokemon         (RF14)");
        System.out.println("14. Evolucionar un Pokemon            (RF15)");
        System.out.println("15. Usar carta de entrenador          (RF16)");
        System.out.println("16. Usar habilidad de un Pokemon      (RF17)");
        System.out.println("17. Usar ataque del activo / pasar    (RF18)");
        System.out.println("--- Extras (opcional) ---");
        System.out.println("18. Obtener recomendaciones IA        (RF-Opcional)");
        System.out.println("19. Ver recomendaciones IA guardadas  (RF-Opcional)");
        System.out.println(" 0. Salir");
    }

    /**
     * Imprime los mensajes de retroalimentacion que dejo la partida.
     */
    private void imprimirMensajes() {
        if (juego == null) {
            return;
        }
        String texto = juego.consumirMensajes();
        if (!texto.isEmpty()) {
            String[] lineas = texto.split("\n");
            for (int i = 0; i < lineas.length; i++) {
                System.out.println("[Sistema] " + lineas[i]);
            }
        }
    }

    /**
     * Opcion 1 (RF03): crea una carta de energia basica.
     */
    private void opcionCrearCartaEnergia() {
        String expansion = leerTexto("Expansion: ");
        int numero = leerEntero("Numero: ", 0, Integer.MAX_VALUE);
        System.out.println("Tipos validos: " + CartaEnergia_BorisTroncoso_195598568.TIPOS_BASICOS);
        String nombre = leerTexto("Nombre (tipo de energia basica): ");
        CartaEnergia_BorisTroncoso_195598568 carta =
                new CartaEnergia_BorisTroncoso_195598568(expansion, numero, nombre);
        cartas.add(carta);
        System.out.println("[Sistema] Carta de energia creada: " + carta.descripcion());
    }

    /**
     * Opcion 2 (RF04): crea un ataque con su costo, dano y efecto.
     */
    private void opcionCrearAtaque() {
        List<Ataque_BorisTroncoso_195598568.ParCosto> costo =
                new ArrayList<Ataque_BorisTroncoso_195598568.ParCosto>();
        int pares = leerEntero("Cantidad de pares del costo (0 = sin costo/habilidad): ", 0, 10);
        for (int i = 0; i < pares; i++) {
            int cantidad = leerEntero("  Par " + (i + 1) + " - cantidad: ", 1, 10);
            System.out.println("  Tipos validos: "
                    + CartaEnergia_BorisTroncoso_195598568.TIPOS_BASICOS + " o "
                    + CartaEnergia_BorisTroncoso_195598568.TIPO_INCOLORA);
            String tipo = leerTexto("  Par " + (i + 1) + " - tipo de energia: ");
            costo.add(new Ataque_BorisTroncoso_195598568.ParCosto(cantidad, tipo));
        }
        String nombre = leerTexto("Nombre del ataque: ");
        String descripcion = leerTexto("Descripcion: ");
        int dano = leerEntero("Dano: ", 0, Integer.MAX_VALUE);
        Efecto_BorisTroncoso_195598568 efecto = elegirEfecto();
        Ataque_BorisTroncoso_195598568 ataque =
                new Ataque_BorisTroncoso_195598568(costo, nombre, descripcion, dano, efecto);
        ataques.add(ataque);
        System.out.println("[Sistema] Ataque creado: " + ataque.resumen());
    }

    /**
     * Opcion 3 (RF05): crea una carta Pokemon con sus ataques y habilidad.
     */
    private void opcionCrearCartaPokemon() {
        String expansion = leerTexto("Expansion: ");
        int numero = leerEntero("Numero: ", 0, Integer.MAX_VALUE);
        String nombre = leerTexto("Nombre del Pokemon: ");
        String evolucionaDe = leerTextoOpcional("Evoluciona de (vacio = basico): ");
        int ps = leerEntero("PS: ", 1, Integer.MAX_VALUE);
        System.out.println("Tipos validos: " + CartaEnergia_BorisTroncoso_195598568.TIPOS_BASICOS
                + " o " + CartaEnergia_BorisTroncoso_195598568.TIPO_INCOLORA);
        String tipo = leerTexto("Tipo: ");
        String debilidad = leerTextoOpcional("Debilidad (vacio = sin debilidad): ");
        String resistencia = leerTextoOpcional("Resistencia (vacio = sin resistencia): ");
        int costoRetirada = leerEntero("Costo de retirada (energias incoloras): ", 0, 10);
        boolean esEX = leerSiNo("Es Pokemon EX? (s/n): ");
        Ataque_BorisTroncoso_195598568 habilidad = null;
        if (leerSiNo("Tiene habilidad? (s/n): ")) {
            List<Ataque_BorisTroncoso_195598568> sinCosto =
                    new ArrayList<Ataque_BorisTroncoso_195598568>();
            for (int i = 0; i < ataques.size(); i++) {
                if (ataques.get(i).sinCosto()) {
                    sinCosto.add(ataques.get(i));
                }
            }
            if (sinCosto.isEmpty()) {
                throw new IllegalStateException("No hay ataques sin costo en el catalogo: "
                        + "cree uno con 0 pares de costo (las habilidades no llevan costo)");
            }
            List<String> opciones = new ArrayList<String>();
            for (int i = 0; i < sinCosto.size(); i++) {
                opciones.add(sinCosto.get(i).resumen());
            }
            habilidad = sinCosto.get(elegirIndice("Habilidades disponibles:", opciones));
        }
        int maximo = 3;
        if (habilidad != null) {
            maximo = 2;
        }
        int cuantos = leerEntero("Cantidad de ataques (0 a " + maximo + "): ", 0, maximo);
        List<Ataque_BorisTroncoso_195598568> elegidos =
                new ArrayList<Ataque_BorisTroncoso_195598568>();
        if (cuantos > 0 && ataques.isEmpty()) {
            throw new IllegalStateException("No hay ataques en el catalogo: cree uno con la opcion 2");
        }
        for (int i = 0; i < cuantos; i++) {
            List<String> opciones = new ArrayList<String>();
            for (int j = 0; j < ataques.size(); j++) {
                opciones.add(ataques.get(j).resumen());
            }
            elegidos.add(ataques.get(elegirIndice("Ataque " + (i + 1) + ":", opciones)));
        }
        CartaPokemon_BorisTroncoso_195598568 carta = new CartaPokemon_BorisTroncoso_195598568(
                expansion, numero, nombre, evolucionaDe, ps, tipo, debilidad, resistencia,
                costoRetirada, esEX, habilidad, elegidos);
        cartas.add(carta);
        System.out.println("[Sistema] Carta Pokemon creada: " + carta.descripcion());
    }

    /**
     * Opcion 4 (RF06): crea una carta de entrenador con su efecto.
     */
    private void opcionCrearCartaEntrenador() {
        String expansion = leerTexto("Expansion: ");
        int numero = leerEntero("Numero: ", 0, Integer.MAX_VALUE);
        String nombre = leerTexto("Nombre: ");
        int tipoOpcion = leerEntero("Tipo (1 = partidario, 2 = objeto): ", 1, 2);
        String tipo;
        if (tipoOpcion == 1) {
            tipo = CartaEntrenador_BorisTroncoso_195598568.TIPO_PARTIDARIO;
        } else {
            tipo = CartaEntrenador_BorisTroncoso_195598568.TIPO_OBJETO;
        }
        String texto = leerTexto("Texto (descripcion): ");
        Efecto_BorisTroncoso_195598568 efecto = elegirEfecto();
        CartaEntrenador_BorisTroncoso_195598568 carta =
                new CartaEntrenador_BorisTroncoso_195598568(expansion, numero, nombre, tipo,
                        texto, efecto);
        cartas.add(carta);
        System.out.println("[Sistema] Carta de entrenador creada: " + carta.descripcion());
    }

    /**
     * Opcion 5 (RF07): arma un mazo de 60 cartas a partir del catalogo.
     */
    private void opcionCrearMazo() {
        if (cartas.isEmpty()) {
            throw new IllegalStateException("Primero cree cartas con las opciones 1 a 4");
        }
        String nombre = leerTexto("Nombre del mazo: ");
        System.out.println("Catalogo de cartas:");
        for (int i = 0; i < cartas.size(); i++) {
            System.out.println("  " + cartas.get(i).descripcion());
        }
        List<Carta_BorisTroncoso_195598568> lista = new ArrayList<Carta_BorisTroncoso_195598568>();
        while (lista.size() < Mazo_BorisTroncoso_195598568.TAMANO) {
            System.out.println("Cartas agregadas: " + lista.size() + "/"
                    + Mazo_BorisTroncoso_195598568.TAMANO);
            String linea = leerTexto("Ingrese 'idCarta,cantidad' (o 'fin' para intentar construir): ");
            if (linea.equalsIgnoreCase("fin")) {
                break;
            }
            String[] partes = linea.split(",");
            try {
                int id = Integer.parseInt(partes[0].trim());
                int cantidad = 1;
                if (partes.length > 1) {
                    cantidad = Integer.parseInt(partes[1].trim());
                }
                Carta_BorisTroncoso_195598568 carta = buscarCartaPorId(id);
                if (carta == null) {
                    System.out.println("[Error] No existe una carta con id " + id);
                } else if (cantidad <= 0
                        || lista.size() + cantidad > Mazo_BorisTroncoso_195598568.TAMANO) {
                    System.out.println("[Error] Cantidad invalida (excederia las "
                            + Mazo_BorisTroncoso_195598568.TAMANO + " cartas)");
                } else {
                    for (int i = 0; i < cantidad; i++) {
                        lista.add(carta);
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("[Error] Formato invalido. Ejemplo: 3,4");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("[Error] Formato invalido. Ejemplo: 3,4");
            }
        }
        Mazo_BorisTroncoso_195598568 mazo = new Mazo_BorisTroncoso_195598568(nombre, lista);
        mazos.add(mazo);
        System.out.println("[Sistema] Mazo creado: " + mazo.resumen());
    }

    /**
     * Opcion 6 (RF08): baraja un mazo con una semilla.
     */
    private void opcionBarajarMazo() {
        Mazo_BorisTroncoso_195598568 mazo = elegirMazo("Mazo a barajar:");
        long semilla = leerLong("Semilla: ");
        mazo.barajar(semilla);
        System.out.println("[Sistema] Mazo barajado con semilla " + semilla + ". Nuevo orden:");
        List<Carta_BorisTroncoso_195598568> orden = mazo.getCartas();
        for (int i = 0; i < orden.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + orden.get(i).getNombre()
                    + " [id " + orden.get(i).getId() + "]");
        }
    }

    /**
     * Opcion 7 (RF09): inicia una partida con dos mazos y una semilla.
     */
    private void opcionIniciarJuego() {
        if (mazos.size() < 2) {
            throw new IllegalStateException(
                    "Se requieren al menos 2 mazos (cree mazos con la opcion 5 o use la opcion 8)");
        }
        Mazo_BorisTroncoso_195598568 mazo1 = elegirMazo("Mazo del Jugador 1:");
        Mazo_BorisTroncoso_195598568 mazo2 = elegirMazo("Mazo del Jugador 2:");
        long semilla = leerLong("Semilla: ");
        System.out.println("[Sistema] Iniciando juego con los mazos " + mazo1.getNombre()
                + " y " + mazo2.getNombre() + ", semilla " + semilla + "...");
        juego = new Juego_BorisTroncoso_195598568(mazo1, mazo2, semilla);
        System.out.println("[Sistema] Juego iniciado correctamente.");
    }

    /**
     * Opcion 8 (conveniencia, no es un RF evaluado): crea cartas, ataques,
     * entrenadores y dos mazos de demostracion, y deja una partida iniciada.
     */
    private void opcionCargarDemostracion() {
        System.out.println("[Sistema] Creando cartas y mazos de demostracion...");
        CartaEnergia_BorisTroncoso_195598568 energiaRayo =
                new CartaEnergia_BorisTroncoso_195598568("Demo", 1, "Rayo");
        CartaEnergia_BorisTroncoso_195598568 energiaPsiquica =
                new CartaEnergia_BorisTroncoso_195598568("Demo", 2, "Psiquica");
        cartas.add(energiaRayo);
        cartas.add(energiaPsiquica);

        Ataque_BorisTroncoso_195598568 impactrueno = new Ataque_BorisTroncoso_195598568(
                Arrays.asList(new Ataque_BorisTroncoso_195598568.ParCosto(1, "Rayo")),
                "Impactrueno", "Descarga electrica basica", 20,
                new EfectoSinEfecto_BorisTroncoso_195598568());
        Ataque_BorisTroncoso_195598568 rayoParalizador = new Ataque_BorisTroncoso_195598568(
                Arrays.asList(new Ataque_BorisTroncoso_195598568.ParCosto(1, "Rayo"),
                        new Ataque_BorisTroncoso_195598568.ParCosto(1, "Incolora")),
                "Rayo Paralizador", "El Pokemon activo rival queda Paralizado", 20,
                new EfectoEstado_BorisTroncoso_195598568(
                        CartaEnJuego_BorisTroncoso_195598568.ESTADO_PARALIZADO, false));
        Ataque_BorisTroncoso_195598568 bolaVoltio = new Ataque_BorisTroncoso_195598568(
                Arrays.asList(new Ataque_BorisTroncoso_195598568.ParCosto(2, "Rayo")),
                "Bola Voltio", "Gran descarga electrica", 50,
                new EfectoSinEfecto_BorisTroncoso_195598568());
        Ataque_BorisTroncoso_195598568 cabezazo = new Ataque_BorisTroncoso_195598568(
                Arrays.asList(new Ataque_BorisTroncoso_195598568.ParCosto(1, "Incolora")),
                "Cabezazo", "Golpe simple", 10,
                new EfectoSinEfecto_BorisTroncoso_195598568());
        Ataque_BorisTroncoso_195598568 pesadilla = new Ataque_BorisTroncoso_195598568(
                Arrays.asList(new Ataque_BorisTroncoso_195598568.ParCosto(1, "Psiquica"),
                        new Ataque_BorisTroncoso_195598568.ParCosto(1, "Incolora")),
                "Pesadilla", "Lanza 1 moneda; si sale cara, el rival queda Dormido", 20,
                new EfectoEstado_BorisTroncoso_195598568(
                        CartaEnJuego_BorisTroncoso_195598568.ESTADO_DORMIDO, true));
        Ataque_BorisTroncoso_195598568 psicorrayo = new Ataque_BorisTroncoso_195598568(
                Arrays.asList(new Ataque_BorisTroncoso_195598568.ParCosto(2, "Psiquica")),
                "Psicorrayo", "Lanza 1 moneda; si sale cara, el rival queda Confundido", 30,
                new EfectoEstado_BorisTroncoso_195598568(
                        CartaEnJuego_BorisTroncoso_195598568.ESTADO_CONFUNDIDO, true));
        Ataque_BorisTroncoso_195598568 multiataque = new Ataque_BorisTroncoso_195598568(
                Arrays.asList(new Ataque_BorisTroncoso_195598568.ParCosto(2, "Psiquica"),
                        new Ataque_BorisTroncoso_195598568.ParCosto(1, "Incolora")),
                "Multiataque", "Lanza 2 monedas; dano = caras x 50", 0,
                new EfectoDanoPorMoneda_BorisTroncoso_195598568(2, 50));
        Ataque_BorisTroncoso_195598568 clarividencia = new Ataque_BorisTroncoso_195598568(
                null, "Clarividencia", "Habilidad: roba 1 carta de tu mazo", 0,
                new EfectoRobarCartas_BorisTroncoso_195598568(1));
        ataques.add(impactrueno);
        ataques.add(rayoParalizador);
        ataques.add(bolaVoltio);
        ataques.add(cabezazo);
        ataques.add(pesadilla);
        ataques.add(psicorrayo);
        ataques.add(multiataque);
        ataques.add(clarividencia);

        CartaPokemon_BorisTroncoso_195598568 pikachu = new CartaPokemon_BorisTroncoso_195598568(
                "Demo", 10, "Pikachu", null, 60, "Rayo", "Lucha", null, 1, false, null,
                Arrays.asList(impactrueno, rayoParalizador));
        CartaPokemon_BorisTroncoso_195598568 raichu = new CartaPokemon_BorisTroncoso_195598568(
                "Demo", 11, "Raichu", "Pikachu", 90, "Rayo", "Lucha", null, 1, false, null,
                Arrays.asList(bolaVoltio));
        CartaPokemon_BorisTroncoso_195598568 voltorb = new CartaPokemon_BorisTroncoso_195598568(
                "Demo", 12, "Voltorb", null, 40, "Rayo", "Lucha", null, 1, false, null,
                Arrays.asList(cabezazo));
        CartaPokemon_BorisTroncoso_195598568 electrode = new CartaPokemon_BorisTroncoso_195598568(
                "Demo", 13, "Electrode", "Voltorb", 80, "Rayo", "Lucha", null, 1, false, null,
                Arrays.asList(bolaVoltio));
        CartaPokemon_BorisTroncoso_195598568 abra = new CartaPokemon_BorisTroncoso_195598568(
                "Demo", 14, "Abra", null, 40, "Psiquica", "Psiquica", null, 1, false,
                clarividencia, Arrays.asList(pesadilla));
        CartaPokemon_BorisTroncoso_195598568 mewtwoEx = new CartaPokemon_BorisTroncoso_195598568(
                "Demo", 15, "Mewtwo", null, 170, "Psiquica", "Psiquica", "Lucha", 2, true, null,
                Arrays.asList(psicorrayo, multiataque));
        cartas.add(pikachu);
        cartas.add(raichu);
        cartas.add(voltorb);
        cartas.add(electrode);
        cartas.add(abra);
        cartas.add(mewtwoEx);

        CartaEntrenador_BorisTroncoso_195598568 pocion =
                new CartaEntrenador_BorisTroncoso_195598568("Demo", 20, "Pocion",
                        CartaEntrenador_BorisTroncoso_195598568.TIPO_OBJETO,
                        "Cura 30 puntos de dano a un Pokemon en juego",
                        new EfectoCurar_BorisTroncoso_195598568(30));
        CartaEntrenador_BorisTroncoso_195598568 investigacion =
                new CartaEntrenador_BorisTroncoso_195598568("Demo", 21, "Investigacion",
                        CartaEntrenador_BorisTroncoso_195598568.TIPO_PARTIDARIO,
                        "Roba 2 cartas de tu mazo",
                        new EfectoRobarCartas_BorisTroncoso_195598568(2));
        CartaEntrenador_BorisTroncoso_195598568 interruptor =
                new CartaEntrenador_BorisTroncoso_195598568("Demo", 22, "Interruptor",
                        CartaEntrenador_BorisTroncoso_195598568.TIPO_OBJETO,
                        "Cambia tu Pokemon activo por uno de tu banca sin costo",
                        new EfectoCambiarActivo_BorisTroncoso_195598568());
        cartas.add(pocion);
        cartas.add(investigacion);
        cartas.add(interruptor);

        // ambos mazos de demostracion llevan la misma composicion de 60 cartas
        List<Carta_BorisTroncoso_195598568> lista1 = new ArrayList<Carta_BorisTroncoso_195598568>();
        agregarCopias(lista1, pikachu, 4);
        agregarCopias(lista1, raichu, 3);
        agregarCopias(lista1, voltorb, 3);
        agregarCopias(lista1, electrode, 2);
        agregarCopias(lista1, abra, 3);
        agregarCopias(lista1, mewtwoEx, 2);
        agregarCopias(lista1, pocion, 4);
        agregarCopias(lista1, investigacion, 4);
        agregarCopias(lista1, interruptor, 3);
        agregarCopias(lista1, energiaRayo, 20);
        agregarCopias(lista1, energiaPsiquica, 12);
        List<Carta_BorisTroncoso_195598568> lista2 =
                new ArrayList<Carta_BorisTroncoso_195598568>(lista1);
        Mazo_BorisTroncoso_195598568 mazo1 = new Mazo_BorisTroncoso_195598568("Demo 1", lista1);
        Mazo_BorisTroncoso_195598568 mazo2 = new Mazo_BorisTroncoso_195598568("Demo 2", lista2);
        mazos.add(mazo1);
        mazos.add(mazo2);
        System.out.println("[Sistema] Mazos creados: " + mazo1.resumen() + " y " + mazo2.resumen());

        long semilla = leerLong("Semilla para iniciar la partida (sugerida 11111): ");
        System.out.println("[Sistema] Iniciando juego con los mazos 'Demo 1' y 'Demo 2', semilla "
                + semilla + "...");
        juego = new Juego_BorisTroncoso_195598568(mazo1, mazo2, semilla);
        System.out.println("[Sistema] Partida de demostracion iniciada correctamente.");
    }

    /**
     * Agrega varias copias de una carta a una lista (para armar los mazos).
     *
     * @param lista lista donde se agregan
     * @param carta carta que se repite
     * @param copias cuantas veces
     */
    private void agregarCopias(List<Carta_BorisTroncoso_195598568> lista,
                               Carta_BorisTroncoso_195598568 carta, int copias) {
        for (int i = 0; i < copias; i++) {
            lista.add(carta);
        }
    }

    /**
     * Opcion 9 (RF10): muestra el estado del juego.
     */
    private void opcionMostrarJuego() {
        int numero = leerEntero("Mostrar la mano de que jugador (1/2): ", 1, 2);
        System.out.println(juegoActivo().mostrarJuego(numero));
    }

    /**
     * Opcion 10 (RF11): juega un Pokemon basico de la mano a la banca.
     */
    private void opcionJugarABanca() {
        Juego_BorisTroncoso_195598568 partida = juegoActivo();
        Jugador_BorisTroncoso_195598568 jugador = partida.getJugadorActual();
        List<CartaPokemon_BorisTroncoso_195598568> basicos =
                new ArrayList<CartaPokemon_BorisTroncoso_195598568>();
        for (int i = 0; i < jugador.getMano().size(); i++) {
            Carta_BorisTroncoso_195598568 carta = jugador.getMano().get(i);
            if (carta instanceof CartaPokemon_BorisTroncoso_195598568) {
                CartaPokemon_BorisTroncoso_195598568 pokemon =
                        (CartaPokemon_BorisTroncoso_195598568) carta;
                if (pokemon.esBasico()) {
                    basicos.add(pokemon);
                }
            }
        }
        if (basicos.isEmpty()) {
            throw new IllegalStateException("No hay Pokemon basicos en la mano de "
                    + jugador.getNombre());
        }
        List<String> opciones = new ArrayList<String>();
        for (int i = 0; i < basicos.size(); i++) {
            opciones.add(basicos.get(i).descripcion());
        }
        int indice = elegirIndice("Pokemon basicos en la mano de " + jugador.getNombre() + ":",
                opciones);
        partida.jugarABanca(basicos.get(indice));
    }

    /**
     * Opcion 11 (RF12): cambia o promueve el Pokemon activo desde la banca.
     */
    private void opcionCambiarActivo() {
        Juego_BorisTroncoso_195598568 partida = juegoActivo();
        Jugador_BorisTroncoso_195598568 jugador = partida.getJugadorActual();
        if (jugador.getBanca().isEmpty()) {
            throw new IllegalStateException("La banca de " + jugador.getNombre() + " esta vacia");
        }
        CartaEnJuego_BorisTroncoso_195598568 elegido =
                elegirPokemonDeLista(jugador.getBanca(), "Banca de " + jugador.getNombre() + ":");
        partida.cambiarPokemonActivo(elegido);
    }

    /**
     * Opcion 12 (RF13): roba una carta del mazo.
     */
    private void opcionRobarCarta() {
        juegoActivo().robarCarta();
    }

    /**
     * Opcion 13 (RF14): une una energia de la mano a un Pokemon en juego.
     */
    private void opcionUsarEnergia() {
        Juego_BorisTroncoso_195598568 partida = juegoActivo();
        Jugador_BorisTroncoso_195598568 jugador = partida.getJugadorActual();
        List<CartaEnJuego_BorisTroncoso_195598568> enJuego = jugador.pokemonsEnJuego();
        if (enJuego.isEmpty()) {
            throw new IllegalStateException(jugador.getNombre() + " no tiene Pokemon en juego");
        }
        CartaEnJuego_BorisTroncoso_195598568 objetivo =
                elegirPokemonDeLista(enJuego, "Pokemon que recibira la energia:");
        List<CartaEnergia_BorisTroncoso_195598568> energias =
                new ArrayList<CartaEnergia_BorisTroncoso_195598568>();
        for (int i = 0; i < jugador.getMano().size(); i++) {
            Carta_BorisTroncoso_195598568 carta = jugador.getMano().get(i);
            if (carta instanceof CartaEnergia_BorisTroncoso_195598568) {
                energias.add((CartaEnergia_BorisTroncoso_195598568) carta);
            }
        }
        if (energias.isEmpty()) {
            throw new IllegalStateException("No hay cartas de energia en la mano");
        }
        List<String> opciones = new ArrayList<String>();
        for (int i = 0; i < energias.size(); i++) {
            opciones.add(energias.get(i).descripcion());
        }
        int indice = elegirIndice("Energias en la mano:", opciones);
        partida.usarCartaEnergia(objetivo, energias.get(indice));
    }

    /**
     * Opcion 14 (RF15): evoluciona un Pokemon en juego con una carta de la mano.
     */
    private void opcionEvolucionar() {
        Juego_BorisTroncoso_195598568 partida = juegoActivo();
        Jugador_BorisTroncoso_195598568 jugador = partida.getJugadorActual();
        List<CartaEnJuego_BorisTroncoso_195598568> enJuego = jugador.pokemonsEnJuego();
        if (enJuego.isEmpty()) {
            throw new IllegalStateException(jugador.getNombre() + " no tiene Pokemon en juego");
        }
        CartaEnJuego_BorisTroncoso_195598568 objetivo =
                elegirPokemonDeLista(enJuego, "Pokemon a evolucionar:");
        List<CartaPokemon_BorisTroncoso_195598568> evoluciones =
                new ArrayList<CartaPokemon_BorisTroncoso_195598568>();
        for (int i = 0; i < jugador.getMano().size(); i++) {
            Carta_BorisTroncoso_195598568 carta = jugador.getMano().get(i);
            if (carta instanceof CartaPokemon_BorisTroncoso_195598568) {
                CartaPokemon_BorisTroncoso_195598568 pokemon =
                        (CartaPokemon_BorisTroncoso_195598568) carta;
                if (!pokemon.esBasico()) {
                    evoluciones.add(pokemon);
                }
            }
        }
        if (evoluciones.isEmpty()) {
            throw new IllegalStateException("No hay cartas de evolucion en la mano");
        }
        List<String> opciones = new ArrayList<String>();
        for (int i = 0; i < evoluciones.size(); i++) {
            opciones.add(evoluciones.get(i).descripcion());
        }
        int indice = elegirIndice("Cartas de evolucion en la mano:", opciones);
        partida.evolucionarPokemon(objetivo, evoluciones.get(indice));
    }

    /**
     * Opcion 15 (RF16): usa una carta de entrenador de la mano.
     */
    private void opcionUsarEntrenador() {
        Juego_BorisTroncoso_195598568 partida = juegoActivo();
        Jugador_BorisTroncoso_195598568 jugador = partida.getJugadorActual();
        List<CartaEntrenador_BorisTroncoso_195598568> entrenadores =
                new ArrayList<CartaEntrenador_BorisTroncoso_195598568>();
        for (int i = 0; i < jugador.getMano().size(); i++) {
            Carta_BorisTroncoso_195598568 carta = jugador.getMano().get(i);
            if (carta instanceof CartaEntrenador_BorisTroncoso_195598568) {
                entrenadores.add((CartaEntrenador_BorisTroncoso_195598568) carta);
            }
        }
        if (entrenadores.isEmpty()) {
            throw new IllegalStateException("No hay cartas de entrenador en la mano");
        }
        List<String> opciones = new ArrayList<String>();
        for (int i = 0; i < entrenadores.size(); i++) {
            opciones.add(entrenadores.get(i).descripcion());
        }
        int indice = elegirIndice("Cartas de entrenador en la mano:", opciones);
        partida.usarCartaEntrenador(entrenadores.get(indice), leerArgsAdicionales(jugador));
    }

    /**
     * Opcion 16 (RF17): usa la habilidad de un Pokemon en juego.
     */
    private void opcionUsarHabilidad() {
        Juego_BorisTroncoso_195598568 partida = juegoActivo();
        Jugador_BorisTroncoso_195598568 jugador = partida.getJugadorActual();
        List<CartaEnJuego_BorisTroncoso_195598568> conHabilidad =
                new ArrayList<CartaEnJuego_BorisTroncoso_195598568>();
        List<CartaEnJuego_BorisTroncoso_195598568> enJuego = jugador.pokemonsEnJuego();
        for (int i = 0; i < enJuego.size(); i++) {
            if (enJuego.get(i).getCarta().getHabilidad() != null) {
                conHabilidad.add(enJuego.get(i));
            }
        }
        if (conHabilidad.isEmpty()) {
            throw new IllegalStateException("No hay Pokemon en juego con habilidad");
        }
        CartaEnJuego_BorisTroncoso_195598568 elegido =
                elegirPokemonDeLista(conHabilidad, "Pokemon con habilidad:");
        partida.usarHabilidadPokemon(elegido, leerArgsAdicionales(jugador));
    }

    /**
     * Opcion 17 (RF18): usa un ataque del activo o pasa el turno.
     */
    private void opcionUsarAtaque() {
        Juego_BorisTroncoso_195598568 partida = juegoActivo();
        Jugador_BorisTroncoso_195598568 jugador = partida.getJugadorActual();
        CartaEnJuego_BorisTroncoso_195598568 activo = jugador.getActivo();
        if (activo == null) {
            throw new IllegalStateException(
                    "No hay Pokemon activo: promueva uno desde la banca (opcion 11)");
        }
        List<Ataque_BorisTroncoso_195598568> disponibles = activo.getCarta().getAtaques();
        System.out.println("Ataques de " + activo.getCarta().getNombre() + ":");
        System.out.println("  0. No atacar (terminar el turno)");
        for (int i = 0; i < disponibles.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + disponibles.get(i).resumen());
        }
        int eleccion = leerEntero("Opcion: ", 0, disponibles.size());
        String nombre = null;
        if (eleccion > 0) {
            nombre = disponibles.get(eleccion - 1).getNombre();
        }
        partida.usarAtaquePokemon(nombre, new ArrayList<Object>());
    }

    /**
     * Opcion 18 (RF opcional): pide recomendaciones a Google Gemini con el
     * estado de la partida (o un mazo si no hay partida) y las guarda.
     */
    private void opcionObtenerRecomendaciones() {
        if (!recomendador.tieneApiKey()) {
            recomendador.setApiKey(leerTexto(
                    "Ingrese su API key de Google Gemini (o defina la variable GEMINI_API_KEY): "));
        }
        String contexto;
        if (juego != null) {
            contexto = "Estado actual de la partida (soy el "
                    + juego.getJugadorActual().getNombre() + "):\n"
                    + juego.mostrarJuego(juego.getNumeroJugadorActual());
        } else {
            if (mazos.isEmpty()) {
                throw new IllegalStateException(
                        "No hay partida iniciada ni mazos creados para analizar");
            }
            Mazo_BorisTroncoso_195598568 mazo = elegirMazo("Mazo a analizar:");
            StringBuilder sb = new StringBuilder("Composicion del mazo " + mazo.getNombre() + ": ");
            List<String> nombresVistos = new ArrayList<String>();
            for (int i = 0; i < mazo.getCartas().size(); i++) {
                String nombreCarta = mazo.getCartas().get(i).getNombre();
                if (!nombresVistos.contains(nombreCarta)) {
                    nombresVistos.add(nombreCarta);
                    int cuenta = 0;
                    for (int j = 0; j < mazo.getCartas().size(); j++) {
                        if (mazo.getCartas().get(j).getNombre().equals(nombreCarta)) {
                            cuenta = cuenta + 1;
                        }
                    }
                    sb.append(cuenta).append("x ").append(nombreCarta).append("; ");
                }
            }
            contexto = sb.toString();
        }
        System.out.println("[Sistema] Consultando a Google Gemini...");
        try {
            List<String> lista = recomendador.obtenerRecomendaciones(contexto);
            System.out.println("[Sistema] Recomendaciones recibidas y almacenadas:");
            for (int i = 0; i < lista.size(); i++) {
                System.out.println("  " + lista.get(i));
            }
        } catch (IOException e) {
            System.out.println("[Error] No se pudieron obtener recomendaciones: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("[Error] La consulta fue interrumpida: " + e.getMessage());
        }
    }

    /**
     * Opcion 19 (RF opcional): muestra las recomendaciones guardadas.
     */
    private void opcionVerRecomendaciones() {
        List<String> lista = recomendador.getRecomendaciones();
        if (lista.isEmpty()) {
            System.out.println("[Sistema] No hay recomendaciones almacenadas (use la opcion 18).");
            return;
        }
        System.out.println("[Sistema] Recomendaciones IA almacenadas:");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println("  " + lista.get(i));
        }
    }

    /**
     * Retorna la partida actual y reclama si no hay ninguna.
     *
     * @return la partida en curso
     */
    private Juego_BorisTroncoso_195598568 juegoActivo() {
        if (juego == null) {
            throw new IllegalStateException("No hay una partida iniciada (use la opcion 7 u 8)");
        }
        return juego;
    }

    /**
     * Pregunta por los argumentos adicionales de un efecto: opcionalmente se
     * puede elegir un Pokemon en juego (por ejemplo para curar o para el
     * cambio de activo).
     *
     * @param jugador jugador actual
     * @return lista de argumentos (vacia o con el Pokemon elegido)
     */
    private List<Object> leerArgsAdicionales(Jugador_BorisTroncoso_195598568 jugador) {
        List<Object> args = new ArrayList<Object>();
        if (leerSiNo("Indicar un Pokemon en juego como argumento del efecto? (s/n): ")) {
            List<CartaEnJuego_BorisTroncoso_195598568> enJuego = jugador.pokemonsEnJuego();
            if (enJuego.isEmpty()) {
                throw new IllegalStateException("No hay Pokemon en juego para elegir");
            }
            args.add(elegirPokemonDeLista(enJuego, "Pokemon objetivo:"));
        }
        return args;
    }

    /**
     * Muestra los efectos disponibles y retorna el que se elija.
     *
     * @return efecto elegido
     */
    private Efecto_BorisTroncoso_195598568 elegirEfecto() {
        List<String> opciones = new ArrayList<String>();
        for (int i = 0; i < efectos.size(); i++) {
            opciones.add(efectos.get(i).getDescripcion());
        }
        return efectos.get(elegirIndice("Efectos disponibles:", opciones));
    }

    /**
     * Muestra los mazos creados y retorna el que se elija.
     *
     * @param titulo titulo que se muestra arriba de la lista
     * @return mazo elegido
     */
    private Mazo_BorisTroncoso_195598568 elegirMazo(String titulo) {
        if (mazos.isEmpty()) {
            throw new IllegalStateException("No hay mazos creados (use la opcion 5 o la 8)");
        }
        List<String> opciones = new ArrayList<String>();
        for (int i = 0; i < mazos.size(); i++) {
            opciones.add(mazos.get(i).resumen());
        }
        return mazos.get(elegirIndice(titulo, opciones));
    }

    /**
     * Muestra una lista de Pokemon en juego y retorna el que se elija.
     *
     * @param lista Pokemon para elegir
     * @param titulo titulo que se muestra arriba de la lista
     * @return Pokemon elegido
     */
    private CartaEnJuego_BorisTroncoso_195598568 elegirPokemonDeLista(
            List<CartaEnJuego_BorisTroncoso_195598568> lista, String titulo) {
        List<String> opciones = new ArrayList<String>();
        for (int i = 0; i < lista.size(); i++) {
            opciones.add(lista.get(i).descripcion());
        }
        return lista.get(elegirIndice(titulo, opciones));
    }

    /**
     * Muestra opciones numeradas y lee cual elige el usuario.
     *
     * @param titulo titulo de la lista
     * @param opciones textos que se muestran
     * @return indice elegido (partiendo de 0)
     */
    private int elegirIndice(String titulo, List<String> opciones) {
        System.out.println(titulo);
        for (int i = 0; i < opciones.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + opciones.get(i));
        }
        return leerEntero("Seleccion: ", 1, opciones.size()) - 1;
    }

    /**
     * Busca una carta del catalogo por su id.
     *
     * @param id id de la carta
     * @return la carta o null si no existe
     */
    private Carta_BorisTroncoso_195598568 buscarCartaPorId(int id) {
        for (int i = 0; i < cartas.size(); i++) {
            if (cartas.get(i).getId() == id) {
                return cartas.get(i);
            }
        }
        return null;
    }

    /**
     * Lee un numero entero dentro de un rango, repitiendo hasta que sea valido.
     *
     * @param prompt mensaje que se muestra
     * @param minimo valor minimo aceptado
     * @param maximo valor maximo aceptado
     * @return el numero leido
     */
    private int leerEntero(String prompt, int minimo, int maximo) {
        while (true) {
            System.out.print(prompt);
            String linea = scanner.nextLine().trim();
            try {
                int valor = Integer.parseInt(linea);
                if (valor >= minimo && valor <= maximo) {
                    return valor;
                }
            } catch (NumberFormatException e) {
                // entrada no numerica, se avisa abajo
            }
            System.out.println("[Error] Ingrese un numero entre " + minimo + " y " + maximo + ".");
        }
    }

    /**
     * Lee un numero long no negativo (se usa para las semillas).
     *
     * @param prompt mensaje que se muestra
     * @return el numero leido
     */
    private long leerLong(String prompt) {
        while (true) {
            System.out.print(prompt);
            String linea = scanner.nextLine().trim();
            try {
                long valor = Long.parseLong(linea);
                if (valor >= 0) {
                    return valor;
                }
            } catch (NumberFormatException e) {
                // entrada no numerica, se avisa abajo
            }
            System.out.println("[Error] Ingrese un numero valido (mayor o igual a 0).");
        }
    }

    /**
     * Lee un texto que no puede quedar vacio.
     *
     * @param prompt mensaje que se muestra
     * @return el texto leido
     */
    private String leerTexto(String prompt) {
        while (true) {
            System.out.print(prompt);
            String linea = scanner.nextLine().trim();
            if (!linea.isEmpty()) {
                return linea;
            }
            System.out.println("[Error] El texto no puede estar vacio.");
        }
    }

    /**
     * Lee un texto opcional.
     *
     * @param prompt mensaje que se muestra
     * @return el texto leido, o null si se dejo vacio
     */
    private String leerTextoOpcional(String prompt) {
        System.out.print(prompt);
        String linea = scanner.nextLine().trim();
        if (linea.isEmpty()) {
            return null;
        }
        return linea;
    }

    /**
     * Lee una respuesta si/no.
     *
     * @param prompt mensaje que se muestra
     * @return true si responde s o si
     */
    private boolean leerSiNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String linea = scanner.nextLine().trim().toLowerCase();
            if (linea.equals("s") || linea.equals("si")) {
                return true;
            }
            if (linea.equals("n") || linea.equals("no")) {
                return false;
            }
            System.out.println("[Error] Responda 's' o 'n'.");
        }
    }
}
