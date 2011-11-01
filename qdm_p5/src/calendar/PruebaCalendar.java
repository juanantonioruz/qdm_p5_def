package calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import processing.core.PApplet;

public class PruebaCalendar extends PApplet {
	int meses = 6;
	float altoMes;
	float anchoMes;
	float anchoDia;
	float altoDia;

	public void setup() {
		size(800, 650);
		altoMes = height / meses;
		anchoMes = height / meses;
		altoMes -= 2;
		anchoMes -= 2;
		altoDia = altoMes / 6;
		anchoDia = anchoMes / 7;
		c = Calendar.getInstance();
		format = new SimpleDateFormat("dd/MM/yyyy");
		c.set(2011, 0, 1, 0, 1);
		println(format.format(c.getTime()));
		noLoop();
	}

	Calendar c;
	SimpleDateFormat format;

	public void draw() {

		for (int j = 0; j < 1; j++)
			for (int i = 0; i < 3; i++) {
				float x = j * anchoMes;
				float y = altoMes * i;
				int semana =0;
				fill(255);
				rect(x, y, anchoMes, altoMes);
				int actualMaximum = c.getActualMaximum(Calendar.DAY_OF_MONTH);
				for (int d = 0; d < actualMaximum; d++) {
					int diaSemana = c.get(Calendar.DAY_OF_WEEK);
					println("mes"+i+"....."+actualMaximum+"//d:"+d+"/"+diaSemana + "---" + semana + "---" + format.format(c.getTime()));
					if (diaSemana == 1) {
						diaSemana = 8;
						fill(255, 0, 0);
					} else {
						fill(random(100, 150));
					}
					diaSemana=diaSemana-2;
					rect(x + (diaSemana * anchoDia), y + (semana * altoDia), anchoDia, altoDia);
					c.add(Calendar.DAY_OF_YEAR, 1);
					if(diaSemana==6)semana++;

				}
			}
	}

}
