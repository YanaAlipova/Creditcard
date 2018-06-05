package dto;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**User abstract */

public abstract class UserBanking {
	protected int role;
	protected int id;
	protected String password;
	protected String md5password; 
	protected String name;
	protected List<Card> cards = new ArrayList<>(); //карты
	protected List<Account> accounts = new ArrayList<>(); //счета

	public UserBanking() {
	}

	public UserBanking(int inId, int inRole) {
		id = inId;
		role = inRole;
	}

	public UserBanking(int inId, int inRole, String password) {
		id = inId;
		role = inRole;
		md5password = md5Custom(password);
	}

	// шифрование пароля
	public static String md5Custom(String st) {
		String soul = "Солянка test!";
		st = st.concat(soul);
		MessageDigest messageDigest = null;
		byte[] digest = new byte[0];
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(st.getBytes());
			digest = messageDigest.digest();
		} catch (NoSuchAlgorithmException e) {
			// тут можно обработать ошибку
			// возникает она если в передаваемый алгоритм в getInstance(,,,) не
			// существует
			e.printStackTrace();
		}
		BigInteger bigInt = new BigInteger(1, digest);
		String md5Hex = bigInt.toString(16);
		while (md5Hex.length() < 32) {
			md5Hex = "0" + md5Hex;
		}
		return md5Hex;
	}

	public int getRole() {
		return role;
	};

	public int getId() {
		return id;
	};

	public String getMd5password() {
		return md5password;
	}

	public void setMd5password(String md) {
		this.md5password = md;
	}

	public void setRole(int r) {
		role = r;
	};

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return this.password;
	};

	public void setPassword(String pas) {
		password = pas;
	};

	public List<Card> getCards() {
		return cards;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public abstract ArrayList<Account> getAccounts();

	public abstract void setAccounts(ArrayList<Account> accounts);

	@Override
	public String toString() {
		return "UserBanking [role=" + role + ", id=" + id + ", password=" + password + ", md5password=" + md5password + ", cards=" + cards + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accounts == null) ? 0 : accounts.hashCode());
		result = prime * result + ((cards == null) ? 0 : cards.hashCode());
		result = prime * result + id;
		result = prime * result + ((md5password == null) ? 0 : md5password.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + role;
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
		UserBanking other = (UserBanking) obj;
		if (accounts == null) {
			if (other.accounts != null)
				return false;
		} else if (!accounts.equals(other.accounts))
			return false;
		if (cards == null) {
			if (other.cards != null)
				return false;
		} else if (!cards.equals(other.cards))
			return false;
		if (id != other.id)
			return false;
		if (md5password == null) {
			if (other.md5password != null)
				return false;
		} else if (!md5password.equals(other.md5password))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (role != other.role)
			return false;
		return true;
	}

	
}
