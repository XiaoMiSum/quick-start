package org.example.dal.dataobject.project;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.migoo.framework.mybatis.core.dataobject.BaseDO;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "mg_project", autoResultMap = true)
@Data
public class Project extends BaseDO {

    @TableId
    private Long id;

    private String name;

    private Integer status;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> productManagers;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> developers;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> testers;

    private String memo;
}
