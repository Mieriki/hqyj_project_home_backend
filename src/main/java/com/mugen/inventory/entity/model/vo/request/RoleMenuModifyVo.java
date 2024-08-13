package com.mugen.inventory.entity.model.vo.request;

import lombok.Data;

import java.util.List;

@Data
public class RoleMenuModifyVo {
    private Integer id;
    private List<Integer> menuIdList;
}
