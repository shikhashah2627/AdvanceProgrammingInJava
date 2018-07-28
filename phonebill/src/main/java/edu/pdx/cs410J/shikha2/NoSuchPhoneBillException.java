package edu.pdx.cs410J.shikha2;

import javax.management.RuntimeErrorException;

public class NoSuchPhoneBillException extends RuntimeException {
    private final String customerName;

    /**
     * Default constructor.
     *
     * @param customerName the wrapped error.
     */
    public NoSuchPhoneBillException(String customerName) {
        this.customerName = customerName;
    }
}
