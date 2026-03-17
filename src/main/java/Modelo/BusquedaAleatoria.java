package main.java.Modelo;

import main.java.DataTypes.*;
import java.util.*;

public class BusquedaAleatoria extends Algoritmo {

    public BusquedaAleatoria(List<Estacion> dataset) {
        this.listaEstaciones = dataset;
    }

    public void run(){

        for (int i = 0; i < 5; i++) {
            // Generamos la semilla que vamos a utilizar
            Random rand = new Random(this.semilla[i]);

            // Hacemos una copia del dataset que es la que vamos a utlizar para la mezcla
            List<Estacion> copiaLista = this.listaEstaciones;

            // Definimos la distancia a 0 ara cada iteración de cada semilla
            this.distanciaRecorrida = 0;

            for (int j = 0; j < 100; j++) {
                // Generamos la copia "remezclada" de nuestro dataset
                Collections.shuffle(copiaLista, rand);
                // Definimos la distancia a 0 ara cada iteración de cada semilla
                this.distanciaRecorrida = 0;

                // Aquí vamos a calcular la distancia entre puntos de cada una de las 100 mezclas
                for (int k = 0; k < copiaLista.size()-1; k++) {
                    this.distanciaRecorrida += distanciaManhattan.calculaDistancia(copiaLista.get(k), copiaLista.get(k+1));
                }

                // Calculamos la vuelta
                this.distanciaRecorrida += distanciaManhattan.calculaDistancia(copiaLista.getLast(), copiaLista.getFirst());
            }

            // Devolvemos la mejor distancia de la semilla que estemos ejecutando
            System.out.println(this.distanciaRecorrida);
        }
    }

}
