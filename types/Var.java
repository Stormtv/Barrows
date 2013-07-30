package scripts.Barrows.types;

import org.tribot.api2007.types.RSTile;

import scripts.Barrows.util.RSArea;

public class Var {

	public static final RSArea veracDig = new RSArea(new RSTile[] {
			new RSTile(3553, 3301, 0), new RSTile(3553, 3294, 0),
			new RSTile(3561, 3294, 0), new RSTile(3561, 3301, 0) });

	public static final RSArea dharokDig = new RSArea(new RSTile[] {
			new RSTile(3570, 3300, 0), new RSTile(3578, 3300, 0),
			new RSTile(3578, 3293, 0), new RSTile(3570, 3293, 0) });

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
	
	public static Brother curBrother;

	public static boolean running = true;

}
