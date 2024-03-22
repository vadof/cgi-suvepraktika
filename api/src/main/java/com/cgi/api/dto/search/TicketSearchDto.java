package com.cgi.api.dto.search;

import com.cgi.api.entities.Ticket;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class TicketSearchDto extends SearchDto<Ticket> {

    @JsonIgnore
    private Long userId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long movieSessionId;

    @Override
    protected void addFilters(Root<Ticket> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, List<Predicate> filters) {
        if (userId != null) {
            filters.add(criteriaBuilder.equal(root.get("user").get("id"), userId));
        }

        if (movieSessionId != null) {
            filters.add(criteriaBuilder.equal(root.get("movieSession").get("id"), movieSessionId));
        }
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setMovieSessionId(Long movieSessionId) {
        this.movieSessionId = movieSessionId;
    }
}
