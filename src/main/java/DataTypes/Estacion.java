package main.java.DataTypes;

public class Estacion {
    // Atributos de la clase punto
    int id, capacidad, carga;
    double latitud;
    double longitud;

    // Constructor de la clase
    public Estacion(int id, double latitud, double longitud) {
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    // Métodos para obtener los datos de una estacion
    public int getId() {
        return id;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public double getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getCarga() {
        return carga;
    }

    public void setCarga(int carga) {
        this.carga = carga;
    }


    // Damos formato a la visualización de la estación (en la terminal, en mapa ya buscamos si eso)
    public String toString(){
        return String.format("%d (%f, %f)", id, latitud, longitud);
    }
}
