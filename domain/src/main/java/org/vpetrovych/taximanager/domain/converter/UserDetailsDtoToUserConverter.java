package org.vpetrovych.taximanager.domain.converter;

import org.vpetrovych.taximanager.domain.dto.UserDetailsDto;
import org.vpetrovych.taximanager.domain.entities.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsDtoToUserConverter implements Converter<UserDetailsDto, User> {

    @Override
    public User convert(UserDetailsDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setPassword(dto.getPassword());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setFatherName(dto.getFatherName());
        user.setAge(dto.getAge());
        return user;
    }
}
