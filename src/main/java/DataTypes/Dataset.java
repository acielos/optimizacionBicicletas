package main.java.DataTypes;


import java.io.*;
import java.util.*;

public class Dataset {
    // Método para leer los datasets
    public static List<Estacion> leerFicheros(String nombre) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(nombre))) {
            String linea;
            int dimension = -1;

            List<Estacion> lecturaDataset = new ArrayList<>();

            // Leer cabecera hasta NODE_COORD_SECTION
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                if (linea.startsWith("DIMENSION")) {
                    String[] partes = linea.split(":");
                    if (partes.length > 1) {
                        dimension = Integer.parseInt(partes[1].trim());
                    }
                }

                if (linea.startsWith("NODE_COORD_SECTION")) {
                    break; // Empezar a leer coordenadas a partir de aquí
                }
            }

            Estacion[] dataset = new Estacion[dimension];
            int count = 0;

            // Leer coordenadas hasta EOF o hasta 'dimension' líneas válidas
            while ((linea = br.readLine()) != null && count < dimension) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;
                if (linea.equalsIgnoreCase("EOF")) break;

                // TSPLIB separa por uno o más espacios; usar regex de espacio en blanco
                String[] tokens = linea.split("\\s+");
                if (tokens.length >= 3) {
                    int indice = Integer.parseInt(tokens[0]);
                    double latitud = Double.parseDouble(tokens[1]);
                    double longitud = Double.parseDouble(tokens[2]);

                    lecturaDataset.add(new Estacion(indice, latitud, longitud));
                }
            }

            // Si hubiese menos puntos que la DIMENSION declarada, recortar el array
            if (count != dimension) {
                dataset = java.util.Arrays.copyOf(dataset, count);
            }
            return lecturaDataset;
        }
    }
}
