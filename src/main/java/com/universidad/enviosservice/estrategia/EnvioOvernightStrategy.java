package com.universidad.enviosservice.estrategia;

/**
 * Estrategia de cálculo de costo para envíos nocturnos (Overnight).
 */
public class EnvioOvernightStrategy implements EstrategiaEnvio {

    @Override
    public double calcular(double pesoKg, double distanciaKm) {
        if (distanciaKm > 300.0) {
            throw new IllegalArgumentException("OVERNIGHT no disponible para mas de 300 km");
        }

        double costo;

        if (pesoKg <= 5.0) {
            costo = distanciaKm * 2.0 + 20.0;
        } else if (pesoKg <= 20.0) {
            costo = distanciaKm * 2.0 + 20.0 + (pesoKg - 5.0) * 2.5;
        } else {
            costo = distanciaKm * 2.0 + 20.0 + 15.0 * 2.5 + (pesoKg - 20.0) * 3.0;
        }

        return costo;
    }
}
