package scripts.Barrows.methods.tunnel;

import java.util.ArrayList;

import org.tribot.api.General;

import scripts.Barrows.methods.tunnel.Rooms.TunnelRoom;

public class WTunnelTraverse {
	
	public static ArrayList<TunnelDoor> visitedDoors = new ArrayList<TunnelDoor>();
	public static TunnelRoom currentRoom = TunnelRoom.SE;
	
	public static TunnelDoor[] pathToChest() {
		//This is how we will keep track of if we have tried to go that way
		ArrayList<TunnelRoom> visitedRooms = new ArrayList<TunnelRoom>();
		
		//This will be what we return in the end
		//ArrayList<TunnelDoor> visitedDoors = new ArrayList<TunnelDoor>();
		
		//This is for testing since I don't want to go to the tunnels (http://i.imgur.com/Cztt9KW.jpg)
		
		//This grabs the start room that your character is in
		//TunnelRoom currentRoom = Room.getRoom(); 
		
		//Adds first room we start in to the visited room arraylist
		visitedRooms.add(currentRoom);
		General.println("Current Room: "+currentRoom.toString());
		//While we are not in the chest room
		while (!currentRoom.equals(TunnelRoom.CC)) {
			//Loops through all the open doors in the current room
			for (TunnelDoor d : currentRoom.getOpenDoors()) {
				//Checks if we have not visited this room yet
				if (!visitedRooms.contains(d.getOtherRoom(currentRoom))) {
					//Adds the room we are going to
					visitedRooms.add(d.getOtherRoom(currentRoom));
					//Adds the door we had to go through to get to the room
					visitedDoors.add(d);
					//Found a unvisited room so exit the for
					break;
				}
			}
			//if the last visited room is the same as the current room after looking for a new room we found a dead end
			if (visitedRooms.get(visitedRooms.size()-1).equals(currentRoom)) {
				//Dead end so we will go back two rooms (one cooridor/tunnel)(one room)
				General.println("Dead end");
				currentRoom = visitedRooms.get(visitedRooms.size()-2);
				General.println("Current Room: "+currentRoom.toString());
				//Remove the last door
				visitedDoors.remove(visitedDoors.size()-1);
			} else {
				currentRoom = visitedRooms.get(visitedRooms.size()-1);
				General.println("Current Room: "+currentRoom.toString());
			}
			General.sleep(1000);
		}
		General.println("Chest Room Found!");
		//Hopefully we have reached the chest room so lets return what doors we took to get there!
		return visitedDoors.toArray(new TunnelDoor[visitedDoors.size()]);
	}
}
