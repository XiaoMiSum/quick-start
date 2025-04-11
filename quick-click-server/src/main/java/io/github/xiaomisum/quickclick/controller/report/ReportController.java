package io.github.xiaomisum.quickclick.controller.report;

import io.github.xiaomisum.quickclick.controller.report.vo.ReportQueryReqVO;
import io.github.xiaomisum.quickclick.controller.report.vo.days.DeveloperDaysPageRespVO;
import io.github.xiaomisum.quickclick.controller.report.vo.days.DeveloperDaysUpdateReqVO;
import io.github.xiaomisum.quickclick.convert.report.ReportConvert;
import io.github.xiaomisum.quickclick.service.report.ReportService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.Result;

@RestController
@RequestMapping("charts")
public class ReportController {

    @Resource
    private ReportService service;

    @GetMapping("/days/developer")
    private Result<PageResult<DeveloperDaysPageRespVO>> getDevDays(ReportQueryReqVO req) {

        return Result.getSuccessful(service.getDevDays(req));
    }

    @PutMapping("/days/developer")
    private Result<?> updateDevDays(@RequestBody @Valid DeveloperDaysUpdateReqVO req) {
        service.updateDevDays(ReportConvert.INSTANCE.convert(req));
        return Result.getSuccessful();
    }
}
