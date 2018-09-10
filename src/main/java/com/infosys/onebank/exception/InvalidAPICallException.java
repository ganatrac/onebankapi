package com.infosys.onebank.exception;

/**
 * Created by chirag.ganatra on 9/8/2018.
 */
public class InvalidAPICallException extends RuntimeException {
    public InvalidAPICallException(String message) {
        super(message);
    }
}
