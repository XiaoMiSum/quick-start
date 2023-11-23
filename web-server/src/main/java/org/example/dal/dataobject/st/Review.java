package org.example.dal.dataobject.st;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.migoo.framework.mybatis.core.dataobject.BaseDO;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "mg_st_review", autoResultMap = true)
@Data
public class Review extends BaseDO {

    @TableId
    private Long id;

    private Long projectId;

    private String name;

    private Long speaker;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> reviewers;

    private Date expectedStartTime;

    private Date expectedEndTime;

    private Date actualStartTime;

    private Date actualEndTime;

    private String status;

    private String memo;
}
