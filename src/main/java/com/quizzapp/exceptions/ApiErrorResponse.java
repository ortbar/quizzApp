package com.quizzapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ApiErrorResponse {

        private String message;
        private int status;
        private String timestamp;

}
