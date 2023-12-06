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

package com.github.xiaomisum.mstar.controller.project.archive;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.github.xiaomisum.mstar.controller.project.archive.vo.*;
import com.github.xiaomisum.mstar.convert.project.ArchiveConvert;
import com.github.xiaomisum.mstar.service.project.archive.ArchiveService;
import com.github.xiaomisum.mstar.service.sys.user.UserService;
import com.github.xiaomisum.mstar.service.track.testcase.NodeService;
import com.github.xiaomisum.mstar.service.track.testcase.TestcaseService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.Result;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static cn.afterturn.easypoi.excel.entity.enmus.ExcelType.XSSF;

@RestController
@RequestMapping("/project/archive")
public class ArchiveController {

    @Resource
    private ArchiveService service;
    @Resource
    private NodeService nodeService;
    @Resource
    private TestcaseService testcaseService;
    @Resource
    private UserService userService;

    @GetMapping
    public Result<?> getPage(@RequestHeader("x-project-id") String projectId, ArchiveQueryReqVO req) {
        req.setProjectId(projectId);
        return Result.getSuccessful(ArchiveConvert.INSTANCE.convert(service.getPage(req)));
    }

    @GetMapping("/module")
    public Result<?> getModules(String archiveId) {
        return Result.getSuccessful(ArchiveConvert.INSTANCE.convert(service.getModules(archiveId)));
    }

    @GetMapping("/testcase")
    public Result<?> getTestcases(ArchiveTestcaseQueryReqVO req) {
        PageResult<ArchiveTestcasePageRespVO> result = ArchiveConvert.INSTANCE.convert1(service.getTestcasePage(req));
        result.getList().forEach(item -> {
            if (item.getNodeId().length() > 10) {
                item.setPath(service.getModule(req.getArchiveId(), item.getNodeId()).getPath());
            }
        });
        return Result.getSuccessful(result);
    }

    @GetMapping("/testcase/{id}")
    public Result<?> getTestcases(@PathVariable String id) {
        ArchiveTestcaseRespVO result = ArchiveConvert.INSTANCE.convert(service.getTestcase(id));
        if (result.getNodeId().length() > 10) {
            result.setPath(service.getModule(result.getArchiveId(), result.getNodeId()).getPath());
        }
        return Result.getSuccessful(result);
    }

    @GetMapping("/download/{id}")
    @SneakyThrows
    public void download(@PathVariable String id, HttpServletResponse response) {
        List<ArchiveTestcaseExportVO> exports = ArchiveConvert.INSTANCE.convert4(service.getTestcases(id));
        exports.forEach(item -> {
            if (item.getNodeId().length() > 10) {
                item.setPath(service.getModule(id, item.getNodeId()).getPath());
            }
        });

        ExportParams params = new ExportParams();
        params.setSheetName("测试用例");
        params.setType(XSSF);
        try (Workbook workbook = ExcelExportUtil.exportExcel(params, ArchiveTestcaseExportVO.class, exports);
             OutputStream os = response.getOutputStream()) {
            workbook.write(os);
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("归档用例", StandardCharsets.UTF_8));
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        }

    }

    @PostMapping
    public Result<?> add(@RequestHeader("x-project-id") String projectId, @RequestBody ArchiveAddReqVO data) {
        data.setProjectId(projectId);
        service.add(
                ArchiveConvert.INSTANCE.convert(data),
                ArchiveConvert.INSTANCE.convert2(nodeService.getList(projectId)),
                ArchiveConvert.INSTANCE.convert3(testcaseService.getList(projectId))
        );
        return Result.getSuccessful();
    }


    @PutMapping
    public Result<?> update(@RequestBody ArchiveUpdateReqVO data) {
        service.update(ArchiveConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    @DeleteMapping("/{id}")
    public Result<?> remove(@PathVariable String id) {
        service.remove(id);
        return Result.getSuccessful();
    }
}
