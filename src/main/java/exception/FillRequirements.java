package exception;


//in case required fields for user(username,password,email) are not set
public class FillRequirements extends RuntimeException{
    public FillRequirements() {
    }
}
