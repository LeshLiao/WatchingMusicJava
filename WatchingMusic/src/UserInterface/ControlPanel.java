
package UserInterface;

import javax.swing.*;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import Communication.RemoteControl;
import Communication.SyncFile;
import Data.ConfigTable;
import netP5.NetAddress;
import netP5.NetAddressList;
import oscP5.OscEventListener;
import oscP5.OscMessage;
import oscP5.OscP5;
import oscP5.OscStatus;

import java.awt.*; 
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ControlPanel extends JFrame
{ 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	OscP5 oscP5;
	NetAddressList reachableAddressList = new NetAddressList();
	NetAddressList availableAddressList = new NetAddressList();
	
	InetAddress inetAddress;
	String _myHostAddress;
	int _myHostport;
	ConfigTable NewConfig;
	RemoteControl newRemoteControl;
	
	
	public ControlPanel(ConfigTable _newConfig) 
    { 
		super("Watching Music Control Panel"); 
		setSize(240,500); 
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); 
		setVisible(true); 
		
		NewConfig = _newConfig;
		
		
		try {
			init();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		oscP5 = new OscP5(this, _myHostport);
			
		Container pn = getContentPane(); 
		pn.setBackground(new Color(0,0,0));
		
		FlowLayout fy = new FlowLayout();
		pn.setLayout(fy); 

		JButton bt1 = new JButton("Scan network"); 
		pn.add(bt1); 
		
		JButton bt2 = new JButton("Check Osc connection"); 
		pn.add(bt2); 
		
		JButton bt3 = new JButton("Sync Json Files"); 
		pn.add(bt3); 
		
		JButton bt4 = new JButton("Breathing light"); 
		pn.add(bt4);
		
		JButton bt5 = new JButton("Run all OSC service"); 
		pn.add(bt5);
		
		JButton bt6 = new JButton("Stop all OSC service"); 
		pn.add(bt6);
		
		JButton bt7 = new JButton("Pull latest version"); 
		pn.add(bt7);
		
		setContentPane(pn); 
		
		
		bt1.addActionListener(new ActionListener(){ 
			public void actionPerformed(ActionEvent e) 
			{ 
				
				String subnet = "172.20.10";
				reachableAddressList.list().clear();
				System.out.println("Button Event: Scan subnet:"+subnet);
				
				try {
					checkHosts(subnet);
				} catch (IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
				//JOptionPane.showMessageDialog(null,"Done!"); 
				
			} 
		}); 
		
		bt2.addActionListener(new ActionListener()
		{ 
			public void actionPerformed(ActionEvent e) 
			{ 
				availableAddressList.list().clear();
				OscMessage myMessage = new OscMessage("/Instruction");
				myMessage.add("CHECK_OSC");
				myMessage.add(_myHostAddress); 
				myMessage.add(Integer.toString(_myHostport)); 
				oscP5.send(myMessage, reachableAddressList);
				System.out.println("Button Event: CHECK_OSC");
			} 
		}); 
			
		bt3.addActionListener(new ActionListener()
		{ 
			public void actionPerformed(ActionEvent e) 
			{ 
				SyncFile newSyncFile;
				System.out.println("Button Event: SYNC_JSON");

				try {
					System.out.println("Reload:StationSetup.json");
					NewConfig.LoadJsonFile("StationSetup.json");
					NewConfig.initNetSettings();
					System.out.println("Json TimeStamp:"+NewConfig.getTimeStamp());
					
					for (int i=0; i<availableAddressList.size(); i++) 
					{
			            System.out.println("put file to:"+availableAddressList.get(i).address());
					
						newSyncFile = new SyncFile("pi","raspberry",availableAddressList.get(i).address());
						newSyncFile.putFile("StationSetup.json", "rpi-ws281x-python-and-osc/examples/config/StationSetup.json");
						newSyncFile.Close();
						
						OscMessage myMessage = new OscMessage("/Instruction");
						myMessage.add("RELOAD_JSON");
						myMessage.add("0"); 
						myMessage.add("0"); 
						
						oscP5.send(myMessage, availableAddressList.get(i));
					}
					
				} catch (JSchException | SftpException | FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("Copy completed!");
				
			} 
		}); 		
		
		bt4.addActionListener(new ActionListener()
		{ 
			public void actionPerformed(ActionEvent e) 
			{                                            
				OscMessage myMessage = new OscMessage("/Instruction");
				myMessage.add("BREATHING_LIGHT");
				myMessage.add("256"); 
				myMessage.add("64"); 
				
				oscP5.send(myMessage, availableAddressList);
				System.out.println("Button Event: BREATHING_LIGHT");
			} 
		}); 
		
		bt5.addActionListener(new ActionListener()
		{ 
			public void actionPerformed(ActionEvent e) 
			{                                            
				for (int i=0; i<reachableAddressList.size(); i++) 
				{
		            System.out.println("send commend to:"+reachableAddressList.get(i).address());
				
		            try {
						newRemoteControl = new RemoteControl("pi","raspberry",reachableAddressList.get(i).address());
						newRemoteControl.sendCommend("sudo python3 rpi-ws281x-python-and-osc/examples/osc_server.py");
			            newRemoteControl.Close();
		            } catch (JSchException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				System.out.println("Button Event: RUN_OSC");
			} 
		});
		
		bt6.addActionListener(new ActionListener()
		{ 
			public void actionPerformed(ActionEvent e) 
			{                                            
				for (int i=0; i<reachableAddressList.size(); i++) 
				{
		            System.out.println("send commend to:"+reachableAddressList.get(i).address());
				
		            try {
						newRemoteControl = new RemoteControl("pi","raspberry",reachableAddressList.get(i).address());
			            newRemoteControl.sendCommend("sudo pkill -9 python");
			            newRemoteControl.Close();
		            } catch (JSchException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				System.out.println("Button Event: STOP_OSC");
			} 
		});
		
		bt7.addActionListener(new ActionListener()
		{ 
			public void actionPerformed(ActionEvent e) 
			{                                            
				for (int i=0; i<reachableAddressList.size(); i++) 
				{
		            System.out.println("send commend to:"+reachableAddressList.get(i).address());
				
		            try {
						newRemoteControl = new RemoteControl("pi","raspberry",reachableAddressList.get(i).address());
						newRemoteControl.sendCommend("sudo sh rpi-ws281x-python-and-osc/git_pull_script.sh");
			            //newRemoteControl.Close();   // close so fast, so we comment it.
		            } catch (JSchException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				System.out.println("Button Event: GIT_PULL");
			} 
		});
		
		oscP5.addListener(new OscEventListener()
		{

			@Override
			public void oscEvent(OscMessage arg0) 
			{
				// TODO Auto-generated method stub
				System.out.println("Control Panel oscEvent:"+arg0.addrPattern());
				if(arg0.addrPattern().equals("/Response"))
				{
					AddToAvailableAddressList(arg0.get(0).stringValue(),arg0.get(1).intValue());
					
					System.out.print("IP:"+arg0.get(0).stringValue());
					System.out.println(", GIT SHA:"+arg0.get(2).stringValue().substring(0, 7));
					//System.out.println("2):"+Integer.toString(arg0.get(1).intValue()));
					
				}
				System.out.println("### currently there are "+availableAddressList.list().size()+" available OSC device.");
			
				
			}

			@Override
			public void oscStatus(OscStatus arg0) 
			{
				// TODO Auto-generated method stub
				System.out.println("Control Panel oscStatus:"+arg0.toString());
			}
			
			
		});
		
    } 
	
	private void init() throws UnknownHostException
	{
		inetAddress = InetAddress.getLocalHost();
		_myHostAddress = inetAddress.getHostAddress();	
		_myHostport = 2349;
	
	}
	
	public void checkHosts(String subnet) throws UnknownHostException, IOException
	{

		int timeout=50;
		int OSC_port = 2346;
		for (int i=1;i<15;i++)
		{
			String host=subnet + "." + i;
		
			if(_myHostAddress.equals(host))
				continue;
		
			if (InetAddress.getByName(host).isReachable(timeout))
			{
	            try 
	            {
					newRemoteControl = new RemoteControl("pi","raspberry",host);
		            //newRemoteControl.Close();   // close so fast, so we comment it.
		            AddToReachableAddressList(host,OSC_port);
	            } catch (JSchException e1) {
					// TODO Auto-generated catch block
	            	//e1.
	            	
	            	
	            	System.out.println(host+":"+e1.getMessage());
					//e1.printStackTrace();
				}
			}
		}
		System.out.println("### currently there are "+reachableAddressList.list().size()+" reachable locations.");
	}
	
	private void AddToReachableAddressList(String theIPaddress,int port) 
	{
		if (!reachableAddressList.contains(theIPaddress, port)) 
		{
			reachableAddressList.add(new NetAddress(theIPaddress, port));
			System.out.println("### adding "+theIPaddress+" to the reachable AddressList.");
		} 
		else 
		{
			System.out.println("### "+theIPaddress+" has already existed.");
		}
		
	}
	
	private void AddToAvailableAddressList(String theIPaddress,int port) 
	{
		if (!availableAddressList.contains(theIPaddress, port)) 
		{
			availableAddressList.add(new NetAddress(theIPaddress, port));
			System.out.println("### adding "+theIPaddress+" to the available AddressList.");
		} 
		else 
		{
			System.out.println("### "+theIPaddress+" has already existed.");
		}
		
	}
}