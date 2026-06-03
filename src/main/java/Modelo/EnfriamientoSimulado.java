package main.java.Modelo;

import main.java.DataTypes.Dataset;
import main.java.DataTypes.Estacion;

import java.util.*;

public class EnfriamientoSimulado extends Algoritmo {

    public EnfriamientoSimulado(List<Estacion> dataset) {
        this.listaEstaciones = dataset;
    }

    public EnfriamientoSimulado(List<Estacion> dataset, int maxVecinos, int enfriamiento) {
        this.listaEstaciones = dataset;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            this.numEvaluaciones = 0;
            this.historialExplotacion.clear();

            int maxVecinos = 20;
            int enfriamiento = 80;

            Random rand = new Random(this.semilla[i]);
            int tam = this.listaEstaciones.size();

            List<Estacion> copia = Dataset.copiaDataset(this.listaEstaciones);
            List<Estacion> resto = new ArrayList<>(copia.subList(1,  copia.size()));
            Collections.shuffle(resto, rand);
            resto.addFirst(copia.getFirst());

            List<Estacion> solActual = recomponer(resto);

            double kmActual = distanciaManhattan.calculaCompleto(solActual);
            double enActual = calcularEntropiaTotal(solActual);
            double foActual = calcularFObjetivo(kmActual, solActual);

            double mejorFOGlobal = foActual;
            double mejorKMGlobal = kmActual;
            double mejorEntropiaGlobal = enActual;
            List<Estacion> mejorSolucionGlobal = Dataset.copiaDataset(solActual);


//            double[] pCandidatos = {0.10, 0.15, 0.20, 0.25, 0.30};
//            int n = this.listaEstaciones.size();
//
//            List<Double> calibrar = new ArrayList<>();
//            for (int v = 0; v < 1000; v++) {
//                int p1 = 1 + rand.nextInt(n - 2);
//                int p2 = 1 + rand.nextInt(n - 2);
//                while (p1 == p2) p2 = 1 + rand.nextInt(n - 2);
//                List<Estacion> vecino = new ArrayList<>(solActual);
//                Collections.swap(vecino, p1, p2);
//                List<Estacion> vecinoEq = recomponer(vecino);
//                double kmsV = distanciaManhattan.calculaCompleto(vecinoEq);
//                double foV = kmsV + alpha * (n - calcularEntropiaTotal(vecinoEq));
//                calibrar.add(foV - foActual);
//            }
//            System.out.printf("Semilla %d - Tasa de aceptación por P0:%n", semilla[i]);
//            for (double p0 : pCandidatos) {
//                double t0 = (-0.15 * foActual) / Math.log(p0);
//                int aceptados = 0;
//                for (double cal : calibrar) {
//                    if (cal < 0 || rand.nextDouble() < Math.exp(-cal / t0)) {
//                        aceptados++;
//                    }
//                }
//                double tasa = (double) aceptados / calibrar.size();
//                System.out.printf("  P0=%.2f -> T0=%.4f -> tasa=%.2f%n", p0, t0, tasa);
//            }

            double temperaturaInicial = (-0.1 * foActual) / Math.log(0.3);

            for (int k = 0; k < enfriamiento; k++) {
                double Tk = temperaturaInicial / (1 + k);  // Cauchy: T disminuye con 1/k
                for (int l = 0; l < maxVecinos; l++) {
                    // Generar dos posiciones distintas en [1, n-2] (excluimos 0 y última)
                    int p1 = 1 + rand.nextInt(tam - 2);
                    int p2 = 1 + rand.nextInt(tam - 2);
                    while (p1 == p2) p2 = 1 + rand.nextInt(tam - 2);
                    // Copiar solución actual y aplicar swap
                    List<Estacion> ordenVecino = new ArrayList<>(solActual);
                    Collections.swap(ordenVecino, p1, p2);
                    // Reconstruir con cargas equilibradas
                    List<Estacion> vecinoEq = recomponer(ordenVecino);
                    // Evaluar el vecino
                    double kmsVecino = distanciaManhattan.calculaCompleto(vecinoEq);
                    double entropiaVecino = calcularEntropiaTotal(vecinoEq);
                    double foVecino = calcularFObjetivo(kmsVecino, vecinoEq);
                    double delta = foVecino - foActual;
                    // Criterio de aceptación: mejora siempre, empeora con prob Boltzmann
                    if (delta < 0 || rand.nextDouble() < Math.exp(-delta / Tk)) {
                        solActual = Dataset.copiaDataset(vecinoEq);
                        foActual = foVecino;
                        kmActual = kmsVecino;
                        enActual = entropiaVecino;
                        // Actualizar mejor global si corresponde
                        if (foActual < mejorFOGlobal) {
                            mejorFOGlobal = foActual;
                            mejorKMGlobal = kmActual;
                            mejorEntropiaGlobal = enActual;
                            mejorSolucionGlobal = Dataset.copiaDataset(solActual);
                        }
                    }
                }
            }

            this.recorrido = mejorSolucionGlobal;
            this.mejorFuncionObjetivo = mejorFOGlobal;
            this.distanciaRecorrida = mejorKMGlobal;
            this.entropiaFinal = mejorEntropiaGlobal;
            this.fObjetivo = mejorFOGlobal;
            mostrarResultados("Enfriamiento Simulado");
            guardarDatos("EnfriamientoSimulado", i);
        }
    }
}
