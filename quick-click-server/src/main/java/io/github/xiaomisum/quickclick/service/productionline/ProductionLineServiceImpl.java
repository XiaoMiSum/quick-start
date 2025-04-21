package io.github.xiaomisum.quickclick.service.productionline;

import cn.hutool.core.util.IdUtil;
import io.github.xiaomisum.quickclick.controller.productionline.vo.ProductionLineQueryReqVO;
import io.github.xiaomisum.quickclick.dal.dataobject.productionline.ProductionLine;
import io.github.xiaomisum.quickclick.dal.mapper.productionline.ProductionLineMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

@Service
public class ProductionLineServiceImpl implements ProductionLineService {

    @Resource
    private ProductionLineMapper mapper;

    @Override
    public PageResult<ProductionLine> getPage(ProductionLineQueryReqVO req) {
        return mapper.selectPage(req);
    }

    @Override
    public List<ProductionLine> getList() {
        return mapper.selectList();
    }

    @Override
    public ProductionLine get(String id) {
        return mapper.selectById(id);
    }

    @Override
    public void add(ProductionLine data) {
        data.setId(IdUtil.getSnowflakeNextIdStr());
        mapper.insert(data);
    }

    @Override
    public void update(ProductionLine data) {
        mapper.updateById(data);
    }

    @Override
    public void remove(List<String> ids) {
        mapper.deleteByIds(ids);
    }
}
