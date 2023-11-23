package org.example.dal.dataobject.st;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.controller.st.testcase.vo.TestcaseStep;
import xyz.migoo.framework.mybatis.core.dataobject.BaseDO;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "mg_st_review_case", autoResultMap = true)
@Data
public class ReviewCase extends BaseDO {

    @TableId
    private Long id;

    private Long projectId;

    private Long reviewId;

    private Long moduleId;

    private Long caseId;

    private String name;

    private String level;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> tags;

    private String precondition;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<TestcaseStep> steps;

    private Long chargeUserId;

    private Long reviewer;

    private Date reviewTime;

    private String reviewResult;

}
