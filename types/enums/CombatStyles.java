package scripts.Barrows.types.enums;

import org.tribot.api2007.Combat;

public class CombatStyles {
	public enum Style {
		Ignore(""),
		Accurate("Accurate");
		private final String style;
		private Style(String style) {
			this.style = style;
		}
		
		public String getStringStyle() {
			return style;
		}
	
		public Style getStyle() {
			for (String s : Combat.getAvailableStyles()) {
				for (Style a : Style.values()) {
					if (a.getStringStyle().equalsIgnoreCase(s)) {
						return a;
					}
				}
			}
			return null;
		}
		
		
	}
}
