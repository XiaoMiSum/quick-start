package io.github.xiaomisum.quickclick.dal.mapper.qualitycenter;

import io.github.xiaomisum.quickclick.dal.dataobject.quality.BugUnclosedRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.LambdaQueryWrapperX;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface BugUncloseRecordMapper extends BaseMapperX<BugUnclosedRecord> {

    default List<BugUnclosedRecord> selectList(LocalDateTime startDate, LocalDateTime endDate) {
        return selectList(new LambdaQueryWrapperX<BugUnclosedRecord>()
                .ge(BugUnclosedRecord::getCreateDate, startDate)
                .le(BugUnclosedRecord::getCreateDate, endDate));
    }

    @Delete("""
            <script>
            delete from qc_quality_bug_unclosed_record where bug_id in
            <foreach collection="bugs" item="id" index="index" open="(" close=")" separator=", ">
              #{id}
            </foreach>
            </script>
            """)
    void deleteByBugIds(@Param("bugs") List<String> bugs);

}
