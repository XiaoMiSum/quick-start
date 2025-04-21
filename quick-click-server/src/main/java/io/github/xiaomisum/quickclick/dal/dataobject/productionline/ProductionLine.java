package io.github.xiaomisum.quickclick.dal.dataobject.productionline;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.migoo.framework.mybatis.core.dataobject.BaseDO;

/**
 * 产品线
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "qc_production_line", autoResultMap = true)
@Data
public class ProductionLine extends BaseDO<String> {

    /**
     * 产线名称
     */
    private String title;

    /**
     * 产线总监
     */
    private Long manager;

    /**
     * 备注
     */
    private String memo;

}
