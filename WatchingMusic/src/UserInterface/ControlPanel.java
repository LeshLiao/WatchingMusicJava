
package UserInterface;

import javax.swing.*;

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

	NetAddressList reachableAddressList = new NetAddressList();
	NetAddressList availableAddressList = new NetAddressList();
	
	OscP5 oscP5;
	
	public ControlPanel() 
    { 
		super("Watching Music Control Panel"); 
		setSize(600,300); 
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); 
		setVisible(true); 
		
		oscP5 = new OscP5(this, 2349);
			
			//容器 
			Container pn = getContentPane(); 
				//設定成FlowLayout
				FlowLayout fy = new FlowLayout();
				pn.setLayout(fy); 


			//建立button
			JButton bt1 = new JButton("Scan network"); 
			pn.add(bt1); 
			
			JButton bt2 = new JButton("Check Osc connection"); 
			pn.add(bt2); 
			


			//bt1事件的動作
			bt1.addActionListener(new ActionListener(){ 
				public void actionPerformed(ActionEvent e) 
				{ 
					try {
						checkHosts("172.20.10");
					} catch (IOException ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
					System.out.println("Done!");
					//JOptionPane.showMessageDialog(null,"Done!"); //呼叫msgbox
				} 
			}); 
			
			//bt2事件的動作
			bt2.addActionListener(new ActionListener()
			{ 
				public void actionPerformed(ActionEvent e) 
				{ 
					OscMessage myMessage = new OscMessage("/Instruction");
					myMessage.add(123); /* add an int to the osc message */
					oscP5.send(myMessage, reachableAddressList);
					System.out.println("Done!");
					//JOptionPane.showMessageDialog(null,"Done!"); //呼叫msgbox
				} 
			}); 
		setContentPane(pn); 
		
		
		oscP5.addListener(new OscEventListener()
		{

			@Override
			public void oscEvent(OscMessage arg0) 
			{
				// TODO Auto-generated method stub
				System.out.println("Control Panel oscEvent:"+arg0.addrPattern());
				if(arg0.addrPattern().equals("/Response"))
				{
					///System.out.println("Control Panel :"+arg0.netAddress().address());
					//System.out.println("Control Panel :"+arg0.netAddress().port());
					AddToAvailableAddressList(arg0.netAddress().address(),arg0.netAddress().port());
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
	
	public void checkHosts(String subnet) throws UnknownHostException, IOException
	{
		InetAddress inetAddress = InetAddress. getLocalHost();
		String _myHostAddress = inetAddress.getHostAddress();

		int timeout=50;
		for (int i=1;i<20;i++)
		{
			String host=subnet + "." + i;
		
			if(_myHostAddress.equals(host))
				continue;
		
			if (InetAddress.getByName(host).isReachable(timeout))
			{
		
				System.out.println(host + " is reachable");
				AddToReachableAddressList(host,2346);
			}
		}
		
		
//		AddToReachableAddressList("172.20.10.2",2346);
//		AddToReachableAddressList("172.20.10.1",2346);
//		AddToReachableAddressList("172.20.10.13",2346);
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
		//System.out.println("### currently there are "+reachableAddressList.list().size()+" reachable locations.");
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
		System.out.println("### currently there are "+availableAddressList.list().size()+" available locations.");
	}

}