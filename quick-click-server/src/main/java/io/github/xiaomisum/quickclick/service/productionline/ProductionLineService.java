package io.github.xiaomisum.quickclick.service.productionline;


import io.github.xiaomisum.quickclick.controller.productionline.vo.ProductionLineQueryReqVO;
import io.github.xiaomisum.quickclick.dal.dataobject.productionline.ProductionLine;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

public interface ProductionLineService {


    PageResult<ProductionLine> getPage(ProductionLineQueryReqVO req);

    List<ProductionLine> getList();

    ProductionLine get(String id);

    void add(ProductionLine data);

    void update(ProductionLine data);

    void remove(List<String> ids);
}
