package co.edu.univalle.poe.mvc.model;

public enum Valor {
    AS(1), DOS(2), TRES(3), CUATRO(4), CINCO(5), SEIS(6), SIETE(7),
    OCHO(8), NUEVE(9), DIEZ(10), J(10), Q(10), K(10);

    private final int puntos; // el As vale 1 aquí; Mano decide si cuenta como 11

    Valor(int puntos) {
        this.puntos = puntos;
    }

    public int getPuntos() {
        return puntos;
    }
}