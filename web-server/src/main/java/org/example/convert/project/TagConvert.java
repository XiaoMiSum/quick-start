package org.example.convert.project;

import com.google.common.collect.Lists;
import org.example.controller.project.tag.vo.TagAddReqVO;
import org.example.controller.project.tag.vo.TagRespVO;
import org.example.controller.project.tag.vo.TagUpdateReqVo;
import org.example.controller.project.tag.vo.TagUpdateStatusReqVo;
import org.example.dal.dataobject.project.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.SimpleData;

import java.util.List;
import java.util.Objects;

import static xyz.migoo.framework.common.enums.CommonStatusEnum.ENABLE;

@Mapper
public interface TagConvert {

    TagConvert INSTANCE = Mappers.getMapper(TagConvert.class);

    Tag convert(TagAddReqVO bean);

    Tag convert(TagUpdateReqVo bean);

    Tag convert(TagUpdateStatusReqVo bean);

    TagRespVO convert(Tag bean);

    PageResult<TagRespVO> convert(PageResult<Tag> beans);

    default SimpleData<Long> convert1(Tag bean) {
        return new SimpleData<>(bean.getId(), bean.getName(), !Objects.equals(ENABLE.getStatus(), bean.getStatus()));
    }

    List<TagRespVO> convert(List<Tag> beans);

    default List<SimpleData<Long>> convert1(List<Tag> beans) {
        List<SimpleData<Long>> result = Lists.newArrayList();
        beans.forEach(item -> result.add(convert1(item)));
        return result;
    }
}
