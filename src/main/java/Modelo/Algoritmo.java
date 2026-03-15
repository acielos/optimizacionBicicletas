package main.java.Modelo;

import java.util.*;
import main.java.DataTypes.*;

public abstract class Algoritmo {
    // Asquí metemos los atributos que usen TODOS
    protected List<Estacion> listaEstaciones;
    protected Camion camion;
    protected int[] semilla = {};

    // Asquí el método que TODOS deben implementar (porque heredan)
    public abstract void run();
}
