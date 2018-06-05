package bsu;

//(на перспективу)вынести имена нужных полей из ICommand 
public enum FildName {
	CMD_VALUE {
		{
			this.fildName = "cmdValue";
		}
	},
	SESSION_USER {
		{
			this.fildName = "currentUser";
		}
	},
	MESSAGE {
		{
			this.fildName = "message";
		}
	};

	public String fildName;

	public String getFildName() {
		return fildName;
	}
}
