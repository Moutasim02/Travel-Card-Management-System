package com.demo.travelcardsystem.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class TravelCardException extends RuntimeException{
    protected TravelCardException(String message) {
        super(message);
    }
}
