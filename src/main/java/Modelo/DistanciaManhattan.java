package main.java.Modelo;

public class DistanciaManhattan {
    private double distancia;
    private double radio = 6371.0;
    private double lat1, lat2, lon1, lon2;

    // Constructor de la clase
    public DistanciaManhattan(double lat1, double lon1, double lat2, double lon2) {
        this.lat1 = lat1;
        this.lon1 = lon1;
        this.lat2 = lat2;
        this.lon2 = lon2;
    }

    // Calculamos en radianes los variables que necesitamos
    double dlat_rad = Math.toRadians(lat2 - lat1);
    double dlon_rad = Math.toRadians(lon2 - lon1);
    double lat_media_rad = Math.toRadians((lat1 + lat2) / 2);
    double dist_norte_sur = radio * dlat_rad;
    double dist_este_oeste = radio * dlon_rad * Math.cos(lat_media_rad);

    // Calculamos la distancia total entre dos puntos
    double distancia_total = dist_norte_sur + dist_este_oeste;

    // Evidentemente devolvemos la distancia total entre los dos puntos
    return distancia_total;
}
