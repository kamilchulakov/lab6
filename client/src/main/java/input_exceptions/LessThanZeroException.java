package input_exceptions;

public class LessThanZeroException extends LessThanException{

    public LessThanZeroException(int number1) {
        super(0);
    }
    public LessThanZeroException() {
        super(0);
    }
}
