package UserInterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.CompoundBorder;

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

public class ControlPanel extends JFrame {

	private JPanel contentPane;
	OscP5 oscP5;
	InetAddress inetAddress;
	String _myHostAddress;
	int _myHostport;
	ConfigTable NewConfig;
	RemoteControl newRemoteControl;
	StatusTable Stable;
	private JTextField textField_Subnet;
	private JTextField textField_port;
	private JTextField textField_ipRange;
	private JTextField textField_timeout;
	/**
	 * Create the frame.
	 */
	public ControlPanel(ConfigTable _newConfig) {
		NewConfig = _newConfig;
		try {
			init();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		oscP5 = new OscP5(this, _myHostport);
		
		setBackground(Color.DARK_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 833, 645);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(27, 64, 759, 133);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("Scan network");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String subnet = textField_Subnet.getText();
				int port = Integer.parseInt(textField_port.getText());
				int ipRange = Integer.parseInt(textField_ipRange.getText());
				int timeout = Integer.parseInt(textField_timeout.getText());
				
				Stable.ClearTable();
				System.out.println("Button Event: Scan subnet:"+subnet);
				
				;
				try {
					checkHosts(subnet,timeout,port,ipRange);
				} catch (IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(57, 53, 125, 27);
		panel.add(btnNewButton);
		
		JButton btnInitalAllDevice = new JButton("Inital All Device");
		btnInitalAllDevice.setBounds(14, 13, 153, 27);
		panel.add(btnInitalAllDevice);
		
		JButton btnStopAllService = new JButton("Stop all service");
		btnStopAllService.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i=0; i<Stable.reachableAddressList.size(); i++) 
				{
		            System.out.println("send commend to:"+Stable.reachableAddressList.get(i).address());
				
		            try {
						newRemoteControl = new RemoteControl("pi","raspberry",Stable.reachableAddressList.get(i).address());
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
		btnStopAllService.setBounds(57, 93, 125, 27);
		panel.add(btnStopAllService);
		
		JButton btnRunAllService = new JButton("Run all service");
		btnRunAllService.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i=0; i<Stable.reachableAddressList.size(); i++) 
				{
		            System.out.println("send commend to:"+Stable.reachableAddressList.get(i).address());
				
		            try {
						newRemoteControl = new RemoteControl("pi","raspberry",Stable.reachableAddressList.get(i).address());
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
		btnRunAllService.setBounds(195, 93, 125, 27);
		panel.add(btnRunAllService);
		
		JButton btnCheckOsc = new JButton("Check OSC Json Git");
		btnCheckOsc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Stable.availableAddressList.list().clear();
				OscMessage myMessage = new OscMessage("/Instruction");
				myMessage.add("CHECK_OSC");
				myMessage.add(_myHostAddress); 
				myMessage.add(Integer.toString(_myHostport)); 
				oscP5.send(myMessage, Stable.reachableAddressList);
				System.out.println("Button Event: CHECK_OSC");
			}
		});
		btnCheckOsc.setBounds(334, 93, 208, 27);
		panel.add(btnCheckOsc);
		
		textField_Subnet = new JTextField();
		textField_Subnet.setText("172.20.10");
		textField_Subnet.setBounds(196, 54, 116, 25);
		panel.add(textField_Subnet);
		textField_Subnet.setColumns(10);
		
		textField_port = new JTextField();
		textField_port.setText("2346");
		textField_port.setBounds(332, 54, 60, 25);
		panel.add(textField_port);
		textField_port.setColumns(10);
		
		textField_ipRange = new JTextField();
		textField_ipRange.setText("15");
		textField_ipRange.setBounds(406, 54, 60, 25);
		panel.add(textField_ipRange);
		textField_ipRange.setColumns(10);
		
		textField_timeout = new JTextField();
		textField_timeout.setText("50");
		textField_timeout.setBounds(482, 54, 60, 25);
		panel.add(textField_timeout);
		textField_timeout.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.setBounds(27, 419, 759, 166);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 0, 731, 140);
		panel_2.add(scrollPane);
		Stable = new StatusTable();
		scrollPane.setViewportView(Stable);
		Stable.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(27, 218, 759, 94);
		contentPane.add(panel_1);
		oscP5.addListener(new OscEventListener()
		{

			@Override
			public void oscEvent(OscMessage arg0) 
			{
				// TODO Auto-generated method stub
				System.out.println("Control Panel oscEvent:"+arg0.addrPattern());
				if(arg0.addrPattern().equals("/Response"))
				{
					Stable.AddToAvailableAddressList(arg0.get(0).stringValue(),arg0.get(1).intValue());
					Stable.UpdateGitHash(arg0.get(0).stringValue(),arg0.get(2).stringValue().substring(0, 7));
					System.out.print("IP:"+arg0.get(0).stringValue());
					System.out.println(", GIT SHA:"+arg0.get(2).stringValue().substring(0, 7));
					//System.out.println("2):"+Integer.toString(arg0.get(1).intValue()));
					
				}
				System.out.println("### currently there are "+Stable.availableAddressList.list().size()+" available OSC device.");
			}

			@Override
			public void oscStatus(OscStatus arg0) 
			{
				// TODO Auto-generated method stub
				System.out.println("Control Panel oscStatus:"+arg0.toString());
			}
		});
		
		JButton button_2 = new JButton("Sync Json Files");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SyncFile newSyncFile;
				System.out.println("Button Event: SYNC_JSON");

				try {
					System.out.println("Reload:StationSetup.json");
					NewConfig.LoadJsonFile("StationSetup.json");
					NewConfig.initNetSettings();
					System.out.println("Json TimeStamp:"+NewConfig.getTimeStamp());
					
					for (int i=0; i<Stable.availableAddressList.size(); i++) 
					{
			            System.out.println("put file to:"+Stable.availableAddressList.get(i).address());
					
						newSyncFile = new SyncFile("pi","raspberry",Stable.availableAddressList.get(i).address());
						newSyncFile.putFile("StationSetup.json", "rpi-ws281x-python-and-osc/examples/config/StationSetup.json");
						newSyncFile.Close();
						
						OscMessage myMessage = new OscMessage("/Instruction");
						myMessage.add("RELOAD_JSON");
						myMessage.add("0"); 
						myMessage.add("0"); 
						
						oscP5.send(myMessage, Stable.availableAddressList.get(i));
					}
					
				} catch (JSchException | SftpException | FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("Copy completed!");
				
			}
		});
		button_2.setBounds(14, 53, 153, 27);
		panel_1.add(button_2);
		
		JButton button_3 = new JButton("Pull latest version");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i=0; i<Stable.reachableAddressList.size(); i++) 
				{
		            System.out.println("send commend to:"+Stable.reachableAddressList.get(i).address());
				
		            try {
						newRemoteControl = new RemoteControl("pi","raspberry",Stable.reachableAddressList.get(i).address());
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
		button_3.setBounds(14, 13, 153, 27);
		panel_1.add(button_3);
		
		JButton button = new JButton("Breathing light");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OscMessage myMessage = new OscMessage("/Instruction");
				myMessage.add("BREATHING_LIGHT");
				myMessage.add("256"); 
				myMessage.add("64"); 
				
				oscP5.send(myMessage, Stable.availableAddressList);
				System.out.println("Button Event: BREATHING_LIGHT");
			}
		});
		button.setBounds(195, 13, 125, 27);
		panel_1.add(button);
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_3.setBounds(27, 338, 759, 54);
		contentPane.add(panel_3);
		
		JButton btnTest_1 = new JButton("test01");
		btnTest_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Stable.AddDevice("192.168.0.0","0");
				Stable.AddToReachableAddressList("192.168.0.1",100);
				Stable.AddToReachableAddressList("192.168.0.2",100); 
				Stable.AddToReachableAddressList("192.168.0.3",100); 
			}
		});
		btnTest_1.setBounds(14, 13, 153, 27);
		panel_3.add(btnTest_1);
		
		JButton btnTest_2 = new JButton("test02");
		btnTest_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Stable.ClearTable();
			}
		});
		btnTest_2.setBounds(181, 13, 153, 27);
		panel_3.add(btnTest_2);
		
		JButton btnTest_3 = new JButton("test03");
		btnTest_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Stable.AddToAvailableAddressList("192.168.0.3",100);
			}
		});
		btnTest_3.setBounds(348, 13, 153, 27);
		panel_3.add(btnTest_3);
		
		JButton btnTest_4 = new JButton("test04");
		btnTest_4.setBounds(515, 13, 153, 27);
		panel_3.add(btnTest_4);
	}
	private void init() throws UnknownHostException
	{
		inetAddress = InetAddress.getLocalHost();
		_myHostAddress = inetAddress.getHostAddress();	
		_myHostport = 2349;
	}
	
	public void checkHosts(String subnet,int _timeout,int _OscPort,int _IpRange) throws UnknownHostException, IOException
	{
		for (int i=1;i<_IpRange;i++)
		{
			String host=subnet + "." + i;
		
			if(_myHostAddress.equals(host))
				continue;
		
			if (InetAddress.getByName(host).isReachable(_timeout))
			{
	            try 
	            {
					newRemoteControl = new RemoteControl("pi","raspberry",host);
		            //newRemoteControl.Close();   // close so fast, so we comment it.
					Stable.AddToReachableAddressList(host,_OscPort);
	            } catch (JSchException e1) {
	            	System.out.println(host+":"+e1.getMessage());
				}
			}
		}
		System.out.println("### currently there are "+Stable.reachableAddressList.list().size()+" reachable locations.");
	}
}
