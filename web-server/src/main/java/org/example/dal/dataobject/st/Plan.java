package org.example.dal.dataobject.st;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.migoo.framework.mybatis.core.dataobject.BaseDO;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "mg_st_plan", autoResultMap = true)
@Data
public class Plan extends BaseDO {

    @TableId
    private Long id;

    private Long projectId;

    private String name;

    private Long executor;

    private Date expectedStartTime;

    private Date expectedEndTime;

    private Date actualStartTime;

    private Date actualEndTime;
}
