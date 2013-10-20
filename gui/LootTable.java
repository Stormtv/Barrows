package scripts.Barrows.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.tribot.api2007.types.RSItem;

import scripts.Barrows.methods.GeneralMethods;

public class LootTable extends JFrame {

	private static final long serialVersionUID = 1L;

	private static JPanel contentPane;
	static boolean hide = false;

	static int l = 1;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		
	}

	static ArrayList<Integer> ids = new ArrayList<Integer>();
	static ArrayList<String> names = new ArrayList<String>();
	static ArrayList<Integer> ammounts = new ArrayList<Integer>();
	static ArrayList<Integer> prices = new ArrayList<Integer>();

	static JList idslist;
	static JList nameslist;
	static JList ammslist;
	static JList pricesList;

	final static DefaultListModel model = new DefaultListModel();
	final static DefaultListModel model3 = new DefaultListModel();
	final static DefaultListModel model2 = new DefaultListModel();
	final static DefaultListModel model4 = new DefaultListModel();
	private final JLabel lblNewLabel;
	private final JPanel panel_1;

	/**
	 * Create the frame.
	 * 
	 * @throws MalformedURLException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LootTable() throws MalformedURLException {
		addReward("Item1", 4718, 2, 55);
		addReward("Item2", 4720, 1, 525);
		addReward("Item1", 4718, 1, 55);
		addReward("Item3", 60, 18, 5);
		setTitle("Reward Table");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				setVisible(false);
			}
		});
		setBounds(100, 100, 367, 549);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(17, 17, 17));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Hide Loot Table");
		lblNewLabel_1.setFont(new Font("SansSerif", Font.ITALIC, 21));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(108, 460, 156, 53);
		contentPane.add(lblNewLabel_1);

		panel_1 = new JPanel();
		panel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
			}
		});
		panel_1.setBackground(Color.BLACK);
		panel_1.setBorder(new LineBorder(Color.WHITE, 1, false));
		panel_1.setBounds(91, 470, 183, 33);
		contentPane.add(panel_1);

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBounds(0, 460, 351, 53);
		contentPane.add(panel);

		idslist = new JList(model);
		idslist.setForeground(Color.WHITE);
		idslist.setBorder(null);
		idslist.setBounds(143, 97, 50, 352);
		idslist.setBackground(new Color(49, 49, 49));
		contentPane.add(idslist);

		ammslist = new JList(model3);
		ammslist.setForeground(Color.WHITE);
		ammslist.setBorder(null);
		ammslist.setBounds(196, 97, 64, 352);
		ammslist.setBackground(new Color(49, 49, 49));
		contentPane.add(ammslist);

		nameslist = new JList(model2);
		nameslist.setForeground(Color.WHITE);
		nameslist.setBounds(0, 97, 140, 352);
		nameslist.setBorder(null);
		nameslist.setBackground(new Color(49, 49, 49));
		contentPane.add(nameslist);

		pricesList = new JList(model4);
		pricesList.setForeground(Color.WHITE);
		pricesList.setBounds(263, 97, 88, 352);
		pricesList.setBorder(null);
		pricesList.setBackground(new Color(49, 49, 49));
		contentPane.add(pricesList);

		java.net.URL where = new URL("http://i.imgur.com/0wC7NoV.png");
		ImageIcon icon = new ImageIcon(where);
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(icon);
		lblNewLabel.setBounds(0, 0, 351, 488);
		// contentPane.add(lblNewLabel);

		updateLists();
	}

	@SuppressWarnings("unchecked")
	static void updatePrices() {
		if (model4 != null)
			model4.removeAllElements();
		for (int i : prices) {
			model4.addElement("" + i);
		}
		if (pricesList != null) {
			pricesList.setModel(model4);
		}
	}

	static ArrayList<Integer> indexes = new ArrayList<Integer>();

	@SuppressWarnings("unchecked")
	static void updateIDs() {
		indexes.clear();
		if (model != null)
			model.removeAllElements();
		for (int i : ids) {
			model.addElement("" + i);

		}
		if (idslist != null) {
			idslist.setModel(model);
		}
	}

	@SuppressWarnings("unchecked")
	static void updateAmmounts() {
		if (model3 != null)
			model3.removeAllElements();
		for (int i : ammounts) {
			model3.addElement(i);
		}
		if (ammslist != null) {
			ammslist.setModel(model3);

		}
	}

	@SuppressWarnings("unchecked")
	static void updateNames() {
		if (model2 != null)
			model2.removeAllElements();
		for (String s : names) {
			model2.addElement(s);
		}
		if (nameslist != null) {
			nameslist.setModel(model2);
		}
	}

	static void updateLists() {
		updateIDs();
		updateNames();
		updateAmmounts();
		updatePrices();
	}

	static int getIndex(ArrayList a, Object b) {
		if (a.contains(b)) {
			return a.indexOf(b);
		}
		return -1;
	}

	public static void addReward(String name, int id, int ammount, int price) {
		if (names.size() > 20 && ids.size() > 0 && ammounts.size() > 0
				&& prices.size() > 0) {
			names.remove(0);
			ids.remove(0);
			ammounts.remove(0);
			prices.remove(0);
			updateLists();
		} else {
			if (ids.contains(id)) {
				int index = getIndex(ids, id);
				ammounts.set(index, ammounts.get(index) + ammount);
			} else {
				names.add(name);
				ids.add(id);
				ammounts.add(ammount);
				prices.add(price);
			}
			updateLists();
		}
	}

	public static void addReward(RSItem loot) {
		try {
			String name = loot.getDefinition().getName();
			int price = GeneralMethods.getPrice(loot.getID());
			addReward(name, loot.getID(), loot.getStack(), price);
		} catch (Exception e) {

		}
	}
	
	public static void addReward(RSItem loot, int stack) {
		try {
			String name = loot.getDefinition().getName();
			int price = GeneralMethods.getPrice(loot.getID());
			addReward(name, loot.getID(), stack, price);
		} catch (Exception e) {

		}
	}
}
