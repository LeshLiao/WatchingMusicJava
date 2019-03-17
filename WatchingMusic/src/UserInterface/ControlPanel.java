
package UserInterface;

import javax.swing.*;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import Communication.SyncFile;
import netP5.NetAddress;
import netP5.NetAddressList;
import oscP5.OscEventListener;
import oscP5.OscMessage;
import oscP5.OscP5;
import oscP5.OscStatus;

import java.awt.*; 
import java.awt.event.*;
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
	
	
	public ControlPanel() 
    { 
		super("Watching Music Control Panel"); 
		setSize(600,300); 
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); 
		setVisible(true); 
		
		
		
		try {
			init();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		oscP5 = new OscP5(this, _myHostport);
			
		Container pn = getContentPane(); 
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
					
					for (int i=0; i<availableAddressList.size(); i++) 
					{
			            System.out.println("put file to:"+availableAddressList.get(i).address());
					
						newSyncFile = new SyncFile("pi","raspberry",availableAddressList.get(i).address());
						newSyncFile.putFile("StationSetup.json", "rpi-ws281x-python-and-osc/examples/config/StationSetup.json");
						newSyncFile.Close();
					}
					
				} catch (JSchException | SftpException e1) {
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
					//System.out.println("1):"+arg0.get(0).stringValue());
					//System.out.println("2):"+Integer.toString(arg0.get(1).intValue()));
				}
				
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
		for (int i=1;i<15;i++)
		{
			String host=subnet + "." + i;
		
			if(_myHostAddress.equals(host))
				continue;
		
			if (InetAddress.getByName(host).isReachable(timeout))
			{
				AddToReachableAddressList(host,2346);
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
		System.out.println("### currently there are "+availableAddressList.list().size()+" available OSC device.");
	}

}