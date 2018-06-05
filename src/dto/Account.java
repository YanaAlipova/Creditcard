package dto;

import java.util.ArrayList;
import java.util.Collection;

/**Счет пользователя */
public class Account {
	private int id;
	private Collection<Card> cards = new ArrayList<>();
	private int userId;
	private int ballance;

	public Account(int id,  Collection<Card> cards, int userId, int ballance) {
		this.id = id;
		this.cards = cards;
		this.userId = userId;
		this.ballance = ballance;
	}

	public Account() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBallance() {
		return ballance;
	}

	public void setBallance(int ballance) {
		this.ballance = ballance;
	}

	public Collection<Card> getCards() {
		return cards;
	}

	public void setCards(Collection<Card> cards) {
		this.cards = cards;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "\nAccount [id=" + id + ", cards=" + cards + ", userId=" + userId + ", ballance=" + ballance/100 + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ballance;
		result = prime * result + ((cards == null) ? 0 : cards.hashCode());
		result = prime * result + id;
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
		Account other = (Account) obj;
		if (ballance != other.ballance)
			return false;
		if (cards == null) {
			if (other.cards != null)
				return false;
		} else if (!cards.equals(other.cards))
			return false;
		if (id != other.id)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}
	
}