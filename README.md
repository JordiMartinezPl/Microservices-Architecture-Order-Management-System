# Microservices Architecture – Order Management System

Proyecto académico desarrollado en la **Universitat Pompeu Fabra (UPF)** centrado en el diseño e implementación de una **arquitectura de microservicios** y la orquestación de **SAGAs síncronas**.

## Descripción

El sistema implementa un flujo completo de gestión de pedidos utilizando varios microservicios independientes que colaboran entre sí para garantizar consistencia y tolerancia a fallos.

## Microservicios

- **Customer Service** – Gestión de clientes y pagos  
- **Product Service** – Gestión de productos y stock  
- **Order Service** – Orquestación de pedidos y SAGAs  
- **OrderHistory Service** – Persistencia del historial de pedidos  
- **Delivery Service** – Gestión de entregas *(proporcionado)*  

## Funcionalidades principales

### Create Order SAGA

Implementación de una **SAGA síncrona** basada en el patrón **request/response**:

1. Creación del pedido  
2. Reserva de productos  
3. Reserva de transporte mediante el *Delivery Service*  
4. Finalización del pedido  
5. Publicación de un evento para persistencia en *OrderHistory*  
6. Ejecución de **transacciones compensatorias** en caso de error  

### Cancel Order SAGA

Cancelación de pedidos mediante propagación de eventos entre servicios:

- Cancelación de la entrega (si aún no ha sido enviada)  
- Reembolso del pago al cliente  
- Restauración del stock de productos  
- Actualización del estado del pedido en el historial  

## Conceptos clave

- Arquitectura de microservicios  
- SAGA Pattern (orquestado)  
- Comunicación síncrona entre servicios  
- Eventos y consistencia eventual  
- Transacciones compensatorias  
