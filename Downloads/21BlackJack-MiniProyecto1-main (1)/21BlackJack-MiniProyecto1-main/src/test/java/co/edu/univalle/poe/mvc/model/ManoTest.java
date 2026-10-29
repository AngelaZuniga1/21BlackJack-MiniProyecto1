package co.edu.univalle.poe.mvc.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManoTest {

    private Mano mano;

    @BeforeEach
    void setUp() {
        mano = new Mano();
    }

    @Test
    @DisplayName("Cálculo de puntaje sin Ases")
    void testPuntajeSinAs() {
        mano.agregarCarta(new Carta(Valor.SIETE, Palo.CORAZONES));
        mano.agregarCarta(new Carta(Valor.OCHO, Palo.TREBOLES));
        assertEquals(15, mano.getPuntaje());
        assertFalse(mano.sePaso());
    }

    @Test
    @DisplayName("El As vale 11 si no sobrepasa 21")
    void testAsComoOnce() {
        mano.agregarCarta(new Carta(Valor.AS, Palo.PICAS));
        mano.agregarCarta(new Carta(Valor.NUEVE, Palo.DIAMANTES));
        assertEquals(20, mano.getPuntaje());
    }

    @Test
    @DisplayName("El As se reduce a 1 si sumar 11 haría volar la mano")
    void testAsAjusteAUno() {
        mano.agregarCarta(new Carta(Valor.AS, Palo.PICAS));
        mano.agregarCarta(new Carta(Valor.NUEVE, Palo.DIAMANTES));
        mano.agregarCarta(new Carta(Valor.CINCO, Palo.CORAZONES));
        assertEquals(15, mano.getPuntaje());
        assertFalse(mano.sePaso());
    }

    @Test
    @DisplayName("Detecta Blackjack inicial (As + J/Q/K o 10 con 2 cartas)")
    void testEsBlackjack() {
        mano.agregarCarta(new Carta(Valor.AS, Palo.PICAS));
        mano.agregarCarta(new Carta(Valor.J, Palo.CORAZONES));
        assertTrue(mano.esBlackjack());
        assertFalse(mano.esVeintiuno());
    }

    @Test
    @DisplayName("Detecta Veinte y medio (Figura + 4 con 2 cartas)")
    void testEsVeinteYMedio() {
        mano.agregarCarta(new Carta(Valor.K, Palo.TREBOLES));
        mano.agregarCarta(new Carta(Valor.CUATRO, Palo.CORAZONES));
        assertEquals(14, mano.getPuntaje());
        assertTrue(mano.esVeinteYMedio());
    }

    @Test
    @DisplayName("Verifica si la mano supera los 21 puntos")
    void testSePaso() {
        mano.agregarCarta(new Carta(Valor.K, Palo.CORAZONES));
        mano.agregarCarta(new Carta(Valor.Q, Palo.PICAS));
        mano.agregarCarta(new Carta(Valor.DOS, Palo.DIAMANTES));
        assertEquals(22, mano.getPuntaje());
        assertTrue(mano.sePaso());
    }
}