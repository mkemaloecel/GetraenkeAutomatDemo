package com.peuka.getraenkeautomatdemo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.peuka.Artikel;
import com.peuka.Automat;
import com.peuka.AutomatImpl;
import com.peuka.Fach;
import com.peuka.GetraenkUndWechselgeld;
import com.peuka.Muenze;

/**
 * @author kemal
 * @since 07.11.2021
 */

public class GetraenkeAutomatTest {

	public static final String BITTE_NEHMEN_SIE_IHR = "Bitte nehmen Sie, Ihr ";
	public static final String INFO_GELD_PREIS = "cent wert ist und Sie haben 200 cent bezahlt und Ihr Wechseldeld";
	private static final String FALL_1_ERWARTET = BITTE_NEHMEN_SIE_IHR + "Wasser, welche 80 " + INFO_GELD_PREIS + ", 1 mal 100, 1 mal 20";
	private static final String FALL_3_ERWARTET = BITTE_NEHMEN_SIE_IHR + "Redbull, welche 120 " + INFO_GELD_PREIS + ", 1 mal 50, 1 mal 20, 1 mal 10";
	private static final String FALL_2_ERWARTET = BITTE_NEHMEN_SIE_IHR + "Apfelsaft, welche 110 " + INFO_GELD_PREIS + ", 1 mal 50, 1 mal 20, 1 mal 10";
	private static final String FALL_4_ERWARTET = BITTE_NEHMEN_SIE_IHR + "RockStar, welche 130 " + INFO_GELD_PREIS + ", 1 mal 50, 1 mal 20";
	private static final String FALL_UNBEKANNT_ERWARTET = "Auswahl leider ausverkauft!";
	private static final String FALL_NICHT_GENUG_ERWARTET = "Auswahl kostet 80 und Sie haben wenig Geld{60}";
	private static final String FALL_KEINE_WECHSELGELD_ERWARTET = "Bitte nehmen Sie Ihr Geld zurück, wir haben nicht genug Wechselgeld. Es tut uns leid!";
	private static final String FALL_ARTIEL_KAUF_OHNE_WECHSELGELD = "Bitte nehmen Sie, Ihr Wasser, welche 80 cent wert ist und Sie haben 80 cent bezahlt und Ihr Wechseldeld {0}";
	private static Artikel wasser = new Artikel(1, "Wasser");
	private static Artikel apfelsaft = new Artikel(2, "Apfelsaft");
	private static Artikel redBull = new Artikel(3, "Redbull");
	private static Artikel rockStar = new Artikel(4, "RockStar");
	private static Artikel unbekannt = new Artikel(1000, "unbekannt");

	private static Muenze cent100 = new Muenze(100, 1);
	private static Muenze cent50 = new Muenze(50, 2);
	private static Muenze cent20 = new Muenze(20, 3);

	private Automat automat;

	private static List<Fach> getVolleFaches() {
		Artikel w = new Artikel(1, "Wasser");
		Artikel a = new Artikel(2, "Apfelsaft");
		Artikel rb = new Artikel(3, "redbull");
		Artikel rs = new Artikel(4, "rockstar");

		List<Fach> faecher = new ArrayList<>();

		faecher.add(new Fach(w, 3, 80));
		faecher.add(new Fach(a, 3, 110));
		faecher.add(new Fach(rb, 3, 120));
		faecher.add(new Fach(rs, 3, 130));
		return faecher;
	}

	private static List<Muenze> getVolleMuenzeList() {
		List<Muenze> muenzeList = new ArrayList<>();

		muenzeList.add(new Muenze(10, 10));
		muenzeList.add(new Muenze(20, 10));
		muenzeList.add(new Muenze(50, 10));
		muenzeList.add(new Muenze(100, 10));
		muenzeList.add(new Muenze(200, 10));
		return muenzeList;
	}

	@Before
	public void start() {

		automat = new AutomatImpl();
		automat.setMuenzeList(getVolleMuenzeList());
		automat.setFaecher(getVolleFaches());
		System.out.println("automat" + automat);
	}

	@Test
	public void artiel1GenugGeldTest() {
		int artikelMenge1 = automat.getFaecher().get(0).getAnzahl();
		System.out.println("artikelMenge1: " + artikelMenge1);
		GetraenkUndWechselgeld getraenkUndWechselgeld = automat.kaufen(wasser, cent100, cent50);
		System.out.println(getraenkUndWechselgeld.getMessage());
		int artikelMenge2 = automat.getFaecher().get(0).getAnzahl();
		System.out.println("artikelMenge2: " + artikelMenge2);
		Assert.assertEquals(artikelMenge1 - artikelMenge2, 1);

	}

	@Test
	public void artiel2GenugGeldTest() {
		int artikelMenge1 = automat.getFaecher().get(1).getAnzahl();
		System.out.println("artikelMenge1: " + artikelMenge1);
		GetraenkUndWechselgeld getraenkUndWechselgeld = automat.kaufen(apfelsaft, cent100, cent50);
		System.out.println(getraenkUndWechselgeld.getMessage());
		Assert.assertEquals(FALL_2_ERWARTET, getraenkUndWechselgeld.getMessage());
		int artikelMenge2 = automat.getFaecher().get(1).getAnzahl();
		System.out.println("artikelMenge2: " + artikelMenge2);
		Assert.assertEquals(artikelMenge1 - artikelMenge2, 1);
	}

