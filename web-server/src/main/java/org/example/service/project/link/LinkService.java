package org.example.service.project.link;

import org.example.controller.project.link.vo.LinkQueryReqVO;
import org.example.dal.dataobject.project.ProjectLink;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

public interface LinkService {

    PageResult<ProjectLink> getPage(LinkQueryReqVO req);

    List<ProjectLink> getList(Long projectId);

    ProjectLink get(Long id);

    void add(ProjectLink projectLink);

    void update(ProjectLink projectLink);

    void remove(Long id);
}
