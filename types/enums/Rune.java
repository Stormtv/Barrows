package scripts.Barrows.types.enums;

public enum Rune {

	FIRE(554, true), WATER(555, true), EARTH(557, true), AIR(556, true), MIND(
			558, false), CHAOS(562, false), DEATH(560, false), BLOOD(565, false);

	int id;
	boolean elemental;

	Rune(int id, boolean elemental) {
		this.id = id;
		this.elemental = elemental;
	}

}
