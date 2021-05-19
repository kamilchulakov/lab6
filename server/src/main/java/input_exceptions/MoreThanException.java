package input_exceptions;

public class MoreThanException extends Exception{
    private int number;
    public MoreThanException(int number1) {
        number = number1;
    }

    public int getNumber() {
        return number;
    }
}
