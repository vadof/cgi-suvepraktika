package com.cgi.api.controllers;

import com.cgi.api.dto.PaginatedResponse;
import com.cgi.api.dto.TicketDto;
import com.cgi.api.dto.search.TicketSearchDto;
import com.cgi.api.services.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Ticket", description = "API operations with Tickets")
@RestController
@RequestMapping("/api/v1/tickets")
@AllArgsConstructor
@Slf4j
public class TicketController {

    private final TicketService ticketService;


    @Operation(summary = "Get list of User's Tickets as a Paginated Response")
    @ApiResponse(responseCode = "200", description = "Return Paginated Response",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginatedResponse.class)))
    @GetMapping
    public ResponseEntity<PaginatedResponse<TicketDto>> getAllUserTickets(TicketSearchDto ticketSearch) {
        log.debug("REST request to get user Tickets");
        PaginatedResponse<TicketDto> response = ticketService.getAllUserTickets(ticketSearch);
        return ResponseEntity.ok().body(response);
    }

}
