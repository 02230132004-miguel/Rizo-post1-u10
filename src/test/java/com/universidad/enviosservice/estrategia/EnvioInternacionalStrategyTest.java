package com.universidad.enviosservice.estrategia;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnvioInternacionalStrategyTest {

    private EnvioInternacionalStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new EnvioInternacionalStrategy();
    }

    @ParameterizedTest(name = "Peso: {0}kg, Distancia: {1}km -> Esperado: ${2}")
    @CsvSource({
        // Tramo 1: peso <= 5 kg (distancia <= 5000 km)
        "3.0, 1000.0, 1850.0",
        "5.0, 5000.0, 9050.0",
        // Tramo 1: peso <= 5 kg (distancia > 5000 km, recargo +100)
        "5.0, 6000.0, 10950.0",

        // Tramo 2: 5 < peso <= 20 kg (distancia <= 5000 km)
        "10.0, 1000.0, 1865.0",
        "20.0, 3000.0, 5495.0",
        // Tramo 2: 5 < peso <= 20 kg (distancia > 5000 km, recargo +100)
        "15.0, 6000.0, 10980.0",

        // Tramo 3: peso > 20 kg (distancia <= 5000 km)
        "25.0, 1000.0, 1917.5",
        // Tramo 3: peso > 20 kg (distancia > 5000 km, recargo +100)
        "30.0, 7000.0, 12840.0"
    })
    @DisplayName("Debe calcular correctamente el costo de envío internacional con recargos por distancia")
    void debeCalcularCostoInternacionalCorrectamente(double pesoKg, double distanciaKm, double esperado) {
        double resultado = strategy.calcular(pesoKg, distanciaKm);
        assertEquals(esperado, resultado, 0.001);
    }
}
