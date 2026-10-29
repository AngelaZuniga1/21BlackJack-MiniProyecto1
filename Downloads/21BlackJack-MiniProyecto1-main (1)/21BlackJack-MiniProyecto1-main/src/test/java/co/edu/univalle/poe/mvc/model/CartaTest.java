package co.edu.univalle.poe.mvc.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartaTest {

    @Test
    @DisplayName("Retorna los atributos de valor, palo y puntos correctamente")
    void testAtributosCarta() {
        Carta carta1 = new Carta(Valor.AS, Palo.CORAZONES);
        Carta carta2 = new Carta(Valor.K, Palo.PICAS);

        assertEquals(Valor.AS, carta1.getValor());
        assertEquals(Palo.CORAZONES, carta1.getPalo());
        assertEquals(1, carta1.getPuntos());

        assertEquals(Valor.K, carta2.getValor());
        assertEquals(Palo.PICAS, carta2.getPalo());
        assertEquals(10, carta2.getPuntos());
    }

    @Test
    @DisplayName("Debe generar la representación en texto adecuada")
    void testToString() {
        Carta carta = new Carta(Valor.Q, Palo.DIAMANTES);
        assertEquals("Q de DIAMANTES", carta.toString());
    }
}