package main.java.Modelo;

import main.java.DataTypes.Estacion;

public class DistanciaManhattan {
    private double distancia_total;
    private double radio = 6371.0;

    public double calculaDistancia(Estacion estacion1, Estacion estacion2) {
        // Calculamos en radianes los variables que necesitamos
        double dlat_rad = Math.toRadians(estacion2.getLatitud() - estacion1.getLatitud());
        double dlon_rad = Math.toRadians(estacion2.getLongitud() - estacion1.getLongitud());
        double lat_media_rad = Math.toRadians((estacion1.getLatitud() + estacion2.getLatitud()) / 2);
        double dist_norte_sur = radio * dlat_rad;
        double dist_este_oeste = radio * dlon_rad * Math.cos(lat_media_rad);

        // Calculamos la distancia total entre dos puntos
        distancia_total = dist_norte_sur + dist_este_oeste;

        // Evidentemente devolvemos la distancia total entre los dos puntos
        return distancia_total;
    }
}
