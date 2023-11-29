package org.example.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import org.example.controller.tracked.testcase.vo.TestcaseStep;

import java.util.List;

@Data
public class TestcaseDTO {

    private Long caseId;

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
}
