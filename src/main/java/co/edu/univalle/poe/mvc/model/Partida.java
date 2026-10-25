package co.edu.univalle.poe.mvc.model;

public class Partida {

    // Constantes

    public static final int SALDO_INICIAL = 1000;
    public static final int APUESTA_MINIMA = 10;
    public static final int PLANTARSE_COMPUTADOR = 17;

    // Atributos

    private final Mazo mazo = new Mazo();
    private final Mano manoJugador = new Mano();
    private final Mano manoComputador = new Mano();
    private int saldo = SALDO_INICIAL;
    private int apuestaVigente = 0;
    private EstadoRonda estado = EstadoRonda.ESPERANDO_APUESTA;
    private ResultadoRonda ultimoResultado = null;

    // Apuestas

    // Devuelve el motivo si la apuesta es inválida, o null si es válida
    public String motivoApuestaInvalida(int apuesta) {
        if (apuesta < APUESTA_MINIMA) {
            return "La apuesta mínima es " + APUESTA_MINIMA + ".";
        }
        if (apuesta % APUESTA_MINIMA != 0) {
            return "La apuesta debe ser múltiplo de " + APUESTA_MINIMA + ".";
        }
        if (apuesta > saldo) {
            return "Saldo insuficiente.";
        }
        return null;
    }

    // Ronda

    // Inicia la ronda; devuelve el motivo si no se pudo, o null si todo salió bien
    public String iniciarRonda(int apuesta) {
        if (esTurnoJugador()) {
            return "Termine la ronda actual antes de iniciar otra.";
        }
        String motivo = motivoApuestaInvalida(apuesta);
        if (motivo != null) {
            return motivo;
        }

        saldo -= apuesta;
        apuestaVigente = apuesta;
        ultimoResultado = null;
        mazo.barajar();
        manoJugador.limpiar();
        manoComputador.limpiar();
        manoJugador.agregarCarta(mazo.repartirCarta());
        manoJugador.agregarCarta(mazo.repartirCarta());
        estado = EstadoRonda.TURNO_JUGADOR;

        plantarseSiLlegoA21();
        return null;
    }

    // El jugador pide carta: si se pasa pierde, y si llega a 21 se planta solo
    public void pedirCartaJugador() {
        if (!esTurnoJugador()) {
            return;
        }
        manoJugador.agregarCarta(mazo.repartirCarta());

        if (manoJugador.sePaso()) {
            finalizarRonda(ResultadoRonda.DERROTA);
        } else {
            plantarseSiLlegoA21();
        }
    }

    // El jugador se planta: juega el computador y se cierra la ronda
    public void plantarse() {
        if (!esTurnoJugador()) {
            return;
        }
        jugarTurnoComputador();
        finalizarRonda(calcularResultado());
    }

    // Turnos

    public boolean esTurnoJugador() {
        return estado == EstadoRonda.TURNO_JUGADOR;
    }

    // Con 21 (incluido Blackjack) el turno del jugador termina solo
    private void plantarseSiLlegoA21() {
        if (manoJugador.getPuntaje() == Mano.OBJETIVO) {
            plantarse();
        }
    }

    // El computador recibe dos cartas y pide mientras tenga menos de 17
    private void jugarTurnoComputador() {
        estado = EstadoRonda.TURNO_COMPUTADOR;
        manoComputador.agregarCarta(mazo.repartirCarta());
        manoComputador.agregarCarta(mazo.repartirCarta());

        while (manoComputador.getPuntaje() < PLANTARSE_COMPUTADOR) {
            manoComputador.agregarCarta(mazo.repartirCarta());
        }
    }

    // Resultado (el jugador nunca llega aquí pasado de 21)

    private ResultadoRonda calcularResultado() {
        if (manoComputador.sePaso()) {
            return manoJugador.esBlackjack() ? ResultadoRonda.VICTORIA_BLACKJACK : ResultadoRonda.VICTORIA;
        }
        if (manoJugador.esBlackjack()) {
            return manoComputador.esBlackjack() ? ResultadoRonda.EMPATE : ResultadoRonda.VICTORIA_BLACKJACK;
        }
        if (manoComputador.esBlackjack()) {
            return ResultadoRonda.DERROTA;
        }
        if (manoJugador.getPuntaje() > manoComputador.getPuntaje()) {
            return ResultadoRonda.VICTORIA;
        }
        if (manoJugador.getPuntaje() == manoComputador.getPuntaje()) {
            return ResultadoRonda.EMPATE;
        }
        return ResultadoRonda.DERROTA;
    }

    // Acredita el pago una sola vez y cierra la ronda
    private void finalizarRonda(ResultadoRonda resultado) {
        switch (resultado) {
            case EMPATE:
                saldo += apuestaVigente;
                break;
            case VICTORIA:
                saldo += apuestaVigente * 2;
                break;
            case VICTORIA_BLACKJACK:
                saldo += apuestaVigente * 5 / 2;
                break;
            default: // DERROTA: no se acredita nada
                break;
        }
        apuestaVigente = 0;
        ultimoResultado = resultado;
        estado = EstadoRonda.RONDA_TERMINADA;
    }

    // Getters

    public int getSaldo() {
        return saldo;
    }

    public int getApuestaVigente() {
        return apuestaVigente;
    }

    public Mano getManoJugador() {
        return manoJugador;
    }

    public Mano getManoComputador() {
        return manoComputador;
    }

    public ResultadoRonda getUltimoResultado() {
        return ultimoResultado;
    }
}