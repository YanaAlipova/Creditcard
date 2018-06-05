package dto;

import java.util.ArrayList;
import java.util.List;

/**Клиент */
public class UserBankingClient extends UserBanking {
	// список счетов
	private List<Account> accounts = new ArrayList<>();

	public UserBankingClient() {
		// accounts= null;
		role = 0;
	}

	public UserBankingClient(int inId, int inRole) {
		super(inId, inRole);
	}

	public UserBankingClient(int inId, int inRole, String pas) {
		super(inId, inRole, pas);
	}

	@Override
	public void setAccounts(ArrayList<Account> accounts) {
		this.accounts = accounts;
	}

	@Override
	public ArrayList<Account> getAccounts() {
		return (ArrayList<Account>) this.accounts;
	}

	@Override
	public String toString() {
		// return "UserBankingClient [accounts=" + accounts + ", \nrole=" + role + ", id=" + id + "]";
		return "UserBankingClient \n[ role=" + role + ", id=" + id + "password=" + password + ", md5password=" + md5password + "\naccounts=" + accounts + "]";
	}

}
