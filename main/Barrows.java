package scripts.Barrows.main;

import org.tribot.script.Script;

import scripts.Barrows.types.Brother;
import scripts.Barrows.types.Var;

public class Barrows extends Script {

	@Override
	public void run() {
		onStart();		
		while (Var.running) {
			sleep(loop());
		}
	}

	private int loop() {
		// TODO Auto-generated method stub
		return 50;
	}

	private void onStart() {
        Var.ahrim = new Brother();
        Var.torag = new Brother();
        Var.verac = new Brother();
        Var.guthan = new Brother();
        Var.karil = new Brother();
        Var.dharok = new Brother();
        activateGUI();
	}

	private void activateGUI() {
		// TODO Auto-generated method stub
		
	}
	
}
