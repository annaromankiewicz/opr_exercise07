package exceptions;

public class InvalidAccessException extends Exception {
    private int whichValue;

    public InvalidAccessException() {
        super();
        whichValue = 0;
    }

    public InvalidAccessException(String msg, int whichValue) {
        super(msg);
        this.whichValue = whichValue;
    }

    public InvalidAccessException(String msg) {
        super(msg);
        whichValue = 0;
    }

    public int getWhichValue() {
        return whichValue;
    }

}
