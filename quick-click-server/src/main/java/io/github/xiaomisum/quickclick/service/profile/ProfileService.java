package io.github.xiaomisum.quickclick.service.profile;

import io.github.xiaomisum.quickclick.dal.dataobject.profile.Profile;

public interface ProfileService {

    Profile get(Long userId);

    void save(Profile profileVO);
}
