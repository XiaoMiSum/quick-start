package org.example.controller.tracked.testcase;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Workbook;
import org.example.controller.tracked.testcase.vo.*;
import org.example.convert.tracked.TestcaseConvert;
import org.example.dal.dataobject.project.ProjectModule;
import org.example.dal.dataobject.project.Tag;
import org.example.dal.dataobject.tracked.Testcase;
import org.example.service.project.ProjectService;
import org.example.service.project.module.ModuleService;
import org.example.service.project.tag.TagService;
import org.example.service.sys.user.UserService;
import org.example.service.tracked.TestcaseService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.migoo.framework.common.exception.util.ServiceExceptionUtil;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.Result;
import xyz.migoo.framework.security.core.LoginUser;
import xyz.migoo.framework.security.core.annotation.CurrentUser;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

import static cn.afterturn.easypoi.excel.entity.enmus.ExcelType.XSSF;
import static org.example.enums.ErrorCodeConstants.TEST_CASE_IMPORT_ERROR;
import static xyz.migoo.framework.common.enums.CommonStatusEnum.ENABLE;

@RestController
@RequestMapping("/tracked/case")
public class CaseController {

    @Resource
    private TestcaseService service;
    @Resource
    private ProjectService projectService;
    @Resource
    private ModuleService moduleService;
    @Resource
    private UserService userService;
    @Resource
    private TagService tagService;

    @GetMapping
    public Result<?> getPage(@RequestHeader("x-project-id") Long projectId, TestcaseQueryReqVO req) {
        // 获得测试用例分页列表
        req.setProjectId(projectId);
        PageResult<TestcasePageRespVO> result = TestcaseConvert.INSTANCE.convert(service.getPage(req));
        result.getList().forEach(item -> {
            if (item.getModuleId() > 0) {
                item.setPath(moduleService.get(projectId, item.getModuleId()).getPath());
            }
            item.setChargeUser(userService.get(item.getChargeUserId()).getName());
        });
        return Result.getSuccessful(result);
    }

    @GetMapping("/{id}")
    public Result<?> get(@PathVariable Long id) {
        return Result.getSuccessful(TestcaseConvert.INSTANCE.convert1(service.get(id)));
    }

    @PostMapping
    public Result<?> add(@RequestHeader("x-project-id") Long projectId, @RequestBody TestcaseAddReqVO data) {
        data.setProjectId(projectId);
        Long id = service.add(TestcaseConvert.INSTANCE.convert(data));
        tagService.save(projectId, data.getTags());
        return Result.getSuccessful(id);
    }

    @PutMapping
    public Result<?> update(@RequestHeader("x-project-id") Long projectId, @RequestBody TestcaseUpdateReqVO data) {
        data.setProjectId(projectId);
        service.update(TestcaseConvert.INSTANCE.convert(data));
        tagService.save(projectId, data.getTags());
        return Result.getSuccessful();
    }

    @DeleteMapping("/{id}")
    public Result<?> remove(@PathVariable Long id) {
        service.remove(id);
        return Result.getSuccessful();
    }

    @DeleteMapping
    public Result<?> remove(String ids) {
        service.remove(StrUtil.split(ids, ",").stream().map(Long::valueOf).toList());
        return Result.getSuccessful();
    }

    @SneakyThrows
    @GetMapping("/download")
    public void downloadTestcase(@RequestHeader("x-project-id") Long projectId, boolean template,
                                 HttpServletResponse response) {
        List<TestcaseExportVO> exports;
        if (template) {
            exports = Lists.newArrayList(new TestcaseExportVO("测试用例", 0L, "P0", "前置条件，可空",
                    Lists.newArrayList(new TestcaseStep("步骤1", "期望结果1", ""),
                            new TestcaseStep("步骤2", "期望结果2", ""))));

        } else {
            exports = TestcaseConvert.INSTANCE.convert1(service.getList(projectId));
            if (exports.isEmpty()) {
                throw ServiceExceptionUtil.get(TEST_CASE_IMPORT_ERROR);
            }
        }
        List<ProjectModule> modules = moduleService.getList(projectId);
        List<Tag> tags = tagService.getList(projectId).stream()
                .filter(item -> Objects.equals(item.getStatus(), ENABLE.getStatus()))
                .toList();
        // 指定单元格转换字典
        TestcaseExportVO.TestcaseExcelDictHandler handler = new TestcaseExportVO.TestcaseExcelDictHandler();
        handler.add("module", modules);
        handler.add("tag", tags);
        ExportParams params = new ExportParams();
        params.setDictHandler(handler);
        params.setSheetName("测试用例");
        params.setType(XSSF);
        String filename = projectService.get(projectId).getName();
        try (Workbook workbook = ExcelExportUtil.exportExcel(params, TestcaseExportVO.class, exports);
             OutputStream os = response.getOutputStream()) {
            workbook.write(os);
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, StandardCharsets.UTF_8));
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        }
    }

    @SneakyThrows
    @PostMapping("/imports")
    public Result<?> importTestcase(@RequestHeader("x-project-id") Long projectId, @CurrentUser LoginUser user,
                                    @RequestPart MultipartFile file) {
        List<ProjectModule> modules = moduleService.getList(projectId);
        List<Tag> tags = tagService.getList(projectId).stream()
                .filter(item -> Objects.equals(item.getStatus(), ENABLE.getStatus()))
                .toList();
        // 指定单元格转换字典
        TestcaseExportVO.TestcaseExcelDictHandler handler = new TestcaseExportVO.TestcaseExcelDictHandler();
        handler.add("module", modules);
        handler.add("tag", tags);
        ImportParams params = new ImportParams();
        params.setDictHandler(handler);
        params.setHeadRows(2);
        List<TestcaseExportVO> imports = ExcelImportUtil.importExcel(file.getInputStream(), TestcaseExportVO.class, params);
        List<Testcase> testcases = TestcaseConvert.INSTANCE.convert2(imports);
        testcases.forEach(item -> item.setChargeUserId(user.getId()).setProjectId(projectId));
        service.add(testcases);
        return Result.getSuccessful();
    }
}
