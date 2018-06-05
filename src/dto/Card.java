package dto;

/**Платежная карта */
public class Card {
	private int id;
	private int accountNumber;
	private boolean status;
	private int userId;

	public Card() {
	}

	public Card(int id, int accountNumber, boolean status, int userId) {
		this.id = id;
		this.accountNumber = accountNumber;
		this.status = status;
		this.userId= userId;
	}

	public boolean getStatus() {
		return status;
	}

	public int getId() {
		return id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "\nCard [id=" + id + ", accountNumber=" + accountNumber + ", status=" + status + ", userId=" + userId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accountNumber;
		result = prime * result + id;
		result = prime * result + (status ? 1231 : 1237);
		result = prime * result + userId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (accountNumber != other.accountNumber)
			return false;
		if (id != other.id)
			return false;
		if (status != other.status)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}
	
}