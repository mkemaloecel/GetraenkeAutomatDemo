package com.peuka;

import java.util.List;

/**
 * @author kemal
 * @since 07.11.2021
 */
public interface Automat {

	/**
	 * ein Artikel kaufen mit eingeworfene Münzen.
	 * <p>
	 * dabei geprüft ob
	 * <p>
	 * genug Wechselgeld gibt,
	 * artikel existiert,
	 * eingeworfene Münzen ausreichen sind
	 * <p>
	 * Liefert als Rückgabe Wechselgeld als List mit Münzen Object,
	 * Kauf Message
	 * Artikel
	 *
	 * @param auswahl
	 * @param einzahlung
	 * @return
	 */
	GetraenkUndWechselgeld kaufen(Artikel auswahl, Muenze... einzahlung);

	/**
	 * Ein Artikel befüllt wird. benötigt menge, kosten und ein Artikel Object
	 *
	 * @param auswahl
	 * @param menge
	 * @param kosten
	 */
	void artikelBefuellen(Artikel auswahl, int menge, int kosten);

	/**
	 * ein Artikel wird entleert
	 *
	 * @param auswahl
	 */
	void artikelEtleeren(Artikel auswahl);

	/**
	 * Münzen wird befüllt
	 *
	 * @param cent
	 * @param menge
	 */
	void geldBefuellen(int cent, int menge);

	/**
	 * Bestimmt Münze wird entleert
	 *
	 * @param cent
	 */
	void geldEntleeren(int cent);

	List<Fach> getFaecher();

	void setFaecher(List<Fach> faecher);

	List<Muenze> getMuenzeList();

	void setMuenzeList(List<Muenze> muenzeList);
}
