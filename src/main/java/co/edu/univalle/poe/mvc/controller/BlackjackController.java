package co.edu.univalle.poe.mvc.controller;

import co.edu.univalle.poe.mvc.model.Carta;
import co.edu.univalle.poe.mvc.model.Partida;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;

public class BlackjackController {

    @FXML
    private Label lblPuntajeComputador;

    @FXML
    private HBox hbCartasComputador;

    @FXML
    private Label lblSaldo;

    @FXML
    private Label lblApuestaVigente;

    @FXML
    private Label lblMensajeTurno;

    @FXML
    private Label lblJugadaEspecial;

    @FXML
    private Label lblResultado;

    @FXML
    private Label lblPuntajeJugador;

    @FXML
    private HBox hbCartasJugador;

    @FXML
    private TextField txtApuesta;

    @FXML
    private Button btnIniciarRonda;

    @FXML
    private Button btnPedirCarta;

    @FXML
    private Button btnPlantarse;

    @FXML
    private Button btnVerReglas;

    @FXML
    private Button btnCerrarJuego;

    private Partida partida;

    /*  private Mano manoJugador; agregar cunado se cree la clase mano
        private Mano manoComputador;*/

    @FXML
    public void initialize() {
        partida = new Partida();

        // Estado inicial: solo se puede iniciar ronda.
        btnPedirCarta.setDisable(true);
        btnPlantarse.setDisable(true);

        lblPuntajeComputador.setText("Puntaje: 0");
        lblPuntajeJugador.setText("Puntaje: 0");
        lblJugadaEspecial.setText("");
        lblResultado.setText("");
        lblMensajeTurno.setText("Ingrese su apuesta e inicie la ronda.");

        actualizarSaldo();
        actualizarApuestaVigente();
    }

    @FXML
    public void onIniciarRonda() {
        int apuesta;
        
        try {
            apuesta = Integer.parseInt(txtApuesta.getText().trim());
        } catch (NumberFormatException ex) {
            lblMensajeTurno.setText("Ingrese un número válido para la apuesta.");
            return;
        }

        if (!partida.validarApuesta(apuesta)) {
            lblMensajeTurno.setText("Apuesta inválida: mínimo 10, múltiplo de 10 y no mayor al saldo.");
            return;
        }

        // El modelo ya descuenta el saldo, fija la apuesta, baraja el mazo
        // y cambia el estado a TURNO_JUGADOR.
        partida.iniciarRonda(apuesta);

        actualizarSaldo();
        actualizarApuestaVigente();

        btnIniciarRonda.setDisable(true);
        btnPedirCarta.setDisable(false);
        btnPlantarse.setDisable(false);

        lblResultado.setText("");
        lblJugadaEspecial.setText("");
        lblMensajeTurno.setText("Turno del jugador. Pida carta o plántese.");

        // Se limpia la mesa para la nueva ronda.
        limpiarCartas(hbCartasJugador);
        limpiarCartas(hbCartasComputador);

        /*manoJugador = new Mano();
        manoComputador = new Mano();

        Carta cartaJugador1 = partida.getMazo().repartirCarta();
        Carta cartaJugador2 = partida.getMazo().repartirCarta();
        Carta cartaComputador1 = partida.getMazo().repartirCarta();
        Carta cartaComputador2 = partida.getMazo().repartirCarta();

        manoJugador.agregarCarta(cartaJugador1);
        manoJugador.agregarCarta(cartaJugador2);
        manoComputador.agregarCarta(cartaComputador1);
        manoComputador.agregarCarta(cartaComputador2);

        mostrarCarta(hbCartasJugador, cartaJugador1);
        mostrarCarta(hbCartasJugador, cartaJugador2);
        mostrarCarta(hbCartasComputador, cartaComputador1);
        mostrarReverso(hbCartasComputador); // segunda carta de la banca, oculta
        */
    }

    @FXML
    public void onPedirCarta() {
        // Pendiente: depende de la clase Mano (la está creando el compañero).
        lblMensajeTurno.setText("Pedir carta disponible cuando exista la clase Mano.");

        /*Carta nueva = partida.getMazo().repartirCarta();
        manoJugador.agregarCarta(nueva);
        mostrarCarta(hbCartasJugador, nueva);
        if (manoJugador.getPuntos() > 21) {
            partida.finalizarRonda(ResultadoRonda.DERROTA);
            mostrarResultado("Te pasaste de 21. ¡Perdiste!");
            terminarRonda();
        }*/
    }

    @FXML
    public void onPlantarse() {
        // Pendiente: depende de la clase Mano (la está creando el compañero).
        lblMensajeTurno.setText("Plantarse disponible cuando exista la clase Mano.");

        /*partida.setEstado(EstadoRonda.TURNO_COMPUTADOR);
        while (manoComputador.getPuntos() < 17) {
            Carta nueva = partida.getMazo().repartirCarta();
            manoComputador.agregarCarta(nueva);
            mostrarCarta(hbCartasComputador, nueva);
        }
        // aquí se debe revelar la carta oculta de la banca
        // (volver a dibujar su mano completa con mostrarCarta)

        ResultadoRonda resultado = calcularResultado();
        partida.finalizarRonda(resultado);
        mostrarResultado(resultado);
        terminarRonda();*/
    }

