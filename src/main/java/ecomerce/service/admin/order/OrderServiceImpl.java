package ecomerce.service.admin.order;

import ecomerce.dto.AnalyticsResponse;
import ecomerce.dto.DateDto;
import ecomerce.dto.OrderDto;
import ecomerce.enums.OrderStatus;
import ecomerce.models.Order;
import ecomerce.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderDto> getAllPlaceOrders(){

        List<Order> orderList = orderRepository.findAllByOrderStatusIn(List.of(OrderStatus.Placed,OrderStatus.Shipped, OrderStatus.Delivered));
        return orderList.stream().map(Order::getOrderDto).collect(Collectors.toList());
    }

    public OrderDto changeOrderStatus( Long idOrder, String status) {
        Optional<Order> optionalOrder = orderRepository.findById(idOrder);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            if (Objects.equals(status, "Shipped")) {
                order.setOrderStatus(OrderStatus.Shipped);
            } else if(Objects.equals(status, "Delivered")) {
                order.setOrderStatus(OrderStatus.Delivered);
            }
            return orderRepository.save(order).getOrderDto();
        }
        return null;
    }

    @Override
    public AnalyticsResponse calculateAnalytics() {
        LocalDate currentDate = LocalDate.now();
        LocalDate previousMonthDate = currentDate.minusMonths(1);

        Long currentMonthOrders = getTotalOrdersForMonth(currentDate.getMonthValue(), currentDate.getYear());
        Long previousMonthOrders = getTotalOrdersForMonth(previousMonthDate.getMonthValue(), previousMonthDate.getYear());

        Long currentMonthEarning = getTotalEarningsForMonth(currentDate.getMonthValue(), currentDate.getYear());
        Long previousMonthEarnings = getTotalEarningsForMonth(previousMonthDate.getMonthValue(), previousMonthDate.getYear());

        Long placed = orderRepository.countByOrderStatus(OrderStatus.Placed);
        Long shipped = orderRepository.countByOrderStatus(OrderStatus.Shipped);
        Long delerived = orderRepository.countByOrderStatus(OrderStatus.Delivered);

        return new AnalyticsResponse(placed, shipped, delerived, currentMonthOrders, previousMonthOrders,
                currentMonthEarning, previousMonthEarnings);
    }

    public Long getTotalOrdersForMonth(int month, int year){
        DateDto dateDto = dateMonth(month,year);

        List<Order> orders = orderRepository.findByDateBetweenAndOrderStatus(dateDto.getStartOfMonth(),dateDto.getEndofMonth(), OrderStatus.Delivered);

        return (long) orders.size();
    }

    public Long getTotalEarningsForMonth(int month, int year) {
        DateDto dateDto = dateMonth(month,year);
        List<Order> orders = orderRepository.findByDateBetweenAndOrderStatus(dateDto.getStartOfMonth(),dateDto.getEndofMonth(), OrderStatus.Delivered);

        Long sum = 0L;

        for (Order order: orders) {
            sum += order.getAmount();
        }

        return sum;
    }

    public DateDto dateMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();

        DateDto dateDto = new DateDto();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date startOfMonth = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        Date endofMonth = calendar.getTime();

        dateDto.setStartOfMonth(startOfMonth);
        dateDto.setEndofMonth(endofMonth);

        return dateDto ;
    }
}
