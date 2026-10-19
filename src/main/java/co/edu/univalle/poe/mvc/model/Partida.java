package co.edu.univalle.poe.mvc.model;

public class Partida {

    // Atributos

    private int saldo;
    private int apuestaVigente;
    private EstadoRonda estado;
    private Mazo mazo;

   /*  private Mano manoJugador; agregar cunado se cree la clase mano
    private Mano manoComputador;*/

    // Constructor

    public Partida() {
        this.saldo = 1000;
        this.apuestaVigente = 0;
        this.estado = EstadoRonda.ESPERANDO_APUESTA;
        this.mazo = new Mazo();
    }

    // Métodos


    //Gestión de Saldo y Apuestas
    public boolean validarApuesta(int apuesta) {
        return apuesta >= 10
                && apuesta % 10 == 0
                && apuesta <= saldo;
    }

    
    public void iniciarRonda(int apuesta) {
        if (estado == EstadoRonda.TURNO_JUGADOR
                || estado == EstadoRonda.TURNO_COMPUTADOR) {
            throw new IllegalStateException("Ya hay una ronda en curso.");
        }

        if (!validarApuesta(apuesta)) {
            throw new IllegalArgumentException("Apuesta inválida.");
        }

        saldo -= apuesta;
        apuestaVigente = apuesta;
        mazo.barajar(); // mazo completo y mezclado para la nueva ronda
        /*manoJugador = new Mano();
        manoComputador = new Mano();
        manoJugador.agregarCarta(mazo.repartirCarta());
        manoJugador.agregarCarta(mazo.repartirCarta());
        manoComputador.agregarCarta(mazo.repartirCarta());
        manoComputador.agregarCarta(mazo.repartirCarta());
        */
        estado = EstadoRonda.TURNO_JUGADOR;
    }

    public void finalizarRonda(ResultadoRonda resultado) {
        if (estado != EstadoRonda.TURNO_JUGADOR
                && estado != EstadoRonda.TURNO_COMPUTADOR) {
            throw new IllegalStateException("No hay ronda activa para finalizar.");
        }

        switch (resultado) {
            case EMPATE:
                saldo += apuestaVigente;
                break;
            case VICTORIA:
                saldo += apuestaVigente * 2;
                break;
            case VICTORIA_BLACKJACK:
                saldo += apuestaVigente * 5 / 2; // devuelve apuesta + 1.5
                break;
            case DERROTA:
                // no se acredita nada
                break;
        }

        apuestaVigente = 0;
        estado = EstadoRonda.RONDA_TERMINADA;
    }

    // Getters

    public int getSaldo() {
        return saldo;
    }

    public int getApuestaVigente() {
        return apuestaVigente;
    }

    public EstadoRonda getEstado() {
        return estado;
    }

    public Mazo getMazo() {
        return mazo;
    }

    // Setters

    public void setEstado(EstadoRonda estado) {
        this.estado = estado;
    }
}
