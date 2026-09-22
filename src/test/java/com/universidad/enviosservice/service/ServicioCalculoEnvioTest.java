package com.universidad.enviosservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ServicioCalculoEnvioTest {

    private ServicioCalculoEnvio servicio;

    @BeforeEach
    void setUp() {
        servicio = new ServicioCalculoEnvio();
    }

    @Test
    @DisplayName("Debe calcular costo base para método ESTANDAR sin fragilidad ni descuentos")
    void debeCalcularCostoBaseEstandar() {
        double costo = servicio.calcularCosto("ESTANDAR", 4.0, 100.0, "REGULAR", false);
        assertEquals(50.0, costo, 0.001);
    }

    @Test
    @DisplayName("Debe aplicar recargo del 15% por paquete frágil")
    void debeAplicarRecargoFragilidad() {
        double costo = servicio.calcularCosto("ESTANDAR", 4.0, 100.0, "REGULAR", true);
        assertEquals(57.5, costo, 0.001); // 50.0 * 1.15
    }

    @Test
    @DisplayName("Debe aplicar descuento del 10% para cliente PREMIUM")
    void debeAplicarDescuentoClientePremium() {
        double costo = servicio.calcularCosto("ESTANDAR", 4.0, 100.0, "PREMIUM", false);
        assertEquals(45.0, costo, 0.001); // 50.0 * 0.90
    }

    @Test
    @DisplayName("Debe aplicar descuento del 15% para cliente CORPORATIVO")
    void debeAplicarDescuentoClienteCorporativo() {
        double costo = servicio.calcularCosto("ESTANDAR", 4.0, 100.0, "CORPORATIVO", false);
        assertEquals(42.5, costo, 0.001); // 50.0 * 0.85
    }

    @Test
    @DisplayName("Debe aplicar recargo por fragilidad y descuento PREMIUM combinados")
    void debeAplicarFragilidadYDescuentoPremium() {
        double costo = servicio.calcularCosto("ESTANDAR", 4.0, 100.0, "PREMIUM", true);
        assertEquals(51.75, costo, 0.001); // 50.0 * 1.15 * 0.90
    }

    @Test
    @DisplayName("Debe aplicar recargo por fragilidad y descuento CORPORATIVO combinados")
    void debeAplicarFragilidadYDescuentoCorporativo() {
        double costo = servicio.calcularCosto("ESTANDAR", 4.0, 100.0, "CORPORATIVO", true);
        assertEquals(48.875, costo, 0.001); // 50.0 * 1.15 * 0.85
    }

    @ParameterizedTest(name = "Método: {0}, Peso: {1}kg, Distancia: {2}km, Cliente: {3}, Frágil: {4} -> Costo: ${5}")
    @CsvSource({
        "EXPRESS, 4.0, 100.0, REGULAR, false, 156.0",
        "EXPRESS, 4.0, 100.0, REGULAR, true, 179.4",
        "EXPRESS, 4.0, 100.0, PREMIUM, false, 140.4",
        "EXPRESS, 4.0, 100.0, CORPORATIVO, false, 132.6",
        "OVERNIGHT, 4.0, 100.0, REGULAR, false, 220.0",
        "OVERNIGHT, 4.0, 100.0, REGULAR, true, 253.0",
        "OVERNIGHT, 4.0, 100.0, PREMIUM, false, 198.0",
        "OVERNIGHT, 4.0, 100.0, CORPORATIVO, false, 187.0",
        "INTERNACIONAL, 4.0, 1000.0, REGULAR, false, 1850.0",
        "INTERNACIONAL, 4.0, 1000.0, REGULAR, true, 2127.5",
        "INTERNACIONAL, 4.0, 1000.0, PREMIUM, false, 1665.0",
        "INTERNACIONAL, 4.0, 1000.0, CORPORATIVO, false, 1572.5"
    })
    @DisplayName("Debe calcular correctamente los costos para los distintos métodos y tipos de cliente")
    void debeCalcularCostosParaMultiplesMetodosYClientes(String metodo, double peso, double distancia, String cliente, boolean fragil, double esperado) {
        double costo = servicio.calcularCosto(metodo, peso, distancia, cliente, fragil);
        assertEquals(esperado, costo, 0.001);
    }

    @Test
    @DisplayName("Debe calcular correctamente cuando el cliente es null o tipo no reconocido (sin descuento)")
    void debeManejarClienteNuloOSinDescuento() {
        double costoNull = servicio.calcularCosto("ESTANDAR", 4.0, 100.0, null, false);
        assertEquals(50.0, costoNull, 0.001);

        double costoDesconocido = servicio.calcularCosto("ESTANDAR", 4.0, 100.0, "OTRO", false);
        assertEquals(50.0, costoDesconocido, 0.001);
    }

    @ParameterizedTest(name = "Método inválido: {0}")
    @ValueSource(strings = {"DRONE", "MISIL", "TELETRANSPORTE", "INVALIDO", ""})
    @DisplayName("Debe lanzar IllegalArgumentException cuando el método de envío no es soportado")
    void debeLanzarExcepcionParaMetodoNoSoportado(String metodoInvalido) {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> servicio.calcularCosto(metodoInvalido, 5.0, 100.0, "REGULAR", false)
        );
        assertEquals("Metodo de envio no soportado: " + metodoInvalido, exception.getMessage());
    }
}
