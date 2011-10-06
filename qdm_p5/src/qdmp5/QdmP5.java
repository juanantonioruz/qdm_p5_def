package qdmp5;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.opengl.*;
import geomerative.*;


public class QdmP5 extends PApplet {

	float toldist;
	RFont f;
	//RGroup grupo;
	boolean restart = false;
	Particle[] psys;
	int numPoints, numParticles;
	float maxvel;


	//------------------------ Runtime properties ----------------------------------
	// Save each frame
	boolean SAVEVIDEO = false;
	boolean SAVEFRAME = false;
	boolean APPLICATION = true;

	String DEFAULTAPPLETRENDERER = P3D;
	int DEFAULTAPPLETWIDTH = 680;
	int DEFAULTAPPLETHEIGHT = 480;

	String DEFAULTAPPLICRENDERER = OPENGL;
	int DEFAULTAPPLICWIDTH = 800;
	int DEFAULTAPPLICHEIGHT = 600;
	//------------------------------------------------------------------------------


	// Text to be written
	String STRNG = "Wiven";

	// Font to be used
	String FONT = "Gentium.ttf";

	// Velocity of change
	int VELOCITY = 3;

	// Velacity of deformation
	float TOLCHANGE = 0.025f;

	// Coefficient that handles the variation of amount of ink for the drawing 
	float INKERRCOEFF = 0.8f;

	// Coefficient that handles the amount of ink for the drawing
	float INKCOEFF = 0.3f;

	// Coefficient of precision: 0 for lowest precision
	float PRECCOEFF = 0.75f;

	String newString = "";

	public void setup(){

	  int w = 800, h = 600;
	  String r = JAVA2D;

	 

	  size(800,600,JAVA2D);
	  frameRate(25);
	  try{
	    smooth();
	  }
	  catch(Exception e){
	  }

	  background(255);
	  RG.init(this);
	  f = new RFont(FONT,372,RFont.CENTER);
	  mySvg = RG.loadShape("dibujo.svg");

	  initialize();
	}

	public void draw(){
	  pushMatrix();
	  scale(1.5f);
	  translate(10,10);
	  noStroke();

	  for(int i=0;i<numParticles;i++){
	    for(int j=0;j<VELOCITY;j++){
	      psys[i].update(polyshp);
	      psys[i].draw(g);
	    }
	  }
	  popMatrix();

	  if(SAVEVIDEO) saveFrame("filesvideo/"+STRNG+"-####.tga");
	  toldist += TOLCHANGE;
	}
	RShape mySvg;
	RShape polyshp;

	void initialize(){
	  toldist = ceil(width/200F) * (6F/(STRNG.length()+1));
	  maxvel = width/80F * INKERRCOEFF * (6F/(STRNG.length()+1));

	 // grupo = f.toGroup(STRNG);

	  RCommand.setSegmentStep(1-constrain(PRECCOEFF,0,0.99f));
	  RCommand.setSegmentator(RCommand.UNIFORMSTEP);

	 // grupo = grupo.toPolygonGroup();
	 // grupo.centerIn(g, 5, 1, 1);
	  polyshp = RG.polygonize(mySvg);

	  background(255);
	  RPoint[] ps = mySvg.getPoints();
	  numPoints = ps.length;
	  numParticles = numPoints;
	  psys = new Particle[numParticles];
	  for(int i=0;i<numParticles;i++){
	    psys[i] = new Particle(this, g,i,(int) (((float)(i)/(float)(numParticles)*10)));
	    psys[i].pos = new RPoint(ps[i]);
	    psys[i].vel.add(new RPoint(random(-10,10),random(-10,10)));
	  } 

	  toldist = 8;
	}

	public void keyReleased(){
	  if(keyCode==ENTER){
	    STRNG = newString; 
	    newString = "";
	    initialize();
	  }
	  else if(keyCode==BACKSPACE){
	    if(newString.length() !=0 ){
	      newString = newString.substring(0,newString.length()-1);
	    }
	  }
	  else if(keyCode!=SHIFT){
	    newString += key;
	  }
	}

	public class Particle{
	  // Velocity
	  RPoint vel;

	  // Position
	  RPoint pos;
	  RPoint lastpos;

	  // Caracteristics
	  int col;
	  int hueval;
	  float sz;

	  // ID
	  int id;

	private final PApplet p5;

	  // Constructor
	  public Particle(PApplet p5, PGraphics gfx, int ident, int huevalue){
	    this.p5 = p5;
		pos = new RPoint(random(-gfx.width/2,gfx.width/2), random(-gfx.height/2,gfx.height/2));
	    lastpos = new RPoint(pos);
	    vel = new RPoint(0, 0);

	    colorMode(HSB);
	    sz = random(2,3);

	    id = ident;
	    hueval = huevalue;
	  }

	  // Updater of position, velocity and colour depending on a RGroup
	  public void update(RShape grp){
	    lastpos = new RPoint(pos);
	    pos.add(vel);
	    RPoint[] ps = grp.getPoints();
	    if(ps != null){
	      float distancia = dist(pos.x,pos.y,ps[id].x,ps[id].y);
	      if(distancia <= toldist){
	        id = (id + 1) % ps.length;

	      }

	      RPoint distPoint = new RPoint(ps[id]);
	      distPoint.sub(pos);

	      distPoint.scale(p5.random(0.028f,0.029f));
	      vel.scale(p5.random(0.5f,1.3f));
	      vel.add(distPoint);
	      float sat = p5.constrain((width-distancia)*0.25f,0.001f,255);
	      float velnorm = constrain(vel.norm(),0,maxvel);

	      sat = abs(maxvel-velnorm)/maxvel*INKCOEFF*255;
	      sat = constrain(sat,0,255);
	      col = color(hueval,150,255,sat*(toldist/80));
	    }
	  }

	  public void setPos(RPoint newpos){
	    lastpos = new RPoint(pos);
	    pos = newpos;
	  }

	  // Drawing the particle
	  public void draw(PGraphics gfx){
	    stroke(col);
	    line(lastpos.x,lastpos.y,pos.x, pos.y);
	  }
	}
}
