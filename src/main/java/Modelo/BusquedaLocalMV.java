package main.java.Modelo;

import main.java.DataTypes.*;
import java.util.*;

public class BusquedaLocalMV extends Algoritmo {

    // Constructor de la clase
    public BusquedaLocalMV(List<Estacion> dataset){
        this.listaEstaciones = dataset;
    }

    // Método run() porque hereda
    @Override
    public void run() {

        // Vamos a trabajar con una copia del dataset, para que no se lie
        List<Estacion> datasetCopiado = Dataset.copiaDataset(this.listaEstaciones);


        for (int i = 0; i < 5; i++) {

            // Creamos el array en el que guardaremos las soluciones de los vecinos
            List<List<Estacion>> vecinos = new ArrayList<>(datasetCopiado.size());

            // Limpiamos para cada ejecución
            vecinos.clear();

            // Array para guardar las distancias de los distintos vecinos
            List<Double> funcionObjetivo = new ArrayList<>(datasetCopiado.size());

            // Reseteamos la mejor distancia
            this.mejorDistancia = Double.POSITIVE_INFINITY;

            // Para la semilla
            Random rand = new Random(this.semilla[i]);

            // Vamos a mezclarlo (a excepción de la primera estación) para nuestra solución inicial
            List<Estacion> mezclado = datasetCopiado.subList(1, this.listaEstaciones.size());

            // Mezclamos todas menos la primera
            Collections.shuffle(mezclado, rand);

            // Unimos de nuevo todo
            mezclado.addFirst(datasetCopiado.getFirst());

            // Hacemos una copia del dataset donde iremos guardando el mejor vecino hasta el momento
            List<Estacion> mejorVecino = Dataset.copiaDataset(mezclado);

            // "Número" de llamadas a la función objetivo
            int llamadas = 0;

            // Para comprobar si mejora o no
            int noMejorado = 0;

            // Una vez lo tenemos todo, vamos a proceder a la parte interesante del algoritmo
            while(llamadas < 3000 && noMejorado < 1000) {

                // Generamos 200 soluciones
                for (int j = 0; j < 200; j++) {
                    List<Estacion> vecino = inter.cambiar(Dataset.copiaDataset(mejorVecino));
                    double distVecino = distanciaManhattan.calculaCompleto(vecino);
                    funcionObjetivo.add(calcularFObjetivo(distVecino, vecino));
                    vecinos.add(vecino);
                }

                // Comprobamos cual es el mejor vecino encontrado hasta ahora y nos quedamos con el
                for (int c = 0; c < funcionObjetivo.size(); c++) {
                    if (funcionObjetivo.get(c) < this.mejorDistancia) {
                        this.mejorFuncionObjetivo = funcionObjetivo.get(c);
                        mejorVecino = Dataset.copiaDataset(vecinos.get(c));
                    }
                }
                llamadas++;
            }

            this.distanciaRecorrida = distanciaManhattan.calculaCompleto(mejorVecino);

            // Mostramos por pantalla la distancia calculada con cada una de las 5 semillas
            System.out.println("\n--- Resultado Búsqueda Local: Mejor Vecino ---");
            System.out.printf("Recorrido: ");
            for (Estacion e : recorrido) System.out.print(e.id + " ");
            System.out.println("-> 0");

            System.out.printf("Kilómetros recorridos : %.4f km%n", distanciaRecorrida);
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
