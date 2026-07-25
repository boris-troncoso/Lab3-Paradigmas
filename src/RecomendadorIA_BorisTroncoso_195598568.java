import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * TDA RecomendadorIA (RF opcional): manda el estado de la partida o la
 * composicion de un mazo a Google Gemini y guarda hasta 10 recomendaciones,
 * que despues se pueden ver desde el menu.
 *
 * Se usa solo la biblioteca estandar de Java 11 (java.net.http.HttpClient),
 * sin librerias externas. La API key se saca de la variable de entorno
 * GEMINI_API_KEY o se pide por el menu.
 *
 * Representacion: apiKey y la lista de recomendaciones guardadas.
 *
 * @author Boris Troncoso 195598568
 */
public class RecomendadorIA_BorisTroncoso_195598568 {

    /** direccion del servicio de Gemini, la API key va al final */
    private static final String URL_BASE =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=";

    /** API key de Google Gemini */
    private String apiKey = System.getenv("GEMINI_API_KEY");

    /** ultimas recomendaciones guardadas (maximo 10) */
    private final List<String> recomendaciones = new ArrayList<String>();

    /**
     * Constructor: intenta tomar la API key desde la variable de entorno.
     */
    public RecomendadorIA_BorisTroncoso_195598568() {
    }

    /**
     * Indica si hay una API key configurada.
     *
     * @return true si hay API key
     */
    public boolean tieneApiKey() {
        return apiKey != null && !apiKey.trim().isEmpty();
    }

    /**
     * Cambia la API key.
     *
     * @param apiKey clave de la API de Gemini
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Retorna las recomendaciones guardadas.
     *
     * @return lista con las ultimas recomendaciones
     */
    public List<String> getRecomendaciones() {
        return recomendaciones;
    }

    /**
     * Manda la consulta a Gemini y guarda hasta 10 recomendaciones.
     *
     * @param contexto estado de la partida o descripcion del mazo
     * @return las recomendaciones que se obtuvieron
     * @throws IOException si falla la conexion o la respuesta viene mala
     * @throws InterruptedException si la peticion se interrumpe
     */
    public List<String> obtenerRecomendaciones(String contexto)
            throws IOException, InterruptedException {
        if (!tieneApiKey()) {
            throw new IOException("No hay API key de Gemini configurada");
        }
        String prompt = "Eres un experto en el Juego de Cartas Coleccionables Pokemon (TCG). "
                + "Con base en la siguiente informacion, entrega una lista de a lo mas 10 "
                + "recomendaciones breves y numeradas sobre que cartas jugar o que estrategia "
                + "seguir. Responde solo con la lista, sin acentos ni caracteres especiales.\n\n"
                + contexto;
        String cuerpo = "{\"contents\":[{\"parts\":[{\"text\":\"" + escaparJson(prompt) + "\"}]}]}";
        HttpClient cliente = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(20))
                .build();
        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(URL_BASE + apiKey))
                .timeout(Duration.ofSeconds(60))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(cuerpo))
                .build();
        HttpResponse<String> respuesta =
                cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());
        if (respuesta.statusCode() != 200) {
            throw new IOException("Gemini respondio HTTP " + respuesta.statusCode()
                    + ": " + recortar(respuesta.body()));
        }
        String texto = extraerPrimerTexto(respuesta.body());
        if (texto == null || texto.trim().isEmpty()) {
            throw new IOException("La respuesta de Gemini no contiene texto");
        }
        recomendaciones.clear();
        String[] lineas = texto.split("\n");
        for (int i = 0; i < lineas.length; i++) {
            String limpia = lineas[i].trim();
            if (!limpia.isEmpty() && recomendaciones.size() < 10) {
                recomendaciones.add(limpia);
            }
        }
        return recomendaciones;
    }

    /**
     * Acorta un texto largo para los mensajes de error.
     *
     * @param texto texto a acortar
     * @return texto de maximo 200 caracteres
     */
    private static String recortar(String texto) {
        if (texto == null) {
            return "";
        }
        if (texto.length() <= 200) {
            return texto;
        }
        return texto.substring(0, 200) + "...";
    }

    /**
     * Escapa un texto para poder meterlo dentro de un string JSON.
     *
     * @param texto texto normal
     * @return texto con los caracteres especiales escapados
     */
    private static String escaparJson(String texto) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            if (c == '\\' || c == '"') {
                sb.append('\\').append(c);
            } else if (c == '\n') {
                sb.append("\\n");
            } else if (c == '\r') {
                sb.append("\\r");
            } else if (c == '\t') {
                sb.append("\\t");
            } else if (c >= 32) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Saca el primer campo "text" de la respuesta JSON de Gemini, procesando
     * los escapes basicos del formato JSON.
     *
     * @param json cuerpo de la respuesta
     * @return el texto encontrado, o null si no hay
     */
    private static String extraerPrimerTexto(String json) {
        int indice = json.indexOf("\"text\"");
        if (indice < 0) {
            return null;
        }
        int dosPuntos = json.indexOf(':', indice);
        if (dosPuntos < 0) {
            return null;
        }
        int inicio = json.indexOf('"', dosPuntos + 1);
        if (inicio < 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int i = inicio + 1;
        while (i < json.length()) {
            char c = json.charAt(i);
            if (c == '"') {
                break;
            }
            if (c == '\\' && i + 1 < json.length()) {
                char escape = json.charAt(i + 1);
                if (escape == 'n') {
                    sb.append('\n');
                } else if (escape == 't') {
                    sb.append('\t');
                } else if (escape == 'r') {
                    sb.append('\r');
                } else if (escape == 'u' && i + 5 < json.length()) {
                    String hexa = json.substring(i + 2, i + 6);
                    sb.append((char) Integer.parseInt(hexa, 16));
                    i = i + 4;
                } else {
                    sb.append(escape);
                }
                i = i + 2;
            } else {
                sb.append(c);
                i = i + 1;
            }
        }
        return sb.toString();
    }
}
