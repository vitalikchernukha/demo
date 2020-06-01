package by.vchernukha.demo.exception;

public class NegativeBalanceException extends Exception {

    public NegativeBalanceException(String errorMessage) {
        super(errorMessage);
    }

}
