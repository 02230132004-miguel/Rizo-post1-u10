package com.universidad.enviosservice.estrategia;

/**
 * Estrategia de cálculo de costo para envíos exprés.
 */
public class EnvioExpressStrategy implements EstrategiaEnvio {

    @Override
    public double calcular(double pesoKg, double distanciaKm) {
        double costo;

        if (pesoKg <= 5.0) {
            costo = distanciaKm * 1.2;
        } else if (pesoKg <= 20.0) {
            costo = distanciaKm * 1.2 + (pesoKg - 5.0) * 1.5;
        } else {
            costo = distanciaKm * 1.2 + 15.0 * 1.5 + (pesoKg - 20.0) * 2.0;
        }

        if (distanciaKm > 500.0) {
            costo += 25.0;
        }

        return costo * 1.3;
    }
}
