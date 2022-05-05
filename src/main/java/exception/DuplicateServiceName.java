package exception;


//in case two services with the same name are trying to be added
public class DuplicateServiceName extends RuntimeException {
    public DuplicateServiceName() {
    }
}
