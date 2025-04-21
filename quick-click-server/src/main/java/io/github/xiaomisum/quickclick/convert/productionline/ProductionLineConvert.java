package io.github.xiaomisum.quickclick.convert.productionline;

import io.github.xiaomisum.quickclick.controller.productionline.vo.ProductionLineAddReqVO;
import io.github.xiaomisum.quickclick.controller.productionline.vo.ProductionLineRespVO;
import io.github.xiaomisum.quickclick.controller.productionline.vo.ProductionLineUpdateReqVO;
import io.github.xiaomisum.quickclick.dal.dataobject.productionline.ProductionLine;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.SimpleData;

import java.util.List;

@Mapper
public interface ProductionLineConvert {

    ProductionLineConvert INSTANCE = Mappers.getMapper(ProductionLineConvert.class);

    PageResult<ProductionLineRespVO> convert(PageResult<ProductionLine> page);

    List<ProductionLineRespVO> convert(List<ProductionLine> beans);

    ProductionLineRespVO convert(ProductionLine bean);

    ProductionLine convert(ProductionLineAddReqVO bean);

    ProductionLine convert(ProductionLineUpdateReqVO bean);

    List<SimpleData> convert2(List<ProductionLine> beans);

    default SimpleData convert2(ProductionLine bean) {
        return new SimpleData(bean.getId(), bean.getTitle());
    }

}
