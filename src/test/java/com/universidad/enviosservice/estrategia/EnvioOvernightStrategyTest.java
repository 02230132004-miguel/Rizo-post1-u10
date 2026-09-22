package com.universidad.enviosservice.estrategia;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EnvioOvernightStrategyTest {

    private EnvioOvernightStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new EnvioOvernightStrategy();
    }

    @ParameterizedTest(name = "Peso: {0}kg, Distancia: {1}km -> Esperado: ${2}")
    @CsvSource({
        // Tramo 1: peso <= 5 kg (distancia <= 300 km)
        "3.0, 100.0, 220.0",
        "5.0, 300.0, 620.0",

        // Tramo 2: 5 < peso <= 20 kg (distancia <= 300 km)
        "10.0, 100.0, 232.5",
        "20.0, 200.0, 457.5",

        // Tramo 3: peso > 20 kg (distancia <= 300 km)
        "25.0, 200.0, 472.5",
        "30.0, 300.0, 687.5"
    })
    @DisplayName("Debe calcular correctamente el costo de envío overnight para distancias permitidas (<= 300 km)")
    void debeCalcularCostoOvernightCorrectamente(double pesoKg, double distanciaKm, double esperado) {
        double resultado = strategy.calcular(pesoKg, distanciaKm);
        assertEquals(esperado, resultado, 0.001);
    }

    @ParameterizedTest(name = "Distancia no permitida: {0}km con peso: {1}kg")
    @CsvSource({
        "300.1, 5.0",
        "350.0, 10.0",
        "500.0, 25.0"
    })
    @DisplayName("Debe lanzar IllegalArgumentException cuando la distancia supera los 300 km")
    void debeLanzarExcepcionCuandoDistanciaSupera300Km(double distanciaKm, double pesoKg) {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> strategy.calcular(pesoKg, distanciaKm)
        );
        assertEquals("OVERNIGHT no disponible para mas de 300 km", exception.getMessage());
    }
}
