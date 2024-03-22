package com.cgi.api.mappers;

import com.cgi.api.dto.common.UserDto;
import com.cgi.api.entities.User;
import com.cgi.api.mappers.common.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends EntityMapper<User, UserDto> {
}
