package scripts.Barrows.methods.tunnel;

import java.util.ArrayList;

import org.tribot.api2007.PathFinding;

public enum TunnelRoom {
	//TOP ROW
	NW(new TunnelDoor[] {TunnelDoor.NW_W_DOOR,
			TunnelDoor.NW_N_DOOR,
			TunnelDoor.NW_E_DOOR,
			TunnelDoor.NW_S_DOOR}),
	NW_CORRIDOR(new TunnelDoor[] {TunnelDoor.NW_E_DOOR,
			TunnelDoor.N_W_DOOR}),
	NC(new TunnelDoor[] {TunnelDoor.N_W_DOOR,
			TunnelDoor.N_S_DOOR,
			TunnelDoor.N_E_DOOR}),
	NE_CORRIDOR(new TunnelDoor[] {TunnelDoor.N_E_DOOR,
			TunnelDoor.NE_W_DOOR}),
	NE(new TunnelDoor[] {TunnelDoor.NE_E_DOOR,
			TunnelDoor.NE_N_DOOR,
			TunnelDoor.NE_S_DOOR,
			TunnelDoor.NE_W_DOOR}),
	NW_S_CORRIDOR(new TunnelDoor[]{TunnelDoor.NW_S_DOOR,
			TunnelDoor.W_N_DOOR}),
	NC_CORRIDOR(new TunnelDoor[]{TunnelDoor.N_S_DOOR,
			TunnelDoor.C_N_DOOR}),
	NE_S_CORRIDOR(new TunnelDoor[]{TunnelDoor.NE_S_DOOR,
			TunnelDoor.E_N_DOOR}),

	//Center Row
 	CW(new TunnelDoor[]{TunnelDoor.W_N_DOOR,
 			TunnelDoor.W_E_DOOR,
 			TunnelDoor.W_S_DOOR}),
 	W_E_CORRIDOR(new TunnelDoor[]{TunnelDoor.W_E_DOOR,
 			TunnelDoor.C_W_DOOR}),
 	W_S_CORRIDOR(new TunnelDoor[]{TunnelDoor.W_S_DOOR,
 			TunnelDoor.SW_N_DOOR}),
	CC(new TunnelDoor[]{TunnelDoor.C_E_DOOR,
			TunnelDoor.C_N_DOOR,
			TunnelDoor.C_W_DOOR,
			TunnelDoor.C_S_DOOR}),
	C_E_CORRIDOR(new TunnelDoor[]{TunnelDoor.C_E_DOOR,
			TunnelDoor.E_W_DOOR}),
	C_S_CORRIDOR(new TunnelDoor[]{TunnelDoor.C_S_DOOR,
			TunnelDoor.S_N_DOOR}),
	CE(new TunnelDoor[]{TunnelDoor.E_N_DOOR,
			TunnelDoor.E_S_DOOR,
			TunnelDoor.E_W_DOOR}),
	E_S_CORRIDOR(new TunnelDoor[]{TunnelDoor.E_S_DOOR,
			TunnelDoor.SE_N_DOOR}),
			
	//Bottom Row
	SW(new TunnelDoor[]{TunnelDoor.SW_E_DOOR,
			TunnelDoor.SW_N_DOOR,
			TunnelDoor.SW_S_DOOR,
			TunnelDoor.SW_W_DOOR}),
	SW_E_CORRIDOR(new TunnelDoor[]{TunnelDoor.SW_E_DOOR,
			TunnelDoor.S_W_DOOR}),
	SC(new TunnelDoor[]{TunnelDoor.S_E_DOOR,
			TunnelDoor.S_N_DOOR,
			TunnelDoor.S_W_DOOR}),
	SE_W_CORRIDOR(new TunnelDoor[]{TunnelDoor.SE_E_DOOR,
			TunnelDoor.SE_W_DOOR}),
	SE(new TunnelDoor[]{TunnelDoor.SE_E_DOOR,
			TunnelDoor.SE_N_DOOR,
			TunnelDoor.SE_S_DOOR,
			TunnelDoor.SE_W_DOOR}),
			
	//Tunnels
	WTunnel(new TunnelDoor[]{TunnelDoor.NW_W_DOOR,
			TunnelDoor.SW_W_DOOR}),
	NTunnel(new TunnelDoor[]{TunnelDoor.NW_N_DOOR,
			TunnelDoor.NE_N_DOOR}),
	STunnel(new TunnelDoor[]{TunnelDoor.SW_S_DOOR,
			TunnelDoor.SE_S_DOOR}),
	ETunnel(new TunnelDoor[]{TunnelDoor.NE_E_DOOR,
			TunnelDoor.SE_E_DOOR});
	
	
	private final TunnelDoor[] connectedDoors;
	
	TunnelRoom(TunnelDoor[] connectedDoors) {
		this.connectedDoors = connectedDoors;
	}
	
	public TunnelDoor[] getConnectedDoors() {
		return connectedDoors;
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
	
	public TunnelRoom getRoom() {
		for (TunnelRoom r : TunnelRoom.values()) {
			int i = 0;
			for (TunnelDoor d : r.getConnectedDoors()) {
				if (PathFinding.canReach(d.getLocation(), true)) {
					i++;
				}
			}
			if (r.getConnectedDoors().length == i) {
				return r;
			}
		}
		return null;
	}
}
