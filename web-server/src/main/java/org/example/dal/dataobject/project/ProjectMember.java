package org.example.dal.dataobject.project;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.migoo.framework.mybatis.core.dataobject.BaseDO;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "mg_project_member", autoResultMap = true)
@Data
public class ProjectMember extends BaseDO {

    @TableId
    private Long id;

    private Long projectId;

    private Long userId;
}
