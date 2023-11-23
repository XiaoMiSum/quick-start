package org.example.dal.mapper.project;

import org.apache.ibatis.annotations.Mapper;
import org.example.controller.project.tag.vo.TagQueryReqVO;
import org.example.dal.dataobject.project.Tag;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.LambdaQueryWrapperX;

import java.util.List;

@Mapper
public interface TagMapper extends BaseMapperX<Tag> {

    default PageResult<Tag> selectPage(TagQueryReqVO req) {
        return selectPage(req, new LambdaQueryWrapperX<Tag>()
                .eq(Tag::getProjectId, req.getProjectId())
                .likeIfPresent(Tag::getName, req.getName())
                .eqIfPresent(Tag::getStatus, req.getStatus())
                .orderByDesc(Tag::getId)
        );
    }

    default List<Tag> selectList(Long projectId) {
        return selectList(new LambdaQueryWrapperX<Tag>()
                .eq(Tag::getProjectId, projectId)
                .orderByDesc(Tag::getId)
        );
    }

    default List<Tag> selectList(Long projectId, List<String> tags) {
        return selectList(new LambdaQueryWrapperX<Tag>()
                .eq(Tag::getProjectId, projectId)
                .in(Tag::getName, tags)
        );
    }

}
