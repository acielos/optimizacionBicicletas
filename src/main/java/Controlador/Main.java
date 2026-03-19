package main.java.Controlador;

import main.java.DataTypes.*;
import main.java.Modelo.*;
import java.io.IOException;

import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

//        double lat1 = 43.461719;
//        double lon1 = -3.8021;
//        double lat2 = 43.47441267;
//        double lon2 = -3.785781345;
        DistanciaManhattan distanciaManhattan = new DistanciaManhattan();
//
//
//        Estacion est1 = new Estacion(0, lat1, lon1);
//        Estacion est2 = new Estacion(2, lat2, lon2);
//        double distancia = distanciaManhattan.calculaDistancia(est1, est2);
//
//        System.out.println("Distancia: " + distancia);

//        [0 (43,4617190000, -3,8021000000),
//        6 (43,4701661100, -3,8064994780),
//        5 (43,4711061100, -3,8005864590),
//        8 (43,4691494100, -3,7732759760),
//        7 (43,4718048300, -3,7813461350),
//        1 (43,4744126700, -3,7857813450),
//        4 (43,4719661100, -3,7928544960),
//        2 (43,4757322600, -3,7980638710),
//        3 (43,4784718100, -3,7886531460),
//        10 (43,4621430800, -3,7972414490),
//        9 (43,4635521200, -3,7880054790),
//        12 (43,4607255400, -3,8179782450),
//        11 (43,4579662500, -3,8248012420),
//        13 (43,4583838900, -3,8104683910),
//        15 (43,4548260600, -3,8669006690),
//        14 (43,4528130500, -3,8713906830)]

//        0 43.461719 -3.8021
//        1 43.47441267 -3.785781345
//        2 43.47573226 -3.798063871
//        3 43.47847181 -3.788653146
//        4 43.47196611 -3.792854496
//        5 43.47110611 -3.800586459
//        6 43.47016611 -3.806499478
//        7 43.47180483 -3.781346135
//        8 43.46914941 -3.773275976
//        9 43.46355212 -3.788005479
//        10 43.46214308 -3.797241449
//        11 43.45796625 -3.824801242
//        12 43.46072554 -3.817978245
//        13 43.45838389 -3.810468391
//        14 43.45281305 -3.871390683
//        15 43.45482606 -3.866900669

//        Estacion est0 = new Estacion(0, 43.4617190000, -3.8021000000);
//        Estacion est1 = new Estacion(1,43.4744126700, -3.7857813450);
//        Estacion est2 = new Estacion(2, 43.47573226, -3.798063871);
//        Estacion est3 = new Estacion(3, 43.47847181, -3.788653146);
//        Estacion est4 = new Estacion(4, 43.47196611, -3.792854496);
//        Estacion est5 = new Estacion(5, 43.47110611, -3.800586459);
//        Estacion est6 = new Estacion(6, 43.47016611, -3.806499478);
//        Estacion est7 = new Estacion(7, 43.47180483, -3.781346135);
//        Estacion est8 = new Estacion(8, 43.46914941, -3.773275976);
//        Estacion est9 = new Estacion(9, 43.46355212, -3.788005479);
//        Estacion est10 = new Estacion(10, 43.46214308, -3.797241449);
//        Estacion est11 = new Estacion(11, 43.45796625, -3.824801242);
//        Estacion est12 = new Estacion(12, 43.46072554, -3.817978245);
//        Estacion est13 = new Estacion(13, 43.45838389, -3.810468391);
//        Estacion est14 = new Estacion(14, 43.45281305, -3.871390683);
//        Estacion est15 = new Estacion(15, 43.45482606, -3.866900669);
//
//        double distancia = distanciaManhattan.calculaDistancia(est0, est6);
//        distancia += distanciaManhattan.calculaDistancia(est6, est5);
//        distancia += distanciaManhattan.calculaDistancia(est5, est8);
//        distancia += distanciaManhattan.calculaDistancia(est8, est7);
//        distancia += distanciaManhattan.calculaDistancia(est7, est1);
//        distancia += distanciaManhattan.calculaDistancia(est1, est4);
//        distancia += distanciaManhattan.calculaDistancia(est4, est2);
//        distancia += distanciaManhattan.calculaDistancia(est2, est3);
//        distancia += distanciaManhattan.calculaDistancia(est3, est10);
//        distancia += distanciaManhattan.calculaDistancia(est10, est9);
//        distancia += distanciaManhattan.calculaDistancia(est9, est12);
//        distancia += distanciaManhattan.calculaDistancia(est12, est11);
//        distancia += distanciaManhattan.calculaDistancia(est11, est13);
//        distancia += distanciaManhattan.calculaDistancia(est13, est15);
//        distancia += distanciaManhattan.calculaDistancia(est15, est14);
//        //distancia += distanciaManhattan.calculaDistancia(est14, est0);
//
//        System.out.println("Distancia: " + distancia);




        // Abrimos scaner
        Scanner sc = new Scanner(System.in);

        // Variables qie lo mismo usamos en varios sitios
        String ruta = "././datasetBicis.tsp";
        List<Estacion> dataset = new ArrayList<Estacion>();

        int opcion = 0;
        do {
            System.out.println(" ");
            System.out.println("    Aplicación Gestión de Estaciones");
            System.out.println("==========================================");
            System.out.println("    1. Cargar Dataset");
            System.out.println("    2. Mostrar Dataset");
            System.out.println("    3. Probar Estrategia");
            System.out.println("    4. Probar Todas las Estrategias");
            System.out.println("    5. Salir");
            System.out.println("==========================================");
            System.out.print("    Escoga una opción -> ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    dataset = Dataset.leerFicheros(ruta);
                    break;
                case 2:
                    if (dataset == null || dataset.isEmpty()) {
                        System.out.println("\nERROR - DATASET NO CARGADO\n");
                    }else{
                        Dataset.mostrarDataset(dataset);
                    }
                    break;
                case 3:
                    break;
                case 4:
                    System.out.println(" **** Greedy **** ");
                    Algoritmo greedy = new Greedy(dataset);
                    greedy.run();
                    System.out.println(greedy.distanciaRecorrida);

                    System.out.println(" **** Búsqueda Aleatoria **** ");
                    Algoritmo busquedaAleatoria = new BusquedaAleatoria(dataset);
                    busquedaAleatoria.run();

                    System.out.println(" **** Búsqueda Local Mejor Vecino **** ");
                    Algoritmo busquedaLocalMV = new BusquedaLocalMV(dataset);
                    busquedaLocalMV.run();

                    System.out.println(" **** Búsqueda Local Primer Mejor **** ");
                    Algoritmo busquedaLocalPM = new BusquedaLocalPM(dataset);
                    busquedaLocalPM.run();

                    break;
                case 5:
                    System.out.println("\n ***** Finalización del Programa ***** \n");
                    break;
            }


        }while(opcion!=5);
    }
}

