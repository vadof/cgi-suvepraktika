package com.cgi.api.mappers;

import com.cgi.api.dto.TicketDto;
import com.cgi.api.entities.Ticket;
import com.cgi.api.mappers.common.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {MovieSessionMapper.class, UserMapper.class})
public interface TicketMapper extends EntityMapper<Ticket, TicketDto> {
}
