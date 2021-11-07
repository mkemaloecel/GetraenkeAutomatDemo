package com.peuka;

/**
 * @author kemal
 * @since 07.11.2021
 */
public class Muenze {
	private int cent;
	private int menge;

	public Muenze(int cent, int menge) {
		this.cent = cent;
		this.menge = menge;
	}

	public int getCent() {
		return cent;
	}

	public void setCent(int cent) {
		this.cent = cent;
	}

	public int getMenge() {
		return menge;
	}

	public void setMenge(int menge) {
		this.menge = menge;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Muenze{");
		sb.append("cent=").append(cent);
		sb.append(", menge=").append(menge);
		sb.append('}');
		return sb.toString();
	}
}
