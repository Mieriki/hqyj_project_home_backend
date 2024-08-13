package com.mugen.inventory.entity.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Turnover {
    private String year;
    private String date;
    private Integer num;
    private Double paid;
}
