package main.java.Modelo;

import main.java.DataTypes.*;
import java.util.*;

public class BusquedaTabu extends Algoritmo {

    public BusquedaTabu(List<Estacion> dataset) {
        this.listaEstaciones = dataset;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++){
            this.numEvaluaciones = 0;
            this.historialExplotacion.clear();
            int tamano = this.listaEstaciones.size();
            int tamTabu = Math.max(1, tamano / 4);
            listaTabu listaT = new listaTabu(tamTabu);
            int[][] frecuencias = new int[tamano][tamano];
            Random rand = new Random(this.semilla[i]);

            int iterTotal = 500;
            int iterFases = iterTotal / 5;
            int proximoReinicio = iterFases;
            int reinicios = 0;

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

            for (int j = 0; j < iterTotal; j++){
                // Para movimientos
                int[] movimientos1 = new int[20];
                int[] movimientos2 = new int[20];
                double[] foVecinos = new double[20];
                List<Estacion>[] solVecinos = new List[20];
                double[] kmVecinos = new double[20];

                for (int k = 0; k < 20; k++){
                    // Pos aleaotrias
                    int pos1 = 1 + rand.nextInt(tamano - 2);
                    int pos2 = 1 + rand.nextInt(tamano - 2);
                    while (pos1 == pos2){
                        pos2 = 1 + rand.nextInt(tamano - 2);
                    }

                    // Guardamos ids
                    int id1 = solActual.get(pos1).id;
                    int id2 = solActual.get(pos2).id;

                    // Cambiamos
                    List<Estacion> vecino = new ArrayList<>(solActual);
                    Collections.swap(vecino, pos1, pos2);

                    List<Estacion> vecinoEqui = recomponer(vecino);

                    // Evaluamos
                    double kmVecino = distanciaManhattan.calculaCompleto(vecinoEqui);
                    double foVecino = calcularFObjetivo(kmVecino, vecinoEqui);

                    // Guardamos
                    movimientos1[k] = id1;
                    movimientos2[k] = id2;
                    foVecinos[k] = foVecino;
                    solVecinos[k] = vecinoEqui;
                    kmVecinos[k] = kmVecino;
                }

                // Ordenamos los arrays
                for (int posicion1 = 0; posicion1 < 19; posicion1++){
                    for (int posicion2 = posicion1 + 1; posicion2 < 20; posicion2++){
                        if (foVecinos[posicion2] < foVecinos[posicion1]){
                            double foAux = foVecinos[posicion1];
                            foVecinos[posicion1] = foVecinos[posicion2];
                            foVecinos[posicion2] = foAux;

                            double kmAux = kmVecinos[posicion1];
                            kmVecinos[posicion1] = kmVecinos[posicion2];
                            kmVecinos[posicion2] = kmAux;

                            int mov1Aux = movimientos1[posicion1];
                            movimientos1[posicion1] = movimientos1[posicion2];
                            movimientos1[posicion2] = mov1Aux;

                            int mov2Aux = movimientos2[posicion1];
                            movimientos2[posicion1] = movimientos2[posicion2];
                            movimientos2[posicion2] = mov2Aux;

                            List<Estacion> solucionAux = solVecinos[posicion1];
                            solVecinos[posicion1] = solVecinos[posicion2];
                            solVecinos[posicion2] = solucionAux;
                        }
                    }
                }

                // Nos vamos quedando con el mejor no T
                boolean haySol = false;
                int cont = 0;

                for (int k = 0; k < 20 && !haySol; k++){
                    boolean esTabu = listaT.contiene(movimientos1[k], movimientos2[k]);
                    boolean aspirante = foVecinos[k] < mejorFOGlobal;

                    // Comprobamos
                    if (!esTabu || aspirante){
                        // Guardamos info
                        solActual = Dataset.copiaDataset(solVecinos[k]);
                        foActual = foVecinos[k];
                        kmActual = kmVecinos[k];
                        enActual = calcularEntropiaTotal(solActual);

                        // Añadimos a tabu
                        listaT.annadir(movimientos1[k], movimientos2[k]);

                        // Actualizamos memoria
                        for (int a = 0; a < solActual.size()-1; a++){
                            int indice1 = solActual.get(a).id;
                            int indice2 = solActual.get(a+1).id;
                            frecuencias[indice1][indice2]++;
                        }

                        // Si es mejor, actualizamos
                        if (foActual < mejorFOGlobal){
                            mejorFOGlobal = foActual;
                            mejorEntropiaGlobal = enActual;
                            mejorKMGlobal =  kmActual;
                            mejorSolucionGlobal =  Dataset.copiaDataset(solActual);
                        }

                        haySol = true;
                    }
                }

                if (j >= proximoReinicio && reinicios < 4){
                    double rule = rand.nextDouble();

                    // Seleccionamos la forma
                    List<Estacion> nuevo;
                    if (rule < 0.25){
                        // Aleatorio
                        List<Estacion> copiaA = Dataset.copiaDataset(this.listaEstaciones);
                        List<Estacion> restoA = new ArrayList<>(copiaA.subList(1, copiaA.size()));
                        Collections.shuffle(restoA, rand);
                        restoA.addFirst(copiaA.getFirst());
                        nuevo = Dataset.copiaDataset(restoA);
                    } else if (rule < 0.75){
                        // Greedy
                        nuevo = hacerGreedy(frecuencias, rand);
                    } else {
                        // Mejor
                        nuevo = Dataset.copiaDataset(mejorSolucionGlobal);
                    }

                    List<Estacion> nuevoRec = recomponer(nuevo);
                    double kmNuevo = distanciaManhattan.calculaCompleto(nuevoRec);
                    double foNuevo =  calcularFObjetivo(kmNuevo, nuevoRec);

                    solActual = Dataset.copiaDataset(nuevoRec);
                    foActual = foNuevo;
                    kmActual = kmNuevo;
                    enActual = calcularEntropiaTotal(solActual);

                    // Reset
                    listaT.limpiar();
                    double cambio;
                    if (rand.nextBoolean()){
                        cambio = 0.5;
                    } else {
                        cambio = 1.5;
                    }

                    tamTabu = Math.max(1, (int) ((tamano / 4) * cambio));
                    listaT.setTamMaximo(tamTabu);

                    proximoReinicio += iterFases;
                    reinicios++;
                }
            }

            this.recorrido = mejorSolucionGlobal;
            this.mejorFuncionObjetivo = mejorFOGlobal;
            this.distanciaRecorrida = mejorKMGlobal;
            this.entropiaFinal = mejorEntropiaGlobal;
            this.fObjetivo = mejorFOGlobal;
            mostrarResultados("Busqueda Tabu");
            guardarDatos("BusquedaTabu", i);
        }
    }

    private List<Estacion> hacerGreedy(int[][] frecuencias, Random rand){
        int tam = this.listaEstaciones.size();
        boolean[] visitados = new boolean[tam];
        List<Estacion> resultado = new ArrayList<>();
        resultado.add(this.listaEstaciones.getFirst());
        visitados[0] = true;
        int estActual = 0;

        while (resultado.size() < tam){
            // Cogemos las no visitadas
            List<Integer> noVisitados = new ArrayList<>();
            for (int i = 0; i < tam; i++){
                if (!visitados[i]){
                    noVisitados.add(i);
                }
            }

            // Ordenamos por frecuencias
            int actual = estActual;
            noVisitados.sort(Comparator.comparingInt(j -> frecuencias[actual][j]));

            // NOs quedamos con las 5 mejores (o con las que haya)
            int lim = Math.min(noVisitados.size(), 5);
            List<Integer> mejores = noVisitados.subList(0, lim);

            // Pesos
            double[] pesos = new double[lim];
            double sumPesos = 0;
            for (int i = 0; i < lim; i++){
                int ind = mejores.get(i);
                pesos[i] = (double) 1 / (frecuencias[estActual][ind] + 1);
                sumPesos += pesos[i];
            }

            // Ruleta
            double umbral = rand.nextDouble() * sumPesos;
            double acumulado = 0;
            int estElegida = mejores.get(lim - 1);
            for (int i = 0; i < lim; i++){
                acumulado += pesos[i];
                if (umbral <= acumulado){
                    estElegida = mejores.get(i);
                    break;
                }
            }

            resultado.add(this.listaEstaciones.get(estElegida));
            visitados[estElegida] = true;
            estActual = estElegida;
        }

        return resultado;
    }


    private static class listaTabu {
        private Queue<Long> cola;
        private HashSet<Long> conjunto;
        private int tamMaximo;

        public listaTabu(int tamMaximo) {
            this.cola = new LinkedList<>();
            this.conjunto = new HashSet<>();
            this.tamMaximo = Math.max(1, tamMaximo);
        }

        public void annadir(int a, int b) {
            long key = ((long) Math.min(a, b) << 32) | Math.max(a, b);
            if (conjunto.contains(key)) return;
            cola.add(key);
            conjunto.add(key);
            while (cola.size() > tamMaximo) {
                long antiguo = cola.poll();
                conjunto.remove(antiguo);
            }
        }

        public boolean contiene(int a, int b) {
            long key = ((long) Math.min(a, b) << 32) | Math.max(a, b);
            return conjunto.contains(key);
        }

        public void limpiar() {
            cola.clear();
            conjunto.clear();
        }

        public void setTamMaximo(int t) {
            this.tamMaximo = Math.max(1, t);
            while (cola.size() > tamMaximo) {
                long antiguo = cola.poll();
                conjunto.remove(antiguo);
            }
        }
    }
}
