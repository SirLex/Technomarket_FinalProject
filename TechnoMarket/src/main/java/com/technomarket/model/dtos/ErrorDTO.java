package com.technomarket.model.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class ErrorDTO {

    private String msg;

    private int status;

    private LocalDateTime time;
}
