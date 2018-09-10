
import netP5.NetAddress;

public class SettingRule {

	boolean Enable;
	int Note;
	int condition;
	//ArrayList<NetAddress> NetSettings;
	NetAddress NetSettings;	
	int Tag;
	
	public SettingRule(boolean _Enable,int _Note,int _condition,String _IP,int _Port,int _tag) {
		Enable = _Enable;
		Note = _Note;
		condition = _condition;
		NetSettings = new NetAddress(_IP,_Port);
		Tag = _tag;
	}

}
