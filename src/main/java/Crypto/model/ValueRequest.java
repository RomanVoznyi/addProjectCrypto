package Crypto.model;

import lombok.Data;

@Data
public class ValueRequest {
    private String currency;
    private String startDate;
    private String endDate;
}
