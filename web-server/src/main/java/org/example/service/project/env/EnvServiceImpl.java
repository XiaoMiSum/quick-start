package org.example.service.project.env;

import jakarta.annotation.Resource;
import org.example.controller.project.env.vo.EnvQueryReqVO;
import org.example.dal.dataobject.project.ProjectEnv;
import org.example.dal.mapper.project.ProjectEnvMapper;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

@Service
public class EnvServiceImpl implements EnvService {

    @Resource
    private ProjectEnvMapper mapper;

    @Override
    public PageResult<ProjectEnv> getPage(EnvQueryReqVO req) {
        return mapper.selectPage(req);
    }

    @Override
    public List<ProjectEnv> getList(Long projectId) {
        return mapper.selectList(projectId);
    }

    @Override
    public ProjectEnv get(Long projectId, Long id) {
        return mapper.selectOne(projectId, id);
    }

    @Override
    public void add(ProjectEnv projectEnv) {
        mapper.insert(projectEnv);
    }

    @Override
    public void update(ProjectEnv projectEnv) {
        mapper.updateById(projectEnv);
    }

    @Override
    public void remove(Long id) {
        mapper.deleteById(id);
    }
}
