package ecomerce.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DateDto {
    private Date startOfMonth;
    private Date endofMonth;
}
