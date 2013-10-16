package scripts.Barrows.util;

import java.awt.Color;
import java.awt.Point;

import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.colour.Tolerance;
import org.tribot.api2007.Banking;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Screen;
import org.tribot.api2007.types.RSInterfaceChild;

public class BankClass {

	public static void withdraw(int count, int[] id) {
		int best = 0;
		int counter = 0;
		for (int i : id) {
			if (Banking.find(i).length > 0
					&& Banking.find(i)[0].getStack() > count) {
				best = i;
				counter = Banking.find(i)[0].getStack();
			}
		}
		withdraw(count, best);
	}

	public static void withdraw(int count, int id) {
		if (getScrollY() > 235)
			Mouse.click(490, 80, 1);
		int itemCount = Inventory.getCount(id);

		if (Banking.find(id).length > 0) {
			int index = -1;
			index = Banking.find(id)[0].getIndex();
			while (itemCount == Inventory.getCount(id)
					&& Banking.find(id).length > 0) {
				int section = (int) Math.floor((index) / 8.0 / 6.0);
				General.println("index = " + index + "  " + "section = "
						+ section + "   " + "name = "
						+ Banking.find(id)[0].getDefinition().getName());

				scroll(section);

				int x = (int) Banking.getAll()[index].getArea().getCenterX();
				int y = (int) Banking.getAll()[index].getArea().getCenterY();
				if (count == 1) {
					Mouse.click(getCenter(x, y), 1);
				} else if (count == 5) {
					if (ChooseOption.isOpen()
							&& ChooseOption.isOptionValid(""
									+ Banking.getAll()[index].getDefinition()
											.getName())) {
						ChooseOption.select("-5");
					} else {
						Mouse.move(getCenter(x, y));
						General.sleep(1000);
						Mouse.click(3);
					}
				} else if (count == 10) {
					if (ChooseOption.isOpen()
							&& ChooseOption.isOptionValid(""
									+ Banking.getAll()[index].getDefinition()
											.getName())) {
						ChooseOption.select("-10");
						General.sleep(1000);
					} else {
						Mouse.move(getCenter(x, y));
						General.sleep(1000);
						Mouse.click(3);
					}
				} else if (count == 0) {
					if (ChooseOption.isOpen()
							&& ChooseOption.isOptionValid(""
									+ Banking.getAll()[index].getDefinition()
											.getName())) {
						ChooseOption.select("-All");
						General.sleep(1000);
					} else {
						Mouse.move(getCenter(x, y));
						General.sleep(1000);
						Mouse.click(3);
					}
				} else {
					if (isAmountInterfaceUp()) {
						Keyboard.typeSend("" + count);
						General.sleep(1000);
					} else {
						if (ChooseOption.isOpen()
								&& ChooseOption.isOptionValid(""
										+ Banking.getAll()[index]
												.getDefinition().getName())) {
							ChooseOption.select("-X");
							General.sleep(1000);
						} else {
							Mouse.move(getCenter(x, y));
							General.sleep(1000);
							Mouse.click(3);
						}
					}
				}
				General.sleep(1500, 2000);
			}
		}
	}

	public static void scroll(int section) {
		if (Interfaces.get(12, 7).getChild(1) != null) {
			int yPos = (section * 22) + 75;
			while (getScrollY() != yPos) {
				if (getScrollY() > (yPos + 50) || getScrollY() < (yPos - 50))
					Mouse.click(490, yPos + (General.random(-10, 10)), 1);
				if (Interfaces.get(12, 7).getChild(1).getAbsoluteBounds()
						.getY() > yPos)
					Mouse.click(490, 65, 1);
				else
					Mouse.click(490, 275, 1);

			}
		}
	}

	static Point getCenter(int x, int y) {
		return new Point(x + General.random(-1, 1), y + General.random(-1, 1));
	}

	static int getScrollY() {
		if (Interfaces.get(12, 7).getChild(1) != null)
			return (int) Interfaces.get(12, 7).getChild(1).getAbsoluteBounds()
					.getY();
		return 0;
	}

	private static boolean isAmountInterfaceUp() {
		RSInterfaceChild amount = Interfaces.get(548, 94);
		if (amount != null && !amount.isHidden()) {
			String txt = amount.getText();
			if (txt != null && txt.equals("*")) {
				Color c = Screen.getColorAt(259, 428);
				Color b = new Color(0, 0, 128);
				return org.tribot.api.Screen.coloursMatch(b, c, new Tolerance(
						10));
			}
		}
		return false;
	}

}
