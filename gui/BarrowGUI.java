package scripts.Barrows.gui;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import scripts.Barrows.methods.Pathing;
import scripts.Barrows.types.Brother;
import scripts.Barrows.types.Var;
import scripts.Barrows.types.enums.Equipment;
import scripts.Barrows.types.enums.Food;
import scripts.Barrows.types.enums.Magic;
import scripts.Barrows.types.enums.Magic.Spell;
import scripts.Barrows.types.enums.Prayer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JTabbedPane;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.tribot.api.General;

@SuppressWarnings("serial")
public class BarrowGUI extends JFrame {
	/**
	 * 
	 */
	private static JList<String> listSelected;
	private static DefaultListModel<String> modelSelected = new DefaultListModel<String>();
	private static JCheckBox DharokPrayer, DharokPotions, KarilPrayer, KarilPotions,
	VeracPrayer, VeracPotions, GuthanPrayer, GuthanPotions,
	ToragPrayer, ToragPotions, AhrimPrayer, AhrimPotions;
	private static JComboBox<Magic.Spell> cbxDharok, cbxKaril, cbxVerac, cbxGuthan, cbxTorag, cbxAhrim;
	private static JComboBox<Food.Edibles> cbxFood;
	private static JComboBox<Pathing.PathBarrows> cbxBarrows;
	private static JComboBox<Pathing.PathBank> cbxBank;
	private JTextField txtFood,txtSA,txtSS,txtSD,txtRP,txtPP,txtArrows,txtCasts;
	public BarrowGUI() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				try {
					GUISave.load();
				} catch (IOException e) {
					e.printStackTrace();
				}
				cbxDharok.setSelectedItem(Brother.Brothers.Dharok.getSpell());
				cbxKaril.setSelectedItem(Brother.Brothers.Karil.getSpell());
				cbxGuthan.setSelectedItem(Brother.Brothers.Guthan.getSpell());
				cbxAhrim.setSelectedItem(Brother.Brothers.Ahrim.getSpell());
				cbxVerac.setSelectedItem(Brother.Brothers.Verac.getSpell());
				cbxTorag.setSelectedItem(Brother.Brothers.Torag.getSpell());

				DharokPrayer.setSelected(!Brother.Brothers.Dharok.getPrayer().equals(Prayer.Prayers.None));
				KarilPrayer.setSelected(!Brother.Brothers.Karil.getPrayer().equals(Prayer.Prayers.None));
				GuthanPrayer.setSelected(!Brother.Brothers.Guthan.getPrayer().equals(Prayer.Prayers.None));
				AhrimPrayer.setSelected(!Brother.Brothers.Ahrim.getPrayer().equals(Prayer.Prayers.None));
				VeracPrayer.setSelected(!Brother.Brothers.Verac.getPrayer().equals(Prayer.Prayers.None));
				ToragPrayer.setSelected(!Brother.Brothers.Torag.getPrayer().equals(Prayer.Prayers.None));

				DharokPotions.setSelected(Brother.Brothers.Dharok.usePotions());
				KarilPotions.setSelected(Brother.Brothers.Karil.usePotions());
				GuthanPotions.setSelected(Brother.Brothers.Guthan.usePotions());
				AhrimPotions.setSelected(Brother.Brothers.Ahrim.usePotions());
				VeracPotions.setSelected(Brother.Brothers.Verac.usePotions());
				ToragPotions.setSelected(Brother.Brothers.Torag.usePotions());

				modelSelected.setSize(6);
				modelSelected.set(Brother.Brothers.Dharok.killOrder(), "Dharok");
				modelSelected.set(Brother.Brothers.Karil.killOrder(), "Karil");
				modelSelected.set(Brother.Brothers.Guthan.killOrder(), "Guthan");
				modelSelected.set(Brother.Brothers.Ahrim.killOrder(), "Ahrim");
				modelSelected.set(Brother.Brothers.Verac.killOrder(), "Verac");
				modelSelected.set(Brother.Brothers.Torag.killOrder(), "Torag");

				cbxFood.setSelectedItem(Var.food);

				txtFood.setText(Integer.toString(Var.foodAmount));
				txtSA.setText(Integer.toString(Var.superAttack));
				txtSS.setText(Integer.toString(Var.superStrength));
				txtSD.setText(Integer.toString(Var.superDefence));
				txtRP.setText(Integer.toString(Var.rangingPotion));
				txtPP.setText(Integer.toString(Var.prayerPotion));
				txtArrows.setText(Integer.toString(Var.arrowCount));
				txtCasts.setText(Integer.toString(Var.spellCount));
				
