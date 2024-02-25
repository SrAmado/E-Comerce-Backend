package ecomerce.service.admin.order;


import ecomerce.dto.AnalyticsResponse;
import ecomerce.dto.OrderDto;

import java.util.List;

public interface OrderService {

    List<OrderDto> getAllPlaceOrders();

    OrderDto changeOrderStatus( Long idOrder, String status);

    AnalyticsResponse calculateAnalytics();
}
