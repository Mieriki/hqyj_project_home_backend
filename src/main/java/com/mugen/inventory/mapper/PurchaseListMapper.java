package com.mugen.inventory.mapper;

import com.mugen.inventory.entity.PurchaseList;
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
 * @since 2024-07-31
 */
@Mapper
public interface PurchaseListMapper extends BaseMapper<PurchaseList> {
    @Select("SELECT\n" +
            "    YEAR(purchase_date) AS date,\n" +
            "    COUNT(*) AS num,\n" +
            "    SUM(amount_paid) AS paid\n" +
            "FROM t_purchase_list\n" +
            "WHERE purchase_date >= DATE_SUB(NOW(), INTERVAL 7 YEAR)\n" +
            "GROUP BY YEAR(purchase_date);")
    List<Turnover> selectTurnoverYear();

    @Select("SELECT\n" +
            "    YEAR(purchase_date) AS year,\n" +
            "    MONTH(purchase_date) AS date,\n" +
            "    COUNT(*) AS num,\n" +
            "    SUM(amount_paid) AS paid\n" +
            "FROM t_purchase_list\n" +
            "WHERE purchase_date >= DATE_SUB(NOW(), INTERVAL 1 YEAR)\n" +
            "GROUP BY YEAR(purchase_date), MONTH(purchase_date);")
    List<Turnover> selectTurnoverMonth();

    @Select("SELECT\n" +
            "    DATE(purchase_date) AS date,\n" +
            "    COUNT(*) AS num,\n" +
            "    SUM(amount_paid) AS paid\n" +
            "FROM t_purchase_list\n" +
            "WHERE purchase_date >= DATE_SUB(NOW(), INTERVAL 7 DAY)\n" +
            "GROUP BY DATE(purchase_date);")
    List<Turnover> selectTurnoverDay();
}
