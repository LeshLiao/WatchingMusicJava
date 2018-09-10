import processing.core.PApplet;

import oscP5.*;
import netP5.*;



public class MatrixClassProject extends PApplet{
	OscP5 oscP5;
	NetAddress myRemoteLocation;
	NetAddress AbletonliveLocation;
	Launchpad NewLaunchpad;
	int tempVelocity = 0;
	int tempNote = 0;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("MatrixClassProject");
	}
	
	//	An array of stripes
	Stripe[] stripes = new Stripe[50];

	public void settings() {
		size(200,200);
		fullScreen(1);
	}

	public void setup() {
		background(50);
		surface.setResizable(true);
		
		frameRate(50);
		oscP5 = new OscP5(this,2346);
		myRemoteLocation = new NetAddress("10.1.1.6",2346);
		AbletonliveLocation = new NetAddress("127.0.0.1",8000);
		noStroke(); // no border line
		NewLaunchpad = new Launchpad(this);
	}

	public void draw() {
		background(0);
		smooth();
		noStroke();
		NewLaunchpad.display();
	}
	
	void oscEvent(OscMessage theOscMessage) {
		  
		String temp_Addr = theOscMessage.addrPattern().substring(0,17);

	  if(temp_Addr.equals("/PitchAndVelocity"))
	  {
	    tempNote = theOscMessage.get(0).intValue();
	    tempVelocity = theOscMessage.get(1).intValue(); 
	    
	    if(tempNote >=36 && tempNote <= 99)
	      NewLaunchpad.update(tempNote,tempVelocity);
	    //else
	    //  println("Note:"+tempNote+",Volicity:"+tempVelocity);
	  }


	  /*
		if(temp_Addr.equals("/Velo"))
		{
			tempVelocity = theOscMessage.get(0).intValue();     
		}
		else if(temp_Addr.equals("/Note"))
		{
			tempNote = theOscMessage.get(0).intValue();  
			
			if(tempNote >=36 && tempNote <= 99)
				NewLaunchpad.update(tempNote,tempVelocity);
			else
				println("Note:"+tempNote+",Volicity:"+tempVelocity);


	    // TOP LIGHT 28~35
		} 
	  */

	}
	public void mousePressed() 
	{
		OscMessage myMessage = new OscMessage("/Velocity1");
		int ValueRan = (int) (random(100));
		myMessage.add(ValueRan); /* add an int to the osc message */
		oscP5.send(myMessage, myRemoteLocation); 

		OscMessage myMessage2 = new OscMessage("/Note1");
		myMessage2.add(36); /* add an int to the osc message */
		oscP5.send(myMessage2, myRemoteLocation); 
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
