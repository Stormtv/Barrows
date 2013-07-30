package scripts.Barrows.types;

import scripts.Barrows.util.RSArea;
import scripts.Barrows.types.enums.Prayer;

public class Brother {
	 
    public Brother(RSArea dig, String s) {
    	digArea = dig;
    	name = s;
    }

    public int killOrder;
    public String name;
    public boolean killed;
    public boolean isTunnel;
    public RSArea digArea;
    public int[] equipmentId;
	public Prayer.Prayers prayer;
}