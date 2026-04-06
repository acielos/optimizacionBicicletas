package main.java.Modelo;

import main.java.DataTypes.*;
import java.util.*;

public class BusquedaAleatoria extends Algoritmo {

    public BusquedaAleatoria(List<Estacion> dataset) {
        this.listaEstaciones = dataset;
    }

    public void run(){

        List<Estacion> lista1n = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            // Generamos la semilla que vamos a utilizar
            Random rand = new Random(this.semilla[i]);

            // Reseteamos variables
            this.mejorFuncionObjetivo = Double.POSITIVE_INFINITY;

            this.recorrido.clear();

            // Hacemos una copia del dataset que es la que vamos a utlizar para la mezcla
            List<Estacion> copiaLista = Dataset.copiaDataset(this.listaEstaciones);

            // Definimos la distancia a 0 ara cada iteración de cada semilla
            this.distanciaRecorrida = 0;

            for (int j = 0; j < 100; j++) {
                lista1n.clear();
                lista1n = Dataset.copiaDataset(copiaLista.subList(1, copiaLista.size()));

                // Generamos la copia "remezclada" de nuestro dataset
                Collections.shuffle(lista1n, rand);

                lista1n.addFirst(copiaLista.getFirst());

                this.recorrido = Dataset.copiaDataset(lista1n);

                // Definimos la distancia a 0 ara cada iteración de cada semilla
                this.distanciaRecorrida = 0;

                // Aquí vamos a calcular la distancia entre puntos de cada una de las 100 mezclas
                this.distanciaRecorrida = distanciaManhattan.calculaCompleto(lista1n);

                // Vamos ahora a realizar el equilibrado de las estaciones siempre que sea posible
                for (int k = 0; k < this.recorrido.size(); k++) {
                    equilibrarEstacion(this.recorrido.get(k));
                }

                // double entropia = calcularEntropiaTotal(this.recorrido);
                double funObjetivo = calcularFObjetivo(this.distanciaRecorrida, this.recorrido);

                if (funObjetivo < this.mejorFuncionObjetivo) {
                    this.mejorFuncionObjetivo = funObjetivo;
                    this.recorrido = lista1n;
                    this.distanciaRecorrida = distanciaManhattan.calculaCompleto(lista1n);
                }

            }



            System.out.println("\n--- Resultado Búsqueda Aleatoria ---");
            System.out.printf("Recorrido: ");
            for (Estacion e : recorrido) System.out.print(e.id + " ");
            System.out.println("-> 0");

            System.out.printf("Kilómetros recorridos : %.4f km%n", this.distanciaRecorrida);
            System.out.printf("Función objetivo      : %.4f%n", this.mejorFuncionObjetivo);
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
