package io.github.xiaomisum.quickclick.job;

import cn.hutool.core.collection.CollectionUtil;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.Bug;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.BugUnclosedRecord;
import io.github.xiaomisum.quickclick.dal.mapper.qualitycenter.BugUncloseRecordMapper;
import io.github.xiaomisum.quickclick.service.qualitycenter.bug.BugService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import xyz.migoo.framework.quartz.core.handler.JobHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import static io.github.xiaomisum.quickclick.enums.BugStatus.Closed;

/**
 * 每周统计一次未关闭的缺陷，记录到未关闭缺陷表，并删除已关闭的缺陷
 */
@Component
public class UnclosedBugDataJobHandler implements JobHandler {

    @Resource
    private BugService bugService;
    @Resource
    private BugUncloseRecordMapper uncloseRecordMapper;

    @Override
    public String execute(String s, Long aLong) throws Exception {
        var currentDate = LocalDate.now();
        var currentDayOfWeek = currentDate.getDayOfWeek();
        int daysToSubtract = currentDayOfWeek.getValue() + 6;
        var lastWeekFirstDay = currentDate.minusDays(daysToSubtract);
        var lastWeekLastDay = lastWeekFirstDay.plusDays(6);
        // 获取 上周之前的 未关闭缺陷记录
        var unclosedList = uncloseRecordMapper.selectList();
        var ids = unclosedList.stream().map(BugUnclosedRecord::getBugId).toList();
        if (CollectionUtil.isNotEmpty(ids)) {
            // 过滤已关闭的缺陷
            var bugs = bugService.get(ids).stream().filter(item -> item.getStatus().equals(Closed)).map(Bug::getId).toList();
            // 从未关闭缺陷表中 删除 已关闭的缺陷记录
            if (CollectionUtil.isNotEmpty(bugs)) {
                uncloseRecordMapper.deleteByBugIds(bugs);
            }
        }
        // 获取 上周一00:00:00 ~ 上周天23:59:59 时间段内未关闭的缺陷记录
        var bugs = bugService.selectUncloseList(lastWeekFirstDay.atStartOfDay(), LocalDateTime.of(lastWeekLastDay, LocalTime.MAX));
        var records = new ArrayList<BugUnclosedRecord>();
        bugs.forEach(item -> records.add(new BugUnclosedRecord()
                .setBugId(item.getId())
                .setProjectId(item.getProjectId())
                .setStatus(item.getStatus())
                .setSupervisor(item.getSupervisor())
                .setFixer(item.getFixer())
                .setCreateDate(item.getClosedTime().toLocalDate())
        ));
        if (CollectionUtil.isNotEmpty(records)) {
            uncloseRecordMapper.insertBatch(records);
        }
        return "success";
    }
}
