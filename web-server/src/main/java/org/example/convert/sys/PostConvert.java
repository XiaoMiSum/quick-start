package org.example.convert.sys;

import org.example.controller.sys.post.vo.PostAddReqVO;
import org.example.controller.sys.post.vo.PostRespVO;
import org.example.controller.sys.post.vo.PostSimpleRespVO;
import org.example.controller.sys.post.vo.PostUpdateReqVO;
import org.example.dal.dataobject.sys.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

@Mapper
public interface PostConvert {

    PostConvert INSTANCE = Mappers.getMapper(PostConvert.class);

    PageResult<PostRespVO> convert(PageResult<Post> page);

    Post convert(PostAddReqVO req);

    Post convert(PostUpdateReqVO req);

    List<PostSimpleRespVO> convert(List<Post> list);

    PostRespVO convert(Post post);
}