    // Texto completo de las reglas que se muestra en "Ver reglas".
    private static final String REGLAS =
            "OBJETIVO\n"
          + "Obtener más puntos que el computador sin pasarte de 21.\n"
          + "\n"
          + "VALOR DE LAS CARTAS\n"
          + "• 2 al 10: su valor numérico.\n"
          + "• J, Q y K: 10 puntos.\n"
          + "• As: vale 1 u 11, el que más te convenga.\n"
          + "\n"
          + "APUESTAS\n"
          + "• Empiezas con 1.000 fichas.\n"
          + "• La apuesta debe ser un número entero, múltiplo de 10, mínimo 10 y sin superar tu saldo.\n"
          + "• Se descuenta al iniciar la ronda y no puede cambiarse.\n"
          + "\n"
          + "LA RONDA\n"
          + "1. Recibes dos cartas. El computador espera sin cartas.\n"
          + "2. Pides carta o te plantas. Si llegas a 21, tu turno termina solo.\n"
          + "3. Si te pasas de 21, pierdes de inmediato.\n"
          + "4. Al plantarte, el computador recibe dos cartas y pide mientras tenga menos de 17. "
          + "Con 17 o más se planta, y si se pasa de 21 pierde.\n"
          + "\n"
          + "JUGADAS ESPECIALES\n"
          + "• Blackjack: un As y una carta de valor 10 en las dos primeras cartas. "
          + "Supera a cualquier mano que no sea Blackjack.\n"
          + "• Veintiuno: 21 puntos sin ser Blackjack.\n"
          + "• Veinte y medio: en tus dos primeras cartas, una figura (J, Q o K) y un 4. "
          + "Solo se avisa: no gana automáticamente ni da pago extra, y puedes pedir o plantarte.\n"
          + "\n"
          + "RESULTADOS Y PAGOS\n"
          + "• Derrota: pierdes la apuesta.\n"
          + "• Empate: se te devuelve la apuesta. Ocurre con el mismo puntaje, o con Blackjack de ambos.\n"
          + "• Victoria: recibes tu apuesta más una igual (x2).\n"
          + "• Victoria con Blackjack: recibes tu apuesta más 1,5 veces su valor (x2,5).\n"
          + "Ejemplo con 100 fichas: victoria = 200, Blackjack = 250.";

    @FXML
    public void onVerReglas() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reglas del Blackjack");
        alert.setHeaderText("Reglas del Blackjack");

        Label contenido = new Label(REGLAS);
        contenido.setWrapText(true);
        contenido.setStyle("-fx-font-size: 13px; -fx-padding: 12;");

        ScrollPane scroll = new ScrollPane(contenido);
        scroll.setFitToWidth(true);
        scroll.setPrefViewportWidth(600);
        scroll.setPrefViewportHeight(500);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        alert.getDialogPane().setContent(scroll);
        alert.getDialogPane().setPrefWidth(660);
        alert.setResizable(true);
        alert.showAndWait();
    }

    @FXML
    public void onCerrarJuego() {
        Stage stage = (Stage) btnCerrarJuego.getScene().getWindow();
        stage.close();
    }


    // Ruta de las imágenes dentro de resources (classpath).
    private static final String RUTA_CARTAS = "/co/edu/univalle/poe/mvc/images/cartas/";
    private static final String ARCHIVO_REVERSO = "reverso.png";

    // Tamaño uniforme con el que se dibujan las cartas.
    private static final double ANCHO_CARTA = 82;
    private static final double ALTO_CARTA = 115;

    /**
     * Dibuja una carta boca arriba dentro del contenedor indicado.
     */
    public void mostrarCarta(HBox contenedor, Carta carta) {
        ImageView vista = crearImageView(RUTA_CARTAS + nombreArchivo(carta));
        if (vista != null) {
            contenedor.getChildren().add(vista);
        }
    }

    /**
     * Dibuja el reverso de una carta (para ocultar una carta de la banca).
     */
    public void mostrarReverso(HBox contenedor) {
        ImageView vista = crearImageView(RUTA_CARTAS + ARCHIVO_REVERSO);
        if (vista != null) {
            contenedor.getChildren().add(vista);
        }
    }

    /**
     * Vacía las cartas dibujadas en un contenedor.
     */
    public void limpiarCartas(HBox contenedor) {
        contenedor.getChildren().clear();
    }

    /**
     * Construye el nombre del archivo a partir de los enums Valor y Palo.
     * Ejemplo: Carta(AS, CORAZONES) -> "as_corazones.png".
     */
    private String nombreArchivo(Carta carta) {
        return carta.getValor().name().toLowerCase()
                + "_"
                + carta.getPalo().name().toLowerCase()
                + ".png";
    }

    /**
     * Carga la imagen desde resources y la devuelve como ImageView uniforme.
     */
    private ImageView crearImageView(String archivo) {
        URL url = getClass().getResource(archivo);
        if (url == null) {
            System.err.println("No se encontró la imagen: " + archivo);
            return null;
        }

        ImageView vista = new ImageView(new Image(url.toExternalForm()));
        vista.setFitWidth(ANCHO_CARTA);
        vista.setFitHeight(ALTO_CARTA);
        vista.setPreserveRatio(true);
        return vista;
    }

    private void actualizarSaldo() {
        lblSaldo.setText("Saldo: " + partida.getSaldo() + " fichas");
    }

    private void actualizarApuestaVigente() {
        lblApuestaVigente.setText("Apuesta vigente: " + partida.getApuestaVigente() + " fichas");
    }
    
}
