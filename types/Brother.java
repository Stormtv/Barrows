package scripts.Barrows.types;

import scripts.Barrows.util.RSArea;

public class Brother {
	 
    public Brother(String string, int i) {
            name = string;
            killOrder = i;
    }

    public int killOrder;
    public String name;
    public boolean killed;
    public RSArea digArea;
    public int spade;

}