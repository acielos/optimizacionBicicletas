package main.java.Modelo;

import main.java.DataTypes.Dataset;
import main.java.DataTypes.Estacion;
import java.util.*;

public class BusquedaLocalPM extends Algoritmo{

    public BusquedaLocalPM(List<Estacion> dataset){
        this.listaEstaciones = dataset;
    }

    public void run(){
        // Vamos a trabajar con una copia del dataset, para que no se lie
        List<Estacion> datasetCopiado = Dataset.copiaDataset(this.listaEstaciones);

        for (int i = 0; i < 5; i++) {

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
            int mejorado = 0;

            // Una vez lo tenemos todo, vamos a proceder a la parte interesante del algoritmo
            while(llamadas < 3000 && mejorado < 5) {

                // Reseteamos la distancia
                double coste = distanciaManhattan.calculaCompleto(mezclado);

                // Comprobamos si es mejor la distancia que la que teníamos hasta el momento
                if (coste <= this.mejorDistancia) {
                    this.mejorDistancia = coste;
                    mejorado++;
                    mejorVecino = mezclado;
                }

                // Cambiamos dos posiciones
                //mezclado = inter.cambiar(Dataset.copiaDataset(mejorVecino));

                llamadas++;
            }

            // Mostramos por pantalla la distancia calculada con cada una de las 5 semillas
            System.out.println(this.mejorDistancia);
        }
    }
}
