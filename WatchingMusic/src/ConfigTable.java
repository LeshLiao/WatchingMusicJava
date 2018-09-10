import java.util.ArrayList;

public class ConfigTable 
{
	ArrayList<SettingRule> SettingList;
	
	public ConfigTable() 
	{
		SettingList = new ArrayList<SettingRule>();
		// TODO Auto-generated constructor stub
	}
	
	public boolean initialize(String FileName)
	{
		SettingList.add(new SettingRule(true,108,0,"10.1.1.6",2346,0));
		SettingList.add(new SettingRule(true,109,0,"10.1.1.6",2346,1));
		SettingList.add(new SettingRule(true,110,0,"10.1.1.6",2346,2));
		
		SettingList.add(new SettingRule(true,111,0,"10.1.1.6",2346,3));
		SettingList.add(new SettingRule(true,112,0,"10.1.1.6",2346,4));
		SettingList.add(new SettingRule(true,113,0,"10.1.1.6",2346,5));
		SettingList.add(new SettingRule(true,114,0,"10.1.1.6",2346,6));
		SettingList.add(new SettingRule(true,115,0,"10.1.1.6",2346,7));
		return true;
	}

}
