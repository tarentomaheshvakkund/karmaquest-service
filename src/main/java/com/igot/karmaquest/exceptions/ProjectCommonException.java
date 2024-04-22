package com.igot.karmaquest.exceptions;

/**
 * This exception will be used across all backend code. This will send status code and error message
 *
 * @author Mahesh RV
 */
public class ProjectCommonException extends RuntimeException {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * code String code ResponseCode.
     */
    private  String code;
    /**
     * message String ResponseCode.
     */
    private   String message;
    /**
     * responseCode int ResponseCode.
     */
    private  int responseCode;

    /**
     * three argument constructor.
     *
     * @param code         String
     * @param message      String
     * @param responseCode int
     */
    public ProjectCommonException(String code, String message, int responseCode) {
        super();
        this.code = code;
        this.message = message;
        this.responseCode = responseCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
}