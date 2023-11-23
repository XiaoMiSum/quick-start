package org.example.dal.dataobject;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import xyz.migoo.framework.mybatis.core.dataobject.BaseDO;

import java.util.Date;

@TableName("infra_error_log")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorLogDO extends BaseDO {

    @TableId
    private Long id;

    private String applicationName;

    private String requestMethod;

    private String requestUrl;

    private String requestParams;

    private String userIp;

    private Date exceptionTime;

    private String exceptionName;

    private String exceptionClassName;

    private String exceptionFileName;

    private String exceptionMethodName;

    private Integer exceptionLineNumber;

    private String exceptionStackTrace;

    private String exceptionRootCauseMessage;

    private String exceptionMessage;

    private Integer status;
}
