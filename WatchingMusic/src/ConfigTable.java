import java.util.ArrayList;

public class ConfigTable 
{
	ArrayList<SettingRule> Matrix_SettingList;
	ArrayList<SettingRule> Top_SettingList;
	
	public ConfigTable() 
	{
		Matrix_SettingList = new ArrayList<SettingRule>();
		Top_SettingList = new ArrayList<SettingRule>();
	}
	
	public boolean initialize(String FileName)
	{

		// Led 54 Par Light
		Top_SettingList.add(new SettingRule(true,31,0,"10.1.1.12",2346,0));
		Top_SettingList.add(new SettingRule(true,30,0,"10.1.1.12",2346,1));
		Top_SettingList.add(new SettingRule(true,29,0,"10.1.1.12",2346,2));
		Top_SettingList.add(new SettingRule(true,28,0,"10.1.1.12",2346,3));
		
		//  Led Matrix pad
		//#1
		Matrix_SettingList.add(new SettingRule(true,36,0,"10.1.1.6",2346,0));
		Matrix_SettingList.add(new SettingRule(true,37,0,"10.1.1.6",2346,1));
		Matrix_SettingList.add(new SettingRule(true,38,0,"10.1.1.6",2346,2));
		Matrix_SettingList.add(new SettingRule(true,39,0,"10.1.1.6",2346,3));
		Matrix_SettingList.add(new SettingRule(true,68,0,"10.1.1.6",2346,4));
		Matrix_SettingList.add(new SettingRule(true,69,0,"10.1.1.6",2346,5));
		Matrix_SettingList.add(new SettingRule(true,70,0,"10.1.1.6",2346,6));
		Matrix_SettingList.add(new SettingRule(true,71,0,"10.1.1.6",2346,7));
		
		//#2
		Matrix_SettingList.add(new SettingRule(true,75,0,"10.1.1.6",2346,8));
		Matrix_SettingList.add(new SettingRule(true,74,0,"10.1.1.6",2346,9));
		Matrix_SettingList.add(new SettingRule(true,73,0,"10.1.1.6",2346,10));
		Matrix_SettingList.add(new SettingRule(true,72,0,"10.1.1.6",2346,11));
		Matrix_SettingList.add(new SettingRule(true,43,0,"10.1.1.6",2346,12));
		Matrix_SettingList.add(new SettingRule(true,42,0,"10.1.1.6",2346,13));
		Matrix_SettingList.add(new SettingRule(true,41,0,"10.1.1.6",2346,14));
		Matrix_SettingList.add(new SettingRule(true,40,0,"10.1.1.6",2346,15));
		
		//#3
		Matrix_SettingList.add(new SettingRule(true,44,0,"10.1.1.6",2346,16));
		Matrix_SettingList.add(new SettingRule(true,45,0,"10.1.1.6",2346,17));
		Matrix_SettingList.add(new SettingRule(true,46,0,"10.1.1.6",2346,18));
		Matrix_SettingList.add(new SettingRule(true,47,0,"10.1.1.6",2346,19));
		Matrix_SettingList.add(new SettingRule(true,76,0,"10.1.1.6",2346,20));
		Matrix_SettingList.add(new SettingRule(true,77,0,"10.1.1.6",2346,21));
		Matrix_SettingList.add(new SettingRule(true,78,0,"10.1.1.6",2346,22));
		Matrix_SettingList.add(new SettingRule(true,79,0,"10.1.1.6",2346,23));
		
		//#4
		Matrix_SettingList.add(new SettingRule(true,83,0,"10.1.1.6",2346,24));
		Matrix_SettingList.add(new SettingRule(true,82,0,"10.1.1.6",2346,25));
		Matrix_SettingList.add(new SettingRule(true,81,0,"10.1.1.6",2346,26));
		Matrix_SettingList.add(new SettingRule(true,80,0,"10.1.1.6",2346,27));
		Matrix_SettingList.add(new SettingRule(true,51,0,"10.1.1.6",2346,28));
		Matrix_SettingList.add(new SettingRule(true,50,0,"10.1.1.6",2346,29));
		Matrix_SettingList.add(new SettingRule(true,49,0,"10.1.1.6",2346,30));
		Matrix_SettingList.add(new SettingRule(true,48,0,"10.1.1.6",2346,31));
		
		
		//#5
		Matrix_SettingList.add(new SettingRule(true,52,0,"10.1.1.6",2346,32));
		Matrix_SettingList.add(new SettingRule(true,53,0,"10.1.1.6",2346,33));
		Matrix_SettingList.add(new SettingRule(true,54,0,"10.1.1.6",2346,34));
		Matrix_SettingList.add(new SettingRule(true,55,0,"10.1.1.6",2346,35));
		Matrix_SettingList.add(new SettingRule(true,84,0,"10.1.1.6",2346,36));
		Matrix_SettingList.add(new SettingRule(true,85,0,"10.1.1.6",2346,37));
		Matrix_SettingList.add(new SettingRule(true,86,0,"10.1.1.6",2346,38));
		Matrix_SettingList.add(new SettingRule(true,87,0,"10.1.1.6",2346,39));
		
		//#6
		Matrix_SettingList.add(new SettingRule(true,91,0,"10.1.1.6",2346,40));
		Matrix_SettingList.add(new SettingRule(true,90,0,"10.1.1.6",2346,41));
		Matrix_SettingList.add(new SettingRule(true,89,0,"10.1.1.6",2346,42));
		Matrix_SettingList.add(new SettingRule(true,88,0,"10.1.1.6",2346,43));
		Matrix_SettingList.add(new SettingRule(true,59,0,"10.1.1.6",2346,44));
		Matrix_SettingList.add(new SettingRule(true,58,0,"10.1.1.6",2346,45));
		Matrix_SettingList.add(new SettingRule(true,57,0,"10.1.1.6",2346,46));
		Matrix_SettingList.add(new SettingRule(true,56,0,"10.1.1.6",2346,47));
		
		//#7
		Matrix_SettingList.add(new SettingRule(true,60,0,"10.1.1.6",2346,48));
		Matrix_SettingList.add(new SettingRule(true,61,0,"10.1.1.6",2346,49));
		Matrix_SettingList.add(new SettingRule(true,62,0,"10.1.1.6",2346,50));
		Matrix_SettingList.add(new SettingRule(true,63,0,"10.1.1.6",2346,51));
		Matrix_SettingList.add(new SettingRule(true,92,0,"10.1.1.6",2346,52));	
		Matrix_SettingList.add(new SettingRule(true,93,0,"10.1.1.6",2346,53));
		Matrix_SettingList.add(new SettingRule(true,94,0,"10.1.1.6",2346,54));
		Matrix_SettingList.add(new SettingRule(true,95,0,"10.1.1.6",2346,55));
		
		//#8
		Matrix_SettingList.add(new SettingRule(true,99,0,"10.1.1.6",2346,56));
		Matrix_SettingList.add(new SettingRule(true,98,0,"10.1.1.6",2346,57));
		Matrix_SettingList.add(new SettingRule(true,97,0,"10.1.1.6",2346,58));
		Matrix_SettingList.add(new SettingRule(true,96,0,"10.1.1.6",2346,59));
		Matrix_SettingList.add(new SettingRule(true,67,0,"10.1.1.6",2346,60));
		Matrix_SettingList.add(new SettingRule(true,66,0,"10.1.1.6",2346,61));
		Matrix_SettingList.add(new SettingRule(true,65,0,"10.1.1.6",2346,62));
		Matrix_SettingList.add(new SettingRule(true,64,0,"10.1.1.6",2346,63));
	
		return true;
	}

}
