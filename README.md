# 21 Black Jack · Duelo contra el computador

Mini proyecto 1 de **Fundamentos de Programación Orientada a Eventos (Grupo 01)**, Programa de Ingeniería de Sistemas, Universidad del Valle, sede Meléndez. Docente: Paola J. Rodriguez Carrillo. Octubre de 2026.

Aplicación de escritorio en **Java y JavaFX** en la que una persona juega 21 Black Jack contra el computador, apostando fichas ficticias, pidiendo cartas y plantándose.

## Integrantes

| Nombre | Código | Usuario de GitHub | Correo institucional |
|---|---|---|---|
| Angela Maria Zuñiga Obando | 2342147 | AngelaZuniga1 | zuniga.angela@correounivalle.edu.co |
| Nicolas Andres Ramirez Correa | 2415510 | Nicoramirezc | nicolas.andres.ramirez@correounivalle.edu.co |

---

## 1. Qué necesitas instalar

| Herramienta | ¿Obligatorio? | Para qué |
|---|---|---|
| **IntelliJ IDEA** (Community) | Sí (recomendado) | Editor principal: Java + Maven + JavaFX integrados |
| **JDK 17** | Solo si usas VS Code | IntelliJ lo descarga automáticamente |
| **Maven** | No | El proyecto trae el wrapper `mvnw` / `mvnw.cmd` |
| **JavaFX** | No | Maven la descarga (21.0.6) |

---

## 2. Maven y JavaFX (no hace falta instalarlos)

- **Maven:** usa el wrapper del proyecto.
  - Windows: `.\mvnw.cmd`
  - Linux/macOS: `./mvnw`
- **JavaFX:** no se instala; el `pom.xml` la descarga automáticamente.

---

## 3. Importar el proyecto en IntelliJ (desde la UI)

1. En la pantalla de bienvenida, clic en **Get from VCS** (o `File → New → Project from Version Control...`).
2. Pega la URL del repositorio: `https://github.com/AngelaZuniga1/21BlackJack-MiniProyecto1.git`
3. Elige una carpeta destino y clic en **Clone**.
4. IntelliJ abre el proyecto como **Maven** automáticamente (espera a que importe las dependencias).

> Alternativa (por consola o VS Code): `git clone https://github.com/AngelaZuniga1/21BlackJack-MiniProyecto1.git` o descargar el ZIP desde GitHub.

---

## 4. Configurar el JDK 17

### IntelliJ IDEA (automático)

No necesitas hacer nada: IntelliJ detecta o descarga el **JDK 17** al abrir el proyecto.

> Para verificarlo o cambiarlo: `File → Project Structure... → Project` → en **SDK** debe estar **17**.

### Solo si usas VS Code

En VS Code sí hay que instalar y configurar el JDK 17 a mano.

**1. Instala Amazon Corretto 17 (PowerShell):**

```powershell
winget install Amazon.Corretto.17
```

> Alternativa: descargar el `.msi` desde <https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html>.

**2. Instala la extensión de Java:**

1. `Ctrl + Shift + X` → busca **Extension Pack for Java** (Microsoft) → **Install**.

**3. Apunta VS Code al JDK 17:**

1. `Ctrl + Shift + P` → ejecuta **`Java: Configure Java Runtime`**.
2. En **JDK for the project**, elige tu Corretto 17.

O fíjalo en `.vscode/settings.json`:

```json
{
  "java.configuration.runtimes": [
    {
      "name": "JavaSE-17",
      "path": "C:\\Program Files\\Amazon Corretto\\jdk17.0.20_12",
      "default": true
    }
  ]
}
```

**4. Verifica:**

```powershell
java -version
.\mvnw.cmd -version
```

Debe mostrar `Java version: 17...`.

---

## 5. Ejecutar el programa

### IntelliJ IDEA (más rápido)

1. Abre `src/main/java/co/edu/univalle/poe/mvc/Launcher.java`.
2. Clic en el **▶️ verde** junto a `main` → **Run 'Launcher.main()'**.

> Si aparece `JavaFX runtime components are missing`: abre el panel **Maven** (icono **M**) → **Plugins → javafx → `javafx:run`**.

### VS Code

1. `File → Open Folder...` → selecciona la carpeta.
2. Abre `Launcher.java` → clic en **Run** sobre `main`.

> Si aparece `JavaFX runtime components are missing`: abre el panel **Maven** (icono **M**) → **Plugins → javafx → `javafx:run`**.

### Por consola (último)

Windows:

```powershell
.\mvnw.cmd clean javafx:run
```

Linux / macOS:

```bash
./mvnw clean javafx:run
```

> Usa **`javafx:run`** (no `java -jar`): así Maven configura el *module-path* de JavaFX.

---

## 6. Ejecutar las pruebas

Son **14 pruebas** con JUnit 5 (`CartaTest`, `MazoTest`, `ManoTest`, `PartidaTest`).

### IntelliJ IDEA (más rápido)

- Abre `src/test/java/co/edu/univalle/poe/mvc/model/PartidaTest.java` → **▶️** junto a la clase → **Run 'PartidaTest'**.
- O clic derecho en `src/test/java` → **Run 'All Tests'**.

**Si no funciona con los botones ▶️:**
1. Ve a la barra lateral **derecha** y haz clic en el icono **Maven** (una **M**).
2. Expande **Lifecycle**.
3. Doble clic en **`test`**.

> Si aparece `IllegalAccessError`: `Settings → Build, Execution, Deployment → Build Tools → Maven → Runner` → ✅ **Delegate IDE build/run actions to Maven**.

### VS Code

- Barra lateral → **Testing** (probeta) → **Run All Tests**.
- O **Run Test** encima de la clase o del método.

> Si falla por módulos: panel **Maven** (icono **M**) → **Lifecycle → `test`**.

### Por consola (último)

Windows:

```powershell
.\mvnw.cmd test
```

Linux / macOS:

```bash
./mvnw test
```

Resultado esperado:

```
Tests run: 14, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

Para una sola clase:

```powershell
.\mvnw.cmd test -Dtest=PartidaTest
```

---

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
