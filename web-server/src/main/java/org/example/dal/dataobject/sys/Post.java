package org.example.dal.dataobject.sys;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.migoo.framework.common.enums.CommonStatusEnum;
import xyz.migoo.framework.mybatis.core.dataobject.BaseDO;

@EqualsAndHashCode(callSuper = true)
@TableName("sys_post")
@Data
public class Post extends BaseDO {
    @TableId
    private Long id;

    /**
     * 岗位名称
     */
    private String name;
    /**
     * 岗位编码
     */
    private String code;
    /**
     * 岗位排序
     */
    private Integer sort;
    /**
     * 状态
     * <p>
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;

}
