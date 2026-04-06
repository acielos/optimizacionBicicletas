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

            // Reseteamos variables
            double mejorFO = Double.POSITIVE_INFINITY;
            double mejorE = 0.0;

            this.recorrido.clear();

            // Hacemos una copia del dataset que es la que vamos a utlizar para la mezcla
            List<Estacion> copiaLista = Dataset.copiaDataset(this.listaEstaciones);

            // Definimos la distancia a 0 ara cada iteración de cada semilla
            this.distanciaRecorrida = 0;

            for (int j = 0; j < 100; j++) {
                // Generamos la copia "remezclada" de nuestro dataset
                Collections.shuffle(copiaLista, rand);
                // Definimos la distancia a 0 ara cada iteración de cada semilla
                this.distanciaRecorrida = 0;

                // Aquí vamos a calcular la distancia entre puntos de cada una de las 100 mezclas
                this.distanciaRecorrida = distanciaManhattan.calculaCompleto(copiaLista);

                // Vamos ahora a realizar el equilibrado de las estaciones siempre que sea posible
                for (Estacion estacion : this.recorrido) {
                    equilibrarEstacion(estacion);
                }

                double entropia = calcularEntropiaTotal(this.recorrido);
                double funObjetivo = calcularFObjetivo(this.distanciaRecorrida, this.recorrido);

                if (funObjetivo < mejorFO) {
                    mejorFO = funObjetivo;
                    this.recorrido = copiaLista;
                }

            }

            System.out.println("\n--- Resultado Búsqueda Aleatoria ---");
            System.out.printf("Recorrido: ");
            for (Estacion e : recorrido) System.out.print(e.id + " ");
            System.out.println("-> 0");

            System.out.printf("Kilómetros recorridos : %.4f km%n", distanciaRecorrida);
            System.out.printf("Función objetivo      : %.4f%n", mejorFO);
            System.out.printf("Evaluaciones          : %d%n", numEvaluaciones);

            System.out.println("\nEstado final de las estaciones:");
            System.out.printf("%-6s %-10s %-10s %-8s%n", "ID", "Carga", "Capacidad", "% ocup.");
            for (Estacion e : recorrido) {
                double pct = 100.0 * e.carga / e.capacidad;
                System.out.printf("%-6d %-10d %-10d %.1f%%%n", e.id, e.carga, e.capacidad, pct);
            }
            System.out.printf("%nCarga final del camión: %d/%d bicis%n", camion.carga, camion.getCapacidad());
        }
    }
}
