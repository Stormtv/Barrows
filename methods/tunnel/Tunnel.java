package scripts.Barrows.methods.tunnel;

import org.tribot.api.General;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSObject;

import scripts.Barrows.main.BrotherKilling;
import scripts.Barrows.methods.GeneralMethods;
import scripts.Barrows.types.Brother.Brothers;
import scripts.Barrows.types.Var;

public class Tunnel {

	public static void goToTunnel() {
		for (Brothers b:Brothers.values()) {
			if (b.isTunnel()) {
				BrotherKilling.goToCrypt(b);
				if (BrotherKilling.isInCrypt(b)) {
					RSObject[] sarco = Objects.find(20, b.getCryptID());
					GeneralMethods.clickObject(sarco[0], "Search", false);
					for (int fail=0; fail < 20 && Interfaces.get(210, 1) ==null;fail++) {
						General.sleep(10,21);
					}
					if (Interfaces.get(210,1)!=null) {
						Interfaces.get(210,1).click("Continue");
					}
					for (int fail=0; fail < 20 && Interfaces.get(228, 1) ==null;fail++) {
						General.sleep(10,21);
					}
					if (Interfaces.get(228,1)!=null) {
						Interfaces.get(228,1).click("Continue");
					}
					for (int fail=0; fail<40 && Rooms.getRoom()==null;fail++){
						General.sleep(10,21);
					}
					if (Rooms.getRoom()!=null){
						Var.startingRoom = Rooms.getRoom(); 
					}
				}
			}
		}
	}
	
	public static boolean inCrypt() {
		return Objects.find(20, "Sarcophagus").length>0;
	}

}
