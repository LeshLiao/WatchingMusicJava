import java.util.ArrayList;

import oscP5.OscMessage;
import processing.core.PApplet;

//import processing.core.*;

public class Launchpad
{
	ArrayList<Particle> MainMatrix;
	ColorMappingTable MyColor;
	final static int MatrixHeight = 8;                
	final static int MatrixWidth = 8;
	final static int SlotSize = 50;
	final static int SlotGapX = 20;
	final static int SlotGapY = 20;
	int Edge_X;						
	
	PApplet parent; // The parent PApplet that we will render ourselves onto
	OscMessage myMatrixMessage; 
	String PadName;
	//ConfigTable MyTestconfigTable;

	
	Launchpad(PApplet _p,String _PadName,int _Edge_X)
	{
		parent = _p;
		PadName = _PadName;
		Edge_X = _Edge_X;
		
		MyColor = new ColorMappingTable(); 
		myMatrixMessage = new OscMessage("/MatrixVelocity");
		
		MainMatrix = new ArrayList<Particle>();   
		for(int _note = 0;_note < 128; _note++)		//Launchpad has 128 notes 
			//MainMatrix.add(new Particle(parent));   		// here!!
			MainMatrix.add(new FadedOutParticle(parent));   // Polymorphism

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
		
		int rectY = 0;
		for(int i = 0;i < 10; i++)
		{
			int rectX = 0;
			for(int j = 0;j < 10; j++)
			{	
				Particle P1 = MainMatrix.get(NoteNumber[i][j]);
				P1.SetPosition(rectX+Edge_X,rectY,SlotSize);
				rectX = rectX + SlotSize + SlotGapX;
			}
			rectY = rectY + SlotSize + SlotGapY;
		}
	}

	public void update(int Note,int Volicity)
	{
		int index = Note;
		Particle P1 = MainMatrix.get(index); 
		P1.SetColor(Volicity);
	}
	
	public void changeShape()
	{
		int shape = (int) parent.random(3);
		for (int i = 0; i < MainMatrix.size(); i ++)
		{
			Particle P1 = MainMatrix.get(i);
			P1.SetShape(shape);
		}
	}

	public void display()
	{
		//parent.background(0);
		for (int i = 0; i < MainMatrix.size(); i ++)
		{
			Particle P1 = MainMatrix.get(i);
			P1.display();
		}
	}

}