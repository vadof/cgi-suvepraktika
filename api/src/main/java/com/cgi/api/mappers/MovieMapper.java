package com.cgi.api.mappers;

import com.cgi.api.dto.MovieDto;
import com.cgi.api.entities.Movie;
import com.cgi.api.mappers.common.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {GenreMapper.class})
public interface MovieMapper extends EntityMapper<Movie, MovieDto> {
}
