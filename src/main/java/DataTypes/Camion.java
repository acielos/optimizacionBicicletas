package main.java.DataTypes;

public class Camion {
    private int capacidad = 20;
    private int carga = 0;


    // Vamos a declarar el get y set de la capacidad
    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    // Vamos a declarar el get y set de la carga
    public int getCarga() {
        return carga;
    }

    public void setCarga(int carga) {
        this.carga = carga;
    }
}
