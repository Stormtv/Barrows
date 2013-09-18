package scripts.Barrows.methods.tunnel;

import java.awt.Graphics;

import org.tribot.api2007.Game;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import scripts.Barrows.methods.tunnel.Rooms.TunnelRoom;

public enum TunnelDoor {
	//North Row
	NW_N_DOOR("NW_N_DOOR", 6735, new RSTile(3535, 9718, 0), 10, 39, 22, 7, 3),
	NW_W_DOOR("NW_W_DOOR", 6736, new RSTile(3528, 9712, 0), 11, 21, 39, 3, 7),
	NW_S_DOOR("NW_S_DOOR", 6737, new RSTile(3534, 9705, 0), 12, 38, 61, 7, 3),
	NW_E_DOOR("NW_E_DOOR", 6738, new RSTile(3541, 9711, 0), 13, 61, 38, 3, 7),
	
	N_W_DOOR("N_W_DOOR", 6738, new RSTile(3545, 9712, 0), 13, 76, 39, 3, 7),
	N_S_DOOR("N_S_DOOR", 6739, new RSTile(3551, 9705, 0), 14, 94, 60, 7, 3),
	N_E_DOOR("N_E_DOOR", 6740, new RSTile(3558, 9711, 0), 15, 117, 38, 3, 7),
	
	NE_N_DOOR("NE_N_DOOR", 6735, new RSTile(3569, 9718, 0), 10, 150, 21, 7, 3),
	NE_W_DOOR("NE_W_DOOR", 6740, new RSTile(3562, 9712, 0), 15, 132, 38, 3, 7),
	NE_S_DOOR("NE_S_DOOR", 6741, new RSTile(3568, 9705, 0), 16, 149, 61, 7, 3),
	NE_E_DOOR("NE_E_DOOR", 6742, new RSTile(3575, 9711, 0), 17, 173, 38, 3, 7),
	
	//Center Row
	W_N_DOOR("W_N_DOOR", 6737, new RSTile(3535, 9701, 0), 12, 38, 76, 7, 3),
	W_S_DOOR("W_S_DOOR", 6745, new RSTile(3534, 9688, 0), 20, 38, 117, 7, 3),
	W_E_DOOR("W_E_DOOR", 6743, new RSTile(3541, 9694, 0), 18, 60, 94, 3, 7),
	
	C_N_DOOR("C_N_DOOR", 6739, new RSTile(3552, 9701, 0), 14, 94, 76, 7, 3),
	C_W_DOOR("C_W_DOOR", 6743, new RSTile(3545, 9695, 0), 18, 77, 94, 3, 7),
	C_S_DOOR("C_S_DOOR", 6746, new RSTile(3551, 9688, 0), 21, 94, 117, 7, 3),
	C_E_DOOR("C_E_DOOR", 6744, new RSTile(3558, 9694, 0), 19, 117, 94, 3, 7),

	E_N_DOOR("E_N_DOOR", 6741, new RSTile(3569, 9701, 0), 16, 150, 76, 7, 3),
	E_W_DOOR("E_W_DOOR", 6744, new RSTile(3562, 9695, 0), 19, 133, 94, 3, 7),
	E_S_DOOR("E_S_DOOR", 6747, new RSTile(3568, 9688, 0), 22, 150, 117, 7, 3),

	//Bottom Row
	SW_N_DOOR("SW_N_DOOR", 6745, new RSTile(3535, 9684, 0), 20, 39, 132, 7, 3),
	SW_W_DOOR("SW_W_DOOR", 6736, new RSTile(3528, 9678, 0), 11, 21, 149, 3, 7),
	SW_S_DOOR("SW_S_DOOR", 6750, new RSTile(3534, 9671, 0), 25, 38, 172, 7, 3),
	SW_E_DOOR("SW_E_DOOR", 6748, new RSTile(3541, 9677, 0), 23, 61, 149, 3, 7),

	S_N_DOOR("S_N_DOOR", 6746, new RSTile(3552, 9684, 0), 21, 94, 132, 7, 3),
	S_W_DOOR("S_W_DOOR", 6748, new RSTile(3545, 9678, 0), 23, 76, 149, 3, 7),
	S_E_DOOR("S_E_DOOR", 6749, new RSTile(3558, 9677, 0), 24, 117, 149, 3, 7),
	
	SE_N_DOOR("SE_N_DOOR", 6747, new RSTile(3569, 9684, 0), 22, 150, 132, 7, 3),
	SE_W_DOOR("SE_W_DOOR", 6749, new RSTile(3562, 9678, 0), 24, 132, 149, 3, 7),
	SE_S_DOOR("SE_S_DOOR", 6750, new RSTile(3568, 9671, 0), 25, 150, 173, 7, 3),
	SE_E_DOOR("SE_E_DOOR", 6742, new RSTile(3575, 9677, 0), 17, 173, 150, 3, 7);

	private final String name;
	private final RSTile tile;
	private final int bitSetting,x,y,width,height;

	TunnelDoor(String name, int useless, RSTile tile, int bitSetting, int x, int y, int width, int height) {
		this.name = name;
		this.tile = tile;
		this.bitSetting = bitSetting;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public int getX() {
		return x;
	}
	
	public TunnelDoor getDoor() {
		return this;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void paint(Graphics g) {
		g.fillRect(getX(), getY(), getWidth(), getHeight());
	}
	
	public String getName() {
		return name;
	}

	public RSTile getLocation() {
		return tile;
	}

	public int getBitSetting() {
		return bitSetting;
	}

	public boolean isOpenable() {
		int bitmask = 0x1;
		int val = Game.getSetting(452);
		int shifted = val >> getBitSetting();
		return (shifted & bitmask) == 0;
	}

	public static TunnelDoor getDoorAt(RSTile t) {
		if (t == null)
			return null;
		for (TunnelDoor d : TunnelDoor.values()) {
			if (d.getLocation().equals(t))
				return d;
		}
		return null;
	}

	public static boolean isOpenable(RSObject c) {
		if (c == null)
			return false;

		TunnelDoor cp = getDoorAt(c.getPosition());
		if (cp != null && cp.isOpenable())
			return true;
		return false;
	}

	public TunnelRoom getOtherRoom(TunnelRoom currentRoom) {
		for (TunnelRoom r : Rooms.TunnelRoom.values()) {
			if (!r.equals(currentRoom)) {
				for (TunnelDoor d : r.getConnectedDoors()) {
					if (d.equals(getDoor())) {
						return r;
					}
				}
			}
		}
		return null;
	}
}
