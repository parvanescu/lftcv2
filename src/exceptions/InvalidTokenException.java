package exceptions;

public class InvalidTokenException{
    public String message;

    public InvalidTokenException(Integer line,Integer tokenNr) {
        this.message = "Invalid token exception at line: " + line + " at token no. "+tokenNr;
    }

    @Override
    public String toString() {
        return message+"\n";
    }
}
