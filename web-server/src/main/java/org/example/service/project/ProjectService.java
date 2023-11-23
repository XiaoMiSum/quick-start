package org.example.service.project;

import org.example.controller.project.vo.ProjectQueryReqVO;
import org.example.dal.dataobject.project.Project;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

public interface ProjectService {

    PageResult<Project> getPage(ProjectQueryReqVO req);

    List<Project> getList();

    Project get(Long id);

    void add(Project project);

    void update(Project project);

    void remove(Long id);
}
