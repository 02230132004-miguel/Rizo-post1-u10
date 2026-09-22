package com.universidad.enviosservice.estrategia;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnvioExpressStrategyTest {

    private EnvioExpressStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new EnvioExpressStrategy();
    }

    @ParameterizedTest(name = "Peso: {0}kg, Distancia: {1}km -> Esperado: ${2}")
    @CsvSource({
        // Tramo 1: peso <= 5 kg (distancia <= 500 km)
        "3.0, 100.0, 156.0",
        "5.0, 500.0, 780.0",
        // Tramo 1: peso <= 5 kg (distancia > 500 km, recargo +25 y factor 1.3)
        "5.0, 600.0, 968.5",

        // Tramo 2: 5 < peso <= 20 kg (distancia <= 500 km)
        "10.0, 200.0, 321.75",
        "20.0, 400.0, 653.25",
        // Tramo 2: 5 < peso <= 20 kg (distancia > 500 km, recargo +25 y factor 1.3)
        "15.0, 600.0, 988.0",

        // Tramo 3: peso > 20 kg (distancia <= 500 km)
        "25.0, 200.0, 354.25",
        // Tramo 3: peso > 20 kg (distancia > 500 km, recargo +25 y factor 1.3)
        "30.0, 800.0, 1335.75"
    })
    @DisplayName("Debe calcular correctamente el costo de envío exprés con recargos y factor multiplicador")
    void debeCalcularCostoExpressCorrectamente(double pesoKg, double distanciaKm, double esperado) {
        double resultado = strategy.calcular(pesoKg, distanciaKm);
        assertEquals(esperado, resultado, 0.001);
    }
}
