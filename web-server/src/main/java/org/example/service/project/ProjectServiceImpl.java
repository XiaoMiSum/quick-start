package org.example.service.project;

import jakarta.annotation.Resource;
import org.example.controller.project.vo.ProjectQueryReqVO;
import org.example.dal.dataobject.project.Project;
import org.example.dal.mapper.project.ProjectMapper;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Resource
    private ProjectMapper mapper;

    @Override
    public PageResult<Project> getPage(ProjectQueryReqVO req) {
        return mapper.selectPage(req);
    }

    @Override
    public List<Project> getList() {
        return mapper.selectList();
    }

    @Override
    public Project get(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public void add(Project project) {
        mapper.insert(project);
    }

    @Override
    public void update(Project project) {
        mapper.updateById(project);
    }

    @Override
    public void remove(Long id) {
        mapper.deleteById(id);
    }
}
