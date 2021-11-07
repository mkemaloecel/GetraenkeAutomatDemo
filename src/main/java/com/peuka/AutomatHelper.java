package com.peuka;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kemal
 * @since 07.11.2021
 */
public class AutomatHelper {

	public static List<Fach> getFaches() {
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

	public static List<Muenze> getMuenzeList() {
		List<Muenze> muenzeList = new ArrayList<>();

		muenzeList.add(new Muenze(10, 10));
		muenzeList.add(new Muenze(20, 10));
		muenzeList.add(new Muenze(50, 10));
		muenzeList.add(new Muenze(100, 10));
		muenzeList.add(new Muenze(200, 10));
		return muenzeList;
	}

}