	@Test
	public void artiel3GenugGeldTest() {
		int artikelMenge1 = automat.getFaecher().get(2).getAnzahl();
		System.out.println("artikelMenge1: " + artikelMenge1);
		GetraenkUndWechselgeld getraenkUndWechselgeld = automat.kaufen(redBull, cent100, cent50);
		System.out.println(getraenkUndWechselgeld.getMessage());
		Assert.assertEquals(FALL_3_ERWARTET, getraenkUndWechselgeld.getMessage());
		int artikelMenge2 = automat.getFaecher().get(2).getAnzahl();
		System.out.println("artikelMenge2: " + artikelMenge2);
		Assert.assertEquals(artikelMenge1 - artikelMenge2, 1);
	}

	@Test
	public void artiel4GenugGeldTest() {
		int artikelMenge1 = automat.getFaecher().get(3).getAnzahl();
		System.out.println("artikelMenge1: " + artikelMenge1);
		GetraenkUndWechselgeld getraenkUndWechselgeld = automat.kaufen(rockStar, cent100, cent50);
		System.out.println(getraenkUndWechselgeld.getMessage());
		Assert.assertEquals(FALL_4_ERWARTET, getraenkUndWechselgeld.getMessage());
		int artikelMenge2 = automat.getFaecher().get(3).getAnzahl();
		System.out.println("artikelMenge2: " + artikelMenge2);
		Assert.assertEquals(artikelMenge1 - artikelMenge2, 1);
	}

	/**
	 * wenn Automats gegebene Artikel nicht hat
	 */
	@Test
	public void artielUnbekanntGenugGeldTest() {
		GetraenkUndWechselgeld getraenkUndWechselgeld = automat.kaufen(unbekannt, cent100, cent50);
		System.out.println(getraenkUndWechselgeld.getMessage());
		Assert.assertEquals(FALL_UNBEKANNT_ERWARTET, getraenkUndWechselgeld.getMessage());
	}

	/**
	 * wenn geworfene Münzen zu wenig ist.
	 */
	@Test
	public void artielNichtGenugGeldTest() {
		int artikelMenge1 = automat.getFaecher().get(0).getAnzahl();
		System.out.println("artikelMenge1: " + artikelMenge1);
		GetraenkUndWechselgeld getraenkUndWechselgeld = automat.kaufen(wasser, cent20);
		System.out.println(getraenkUndWechselgeld.getMessage());
		Assert.assertEquals(FALL_NICHT_GENUG_ERWARTET, getraenkUndWechselgeld.getMessage());
		int artikelMenge2 = automat.getFaecher().get(0).getAnzahl();
		System.out.println("artikelMenge2: " + artikelMenge2);
		Assert.assertEquals(artikelMenge1 - artikelMenge2, 0);
	}

	/**
	 * wenn man ganz genau Artikel kosten als Muenze bezahlt.
	 */
	@Test
	public void artielKaufOhneWechselgeldTest() {
		int artikelMenge1 = automat.getFaecher().get(0).getAnzahl();
		System.out.println("artikelMenge1: " + artikelMenge1);
		GetraenkUndWechselgeld getraenkUndWechselgeld = automat.kaufen(wasser, new Muenze(50, 1), new Muenze(20, 1), new Muenze(10, 1));
		System.out.println(getraenkUndWechselgeld.getMessage());
		Assert.assertEquals(FALL_ARTIEL_KAUF_OHNE_WECHSELGELD, getraenkUndWechselgeld.getMessage());
		int artikelMenge2 = automat.getFaecher().get(0).getAnzahl();
		System.out.println("artikelMenge2: " + artikelMenge2);
		Assert.assertEquals(artikelMenge1 - artikelMenge2, 1);
	}

	/**
	 * wenn Automats kein Wechselgeld mehr hat.
	 */
	@Test
	public void keinWechselgeldTest() {

		automat = new AutomatImpl();
		automat.setFaecher(getVolleFaches());
		int artikelMenge1 = automat.getFaecher().get(0).getAnzahl();
		System.out.println("artikelMenge1: " + artikelMenge1);
		GetraenkUndWechselgeld getraenkUndWechselgeld = automat.kaufen(wasser, cent100, cent50);
		System.out.println(getraenkUndWechselgeld.getMessage());
		Assert.assertEquals(FALL_KEINE_WECHSELGELD_ERWARTET, getraenkUndWechselgeld.getMessage());
		int artikelMenge2 = automat.getFaecher().get(0).getAnzahl();
		System.out.println("artikelMenge2: " + artikelMenge2);
		Assert.assertEquals(artikelMenge1 - artikelMenge2, 0);
	}

