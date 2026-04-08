package main.java.Modelo;

import main.java.DataTypes.Dataset;
import main.java.DataTypes.Estacion;

import java.util.*;

public class EnfriamientoSimulado extends Algoritmo {

    public EnfriamientoSimulado(List<Estacion> dataset) {
        this.listaEstaciones = dataset;
    }

    @Override
    public void run() {

        for (int i = 0; i < 5; i++) {
            this.camion.carga = 7;
            this.numEvaluaciones = 0;
            double TemperaturaInicial = 0;
            int maxVecinos = 30;
            int enfriamientos = 100;

            // Mejores valores encontrado
            double mejorKmsGlobal = 0;
            double mejorEntropiaGlobal = 0;
            double mejorFOGlobal = Double.POSITIVE_INFINITY;
            List<Estacion> mejorVecinoGlobal = null;

            // Para las graficas
            List<Double> kmsHistorico = new ArrayList<>();
            List<Double> entropiaHistorico = new ArrayList<>();
            List<Double> foHistorico = new ArrayList<>();

            // Generamos solucion inicial
            List<Estacion> inicialSol = Dataset.copiaDataset(this.listaEstaciones);

            // Mezclamos menos la primera
            List<Estacion> mezclado = Dataset.copiaDataset(inicialSol.subList(1,  inicialSol.size()));

            // Para las semillas
            Random rand = new Random(this.semilla[i]);

            // Mezclamos todas menos la primera
            Collections.shuffle(mezclado, rand);

            // Unimos de nuevo todo
            mezclado.addFirst(inicialSol.getFirst());

            // Hacemos una copia del dataset donde iremos guardando el mejor vecino hasta el momento
            List<Estacion> mejorVecino = Dataset.copiaDataset(mezclado);

            //Recomponemos la solución para asegurar que estaciones y cargas vayan en conjunto
            List<Estacion> solucionActual = recomponer(mejorVecino);

            // Empezamos a calcular/guardar valores de nuestra sol inicial
            double kmsActual = distanciaManhattan.calculaCompleto(solucionActual);
            double foLocal = calcularFObjetivo(kmsActual, solucionActual);
            double entropiaLocal = calcularEntropiaTotal(solucionActual);

            mejorVecinoGlobal = Dataset.copiaDataset(mejorVecino);
            mejorFOGlobal = foLocal;
            mejorEntropiaGlobal = entropiaLocal;

            // Para calcular la temperatura, ejecutamos un greedy
            Algoritmo greedy = new Greedy(this.listaEstaciones);
            greedy.run();
            double costeGreedy = greedy.mejorFuncionObjetivo;

            TemperaturaInicial = calcularTemperaturaInicial(costeGreedy);

            // Pasamos a la parte con chicha
            for (int k = 0; k < enfriamientos; k++) {
                // Calculamos la temperatura actual
                double Tk = TemperaturaInicial / (1+k);

                // Pasamos a generar vecinos (segun tk)
                for (int  l = 0; l < maxVecinos; l++) {
                    // Generamos un vecino
                    int n = this.listaEstaciones.size();
                    int pos1 = 1 + rand.nextInt(n-1);
                    int pos2 = 1 + rand.nextInt(n-1);

                    // Comprobamos que no sean iguales
                    while (pos1 == pos2) {pos2 = 1 + rand.nextInt(n-1);}

                    // Realizamos el cambio y generamos el vecino
                    List<Estacion> ordenVecinos = new ArrayList<>(solucionActual);
                    Collections.swap(ordenVecinos, pos1, pos2);

                    // Recomponemos la solucion para limpieza de los datos
                    List<Estacion> vecinosEquilibrados = recomponer(ordenVecinos);

                    // Hacemos los cálculos de este vecino
                    double kmsVecinoActual = distanciaManhattan.calculaCompleto(vecinosEquilibrados);
                    double funcionObjetivoVecino = calcularFObjetivo(kmsVecinoActual, vecinosEquilibrados);


                    // Vamos a ver si aceptamos o no aceptamos al vecino
                    double calculoFO = funcionObjetivoVecino - foLocal;

                    boolean aceptar = calculoFO < 0 || rand.nextDouble() < Math.exp(-calculoFO/Tk);

                    if (aceptar) {
                        solucionActual = Dataset.copiaDataset(vecinosEquilibrados);
                        foLocal = funcionObjetivoVecino;
                        kmsActual = kmsVecinoActual;
                        entropiaLocal = calcularEntropiaTotal(vecinosEquilibrados);

                        // Si es mejor que nunca
                        if (foLocal < mejorFOGlobal) {
                            mejorFOGlobal = foLocal;
                            mejorEntropiaGlobal = entropiaLocal;
                            mejorVecinoGlobal = vecinosEquilibrados;
                            mejorEntropiaGlobal = entropiaLocal;
                            mejorKmsGlobal = kmsActual;
                        }
                    }
                }

                // Guardamos información para los gráficos
                entropiaHistorico.add(entropiaLocal);
                foHistorico.add(foLocal);
                kmsHistorico.add(kmsActual);
            }
            List<Estacion> mejorSolucionFinal = recomponer(mejorVecinoGlobal); // que el camion da problemas...

            this.recorrido = mejorVecinoGlobal;
            this.mejorFuncionObjetivo = mejorFOGlobal;
            this.distanciaRecorrida = mejorKmsGlobal;
            this.entropiaFinal = mejorEntropiaGlobal;
            this.fObjetivo = mejorFOGlobal;

            // Mostramos por pantalla la distancia calculada con cada una de las 5 semillas
            System.out.println("\n--- Resultado Enfriamiento Simulado ---");
            System.out.printf("Recorrido: ");
            for (Estacion e : recorrido) System.out.print(e.id + " ");
            System.out.println("-> 0");

            System.out.printf("Kilómetros recorridos : %.4f km%n", distanciaRecorrida);
            System.out.printf("Función objetivo      : %.4f%n", this.mejorFuncionObjetivo);
            System.out.printf("Evaluaciones          : %d%n", numEvaluaciones);

            // Si queremos mostrar, pero ahora no
//            System.out.println("\nEstado final de las estaciones:");
//            System.out.printf("%-6s %-10s %-10s %-8s%n", "ID", "Carga", "Capacidad", "% ocup.");
//            for (Estacion e : recorrido) {
//                double pct = 100.0 * e.carga / e.capacidad;
//                System.out.printf("%-6d %-10d %-10d %.1f%%%n", e.id, e.carga, e.capacidad, pct);
//            }
            System.out.printf("%nCarga final del camión: %d/%d bicis%n", camion.carga, camion.getCapacidad());


        }

    }

    private double calcularTemperaturaInicial(double costeGreedy){
        double temperatura = (-0.3 * costeGreedy / Math.log(0.3));
        return temperatura;
    }
}
