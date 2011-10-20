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
	List<Equipo> iniciaEquipos() {
		ColorList listaColoresEquipo=new ServicioToxiColor(p5).iniciaColoresEquipos();
		List<Equipo> equiposIn = new ArrayList();
		equiposIn.add(new Equipo(p5, 1, "bamako", 224, 122,"Niamakoro y Sicoro"));
		equiposIn.add(new Equipo(p5, 2, "barcelona", 236, 55, "Casc Antic"));
		equiposIn.add(new Equipo(p5, 3, "bogota", 133, 145, "Chapinero"));
		equiposIn.add(new Equipo(p5, 4, "elalto", 141, 174, "Santa Rosa"));
		equiposIn.add(new Equipo(p5, 5, "evry", 238, 39, "Pyramides"));
		equiposIn.add(new Equipo(p5, 6, "montreuil", 243, 43, "Bel-Pêche"));
		equiposIn.add(new Equipo(p5, 7, "palma", 241, 61, "Son Roca y Son Gotleu"));
		equiposIn.add(new Equipo(p5, 8, "pikine", 210, 121, "Wakhinane"));
		equiposIn.add(new Equipo(p5, 9, "rio", 175, 221, "La Maré y Rio das Pedras"));
		equiposIn.add(new Equipo(p5, 10, "sale", 224, 72, "Karyan El Oued"));
		for (int i = 0; i < equiposIn.size(); i++)
			equiposIn.get(i).setColor((TColor) listaColoresEquipo.get(i));
		return equiposIn;
	}
}
