package org.example.dal.dataobject.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import xyz.migoo.framework.common.util.json.JsonUtils;
import xyz.migoo.framework.mybatis.core.dataobject.BaseDO;
import xyz.migoo.framework.mybatis.core.handler.JsonLongSetTypeHandler;

import java.util.Set;

@TableName(value = "sys_user", autoResultMap = true)
@Getter
@Setter
public class User extends BaseDO {

    @TableId
    private Long id;

    private String username;

    private String password;

    private String name;

    private String avatar;

    private String mobile;

    private Integer gender;

    private Long deptId;

    @TableField(typeHandler = JsonLongSetTypeHandler.class)
    private Set<Long> postIds;

    private String email;

    private String memo;

    private Integer status;

    @Override
    public String toString() {
        return JsonUtils.toJsonString(this);
    }
}
