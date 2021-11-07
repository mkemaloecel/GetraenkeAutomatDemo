package com.peuka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author kemal
 * @since 07.11.2021
 */
public class AutomatImpl implements Automat {

	public static final String AUSWAHL_LEIDER_AUSVERKAUFT = "Auswahl leider ausverkauft!";
	public static final String BITTE_NEHMEN_SIE = "Bitte nehmen Sie";
	public static final String CENT_WERT_IST_UND_SIE_HABEN = " cent wert ist und Sie haben ";
	public static final String CENT_BEZAHLT_UND_IHR_WECHSELDELD = " cent bezahlt und Ihr Wechseldeld";

	private static List<Integer> muenzenList = Arrays.asList(200, 100, 50, 20, 10);

	private List<Fach> faecher = new ArrayList<>();

	private List<Muenze> muenzeList = new ArrayList<>();

	@Override
	public GetraenkUndWechselgeld kaufen(Artikel auswahl, Muenze... einzahlung) {
		int cent = berechneEinzahlung(einzahlung);

		GetraenkUndWechselgeld wechselgeld = new GetraenkUndWechselgeld(auswahl, Arrays.asList(einzahlung));
		Fach fach = findeFach(auswahl.getId());
		if (fach == null || fach.getAnzahl() == 0) {
			wechselgeld.setMessage(AUSWAHL_LEIDER_AUSVERKAUFT);
			return wechselgeld;
		}
		kaufDurchfuehren(auswahl, cent, wechselgeld, fach);

		return wechselgeld;
	}

	/**
	 * Verschiedene Fälle hier werden geprüfft.
	 *
	 * @param auswahl
	 * @param cent
	 * @param wechselgeld
	 * @param fach
	 */
	private void kaufDurchfuehren(Artikel auswahl, int cent, GetraenkUndWechselgeld wechselgeld, Fach fach) {
		int kosten = fach.getKosten();
		if (kosten == 0) {
			wechselgeld.setMessage(AUSWAHL_LEIDER_AUSVERKAUFT);
		}
		else if (kosten > cent) {
			wechselgeld.setMessage("Auswahl kostet " + kosten + " und Sie haben wenig Geld{" + cent + "}");
		}
		else {
			wechselgeld.setMessage(BITTE_NEHMEN_SIE + " Ihr Geld zurück, wir haben nicht genug Wechselgeld. Es tut uns leid!");
			int rest = cent - kosten;
			if (rest == 0) {
				wechselgeld.setMessage(BITTE_NEHMEN_SIE + ", Ihr " + auswahl.getName() + ", welche " + kosten + CENT_WERT_IST_UND_SIE_HABEN + cent + CENT_BEZAHLT_UND_IHR_WECHSELDELD + " {0}");
				wechselgeld.setWechselgeld(new ArrayList<>());
				artikelSubtraktion(auswahl.getId());
			}
			else if (rest > 0) {
				berecheUndFuehrKaufDurch(auswahl, cent, wechselgeld, kosten);
				if (!wechselgeld.getWechselgeld().isEmpty()) {
					artikelSubtraktion(auswahl.getId());
				}
			}
		}
	}

	/**
	 * Kauf wird hier abgeschlossen
	 *
	 * @param auswahl
	 * @param cent
	 * @param wechselgeld
	 * @param kosten
	 */
	private void berecheUndFuehrKaufDurch(Artikel auswahl, int cent, GetraenkUndWechselgeld wechselgeld, int kosten) {
		List<Muenze> wechselMuenze = berechnerWechselGeld(cent, kosten);
		if (!wechselMuenze.isEmpty()) {
			wechselgeld.setMessage(erstellWechselgeldMessage(auswahl, cent, kosten, wechselMuenze));
		}
		wechselgeld.setWechselgeld(wechselMuenze);
	}

	/**
	 * Erstellt zusammen Ausgabe Text mit Wechselgeld Informationen
	 *
	 * @param auswahl
	 * @param cent
	 * @param kosten
	 * @param wechselMuenze
	 * @return
	 */
	private String erstellWechselgeldMessage(Artikel auswahl, int cent, int kosten, List<Muenze> wechselMuenze) {
		String message;
		message = BITTE_NEHMEN_SIE + ", Ihr " + auswahl.getName() + ", welche " + kosten + CENT_WERT_IST_UND_SIE_HABEN + cent + CENT_BEZAHLT_UND_IHR_WECHSELDELD;
		for (Muenze muenze : wechselMuenze) {
			message += ", 1 mal " + muenze.getCent();
		}
		return message;
	}

