package com.peuka;

import java.util.List;

/**
 * @author kemal
 * @since 07.11.2021
 */
public class GetraenkUndWechselgeld {

	private Artikel getraenke;

	private List<Muenze> wechselgeld;

	private String message;

	public GetraenkUndWechselgeld(Artikel getraenke, List<Muenze> wechselgeld) {
		this.getraenke = getraenke;
		this.wechselgeld = wechselgeld;
	}

	public Artikel getGetraenke() {
		return getraenke;
	}

	public GetraenkUndWechselgeld setGetraenke(Artikel getraenke) {
		this.getraenke = getraenke;
		return this;
	}

	public List<Muenze> getWechselgeld() {
		return wechselgeld;
	}

	public GetraenkUndWechselgeld setWechselgeld(List<Muenze> wechselgeld) {
		this.wechselgeld = wechselgeld;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public GetraenkUndWechselgeld setMessage(String message) {
		this.message = message;
		return this;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("GetraenkUndWechselgeld{");
		sb.append("message=").append(message);
		sb.append("getraenke=").append(getraenke);
		sb.append(", wechselgeld=").append(wechselgeld);
		sb.append('}');
		return sb.toString();
	}
}
