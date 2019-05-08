package UserInterface;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import netP5.NetAddress;
import netP5.NetAddressList;

public class StatusTable extends JTable{
	private String [] BookField;
	private String [][] tmpTableData;
	private DefaultTableModel tmodel;
	NetAddressList reachableAddressList = new NetAddressList();
	NetAddressList availableAddressList = new NetAddressList();
	List<List<String>> listOfLists;
	
	public StatusTable() {
		String [][] td = {{"<No Device>","","","",""}};
		//String [][] td = {{"192.168.0.11","","","",""},{"192.168.0.22","","","",""},{"192.168.0.33","","","",""}};
		tmpTableData = td;
        BookField = new String[]{"IP","Port","Git version","Json file","OSC server"};
        tmodel = new DefaultTableModel(tmpTableData,BookField); //建立表格
        this.setModel(tmodel);
        listOfLists = new ArrayList<List<String>>();
	}
	public boolean ClearTable()
	{
		reachableAddressList.list().clear();
		availableAddressList.list().clear();
		
		int rowCount = tmodel.getRowCount();
		//Remove rows one by one from the end of the table
		for (int i = rowCount - 1; i >= 0; i--) {
			tmodel.removeRow(i);
		}
		return true;
	}
	
	public boolean AddDevice(String _IPAddress,String _Port)
	{
		tmodel.addRow(new Object[]{_IPAddress,_Port,"","",""});
		return true;
	}
	
	public void AddToReachableAddressList(String theIPaddress,int port) 
	{
		if (!reachableAddressList.contains(theIPaddress, port)) 
		{
			reachableAddressList.add(new NetAddress(theIPaddress, port));
			tmodel.addRow(new Object[]{theIPaddress,Integer.toString(port),"","",""});
			System.out.println("### adding "+theIPaddress+" to the reachable AddressList.");
		} 
		else 
		{
			System.out.println("### "+theIPaddress+" has already existed.");
		}
	}
	
	public void AddToAvailableAddressList(String theIPaddress,int port) 
	{
		if (!availableAddressList.contains(theIPaddress, port)) 
		{
			availableAddressList.add(new NetAddress(theIPaddress, port));
			tmodel.setValueAt("True", getIndexByAddress(theIPaddress), 4);
			System.out.println("### adding "+theIPaddress+" to the available AddressList.");
		} 
		else 
		{
			System.out.println("### "+theIPaddress+" has already existed.");
		}
	}
	
	public void UpdateGitHash(String theIPaddress,String GitSha) 
	{
		tmodel.setValueAt(GitSha, getIndexByAddress(theIPaddress), 2);
	}
	
	private int getIndexByAddress(String theIPaddress) 
	{
		int IpIndex = 0;
		for(int i = 0;i < reachableAddressList.size() ;i++)
		{
			if(theIPaddress.equals(reachableAddressList.get(i).address()))
			{
				IpIndex = i;
				break;
			}
		}
		return IpIndex;
	}
	
}
