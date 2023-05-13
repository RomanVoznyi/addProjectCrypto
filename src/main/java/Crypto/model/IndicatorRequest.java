package Crypto.model;

import lombok.Data;

@Data
public class IndicatorRequest {
    private String indicator;
    private String value;
    private String startDate;
    private String endDate;
}
