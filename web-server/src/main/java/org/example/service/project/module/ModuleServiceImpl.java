package org.example.service.project.module;

import jakarta.annotation.Resource;
import org.example.dal.dataobject.project.ProjectModule;
import org.example.dal.mapper.project.ProjectModuleMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Resource
    private ProjectModuleMapper mapper;

    @Override
    public ProjectModule get(Long projectId, Long id) {
        return mapper.select(projectId, id);
    }

    @Override
    public List<ProjectModule> getList(Long projectId) {
        return mapper.selectList(projectId);
    }

    @Override
    public void add(ProjectModule module) {
        module.setPath("/" + module.getName());
        if (module.getParentId() > 0) {
            module.setPath(mapper.selectById(module.getParentId()).getPath() + module.getPath());
        }
        mapper.insert(module);
    }

    @Override
    public void update(ProjectModule module) {
        module.setPath("/" + module.getName());
        if (module.getParentId() > 0) {
            module.setPath(mapper.selectById(module.getParentId()).getPath() + module.getPath());
        }
        mapper.updateById(module);
    }

    @Override
    public void remove(Long id) {
        mapper.deleteById(id);
    }
}
