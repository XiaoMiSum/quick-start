package io.github.xiaomisum.quickclick.service.qualitycenter.bug;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import io.github.xiaomisum.quickclick.controller.quality.bug.vo.BugQueryReqVO;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.Bug;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.BugExecRecord;
import io.github.xiaomisum.quickclick.dal.mapper.qualitycenter.BugMapper;
import io.github.xiaomisum.quickclick.dal.mapper.qualitycenter.BugRecordMapper;
import io.github.xiaomisum.quickclick.enums.BugStatus;
import io.github.xiaomisum.quickclick.model.dto.BugStatisticsDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.security.core.util.SecurityFrameworkUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.github.xiaomisum.quickclick.enums.BugStatus.*;

@Service
public class BugServiceImpl implements BugService {

    @Resource
    private BugMapper mapper;
    @Resource
    private BugRecordMapper commentMapper;

    @Override
    public PageResult<Bug> getPage(BugQueryReqVO req) {
        return mapper.selectPage(req);
    }

    @Override
    public Bug get(String id) {
        return mapper.selectById(id);
    }

    @Override
    public List<Bug> get(List<String> ids) {
        return mapper.selectListByIds(ids);
    }

    @Override
    public String add(Bug data) {
        data.setId(IdUtil.getSnowflakeNextIdStr());
        mapper.insert(data);
        addRecord(new BugExecRecord().setBugId(data.getId())
                .setUserId(SecurityFrameworkUtils.getLoginUserId())
                .setOperation(New)
                .setContent(""));
        return data.getId();
    }

    @Override
    public void update(Bug data) {
        mapper.updateById(data);
    }

    @Override
    public void assign(List<String> ids, Long handler) {
        List<Bug> bugs = Lists.newArrayList();
        ids.forEach(id -> bugs.add((Bug) new Bug().setAssignedTime(LocalDateTime.now()).setHandler(handler).setId(id)));
        mapper.updateBatch(bugs);
    }

    @Override
    public void confirm(List<String> ids) {
        List<Bug> bugs = Lists.newArrayList();
        List<BugExecRecord> comments = Lists.newArrayList();
        ids.forEach(id -> {
            bugs.add((Bug) new Bug().setConfirmedTime(LocalDateTime.now()).setStatus(Opened).setId(id));
            comments.add(new BugExecRecord().setBugId(id).setUserId(SecurityFrameworkUtils.getLoginUserId())
                    .setOperation(Opened).setContent(""));
        });
        mapper.updateBatch(bugs);
        commentMapper.insertBatch(comments);
    }

    @Override
    public void confirm(Bug data, String comment) {
        data.setStatus(Opened);
        data.setConfirmedTime(LocalDateTime.now());
        mapper.updateById(data);
        addRecord(new BugExecRecord().setBugId(data.getId())
                .setUserId(SecurityFrameworkUtils.getLoginUserId())
                .setOperation(Opened)
                .setContent(StrUtil.isBlank(comment) ? "" : comment));

    }

    @Override
    public void reject(Bug data) {
        data.setStatus(Rejected);
        data.setAssignedTime(LocalDateTime.now());
        data.setRejectedTime(LocalDateTime.now());
        mapper.updateById(data);
        addRecord(new BugExecRecord().setBugId(data.getId())
                .setUserId(SecurityFrameworkUtils.getLoginUserId())
                .setOperation(Rejected)
                .setContent(""));
    }

    @Override
    public void fix(Bug data) {
        data.setStatus(Fixed);
        data.setFixedTime(LocalDateTime.now());
        data.setFixer(SecurityFrameworkUtils.getLoginUserId());
        data.setAssignedTime(LocalDateTime.now());
        mapper.updateById(data);
        addRecord(new BugExecRecord().setBugId(data.getId())
                .setUserId(SecurityFrameworkUtils.getLoginUserId())
                .setOperation(Fixed)
                // 修复bug 需记录耗时
                .setDuration(data.getFixDuration())
                .setContent(""));
    }

    @Override
    public void reopen(String id, Long handler, String comment, Integer duration) {
        mapper.reopenById(id, Reopened, handler);
        addRecord(new BugExecRecord().setBugId(id)
                .setUserId(SecurityFrameworkUtils.getLoginUserId())
                .setOperation(Reopened)
                // todo 激活Bug 需要消耗测试人员工时，记录测试工时
                .setDuration(duration)
                .setContent(StrUtil.isBlank(comment) ? "" : comment));
    }

    @Override
    public void close(String id, String comment, Integer duration) {
        mapper.closeById(id, Closed, SecurityFrameworkUtils.getLoginUserId());
        addRecord(new BugExecRecord().setBugId(id)
                .setUserId(SecurityFrameworkUtils.getLoginUserId())
                .setOperation(Closed)
                // todo 关闭Bug 需要消耗测试人员工时，记录测试工时
                .setDuration(duration)
                .setContent(StrUtil.isBlank(comment) ? "" : comment));

    }

