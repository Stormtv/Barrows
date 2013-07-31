package scripts.Barrows.gui;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import scripts.Barrows.types.Brother;
import scripts.Barrows.types.Var;
import scripts.Barrows.types.enums.Equipment;
import scripts.Barrows.types.enums.Prayer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JCheckBox;

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
	public BarrowGUI() {
		modelSelected.addElement("Dharok");
		modelSelected.addElement("Karil");
		modelSelected.addElement("Verac");
		modelSelected.addElement("Guthan");
		modelSelected.addElement("Torag");
		modelSelected.addElement("Ahrim");
		setTitle("Barrows");
		setBounds(100, 100, 442, 306);
		
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

				
				
				Var.guiWait = false;
				Var.gui.setVisible(false);
			}
		});
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Brother Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Pathing Options", TitledBorder.LEADING, TitledBorder.TOP, null, null), "Pathing Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnStart, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(panel_2, 0, 0, Short.MAX_VALUE)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE))
					.addContainerGap(77, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
							.addComponent(btnStart)))
					.addContainerGap())
		);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 345, Short.MAX_VALUE)
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 42, Short.MAX_VALUE)
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
		GroupLayout gl_panelDharok = new GroupLayout(panelDharok);
		gl_panelDharok.setHorizontalGroup(
			gl_panelDharok.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDharok.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelDharok.createParallelGroup(Alignment.LEADING)
						.addComponent(DharokPotions)
						.addComponent(DharokPrayer)
						.addComponent(btnSetEquipment))
					.addContainerGap(200, Short.MAX_VALUE))
		);
		gl_panelDharok.setVerticalGroup(
			gl_panelDharok.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDharok.createSequentialGroup()
					.addContainerGap()
					.addComponent(DharokPrayer)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(DharokPotions)
					.addPreferredGap(ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
					.addComponent(btnSetEquipment)
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
		GroupLayout gl_panelKaril = new GroupLayout(panelKaril);
		gl_panelKaril.setHorizontalGroup(
			gl_panelKaril.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelKaril.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelKaril.createParallelGroup(Alignment.LEADING)
						.addComponent(KarilPrayer, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
						.addComponent(KarilPotions, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
						.addComponent(button, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(200, Short.MAX_VALUE))
		);
		gl_panelKaril.setVerticalGroup(
			gl_panelKaril.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelKaril.createSequentialGroup()
					.addContainerGap()
					.addComponent(KarilPrayer)
					.addComponent(KarilPotions)
					.addPreferredGap(ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
					.addComponent(button)
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
		GroupLayout gl_panelVerac = new GroupLayout(panelVerac);
		gl_panelVerac.setHorizontalGroup(
			gl_panelVerac.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelVerac.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelVerac.createParallelGroup(Alignment.LEADING)
						.addComponent(VeracPrayer, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
						.addComponent(VeracPotions, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
						.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(200, Short.MAX_VALUE))
		);
		gl_panelVerac.setVerticalGroup(
			gl_panelVerac.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panelVerac.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(VeracPrayer)
					.addComponent(VeracPotions)
					.addGap(4)
					.addComponent(button_1)
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
		GroupLayout gl_panelGuthan = new GroupLayout(panelGuthan);
		gl_panelGuthan.setHorizontalGroup(
			gl_panelGuthan.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelGuthan.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelGuthan.createParallelGroup(Alignment.LEADING)
						.addComponent(GuthanPrayer, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
						.addComponent(GuthanPotions, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
						.addComponent(button_2, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(200, Short.MAX_VALUE))
		);
		gl_panelGuthan.setVerticalGroup(
			gl_panelGuthan.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panelGuthan.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(GuthanPrayer)
					.addComponent(GuthanPotions)
					.addGap(4)
					.addComponent(button_2)
					.addContainerGap())
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
		GroupLayout gl_panelTorag = new GroupLayout(panelTorag);
		gl_panelTorag.setHorizontalGroup(
			gl_panelTorag.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelTorag.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelTorag.createParallelGroup(Alignment.LEADING)
						.addComponent(ToragPrayer, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
						.addComponent(ToragPotions, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
						.addComponent(button_3, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(200, Short.MAX_VALUE))
		);
		gl_panelTorag.setVerticalGroup(
			gl_panelTorag.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panelTorag.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(ToragPrayer)
					.addComponent(ToragPotions)
					.addGap(4)
					.addComponent(button_3)
					.addContainerGap())
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
		GroupLayout gl_panelAhrim = new GroupLayout(panelAhrim);
		gl_panelAhrim.setHorizontalGroup(
			gl_panelAhrim.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelAhrim.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelAhrim.createParallelGroup(Alignment.LEADING)
						.addComponent(AhrimPrayer, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
						.addComponent(AhrimPotions, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
						.addComponent(button_4, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(200, Short.MAX_VALUE))
		);
		gl_panelAhrim.setVerticalGroup(
			gl_panelAhrim.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panelAhrim.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(AhrimPrayer)
					.addComponent(AhrimPotions)
					.addGap(4)
					.addComponent(button_4)
					.addContainerGap())
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
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnLeft, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
						.addComponent(listSelected, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnRight, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
					.addGap(154))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnRight)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(listSelected, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnLeft)
					.addContainerGap(38, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);

	}
}
