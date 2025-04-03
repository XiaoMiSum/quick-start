package io.github.xiaomisum.quickclick.dal.mapper.qualitycenter;

import io.github.xiaomisum.quickclick.dal.dataobject.quality.BugComment;
import org.apache.ibatis.annotations.Mapper;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.LambdaQueryWrapperX;

import java.util.List;

@Mapper
public interface BugCommentMapper extends BaseMapperX<BugComment> {

    default List<BugComment> selectList(String bugId) {
        return selectList(new LambdaQueryWrapperX<BugComment>()
                .eq(BugComment::getBugId, bugId)
                .orderByAsc(BugComment::getId));
    }

}