    @Override
    public void remove(String id) {
        mapper.deleteById(id);
    }

    @Override
    public List<BugExecRecord> getRecords(String bugId) {
        return commentMapper.selectList(bugId);
    }

    @Override
    public void addRecord(BugExecRecord data) {
        commentMapper.insert(data);
    }

    @Override
    public Long count(Long userId, BugStatus... status) {
        return mapper.selectCount(userId, status);
    }

    @Override
    public List<Bug> loadProjectBugByCreator(String projectId, Collection<Long> creator, LocalDateTime startTime,
            LocalDateTime endTime) {
        return mapper.selectListByCreator(projectId, creator, startTime, endTime);
    }

    @Override
    public List<Bug> loadProjectBugBySupervisor(String projectId, Collection<Long> supervisor,
            LocalDateTime startTime, LocalDateTime endTime) {
        return mapper.selectListBySupervisor(projectId, supervisor, startTime, endTime);
    }

    @Override
    public List<BugExecRecord> loadProjectClosedRecords(String projectId, Collection<Long> closer,
            LocalDateTime startTime, LocalDateTime endTime) {
        return commentMapper.selectClosedBug(projectId, closer, startTime, endTime);
    }

    @Override
    public List<BugExecRecord> loadProjectFixedRecords(String projectId, Collection<Long> fixer,
            LocalDateTime startTime, LocalDateTime endTime) {
        return commentMapper.selectRecordsByOperation(projectId, fixer, Fixed, startTime, endTime);
    }

    @Override
    public List<Bug> loadProjectReopenBug(String projectId, LocalDateTime startTime, LocalDateTime endTime) {
        return commentMapper.selectBugByReopen(projectId, startTime, endTime);
    }

    @Override
    public List<Bug> loadProjectCloseBug(String projectId, LocalDateTime startTime, LocalDateTime endTime) {
        return mapper.selectListByClose(projectId, startTime, endTime);
    }

    @Override
    public List<Bug> selectUncloseList(LocalDateTime startTime, LocalDateTime endTime) {
        return mapper.selectUncloseList(startTime, endTime);
    }

    @Override
    public List<BugExecRecord> loadProjectReopenRecords(String projectId, List<Long> testers, LocalDateTime startTime,
            LocalDateTime endTime) {
        return commentMapper.selectReopenRecords(projectId, testers, startTime, endTime);
    }

    @Override
    public List<Bug> getByTestcaseId(String testcaseId) {
        return mapper.selectList(new LambdaQueryWrapperX<Bug>()
                .eq(Bug::getTestcaseId, testcaseId));
    }

    @Override
    public BugStatisticsDTO getStatistics(String projectId, LocalDateTime startTime, LocalDateTime endTime) {
        BugStatisticsDTO statistics = new BugStatisticsDTO();

        // 获取项目所有缺陷
        List<Bug> bugs = mapper.selectList(new LambdaQueryWrapperX<Bug>()
                .eq(Bug::getProjectId, projectId)
                .ge(Bug::getCreateTime, startTime)
                .le(Bug::getCreateTime, endTime));

        // 按状态统计
        Map<String, Integer> statusStatistics = bugs.stream()
                .collect(Collectors.groupingBy(
                        bug -> bug.getStatus().name(),
                        Collectors.collectingAndThen(Collectors.toList(), List::size)));
        statistics.setStatusStatistics(statusStatistics);

        // 按严重等级统计
        Map<String, Integer> severityStatistics = bugs.stream()
                .collect(Collectors.groupingBy(
                        Bug::getSeverity,
                        Collectors.collectingAndThen(Collectors.toList(), List::size)));
        statistics.setSeverityStatistics(severityStatistics);

        // 按优先级统计
        Map<String, Integer> priorityStatistics = bugs.stream()
                .collect(Collectors.groupingBy(
                        Bug::getPriority,
                        Collectors.collectingAndThen(Collectors.toList(), List::size)));
        statistics.setPriorityStatistics(priorityStatistics);

        // 按模块统计
        Map<String, Integer> moduleStatistics = bugs.stream()
                .collect(Collectors.groupingBy(
                        Bug::getNodeId,
                        Collectors.collectingAndThen(Collectors.toList(), List::size)));
        statistics.setModuleStatistics(moduleStatistics);

        // 按责任人统计
        Map<Long, Integer> supervisorStatistics = bugs.stream()
                .collect(Collectors.groupingBy(
                        Bug::getSupervisor,
                        Collectors.collectingAndThen(Collectors.toList(), List::size)));
        statistics.setSupervisorStatistics(supervisorStatistics);

        // 按处理人统计
        Map<Long, Integer> handlerStatistics = bugs.stream()
                .collect(Collectors.groupingBy(
                        Bug::getHandler,
                        Collectors.collectingAndThen(Collectors.toList(), List::size)));
        statistics.setHandlerStatistics(handlerStatistics);

        // 按创建人统计
        Map<Long, Integer> creatorStatistics = bugs.stream()
                .collect(Collectors.groupingBy(
                        Bug::getCreatorId,
                        Collectors.collectingAndThen(Collectors.toList(), List::size)));
        statistics.setCreatorStatistics(creatorStatistics);

        // 按日期统计（近30天）
        List<BugStatisticsDTO.DailyBugStatistics> dailyStatistics = getDailyStatistics(projectId, startTime, endTime);
        statistics.setDailyStatistics(dailyStatistics);

        // 修复率统计
        long totalBugs = bugs.size();
        long fixedBugs = bugs.stream().filter(bug -> bug.getStatus() == Fixed).count();
        statistics.setFixRate(totalBugs > 0 ? BigDecimal.valueOf((double) fixedBugs / totalBugs * 100)
                .setScale(2, RoundingMode.HALF_UP).doubleValue() : 0.0);

        // 关闭率统计
        long closedBugs = bugs.stream().filter(bug -> bug.getStatus() == Closed).count();
        statistics.setCloseRate(totalBugs > 0 ? BigDecimal.valueOf((double) closedBugs / totalBugs * 100)
                .setScale(2, RoundingMode.HALF_UP).doubleValue() : 0.0);

        // 平均修复时长（小时）
        List<Bug> fixedBugsList = bugs.stream()
                .filter(bug -> bug.getFixedTime() != null && bug.getAssignedTime() != null)
                .toList();
        if (!fixedBugsList.isEmpty()) {
            long totalDuration = fixedBugsList.stream()
                    .mapToLong(bug -> Duration.between(bug.getAssignedTime(), bug.getFixedTime()).toHours())
                    .sum();
            statistics.setAvgFixDuration(BigDecimal.valueOf((double) totalDuration / fixedBugsList.size())
                    .setScale(2, RoundingMode.HALF_UP).doubleValue());
        } else {
            statistics.setAvgFixDuration(0.0);
        }

        // 激活次数统计
        Map<String, Integer> reopenStatistics = bugs.stream()
                .collect(Collectors.groupingBy(
                        Bug::getId,
                        Collectors.collectingAndThen(Collectors.toList(),
                                list -> list.stream()
                                        .mapToInt(Bug::getReopenedTimes)
                                        .sum())));
        statistics.setReopenStatistics(reopenStatistics);

        return statistics;
    }

