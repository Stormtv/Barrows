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
import javax.swing.JTabbedPane;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class BarrowGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JList<String> listSelected;
	private static DefaultListModel<String> modelSelected = new DefaultListModel<String>();
	private static JCheckBox DharokPrayer, DharokPotions, KarilPrayer, KarilPotions,
	VeracPrayer, VeracPotions, GuthanPrayer, GuthanPotions,
	ToragPrayer, ToragPotions, AhrimPrayer, AhrimPotions;
	private static JComboBox<Magic.Spell> cbxDharok, cbxKaril, cbxVerac, cbxGuthan, cbxTorag, cbxAhrim;
	private static JComboBox<Food.Edibles> cbxFood;
	private static JComboBox<Pathing.PathBarrows> cbxBarrows;
	private static JComboBox<Pathing.PathBank> cbxBank;
	private JTextField txtFood,txtSA,txtSS,txtSD,txtRP,txtPP;
	public BarrowGUI() {
		modelSelected.addElement("Dharok");
		modelSelected.addElement("Karil");
		modelSelected.addElement("Verac");
		modelSelected.addElement("Guthan");
		modelSelected.addElement("Torag");
		modelSelected.addElement("Ahrim");
		setTitle("Barrows");
		setBounds(100, 100, 625, 347);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Kill Order", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int index = 0; index < 6; index++) {
					String s = modelSelected.getElementAt(index);
					switch (s) {
						case "Dharok":
							Brother.Brothers.Dharok.killOrder = index;
						case "Karil":
							Brother.Brothers.Karil.killOrder = index;
						case "Verac":
							Brother.Brothers.Verac.killOrder = index;
						case "Guthan":
							Brother.Brothers.Guthan.killOrder = index;
						case "Torag":
							Brother.Brothers.Torag.killOrder = index;
						case "Ahrim":
							Brother.Brothers.Ahrim.killOrder = index;
					}
				}
				
				if (!DharokPrayer.isSelected()) {
					Brother.Brothers.Dharok.prayer = Prayer.Prayers.None;
				}
				if (!KarilPrayer.isSelected()) {
					Brother.Brothers.Karil.prayer = Prayer.Prayers.None;
				}
				if (!VeracPrayer.isSelected()) {
					Brother.Brothers.Verac.prayer = Prayer.Prayers.None;
				}
				if (!GuthanPrayer.isSelected()) {
					Brother.Brothers.Guthan.prayer = Prayer.Prayers.None;
				}
				if (!ToragPrayer.isSelected()) {
					Brother.Brothers.Torag.prayer = Prayer.Prayers.None;
				}
				if (!AhrimPrayer.isSelected()) {
					Brother.Brothers.Ahrim.prayer = Prayer.Prayers.None;
				}
				Brother.Brothers.Dharok.usePotions = DharokPotions.isSelected();
				Brother.Brothers.Karil.usePotions = KarilPotions.isSelected();
				Brother.Brothers.Verac.usePotions = VeracPotions.isSelected();
				Brother.Brothers.Guthan.usePotions = GuthanPotions.isSelected();
				Brother.Brothers.Torag.usePotions = ToragPotions.isSelected();
				Brother.Brothers.Ahrim.usePotions = AhrimPotions.isSelected();
				
				Brother.Brothers.Dharok.spell = (Magic.Spell) cbxDharok.getSelectedItem();
				Brother.Brothers.Karil.spell = (Magic.Spell) cbxKaril.getSelectedItem();
				Brother.Brothers.Verac.spell = (Magic.Spell) cbxVerac.getSelectedItem();
				Brother.Brothers.Guthan.spell = (Magic.Spell) cbxGuthan.getSelectedItem();
				Brother.Brothers.Torag.spell = (Magic.Spell) cbxTorag.getSelectedItem();
				Brother.Brothers.Ahrim.spell = (Magic.Spell) cbxAhrim.getSelectedItem();
				
				Var.food = (Food.Edibles) cbxFood.getSelectedItem();
				
				Var.foodAmount = Integer.parseInt(txtFood.getText());
				Var.superAttack = Integer.parseInt(txtSA.getText());
				Var.superStrength = Integer.parseInt(txtSS.getText());
				Var.superDefence = Integer.parseInt(txtSD.getText());
				Var.rangingPotion = Integer.parseInt(txtRP.getText());
				Var.prayerPotion = Integer.parseInt(txtPP.getText());
				
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
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnStart, GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(panel_2, 0, 0, Short.MAX_VALUE)
								.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 285, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
								.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))))
					.addGap(157))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
								.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))
							.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
								.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 259, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnStart)
					.addGap(59))
		);
		
		JLabel lblSuperAttack = new JLabel("Super attack:");
		
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
							.addPreferredGap(ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
							.addComponent(txtSA, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(lblSuperStrength, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
							.addComponent(txtSS, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(lblSuperDefence, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
							.addComponent(txtSD, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(lblRangingPotion, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(txtRP, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(lblPrayerPotions, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(txtPP, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)))
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
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(3)
							.addComponent(lblRangingPotion))
						.addComponent(txtRP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(3)
							.addComponent(lblPrayerPotions))
						.addComponent(txtPP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(99, Short.MAX_VALUE))
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
						.addComponent(label_6, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_3.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(Alignment.LEADING, gl_panel_3.createSequentialGroup()
								.addComponent(label_7, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(txtFood, 0, 0, Short.MAX_VALUE))
							.addComponent(cbxFood, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(163, Short.MAX_VALUE))
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
						.addComponent(txtFood, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_7))
					.addContainerGap(133, Short.MAX_VALUE))
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
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(lblToBarrows)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cbxBarrows, 0, 189, Short.MAX_VALUE))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(lblToBank, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(cbxBank, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblToBarrows)
						.addComponent(cbxBarrows, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(3)
							.addComponent(lblToBank))
						.addComponent(cbxBank, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
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
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		JPanel panelDharok = new JPanel();
		tabbedPane.addTab("Dharok", null, panelDharok, null);
		
		JButton btnSetEquipment = new JButton("Set Equipment");
		btnSetEquipment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Brother.Brothers.Dharok.equipmentIds = Equipment.getEquipedItems();
			}
		});
		
		DharokPrayer = new JCheckBox("Use Prayer");
		DharokPrayer.setSelected(true);
		
		DharokPotions = new JCheckBox("Use Potions");
		
		final DefaultComboBoxModel<Magic.Spell> SpellModel = new DefaultComboBoxModel<Magic.Spell>();
		for(Magic.Spell s : Magic.Spell.values()) {
				SpellModel.addElement(s);
		}
		
		cbxDharok = new JComboBox<Spell>(SpellModel);
		
		JLabel label_5 = new JLabel("Select Spell");
		GroupLayout gl_panelDharok = new GroupLayout(panelDharok);
		gl_panelDharok.setHorizontalGroup(
			gl_panelDharok.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDharok.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelDharok.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panelDharok.createSequentialGroup()
							.addComponent(DharokPrayer)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(DharokPotions))
						.addGroup(gl_panelDharok.createSequentialGroup()
							.addComponent(btnSetEquipment)
							.addGap(18)
							.addGroup(gl_panelDharok.createParallelGroup(Alignment.LEADING)
								.addComponent(label_5, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
								.addComponent(cbxDharok, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panelDharok.setVerticalGroup(
			gl_panelDharok.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDharok.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelDharok.createParallelGroup(Alignment.BASELINE)
						.addComponent(DharokPrayer)
						.addComponent(DharokPotions))
					.addPreferredGap(ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
					.addComponent(label_5)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelDharok.createParallelGroup(Alignment.TRAILING)
						.addComponent(cbxDharok, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSetEquipment))
					.addContainerGap())
		);
		panelDharok.setLayout(gl_panelDharok);
		
		JPanel panelKaril = new JPanel();
		tabbedPane.addTab("Karil", null, panelKaril, null);
		
		JButton button = new JButton("Set Equipment");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Brother.Brothers.Karil.equipmentIds = Equipment.getEquipedItems();
			}
		});
		
		KarilPotions = new JCheckBox("Use Potions");
		
		KarilPrayer = new JCheckBox("Use Prayer");
		KarilPrayer.setSelected(true);
		
		JLabel label = new JLabel("Select Spell");
		
		cbxKaril = new JComboBox<Spell>(SpellModel);
		GroupLayout gl_panelKaril = new GroupLayout(panelKaril);
		gl_panelKaril.setHorizontalGroup(
			gl_panelKaril.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelKaril.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelKaril.createParallelGroup(Alignment.LEADING)
						.addComponent(KarilPrayer, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
						.addComponent(button, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panelKaril.createParallelGroup(Alignment.LEADING)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panelKaril.createParallelGroup(Alignment.TRAILING)
							.addComponent(KarilPotions, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
							.addComponent(cbxKaril, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panelKaril.setVerticalGroup(
			gl_panelKaril.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelKaril.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelKaril.createParallelGroup(Alignment.BASELINE)
						.addComponent(KarilPrayer)
						.addComponent(KarilPotions))
					.addPreferredGap(ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
					.addGroup(gl_panelKaril.createParallelGroup(Alignment.TRAILING)
						.addComponent(button)
						.addGroup(gl_panelKaril.createSequentialGroup()
							.addComponent(label)
							.addGap(7)
							.addComponent(cbxKaril, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		panelKaril.setLayout(gl_panelKaril);
		
		JPanel panelVerac = new JPanel();
		tabbedPane.addTab("Verac", null, panelVerac, null);
		
		JButton button_1 = new JButton("Set Equipment");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Brother.Brothers.Verac.equipmentIds = Equipment.getEquipedItems();
			}
		});
		
		VeracPotions = new JCheckBox("Use Potions");
		
		VeracPrayer = new JCheckBox("Use Prayer");
		VeracPrayer.setSelected(true);
		
		JLabel label_1 = new JLabel("Select Spell");
		
		cbxVerac = new JComboBox<Spell>(SpellModel);
		GroupLayout gl_panelVerac = new GroupLayout(panelVerac);
		gl_panelVerac.setHorizontalGroup(
			gl_panelVerac.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelVerac.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelVerac.createParallelGroup(Alignment.LEADING)
						.addComponent(VeracPrayer, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panelVerac.createSequentialGroup()
							.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addGroup(gl_panelVerac.createParallelGroup(Alignment.LEADING)
								.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panelVerac.createParallelGroup(Alignment.TRAILING)
									.addComponent(VeracPotions, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
									.addComponent(cbxVerac, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panelVerac.setVerticalGroup(
			gl_panelVerac.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelVerac.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelVerac.createParallelGroup(Alignment.BASELINE)
						.addComponent(VeracPrayer)
						.addComponent(VeracPotions))
					.addPreferredGap(ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
					.addGroup(gl_panelVerac.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panelVerac.createSequentialGroup()
							.addComponent(label_1)
							.addGap(7)
							.addComponent(cbxVerac, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(button_1))
					.addContainerGap())
		);
		panelVerac.setLayout(gl_panelVerac);
		
		JPanel panelGuthan = new JPanel();
		tabbedPane.addTab("Guthan", null, panelGuthan, null);
		
		JButton button_2 = new JButton("Set Equipment");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Brother.Brothers.Guthan.equipmentIds = Equipment.getEquipedItems();
			}
		});
		
		GuthanPotions = new JCheckBox("Use Potions");
		
		GuthanPrayer = new JCheckBox("Use Prayer");
		GuthanPrayer.setSelected(true);
		
		cbxGuthan = new JComboBox<Spell>(SpellModel);
		
		JLabel label_2 = new JLabel("Select Spell");
		GroupLayout gl_panelGuthan = new GroupLayout(panelGuthan);
		gl_panelGuthan.setHorizontalGroup(
			gl_panelGuthan.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelGuthan.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelGuthan.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelGuthan.createSequentialGroup()
							.addComponent(GuthanPrayer, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
							.addGap(70)
							.addComponent(GuthanPotions, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelGuthan.createSequentialGroup()
							.addComponent(button_2, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addGroup(gl_panelGuthan.createParallelGroup(Alignment.LEADING)
								.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
								.addComponent(cbxGuthan, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panelGuthan.setVerticalGroup(
			gl_panelGuthan.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelGuthan.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelGuthan.createParallelGroup(Alignment.BASELINE)
						.addComponent(GuthanPrayer)
						.addComponent(GuthanPotions))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelGuthan.createParallelGroup(Alignment.TRAILING)
						.addComponent(button_2)
						.addGroup(gl_panelGuthan.createSequentialGroup()
							.addComponent(label_2)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cbxGuthan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(53))
		);
		panelGuthan.setLayout(gl_panelGuthan);
		
		JPanel panelTorag = new JPanel();
		tabbedPane.addTab("Torag", null, panelTorag, null);
		
		JButton button_3 = new JButton("Set Equipment");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Brother.Brothers.Torag.equipmentIds = Equipment.getEquipedItems();
			}
		});
		
		ToragPotions = new JCheckBox("Use Potions");
		
		ToragPrayer = new JCheckBox("Use Prayer");
		ToragPrayer.setSelected(true);
		
		JLabel label_3 = new JLabel("Select Spell");
		
		cbxTorag = new JComboBox<Spell>(SpellModel);
		GroupLayout gl_panelTorag = new GroupLayout(panelTorag);
		gl_panelTorag.setHorizontalGroup(
			gl_panelTorag.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelTorag.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelTorag.createParallelGroup(Alignment.LEADING)
						.addComponent(ToragPrayer, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panelTorag.createSequentialGroup()
							.addComponent(button_3, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addGroup(gl_panelTorag.createParallelGroup(Alignment.LEADING)
								.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panelTorag.createParallelGroup(Alignment.TRAILING)
									.addComponent(ToragPotions, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
									.addComponent(cbxTorag, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panelTorag.setVerticalGroup(
			gl_panelTorag.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelTorag.createSequentialGroup()
					.addContainerGap(43, Short.MAX_VALUE)
					.addGroup(gl_panelTorag.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panelTorag.createSequentialGroup()
							.addComponent(label_3)
							.addGap(7)
							.addComponent(cbxTorag, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(button_3))
					.addContainerGap())
				.addGroup(Alignment.LEADING, gl_panelTorag.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelTorag.createParallelGroup(Alignment.BASELINE)
						.addComponent(ToragPotions)
						.addComponent(ToragPrayer))
					.addContainerGap(61, Short.MAX_VALUE))
		);
		panelTorag.setLayout(gl_panelTorag);
		
		JPanel panelAhrim = new JPanel();
		tabbedPane.addTab("Ahrim", null, panelAhrim, null);
		
		JButton button_4 = new JButton("Set Equipment");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Brother.Brothers.Ahrim.equipmentIds = Equipment.getEquipedItems();
			}
		});
		
		AhrimPotions = new JCheckBox("Use Potions");
		
		AhrimPrayer = new JCheckBox("Use Prayer");
		AhrimPrayer.setSelected(true);
		
		cbxAhrim = new JComboBox<Spell>(SpellModel);
		
		JLabel label_4 = new JLabel("Select Spell");
		GroupLayout gl_panelAhrim = new GroupLayout(panelAhrim);
		gl_panelAhrim.setHorizontalGroup(
			gl_panelAhrim.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelAhrim.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelAhrim.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelAhrim.createSequentialGroup()
							.addComponent(button_4, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addGroup(gl_panelAhrim.createParallelGroup(Alignment.LEADING)
								.addComponent(label_4, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panelAhrim.createParallelGroup(Alignment.TRAILING)
									.addComponent(AhrimPotions, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
									.addComponent(cbxAhrim, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE))))
						.addComponent(AhrimPrayer, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panelAhrim.setVerticalGroup(
			gl_panelAhrim.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelAhrim.createSequentialGroup()
					.addContainerGap(43, Short.MAX_VALUE)
					.addGroup(gl_panelAhrim.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panelAhrim.createSequentialGroup()
							.addComponent(label_4)
							.addGap(7)
							.addComponent(cbxAhrim, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(button_4))
					.addContainerGap())
				.addGroup(Alignment.LEADING, gl_panelAhrim.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelAhrim.createParallelGroup(Alignment.BASELINE)
						.addComponent(AhrimPotions)
						.addComponent(AhrimPrayer))
					.addContainerGap(61, Short.MAX_VALUE))
		);
		panelAhrim.setLayout(gl_panelAhrim);
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
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(btnRight, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnLeft, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(listSelected, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnRight)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(listSelected, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnLeft)
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);

	}
}
