package com.mugen.inventory.entity.model.vo.response;

import com.mugen.inventory.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder(toBuilder = true)
public class DepartmentTreeVo {

    // id
    private Integer id;

    // 部门名称
    private String name;

    // 父id
    private Integer parentId;

    // 路径
    private String depPath;

    // 是否启用
    private Boolean enabled;

    // 是否上级
    private Boolean isParent;

    // 孩子节点
    List<DepartmentTreeVo> children;
}
