package main.java.DataTypes;

import java.util.*;

public class Camion {
    private int capacidad = 20;
    int carga = 0;
    public List<Estacion> recorrido = new ArrayList<Estacion>();

    // Getter para la capacidad, que no la vamos a modificar
    public int getCapacidad() {
        return capacidad;
    }
}
