package co.edu.univalle.poe.mvc.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Mano {

    // Constantes

    public static final int OBJETIVO = 21;
    public static final int PUNTAJE_VEINTE_Y_MEDIO = 14;
    public static final int BONO_AS = 10;

    // false = cualquier par de cartas que sume 14; true = solo figura + 4.
    public static final boolean SOLO_FIGURA_Y_CUATRO = true;

    // Atributos

    private LinkedList<Carta> cartas; // la mano es una lista enlazada

    // Constructor

    public Mano() {
        cartas = new LinkedList<>();
    }

    // Métodos

    public void agregarCarta(Carta carta) {
        if (carta == null) {
            throw new IllegalArgumentException("No se puede agregar una carta nula.");
        }
        cartas.add(carta);
    }

    // Mayor puntaje posible sin pasarse: los ases cuentan 1 y, si cabe, UN solo as sube a 11
    // (dos ases como 11 ya darían 22).
    public int getPuntaje() {
        int suma = 0;
        boolean hayAs = false;
        for (Carta carta : cartas) {
            suma += carta.getPuntos();
            if (carta.getValor() == Valor.AS) {
                hayAs = true;
            }
        }
        if (hayAs && suma + BONO_AS <= OBJETIVO) {
            suma += BONO_AS;
        }
        return suma;
    }

    public boolean sePaso() {
        return getPuntaje() > OBJETIVO;
    }

    // Dos cartas iniciales que suman 21 (as + una carta que vale 10)
    public boolean esBlackjack() {
        return cartas.size() == 2 && getPuntaje() == OBJETIVO;
    }

    // 21 que no es Blackjack, por ejemplo 7 + 7 + 7
    public boolean esVeintiuno() {
        return getPuntaje() == OBJETIVO && !esBlackjack();
    }

    // Solo se reconoce con las dos primeras cartas: al pedir una tercera deja de serlo
    public boolean esVeinteYMedio() {
        if (cartas.size() != 2 || getPuntaje() != PUNTAJE_VEINTE_Y_MEDIO) {
            return false;
        }
        if (!SOLO_FIGURA_Y_CUATRO) {
            return true;
        }
        Carta a = cartas.get(0);
        Carta b = cartas.get(1);
        return (esFigura(a) && b.getValor() == Valor.CUATRO)
                || (esFigura(b) && a.getValor() == Valor.CUATRO);
    }

    private boolean esFigura(Carta carta) {
        Valor valor = carta.getValor();
        return valor == Valor.J || valor == Valor.Q || valor == Valor.K;
    }

    public void limpiar() {
        cartas.clear();
    }

    // Getters

    public List<Carta> getCartas() {
        return Collections.unmodifiableList(cartas);
    }

    public int getCantidadCartas() {
        return cartas.size();
    }

    public boolean estaVacia() {
        return cartas.isEmpty();
    }
}
