package org.example.dal.dataobject.st;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.controller.st.testcase.vo.TestcaseStep;
import xyz.migoo.framework.mybatis.core.dataobject.BaseDO;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "mg_st_testcase", autoResultMap = true)
@Data
public class Testcase extends BaseDO {

    @TableId
    private Long id;

    private Long projectId;

    private Long moduleId;

    private String name;

    private String level;

    private String precondition;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<TestcaseStep> steps;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> tags;

    private Long chargeUserId;

    private String reviewed;

}
