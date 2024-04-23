package com.igot.karmaquest.exceptions;

/**
 * @author Mahesh RV
 */
public class ProjectCommonException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final String code;
    private final String message;
    private final int responseCode;

    public ProjectCommonException(String code, String message, int responseCode) {
        super();
        this.code = code;
        this.message = message;
        this.responseCode = responseCode;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getResponseCode() {
        return responseCode;
    }

}