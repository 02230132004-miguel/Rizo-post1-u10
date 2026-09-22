package com.universidad.enviosservice.estrategia;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnvioEstandarStrategyTest {

    private EnvioEstandarStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new EnvioEstandarStrategy();
    }

    @ParameterizedTest(name = "Peso: {0}kg, Distancia: {1}km -> Esperado: ${2}")
    @CsvSource({
        // Tramo 1: peso <= 5 kg (distancia <= 500 km)
        "3.0, 100.0, 50.0",
        "5.0, 500.0, 250.0",
        // Tramo 1: peso <= 5 kg (distancia > 500 km, recargo +10)
        "5.0, 600.0, 310.0",

        // Tramo 2: 5 < peso <= 20 kg (distancia <= 500 km)
        "10.0, 200.0, 104.0",
        "20.0, 400.0, 212.0",
        // Tramo 2: 5 < peso <= 20 kg (distancia > 500 km, recargo +10)
        "10.0, 600.0, 314.0",
        "20.0, 800.0, 422.0",

        // Tramo 3: peso > 20 kg (distancia <= 500 km)
        "25.0, 200.0, 118.0",
        "30.0, 500.0, 274.0",
        // Tramo 3: peso > 20 kg (distancia > 500 km, recargo +10)
        "25.0, 600.0, 328.0"
    })
    @DisplayName("Debe calcular correctamente el costo de envío estándar para los tramos de peso y distancias")
    void debeCalcularCostoEstandarCorrectamente(double pesoKg, double distanciaKm, double esperado) {
        double resultado = strategy.calcular(pesoKg, distanciaKm);
        assertEquals(esperado, resultado, 0.001);
    }
}
