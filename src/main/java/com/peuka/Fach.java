package com.peuka;

/**
 * @author kemal
 * @since 07.11.2021
 */
public class Fach {

	private Artikel artikel;

	private int anzahl;

	private int kosten;

	public Fach(Artikel artikel, int anzahl, int kosten) {
		this.artikel = artikel;
		this.anzahl = anzahl;
		this.kosten = kosten;
	}

	public Artikel getArtikel() {
		return artikel;
	}

	public void setArtikel(Artikel artikel) {
		this.artikel = artikel;

	}

	public int getAnzahl() {
		return anzahl;
	}

	public void setAnzahl(int anzahl) {
		this.anzahl = anzahl;
	}

	public int getKosten() {
		return kosten;
	}

	public void setKosten(int kosten) {
		this.kosten = kosten;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Fach{");
		sb.append("artikel=").append(artikel);
		sb.append(", anzahl=").append(anzahl);
		sb.append(", kosten=").append(kosten);
		sb.append('}');
		return sb.toString();
	}
}
