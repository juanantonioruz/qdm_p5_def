package qdmp5.escale;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import qdmp5.ClaseP5;

public class ServicioEscala extends ClaseP5{

	private final List<EquipoEscale> equipos;
	private final List<ComentarioEscale> comentariosRepresentados;

	public ServicioEscala(PApplet p5, List<EquipoEscale> equipos, List<ComentarioEscale> comentariosRepresentados) {
		super(p5);
		this.equipos = equipos;
		this.comentariosRepresentados = comentariosRepresentados;
	}


	public  EscalaYTranslacion calculaEscala(boolean soloUltimo, int width, int height) {

		float equilibrio = 30;
		float dameX1 = dameX1(soloUltimo) - equilibrio;
		float dameX2 = dameX2(soloUltimo) + equilibrio;
		float dameY1 = dameY1(soloUltimo) - equilibrio;
		float dameY2 = dameY2(soloUltimo) + equilibrio;

		float anchoRepresentar = dameX2 - dameX1;
		float altoRepresentar = dameY2 - dameY1;
		float escalaProvX = (width / anchoRepresentar);
		float escalaProvY = (height / altoRepresentar);
		float incremento = dameIncremento(escalaProvX, escalaProvY);

		float origenX = dameX1 + (anchoRepresentar / 2);
		float origenY = dameY1 + (altoRepresentar / 2);
		log.debug("origenX" + origenX + " origenY" + origenY);
		EscalaYTranslacion escalaActual = new EscalaYTranslacion(incremento, origenX, origenY);

		return escalaActual;

	}

	
	float dameIncremento(float x, float y) {
		if (x > y)
			return y;
		return x;
	}




	float dameX1(boolean soloUltimo) {
		if (soloUltimo)
			return dameEquipoDeUltimoComent().x - soloUnEquipoMargin;

		float resultado = p5.width;
		for (EquipoEscale e : equipos) {
			if (e.x < resultado)
				resultado = e.x;
		}
		return resultado;
	}

	private EquipoEscale dameEquipoDeUltimoComent() {
		return comentariosRepresentados.get(comentariosRepresentados.size() - 1).usuario.equipo;
	}

	private float soloUnEquipoMargin = 50;

	float dameX2(boolean soloUltimo) {
		if (soloUltimo)
			return dameEquipoDeUltimoComent().x + soloUnEquipoMargin;
		float resultado = 0;
		for (EquipoEscale e : equipos) {
			if (e.x > resultado)
				resultado = e.x;
		}
		return resultado;
	}

	float dameY1(boolean soloUltimo) {
		if (soloUltimo)
			return dameEquipoDeUltimoComent().y - soloUnEquipoMargin;
		float resultado = p5.height;
		for (EquipoEscale e : equipos) {
			if (e.y < resultado)
				resultado = e.y;
		}
		return resultado;
	}

	float dameY2(boolean soloUltimo) {
		if (soloUltimo)
			return dameEquipoDeUltimoComent().y + soloUnEquipoMargin;
		float resultado = 0;
		for (EquipoEscale e : equipos) {
			if (e.y > resultado)
				resultado = e.y;
		}
		return resultado;
	}

}
