package io.github.xiaomisum.quickclick.convert.qualitycenter;

import io.github.xiaomisum.quickclick.controller.quality.bug.vo.*;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.Bug;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.BugComment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

@Mapper
public interface BugConvert {

    BugConvert INSTANCE = Mappers.getMapper(BugConvert.class);

    PageResult<BugPageRespVO> convert(PageResult<Bug> beans);

    BugRespVO convert(Bug bug);

    Bug convert(BugAddReqVO data);

    Bug convert(BugUpdateReqVO data);

    Bug convert(BugFixReqVO data);

    Bug convert(BugConfirmReqVO data);

    Bug convert(BugRejectedReqVO data);

    BugComment convert(BugCommentAddReqVO data);

    List<BugCommentRespVO> convert(List<BugComment> comment);
}
