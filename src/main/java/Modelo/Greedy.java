package main.java.Modelo;

import main.java.DataTypes.*;
import java.util.*;

public class Greedy extends Algoritmo {

    public Greedy(List<Estacion> dataset) {
        this.listaEstaciones = dataset;
    }

    public void run(){
        // Creamos la matriz para guardar las distancias
        this.distancias = new Double[this.listaEstaciones.size()][this.listaEstaciones.size()];

        // Calculamos las distancias
        calcularDistancias();

        // Creamos un nuevo objeto de CAMINO
        Camion camion = new Camion();

        // Definimos que SIEMPRE comenzaremos por la primera estación, pase lo que pase
        int estInicial = 0;

        // Como comenzamos por la estacion 0, vamos a marcar su distancia como infinito para que no volvamos
        // a visitarla en un futuro
        for (int i = 0; i < this.listaEstaciones.size(); i++) {
            distancias[i][estInicial] = Double.POSITIVE_INFINITY;
        }

        // Añadimos al recorrido del camión que ya hemos visitado la primera estación
        camion.recorrido.add(this.listaEstaciones.get(estInicial));

        // Nuestro dataset será siempre de 16 estaciones, pero por si a caso en el
        // futuro lo aumentamos, jugaremos con el zise; También tendremos un contador
        // para que nos cuente cuántas estaciones hemos recorrido
        int visitadas = 1; // -> No es igual a 0 porque la primera estación ya la hemos visitado

        while (visitadas < this.listaEstaciones.size()) {

            visitadas++;
        }

    };
}
