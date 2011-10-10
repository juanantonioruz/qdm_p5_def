package qdmp5;

import toxi.color.ColorList;
import toxi.color.TColor;
import toxi.color.theory.ColorTheoryStrategy;
import toxi.color.theory.CompoundTheoryStrategy;

public class ServicioToxiColor {
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

}
