package com.cgi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatsInfo {
    private Integer rows;
    private Integer seatsInRow;
    private int[][] seats;
}
