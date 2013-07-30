package scripts.Barrows.main;

import javax.swing.SwingUtilities;

import org.tribot.script.Script;

import scripts.Barrows.gui.BarrowGUI;
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
        Var.ahrim = new Brother(Var.ahrimDig, "Ahrim");
        Var.torag = new Brother(Var.toragDig, "Torag");
        Var.verac = new Brother(Var.veracDig, "Verac");
        Var.guthan = new Brother(Var.guthanDig, "Guthan");
        Var.karil = new Brother(Var.karilDig, "Karil");
        Var.dharok = new Brother(Var.dharokDig, "Dharok");
        activateGUI();
	}

	private void activateGUI() {
		SwingUtilities.invokeLater(new Runnable() 
		{
			public void run() 
			{ 
				Var.gui = new BarrowGUI();
				Var.gui.setVisible(true);
			}
		});
		while (Var.guiWait) {
			sleep(100);
		}
	}
	
}
