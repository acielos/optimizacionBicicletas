package main.java.Controlador;

import main.java.DataTypes.*;
import main.java.Modelo.*;

import javax.xml.crypto.Data;
import java.io.IOException;

import java.io.IOException;  // Si usas BufferedReader o similar

import main.java.DataTypes.Dataset;
import main.java.DataTypes.Estacion;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // Ruta al archivo TSP (ajusta si está en resources o src)
            String ruta = "././datasetBicis.tsp";  // O ruta absoluta

            List<Estacion> dataset = Dataset.leerFicheros(ruta);
            Dataset.mostrarDataset(dataset);  // Muestra y verifica

            // Aquí puedes usar dataset para algoritmos...
            System.out.println("\n¡Listo para optimizar rutas de bicicletas!");

        } catch (IOException e) {
            System.err.println("Error cargando dataset: " + e.getMessage());
            System.err.println("Verifica la ruta al archivo datosBicis.tsp");
        }
    }
}

