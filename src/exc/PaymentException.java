package exc;

public class PaymentException extends Exception{
	/**
	 * Ошибки в платежах (для общего развития)
	 */
	private static final long serialVersionUID = 1L;
	private String exc= new String();
	
	public PaymentException(String e) {
		exc = e;
	}
	public String toString(){
		return "<br/> У нас возникли следующие недопонимания: " + exc + " (автор - PaymentException)";
	}
}
