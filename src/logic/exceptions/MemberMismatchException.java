package logic.exceptions;

public abstract class MemberMismatchException extends RuntimeException{
    private String message;
    public MemberMismatchException(String s) {
        this.message = s;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
