package com.mugen.inventory.mapper;

import com.mugen.inventory.entity.SaleList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mugen.inventory.entity.model.dto.Turnover;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Mieriki
 * @since 2024-08-01
 */
@Mapper
public interface SaleListMapper extends BaseMapper<SaleList> {
    @Select("SELECT\n" +
            "    YEAR(sale_date) AS date,\n" +
            "    COUNT(*) AS num_orders,\n" +
            "    SUM(amount_paid) AS paid\n" +
            "FROM t_sale_list\n" +
            "WHERE sale_date >= DATE_SUB(NOW(), INTERVAL 7 YEAR)\n" +
            "GROUP BY YEAR(sale_date);")
    List<Turnover> selectTurnoverYear();

    @Select("SELECT\n" +
            "    YEAR(sale_date) AS year,\n" +
            "    MONTH(sale_date) AS date,\n" +
            "    COUNT(*) AS num,\n" +
            "    SUM(amount_paid) AS paid\n" +
            "FROM t_sale_list\n" +
            "WHERE sale_date >= DATE_SUB(NOW(), INTERVAL 1 YEAR)\n" +
            "GROUP BY YEAR(sale_date), MONTH(sale_date);")
    List<Turnover> selectTurnoverMonth();

    @Select("SELECT\n" +
            "    DATE(sale_date) AS date,\n" +
            "    SUM(amount_paid) AS paid\n" +
            "FROM t_sale_list\n" +
            "WHERE sale_date >= DATE_SUB(NOW(), INTERVAL 7 DAY)\n" +
            "GROUP BY DATE(sale_date);")
    List<Turnover> selectTurnoverDay();
}
