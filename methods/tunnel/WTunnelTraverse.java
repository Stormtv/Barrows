package scripts.Barrows.methods.tunnel;

import java.util.ArrayList;
import java.util.Random;

import scripts.Barrows.methods.tunnel.Rooms.TunnelRoom;

public class WTunnelTraverse {
	
	public static TunnelDoor[] pathToChest() {
		ArrayList<TunnelDoor> savedDoors = new ArrayList<TunnelDoor>();
		for (int i = 0; i<20; i++) {
			boolean deadEnd = true;
			TunnelRoom currentRoom = TunnelRoom.NC;
			ArrayList<TunnelDoor> visitedDoors = new ArrayList<TunnelDoor>();
			ArrayList<TunnelRoom> visitedRooms = new ArrayList<TunnelRoom>();
			ArrayList<TunnelRoom> roomPath = new ArrayList<TunnelRoom>();
			visitedRooms.add(currentRoom);
			while (!currentRoom.equals(TunnelRoom.CC)) {
				deadEnd = true;
				for (TunnelDoor d : shuffleArray(currentRoom.getOpenDoors())) {
					TunnelRoom room = d.getOtherRoom(currentRoom);
					if (!visitedRooms.contains(room)) {
						visitedRooms.add(room);
						currentRoom = room;
						roomPath.add(room);
						visitedDoors.add(d);
						deadEnd = false;
						break;
					}
				}
				if (deadEnd) {
					currentRoom = roomPath.get(roomPath.size() - 2);
					roomPath.remove(roomPath.size()-1);
					visitedDoors.remove(visitedDoors.size()-1);
				}
			}
			if (savedDoors.size() > visitedDoors.size() || savedDoors.size() == 0) {
				savedDoors = visitedDoors;
			}
		}
		return savedDoors.toArray(new TunnelDoor[savedDoors.size()]);
	}
	
	//Fisher–Yates shuffle (modified slightly)
	private static TunnelDoor[] shuffleArray(TunnelDoor[] ar) {
		Random rnd = new Random();
	    for (int i = ar.length - 1; i > 0; i--) {
	    	int index = rnd.nextInt(i + 1);
	    	// Simple swap
	    	TunnelDoor a = ar[index];
	    	ar[index] = ar[i];
	    	ar[i] = a;
	      }
	    return ar;
	  }
}
