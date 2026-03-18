package main.java.Modelo;

import java.util.*;
import main.java.DataTypes.*;

public abstract class Algoritmo {
    // Asquí metemos los atributos que usen TODOS
    protected List<Estacion> listaEstaciones;
    public Camion camion;
    protected long[] semilla = {11111, 22222, 33333, 44444, 55555};
    protected Double[][] distancias;
    protected DistanciaManhattan distanciaManhattan = new DistanciaManhattan();
    public List<Estacion> recorrido = new ArrayList<Estacion>();
    public double distanciaRecorrida = 0;
    protected double mejorDistancia = Double.POSITIVE_INFINITY;

    protected Intercambio inter = new Intercambio();

    // Asquí el método que TODOS deben implementar (porque heredan)
    public abstract void run();

    // Método para calcular las distancias
    public void calcularDistancias() {
        for (int i = 0; i < this.listaEstaciones.size(); i++) {
            this.distancias[i][i] = Double.POSITIVE_INFINITY;
            for (int j = 0; j < i; j++) {
                this.distancias[i][j] = this.distanciaManhattan.calculaDistancia(this.listaEstaciones.get(i), this.listaEstaciones.get(j));
                this.distancias[j][i] = this.distancias[i][j];
            }
        }
    }
}
