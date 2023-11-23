package org.example.dal.dataobject.st;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.migoo.framework.mybatis.core.dataobject.BaseDO;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "mg_st_testcase", autoResultMap = true)
@Data
public class TestcaseHistory extends BaseDO {

    @TableId
    private Long id;

    private Long projectId;

    private Long caseId;

    private String name;

    private String level;

    private String precondition;

    private String steps;

    private String version;

}
