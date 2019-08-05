import processing.core.PApplet;
import oscP5.*;

import java.io.FileNotFoundException;
import java.time.Instant;
import java.util.ArrayList;

import Config.MyStation;
import Data.ConfigTable;
import UserInterface.*;
import netP5.*;

public class MatrixClassProject extends PApplet{
	OscP5 oscP5;
	NetAddress myRemoteLocation;
	NetAddress AbletonliveLocation;
	NetAddress TestLocation;
	OscMessage myMatrixMessage;
	ConfigTable NewConfig;
	ArrayList<Launchpad> MidiDevice;
	
	int _PadNumber = 0;
	int _Velocity = 0;
	int _Note = 0;
	int test_count = 0; //testing
	
	public static void main(String[] args) {
		PApplet.main("MatrixClassProject");
	}

	public void settings() 
	{
		size(1600,800);
		//fullScreen(1);
		//fullScreen(2);
	}

	public void setup() 
	{
		background(50);
		surface.setResizable(true);
		noStroke(); // no border line
		//frameRate(60);	// 60Hz
		frameRate(40);		// 40Hz
		
		oscP5 = new OscP5(this,2346);
		myRemoteLocation = new NetAddress("10.1.1.6",2346);
		AbletonliveLocation = new NetAddress("127.0.0.1",8000);
		TestLocation = new NetAddress("172.20.10.13",2346);
		NewConfig = new ConfigTable();

		try {
			NewConfig.LoadJsonFile("StationSetup.json");
			NewConfig.initNetSettings();
			System.out.println("Json TimeStamp:"+NewConfig.getTimeStamp());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MidiDevice = new ArrayList<Launchpad>(); 
		MidiDevice.add(new Launchpad(this,"Launchpad Pro",0));
		MidiDevice.add(new Launchpad(this,"Launchpad Mini",800));
		
		myMatrixMessage = new OscMessage("/MatrixVelocity");
		
		ControlPanel newPanel2 = new ControlPanel(NewConfig); 
		newPanel2.setVisible(true);
		
		//surface.setVisible(false); //Hide Main frame
		surface.setVisible(true); //Show Main frame
	}

	public void draw()     
	{
//		test_count++;  //testing
//		if(test_count >= 60)
//		{
//			System.out.println("sta:"+Instant.now());
//		}
		
		background(0);
		for (int i = 0; i < MidiDevice.size(); i++) 
		{
			MidiDevice.get(i).display();
		}

		for (int i = 0; i < NewConfig._myJsonConfig.getMyStations().size(); i++)
		{
			MyStation myStation = NewConfig._myJsonConfig.getMyStations().get(i);
			String tempStr = "";
			for (int j = 0; j < myStation.getRules().size(); j++)
			{
				int Note = myStation.getRules().get(j).getInput();
				int PadNumber = myStation.getRules().get(j).getPadNo();
				Particle P1 = MidiDevice.get(PadNumber).MainMatrix.get(Note);
				tempStr = tempStr + P1.getRGB_str();
				
				//Particle P1 = MidiDevice.get(PadNumber).MainMatrix.get(Note);//before
				//tempStr = tempStr + Integer.toString(P1.MyVolicity)+",";     //before
			}
			if(tempStr.equals(myStation.getLastTempString()) == false)
			{
				myMatrixMessage.clearArguments();
				myMatrixMessage.add(tempStr);
				oscP5.send(myMatrixMessage, myStation.getNetSettings());
			}
			myStation.setLastTempString(tempStr);
		}
		
//		if(test_count >= 60) //testing
//		{
//			System.out.println("End:"+Instant.now());
//			test_count = 0;
//		}
	}
	
	void oscEvent(OscMessage theOscMessage) 
	{
		String temp_Addr = theOscMessage.addrPattern().substring(0,10);
		if(temp_Addr.equals("/PitchAndV"))	//PitchAndVelocity
		{
			_PadNumber = theOscMessage.get(0).intValue();
			_Note = theOscMessage.get(1).intValue();
			_Velocity = theOscMessage.get(2).intValue(); 	
			if(_PadNumber < MidiDevice.size())
				MidiDevice.get(_PadNumber).updateVolicity(_Note,_Velocity);	
		}
		else if(temp_Addr.equals("/PitchAndM"))	//PitchAndMode
		{
			_PadNumber = theOscMessage.get(0).intValue();
			_Note = theOscMessage.get(1).intValue();
			_Velocity = theOscMessage.get(2).intValue(); 	
			if(_PadNumber < MidiDevice.size())
				MidiDevice.get(_PadNumber).updateMode(_Note,_Velocity);
		}
	}
	
	public void mousePressed() 
	{
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
		for (int i = 0; i < MidiDevice.size(); i++) 
		{
			MidiDevice.get(i).changeShape();
		}
	}
}
