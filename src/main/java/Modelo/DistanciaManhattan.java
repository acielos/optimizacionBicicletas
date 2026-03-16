package main.java.Modelo;

import main.java.DataTypes.Estacion;

public class DistanciaManhattan {
    public double distancia_total;
    private double radio = 6371.0;

    public double calculaDistancia(Estacion estacion1, Estacion estacion2) {
        // Calculamos en radianes los variables que necesitamos
        double dlat_rad = Math.toRadians(Math.abs(estacion2.latitud - estacion1.latitud));
        double dlon_rad = Math.toRadians(Math.abs(estacion2.longitud - estacion1.longitud));
        double lat_media_rad = Math.toRadians((estacion1.latitud + estacion2.latitud) / 2.0);
        double dist_norte_sur = radio * dlat_rad;
        double dist_este_oeste = radio * dlon_rad * Math.cos(lat_media_rad);

        // Calculamos la distancia total entre dos puntos
        distancia_total = dist_norte_sur + dist_este_oeste;

        // Evidentemente devolvemos la distancia total entre los dos puntos
        return distancia_total;
    }
}
