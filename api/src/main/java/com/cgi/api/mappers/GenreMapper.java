package com.cgi.api.mappers;

import com.cgi.api.dto.GenreDto;
import com.cgi.api.entities.Genre;
import com.cgi.api.mappers.common.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenreMapper extends EntityMapper<Genre, GenreDto> {
}
