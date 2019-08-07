import java.util.ArrayList;

import oscP5.OscMessage;
import processing.core.PApplet;

//import processing.core.*;

public class Launchpad
{
	ArrayList<Particle> MainMatrix;
	ColorMappingTable MyColor;
	final static int MatrixWidth = 8;
	final static int MatrixHeight = 8;                
	final static int particleWidth = 35;
	final static int particleHeight = 35;
	final static int particleDepth = 5;
	final static int SlotGapX = 5;
	final static int SlotGapY = 5;
	int padPosition_x;
	int padPosition_y;
	
	PApplet parent; // The parent PApplet that we will render ourselves onto
	OscMessage myMatrixMessage; 
	String PadName;
	
	Launchpad(PApplet _p,String _PadName,int _padPosition_x,int _padPosition_y)
	{
		parent = _p;
		PadName = _PadName;
		padPosition_x = _padPosition_x;
		padPosition_y = _padPosition_y;
		
		MyColor = new ColorMappingTable(); 
		myMatrixMessage = new OscMessage("/MatrixVelocity");
		
		MainMatrix = new ArrayList<Particle>();   
		for(int _note = 0;_note < 128; _note++)		//Launchpad has 128 notes 
			MainMatrix.add(new Particle(parent));   		// here!!
			//MainMatrix.add(new FadedOutParticle(parent));   // Polymorphism

		InitParticlePosition();
	}
	
	private void InitParticlePosition()
	{
		int[][] NoteNumber = {{  0, 28, 29, 30, 31, 32, 33, 34, 35,  0},
                			  {108, 64, 65, 66, 67, 96, 97, 98, 99,100},
                			  {109, 60, 61, 62, 63, 92, 93, 94, 95,101},
                			  {110, 56, 57, 58, 59, 88, 89, 90, 91,102},
                			  {111, 52, 53, 54, 55, 84, 85, 86, 87,103},
                			  {112, 48, 49, 50, 51, 80, 81, 82, 83,104},
                			  {113, 44, 45, 46, 47, 76, 77, 78, 79,105},
                			  {114, 40, 41, 42, 43, 72, 73, 74, 75,106},
                			  {115, 36, 37, 38, 39, 68, 69, 70, 71,107},
                			  {  0,116,117,118,119,120,121,122,123,  0}};
		
		int rectY = padPosition_y;
		for(int i = 0;i < 10; i++)
		{
			int rectX = 0;
			for(int j = 0;j < 10; j++)
			{	
				Particle P1 = MainMatrix.get(NoteNumber[i][j]);
				P1.SetPosition(rectX+padPosition_x,rectY,0,particleWidth,particleHeight,particleDepth);
				rectX = rectX + particleWidth + SlotGapX;
			}
			rectY = rectY + particleHeight + SlotGapY;
		}
	}

	public void updateVolicity(int Note,int Volicity)
	{
		int index = Note;
		Particle P1 = MainMatrix.get(index); 
		P1.SetVolicity(Volicity);
	}
	
	public void updateMode(int Note,int Mode)
	{
		int index = Note;
		Particle P1 = MainMatrix.get(index); 
		P1.SetShape(Mode);	//test
	}
	
	public void changeParticle(int Note,int Mode)
	{
		//System.out.println("changeParticle01");
		Particle P1 = MainMatrix.get(Note); 
		Particle _newParticle = null;
		if(Mode == 2)	 				// Faded out 
		{
			//System.out.println("changeParticle02");
			_newParticle = new FadedOutParticle(parent,P1);
		}
		else if(Mode == 0 || Mode == 1)	// default mode
		{
			//System.out.println("changeParticle03");
			_newParticle = new Particle(parent,P1);
		}
		//System.out.println("P1.MyVolicity="+P1.MyVolicity);
		_newParticle.SetVolicity(P1.MyVolicity);
		MainMatrix.set(Note,_newParticle);
		//System.out.println("_newParticle.MyVolicity="+_newParticle.MyVolicity);
		//System.out.println("changeParticle04");
	}
	
	public void changeShape()
	{
		int shape = (int) parent.random(3)+1; // 1~3
		for (int i = 1; i < MainMatrix.size(); i++)
		{
			Particle P1 = MainMatrix.get(i);
			P1.SetShape(shape);
		}
	}

	public void display()
	{
		//parent.background(0);
		for (int i = 1; i < MainMatrix.size(); i++)
		{
			Particle P1 = MainMatrix.get(i);
			P1.display();
		}
	}
	
	public void display3D()
	{
		int _pointerX = 0;	
		int _pointerY = 0;
		int _pointerZ = 0;

		//parent.directionalLight(255, 255, 255, (float)0.5, (float)0.5, 0);
		//parent.directionalLight(255, 255, 255, (float)-0.5, (float)-0.5, 0);
		//parent.ambientLight(0,100,255);
		parent.rotateX(PApplet.radians(45));
		parent.rotateY(PApplet.radians(0));
		parent.rotateZ(PApplet.radians(0));
		parent.stroke(200);
		for (int i = 1; i < MainMatrix.size(); i++)
		{
			Particle P1 = MainMatrix.get(i);
			parent.translate(P1.coordinate_x - _pointerX, P1.coordinate_y - _pointerY, P1.coordinate_z - _pointerZ);
			P1.display(4);
			_pointerX = P1.coordinate_x;
			_pointerY = P1.coordinate_y;
			_pointerZ = P1.coordinate_z;
		}	
	}

}