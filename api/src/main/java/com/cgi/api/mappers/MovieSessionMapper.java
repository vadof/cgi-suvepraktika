package com.cgi.api.mappers;

import com.cgi.api.dto.MovieSessionDto;
import com.cgi.api.entities.MovieSession;
import com.cgi.api.mappers.common.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {MovieMapper.class})
public interface MovieSessionMapper extends EntityMapper<MovieSession, MovieSessionDto> {
}
