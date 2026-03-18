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

        // Definimos que SIEMPRE comenzaremos por la primera estación, pase lo que pase
        int estInicial = 0;
        int estActual = 0;  // Porque siempre empezaremos por la 0

        // Como comenzamos por la estacion 0, vamos a marcar su distancia como infinito para que no volvamos
        // a visitarla en un futuro
        for (int i = 0; i < this.listaEstaciones.size(); i++) {
            this.distancias[i][estInicial] = Double.POSITIVE_INFINITY;
        }

        // Limpiamos por si nos ad errores o algo
        this.recorrido.clear();

        // Añadimos al recorrido del camión que ya hemos visitado la primera estación
        this.recorrido.add(this.listaEstaciones.get(estInicial));

        // Nuestro dataset será siempre de 16 estaciones, pero por si a caso en el
        // futuro lo aumentamos, jugaremos con el zise; También tendremos un contador
        // para que nos cuente cuántas estaciones hemos recorrido
        int visitadas = 1; // -> No es igual a 0 porque la primera estación ya la hemos visitado

        while (visitadas < this.listaEstaciones.size()) {

            for (int i = 0; i < this.listaEstaciones.size(); i++) {
                if (this.distancias[estActual][i] < this.mejorDistancia) {
                    this.mejorDistancia = distancias[estActual][i];
                    estActual = i;
                }
            }
            visitadas++;

            // Añadimos la ciudad que vamos a visitar al camino
            this.recorrido.add(this.listaEstaciones.get(estActual));

            // Añadimos la distancia recorrida para guardar
            this.distanciaRecorrida += mejorDistancia;

            // "Recalculamos" la distancia a la ciudad actual
            for (int i = 0; i < this.listaEstaciones.size(); i++) {
                this.distancias[i][estActual] = Double.POSITIVE_INFINITY;
            }
        }

        // Añadimos la distancia desde la ultima estaci´on hasta la primera, que tendrá que volver
        this.distanciaRecorrida += distanciaManhattan.calculaDistancia(this.recorrido.getLast(),  this.recorrido.getFirst());
    };
}
