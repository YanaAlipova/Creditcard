package bsu;
/**Список комманд */
public enum CommandEnum {
	LOGIN {
		{
			this.command = new LoginUserComandImpl();
		}
	},
	LOGOUT {
		{
			this.command = new LogoutUserComandImpl();
		}
	},
	CARD_BLOCKING {
		{
			this.command = new CardBlockingComandImpl();
		}
	}
	,
	CARD_UNBLOCKING {
		{
			this.command = new CardUnblockingComandImpl();
		}
	},
	ACCOUNT_RECHARGE {
		{
			this.command = new AccountRechargeComandImpl();
		}
	},
	CREATE_PAYMENT {
		{
			this.command = new CreatePaymentComandImpl();
		}
	};

	ICommand command;

	public ICommand getCurrentCommand() {
		return command;
	}
}