    /**
     * 获取每日缺陷统计数据
     *
     * @param projectId 项目ID
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 每日缺陷统计数据
     */
    private List<BugStatisticsDTO.DailyBugStatistics> getDailyStatistics(String projectId, LocalDateTime startTime,
            LocalDateTime endTime) {
        List<BugStatisticsDTO.DailyBugStatistics> dailyStats = new ArrayList<>();

        // 获取指定时间范围内的所有缺陷
        List<Bug> bugs = mapper.selectList(new LambdaQueryWrapperX<Bug>()
                .eq(Bug::getProjectId, projectId)
                .ge(Bug::getCreateTime, startTime)
                .le(Bug::getCreateTime, endTime));

        // 按日期分组
        Map<LocalDate, List<Bug>> bugsByDate = bugs.stream()
                .collect(Collectors.groupingBy(bug -> bug.getCreateTime().toLocalDate()));

        // 获取修复记录
        List<BugExecRecord> fixedRecords = commentMapper.selectRecordsByOperation(
                projectId, null, Fixed, startTime, endTime);

        // 按日期分组修复记录
        Map<LocalDate, List<BugExecRecord>> fixedByDate = fixedRecords.stream()
                .collect(Collectors.groupingBy(record -> record.getCreateTime().toLocalDate()));

        // 获取关闭记录
        List<BugExecRecord> closedRecords = commentMapper.selectClosedBug(
                projectId, null, startTime, endTime);

        // 按日期分组关闭记录
        Map<LocalDate, List<BugExecRecord>> closedByDate = closedRecords.stream()
                .collect(Collectors.groupingBy(record -> record.getCreateTime().toLocalDate()));

        // 生成每日统计数据
        LocalDate currentDate = startTime.toLocalDate();
        while (!currentDate.isAfter(endTime.toLocalDate())) {
            BugStatisticsDTO.DailyBugStatistics dailyStat = new BugStatisticsDTO.DailyBugStatistics();
            dailyStat.setDate(currentDate.toString());

            // 当天创建的缺陷数
            List<Bug> createdBugs = bugsByDate.getOrDefault(currentDate, new ArrayList<>());
            dailyStat.setCreatedCount(createdBugs.size());

            // 当天修复的缺陷数
            List<BugExecRecord> fixedOnDate = fixedByDate.getOrDefault(currentDate, new ArrayList<>());
            dailyStat.setFixedCount(fixedOnDate.size());

            // 当天关闭的缺陷数
            List<BugExecRecord> closedOnDate = closedByDate.getOrDefault(currentDate, new ArrayList<>());
            dailyStat.setClosedCount(closedOnDate.size());

            dailyStats.add(dailyStat);
            currentDate = currentDate.plusDays(1);
        }

        return dailyStats;
    }
}
