package com.mugen.inventory.entity.model.vo.response;

import lombok.Data;

import java.util.Date;

@Data
public class AuthorizeVO {
    Integer id;
    String name;
    String token;
    Date expire;
}
