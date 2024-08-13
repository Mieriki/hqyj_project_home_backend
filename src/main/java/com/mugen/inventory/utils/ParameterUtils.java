package com.mugen.inventory.utils;

public class ParameterUtils {
    public static Integer getCurrentPage(Integer currentPage, Integer pageSize) {
        if (currentPage == null || currentPage < 1)
            return 1;
        else if (pageSize == null || pageSize < 1)
            return 1;
        return (currentPage - 1) * pageSize;
    }
}
