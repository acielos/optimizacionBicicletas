package main.java.Controlador;

import main.java.DataTypes.*;
import main.java.Modelo.*;
import java.io.IOException;

import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

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
                    break;
                case 5:
                    System.out.println("\n ***** Finalización del Programa ***** \n");
                    break;
            }


        }while(opcion!=5);
    }
}

