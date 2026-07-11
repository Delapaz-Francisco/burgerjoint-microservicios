package com.burgerjoint.deliveryservice.service;

import com.burgerjoint.deliveryservice.client.PedidoFeignClient;
import com.burgerjoint.deliveryservice.dto.EnvioDTO;
import com.burgerjoint.deliveryservice.model.Envio;
import com.burgerjoint.deliveryservice.repository.EnvioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EnvioServiceTest {

    @Mock
    private EnvioRepository envioRepository;

    @Mock
    private PedidoFeignClient pedidoFeignClient;

    @InjectMocks
    private EnvioService envioService;

    @BeforeEach
    void setUp() {
        // Inicializa Mockito antes de cada caso de prueba
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void cuandoCrearEnvioConPedidoExistente_entoncesRetornaEnvioExitoso() {
        // --- 1. GIVEN (Preparación del escenario simulado) ---
        Long pedidoIdExistente = 7L;

        // Simulamos la entrada de datos (el DTO que vendría de Postman)
        EnvioDTO dtoEntrada = new EnvioDTO();
        dtoEntrada.setPedidoId(pedidoIdExistente);
        dtoEntrada.setDireccion("Av. Las Condes 1234");
        dtoEntrada.setRepartidor("Juan Pérez");

        // Mock: Cuando Feign Client busque el pedido 7, responderá que SÍ existe (retornando un Object genérico)
        when(pedidoFeignClient.obtenerPedidoPorId(pedidoIdExistente)).thenReturn(new Object());

        // Mock: Cuando el repositorio intente guardar, simulamos que la BD le autogenera la ID 3
        Envio envioSimuladoGuardado = new Envio();
        envioSimuladoGuardado.setId(3L);
        envioSimuladoGuardado.setPedidoId(pedidoIdExistente);
        envioSimuladoGuardado.setDireccion("Av. Las Condes 1234");
        envioSimuladoGuardado.setRepartidor("Juan Pérez");
        envioSimuladoGuardado.setEstado("PENDIENTE");

        when(envioRepository.save(any(Envio.class))).thenReturn(envioSimuladoGuardado);


        // --- 2. WHEN (Ejecución del método real a probar) ---
        EnvioDTO resultado = envioService.crearEnvio(dtoEntrada);


        // --- 3. THEN (Verificaciones de calidad y negocio) ---
        assertNotNull(resultado, "El resultado no debería ser nulo");
        assertEquals(3L, resultado.getId(), "La ID del envío guardado debería ser 3");
        assertEquals("PENDIENTE", resultado.getEstado(), "El estado inicial debería ser PENDIENTE");

        // Verificación de comportamiento: El repositorio de BD se debió llamar exactamente 1 vez
        verify(envioRepository, times(1)).save(any(Envio.class));
    }

    @Test
    void cuandoCrearEnvioConPedidoInexistente_entoncesLanzaExcepcion() {
        // --- 1. GIVEN (Escenario donde el pedido NO existe) ---
        Long pedidoIdNoExistente = 999L;

        EnvioDTO dtoEntrada = new EnvioDTO();
        dtoEntrada.setPedidoId(pedidoIdNoExistente);
        dtoEntrada.setDireccion("Calle Falsa 123");

        // Mock: Simulamos que el microservicio de pedidos arroja una excepción (o devuelve null)
        // Como tu código maneja un catch general, si arroja RuntimeException irá directo al catch de error.
        when(pedidoFeignClient.obtenerPedidoPorId(pedidoIdNoExistente))
                .thenThrow(new RuntimeException("Servicio no disponible o ID inválida"));


        // --- 2. WHEN & THEN juntos (Verificar que se lance el error esperado) ---
        IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class, () -> {
            envioService.crearEnvio(dtoEntrada);
        });

        // Validamos que el mensaje del error sea el estricto que escribiste en tu service
        assertEquals("No se puede registrar el envío: El pedido no existe o el servicio de pedidos está caído.", excepcion.getMessage());

        // Verificación crítica de calidad: Como el pedido falló, la base de datos NUNCA debió guardar el envío
        verify(envioRepository, never()).save(any(Envio.class));
    }

}
