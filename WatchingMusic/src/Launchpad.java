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
	final static int SlotSize = 100;
	final static int SlotGapX = 145;
	final static int SlotGapY = 40;
	PApplet parent; // The parent PApplet that we will render ourselves onto
	OscMessage myMatrixMessage; 
	
	//ConfigTable MyTestconfigTable;
	String OscAddress = "";
	
	Launchpad(PApplet _p,String _OscAddress)
	{
		parent = _p;
		OscAddress = _OscAddress;
				
		MyColor = new ColorMappingTable(); 
		myMatrixMessage = new OscMessage("/MatrixVelocity");
		
		MainMatrix = new ArrayList<Particle>();   
		for(int _note = 0;_note < 128; _note++)		//Launchpad has 128 notes 
			MainMatrix.add(new Particle(parent));
		
		InitPosition_center();
		
		//Test
		//MyTestconfigTable = new ConfigTable();
		//MyTestconfigTable.initialize("JSON_File");
	}

	public void InitPosition_center()				// Initial note 36~99
	{
		int Init_rectX = 0;
		for(int BeginValue = 0;BeginValue < 64; BeginValue+=32)
		{
			int rectX = Init_rectX;
			int rectY = MatrixHeight*SlotSize+(SlotGapY*MatrixHeight);
			for(int i = BeginValue;i < 32+BeginValue; i++)
			{
				if(i % 4 == 0)
				{
					rectX = Init_rectX;
					rectY = rectY - SlotSize - SlotGapY; 
				}
				else
				{
					rectX = rectX + SlotSize + SlotGapX;
				}
				Particle P1 = MainMatrix.get(i+36); 
				P1.SetPosition(rectX,rectY,SlotSize);
			} 
			Init_rectX = (SlotSize*MatrixWidth/2)+(SlotGapX*(MatrixWidth/2));
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
		parent.background(0);
		for (int i = 0; i < MainMatrix.size(); i ++)
		{
			Particle P1 = MainMatrix.get(i);
			P1.display();
		}
	}

}