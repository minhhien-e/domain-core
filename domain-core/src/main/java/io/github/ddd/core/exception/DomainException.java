package io.github.ddd.core.exception;

public abstract class DomainException extends RuntimeException {
    private final Integer errorCode;

    protected DomainException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    protected DomainException(Integer errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
