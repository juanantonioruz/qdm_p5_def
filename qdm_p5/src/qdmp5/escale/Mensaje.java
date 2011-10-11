package qdmp5.escale;

import processing.core.PApplet;
import processing.core.PFont;
import qdmp5.ClaseP5;

public class Mensaje extends ClaseP5 {
	public Mensaje(PApplet p5) {
		super(p5);
	}

	int contador;
	float textAscent;
	boolean yaSaturado;

	void pintaMensaje(PFont font, int colorTexto, int colorFondo, String mensaje, float x, float y, int tam,
			int tiempoDeComentario, int tiempoDeDelay) {
		if (contador < 20 && yaSaturado)
			return;
		if ((contador >= (tiempoDeComentario + tiempoDeDelay - 1)))
			return;
		if (contador < tiempoDeDelay) {
			contador++;
			return;
		}
		p5.pushStyle();
		p5.textFont(font);
		float map = p5.map(contador, tiempoDeDelay, tiempoDeComentario + tiempoDeDelay, 40, 100);
		if (map > 90) {
			yaSaturado = true;
		}
		p5.fill(colorFondo, map);
		p5.noStroke();
		p5.textSize(tam);
		p5.textAlign(p5.CENTER);
		float textWidth = p5.textWidth(mensaje);
		textAscent = p5.textAscent();

		p5.rect(x - textWidth / 2, y, textWidth, textAscent);
		p5.fill(colorTexto);
		p5.text(mensaje, x, y + textAscent);
		p5.popStyle();
		if (!yaSaturado)
			contador++;
		else
			contador--;

	}

}