				cbxBarrows.setSelectedItem(Var.barrowsPath);
				cbxBank.setSelectedItem(Var.bankPath);
			}
		});
		if (modelSelected.isEmpty()) {
			General.println("Model was empty");
			modelSelected.addElement("Dharok");
			modelSelected.addElement("Karil");
			modelSelected.addElement("Verac");
			modelSelected.addElement("Guthan");
			modelSelected.addElement("Torag");
			modelSelected.addElement("Ahrim");
		}
		setTitle("Barrows");
		setBounds(100, 100, 779, 446);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Kill Order", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int index = 0; index < 6; index++) {
					String s = modelSelected.getElementAt(index);
					switch (s) {
						case "Dharok":
							Brother.Brothers.Dharok.setKillOrder(index);
						case "Karil":
							Brother.Brothers.Karil.setKillOrder(index);
						case "Verac":
							Brother.Brothers.Verac.setKillOrder(index);
						case "Guthan":
							Brother.Brothers.Guthan.setKillOrder(index);
						case "Torag":
							Brother.Brothers.Torag.setKillOrder(index);
						case "Ahrim":
							Brother.Brothers.Ahrim.setKillOrder(index);
					}
				}

				if (!DharokPrayer.isSelected()) {
					Brother.Brothers.Dharok.setPrayer(Prayer.Prayers.None);
				}
				if (!KarilPrayer.isSelected()) {
					Brother.Brothers.Karil.setPrayer(Prayer.Prayers.None);
				}
				if (!VeracPrayer.isSelected()) {
					Brother.Brothers.Verac.setPrayer(Prayer.Prayers.None);
				}
				if (!GuthanPrayer.isSelected()) {
					Brother.Brothers.Guthan.setPrayer(Prayer.Prayers.None);
				}
				if (!ToragPrayer.isSelected()) {
					Brother.Brothers.Torag.setPrayer(Prayer.Prayers.None);
				}
				if (!AhrimPrayer.isSelected()) {
					Brother.Brothers.Ahrim.setPrayer(Prayer.Prayers.None);
				}
				Brother.Brothers.Dharok.setUsePotions(DharokPotions.isSelected());
				Brother.Brothers.Karil.setUsePotions(KarilPotions.isSelected());
				Brother.Brothers.Verac.setUsePotions(VeracPotions.isSelected());
				Brother.Brothers.Guthan.setUsePotions(GuthanPotions.isSelected());
				Brother.Brothers.Torag.setUsePotions(ToragPotions.isSelected());
				Brother.Brothers.Ahrim.setUsePotions(AhrimPotions.isSelected());

				Brother.Brothers.Dharok.setSpell((Magic.Spell) cbxDharok.getSelectedItem());
				Brother.Brothers.Karil.setSpell((Magic.Spell) cbxKaril.getSelectedItem());
				Brother.Brothers.Verac.setSpell((Magic.Spell) cbxVerac.getSelectedItem());
				Brother.Brothers.Guthan.setSpell((Magic.Spell) cbxGuthan.getSelectedItem());
				Brother.Brothers.Torag.setSpell((Magic.Spell) cbxTorag.getSelectedItem());
				Brother.Brothers.Ahrim.setSpell((Magic.Spell) cbxAhrim.getSelectedItem());

				Var.food = (Food.Edibles) cbxFood.getSelectedItem();

				Var.foodAmount = Integer.parseInt(txtFood.getText());
				Var.superAttack = Integer.parseInt(txtSA.getText());
				Var.superStrength = Integer.parseInt(txtSS.getText());
				Var.superDefence = Integer.parseInt(txtSD.getText());
				Var.rangingPotion = Integer.parseInt(txtRP.getText());
				Var.prayerPotion = Integer.parseInt(txtPP.getText());
				Var.arrowCount = Integer.parseInt(txtArrows.getText());
				Var.spellCount = Integer.parseInt(txtCasts.getText());
				
				Var.bankPath = (Pathing.PathBank) cbxBank.getSelectedItem();
				Var.barrowsPath = (Pathing.PathBarrows) cbxBarrows.getSelectedItem();
				
				Var.arrowId = Equipment.getEquipmentID(Equipment.Gear.ARROW);
				try {
					GUISave.save();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				Var.guiWait = false;
				Var.gui.setVisible(false);
			}
		});

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Brother Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Pathing Options", TitledBorder.LEADING, TitledBorder.TOP, null, null), "Pathing Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Food", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Potion Withdraw Amounts", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Arrows / Runes", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(panel, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
									.addGap(18))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(btnStart, GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
								.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 405, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
								.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
							.addGap(13))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(595, Short.MAX_VALUE))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnStart, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addComponent(panel_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(20))
		);
		
		JLabel lblArrows = new JLabel("Arrows:");
		
		txtArrows = new JTextField();
		txtArrows.setText("0");
		txtArrows.setColumns(10);
		
		JLabel lblOfCasts = new JLabel("# of Casts:");
		
		txtCasts = new JTextField();
		txtCasts.setText("0");
		txtCasts.setColumns(10);
		GroupLayout gl_panel_5 = new GroupLayout(panel_5);
		gl_panel_5.setHorizontalGroup(
			gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblOfCasts, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblArrows, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING)
						.addComponent(txtArrows, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtCasts, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(40, Short.MAX_VALUE))
		);
		gl_panel_5.setVerticalGroup(
			gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblArrows)
						.addComponent(txtArrows, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING)
						.addComponent(txtCasts, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_5.createSequentialGroup()
							.addGap(3)
							.addComponent(lblOfCasts)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_5.setLayout(gl_panel_5);

		JLabel lblSuperAttack = new JLabel("Super Attack:");

		txtSA = new JTextField();
		txtSA.setText("0");
		txtSA.setColumns(10);

		JLabel lblSuperStrength = new JLabel("Super Strength:");

		txtSS = new JTextField();
		txtSS.setText("0");
		txtSS.setColumns(10);

		JLabel lblSuperDefence = new JLabel("Super Defence:");

		txtSD = new JTextField();
		txtSD.setText("0");
		txtSD.setColumns(10);

		JLabel lblRangingPotion = new JLabel("Ranging Potion:");

		txtRP = new JTextField();
		txtRP.setText("0");
		txtRP.setColumns(10);

		JLabel lblPrayerPotions = new JLabel("Prayer Potions:");

		txtPP = new JTextField();
		txtPP.setText("0");
		txtPP.setColumns(10);
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(lblSuperAttack, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(txtSA, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(lblSuperStrength, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(txtSS, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(lblSuperDefence, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(txtSD, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addComponent(lblPrayerPotions, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblRangingPotion, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addComponent(txtPP, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtRP, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSuperAttack)
						.addComponent(txtSA, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSuperStrength)
						.addComponent(txtSS, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSuperDefence)
						.addComponent(txtSD, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtRP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblRangingPotion))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPrayerPotions)
						.addComponent(txtPP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(15, Short.MAX_VALUE))
		);
		panel_4.setLayout(gl_panel_4);

		final DefaultComboBoxModel<Food.Edibles> FoodModel = new DefaultComboBoxModel<Food.Edibles>();
		for(Food.Edibles f : Food.Edibles.values()) {
				FoodModel.addElement(f);
		}

		cbxFood = new JComboBox<Food.Edibles>(FoodModel);

		JLabel label_6 = new JLabel("Select Your Food");

		JLabel label_7 = new JLabel("Withdraw amount:");

		txtFood = new JTextField();
		txtFood.setText("0");
		txtFood.setColumns(10);
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addComponent(label_6, GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
						.addGroup(gl_panel_3.createSequentialGroup()
							.addComponent(label_7, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtFood, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
						.addComponent(cbxFood, 0, 140, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_6)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cbxFood, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_7)
						.addComponent(txtFood, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_3.setLayout(gl_panel_3);

		JLabel lblToBarrows = new JLabel("To Barrows:");

		final DefaultComboBoxModel<Pathing.PathBarrows> BarrowModel = new DefaultComboBoxModel<Pathing.PathBarrows>();
		for (Pathing.PathBarrows s : Pathing.PathBarrows.values()) {
			BarrowModel.addElement(s);
		}

		cbxBarrows = new JComboBox<Pathing.PathBarrows>(BarrowModel);

		JLabel lblToBank = new JLabel("To Bank:");

		final DefaultComboBoxModel<Pathing.PathBank> BankModel = new DefaultComboBoxModel<Pathing.PathBank>();
		for (Pathing.PathBank s : Pathing.PathBank.values()) {
			BankModel.addElement(s);
		}

		cbxBank = new JComboBox<Pathing.PathBank>(BankModel);

		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(lblToBarrows)
						.addComponent(lblToBank, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(cbxBarrows, 0, 215, Short.MAX_VALUE)
						.addComponent(cbxBank, 0, 215, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblToBarrows)
						.addComponent(cbxBarrows, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblToBank)
						.addComponent(cbxBank, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(14, 14, Short.MAX_VALUE))
		);
		panel_2.setLayout(gl_panel_2);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 137, Short.MAX_VALUE)
					.addContainerGap())
		);

		JPanel panelDharok = new JPanel();
		tabbedPane.addTab("Dharok", null, panelDharok, null);

		JButton btnSetEquipment = new JButton("Set Equipment");
		btnSetEquipment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Brother.Brothers.Dharok.setEquipmentIds(Equipment.getEquipedItems());
			}
		});

		DharokPrayer = new JCheckBox("Use Prayer");
		DharokPrayer.setSelected(true);

		DharokPotions = new JCheckBox("Use Potions");

		final DefaultComboBoxModel<Magic.Spell> DharokSpellModel = new DefaultComboBoxModel<Magic.Spell>();
		final DefaultComboBoxModel<Magic.Spell> KarilSpellModel = new DefaultComboBoxModel<Magic.Spell>();
		final DefaultComboBoxModel<Magic.Spell> VeracSpellModel = new DefaultComboBoxModel<Magic.Spell>();
		final DefaultComboBoxModel<Magic.Spell> ToragSpellModel = new DefaultComboBoxModel<Magic.Spell>();
		final DefaultComboBoxModel<Magic.Spell> GuthanSpellModel = new DefaultComboBoxModel<Magic.Spell>();
		final DefaultComboBoxModel<Magic.Spell> AhrimSpellModel = new DefaultComboBoxModel<Magic.Spell>();

		for(Magic.Spell s : Magic.Spell.values()) {
				DharokSpellModel.addElement(s);
				KarilSpellModel.addElement(s);
				VeracSpellModel.addElement(s);
				ToragSpellModel.addElement(s);
				GuthanSpellModel.addElement(s);
				AhrimSpellModel.addElement(s);
		}

		cbxDharok = new JComboBox<Spell>(DharokSpellModel);

		JLabel label_5 = new JLabel("Select Spell");
		GroupLayout gl_panelDharok = new GroupLayout(panelDharok);
		gl_panelDharok.setHorizontalGroup(
			gl_panelDharok.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDharok.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelDharok.createParallelGroup(Alignment.LEADING, false)
						.addComponent(DharokPrayer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnSetEquipment, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(58)
					.addGroup(gl_panelDharok.createParallelGroup(Alignment.LEADING, false)
						.addComponent(DharokPotions, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(cbxDharok, 0, 111, Short.MAX_VALUE)
						.addComponent(label_5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap(10, Short.MAX_VALUE))
		);
		gl_panelDharok.setVerticalGroup(
			gl_panelDharok.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDharok.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelDharok.createParallelGroup(Alignment.BASELINE)
						.addComponent(DharokPrayer)
						.addComponent(DharokPotions))
					.addGap(22)
					.addComponent(label_5)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelDharok.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSetEquipment)
						.addComponent(cbxDharok, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panelDharok.setLayout(gl_panelDharok);

		JPanel panelKaril = new JPanel();
		tabbedPane.addTab("Karil", null, panelKaril, null);

		JButton button = new JButton("Set Equipment");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Brother.Brothers.Karil.setEquipmentIds(Equipment.getEquipedItems());
			}
		});

		KarilPotions = new JCheckBox("Use Potions");

		KarilPrayer = new JCheckBox("Use Prayer");
		KarilPrayer.setSelected(true);

		JLabel label = new JLabel("Select Spell");

		cbxKaril = new JComboBox<Spell>(KarilSpellModel);
		GroupLayout gl_panelKaril = new GroupLayout(panelKaril);
		gl_panelKaril.setHorizontalGroup(
			gl_panelKaril.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelKaril.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelKaril.createParallelGroup(Alignment.LEADING, false)
						.addComponent(KarilPrayer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(58)
					.addGroup(gl_panelKaril.createParallelGroup(Alignment.LEADING, false)
						.addComponent(KarilPotions, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(cbxKaril, 0, 111, Short.MAX_VALUE)
						.addComponent(label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap(10, Short.MAX_VALUE))
		);
		gl_panelKaril.setVerticalGroup(
			gl_panelKaril.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelKaril.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelKaril.createParallelGroup(Alignment.BASELINE)
						.addComponent(KarilPrayer)
						.addComponent(KarilPotions))
					.addGap(22)
					.addComponent(label)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelKaril.createParallelGroup(Alignment.BASELINE)
						.addComponent(button)
						.addComponent(cbxKaril, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panelKaril.setLayout(gl_panelKaril);

		JPanel panelVerac = new JPanel();
		tabbedPane.addTab("Verac", null, panelVerac, null);

		JButton button_1 = new JButton("Set Equipment");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Brother.Brothers.Verac.setEquipmentIds(Equipment.getEquipedItems());
			}
		});

		VeracPotions = new JCheckBox("Use Potions");

		VeracPrayer = new JCheckBox("Use Prayer");
		VeracPrayer.setSelected(true);

		JLabel label_1 = new JLabel("Select Spell");

		cbxVerac = new JComboBox<Spell>(VeracSpellModel);
		GroupLayout gl_panelVerac = new GroupLayout(panelVerac);
		gl_panelVerac.setHorizontalGroup(
			gl_panelVerac.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelVerac.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelVerac.createParallelGroup(Alignment.LEADING, false)
						.addComponent(VeracPrayer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(button_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(58)
					.addGroup(gl_panelVerac.createParallelGroup(Alignment.LEADING, false)
						.addComponent(VeracPotions, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(cbxVerac, 0, 111, Short.MAX_VALUE)
						.addComponent(label_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap(10, Short.MAX_VALUE))
		);
		gl_panelVerac.setVerticalGroup(
			gl_panelVerac.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelVerac.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelVerac.createParallelGroup(Alignment.BASELINE)
						.addComponent(VeracPrayer)
						.addComponent(VeracPotions))
					.addGap(22)
					.addComponent(label_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelVerac.createParallelGroup(Alignment.BASELINE)
						.addComponent(button_1)
						.addComponent(cbxVerac, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panelVerac.setLayout(gl_panelVerac);

		JPanel panelGuthan = new JPanel();
		tabbedPane.addTab("Guthan", null, panelGuthan, null);

		JButton button_2 = new JButton("Set Equipment");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Brother.Brothers.Guthan.setEquipmentIds(Equipment.getEquipedItems());
			}
		});

		GuthanPotions = new JCheckBox("Use Potions");

		GuthanPrayer = new JCheckBox("Use Prayer");
		GuthanPrayer.setSelected(true);

		cbxGuthan = new JComboBox<Spell>(GuthanSpellModel);

		JLabel label_2 = new JLabel("Select Spell");
		GroupLayout gl_panelGuthan = new GroupLayout(panelGuthan);
		gl_panelGuthan.setHorizontalGroup(
			gl_panelGuthan.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelGuthan.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelGuthan.createParallelGroup(Alignment.LEADING, false)
						.addComponent(GuthanPrayer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(button_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(58)
					.addGroup(gl_panelGuthan.createParallelGroup(Alignment.LEADING, false)
						.addComponent(GuthanPotions, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(cbxGuthan, 0, 111, Short.MAX_VALUE)
						.addComponent(label_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap(10, Short.MAX_VALUE))
		);
		gl_panelGuthan.setVerticalGroup(
			gl_panelGuthan.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelGuthan.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelGuthan.createParallelGroup(Alignment.BASELINE)
						.addComponent(GuthanPrayer)
						.addComponent(GuthanPotions))
					.addGap(22)
					.addComponent(label_2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelGuthan.createParallelGroup(Alignment.BASELINE)
						.addComponent(button_2)
						.addComponent(cbxGuthan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panelGuthan.setLayout(gl_panelGuthan);

		JPanel panelTorag = new JPanel();
		tabbedPane.addTab("Torag", null, panelTorag, null);

		JButton button_3 = new JButton("Set Equipment");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Brother.Brothers.Torag.setEquipmentIds(Equipment.getEquipedItems());
			}
		});

		ToragPotions = new JCheckBox("Use Potions");

		ToragPrayer = new JCheckBox("Use Prayer");
		ToragPrayer.setSelected(true);

		JLabel label_3 = new JLabel("Select Spell");

		cbxTorag = new JComboBox<Spell>(ToragSpellModel);
		GroupLayout gl_panelTorag = new GroupLayout(panelTorag);
		gl_panelTorag.setHorizontalGroup(
			gl_panelTorag.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelTorag.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelTorag.createParallelGroup(Alignment.LEADING, false)
						.addComponent(ToragPrayer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(button_3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(58)
					.addGroup(gl_panelTorag.createParallelGroup(Alignment.LEADING, false)
						.addComponent(ToragPotions, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(cbxTorag, 0, 111, Short.MAX_VALUE)
						.addComponent(label_3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap(10, Short.MAX_VALUE))
		);
		gl_panelTorag.setVerticalGroup(
			gl_panelTorag.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelTorag.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelTorag.createParallelGroup(Alignment.BASELINE)
						.addComponent(ToragPrayer)
						.addComponent(ToragPotions))
					.addGap(22)
					.addComponent(label_3)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelTorag.createParallelGroup(Alignment.BASELINE)
						.addComponent(button_3)
						.addComponent(cbxTorag, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panelTorag.setLayout(gl_panelTorag);

		JPanel panelAhrim = new JPanel();
		tabbedPane.addTab("Ahrim", null, panelAhrim, null);

		JButton button_4 = new JButton("Set Equipment");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Brother.Brothers.Ahrim.setEquipmentIds(Equipment.getEquipedItems());
			}
		});

		AhrimPotions = new JCheckBox("Use Potions");

		AhrimPrayer = new JCheckBox("Use Prayer");
		AhrimPrayer.setSelected(true);

		cbxAhrim = new JComboBox<Spell>(AhrimSpellModel);

		JLabel label_4 = new JLabel("Select Spell");
		GroupLayout gl_panelAhrim = new GroupLayout(panelAhrim);
		gl_panelAhrim.setHorizontalGroup(
			gl_panelAhrim.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelAhrim.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelAhrim.createParallelGroup(Alignment.LEADING, false)
						.addComponent(AhrimPrayer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(button_4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(58)
					.addGroup(gl_panelAhrim.createParallelGroup(Alignment.LEADING, false)
						.addComponent(AhrimPotions, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(cbxAhrim, 0, 111, Short.MAX_VALUE)
						.addComponent(label_4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap(10, Short.MAX_VALUE))
		);
		gl_panelAhrim.setVerticalGroup(
			gl_panelAhrim.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelAhrim.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelAhrim.createParallelGroup(Alignment.BASELINE)
						.addComponent(AhrimPrayer)
						.addComponent(AhrimPotions))
					.addGap(22)
					.addComponent(label_4)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelAhrim.createParallelGroup(Alignment.BASELINE)
						.addComponent(button_4)
						.addComponent(cbxAhrim, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panelAhrim.setLayout(gl_panelAhrim);

		JPanel panelTunnels = new JPanel();
		tabbedPane.addTab("Tunnel", null, panelTunnels, null);

		JButton button_5 = new JButton("Set Equipment");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Var.tunnelEquipment = Equipment.getEquipedItems();
			}
		});
		GroupLayout gl_panelTunnels = new GroupLayout(panelTunnels);
		gl_panelTunnels.setHorizontalGroup(
			gl_panelTunnels.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelTunnels.createSequentialGroup()
					.addContainerGap()
					.addComponent(button_5, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(180, Short.MAX_VALUE))
		);
		gl_panelTunnels.setVerticalGroup(
			gl_panelTunnels.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelTunnels.createSequentialGroup()
					.addContainerGap()
					.addComponent(button_5)
					.addContainerGap(79, Short.MAX_VALUE))
		);
		panelTunnels.setLayout(gl_panelTunnels);
		panel_1.setLayout(gl_panel_1);

		JButton btnRight = new JButton("▲");
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String s1 = listSelected.getSelectedValue();
				int i = listSelected.getSelectedIndex();
				if (i > 0) {
					modelSelected.remove(i);
					modelSelected.add(i-1, s1);
					listSelected.setSelectedIndex(i-1);
				}
			}
		});

		JButton btnLeft = new JButton("▼");
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s1 = listSelected.getSelectedValue();
				int i = listSelected.getSelectedIndex();
				if (i < 5) {
					modelSelected.remove(i);
					modelSelected.add(i+1, s1);
					listSelected.setSelectedIndex(i+1);
				}
			}
		});

		listSelected = new JList<String>(modelSelected);

		listSelected.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(listSelected, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
						.addComponent(btnLeft, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
						.addComponent(btnRight, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnRight)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(listSelected, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnLeft)
					.addContainerGap(46, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);

	}
}