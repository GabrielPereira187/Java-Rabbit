package br.com.rabbit.entities;

import lombok.Builder;
import lombok.Data;

import java.time.Clock;
import java.time.LocalDateTime;

@Data
@Builder
public class Response {

    private LocalDateTime responseDate;
    private String description;
    private Integer statusCode;


}
