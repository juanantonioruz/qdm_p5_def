package com.enjava.p5.servicios;

import java.util.Date;

import processing.core.PApplet;
import qdmp5.ClaseP5;

public class ServicioFechas extends ClaseP5{
//	int numeroSegundosPorComentario = 3;
//	int numeroSegundosPorComentarioRetardo = 5;
//	int numeroComentarios = comentarios.size();

	/*
	 * otra forma de tener en cuenta la temporalidad pero se sale del
	 * problema totalmente habria que hacer una funcion recursiva que
	 * agrupase los valores por proximidad Map<Comentario, Integer>
	 * mapa=new HashMap(); for(int i=0; i<comentarios.size()-1;i++){
	 * float dif=calculaDiferencia(comentarios.get(i).fecha,
	 * comentarios.get(i+1).fecha); mapa.put(comentarios.get(i+1), new
	 * Integer(map(dif,))); }
	 */
//	 servicioFechas=new ServicioFechas(this);
//	daysForum = servicioFechas.calculaDiferencia(comentarios.get(0).fecha,
//			comentarios.get(comentarios.size() - 1).fecha);

	boolean debug;
	
	public ServicioFechas(PApplet p5) {
		super(p5);
	}
	float calculaDiferencia(Date inicio, Date fin) {
		
		long diff = fin.getTime() - inicio.getTime();
		long l = diff / (1000 * 60 * 60 * 24);
		return l;
	}
}
