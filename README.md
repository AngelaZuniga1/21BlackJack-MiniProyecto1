# 21 Black Jack · Duelo contra el computador

Mini proyecto 1 de **Fundamentos de Programación Orientada a Eventos (Grupo 01)**, Programa de Ingeniería de Sistemas, Universidad del Valle, sede Meléndez. Docente: Paola J. Rodriguez Carrillo. Octubre de 2026.

Aplicación de escritorio en **Java y JavaFX** en la que una persona juega 21 Black Jack contra el computador, apostando fichas ficticias, pidiendo cartas y plantándose.

## Integrantes

| Nombre | Código | Usuario de GitHub | Correo institucional |
|---|---|---|---|
| Angela Maria Zuñiga Obando | 2342147 | AngelaZuniga1 | zuniga.angela@correounivalle.edu.co |
| Nicolas Andres Ramirez Correa | 2415510 | Nicoramirezc | nicolas.andres.ramirez@correounivalle.edu.co |

## Requisitos

- JDK [versión, por ejemplo 17 o 21] o superior.
- Maven 3.8 o superior (o el wrapper del proyecto, si lo incluye).
- JavaFX [versión, la misma del `pom.xml`]. Maven la descarga como dependencia, no hace falta instalarla aparte.
- Sistema operativo con entorno gráfico (Windows, Linux o macOS).

## Cómo ejecutar

1. Clonar el repositorio:

   ```bash
   git clone https://github.com/AngelaZuniga1/21BlackJack-MiniProyecto1.git
   cd 21BlackJack-MiniProyecto1
   ```

2. Ejecutar con Maven:

   ```bash
   mvn clean javafx:run
   ```

También se puede abrir la carpeta en IntelliJ IDEA o Eclipse como proyecto Maven y ejecutar la clase principal BlackjackApplication.

## Reglas implementadas

- **Mazo:** 52 cartas sin comodines, mezclado al iniciar cada ronda; ninguna carta se reparte dos veces en la misma ronda.
- **Valores:** del 2 al 10 su número; J, Q y K valen 10; el As vale 1 u 11, el que más convenga sin pasarse de 21.
- **Apuestas:** saldo inicial de 1.000 fichas. La apuesta es un entero, múltiplo de 10, mínimo 10 y sin superar el saldo. Se descuenta al iniciar la ronda y no cambia durante ella.
- **Ronda:** el jugador recibe dos cartas y puede pedir o plantarse. El computador no tiene cartas hasta que el jugador se planta. Con 21 el turno del jugador termina solo; si se pasa de 21 pierde de inmediato.
- **Computador:** recibe dos cartas, pide mientras tenga menos de 17 y se planta con 17 o más (incluido un 17 con As como 11). Si se pasa de 21 pierde.
- **Jugadas especiales:** Blackjack (As y carta de valor 10 en las dos primeras cartas), Veintiuno (21 sin ser Blackjack) y Veinte y medio (figura y 4 en las dos primeras cartas, solo se avisa).
- **Pagos:** derrota, se pierde la apuesta; empate, se devuelve; victoria, apuesta más una igual (x2); victoria con Blackjack, apuesta más 1,5 veces su valor (x2,5). El pago se acredita una sola vez.
- **Interfaz:** botones Iniciar ronda, Pedir carta, Plantarse, Ver reglas y Cerrar juego, habilitados según el estado de la partida. Ver reglas conserva la partida y cerrar (botón o X de la ventana) pide confirmación.

## Arquitectura (MVC)

- **Modelo** (`co.edu.univalle.poe.mvc.model`): `Carta`, `Valor`, `Palo`, `Mazo` (arreglo de 52 posiciones), `Mano` (lista enlazada), `Partida` (saldo, apuestas, turnos y resultados), `EstadoRonda` y `ResultadoRonda`.
- **Vista:** archivo FXML con la zona del jugador, la del computador, puntajes, saldo, apuesta vigente y mensajes.
- **Controlador** (`co.edu.univalle.poe.mvc.controller`): `BlackjackController` atiende los eventos, llama al modelo y redibuja la vista.

Las imágenes de las cartas están en `src/main/resources/co/edu/univalle/poe/mvc/images/cartas/` y se nombran `valor_palo.png` (por ejemplo `as_corazones.png`, `dos_picas.png`, `j_treboles.png`).