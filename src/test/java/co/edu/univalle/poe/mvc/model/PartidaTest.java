package co.edu.univalle.poe.mvc.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PartidaTest {

    private Partida partida;

    @BeforeEach
    void setUp() {
        partida = new Partida();
    }

    @Test
    @DisplayName("Estado inicial de la partida")
    void testEstadoInicial() {
        assertEquals(1000, partida.getSaldo());
        assertEquals(0, partida.getApuestaVigente());
        assertFalse(partida.esTurnoJugador());
        assertNull(partida.getUltimoResultado());
    }

    @Test
    @DisplayName("Validación de apuestas inválidas")
    void testValidacionApuestas() {
        assertNotNull(partida.motivoApuestaInvalida(5));
        assertNotNull(partida.motivoApuestaInvalida(15));
        assertNotNull(partida.motivoApuestaInvalida(1200));
        assertNull(partida.motivoApuestaInvalida(100));
    }

    @Test
    @DisplayName("Iniciar ronda descuenta el saldo y otorga cartas iniciales")
    void testIniciarRonda() {
        String error = partida.iniciarRonda(100);
        assertNull(error);
        assertEquals(2, partida.getManoJugador().getCantidadCartas());
        
        if (partida.getManoJugador().esBlackjack()) {
            assertEquals(0, partida.getApuestaVigente());
            assertFalse(partida.esTurnoJugador());
        } else {
            assertEquals(900, partida.getSaldo());
            assertEquals(100, partida.getApuestaVigente());
            assertTrue(partida.getManoComputador().estaVacia());
            assertTrue(partida.esTurnoJugador());
        }
    }
}