	/**
	 * finde heraus wie viel ingesamt(in Cent) Münzen eingeworfen.
	 *
	 * @param einzahlung
	 * @return
	 */
	private int berechneEinzahlung(Muenze... einzahlung) {
		int cent = 0;

		for (Muenze muenze : einzahlung) {
			cent += (muenze.getCent() * muenze.getMenge());
		}
		return cent;
	}

	private void artikelSubtraktion(int artikelId) {
		for (Fach fach : getFaecher()) {
			if (fach.getArtikel().getId() == artikelId) {
				fach.setAnzahl(fach.getAnzahl() - 1);
			}
		}
	}

	private boolean muenzenSubtraktion(int cent) {
		for (Muenze muenze : getMuenzeList()) {
			if (muenze.getCent() == cent && muenze.getMenge() > 0) {
				muenze.setMenge(muenze.getMenge() - 1);
				return true;
			}
		}
		return false;
	}

	/**
	 * Wechselgeld Berechnung, dabei subtraktion auch geprüft und durchgeführt.
	 * <p>
	 * Falls subtraktion nicht erfolgreich wird, soll zurückerstatet werden...
	 *
	 * @param cent
	 * @param kosten
	 * @return
	 */
	private List<Muenze> berechnerWechselGeld(int cent, int kosten) {
		List<Muenze> list = new ArrayList<>();
		int restGeld = cent - kosten;
		boolean subtraktion = true;
		for (Integer muenze : muenzenList) {
			int rest = restGeld - muenze;
			if (rest >= 0) {
				subtraktion = muenzenSubtraktion(muenze);
				if (!subtraktion) {
					list = new ArrayList<>();
					break;
				}
				list.add(new Muenze(muenze, 1));
				restGeld = rest;
			}
		}

		//wenn nicht genut Wechselgeld gibt, soll das System Münzen wiederherstellen...
		if (!subtraktion) {
			for (Muenze muenze : list) {
				geldBefuellen(muenze.getCent(), 1);
			}
		}

		return list;
	}

	/**
	 * ein Fach wird hier gesucht durch mit Artikel Id
	 *
	 * @param id
	 * @return
	 */
	private Fach findeFach(int id) {
		for (Fach fach : getFaecher()) {
			if (fach.getArtikel().getId() == id) {
				return fach;
			}
		}
		return null;
	}

	@Override
	public void artikelBefuellen(Artikel auswahl, int menge, int kosten) {
		boolean found = false;
		for (Fach fach : getFaecher()) {
			if (fach.getArtikel().getId() == auswahl.getId()) {
				fach.setAnzahl(menge);
				found = true;
				break;
			}
		}

		if (!found) {
			getFaecher().add(new Fach(auswahl, menge, kosten));
		}
	}

	@Override
	public void artikelEtleeren(Artikel auswahl) {
		artikelBefuellen(auswahl, 0, 0);
	}

	@Override
	public void geldBefuellen(int cent, int menge) {
		boolean found = false;

		for (Muenze muenze : getMuenzeList()) {
			if (muenze.getCent() == cent) {
				muenze.setMenge(menge);
				found = true;
				break;
			}
		}
		if (!found) {
			getMuenzeList().add(new Muenze(cent, menge));
		}
	}

	@Override
	public void geldEntleeren(int cent) {
		geldBefuellen(cent, 0);
	}

	@Override
	public List<Fach> getFaecher() {
		return faecher;
	}

	@Override
	public void setFaecher(List<Fach> faecher) {
		this.faecher = faecher;

	}

	@Override
	public List<Muenze> getMuenzeList() {
		return muenzeList;
	}

	@Override
	public void setMuenzeList(List<Muenze> muenzeList) {
		this.muenzeList = muenzeList;

	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Automats{");
		sb.append("faecher=").append(faecher);
		sb.append(", muenzeList=").append(muenzeList);
		sb.append('}');
		return sb.toString();
	}
}
