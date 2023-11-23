package org.example.service.project.env;

import org.example.controller.project.env.vo.EnvQueryReqVO;
import org.example.dal.dataobject.project.ProjectEnv;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

public interface EnvService {


    PageResult<ProjectEnv> getPage(EnvQueryReqVO req);

    List<ProjectEnv> getList(Long projectId);

    ProjectEnv get(Long projectId, Long id);

    void add(ProjectEnv projectEnv);

    void update(ProjectEnv projectEnv);

    void remove(Long id);
}
