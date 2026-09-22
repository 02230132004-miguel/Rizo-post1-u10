package com.universidad.enviosservice.estrategia;

/**
 * Estrategia de cálculo de costo para envíos internacionales.
 */
public class EnvioInternacionalStrategy implements EstrategiaEnvio {

    @Override
    public double calcular(double pesoKg, double distanciaKm) {
        double costo;

        if (pesoKg <= 5.0) {
            costo = distanciaKm * 1.8 + 50.0;
        } else if (pesoKg <= 20.0) {
            costo = distanciaKm * 1.8 + 50.0 + (pesoKg - 5.0) * 3.0;
        } else {
            costo = distanciaKm * 1.8 + 50.0 + 15.0 * 3.0 + (pesoKg - 20.0) * 4.5;
        }

        if (distanciaKm > 5000.0) {
            costo += 100.0;
        }

        return costo;
    }
}
