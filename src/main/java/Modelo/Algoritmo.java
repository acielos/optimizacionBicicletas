package main.java.Modelo;

import java.util.*;
import main.java.DataTypes.*;

public abstract class Algoritmo {
    // Asquí metemos los atributos que usen TODOS
    protected List<Estacion> listaEstaciones;
    protected Camion camion;
    protected int[] semilla = {};
    protected Double[][] distancias;
    protected DistanciaManhattan distanciaManhattan;

    // Asquí el método que TODOS deben implementar (porque heredan)
    public abstract void run();

    // Método para calcular las distancias
    protected void calcularDistancias() {
        for (int i = 0; i < this.listaEstaciones.size(); i++) {
            this.distancias[i][i] = Double.POSITIVE_INFINITY;
            for (int j = 0; j < i; j++) {
                this.distancias[i][j] = distanciaManhattan.calculaDistancia(this.listaEstaciones.get(i), this.listaEstaciones.get(j));
            }
        }
    }
}
