package main.java.Modelo;

import main.java.DataTypes.*;
import java.util.*;

public class BusquedaTabu extends Algoritmo {

    public BusquedaTabu(List<Estacion> dataset) {
        this.listaEstaciones = dataset;
    }

    @Override
    public void run() {
        /*
        * Pasos a seguir para realizar la Búsqueda Tabú:
        *   1. Para seleccionar vecinos -> Generamos 20 vecinos y nos quedadmos con el bueno
        *   2. Matriz NxN (tamaño de niestras estaciones) -> para guardar la frecuencia de cambios
        *   3. Lista tabú: 4 posiciones [i,j] que no pueden cambiarse (si estan en la lista claro, si no ya veremos)
        *   4. Reinicializar (en cada iteracion):
        *       4.1: 0,25 empezar de nuecvo
        *       4.2: 0,5 continuar como estamos
        *       4.3: 0,25 reinicializar desde la mejor solucion obtenida
        *   5. */
    }
}
