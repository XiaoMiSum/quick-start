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

package io.github.xiaomisum.quickclick.controller.quality.testcase;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import io.github.xiaomisum.quickclick.controller.quality.testcase.vo.*;
import io.github.xiaomisum.quickclick.convert.qualitycenter.TestcaseConvert;
import io.github.xiaomisum.quickclick.dal.dataobject.project.ProjectNode;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.Testcase;
import io.github.xiaomisum.quickclick.enums.ErrorCodeConstants;
import io.github.xiaomisum.quickclick.model.dto.CaseStep;
import io.github.xiaomisum.quickclick.service.project.NodeService;
import io.github.xiaomisum.quickclick.service.project.ProjectService;
import io.github.xiaomisum.quickclick.service.qualitycenter.testcase.TestcaseService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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

/**
 * 测试用例
 */
@RestController
@RequestMapping("/quality-center/testcase")
public class CaseController {

    @Resource
    private TestcaseService service;
    @Resource
    private ProjectService projectService;
    @Resource
    private NodeService nodeService;

    /**
     * 用例列表
     * <p>
     * 通过 deleted 区分回收站
     *
     * @param req 查询条件
     * @return 用例列表
     */
    @GetMapping
    public Result<?> getPage(TestcaseQueryReqVO req) {
        // 获得测试用例分页列表
        PageResult<TestcasePageRespVO> result = TestcaseConvert.INSTANCE.convert(service.getPage(req));
        result.getList().forEach(item -> {
            if (StrUtil.isNotBlank(item.getNodeId())) {
                item.setPath(nodeService.get(item.getNodeId()).getPath());
            }
        });
        return Result.getSuccessful(result);
    }

    /**
     * 用例明细
     *
     * @param id 用例编号
     * @return 用例明细
     */
    @GetMapping("/{id}")
    public Result<?> get(@PathVariable String id) {
        return Result.getSuccessful(TestcaseConvert.INSTANCE.convert1(service.get(id)));
    }

    /**
     * 新增用例
     *
     * @param data 用例信息
     * @return 处理结果
     */
    @PostMapping
    public Result<?> add(@RequestBody @Valid TestcaseAddReqVO data) {
        String id = service.add(TestcaseConvert.INSTANCE.convert(data));
        return Result.getSuccessful(id);
    }

    /**
     * 更新用例
     *
     * @param data 用例信息
     * @return 处理结果
     */
    @PutMapping
    public Result<?> update(@RequestBody @Valid TestcaseUpdateReqVO data) {
        service.update(TestcaseConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    /**
     * 删除用例
     *
     * @param ids 用例编号
     * @return 处理结果
     */
    @DeleteMapping
    public Result<?> remove(@RequestParam("ids") List<String> ids) {
        service.remove(ids);
        return Result.getSuccessful();
    }

    /**
     * 下载用例
     *
     * @param template  是否模板
     * @param projectId 项目编号
     * @param response  x
     */
    @SneakyThrows
    @GetMapping("/download")
    public void downloadTestcase(@RequestParam("template") boolean template, @RequestParam("projectId") String projectId,
                                 HttpServletResponse response) {
        List<TestcaseExportVO> exports;
        if (template) {
            exports = Lists.newArrayList(new TestcaseExportVO("测试用例", "", "P0",
                    "可随意设置标签，多个标签以英文逗号分割", "前置条件，可空",
                    Lists.newArrayList(new CaseStep("步骤1", "期望结果1", ""),
                            new CaseStep("步骤2", "期望结果2", ""))));
        } else {
            exports = TestcaseConvert.INSTANCE.convert1(service.getList(projectId));
            if (exports.isEmpty()) {
                throw ServiceExceptionUtil.get(ErrorCodeConstants.TEST_CASE_IMPORT_ERROR);
            }
        }
        List<ProjectNode> modules = nodeService.getList(projectId);
        // 指定单元格转换字典
        TestcaseExportVO.TestcaseExcelDictHandler handler = new TestcaseExportVO.TestcaseExcelDictHandler();
        handler.add("node", modules);
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

    /**
     * 导入用例
     *
     * @param projectId 项目编号
     * @param user      x
     * @param file      导入文件
     * @return
     */
    @SneakyThrows
    @PostMapping("/imports")
    public Result<?> importTestcase(@RequestParam("projectId") String projectId, @CurrentUser LoginUser user,
                                    @RequestPart MultipartFile file) {
        List<ProjectNode> modules = nodeService.getList(projectId);
        // 指定单元格转换字典
        TestcaseExportVO.TestcaseExcelDictHandler handler = new TestcaseExportVO.TestcaseExcelDictHandler();
        handler.add("node", modules);
        ImportParams params = new ImportParams();
        params.setDictHandler(handler);
        params.setHeadRows(2);
        List<TestcaseExportVO> imports = ExcelImportUtil.importExcel(file.getInputStream(), TestcaseExportVO.class, params);
        List<Testcase> testcases = TestcaseConvert.INSTANCE.convert2(imports);
        testcases.forEach(item -> item.setSupervisor(user.getId()).setProjectId(projectId));
        service.add(testcases);
        return Result.getSuccessful();
    }

    /**
     * 彻底删除用例
     * <p>
     * 当 ids为空时，清空项目回收站
     *
     * @param ids       用例编号
     * @param projectId 项目编号
     * @return 处理结果
     */
    @DeleteMapping("/trash")
    public Result<?> removeRecycle(@RequestParam(value = "ids", required = false) List<String> ids,
                                   @RequestParam("projectId") String projectId) {
        service.removeTrash(ids, projectId);
        return Result.getSuccessful();
    }

    /**
     * 还原用例
     * <p>
     * 当 ids为空时，还原项目回收站
     *
     * @param ids       用例编号
     * @param projectId 项目编号
     * @return 处理结果
     */
    @PostMapping("/trash/recover")
    public Result<?> recover(@RequestParam(value = "ids", required = false) List<String> ids,
                             @RequestParam("projectId") String projectId) {
        service.recover(ids, projectId);
        return Result.getSuccessful();
    }
}
