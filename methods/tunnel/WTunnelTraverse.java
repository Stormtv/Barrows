package scripts.Barrows.methods.tunnel;

import java.util.ArrayList;
import java.util.Random;

import org.tribot.api.General;

import scripts.Barrows.methods.tunnel.Rooms.TunnelRoom;

public class WTunnelTraverse {

	public static TunnelDoor[] pathToChest() {
		return doorPathToChest(TunnelRoom.CC);
	}

	public static TunnelDoor[] doorPathToChest(TunnelRoom endRoom) {
		try{
			ArrayList<TunnelDoor> savedDoors = new ArrayList<TunnelDoor>();
			//ArrayList<TunnelRoom> savedRooms = new ArrayList<TunnelRoom>();
			for (int i = 0; i < 20; i++) {
				boolean deadEnd = true;
				TunnelRoom currentRoom = Rooms.getRoom();
				if (currentRoom == null) {
					General.println("currentRoom is null make sure you are in the tunnel");
					return null;
				}
				ArrayList<TunnelDoor> visitedDoors = new ArrayList<TunnelDoor>();
				ArrayList<TunnelRoom> visitedRooms = new ArrayList<TunnelRoom>();
				ArrayList<TunnelRoom> roomPath = new ArrayList<TunnelRoom>();
				visitedRooms.add(currentRoom);
				roomPath.add(currentRoom);
				while (currentRoom != null && !currentRoom.equals(endRoom)) {
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
						roomPath.remove(roomPath.size() - 1);
						currentRoom = roomPath.get(roomPath.size() - 1);
						visitedDoors.remove(visitedDoors.size() - 1);
					}
				}
				if (savedDoors.size() > visitedDoors.size()
						|| savedDoors.size() == 0) {
					savedDoors = visitedDoors;
					//savedRooms = roomPath;
				}
			}
			return savedDoors.toArray(new TunnelDoor[savedDoors.size()]);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static TunnelRoom[] roomPathToChest(TunnelRoom endRoom) {
		try{
			ArrayList<TunnelDoor> savedDoors = new ArrayList<TunnelDoor>();
			ArrayList<TunnelRoom> savedRooms = new ArrayList<TunnelRoom>();
			for (int i = 0; i < 20; i++) {
				boolean deadEnd = true;
				TunnelRoom currentRoom = Rooms.getRoom();
				if (currentRoom == null) {
					General.println("currentRoom is null make sure you are in the tunnel");
					return null;
				}
				ArrayList<TunnelDoor> visitedDoors = new ArrayList<TunnelDoor>();
				ArrayList<TunnelRoom> visitedRooms = new ArrayList<TunnelRoom>();
				ArrayList<TunnelRoom> roomPath = new ArrayList<TunnelRoom>();
				visitedRooms.add(currentRoom);
				roomPath.add(currentRoom);
				while (currentRoom != null && !currentRoom.equals(endRoom)) {
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
						roomPath.remove(roomPath.size() - 1);
						currentRoom = roomPath.get(roomPath.size() - 1);
						visitedDoors.remove(visitedDoors.size() - 1);
					}
				}
				if (savedDoors.size() > visitedDoors.size()
						|| savedDoors.size() == 0) {
					savedDoors = visitedDoors;
					savedRooms = roomPath;
				}
			}
			return savedRooms.toArray(new TunnelRoom[savedRooms.size()]);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Fisher–Yates shuffle (modified slightly)
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
