package io.github.xiaomisum.quickclick.controller.project.days;

import io.github.xiaomisum.quickclick.controller.project.days.vo.*;
import io.github.xiaomisum.quickclick.convert.report.ReportConvert;
import io.github.xiaomisum.quickclick.service.report.ReportService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.Result;

@RestController
@RequestMapping("/project/days")
public class DaysController {

    @Resource
    private ReportService service;

    @GetMapping("/developer")
    private Result<PageResult<DeveloperDaysPageRespVO>> getDevDays(DaysQueryReqVO req) {

        return Result.getSuccessful(service.getDevDays(req));
    }

    @PutMapping("/developer")
    private Result<?> updateDevDays(@RequestBody @Valid DeveloperDaysUpdateReqVO req) {
        service.updateDevDays(ReportConvert.INSTANCE.convert(req));
        return Result.getSuccessful();
    }

    @GetMapping("/tester")
    private Result<PageResult<TesterDaysPageRespVO>> getTestDays(DaysQueryReqVO req) {
        return Result.getSuccessful(service.getTestDays(req));
    }

    @PutMapping("/tester")
    private Result<?> updateTestDays(@RequestBody @Valid TesterDaysUpdateReqVO req) {
        service.updateTestDays(ReportConvert.INSTANCE.convert(req));
        return Result.getSuccessful();
    }
}
