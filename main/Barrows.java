package scripts.Barrows.main;

import org.tribot.script.Script;

import scripts.Barrows.types.Var;

public class Barrows extends Script {

	@Override
	public void run() {
		activateGUI();
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
		// TODO Auto-generated method stub
		
	}

	private void activateGUI() {
		// TODO Auto-generated method stub
		
	}
	
}
