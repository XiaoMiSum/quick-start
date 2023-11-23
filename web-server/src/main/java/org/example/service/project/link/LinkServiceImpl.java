package org.example.service.project.link;

import jakarta.annotation.Resource;
import org.example.controller.project.link.vo.LinkQueryReqVO;
import org.example.dal.dataobject.project.ProjectLink;
import org.example.dal.mapper.project.ProjectLinkMapper;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

@Service
public class LinkServiceImpl implements LinkService {

    @Resource
    private ProjectLinkMapper mapper;

    @Override
    public PageResult<ProjectLink> getPage(LinkQueryReqVO req) {
        return mapper.selectPage(req);
    }

    @Override
    public List<ProjectLink> getList(Long projectId) {
        return mapper.selectList(projectId);
    }

    @Override
    public ProjectLink get(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public void add(ProjectLink projectLink) {
        mapper.insert(projectLink);
    }

    @Override
    public void update(ProjectLink projectLink) {
        mapper.updateById(projectLink);
    }

    @Override
    public void remove(Long id) {
        mapper.deleteById(id);
    }
}
