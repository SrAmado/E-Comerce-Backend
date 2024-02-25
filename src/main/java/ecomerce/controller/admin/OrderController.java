package ecomerce.controller.admin;

import ecomerce.service.admin.order.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order", description = "Endpoind para la gestión de Ordenes")
@RestController
@RequestMapping("/api/admin")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @GetMapping("/placed-orders")
    public ResponseEntity<?> getAllPlacedOrders() {
        return ResponseEntity.ok(orderService.getAllPlaceOrders());
    }

    @GetMapping("/order/analytics")
    public ResponseEntity<?> calculateAnalytics() {
        return ResponseEntity.ok(orderService.calculateAnalytics());
    }


    @GetMapping("/order/{idOrder}/{status}")
    public ResponseEntity<?> changeOrderStatus(@PathVariable Long idOrder, @PathVariable String status) {
        return ResponseEntity.ok(orderService.changeOrderStatus(idOrder,status));
    }
}
