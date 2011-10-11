package qdmp5.escale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import processing.core.PApplet;

import qdmp5.ClaseP5;
import qdmp5.ServicioToxiColor;
import toxi.color.ColorList;
import toxi.color.TColor;

public class ServicioLoadEquipos extends ClaseP5 {
	public ServicioLoadEquipos(PApplet p5) {
		super(p5);
		// TODO Auto-generated constructor stub
	}

	public List<ComentarioEscale> loadXML(List<EquipoEscale> equipos, List<EquipoEscale> equiposDB) {
		equiposDB.add(new EquipoEscale(p5, 1, "bamako", 224, 122, "Niamakoro y Sicoro"));
		equiposDB.add(new EquipoEscale(p5, 2, "barcelona", 236, 55, "Casc Antic"));
		equiposDB.add(new EquipoEscale(p5, 3, "bogota", 133, 145, "Chapinero"));
		equiposDB.add(new EquipoEscale(p5, 4, "elalto", 141, 174, "Santa Rosa"));
		equiposDB.add(new EquipoEscale(p5, 5, "evry", 238, 39, "Pyramides"));
		equiposDB.add(new EquipoEscale(p5, 6, "montreuil", 243, 43, "Bel-Pêche"));
		equiposDB.add(new EquipoEscale(p5, 7, "palma", 241, 61, "Son Roca y Son Gotleu"));
		equiposDB.add(new EquipoEscale(p5, 8, "pikine", 210, 121, "Wakhinane"));
		equiposDB.add(new EquipoEscale(p5, 9, "rio", 175, 221, "La Maré y Rio das Pedras"));
		equiposDB.add(new EquipoEscale(p5, 10, "sale", 224, 72, "Karyan El Oued"));
		ColorList listaColoresEquipo = new ServicioToxiColor().iniciaColoresEquipos();

		for (int i = 0; i < equiposDB.size(); i++)
			equiposDB.get(i).setColor((TColor) listaColoresEquipo.get(i));
		ForosXMLLoadScale forosXMLLoad = new ForosXMLLoadScale(p5, equiposDB);
		List<ComentarioEscale> comentarios = forosXMLLoad.procesaXML("foros.xml");
		Collections.reverse(comentarios);
		return comentarios;
	}

}
