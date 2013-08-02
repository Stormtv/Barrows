package scripts.Barrows.types;

import org.tribot.api2007.types.RSTile;

import scripts.Barrows.gui.BarrowGUI;
import scripts.Barrows.types.Brother.Brothers;
import scripts.Barrows.types.enums.Food;
import scripts.Barrows.util.RSArea;

public class Var {

	public static final RSArea veracDig = new RSArea(new RSTile[] {
			new RSTile(3553, 3301, 0), new RSTile(3553, 3294, 0),
			new RSTile(3561, 3294, 0), new RSTile(3561, 3301, 0) });

	public static final RSArea dharokDig = new RSArea(new RSTile[] {
			new RSTile(3570, 3300, 0), new RSTile(3578, 3300, 0),
			new RSTile(3578, 3293, 0), new RSTile(3570, 3293, 0) });

	public final static int SPADE_ID = 952;

	public static final RSArea ahrimDig = new RSArea(new RSTile[] {
			new RSTile(3560, 3292, 0), new RSTile(3560, 3284, 0),
			new RSTile(3570, 3284, 0), new RSTile(3570, 3292, 0) });

	public static final RSArea guthanDig = new RSArea(new RSTile[] {
			new RSTile(3570, 3283, 0), new RSTile(3579, 3283, 0),
			new RSTile(3579, 3276, 0), new RSTile(3570, 3276, 0) });

	public static final RSArea karilDig = new RSArea(new RSTile[] {
			new RSTile(3560, 3280, 0), new RSTile(3569, 3280, 0),
			new RSTile(3569, 3273, 0), new RSTile(3560, 3273, 0) });

	public static final RSArea toragDig = new RSArea(new RSTile[] {
			new RSTile(3549, 3286, 0), new RSTile(3558, 3286, 0),
			new RSTile(3558, 3278, 0), new RSTile(3549, 3278, 0) });

	public static final RSArea barrowsArea = new RSArea(new RSTile[] {
			new RSTile(3562, 3307, 0), new RSTile(3557, 3307, 0),
			new RSTile(3552, 3304, 0), new RSTile(3550, 3300, 0),
			new RSTile(3547, 3295, 0), new RSTile(3548, 3290, 0),
			new RSTile(3545, 3286, 0), new RSTile(3546, 3281, 0),
			new RSTile(3548, 3276, 0), new RSTile(3553, 3275, 0),
			new RSTile(3558, 3273, 0), new RSTile(3561, 3269, 0),
			new RSTile(3566, 3268, 0), new RSTile(3571, 3270, 0),
			new RSTile(3575, 3272, 0), new RSTile(3580, 3274, 0),
			new RSTile(3583, 3278, 0), new RSTile(3583, 3283, 0),
			new RSTile(3581, 3288, 0), new RSTile(3582, 3293, 0),
			new RSTile(3583, 3298, 0), new RSTile(3581, 3303, 0),
			new RSTile(3578, 3307, 0), new RSTile(3573, 3307, 0),
			new RSTile(3568, 3308, 0) });

	public static Brothers curBrother;

	public static boolean running = true;

	public static BarrowGUI gui;

	public static boolean guiWait = true;

	public static Food.Edibles food;
	
	public static int superAttack,superStrength,superDefence,rangingPotion,prayerPotion, foodAmount;

}
