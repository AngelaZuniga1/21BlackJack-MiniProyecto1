package co.edu.univalle.poe.mvc.controller;

import co.edu.univalle.poe.mvc.model.Carta;
import co.edu.univalle.poe.mvc.model.Mano;
import co.edu.univalle.poe.mvc.model.Partida;
import co.edu.univalle.poe.mvc.model.ResultadoRonda;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class BlackjackController {

    // Constantes

    private static final String RUTA_CARTAS = "/co/edu/univalle/poe/mvc/images/cartas/";
    private static final double ANCHO_CARTA = 82;
    private static final double ALTO_CARTA = 115;

    private static final String MSG_INICIO = "Ingrese su apuesta e inicie la ronda.";
    private static final String MSG_TURNO = "Turno del jugador. Pida carta o plántese.";
    private static final String MSG_FIN = "Ronda terminada. Ingrese una nueva apuesta para jugar otra vez.";
    private static final String MSG_SIN_SALDO = "Saldo insuficiente para continuar. Cierre el juego.";
    private static final String MSG_NO_NUMERO = "Ingrese un número entero para la apuesta.";

    private static final String REGLAS = ""
            + "OBJETIVO\n"
            + "Obtener más puntos que el computador sin pasarte de 21.\n"
            + "\n"
            + "VALOR DE LAS CARTAS\n"
            + "• 2 al 10: su valor numérico.\n"
            + "• J, Q y K: 10 puntos.\n"
            + "• As: vale 1 u 11, el que más te convenga.\n"
            + "\n"
            + "APUESTAS\n"
            + "• Empiezas con 1.000 fichas.\n"
            + "• Entera, múltiplo de 10, mínimo 10 y sin superar tu saldo.\n"
            + "• Se descuenta al iniciar la ronda y no puede cambiarse.\n"
            + "\n"
            + "LA RONDA\n"
            + "1. Recibes dos cartas. El computador espera sin cartas.\n"
            + "2. Pides carta o te plantas. Si llegas a 21, tu turno termina solo.\n"
            + "3. Si te pasas de 21, pierdes de inmediato.\n"
            + "4. Al plantarte, el computador recibe dos cartas y pide mientras tenga menos de 17. Con 17 o más se planta, y si se pasa de 21 pierde.\n"
            + "\n"
            + "JUGADAS ESPECIALES\n"
            + "• Blackjack: un As y una carta de valor 10 en las dos primeras cartas. Supera a cualquier mano que no sea Blackjack.\n"
            + "• Veintiuno: 21 puntos sin ser Blackjack.\n"
            + "• Veinte y medio: en las dos primeras cartas, una figura (J, Q o K) y un 4. Solo se avisa: no gana ni da pago extra.\n"
            + "\n"
            + "RESULTADOS Y PAGOS\n"
            + "• Derrota: pierdes la apuesta.\n"
            + "• Empate: se devuelve la apuesta (mismo puntaje, o Blackjack de ambos).\n"
            + "• Victoria: apuesta más una igual (x2).\n"
            + "• Victoria con Blackjack: apuesta más 1,5 veces su valor (x2,5).\n"
            + "Ejemplo con 100 fichas: victoria = 200, Blackjack = 250.";

    // Vista (FXML)

    @FXML private Label lblPuntajeComputador;
    @FXML private HBox hbCartasComputador;
    @FXML private Label lblSaldo;
    @FXML private Label lblApuestaVigente;
    @FXML private TextField txtApuesta;
    @FXML private Label lblMensajeTurno;
    @FXML private Label lblJugadaEspecial;
    @FXML private Label lblResultado;
    @FXML private Label lblPuntajeJugador;
    @FXML private HBox hbCartasJugador;
    @FXML private Button btnIniciarRonda;
    @FXML private Button btnPedirCarta;
    @FXML private Button btnPlantarse;

    // Modelo

    private Partida partida;

    // Inicio

    @FXML
    public void initialize() {
        partida = new Partida();
        refrescarVista();

        // La X de la ventana también pide confirmación
        Platform.runLater(() -> getStage().setOnCloseRequest(evento -> {
            if (!confirmarCierre()) {
                evento.consume();
            }
        }));
    }

    // Acciones

    @FXML
    public void onIniciarRonda() {
        String texto = txtApuesta.getText().trim();
        if (!texto.matches("\\d{1,9}")) {
            lblMensajeTurno.setText(MSG_NO_NUMERO);
            return;
        }

        String motivo = partida.iniciarRonda(Integer.parseInt(texto));
        if (motivo != null) {
            lblMensajeTurno.setText(motivo);
            return;
        }
        refrescarVista();
    }

    @FXML
    public void onPedirCarta() {
        partida.pedirCartaJugador();
        refrescarVista();
    }

    @FXML
    public void onPlantarse() {
        partida.plantarse();
        refrescarVista();
    }

    @FXML
    public void onVerReglas() {
        Label texto = new Label(REGLAS);
        texto.setWrapText(true);
        texto.setStyle("-fx-font-size: 13px; -fx-padding: 12;");

        ScrollPane scroll = new ScrollPane(texto);
        scroll.setFitToWidth(true);
        scroll.setPrefSize(600, 500);

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Reglas del Blackjack");
        alerta.setHeaderText("Reglas del Blackjack");
        alerta.getDialogPane().setContent(scroll);
        alerta.showAndWait();
    }

    @FXML
    public void onCerrarJuego() {
        if (confirmarCierre()) {
            getStage().close();
        }
    }

    // Vista

    // Redibuja todo a partir del estado del modelo
    private void refrescarVista() {
        dibujarMano(hbCartasJugador, partida.getManoJugador());
        dibujarMano(hbCartasComputador, partida.getManoComputador());

        lblPuntajeJugador.setText("Puntaje: " + textoPuntaje(partida.getManoJugador()));
        lblPuntajeComputador.setText("Puntaje: " + textoPuntaje(partida.getManoComputador()));
        lblSaldo.setText("Saldo: " + partida.getSaldo() + " fichas");
        lblApuestaVigente.setText("Apuesta vigente: " + partida.getApuestaVigente() + " fichas");

        lblJugadaEspecial.setText((jugadaEspecial("Jugador", partida.getManoJugador())
                + jugadaEspecial("Computador", partida.getManoComputador())).trim());
        lblResultado.setText(mensajeResultado(partida.getUltimoResultado()));
        lblMensajeTurno.setText(mensajeTurno());

        actualizarBotones();
    }

    // Las acciones se habilitan según el estado de la partida
    private void actualizarBotones() {
        boolean turnoJugador = partida.esTurnoJugador();
        boolean puedeApostar = !turnoJugador && partida.getSaldo() >= Partida.APUESTA_MINIMA;

        btnIniciarRonda.setDisable(!puedeApostar);
        txtApuesta.setDisable(!puedeApostar);
        btnPedirCarta.setDisable(!turnoJugador);
        btnPlantarse.setDisable(!turnoJugador);
    }

    private void dibujarMano(HBox contenedor, Mano mano) {
        contenedor.getChildren().clear();
        for (Carta carta : mano.getCartas()) {
            contenedor.getChildren().add(crearImagen(carta));
        }
    }

    // La imagen se llama como la carta, por ejemplo "as_corazones.png"
    private ImageView crearImagen(Carta carta) {
        String archivo = carta.getValor().name().toLowerCase() + "_" + carta.getPalo().name().toLowerCase() + ".png";
        ImageView vista = new ImageView(new Image(getClass().getResource(RUTA_CARTAS + archivo).toExternalForm()));
        vista.setFitWidth(ANCHO_CARTA);
        vista.setFitHeight(ALTO_CARTA);
        vista.setPreserveRatio(true);
        return vista;
    }

    // Textos

    private String textoPuntaje(Mano mano) {
        return mano.estaVacia() ? "-" : String.valueOf(mano.getPuntaje());
    }

    // Texto de la jugada especial de una mano, o "" si no tiene
    private String jugadaEspecial(String nombre, Mano mano) {
        if (mano.esBlackjack()) {
            return nombre + ": ¡Blackjack! ";
        }
        if (mano.esVeinteYMedio()) {
            return nombre + ": ¡Veinte y medio! ";
        }
        if (mano.esVeintiuno()) {
            return nombre + ": ¡Veintiuno! ";
        }
        return "";
    }

    private String mensajeTurno() {
        if (partida.esTurnoJugador()) {
            return MSG_TURNO;
        }
        if (partida.getSaldo() < Partida.APUESTA_MINIMA) {
            return MSG_SIN_SALDO;
        }
        return partida.getUltimoResultado() == null ? MSG_INICIO : MSG_FIN;
    }

    private String mensajeResultado(ResultadoRonda resultado) {
        if (resultado == null) {
            return "";
        }
        switch (resultado) {
            case VICTORIA_BLACKJACK:
                return "¡Victoria con Blackjack! Pago x2,5.";
            case VICTORIA:
                return "¡Victoria! Pago x2.";
            case EMPATE:
                return "Empate: se devuelve la apuesta.";
            default:
                return partida.getManoJugador().sePaso()
                        ? "Derrota: te pasaste de 21."
                        : "Derrota: el computador ganó.";
        }
    }

    // Ventana

    private Stage getStage() {
        return (Stage) lblSaldo.getScene().getWindow();
    }

    private boolean confirmarCierre() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Cerrar juego");
        alerta.setHeaderText("¿Seguro que desea cerrar el juego?");
        alerta.setContentText("La partida actual se perderá.");
        return alerta.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }
}