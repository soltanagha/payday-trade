package com.payday.common.exception.util;

public enum ExceptionCodes implements ExceptionKeyAndMessage {

    MAX_UPLOAD_SIZE_EXCEEDED("3650"),
    CONNECT_EXCEPTION("3651","Connection error!"),
    JSON_EXCEPTION("3986","JSON parsing error!"),
    API_LIMIT_EXCEED("4545","API free limit exceed!"),
    SERVLET_EXCEPTION("3818", "Servlet exception"),
    METHOD_ARGUMENT_TYPE_MISMATCH("3653"),
    METHOD_ARGUMENT_NOT_VALID("3700"),
    DUPLICATE_DATA_EXCEPTION("3816", "This email already exits."), 
    USER_NOT_FOUND("3837", "User not found"), 
    JWT_EXPIRED("3840", "JWT is expired"),
    EMAIL_NOT_VERIFIED("3864", "Email is not verified"),
    INVALID_TOKEN("3844","Invalid token"),
    INVALID_ACTIVATION_KEY("3845","Invalid activation key!"),
    MALFORMED_TOKEN("3865","Malformed token"),
    INVALID_EMAIL_OR_SYMBOL("4034","Invalid email or symbol"),
    RESERVED_BALANCE_DOES_NOT_EXIST("4035","Reserved balance doesn't exist");

    private String exceptionKey;
    private final String message;

    ExceptionCodes(String exceptionKey, String message) {
        this.exceptionKey = exceptionKey;
        this.message = message;
    }

    ExceptionCodes(String exceptionKey) {
        this.exceptionKey = exceptionKey;
        this.message = "NO Message";
    }

    @Override
    public String getExceptionKey() {
        return exceptionKey;
    }

    @Override
    public String getExceptionMessage() {
        return message;
    }

    public ExceptionCodes exceptionKey(String exceptionKey) {
        this.exceptionKey = exceptionKey;
        return this;
    }
}
