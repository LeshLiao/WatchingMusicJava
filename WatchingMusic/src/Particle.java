import processing.core.*;


public class Particle {
	protected int colorR;
	protected int colorG;
	protected int colorB;
	PVector pos;
	protected int SlotSize;
	protected int shape;
	PApplet parent; 
	int MyVolicity;
	
	protected ColorMappingTable col; 

	Particle(PApplet p)
	{
		parent = p;
		pos = new PVector(0,0);
		SlotSize = 0; 
		shape = 0;
		MyVolicity = 0;
		col = new ColorMappingTable(); 
	}
	
	Particle(PApplet p,int _x,int _y,int _size)
	{
		this(p);
		pos = new PVector(_x,_y);
		SlotSize = _size; 
	}

	public void SetColor(int Volicity)
	{
		colorR = col.colorTable[Volicity][0];
		colorG = col.colorTable[Volicity][1];	
		colorB = col.colorTable[Volicity][2];	
		MyVolicity = Volicity;
	}

	public void SetPosition(int _x,int _y,int _size)
	{
		pos.x = _x;
		pos.y = _y;
		SlotSize = _size;
	}
	
	public void SetShape(int _shape)
	{
		shape = _shape;
	}
	
	public void display()
	{
		parent.fill(colorR,colorG,colorB);
		draw(shape);
	}
	
	public String getRGB_str()
	{
		String str = Integer.toString(colorR)+","+Integer.toString(colorG)+","+Integer.toString(colorB)+",";
		return str;
	}
	
	public void draw(int _shape)
	{
		switch(_shape)
		{
			case 0:
			{
				parent.ellipse(pos.x+100, pos.y+50, SlotSize, SlotSize);
				break;
			}
			case 1:
			{
				parent.rect(pos.x+50, pos.y,SlotSize,SlotSize,20);	
				break;
			}	
			case 2:
			{
				float testX = pos.x;
				float testY = pos.y;
				int ratio = 2;
				parent.beginShape();
				parent.vertex((50*ratio)+testX, (15*ratio)+testY); 
				parent.bezierVertex((50*ratio)+testX, (-5*ratio)+testY, (100*ratio)+testX, (10*ratio)+testY, (50*ratio)+testX, (40*ratio)+testY); 
				parent.vertex((50*ratio)+testX, (15*ratio)+testY); 
				parent.bezierVertex((50*ratio)+testX, (-5*ratio)+testY, (0*ratio)+testX, (10*ratio)+testY, (50*ratio)+testX, (40*ratio)+testY); 
				parent.endShape();
				break;
			}
			default:
			{
				parent.rect(pos.x+50, pos.y,SlotSize,SlotSize,20);
				break;	
			}
		}
	}
	

}



