package service;

public class OperationResult {

    private final boolean success;
    private final String message;
    private final Object data;

    private OperationResult(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static OperationResult ok(String message) {
        return new OperationResult(true, message, null);
    }

    public static OperationResult ok(String message, Object data) {
        return new OperationResult(true, message, data);
    }

    public static OperationResult error(String message) {
        return new OperationResult(false, message, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}