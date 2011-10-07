package qdmp5;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

import toxi.color.ColorList;
import toxi.color.TColor;
import toxi.color.theory.ColorTheoryStrategy;
import toxi.color.theory.CompoundTheoryStrategy;

public class ServicioEquiposForo extends ClaseP5{
	public ServicioEquiposForo(PApplet p5) {
		super(p5);
	}
	private ColorList iniciaColoresEquipos() {
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
	List<Equipo> iniciaEquipos() {
		ColorList listaColoresEquipo=iniciaColoresEquipos();
		List<Equipo> equiposIn = new ArrayList();
		equiposIn.add(new Equipo(p5, 1, "bamako", 224, 122));
		equiposIn.add(new Equipo(p5, 2, "barcelona", 236, 55));
		equiposIn.add(new Equipo(p5, 3, "bogota", 133, 145));
		equiposIn.add(new Equipo(p5, 4, "elalto", 141, 174));
		equiposIn.add(new Equipo(p5, 5, "evry", 238, 39));
		equiposIn.add(new Equipo(p5, 6, "montreuil", 243, 43));
		equiposIn.add(new Equipo(p5, 7, "palma", 241, 61));
		equiposIn.add(new Equipo(p5, 8, "pikine", 210, 121));
		equiposIn.add(new Equipo(p5, 9, "rio", 175, 221));
		equiposIn.add(new Equipo(p5, 10, "sale", 224, 72));
		for (int i = 0; i < equiposIn.size(); i++)
			equiposIn.get(i).setColor((TColor) listaColoresEquipo.get(i));
		return equiposIn;
	}
}
