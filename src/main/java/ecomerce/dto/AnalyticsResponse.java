package ecomerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnalyticsResponse {

    private Long placed;
    private Long shipped;
    private Long delerived;
    private Long currentMonthOrder;
    private Long previousMonthOrders;
    private Long currentMonthEarnings;
    private Long prvioustMonthEarnings;
}
