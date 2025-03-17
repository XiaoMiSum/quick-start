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

package io.github.xiaomisum.quickclick.controller.project.iteration;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.github.xiaomisum.quickclick.controller.project.iteration.vo.*;
import io.github.xiaomisum.quickclick.convert.project.IterationConvert;
import io.github.xiaomisum.quickclick.dal.dataobject.project.Iteration;
import io.github.xiaomisum.quickclick.service.project.iteration.IterationService;
import io.github.xiaomisum.quickclick.service.sys.user.UserService;
import io.github.xiaomisum.quickclick.service.track.testcase.NodeService;
import io.github.xiaomisum.quickclick.service.track.testcase.TestcaseService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.Result;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static cn.afterturn.easypoi.excel.entity.enmus.ExcelType.XSSF;

@RestController
@RequestMapping("/project/iteration")
public class IterationController {

    @Resource
    private IterationService service;
    @Resource
    private NodeService nodeService;
    @Resource
    private TestcaseService testcaseService;
    @Resource
    private UserService userService;

    @GetMapping
    public Result<?> getPage(@RequestHeader("x-project-id") String projectId, IterationQueryReqVO req) {
        req.setProjectId(projectId);
        return Result.getSuccessful(IterationConvert.INSTANCE.convert(service.getPage(req)));
    }

    @GetMapping("/{iterationId}")
    public Result<?> get(@PathVariable String iterationId) {
        return Result.getSuccessful(IterationConvert.INSTANCE.convert(service.get(iterationId)));
    }

    @GetMapping("/node")
    public Result<?> getNodes(String iterationId) {
        return Result.getSuccessful(IterationConvert.INSTANCE.convert(service.getNodes(iterationId)));
    }

    @GetMapping("/testcase")
    public Result<?> getTestcases(ArchiveTestcaseQueryReqVO req) {
        PageResult<ArchiveTestcasePageRespVO> result = IterationConvert.INSTANCE.convert1(service.getTestcasePage(req));
        result.getList().forEach(item -> {
            if (item.getNodeId().length() > 10) {
                item.setPath(service.getNode(req.getNodeId(), item.getNodeId()).getPath());
            }
        });
        return Result.getSuccessful(result);
    }

    @GetMapping("/testcase/{id}")
    public Result<?> getTestcase(@PathVariable String id) {
        ArchiveTestcaseRespVO result = IterationConvert.INSTANCE.convert(service.getTestcase(id));
        if (result.getNodeId().length() > 10) {
            result.setPath(service.getNode(result.getIterationId(), result.getNodeId()).getPath());
        }
        return Result.getSuccessful(result);
    }

    @GetMapping("/download/{iterationId}")
    @SneakyThrows
    public void download(@PathVariable String iterationId, HttpServletResponse response) {
        List<Map<String, Object>> sheetsList = Lists.newArrayList();
        service.getNodes(iterationId).stream().filter(item -> item.getParentId().equals("0"))
                .forEach(node -> {
                    List<ArchiveTestcaseExportVO> exports = IterationConvert.INSTANCE.convert4(
                            service.getTestcases(iterationId, node.getOriginalId()));
                    exports.forEach(item -> item.setPath(node.getPath()));
                    Map<String, Object> export = Maps.newHashMap();
                    ExportParams params = new ExportParams();
                    params.setSheetName(node.getOriginalId() + "-" + node.getPath());
                    export.put("title", params);
                    export.put("entity", ArchiveTestcaseExportVO.class);
                    export.put("data", exports);
                    sheetsList.add(export);
                });
        try (Workbook workbook = ExcelExportUtil.exportExcel(sheetsList, XSSF);
             OutputStream os = response.getOutputStream()) {
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("归档用例", StandardCharsets.UTF_8));
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            workbook.write(os);
        }
    }

    @PostMapping
    public Result<?> add(@RequestHeader("x-project-id") String projectId, @RequestBody @Valid IterationAddReqVO data) {
        data.setProjectId(projectId);
        service.add(IterationConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    @PostMapping("/{iterationId}")
    public Result<?> archive(@PathVariable String iterationId) {
        Iteration iteration = service.get(iterationId);
        service.archive(iterationId,
                IterationConvert.INSTANCE.convert2(nodeService.getList(iteration.getProjectId())),
                IterationConvert.INSTANCE.convert3(testcaseService.getList(iteration.getProjectId()))
        );
        return Result.getSuccessful();
    }

    @PutMapping
    public Result<?> update(@RequestBody @Valid IterationUpdateReqVO data) {
        service.update(IterationConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    @DeleteMapping("/{id}")
    public Result<?> remove(@PathVariable String id) {
        service.remove(id);
        return Result.getSuccessful();
    }
}
