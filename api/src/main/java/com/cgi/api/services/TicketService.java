package com.cgi.api.services;

import com.cgi.api.dto.common.PaginatedResponse;
import com.cgi.api.dto.TicketDto;
import com.cgi.api.dto.search.TicketSearchDto;
import com.cgi.api.entities.Ticket;
import com.cgi.api.entities.User;
import com.cgi.api.mappers.TicketMapper;
import com.cgi.api.repostirories.TicketRepository;
import com.cgi.api.services.common.GenericService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class TicketService extends GenericService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    @Transactional
    public TicketDto saveTicket(TicketDto ticketDto) {
        Ticket ticket = ticketMapper.toEntity(ticketDto);
        ticketRepository.save(ticket);
        return ticketMapper.toDto(ticket);
    }

    @Transactional
    public TicketDto saveTicket(Ticket ticket) {
        ticketRepository.save(ticket);
        return ticketMapper.toDto(ticket);
    }

    public PaginatedResponse<TicketDto> getAllUserTickets(TicketSearchDto ticketSearch) {
        User user = getCurrentUserAsEntity();
        ticketSearch.setUserId(user.getId());

        Page<Ticket> ticketsPage = ticketRepository.findAll(ticketSearch.getSpecification(), ticketSearch.getPageable());
        List<TicketDto> ticketDtos = ticketMapper.toDtos(ticketsPage.getContent());

        return PaginatedResponse.<TicketDto>builder()
                .page(ticketsPage.getNumber())
                .totalPages(ticketsPage.getTotalPages())
                .size(ticketDtos.size())
                .sortingFields(ticketSearch.getSortingFields())
                .sortDirection(ticketSearch.getSortDirection().toString())
                .data(ticketDtos)
                .build();
    }
}
