import java.util.List;

/**
 * Efecto que calcula el dano del ataque lanzando monedas: el dano queda como
 * la cantidad de caras por un multiplicador. Es un ejemplo de ataque cuyo
 * dano depende del efecto (RF18).
 *
 * @author Boris Troncoso 195598568
 */
public class EfectoDanoPorMoneda_BorisTroncoso_195598568
        implements Efecto_BorisTroncoso_195598568 {

    /** cantidad de monedas que se lanzan */
    private final int monedas;

    /** dano que aporta cada cara */
    private final int danoPorCara;

    /**
     * Constructor del efecto.
     *
     * @param monedas cuantas monedas se lanzan (mayor que 0)
     * @param danoPorCara dano por cada cara (mayor que 0)
     */
    public EfectoDanoPorMoneda_BorisTroncoso_195598568(int monedas, int danoPorCara) {
        if (monedas <= 0 || danoPorCara <= 0) {
            throw new IllegalArgumentException("Las monedas y el dano por cara deben ser positivos");
        }
        this.monedas = monedas;
        this.danoPorCara = danoPorCara;
    }

    /**
     * Lanza las monedas y deja el dano calculado en la partida.
     *
     * @param juego partida actual
     * @param args no se usan
     * @return la partida con el dano del ataque actualizado
     */
    @Override
    public Juego_BorisTroncoso_195598568 ejecutar(Juego_BorisTroncoso_195598568 juego,
                                                  List<Object> args) {
        int caras = 0;
        for (int i = 0; i < monedas; i++) {
            boolean cara = juego.lanzarMoneda();
            if (cara) {
                juego.registrarMensaje("Moneda " + (i + 1) + ": cara.");
                caras = caras + 1;
            } else {
                juego.registrarMensaje("Moneda " + (i + 1) + ": sello.");
            }
        }
        int dano = caras * danoPorCara;
        juego.setDanoAtaqueActual(dano);
        juego.registrarMensaje("Dano del ataque: " + caras + " cara(s) x "
                + danoPorCara + " = " + dano + ".");
        return juego;
    }

    /**
     * Descripcion para el menu.
     *
     * @return texto del efecto
     */
    @Override
    public String getDescripcion() {
        return "Lanza " + monedas + " moneda(s); dano = caras x " + danoPorCara;
    }
}
