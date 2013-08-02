package scripts.Barrows.methods.tunnel;

import org.tribot.api2007.types.RSTile;

public class TunnelDoor {

	private static String name;
	private static RSTile tile;
	private static int bitSetting;

	public TunnelDoor(String name, RSTile tile, int bitSetting) {
		this.name = name;
		this.tile = tile;
		this.bitSetting = bitSetting;
	}

	public static String getName() {
		return name;
	}

	public static RSTile getLocation() {
		return tile;
	}

	public static int getBitSetting() {
		return bitSetting;
	}
}
