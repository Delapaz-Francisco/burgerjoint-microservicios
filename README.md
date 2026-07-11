# BurgerJoint Microservicios - Hamburguesería

## 1. Descripción del Contexto (Dominio del Proyecto)
Este proyecto consiste en un sistema distribuido basado en microservicios para la gestión integral de una cadena de hamburgueserías. Permite la administración de la carta de productos, el procesamiento de pedidos de clientes en tiempo real y la asignación automática de despachos a repartidores.

## 2. Integrante (Equipo 15)
* Francisco Delapaz

## 3. Listado de Microservicios Implementados
* **eureka-server**: Servidor de descubrimiento para registrar los servicios.
* **api-gateway**: Enrutador perimetral del sistema (Puerto 8090).
* **delivery-service**: Gestión de repartos y motorizados (Puerto 8084).
* **order-service**: Procesamiento de compras y estado de pedidos.
* **menu-service**: Catálogo de hamburguesas y combos disponibles.
* **customer-service**: Registro e historial de clientes.

## 4. Rutas Principales del Gateway (Puerto 8090)
El API Gateway centraliza todas las peticiones externas bajo el puerto `8090` y las redirige dinámicamente mediante Eureka:
* **Clientes:** `http://localhost:8090/api/clientes`
* **Menús:** `http://localhost:8090/api/menus`
* **Pedidos:** `http://localhost:8090/api/pedidos`
* **Envíos:** `http://localhost:8090/api/envios


## 5. Enlaces a la Documentación Swagger
* **Swagger Local (Delivery Service):**http://localhost:8084/swagger-ui/index.html#/envio-controller/crear

## 6. Instrucciones Básicas de Ejecución
1. Asegurarse de tener iniciado Docker Desktop.
2. Abrir una terminal en la raíz del proyecto y ejecutar: `docker-compose up -d`