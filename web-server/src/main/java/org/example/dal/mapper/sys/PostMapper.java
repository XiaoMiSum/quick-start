package org.example.dal.mapper.sys;

import org.apache.ibatis.annotations.Mapper;
import org.example.controller.sys.post.vo.PostQueryReqVO;
import org.example.dal.dataobject.sys.Post;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.LambdaQueryWrapperX;

import java.util.Collection;
import java.util.List;

@Mapper
public interface PostMapper extends BaseMapperX<Post> {

    default List<Post> selectList(Collection<Long> ids, Collection<Integer> statuses) {
        return selectList(new LambdaQueryWrapperX<Post>()
                .inIfPresent(Post::getId, ids)
                .inIfPresent(Post::getStatus, statuses));
    }

    default PageResult<Post> selectPage(PostQueryReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<Post>()
                .likeIfPresent(Post::getCode, reqVO.getCode())
                .likeIfPresent(Post::getName, reqVO.getName())
                .eqIfPresent(Post::getStatus, reqVO.getStatus()));
    }

    default Post selectByName(String name) {
        return selectOne(new LambdaQueryWrapperX<Post>().eq(Post::getName, name));
    }

    default Post selectByCode(String code) {
        return selectOne(new LambdaQueryWrapperX<Post>().eq(Post::getCode, code));
    }

}
