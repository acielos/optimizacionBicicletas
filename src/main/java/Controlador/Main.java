package main.java.Controlador;

import main.java.DataTypes.*;
import main.java.Modelo.*;
import java.io.IOException;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            String ruta = "././datasetBicis.tsp";

            List<Estacion> dataset = Dataset.leerFicheros(ruta);
            Dataset.mostrarDataset(dataset);

            System.out.println("\n¡Listo para optimizar rutas de bicicletas!");

        } catch (IOException e) {
            System.err.println("Error cargando dataset: " + e.getMessage());
            System.err.println("Verifica la ruta al archivo datosBicis.tsp");
        }
    }
}

