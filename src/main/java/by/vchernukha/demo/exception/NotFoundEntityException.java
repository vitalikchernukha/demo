package by.vchernukha.demo.exception;

public class NotFoundEntityException extends Exception {

    public NotFoundEntityException(String errorMessage) {
        super(errorMessage);
    }

}
