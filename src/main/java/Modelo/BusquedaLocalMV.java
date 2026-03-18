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

            // Reseteamos la distancia
            this.distanciaRecorrida = 0;

            // Para la semilla
            Random rand = new Random(this.semilla[i]);

            // Vamos a mezclarlo (a excepción de la primera estación) para nuestra solución inicial
            List<Estacion> mezclado = datasetCopiado.subList(1, this.listaEstaciones.size());

            // Mezclamos todas menos la primera
            Collections.shuffle(mezclado, rand);

            // Unimos de nuevo todo
            mezclado.addFirst(datasetCopiado.getFirst());

            // "Número" de llamadas a la función objetivo
            int llamadas = 0;

            // Para comprobar si mejora o no
            int noMejorado = 0;

            // Una vez lo tenemos todo, vamos a proceder a la parte interesante del algoritmo
            while(llamadas < 3000 && noMejorado < 100) {

                // Reseteamos la distancia
                this.distanciaRecorrida = 0;

                // Calculamos la distancia del actual
                for (int j = 0; j < mezclado.size()-1; j++) {
                    this.distanciaRecorrida += distanciaManhattan.calculaDistancia(mezclado.get(j), mezclado.get(j+1));
                }

                // Comprobamos si es mejor la distancia que la que teníamos hasta el momento
                if (this.distanciaRecorrida < this.mejorDistancia) {
                    this.mejorDistancia = this.distanciaRecorrida;
                    this.distanciaRecorrida = 0;
                    noMejorado = 0;
                } else {
                    // En caso que no mejoremos lo anotamos
                    noMejorado++;
                }

                // Cambiamos dos posiciones
                mezclado = inter.cambiar(mezclado);
            }

            // Mostramos por pantalla la distancia calculada con cada una de las 5 semillas
            System.out.println(this.distanciaRecorrida);
        }
    }
}
