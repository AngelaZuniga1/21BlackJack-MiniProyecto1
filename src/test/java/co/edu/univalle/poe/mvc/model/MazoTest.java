package co.edu.univalle.poe.mvc.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MazoTest {

    private Mazo mazo;

    @BeforeEach
    void setUp() {
        mazo = new Mazo();
    }

    @Test
    @DisplayName("El mazo inicial contiene 52 cartas sin repetir")
    void testMazoInicial() {
        assertEquals(52, mazo.getCartasRestantes());
        assertTrue(mazo.hayCartas());

        Set<String> cartasUnicas = new HashSet<>();
        for (Carta c : mazo.getCartas()) {
            cartasUnicas.add(c.toString());
        }
        assertEquals(52, cartasUnicas.size());
    }

    @Test
    @DisplayName("Repartir reduce la cantidad de cartas disponibles")
    void testRepartirCarta() {
        Carta c1 = mazo.repartirCarta();
        assertNotNull(c1);
        assertEquals(51, mazo.getCartasRestantes());

        for (int i = 0; i < 51; i++) {
            mazo.repartirCarta();
        }
        assertEquals(0, mazo.getCartasRestantes());
        assertFalse(mazo.hayCartas());
        assertNull(mazo.repartirCarta());
    }

    @Test
    @DisplayName("Barajar reinicia la posición del mazo")
    void testBarajar() {
        for (int i = 0; i < 10; i++) {
            mazo.repartirCarta();
        }
        assertEquals(42, mazo.getCartasRestantes());

        mazo.barajar();
        assertEquals(52, mazo.getCartasRestantes());
        assertTrue(mazo.hayCartas());
    }
}