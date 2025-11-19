package com.adarsh.booking_app_common.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookTicketRequestDto {

    private String userId;
    private List<String> seats;
    private String movieCode;

}
