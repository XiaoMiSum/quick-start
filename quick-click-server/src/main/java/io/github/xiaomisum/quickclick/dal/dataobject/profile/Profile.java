package io.github.xiaomisum.quickclick.dal.dataobject.profile;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.migoo.framework.mybatis.core.dataobject.BaseDO;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_user_profile", autoResultMap = true)
@Data
public class Profile extends BaseDO<Long> {

    private Long userId;

    private String projectId;
}
