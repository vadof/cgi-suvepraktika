package com.cgi.api.utils;

import com.cgi.api.entities.Ticket;

import java.util.ArrayList;
import java.util.List;

public class SeatRecommendation {
    private static List<Recommendation> getRecommendedSeats(int[][] arr, int peopleAmount, int rows, int seats) {
        if (peopleAmount > seats) return new ArrayList<>();

        List<Recommendation> result = new ArrayList<>();

        int bestValue = Integer.MAX_VALUE;
        int mid = rows / 2;

        for (int row = 0; row < rows; row++) {
            int placed = 0;

            int rowValue = Math.abs(rows - 1 - row * 2);
            if (row < mid) rowValue -= row - 1;
            else if (row > mid) rowValue += row - mid;

            int from = 0;
            for (int seat = 0; seat < seats; seat++) {
                if (arr[row][seat] == 0) {
                    placed++;
                    if (placed == peopleAmount) {
                        int seatValue = Math.abs(seats - from - seat - 1);
                        int total = rowValue + seatValue;

                        if (total <= bestValue) {
                            bestValue = total;
                            result.add(new Recommendation(bestValue, row, from, seat + 1));
                        }

                        placed--;
                        from++;
                    }
                } else {
                    from = seat + 1;
                    placed = 0;
                }
            }
        }

        return result;
    }

    public static void markRecommendedSeats(int[][] arr, int peopleAmount, int rows, int seatsInRow, int limit) {
        List<Recommendation> recomendationList = getRecommendedSeats(arr, peopleAmount, rows, seatsInRow);
        if (recomendationList.size() == 0) return;
        int bestValue = recomendationList.get(recomendationList.size() - 1).value;

        for (int i = recomendationList.size() - 1; i >= 0 && limit > 0; i--) {
            Recommendation best = recomendationList.get(i);
            if (best.value == bestValue || best.value == bestValue + 1) {
                for (int seat = best.from; seat < best.to; seat++) {
                    arr[best.row][seat] = 2;
                }
                limit--;
            } else {
                break;
            }
        }
    }

    public static void markReservedSeats(int[][] arr, List<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            arr[ticket.getRowNumber()][ticket.getSeatNumber()] = 1;
        }
    }

    private static class Recommendation {

        int value;
        int row;
        int from;
        int to;

        public Recommendation(int value, int row, int from, int to) {
            this.value = value;
            this.row = row;
            this.from = from;
            this.to = to;
        }
    }
}
