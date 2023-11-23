package org.example.service.project.tag;

import org.example.controller.project.tag.vo.TagQueryReqVO;
import org.example.dal.dataobject.project.Tag;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

public interface TagService {

    PageResult<Tag> getPage(TagQueryReqVO req);

    List<Tag> getList(Long projectId);

    void save(Long projectId, List<String> tags);

    void add(Tag tag);

    void update(Tag tag);

    void remove(Long id);
}
