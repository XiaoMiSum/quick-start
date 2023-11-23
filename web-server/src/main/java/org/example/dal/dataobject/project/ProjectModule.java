package org.example.dal.dataobject.project;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.migoo.framework.mybatis.core.dataobject.BaseDO;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "mg_project_module", autoResultMap = true)
@Data
public class ProjectModule extends BaseDO {

    @TableId
    private Long id;

    private Long projectId;

    private Long parentId;

    private String name;

    private String path;

    private Integer sort;
}
