import processing.core.PApplet;


public class Myclass extends PApplet{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("Myclass");
	}

	public void settings() 
	{
		size(1200,1000,P3D);
	}

	public void setup() 
	{
		//rectMode(CENTER);
		noStroke();
	}
	float test = 0;
	float x,y,z;
	int colorValue = 220;
	int _angleX = 30;
	int _angleY = 0;

	
	int _boxX = 40;
	int _boxY = 40;
	int _boxH = 5;
	int _nextboxX = 50;
	int _nextboxY = 50;
	public void draw()     
	{
		
		background(0);
		fill(200, 200, 200);
		textSize(20);
		
		//if(colorValue > 255) colorValue = 0;
		//colorValue++;
		String _colorText = "color:"+colorValue;
		text(_colorText, 20, 50); 
		
		if(_angleX > 360) _angleX = 0;
		//_angleX++;
		String _angleXText = "_angleX:"+_angleX+"_angleY:"+_angleY;
		text(_angleXText, 20, 80); 
		
		//delay(10);
		//pushMatrix();
		directionalLight(255, 255, 255, (float)0.5, (float)0.5, 0);
		rotateX(radians(_angleX));
		//rotateX(radians(45));			// radians(45) is good.
		//rotateX(radians(0));
		//rotateY(radians(_angleX));
		rotateY(radians(_angleY));
		rotateZ(radians(0));
		
		ambientLight(0,100,255);
		
		translate(420, 400, 0); // Begin draw position
		
		
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				box(_boxX,_boxY,_boxH);
				translate(_nextboxX, 0, 0);
			}
			translate(-(_nextboxX*8), 0, 0);
			translate(0, _nextboxY, 0);
		}

		if (keyPressed == true) 
		{
			changeAngle();
		}
	}
	
	public void changeAngle()
	{
	  if (key == CODED) 
	  {
		    if (keyCode == UP) 
		    {
		    	_angleX++;
		    } else if (keyCode == DOWN) {
		    	_angleX--;
		    } 
		    else if (keyCode == LEFT) {
		    	_angleY++;
		    } 
		    else if (keyCode == RIGHT) {
		    	_angleY--;
		    } 
	    
	  } 
	  if (key == 'd')
	  {
	    	_angleX = 0;
	    	_angleY = 0;
	  } 
		
	}
	
	boolean fullscreen = false;
	public void keyPressed()
	{
		if (key=='f') 
		{
			if (fullscreen == false) {
			fullscreen = true;
			surface.setSize(displayWidth+10, displayHeight+20);
			surface.setLocation(-10, -40);
			surface.setAlwaysOnTop(true);
			} else {
			fullscreen = false;
			surface.setSize(1200, 1000);
			surface.setLocation(10, 10);
			surface.setAlwaysOnTop(false);
			}
		}
	}

}
