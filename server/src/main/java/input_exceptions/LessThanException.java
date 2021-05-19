package input_exceptions;

public class LessThanException extends Exception{
    private int number;
    public LessThanException(int number1) {
        number = number1;
    }

    public int getNumber() {
        return number;
    }
}
