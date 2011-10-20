package qdmp5;

import processing.core.PApplet;
import toxi.color.ColorList;
import toxi.color.TColor;
import toxi.color.theory.ColorTheoryStrategy;
import toxi.color.theory.CompoundTheoryStrategy;

public class ServicioToxiColor extends ClaseP5{
	public ServicioToxiColor(PApplet p5) {
		super(p5);
		// TODO Auto-generated constructor stub
	}

	public  ColorList iniciaColoresEquipos() {
		ColorList listaColoresEquipo;

		TColor col = TColor.newRandom();
		ColorTheoryStrategy s = new CompoundTheoryStrategy();
		ColorList list = ColorList.createUsingStrategy(s, col);
		listaColoresEquipo = new ColorList(list);
		for (int i = 0; i < list.size(); i++) {
			TColor c = (TColor) list.get(i);
			listaColoresEquipo.add(c.getInverted());
		}
		return listaColoresEquipo;
	}
	
	public  ColorList iniciaColoresEquiposBis() {
		ColorList listaColoresEquipo=new ColorList();
		listaColoresEquipo.add(TColor.newHex("8b0000"));
		listaColoresEquipo.add(TColor.newHex("ff0000"));
		listaColoresEquipo.add(TColor.newHex("adff2f"));
		listaColoresEquipo.add(TColor.newHex("ffff00"));
		listaColoresEquipo.add(TColor.newHex("8b008b"));
		listaColoresEquipo.add(TColor.newHex("00bfff"));
		listaColoresEquipo.add(TColor.newHex("ffa500"));
		listaColoresEquipo.add(TColor.newHex("556b2f"));
		listaColoresEquipo.add(TColor.newHex("483d8b"));
		listaColoresEquipo.add(TColor.newHex("ff00ff"));
		return listaColoresEquipo;
	}


}