	/**
	 * 1. automat hochfahren
	 * <p>
	 * 2. artikel Wasser entleeren
	 * <p>
	 * 4. versuchen wasser kaufen.
	 * <p>
	 * 5. Fehler bekommt man.
	 */
	@Test
	public void artikelEtleerenTest() {
		automat = new AutomatImpl();
		automat.setFaecher(getVolleFaches());
		automat.setMuenzeList(getVolleMuenzeList());

		automat.artikelEtleeren(wasser);
		GetraenkUndWechselgeld getraenkUndWechselgeld = automat.kaufen(wasser, cent100, cent50);
		System.out.println(getraenkUndWechselgeld.getMessage());
		Assert.assertEquals(FALL_UNBEKANNT_ERWARTET, getraenkUndWechselgeld.getMessage());
	}

	/**
	 * 1. automat hochfahren
	 * <p>
	 * 2. artikel entleeren
	 * <p>
	 * 3. versuch Wasser zu kaufen.
	 * <p>
	 * 4. bekommt man fehler.
	 * <p>
	 * 5. Artikel Wasser befühlen.
	 * <p>
	 * 6. noch mal versuchen Wasser zu kaufen.
	 * <p>
	 * 7. Kauf erfolgreich...
	 */

	@Test
	public void artikelBefuellenTest() {
		automat = new AutomatImpl();
		automat.setFaecher(getVolleFaches());
		automat.setMuenzeList(getVolleMuenzeList());

		automat.artikelEtleeren(wasser);
		GetraenkUndWechselgeld getraenkUndWechselgeld = automat.kaufen(wasser, cent100, cent50);
		Assert.assertEquals(FALL_UNBEKANNT_ERWARTET, getraenkUndWechselgeld.getMessage());

		automat.artikelBefuellen(wasser, 1, 80);
		int artikelMenge1 = automat.getFaecher().get(0).getAnzahl();
		System.out.println("artikelMenge1: " + artikelMenge1);

		getraenkUndWechselgeld = automat.kaufen(wasser, cent100, cent50);
		Assert.assertEquals(FALL_1_ERWARTET, getraenkUndWechselgeld.getMessage());
		System.out.println(getraenkUndWechselgeld.getMessage());
		int artikelMenge2 = automat.getFaecher().get(0).getAnzahl();
		System.out.println("artikelMenge2: " + artikelMenge2);
		Assert.assertEquals(artikelMenge1 - artikelMenge2, 1);
	}

	/**
	 * 1. automat hochfahren mit Wechselgeld
	 * <p>
	 * 2. dann 100 und 50' er Cent entleeren.
	 * <p>
	 * 3. versuchen wasser kaufen mit 200cent.
	 * <p>
	 * 4. Kein Wechselgeld Fehler bekommt man.
	 */
	@Test
	public void geldEntleerenTest() {

		automat = new AutomatImpl();
		automat.setFaecher(getVolleFaches());
		automat.setMuenzeList(getVolleMuenzeList());
		int artikelMenge1 = automat.getFaecher().get(0).getAnzahl();
		System.out.println("artikelMenge1: " + artikelMenge1);
		automat.geldEntleeren(100);
		automat.geldEntleeren(50);

		GetraenkUndWechselgeld getraenkUndWechselgeld = automat.kaufen(wasser, cent100, cent50);
		System.out.println(getraenkUndWechselgeld.getMessage());
		Assert.assertEquals(FALL_KEINE_WECHSELGELD_ERWARTET, getraenkUndWechselgeld.getMessage());

		int artikelMenge2 = automat.getFaecher().get(0).getAnzahl();
		System.out.println("artikelMenge2: " + artikelMenge2);
		Assert.assertEquals(artikelMenge1 - artikelMenge2, 0);

	}

	/**
	 * 1. automat hochfahren ohne Wechselgeld
	 * <p>
	 * 2. versuch Wasser zu kaufen.
	 * <p>
	 * 3. bekommt man fehler 'wir haben nicht genug Wechselgeld.'
	 * <p>
	 * 4. automat mit 100*2 und 20*2 Cent Münzen befüllen
	 * <p>
	 * 5. noch mal versuchen Wasser zu kaufen.
	 * <p>
	 * 6. Kauf erfolgreich...
	 */
	@Test
	public void geldBefuellenTest() {

		automat = new AutomatImpl();
		automat.setFaecher(getVolleFaches());

		int artikelMenge1 = automat.getFaecher().get(0).getAnzahl();
		System.out.println("artikelMenge1: " + artikelMenge1);

		GetraenkUndWechselgeld getraenkUndWechselgeld = automat.kaufen(wasser, cent100, cent50);
		System.out.println(getraenkUndWechselgeld.getMessage());
		Assert.assertEquals(FALL_KEINE_WECHSELGELD_ERWARTET, getraenkUndWechselgeld.getMessage());

		automat.geldBefuellen(100, 2);

		automat.geldBefuellen(20, 2);
		getraenkUndWechselgeld = automat.kaufen(wasser, cent100, cent50);
		System.out.println(getraenkUndWechselgeld.getMessage());
		int artikelMenge2 = automat.getFaecher().get(0).getAnzahl();
		System.out.println("artikelMenge2: " + artikelMenge2);
		Assert.assertEquals(artikelMenge1 - artikelMenge2, 1);

	}
}
