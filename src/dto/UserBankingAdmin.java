package dto;

import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
import java.util.List;

/** Администратор (разблокирует карты)*/
public class UserBankingAdmin extends UserBanking {
	private final int role = 1;

	public UserBankingAdmin() {
	}

	public UserBankingAdmin(int inId, int inRole) {
		super(inId, inRole);
	}

	public int getRole() {
		return role;
	}

	public List<Card> getCards() {
		return this.cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public ArrayList<Account> getAccounts() {
		return null;
	}

	public void setAccounts(ArrayList<Account> accounts) {
	}

	@Override
	public String toString() {
		return "UserBankingAdmin [role=" + role + ", id=" + id + ", password=" + password + ", md5password=" + md5password + ", cards=" + cards + "]";
	}
	
	

}
