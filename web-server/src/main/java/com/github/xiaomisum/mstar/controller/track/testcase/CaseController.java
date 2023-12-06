/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021.  Lorem XiaoMiSum (mi_xiao@qq.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * 'Software'), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.xiaomisum.mstar.controller.track.testcase;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.core.util.StrUtil;
import com.github.xiaomisum.mstar.controller.track.testcase.vo.recycle.TestcaseRecyclePageRespVO;
import com.github.xiaomisum.mstar.controller.track.testcase.vo.testcase.*;
import com.github.xiaomisum.mstar.convert.track.TestcaseConvert;
import com.github.xiaomisum.mstar.dal.dataobject.track.Testcase;
import com.github.xiaomisum.mstar.dal.dataobject.track.TestcaseNode;
import com.github.xiaomisum.mstar.dal.dataobject.track.TestcaseRecycle;
import com.github.xiaomisum.mstar.enums.ErrorCodeConstants;
import com.github.xiaomisum.mstar.model.dto.TestcaseStep;
import com.github.xiaomisum.mstar.service.project.ProjectService;
import com.github.xiaomisum.mstar.service.track.testcase.NodeService;
import com.github.xiaomisum.mstar.service.track.testcase.TestcaseRecycleService;
import com.github.xiaomisum.mstar.service.track.testcase.TestcaseService;
import com.google.common.collect.Lists;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Workbook;
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

import static cn.afterturn.easypoi.excel.entity.enmus.ExcelType.XSSF;

@RestController
@RequestMapping("/track/case")
public class CaseController {

    @Resource
    private TestcaseService service;
    @Resource
    private TestcaseRecycleService recycleService;
    @Resource
    private ProjectService projectService;
    @Resource
    private NodeService moduleService;

    @GetMapping
    public Result<?> getPage(@RequestHeader("x-project-id") String projectId, TestcaseQueryReqVO req) {
        // 获得测试用例分页列表
        req.setProjectId(projectId);
        PageResult<TestcasePageRespVO> result = TestcaseConvert.INSTANCE.convert(service.getPage(req));
        result.getList().forEach(item -> {
            if (item.getNodeId().length() > 10) {
                item.setPath(moduleService.get(item.getNodeId()).getPath());
            }
        });
        return Result.getSuccessful(result);
    }

    @GetMapping("/{id}")
    public Result<?> get(@PathVariable String id) {
        return Result.getSuccessful(TestcaseConvert.INSTANCE.convert1(service.get(id)));
    }

    @PostMapping
    public Result<?> add(@RequestHeader("x-project-id") String projectId, @RequestBody TestcaseAddReqVO data) {
        data.setProjectId(projectId);
        String id = service.add(TestcaseConvert.INSTANCE.convert(data));
        return Result.getSuccessful(id);
    }

    @PutMapping
    public Result<?> update(@RequestHeader("x-project-id") String projectId, @RequestBody TestcaseUpdateReqVO data) {
        data.setProjectId(projectId);
        service.update(TestcaseConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    @DeleteMapping
    public Result<?> remove(String ids) {
        service.remove(StrUtil.split(ids, ",").stream().map(String::valueOf).toList());
        return Result.getSuccessful();
    }

    @SneakyThrows
    @GetMapping("/download")
    public void downloadTestcase(@RequestHeader("x-project-id") String projectId, boolean template,
                                 HttpServletResponse response) {
        List<TestcaseExportVO> exports;
        if (template) {
            exports = Lists.newArrayList(new TestcaseExportVO("测试用例", "", "P0",
                    "可随意设置标签，多个标签以英文逗号分割", "前置条件，可空",
                    Lists.newArrayList(new TestcaseStep("步骤1", "期望结果1", ""),
                            new TestcaseStep("步骤2", "期望结果2", ""))));
        } else {
            exports = TestcaseConvert.INSTANCE.convert1(service.getList(projectId));
            if (exports.isEmpty()) {
                throw ServiceExceptionUtil.get(ErrorCodeConstants.TEST_CASE_IMPORT_ERROR);
            }
        }
        List<TestcaseNode> modules = moduleService.getList(projectId);
        // 指定单元格转换字典
        TestcaseExportVO.TestcaseExcelDictHandler handler = new TestcaseExportVO.TestcaseExcelDictHandler();
        handler.add("module", modules);
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
    public Result<?> importTestcase(@RequestHeader("x-project-id") String projectId, @CurrentUser LoginUser user,
                                    @RequestPart MultipartFile file) {
        List<TestcaseNode> modules = moduleService.getList(projectId);
        // 指定单元格转换字典
        TestcaseExportVO.TestcaseExcelDictHandler handler = new TestcaseExportVO.TestcaseExcelDictHandler();
        handler.add("module", modules);
        ImportParams params = new ImportParams();
        params.setDictHandler(handler);
        params.setHeadRows(2);
        List<TestcaseExportVO> imports = ExcelImportUtil.importExcel(file.getInputStream(), TestcaseExportVO.class, params);
        List<Testcase> testcases = TestcaseConvert.INSTANCE.convert2(imports);
        testcases.forEach(item -> item.setMaintainer(user.getName()).setProjectId(projectId));
        service.add(testcases);
        return Result.getSuccessful();
    }

    @GetMapping("/recycle")
    public Result<?> getRecycleTestcase(@RequestHeader("x-project-id") String projectId, TestcaseQueryReqVO req) {
        req.setProjectId(projectId);
        PageResult<TestcaseRecyclePageRespVO> result = TestcaseConvert.INSTANCE.convert1(recycleService.getPage(req));
        result.getList().forEach(item -> {
            if (item.getNodeId().length() > 10) {
                item.setPath(moduleService.get(item.getNodeId()).getPath());
            }
        });
        return Result.getSuccessful(result);
    }

    @DeleteMapping("/recycle")
    public Result<?> removeRecycle(String ids) {
        recycleService.remove(StrUtil.split(ids, ",").stream().map(String::valueOf).toList());
        return Result.getSuccessful();
    }

    @PostMapping("/recycle")
    public Result<?> recoverTestcase(String ids) {
        List<String> list = StrUtil.split(ids, ",").stream().map(String::valueOf).toList();
        if (!list.isEmpty()) {
            List<String> cases = recycleService.getList(list).stream().map(TestcaseRecycle::getCaseId).toList();
            if (!cases.isEmpty()) {
                service.recover(cases);
                recycleService.remove(list);
            }
        }
        return Result.getSuccessful();
    }
}
