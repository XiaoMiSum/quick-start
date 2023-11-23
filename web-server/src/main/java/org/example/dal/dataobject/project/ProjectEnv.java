package org.example.dal.dataobject.project;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.migoo.framework.mybatis.core.dataobject.BaseDO;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "mg_project_env", autoResultMap = true)
@Data
public class ProjectEnv extends BaseDO {

    @TableId
    private Long id;

    private Long projectId;

    private String type;

    private String protocol;

    private String host;

    private String port;

    private String memo;
}
