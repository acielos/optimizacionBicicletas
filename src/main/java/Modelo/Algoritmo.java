//package main.java.Modelo;
//
//import java.util.*;
//import main.java.DataTypes.*;
//
//public abstract class Algoritmo {
//    // Asquí metemos los atributos que usen TODOS
//    protected List<Estacion> listaEstaciones;
//    public Camion camion = new Camion();
//    protected long[] semilla = {11111, 22222, 33333, 44444, 55555};
//    protected Double[][] distancias;
//    protected DistanciaManhattan distanciaManhattan = new DistanciaManhattan();
//    public List<Estacion> recorrido = new ArrayList<Estacion>();
//    public double distanciaRecorrida = 0;
//    protected double mejorDistancia = Double.POSITIVE_INFINITY;
//
//    protected Intercambio inter = new Intercambio();
//
//    // Asquí el método que TODOS deben implementar (porque heredan)
//    public abstract void run();
//
//    // Método para calcular las distancias
//    public void calcularDistancias() {
//        for (int i = 0; i < this.listaEstaciones.size(); i++) {
//            this.distancias[i][i] = Double.POSITIVE_INFINITY;
//            for (int j = 0; j < i; j++) {
//                this.distancias[i][j] = this.distanciaManhattan.calculaDistancia(this.listaEstaciones.get(i), this.listaEstaciones.get(j));
//                this.distancias[j][i] = this.distancias[i][j];
//            }
//        }
//    }
//}

package main.java.Modelo;

import java.util.*;
import main.java.DataTypes.*;

public abstract class Algoritmo {

    // Dataset de estaciones y estado del camión
    protected List<Estacion> listaEstaciones;
    public Camion camion = new Camion();

    protected Intercambio inter = new Intercambio();

    // Semillas para los algoritmos estocásticos (5 ejecuciones)
    protected long[] semilla = {12345L, 67890L, 11111L, 54321L, 99999L};

    protected double mejorDistancia = Double.POSITIVE_INFINITY;
    protected double mejorFuncionObjetivo = Double.POSITIVE_INFINITY;

    // Matriz de distancias Manhattan entre todas las estaciones
    protected Double[][] distancias;

    // Utilidades compartidas
    protected DistanciaManhattan distanciaManhattan = new DistanciaManhattan();

    // Resultado de la ejecución
    public List<Estacion> recorrido = new ArrayList<>();
    public double distanciaRecorrida = 0.0;
    public double entropiaFinal      = 0.0;
    public double fObjetivo          = 0.0;
    public int numEvaluaciones = 0;

    // Alpha para la función objetivo: Fobj = Kms + alpha * (N - Entropia)
    protected double alpha = 1.5;

    // Método que cada algoritmo debe implementar
    public abstract void run();

    // ─── Distancias ────────────────────────────────────────────────────────────

    public void calcularDistancias() {
        for (int i = 0; i < listaEstaciones.size(); i++) {
            distancias[i][i] = Double.POSITIVE_INFINITY;
            for (int j = 0; j < i; j++) {
                distancias[i][j] = distanciaManhattan.calculaDistancia(
                        listaEstaciones.get(i), listaEstaciones.get(j));
                distancias[j][i] = distancias[i][j];
            }
        }
    }

    // ─── Función objetivo ──────────────────────────────────────────────────────

    /**
     * Fobj = Kms + alpha * (N - Entropía)
     * Penalizamos la falta de entropía: cuanto más lejos del máximo (N), peor.
     */
    public double calcularFObjetivo(double kms, List<Estacion> estaciones) {
        numEvaluaciones++;
        double entropia  = calcularEntropiaTotal(estaciones);
        double nEstaciones = estaciones.size();
        return kms + alpha * (nEstaciones - entropia);
    }

    // ─── Entropía ──────────────────────────────────────────────────────────────

    public double calcularEntropiaTotal(List<Estacion> estaciones) {
        double total = 0.0;
        for (Estacion e : estaciones) {
            if (e.carga == 0 || e.carga == e.capacidad) continue; // entropía 0
            double p  = (double) e.carga / e.capacidad;
            double hi = -p * (Math.log(p) / Math.log(2))
                    - (1 - p) * (Math.log(1 - p) / Math.log(2));
            total += hi;
        }
        return total;
    }

    // ─── Equilibrado de una estación ──────────────────────────────────────────

    /**
     * Intenta llevar la estación al 50% de su capacidad.
     * Respeta los límites de la estación (0 ≤ carga ≤ capacidad)
     * y los del camión (0 ≤ camion.carga ≤ camion.capacidad).
     */
    protected void equilibrarEstacion(Estacion est) {
        int objetivo = (int) Math.ceil(est.capacidad / 2.0);

        if (est.carga > objetivo) {
            // Estación por encima del 50%: camión RECOGE bicis
            int puedeRecoger   = camion.getCapacidad() - camion.carga; // espacio libre
            int debeRecoger    = est.carga - objetivo;
            int realmenteRecoge = Math.min(puedeRecoger, debeRecoger);
            est.carga   -= realmenteRecoge;
            camion.carga += realmenteRecoge;

        } else if (est.carga < objetivo) {
            // Estación por debajo del 50%: camión DEJA bicis
            int puedeDar    = camion.carga;           // lo que lleva
            int debeDar     = objetivo - est.carga;
            int realmenteDa = Math.min(puedeDar, debeDar);
            est.carga   += realmenteDa;
            camion.carga -= realmenteDa;
        }
        // Si ya está exactamente al 50%, no se hace nada
    }
}