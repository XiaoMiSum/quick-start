package io.github.xiaomisum.quickclick.controller.productionline.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.migoo.framework.common.pojo.PageParam;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductionLineQueryReqVO extends PageParam {

    /**
     * 产品线名称
     */
    private String title;
}
