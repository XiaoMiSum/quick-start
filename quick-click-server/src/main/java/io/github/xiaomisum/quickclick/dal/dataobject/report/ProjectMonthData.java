package io.github.xiaomisum.quickclick.dal.dataobject.report;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "qc_report_quality_project_month", autoResultMap = true)
@Data
public class ProjectMonthData extends ProjectRangeDataBaseDO {


}
