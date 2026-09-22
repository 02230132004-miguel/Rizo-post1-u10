package com.universidad.enviosservice.estrategia;

/**
 * Interfaz que define la estrategia para el cálculo del costo base de envío.
 */
public interface EstrategiaEnvio {

    /**
     * Calcula el costo de envío en función del peso y la distancia.
     *
     * @param pesoKg      Peso del paquete en kilogramos.
     * @param distanciaKm Distancia del envío en kilómetros.
     * @return Costo calculado para el método de envío.
     */
    double calcular(double pesoKg, double distanciaKm);
}
