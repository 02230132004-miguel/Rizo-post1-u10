package com.universidad.enviosservice.estrategia;

/**
 * Estrategia de cálculo de costo para envíos estándar.
 */
public class EnvioEstandarStrategy implements EstrategiaEnvio {

    @Override
    public double calcular(double pesoKg, double distanciaKm) {
        double costo;

        if (pesoKg <= 5.0) {
            costo = distanciaKm * 0.5;
        } else if (pesoKg <= 20.0) {
            costo = distanciaKm * 0.5 + (pesoKg - 5.0) * 0.8;
        } else {
            costo = distanciaKm * 0.5 + 15.0 * 0.8 + (pesoKg - 20.0) * 1.2;
        }

        if (distanciaKm > 500.0) {
            costo += 10.0;
        }

        return costo;
    }
}
