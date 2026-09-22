package com.universidad.enviosservice.service;

import com.universidad.enviosservice.estrategia.EnvioEstandarStrategy;
import com.universidad.enviosservice.estrategia.EnvioExpressStrategy;
import com.universidad.enviosservice.estrategia.EnvioInternacionalStrategy;
import com.universidad.enviosservice.estrategia.EnvioOvernightStrategy;
import com.universidad.enviosservice.estrategia.EstrategiaEnvio;
import java.util.Map;

/**
 * Servicio de orquestación para el cálculo de costos de envío.
 * Utiliza el patrón Strategy para delegar la tarificación base según el método de envío
 * y centraliza las reglas de negocio transversales (fragilidad y descuentos de cliente).
 */
public class ServicioCalculoEnvio {

    private final Map<String, EstrategiaEnvio> estrategias = Map.of(
        "ESTANDAR", new EnvioEstandarStrategy(),
        "EXPRESS", new EnvioExpressStrategy(),
        "OVERNIGHT", new EnvioOvernightStrategy(),
        "INTERNACIONAL", new EnvioInternacionalStrategy()
    );

    /**
     * Calcula el costo total del envío aplicando tarifas de estrategia,
     * recargo por fragilidad y descuentos por tipo de cliente.
     *
     * @param metodoEnvio  Método de envío ("ESTANDAR", "EXPRESS", "OVERNIGHT", "INTERNACIONAL").
     * @param pesoKg       Peso en kilogramos.
     * @param distanciaKm  Distancia en kilómetros.
     * @param tipoCliente  Tipo de cliente (e.g. "PREMIUM", "CORPORATIVO", "REGULAR").
     * @param esFragil     Indica si el paquete requiere manejo frágil.
     * @return Costo final calculado del envío.
     * @throws IllegalArgumentException si el método de envío no es soportado o la estrategia falla validaciones.
     */
    public double calcularCosto(String metodoEnvio, double pesoKg, double distanciaKm, String tipoCliente, boolean esFragil) {
        EstrategiaEnvio estrategia = estrategias.get(metodoEnvio);
        if (estrategia == null) {
            throw new IllegalArgumentException("Metodo de envio no soportado: " + metodoEnvio);
        }

        double costo = estrategia.calcular(pesoKg, distanciaKm);

        if (esFragil) {
            costo *= 1.15;
        }

        if ("PREMIUM".equals(tipoCliente)) {
            costo *= 0.90;
        } else if ("CORPORATIVO".equals(tipoCliente)) {
            costo *= 0.85;
        }

        return costo;
    }
}
