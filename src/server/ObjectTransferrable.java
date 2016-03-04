package server;

public class ObjectTransferrable implements Runnable {

	private static final long serialVersionUID = 85731937678276152L;
	private String opCode;

	public String getOpCode(){
		return opCode;
	}

	public void setOpCode(String opCode){
		this.opCode = opCode;
	}

	public void run() {
	}
}
