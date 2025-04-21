package io.github.xiaomisum.quickclick.controller.productionline;

import io.github.xiaomisum.quickclick.controller.productionline.vo.ProductionLineAddReqVO;
import io.github.xiaomisum.quickclick.controller.productionline.vo.ProductionLineQueryReqVO;
import io.github.xiaomisum.quickclick.controller.productionline.vo.ProductionLineUpdateReqVO;
import io.github.xiaomisum.quickclick.convert.productionline.ProductionLineConvert;
import io.github.xiaomisum.quickclick.service.productionline.ProductionLineService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.pojo.Result;

import java.util.List;

/**
 * 产线管理
 */
@RestController
@RequestMapping("/production-line")
public class ProductionLineController {


    @Resource
    private ProductionLineService service;

    /**
     * 产品线列表
     *
     * @param req 查询条件
     * @return 产品线列表
     */
    @GetMapping
    public Result<?> getPage(ProductionLineQueryReqVO req) {
        return Result.getSuccessful(ProductionLineConvert.INSTANCE.convert(service.getPage(req)));
    }

    /**
     * 产品线信息
     *
     * @param id 产品线编号
     * @return 产品线信息
     */
    @GetMapping("/{id}")
    public Result<?> get(@PathVariable("id") String id) {
        return Result.getSuccessful(ProductionLineConvert.INSTANCE.convert(service.get(id)));
    }

    /**
     * 新增产品线
     *
     * @param data 产品线信息
     * @return 处理结果
     */
    @PostMapping
    public Result<?> add(@RequestBody ProductionLineAddReqVO data) {
        service.add(ProductionLineConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    /**
     * 更新产品线
     *
     * @param data 产品线信息
     * @return 处理结果
     */
    @PutMapping
    public Result<?> update(@RequestBody ProductionLineUpdateReqVO data) {
        service.update(ProductionLineConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    /**
     * 删除产品线
     *
     * @param id 待删除的产品线
     * @return 处理结果
     */
    @DeleteMapping("/{id}")
    public Result<?> remove(@PathVariable("id") String id) {
        service.remove(List.of(id));
        return Result.getSuccessful();
    }

    /**
     * 获取产品线下拉列表
     *
     * @return 处理结果
     */
    @GetMapping("/simple")
    public Result<?> getSimple() {
        return Result.getSuccessful(ProductionLineConvert.INSTANCE.convert2(service.getList()));
    }
}
