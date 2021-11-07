package com.peuka;

public class GetraenkeautomatStarter {

	private static Automat service = new AutomatImpl();

	private static void start() {
		service.setMuenzeList(AutomatHelper.getMuenzeList());
		service.setFaecher(AutomatHelper.getFaches());
	}

	public static void main(String[] args) {

		//Automats hochfahren
		start();
		//Wasser kaufen und 2€ werfen...
		fallWasser();
		//Redbull kaufen und 2€ werfen...
		fallRB();
		//Artikel entleeren...
		artikelEntleeren();

		//Wasser kaufen
		fallWasser();
		//Geld entleeren
		geldEntleeren();
		//Redbull kaufen
		fallRB();
		//Redbull befüllen
		Artikel redBull = new Artikel(3, "RedBull");
		service.artikelBefuellen(redBull, 5, 120);
		//Geld befüllen
		service.geldBefuellen(50, 10);
		//Redbull kaufen
		fallRB();
		//nicht existierte Artikel kaufen...
		fallUnbekannteArtikel();
	}

	private static void fallUnbekannteArtikel() {
		Artikel redBull = new Artikel(100, "RedBullmmxx");
		System.out.println("Test fallUnbekannteArtikel");

		Muenze cent100 = new Muenze(100, 1);
		Muenze cent50 = new Muenze(50, 2);

		GetraenkUndWechselgeld getraenkUndWechselgeld = service.kaufen(redBull, cent100, cent50);

		System.out.println(getraenkUndWechselgeld.getMessage());
	}

	private static void artikelEntleeren() {
		Artikel redBull = new Artikel(3, "RedBull");
		service.artikelEtleeren(redBull);
	}

	private static void geldEntleeren() {
		service.geldEntleeren(50);
	}

	private static void fallWasser() {

		System.out.println("Test 1 kauf Wasser für 80 cent, Muenze 150");

		Muenze cent100 = new Muenze(100, 1);
		Muenze cent50 = new Muenze(50, 2);
		Artikel wasser = new Artikel(1, "Wasser");
		GetraenkUndWechselgeld getraenkUndWechselgeld = service.kaufen(wasser, cent100, cent50);

		System.out.println(getraenkUndWechselgeld.getMessage());
	}

	private static void fallRB() {

		System.out.println("Test 1 kauf RedBull für 120 cent, Muenze 200");

		Muenze cent100 = new Muenze(100, 1);
		Muenze cent50 = new Muenze(50, 2);
		Artikel redBull = new Artikel(3, "RedBull");
		GetraenkUndWechselgeld getraenkUndWechselgeld = service.kaufen(redBull, cent100, cent50);

		System.out.println(getraenkUndWechselgeld.getMessage());
	}
}
