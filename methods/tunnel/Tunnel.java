package scripts.Barrows.methods.tunnel;

import scripts.Barrows.main.BrotherKilling;
import scripts.Barrows.types.Brother.Brothers;

public class Tunnel {

	public static void goToTunnel() {
		for (Brothers b:Brothers.values()) {
			if (b.isTunnel()) {
				BrotherKilling.goToCrypt(b);
				if (BrotherKilling.isInCrypt(b)) {
					//TODO Enter Tunnels
				}
			}
		}
	}

}
