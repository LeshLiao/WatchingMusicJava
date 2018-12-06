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
	
	ConfigTable MyTestconfigTable;
	Long CompareCheckSum = 0L;
	String LastOneStr = "";
	Boolean IsSendData;
	
	Launchpad(PApplet p)
	{
		parent = p;
		InitPosition();
		MyColor = new ColorMappingTable(); 
		myMatrixMessage = new OscMessage("/MatrixVelocity");
		
		//Test
		MyTestconfigTable = new ConfigTable();
		MyTestconfigTable.initialize("JSON_File");
	}

	public void InitPosition()
	{
		MainMatrix = new ArrayList<Particle>();  
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
				MainMatrix.add(new Particle(parent,rectX,rectY,SlotSize));
			} 
			Init_rectX = (SlotSize*MatrixWidth/2)+(SlotGapX*(MatrixWidth/2));
		}	
	}

	public void update(int Note,int Volicity)
	{
		int index = Note - 36;
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
		for (int i = 0; i < MainMatrix.size(); i ++)
		{
			Particle P1 = MainMatrix.get(i);
			P1.display();
		}
	}
	
	public void PackMyOSCMessage()
	{
		myMatrixMessage.clearArguments();
		String tempStr = "";
		
		/*
		for (int i = 0; i < MainMatrix.size(); i ++)
		{
			Particle P1 = MainMatrix.get(i);
			myMatrixMessage.add(P1.MyVolicity);
		}
		*/
		
		/*
		for (int i = 0; i < MainMatrix.size(); i ++)
		{
			Particle P1 = MainMatrix.get(i);
			tempStr = tempStr + Integer.toString(P1.MyVolicity)+",";
		}
		*/
		
		for (int i = 0; i < MyTestconfigTable.Matrix_SettingList.size(); i ++)
		{
			SettingRule setting = MyTestconfigTable.Matrix_SettingList.get(i);
			
			Particle P1 = MainMatrix.get(setting.Note-36);
			tempStr = tempStr + Integer.toString(P1.MyVolicity)+",";
			//CompareCheckSum = CompareCheckSum + (setting.Note * i * P1.MyVolicity);
		}
		
		if(LastOneStr.equals(tempStr))
		{
			IsSendData = false;
		}
		else
		{
			IsSendData = true;
		}
		
				
		LastOneStr = tempStr;
		myMatrixMessage.add(tempStr);
	}
}