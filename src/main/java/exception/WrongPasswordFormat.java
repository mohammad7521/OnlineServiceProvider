package exception;

//in case password is entered in wrong format (not long enough or isn't alphanumeric)
public class WrongPasswordFormat extends RuntimeException{
    public WrongPasswordFormat() {
    }
}
