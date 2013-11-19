package scripts.Barrows.methods.tunnel;

import java.util.ArrayList;

import org.tribot.api2007.Objects;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.types.RSTile;

public class Rooms {
	public enum TunnelRoom {
		// TOP ROW
		NW(new TunnelDoor[] { TunnelDoor.NW_W_DOOR, TunnelDoor.NW_N_DOOR,
				TunnelDoor.NW_E_DOOR, TunnelDoor.NW_S_DOOR }, new RSTile(3534,9712,0)), NW_CORRIDOR(
				new TunnelDoor[] { TunnelDoor.NW_E_DOOR, TunnelDoor.N_W_DOOR },null), NC(
				new TunnelDoor[] { TunnelDoor.N_W_DOOR, TunnelDoor.N_S_DOOR,
						TunnelDoor.N_E_DOOR }, null), NE_CORRIDOR(new TunnelDoor[] {
				TunnelDoor.N_E_DOOR, TunnelDoor.NE_W_DOOR },null), NE(
				new TunnelDoor[] { TunnelDoor.NE_E_DOOR, TunnelDoor.NE_N_DOOR,
						TunnelDoor.NE_S_DOOR, TunnelDoor.NE_W_DOOR }, new RSTile(3568,9712,0)), NW_S_CORRIDOR(
				new TunnelDoor[] { TunnelDoor.NW_S_DOOR, TunnelDoor.W_N_DOOR },null), NC_CORRIDOR(
				new TunnelDoor[] { TunnelDoor.N_S_DOOR, TunnelDoor.C_N_DOOR },null), NE_S_CORRIDOR(
				new TunnelDoor[] { TunnelDoor.NE_S_DOOR, TunnelDoor.E_N_DOOR },null),

		// Center Row
		CW(new TunnelDoor[] { TunnelDoor.W_N_DOOR, TunnelDoor.W_E_DOOR,
				TunnelDoor.W_S_DOOR }, null), W_E_CORRIDOR(new TunnelDoor[] {
				TunnelDoor.W_E_DOOR, TunnelDoor.C_W_DOOR }, null), W_S_CORRIDOR(
				new TunnelDoor[] { TunnelDoor.W_S_DOOR, TunnelDoor.SW_N_DOOR }, null), CC(
				new TunnelDoor[] { TunnelDoor.C_E_DOOR, TunnelDoor.C_N_DOOR,
						TunnelDoor.C_W_DOOR, TunnelDoor.C_S_DOOR }, null), C_E_CORRIDOR(
				new TunnelDoor[] { TunnelDoor.C_E_DOOR, TunnelDoor.E_W_DOOR }, null), C_S_CORRIDOR(
				new TunnelDoor[] { TunnelDoor.C_S_DOOR, TunnelDoor.S_N_DOOR }, null), CE(
				new TunnelDoor[] { TunnelDoor.E_N_DOOR, TunnelDoor.E_S_DOOR,
						TunnelDoor.E_W_DOOR }, null), E_S_CORRIDOR(new TunnelDoor[] {
				TunnelDoor.E_S_DOOR, TunnelDoor.SE_N_DOOR }, null),

		// Bottom Row
		SW(new TunnelDoor[] { TunnelDoor.SW_E_DOOR, TunnelDoor.SW_N_DOOR,
				TunnelDoor.SW_S_DOOR, TunnelDoor.SW_W_DOOR }, new RSTile(3534,9678,0)), SW_E_CORRIDOR(
				new TunnelDoor[] { TunnelDoor.SW_E_DOOR, TunnelDoor.S_W_DOOR }, null), SC(
				new TunnelDoor[] { TunnelDoor.S_E_DOOR, TunnelDoor.S_N_DOOR,
						TunnelDoor.S_W_DOOR }, null), SE_W_CORRIDOR(new TunnelDoor[] {
				TunnelDoor.S_E_DOOR, TunnelDoor.SE_W_DOOR }, null), SE(
				new TunnelDoor[] { TunnelDoor.SE_E_DOOR, TunnelDoor.SE_N_DOOR,
						TunnelDoor.SE_S_DOOR, TunnelDoor.SE_W_DOOR }, new RSTile (3568,9678,0)),

		// Tunnels
		WTunnel(new TunnelDoor[] { TunnelDoor.NW_W_DOOR, TunnelDoor.SW_W_DOOR }, null), NTunnel(
				new TunnelDoor[] { TunnelDoor.NW_N_DOOR, TunnelDoor.NE_N_DOOR }, null), STunnel(
				new TunnelDoor[] { TunnelDoor.SW_S_DOOR, TunnelDoor.SE_S_DOOR }, null), ETunnel(
				new TunnelDoor[] { TunnelDoor.NE_E_DOOR, TunnelDoor.SE_E_DOOR }, null);

		private final TunnelDoor[] connectedDoors;
		private final RSTile exitTile;
		TunnelRoom(TunnelDoor[] connectedDoors, RSTile exitTile) {
			this.connectedDoors = connectedDoors;
			this.exitTile = exitTile;
		}

		public TunnelDoor[] getConnectedDoors() {
			return connectedDoors;
		}
		
		public RSTile getExitTile() {
			return exitTile;
		}

		public TunnelDoor[] getOpenDoors() {
			ArrayList<TunnelDoor> doors = new ArrayList<TunnelDoor>();
			for (TunnelDoor d : getConnectedDoors()) {
				if (d.isOpenable()) {
					doors.add(d);
				}
			}
			return doors.toArray(new TunnelDoor[doors.size()]);
		}
	}

	public static TunnelRoom getRoom() {
		for (TunnelRoom r : TunnelRoom.values()) {
			int i = 0;
			for (TunnelDoor d : r.getConnectedDoors()) {
				if (PathFinding.canReach(d.getLocation(), true)) {
					i++;
				}
			}
			if (r.getConnectedDoors().length == i
					&& Objects.find(15, "Sarcophagus").length == 0) {
				return r;
			}
		}
		return null;
	}

	public static boolean InTunnel() {
		TunnelRoom r = getRoom();
		return r != null && r.toString() != null && r.toString().length() > 0 && r.toString().contains("unne");
	}
}