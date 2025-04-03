package io.github.xiaomisum.quickclick.service.profile;

import io.github.xiaomisum.quickclick.dal.dataobject.profile.Profile;
import io.github.xiaomisum.quickclick.dal.mapper.profile.ProfileMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Resource
    private ProfileMapper mapper;

    @Override
    public Profile get(Long userId) {
        return mapper.selectByUserId(userId);
    }

    @Override
    public void save(Profile profile) {
        Profile p = mapper.selectByUserId(profile.getUserId());
        if (Objects.isNull(p)) {
            mapper.insert(profile);
        } else {
            profile.setId(p.getId());
            mapper.updateById(profile);
        }
    }
}
