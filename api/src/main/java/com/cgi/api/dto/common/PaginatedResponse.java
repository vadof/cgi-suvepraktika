package com.cgi.api.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginatedResponse<T> {
    private Integer page;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer totalPages;
    private Integer size;
    private String sortingFields;
    private String sortDirection;
    private List<T> data;
}
