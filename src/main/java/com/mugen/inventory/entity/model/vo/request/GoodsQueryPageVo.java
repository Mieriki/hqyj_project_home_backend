package com.mugen.inventory.entity.model.vo.request;

import lombok.Data;

import java.util.List;

@Data
public class GoodsQueryPageVo {
    private String name;
    private String producer;
    private List<Integer> typeId;
    private String idList;
    private Integer currentPage;
    private Integer pageSize;
}
