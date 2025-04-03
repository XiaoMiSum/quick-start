package io.github.xiaomisum.quickclick.convert.profile;

import io.github.xiaomisum.quickclick.controller.self.vo.ProfileVO;
import io.github.xiaomisum.quickclick.dal.dataobject.profile.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface ProfileConvert {

    ProfileConvert INSTANCE = Mappers.getMapper(ProfileConvert.class);


    Profile convert(ProfileVO bean);

    ProfileVO convert(Profile bean);


}
