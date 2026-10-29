package co.edu.univalle.poe.mvc.model;

public class Mazo {

    // Atributos

    private Carta[] cartas;
    private int siguienteCarta;

    // Constructor

    public Mazo() {
        cartas = new Carta[52]; //crea el array de cartas con 52 elementos
        int index = 0;
        for (Palo palo : Palo.values()) { 
            for (Valor valor : Valor.values()) {
                cartas[index++] = new Carta(valor, palo); // agrega una nueva carta al array de cartas
            }
        }
    }

    // Métodos

    public void barajar() {
        siguienteCarta = 0;
        for (int i = 0; i < cartas.length; i++) {
            int j = (int) (Math.random() * cartas.length);
            Carta temp = cartas[i]; // guarda la carta en la posición i en una variable temporal
            cartas[i] = cartas[j]; // reemplaza la carta en la posición i con la carta en la posición j (numero aleatorio)
            cartas[j] = temp; // reemplaza la carta en la posición j con la carta temporal
        }
    }

    public Carta repartirCarta() {
        if (siguienteCarta < cartas.length) {
            return cartas[siguienteCarta++];
        } else {
            return null;
        }
    }

    public boolean hayCartas() {
        return siguienteCarta < cartas.length;
    }


    //getters

     public Carta[] getCartas() {
        return cartas;
    }

    public int getCartasRestantes() {
        return cartas.length - siguienteCarta;
    }
}
