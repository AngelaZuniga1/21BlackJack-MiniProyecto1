package co.edu.univalle.poe.mvc.model;

public class Carta {

    // Atributos
    private Valor valor;
    private Palo palo;

    // Constructor

    public Carta(Valor valor, Palo palo) {
        this.valor = valor;
        this.palo = palo;
    }

    // Métodos

    //getters

    public Valor getValor() {
        return valor;
    }

    public Palo getPalo() {
        return palo;
    }

    public int getPuntos() {
        return valor.getPuntos();
    }

    //toString

    @Override
    public String toString() {
        return valor + " de " + palo; // de vuelve un texto mas facil de leer, por ejemplo: "AS de CORAZONES"
    }

}
