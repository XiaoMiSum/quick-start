package org.example.service.project.module;

import org.example.dal.dataobject.project.ProjectModule;

import java.util.List;

public interface ModuleService {

    ProjectModule get(Long projectId, Long id);

    List<ProjectModule> getList(Long projectId);

    void add(ProjectModule module);

    void update(ProjectModule module);

    void remove(Long id);
}
