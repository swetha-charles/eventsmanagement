package objectTransferrable;

public abstract class ObjectTransferrable implements java.io.Serializable{

	private static final long serialVersionUID = 85731937678276152L;
	private final String opCode;

		public ObjectTransferrable(String opCode){
			this.opCode = opCode;
		}
	public String getOpCode(){
		return opCode;
	}
}
