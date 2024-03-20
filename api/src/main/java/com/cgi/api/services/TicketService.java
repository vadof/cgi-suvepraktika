package com.cgi.api.services;

import com.cgi.api.dto.TicketDto;
import com.cgi.api.entities.Ticket;
import com.cgi.api.mappers.TicketMapper;
import com.cgi.api.repostirories.TicketRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class TicketService {

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

}
