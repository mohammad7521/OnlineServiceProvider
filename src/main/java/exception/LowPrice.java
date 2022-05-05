package exception;

//when the customer offers a price lower than the base price of the service
public class LowPrice extends RuntimeException{
    public LowPrice() {
    }
}
