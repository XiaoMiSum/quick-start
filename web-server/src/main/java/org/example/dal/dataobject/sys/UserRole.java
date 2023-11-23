package org.example.dal.dataobject.sys;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.migoo.framework.mybatis.core.dataobject.BaseDO;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_user_role", autoResultMap = true)
@Data
public class UserRole extends BaseDO {

    @TableId
    private Long id;
    /**
     * 用户 ID
     */
    private Long userId;
    /**
     * 角色 ID
     */
    private Long roleId;

}
