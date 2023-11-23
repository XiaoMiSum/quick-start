package org.example.service.project.tag;

import cn.hutool.core.collection.CollectionUtil;
import jakarta.annotation.Resource;
import org.example.controller.project.tag.vo.TagQueryReqVO;
import org.example.dal.dataobject.project.Tag;
import org.example.dal.mapper.project.TagMapper;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagMapper mapper;

    @Override
    public PageResult<Tag> getPage(TagQueryReqVO req) {
        return mapper.selectPage(req);
    }

    @Override
    public List<Tag> getList(Long projectId) {
        return mapper.selectList(projectId);
    }

    @Override
    public void save(Long projectId, List<String> tags) {
        if (CollectionUtil.isNotEmpty(tags)) {
            List<String> exists = mapper.selectList(projectId, tags).stream().map(Tag::getName).toList();
            List<String> batch = CollectionUtil.subtractToList(tags, exists);
            if (!batch.isEmpty()) {
                batch.forEach(item -> add(new Tag().setName(item).setProjectId(projectId)));
            }
        }
    }

    @Override
    public void add(Tag tag) {
        mapper.insert(tag);
    }

    @Override
    public void update(Tag tag) {
        mapper.updateById(tag);
    }

    @Override
    public void remove(Long id) {
        mapper.deleteById(id);
    }
}
