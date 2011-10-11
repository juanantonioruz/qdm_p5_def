package qdmp5.time;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class Time extends PApplet{

	List<Estado> estados=new ArrayList<Estado>();
	public void setup(){
		for(int i=0; i<6; i++)
			estados.add(new Estado(random(5)));
		frameRate(10);
	}
	int estadoActual;
	int contador;
	public void draw(){
		if(frameCount%30==0){
			contador++;
			estadoActual=contador%estados.size();
		}
		println(estadoActual+" frame: "+frameCount);
	}
}
class Estado{
	float valor;

	public Estado(float valor) {
		super();
		this.valor = valor;
	}
	
}
