import processing.core.PApplet;
import oscP5.*;

import java.io.FileNotFoundException;

import Config.MyStation;
import Data.ConfigTable;
import UserInterface.ControlPanel;
import netP5.*;



public class MatrixClassProject extends PApplet{
	OscP5 oscP5;
	NetAddress myRemoteLocation;
	NetAddress AbletonliveLocation;
	NetAddress TestLocation;
	
	Launchpad NewLaunchpad;
	Launchpad NewLaunchpad_2;
	
	OscMessage myMatrixMessage;
	ConfigTable NewConfig;
	
	
	int tempVelocity = 0;
	int tempNote = 0;
	Stripe[] stripes = new Stripe[50];
	
	public static void main(String[] args) {
		PApplet.main("MatrixClassProject");
	}

	public void settings() 
	{
		size(300,300);
		
		//fullScreen(1);
		//fullScreen(2);
		
		
		

	}

	public void setup() 
	{
		background(50);
		surface.setResizable(true);
		noStroke(); // no border line
		//smooth();
		frameRate(60);	// 60Hz
		
		oscP5 = new OscP5(this,2346);
		myRemoteLocation = new NetAddress("10.1.1.6",2346);
		AbletonliveLocation = new NetAddress("127.0.0.1",8000);
		TestLocation = new NetAddress("172.20.10.13",2346);
		
		NewLaunchpad = new Launchpad(this,"/PitchAndVelocity");
		//NewLaunchpad_2 = new Launchpad(this,"/PitchAndVelocity_Pad2");
		
		NewConfig = new ConfigTable();
		//NewConfig.initialize("JSON_File");
		
		try {
			NewConfig.LoadJsonFile("StationSetup.json");
			NewConfig.initNetSettings();
			System.out.println("Json TimeStamp:"+NewConfig.getTimeStamp());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		myMatrixMessage = new OscMessage("/MatrixVelocity");
		
		ControlPanel newPanel = new ControlPanel(NewConfig); 

	}

	public void draw()     
	{
		NewLaunchpad.display();

//		for (int i = 0; i < NewConfig.StationList.size(); i ++)
//		{
//			Station TempStation = NewConfig.StationList.get(i);
//			TempStation.IsSendData = false;		
//	
//			myMatrixMessage.clearArguments();
//			String tempStr = "";
//
//			for (int j = 0; j < TempStation.SettingList.size(); j ++)
//			{
//				SettingRule setting = TempStation.SettingList.get(j);
//				
//				Particle P1 = NewLaunchpad.MainMatrix.get(setting.Note);
//				tempStr = tempStr + Integer.toString(P1.MyVolicity)+",";
//			}
//			
//			if(TempStation.LastOneStr.equals(tempStr))
//			{
//				TempStation.IsSendData = false;
//			}
//			else
//			{
//				TempStation.IsSendData = true;
//			}
//			
//			TempStation.LastOneStr = tempStr;
//			myMatrixMessage.add(tempStr);
//
//			if(TempStation.IsSendData == true) 
//			{
//				oscP5.send(myMatrixMessage, TempStation.NetSettings);
//			}
//		}
		
		for (int i = 0; i < NewConfig._myJsonConfig.getMyStations().size(); i++)
		{
			MyStation myStation = NewConfig._myJsonConfig.getMyStations().get(i);
			String tempStr = "";

			for (int j = 0; j < myStation.getRules().size(); j++)
			{
				int Note = myStation.getRules().get(j).getInput();
				Particle P1 = NewLaunchpad.MainMatrix.get(Note);
				tempStr = tempStr + Integer.toString(P1.MyVolicity)+",";
			}
			
			if(tempStr.equals(myStation.getLastTempString()) == false)
			{
				myMatrixMessage.clearArguments();
				myMatrixMessage.add(tempStr);
				oscP5.send(myMatrixMessage, myStation.getNetSettings());
				//System.out.println(tempStr);
			}
			
			myStation.setLastTempString(tempStr);
		}

	}
	
	void oscEvent(OscMessage theOscMessage) 
	{
		
		String temp_Addr = theOscMessage.addrPattern().substring(0,17);
		if(temp_Addr.equals("/PitchAndVelocity"))
		{
			tempNote = theOscMessage.get(0).intValue();
			tempVelocity = theOscMessage.get(1).intValue(); 
			NewLaunchpad.update(tempNote,tempVelocity);	
		}
		
//		System.out.println(theOscMessage.addrPattern());
		
		
		/*
		String temp_Addr = theOscMessage.addrPattern().substring(0,17);
		if(temp_Addr.equals("/PitchAndVelocity"))
		{
			tempNote = theOscMessage.get(0).intValue();
			tempVelocity = theOscMessage.get(1).intValue(); 
			    
			//if(tempNote >=36 && tempNote <= 99)
			//	NewLaunchpad.update(tempNote,tempVelocity);
			
			
			if(tempNote >=36 && tempNote <= 99)					// Matrix 8X8
				NewLaunchpad.update(tempNote,tempVelocity);
			else												// Frame 8X4
			{
				OscMessage myMessage = new OscMessage("/TagAndVelocity");
				for (int i = 0; i < NewLaunchpad.MyTestconfigTable.Top_SettingList.size(); i ++)
				{
					SettingRule setting = NewLaunchpad.MyTestconfigTable.Top_SettingList.get(i);
					if(tempNote == setting.Note)
					{
						myMessage.clearArguments();
						myMessage.add(setting.Tag);
						myMessage.add(tempVelocity);
						oscP5.send(myMessage, setting.NetSettings); 
					}
				}
			}
			
			
			//else
			//  println("Note:"+tempNote+",Volicity:"+tempVelocity);
		}
		*/
	}
	
	public void mousePressed() 
	{
//		OscMessage myMessage = new OscMessage("/Velocity1");
//		int ValueRan = (int) (random(100));
//		myMessage.add(ValueRan); /* add an int to the osc message */
//		oscP5.send(myMessage, myRemoteLocation); 
//
//		OscMessage myMessage2 = new OscMessage("/Note1");
//		myMessage2.add(36); /* add an int to the osc message */
//		oscP5.send(myMessage2, myRemoteLocation); 
		
		
		OscMessage myMessage = new OscMessage("/Instruction");
		//int ValueRan = (int) (random(100));
		//float TestValue = 0.005f;
		myMessage.add(32); 
		//myMessage.add(0); 
		//myMessage.add(0); 
		//myMessage.add(255); 
		//myMessage.add(0); 
		//myMessage.add(0); 
		//myMessage.add(0); 
		//myMessage.add(0); 
		//myMessage.add(0); 
		//myMessage.add(0); 
		
		oscP5.send(myMessage, TestLocation); 
		
		
		/*
		// Bundle Test
		OscBundle TestBundle = new OscBundle();
		OscMessage myMessage = new OscMessage("/TagAndVelocity");
		int ValueRan = (int) (random(100));
		myMessage.add(111); 
		myMessage.add(222);
		TestBundle.add(myMessage);
		oscP5.send(TestBundle, TestLocation); 
		*/
	}

	public void keyPressed() 
	{
		NewLaunchpad.changeShape();
		
		//String[] lines = loadStrings("list.txt");
		//println("there are " + lines.length + " lines");
		//for (int i = 0 ; i < lines.length; i++) {
		//  println(lines[i]);
		//}
		

	}
	

	 

}
