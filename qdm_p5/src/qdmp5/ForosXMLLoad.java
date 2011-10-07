package qdmp5;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import processing.core.PApplet;
import processing.xml.XMLElement;

public class ForosXMLLoad extends ClaseP5{
	
	
	
	private final List<Equipo> equiposIn;
	public ForosXMLLoad(PApplet p5, List<Equipo> equipos) {
		super(p5);
		this.equiposIn = equipos;
	}

	List<Comentario> procesaXML(String archivo) {
		List<Comentario> comentariosList = new ArrayList<Comentario>();
		XMLElement xml = new XMLElement(p5, archivo);
		int numSites = xml.getChildCount();
		XMLElement[] kids = xml.getChildren();
		log.info("numero de elementos en " + archivo + "......" + kids.length);
		for (XMLElement el : kids) {
			try {
				String fecha = el.getChild("created-at").getContent();
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ss'Z'");
				Date d = sdf.parse(fecha);
				String texto = el.getChild("textoes").getContent();
				String titulo = el.getChild("tituloes").getContent();
				int idComentario = Integer.parseInt(el.getChild("id")
						.getContent());
				String idComentarioParentString = el.getChild("comentario-id")
						.getContent();
				int idComentarioParent = 0;
				if (idComentarioParentString != null) {
					idComentarioParent = Integer
							.parseInt(idComentarioParentString);
				}
				int idEquipo = Integer.parseInt(el.getChild(
						"usuarioforo/equipo-id").getContent());
				Equipo e = getEquipo(idEquipo);

				String nombreUsuario = el.getChild("usuarioforo/nombre")
						.getContent();
				String mailUsuario = el.getChild("usuarioforo/email")
						.getContent();
				int idUsuario = Integer.parseInt(el.getChild("usuarioforo/id")
						.getContent());

				Usuario usuario = new Usuario(p5, idUsuario, nombreUsuario,
						mailUsuario, e);
				usuario = e.addUsuario(usuario);

				log.debug(texto);
				log.debug(idEquipo);
				log.debug("");
				Comentario comentario = new Comentario(p5, idComentario,
						titulo, texto, usuario, idComentarioParent, d);
				usuario.addComentario(comentario);
				comentariosList.add(comentario);
			} catch (Exception e) {
				log.error("el message" + e.getMessage());
				e.printStackTrace();
			}
		}
		return comentariosList;
	}
	Equipo getEquipo(int id) {
		Equipo e = equiposIn.get(id - 1);
		return e;
	}
}
