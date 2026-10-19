package co.edu.univalle.poe.mvc.model;

public class Carta {
    private Valor valor;
    private Palo palo;

    public Carta(Valor valor, Palo palo) {
        this.valor = valor;
        this.palo = palo;
    }

    public Valor getValor() {
        return valor;
    }

    public Palo getPalo() {
        return palo;
    }

    public int getPuntos() {
        return valor.getPuntos();
    }

    @Override
    public String toString() {
        return valor + " de " + palo; // de vuelve un texto mas facil de leer, por ejemplo: "AS de CORAZONES"
    }

}
