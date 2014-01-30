package scripts.Barrows.gui;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Image;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import scripts.Barrows.methods.Pathing;
import scripts.Barrows.types.Brother;
import scripts.Barrows.types.Var;
import scripts.Barrows.types.enums.Equipment;
import scripts.Barrows.types.enums.Food;
import scripts.Barrows.types.enums.Magic;
import scripts.Barrows.types.enums.Prayer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.tribot.util.Util;

@SuppressWarnings("serial")
public class BarrowGUI extends JFrame {
	/**
	 * 
	 */
	private static JList<String> listSelected;
	private static DefaultListModel<String> modelSelected = new DefaultListModel<String>();
	private static JComboBox<Food.Edibles> cbxFood;
	private static JComboBox<Pathing.PathBarrows> cbxBarrows;
	private static JComboBox<Pathing.PathBank> cbxBank;

	private static JComboBox<Brother.Brothers> cbxBrother;
	private static JComboBox<Magic.Spell> cbxSpell;
	private static JCheckBox chckbxUsePotions, chckbxUsePrayer, chckbxRecharge;
	private static Brother.Brothers currentBrother;
	private static JLabel picHelm, picNeck, picBody, picLegs, picBoots,
			picGloves, picRing, picCape, picSword, picShield, picArrow, picBro;
	private static JTextField txtFood, txtSA, txtSS, txtSD, txtRP, txtPP,
			txtArrows, txtCasts;
	private static JSpinner spinner, spnFood, spnDoses;

	private Image helm = getImage("http://i.imgur.com/S4K9ppc.png");
	private Image necklace = getImage("http://i.imgur.com/2RyM9tH.png");
	private Image body = getImage("http://i.imgur.com/cxHgHov.png");
	private Image legs = getImage("http://i.imgur.com/5vwNvqG.png");
	private Image boots = getImage("http://i.imgur.com/7fkGa1v.png");
	private Image gloves = getImage("http://i.imgur.com/7kVluAY.png");
	private Image ring = getImage("http://i.imgur.com/fLLW2DA.png");
	private Image cape = getImage("http://i.imgur.com/hmuFBVO.png");
	private Image weapon = getImage("http://i.imgur.com/R75B5iq.png");
	private Image shield = getImage("http://i.imgur.com/X7p4Az6.png");
	private Image arrow = getImage("http://i.imgur.com/2xV7dXe.png");
	private Image dharok = getImage("http://i.imgur.com/35LlHTE.png");
	private Image ahrim = getImage("http://i.imgur.com/BZiYlHH.png");
	private Image guthan = getImage("http://i.imgur.com/oWeYxYF.png");
	private Image karil = getImage("http://i.imgur.com/gBnNtd6.png");
	private Image torag = getImage("http://i.imgur.com/NnntbJK.png");
	private Image verac = getImage("http://i.imgur.com/6PVoCag.png");

	public BarrowGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				try {
					GUISave.load();
				} catch (IOException e) {
					e.printStackTrace();
				}
				currentBrother = (Brother.Brothers) cbxBrother
						.getSelectedItem();
				cbxSpell.setSelectedItem(currentBrother.getSpell());
				chckbxUsePrayer.setSelected(!currentBrother.getPrayer().equals(
						Prayer.Prayers.None));
				chckbxUsePotions.setSelected(currentBrother.usePotions());
				chckbxRecharge.setSelected(Var.recharge);

				// if (!imageLoader.isAlive()) {
				imageLoader.start();
				// }

				modelSelected.setSize(6);
				modelSelected
						.set(Brother.Brothers.Dharok.killOrder(), "Dharok");
				modelSelected.set(Brother.Brothers.Karil.killOrder(), "Karil");
				modelSelected
						.set(Brother.Brothers.Guthan.killOrder(), "Guthan");
				modelSelected.set(Brother.Brothers.Ahrim.killOrder(), "Ahrim");
				modelSelected.set(Brother.Brothers.Verac.killOrder(), "Verac");
				modelSelected.set(Brother.Brothers.Torag.killOrder(), "Torag");

				cbxFood.setSelectedItem(Var.food);

				spinner.setValue(Var.killCount);
				spnFood.setValue(Var.nextRunFood);
				spnDoses.setValue(Var.nextRunDoses);
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
			modelSelected.addElement("Dharok");
			modelSelected.addElement("Karil");
			modelSelected.addElement("Verac");
			modelSelected.addElement("Guthan");
			modelSelected.addElement("Torag");
			modelSelected.addElement("Ahrim");
		}
		setTitle("Barrows");
		setBounds(100, 100, 874, 513);

		final DefaultComboBoxModel<Food.Edibles> FoodModel = new DefaultComboBoxModel<Food.Edibles>();
		for (Food.Edibles f : Food.Edibles.values()) {
			FoodModel.addElement(f);
		}

		final DefaultComboBoxModel<Pathing.PathBarrows> BarrowModel = new DefaultComboBoxModel<Pathing.PathBarrows>();
		for (Pathing.PathBarrows s : Pathing.PathBarrows.values()) {
			BarrowModel.addElement(s);
		}

		final DefaultComboBoxModel<Pathing.PathBank> BankModel = new DefaultComboBoxModel<Pathing.PathBank>();
		for (Pathing.PathBank s : Pathing.PathBank.values()) {
			BankModel.addElement(s);
		}

		final DefaultComboBoxModel<Magic.Spell> DharokSpellModel = new DefaultComboBoxModel<Magic.Spell>();
		final DefaultComboBoxModel<Magic.Spell> KarilSpellModel = new DefaultComboBoxModel<Magic.Spell>();
		final DefaultComboBoxModel<Magic.Spell> VeracSpellModel = new DefaultComboBoxModel<Magic.Spell>();
		final DefaultComboBoxModel<Magic.Spell> ToragSpellModel = new DefaultComboBoxModel<Magic.Spell>();
		final DefaultComboBoxModel<Magic.Spell> GuthanSpellModel = new DefaultComboBoxModel<Magic.Spell>();
		final DefaultComboBoxModel<Magic.Spell> AhrimSpellModel = new DefaultComboBoxModel<Magic.Spell>();

		for (Magic.Spell s : Magic.Spell.values()) {
			DharokSpellModel.addElement(s);
			KarilSpellModel.addElement(s);
			VeracSpellModel.addElement(s);
			ToragSpellModel.addElement(s);
			GuthanSpellModel.addElement(s);
			AhrimSpellModel.addElement(s);
		}

		final DefaultComboBoxModel<Brother.Brothers> BrotherModel = new DefaultComboBoxModel<Brother.Brothers>();
		for (Brother.Brothers s : Brother.Brothers.values()) {
			BrotherModel.addElement(s);
		}

		final DefaultComboBoxModel<Magic.Spell> SpellModel = new DefaultComboBoxModel<Magic.Spell>();
		for (Magic.Spell s : Magic.Spell.values()) {
			SpellModel.addElement(s);
		}

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)),
				"Kill Order", TitledBorder.LEADING, TitledBorder.TOP, null,
				null));

		JButton btnRight = new JButton("Up");
		btnRight.setEnabled(true);
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String s1 = listSelected.getSelectedValue();
				int i = listSelected.getSelectedIndex();
				if (i > 0) {
					modelSelected.remove(i);
					modelSelected.add(i - 1, s1);
					listSelected.setSelectedIndex(i - 1);
				}
			}
		});

		JButton btnLeft = new JButton("Down");
		btnLeft.setEnabled(true);
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s1 = listSelected.getSelectedValue();
				int i = listSelected.getSelectedIndex();
				if (i < 5) {
					modelSelected.remove(i);
					modelSelected.add(i + 1, s1);
					listSelected.setSelectedIndex(i + 1);
				}
			}
		});

		listSelected = new JList<String>(modelSelected);

		listSelected.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						Alignment.TRAILING,
						gl_panel.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.TRAILING)
												.addComponent(
														listSelected,
														Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE,
														81, Short.MAX_VALUE)
												.addComponent(
														btnRight,
														Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE,
														81, Short.MAX_VALUE)
												.addComponent(
														btnLeft,
														Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE,
														81, Short.MAX_VALUE))
								.addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel.createSequentialGroup()
						.addContainerGap()
						.addComponent(btnRight)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(listSelected, GroupLayout.DEFAULT_SIZE,
								115, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnLeft).addGap(21)));
		panel.setLayout(gl_panel);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)),
				"Brother Settings", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));

		picHelm = new JLabel(new ImageIcon(helm));

		picNeck = new JLabel(new ImageIcon(necklace));

		picBody = new JLabel(new ImageIcon(body));

		picLegs = new JLabel(new ImageIcon(legs));

		picBoots = new JLabel(new ImageIcon(boots));

		picGloves = new JLabel(new ImageIcon(gloves));

		picRing = new JLabel(new ImageIcon(ring));

		picCape = new JLabel(new ImageIcon(cape));

		picSword = new JLabel(new ImageIcon(weapon));

		picShield = new JLabel(new ImageIcon(shield));

		picArrow = new JLabel(new ImageIcon(arrow));

		JButton btnSetEquipment = new JButton("Set Equipment");
		btnSetEquipment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentBrother.setEquipmentIds(Equipment.getEquipedItems());
				// if (!imageLoader.isAlive()) {
				//imageLoader.start();
				// }
			}
		});
		cbxBrother = new JComboBox<Brother.Brothers>(BrotherModel);
		cbxBrother.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentBrother = (Brother.Brothers) cbxBrother
						.getSelectedItem();
				cbxSpell.setSelectedItem(currentBrother.getSpell());
				chckbxUsePrayer.setSelected(!currentBrother.getPrayer().equals(
						Prayer.Prayers.None));
				chckbxUsePotions.setSelected(currentBrother.usePotions());
				// if (!imageLoader.isAlive()) {
				//imageLoader.start();
				// }
			}
		});

		picBro = new JLabel(new ImageIcon(dharok));
		cbxSpell = new JComboBox<Magic.Spell>(SpellModel);
		cbxSpell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentBrother.setSpell((Magic.Spell) cbxSpell
						.getSelectedItem());
			}
		});

		chckbxUsePrayer = new JCheckBox("Use Prayer?");
		chckbxUsePrayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxUsePrayer.isSelected()) {
					if (!currentBrother.equals(Brother.Brothers.Ahrim)
							&& !currentBrother.equals(Brother.Brothers.Karil)) {
						currentBrother
								.setPrayer(Prayer.Prayers.ProtectFromMelee);
					} else if (currentBrother.equals(Brother.Brothers.Ahrim)) {
						currentBrother
								.setPrayer(Prayer.Prayers.ProtectFromMagic);
					} else if (currentBrother.equals(Brother.Brothers.Karil)) {
						currentBrother
								.setPrayer(Prayer.Prayers.ProtectFromMissiles);
					}
				} else {
					currentBrother.setPrayer(Prayer.Prayers.None);
				}
			}
		});

		chckbxUsePotions = new JCheckBox("Use Potions?");
		chckbxUsePotions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentBrother.setUsePotions(chckbxUsePotions.isSelected());
			}
		});

		JLabel lblSpellChoice = new JLabel("Spell Choice");

		JButton btnSetTunnelEquipment = new JButton("Set Tunnel Equip");
		btnSetTunnelEquipment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Var.tunnelEquipment = Equipment.getEquipedItems();
			}
		});
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1
				.setHorizontalGroup(gl_panel_1
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panel_1
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panel_1
														.createParallelGroup(
																Alignment.LEADING,
																false)
														.addComponent(
																cbxBrother,
																0,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																picBro,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addPreferredGap(
												ComponentPlacement.RELATED, 27,
												Short.MAX_VALUE)
										.addGroup(
												gl_panel_1
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_panel_1
																		.createSequentialGroup()
																		.addGap(100)
																		.addComponent(
																				picHelm))
														.addGroup(
																gl_panel_1
																		.createSequentialGroup()
																		.addGap(47)
																		.addGroup(
																				gl_panel_1
																						.createParallelGroup(
																								Alignment.LEADING,
																								false)
																						.addComponent(
																								btnSetEquipment,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addGroup(
																								gl_panel_1
																										.createSequentialGroup()
																										.addGroup(
																												gl_panel_1
																														.createParallelGroup(
																																Alignment.TRAILING)
																														.addComponent(
																																picGloves)
																														.addComponent(
																																picSword)
																														.addComponent(
																																picCape))
																										.addGap(18)
																										.addGroup(
																												gl_panel_1
																														.createParallelGroup(
																																Alignment.LEADING)
																														.addGroup(
																																gl_panel_1
																																		.createParallelGroup(
																																				Alignment.LEADING)
																																		.addComponent(
																																				picBoots,
																																				Alignment.TRAILING)
																																		.addComponent(
																																				picBody,
																																				Alignment.TRAILING)
																																		.addComponent(
																																				picLegs,
																																				Alignment.TRAILING))
																														.addComponent(
																																picNeck))
																										.addGap(18)
																										.addGroup(
																												gl_panel_1
																														.createParallelGroup(
																																Alignment.LEADING)
																														.addComponent(
																																picShield)
																														.addComponent(
																																picArrow)
																														.addComponent(
																																picRing))))))
										.addPreferredGap(
												ComponentPlacement.RELATED, 35,
												Short.MAX_VALUE)
										.addGroup(
												gl_panel_1
														.createParallelGroup(
																Alignment.LEADING,
																false)
														.addComponent(
																lblSpellChoice)
														.addComponent(
																chckbxUsePotions,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																cbxSpell,
																0,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																btnSetTunnelEquipment,
																GroupLayout.DEFAULT_SIZE,
																118,
																Short.MAX_VALUE)
														.addComponent(
																chckbxUsePrayer,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addContainerGap(32, Short.MAX_VALUE)));
		gl_panel_1
				.setVerticalGroup(gl_panel_1
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panel_1
										.createSequentialGroup()
										.addGap(5)
										.addGroup(
												gl_panel_1
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_panel_1
																		.createSequentialGroup()
																		.addComponent(
																				cbxBrother,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				picBro))
														.addGroup(
																gl_panel_1
																		.createSequentialGroup()
																		.addGroup(
																				gl_panel_1
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								gl_panel_1
																										.createSequentialGroup()
																										.addComponent(
																												picHelm)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addGroup(
																												gl_panel_1
																														.createParallelGroup(
																																Alignment.LEADING)
																														.addGroup(
																																gl_panel_1
																																		.createParallelGroup(
																																				Alignment.TRAILING)
																																		.addComponent(
																																				picArrow)
																																		.addComponent(
																																				picCape))
																														.addComponent(
																																picNeck)))
																						.addGroup(
																								gl_panel_1
																										.createSequentialGroup()
																										.addComponent(
																												chckbxUsePrayer)
																										.addGap(3)
																										.addComponent(
																												chckbxUsePotions)))
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addGroup(
																				gl_panel_1
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								gl_panel_1
																										.createSequentialGroup()
																										.addGroup(
																												gl_panel_1
																														.createParallelGroup(
																																Alignment.LEADING)
																														.addComponent(
																																picBody,
																																Alignment.TRAILING)
																														.addComponent(
																																picSword,
																																Alignment.TRAILING)
																														.addComponent(
																																picShield,
																																Alignment.TRAILING))
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												picLegs)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addGroup(
																												gl_panel_1
																														.createParallelGroup(
																																Alignment.LEADING)
																														.addComponent(
																																picGloves)
																														.addComponent(
																																picBoots)
																														.addComponent(
																																picRing)))
																						.addGroup(
																								gl_panel_1
																										.createSequentialGroup()
																										.addComponent(
																												lblSpellChoice)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												cbxSpell,
																												GroupLayout.PREFERRED_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.PREFERRED_SIZE)))
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addGroup(
																				gl_panel_1
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								btnSetEquipment)
																						.addComponent(
																								btnSetTunnelEquipment,
																								GroupLayout.PREFERRED_SIZE,
																								23,
																								GroupLayout.PREFERRED_SIZE))))
										.addContainerGap(76, Short.MAX_VALUE)));
		panel_1.setLayout(gl_panel_1);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)),
				"Food", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		cbxFood = new JComboBox<Food.Edibles>(FoodModel);

		JLabel label_6 = new JLabel("Select Your Food");

		JLabel label_7 = new JLabel("Withdraw amount:");

		txtFood = new JTextField();
		txtFood.setText("0");
		txtFood.setColumns(10);
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3
				.setHorizontalGroup(gl_panel_3
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panel_3
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panel_3
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																label_6,
																GroupLayout.DEFAULT_SIZE,
																163,
																Short.MAX_VALUE)
														.addGroup(
																gl_panel_3
																		.createSequentialGroup()
																		.addComponent(
																				label_7,
																				GroupLayout.PREFERRED_SIZE,
																				103,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				txtFood,
																				GroupLayout.PREFERRED_SIZE,
																				32,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(cbxFood,
																0, 163,
																Short.MAX_VALUE))
										.addContainerGap()));
		gl_panel_3
				.setVerticalGroup(gl_panel_3
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panel_3
										.createSequentialGroup()
										.addComponent(label_6)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addComponent(cbxFood,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_panel_3
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(label_7)
														.addComponent(
																txtFood,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		panel_3.setLayout(gl_panel_3);

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)),
				"Potion Withdraw Amounts", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));

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
		gl_panel_4
				.setHorizontalGroup(gl_panel_4
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panel_4
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panel_4
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_panel_4
																		.createSequentialGroup()
																		.addComponent(
																				lblSuperAttack,
																				GroupLayout.PREFERRED_SIZE,
																				89,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(18)
																		.addComponent(
																				txtSA,
																				GroupLayout.PREFERRED_SIZE,
																				33,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																gl_panel_4
																		.createSequentialGroup()
																		.addComponent(
																				lblSuperStrength,
																				GroupLayout.PREFERRED_SIZE,
																				89,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(18)
																		.addComponent(
																				txtSS,
																				GroupLayout.PREFERRED_SIZE,
																				33,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																gl_panel_4
																		.createSequentialGroup()
																		.addComponent(
																				lblSuperDefence,
																				GroupLayout.PREFERRED_SIZE,
																				89,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(18)
																		.addComponent(
																				txtSD,
																				GroupLayout.PREFERRED_SIZE,
																				33,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																gl_panel_4
																		.createSequentialGroup()
																		.addGroup(
																				gl_panel_4
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								lblPrayerPotions,
																								GroupLayout.PREFERRED_SIZE,
																								89,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								lblRangingPotion,
																								GroupLayout.PREFERRED_SIZE,
																								89,
																								GroupLayout.PREFERRED_SIZE))
																		.addGap(18)
																		.addGroup(
																				gl_panel_4
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								txtPP,
																								GroupLayout.PREFERRED_SIZE,
																								33,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								txtRP,
																								GroupLayout.PREFERRED_SIZE,
																								33,
																								GroupLayout.PREFERRED_SIZE))))
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		gl_panel_4
				.setVerticalGroup(gl_panel_4
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panel_4
										.createSequentialGroup()
										.addGroup(
												gl_panel_4
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblSuperAttack)
														.addComponent(
																txtSA,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_panel_4
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblSuperStrength)
														.addComponent(
																txtSS,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_panel_4
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblSuperDefence)
														.addComponent(
																txtSD,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_panel_4
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																txtRP,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblRangingPotion))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_panel_4
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblPrayerPotions)
														.addComponent(
																txtPP,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(98, Short.MAX_VALUE)));
		panel_4.setLayout(gl_panel_4);

		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)),
				"Arrows / Runes", TitledBorder.LEADING, TitledBorder.TOP, null,
				null));

		JLabel lblArrows = new JLabel("Arrows:");

		txtArrows = new JTextField();
		txtArrows.setText("0");
		txtArrows.setColumns(10);

		JLabel lblOfCasts = new JLabel("# of Casts:");

		txtCasts = new JTextField();
		txtCasts.setText("0");
		txtCasts.setColumns(10);
		GroupLayout gl_panel_5 = new GroupLayout(panel_5);
		gl_panel_5.setHorizontalGroup(gl_panel_5.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel_5
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_panel_5
										.createParallelGroup(Alignment.LEADING,
												false)
										.addComponent(lblOfCasts,
												Alignment.TRAILING,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(lblArrows,
												Alignment.TRAILING,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(
								gl_panel_5
										.createParallelGroup(Alignment.LEADING)
										.addComponent(txtArrows,
												Alignment.TRAILING,
												GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(txtCasts,
												Alignment.TRAILING,
												GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE))
						.addContainerGap(40, Short.MAX_VALUE)));
		gl_panel_5
				.setVerticalGroup(gl_panel_5
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panel_5
										.createSequentialGroup()
										.addGroup(
												gl_panel_5
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(lblArrows)
														.addComponent(
																txtArrows,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_panel_5
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																txtCasts,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addGroup(
																gl_panel_5
																		.createSequentialGroup()
																		.addGap(3)
																		.addComponent(
																				lblOfCasts)))
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		panel_5.setLayout(gl_panel_5);

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int index = 0; index < 6; index++) {
					String s = modelSelected.getElementAt(index);
					switch (s) {
					case "Dharok":
						Brother.Brothers.Dharok.setKillOrder(index);
						break;
					case "Karil":
						Brother.Brothers.Karil.setKillOrder(index);
						break;
					case "Verac":
						Brother.Brothers.Verac.setKillOrder(index);
						break;
					case "Guthan":
						Brother.Brothers.Guthan.setKillOrder(index);
						break;
					case "Torag":
						Brother.Brothers.Torag.setKillOrder(index);
						break;
					case "Ahrim":
						Brother.Brothers.Ahrim.setKillOrder(index);
						break;
					}
				}

				Var.food = (Food.Edibles) cbxFood.getSelectedItem();

				Var.killCount = (int) spinner.getValue();
				Var.nextRunDoses = (int) spnDoses.getValue();
				Var.nextRunFood = (int) spnFood.getValue();
				Var.foodAmount = Integer.parseInt(txtFood.getText());
				Var.superAttack = Integer.parseInt(txtSA.getText());
				Var.superStrength = Integer.parseInt(txtSS.getText());
				Var.superDefence = Integer.parseInt(txtSD.getText());
				Var.rangingPotion = Integer.parseInt(txtRP.getText());
				Var.prayerPotion = Integer.parseInt(txtPP.getText());
				Var.arrowCount = Integer.parseInt(txtArrows.getText());
				Var.spellCount = Integer.parseInt(txtCasts.getText());

				Var.bankPath = (Pathing.PathBank) cbxBank.getSelectedItem();
				Var.barrowsPath = (Pathing.PathBarrows) cbxBarrows
						.getSelectedItem();

				Var.arrowId = Equipment.getEquipmentID(Equipment.Gear.ARROW);
				try {
					Var.fileName = "Settings";
					GUISave.save();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				Var.guiWait = false;
				Var.gui.setVisible(false);
			}
		});

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(new TitledBorder(new LineBorder(
				new Color(0, 0, 0)), "Pathing Options", TitledBorder.LEADING,
				TitledBorder.TOP, null, null), "Pathing Options",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JLabel lblToBarrows = new JLabel("To Barrows:");

		cbxBarrows = new JComboBox<Pathing.PathBarrows>(BarrowModel);

		JLabel lblToBank = new JLabel("To Bank:");

		cbxBank = new JComboBox<Pathing.PathBank>(BankModel);

		chckbxRecharge = new JCheckBox("Recharge Prayer");
		chckbxRecharge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Var.recharge = chckbxRecharge.isSelected();
			}
		});

		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(gl_panel_2.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel_2
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_panel_2
										.createParallelGroup(Alignment.LEADING)
										.addComponent(lblToBarrows)
										.addComponent(lblToBank,
												GroupLayout.PREFERRED_SIZE, 58,
												GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(
								gl_panel_2
										.createParallelGroup(Alignment.LEADING)
										.addComponent(cbxBarrows, 0, 151,
												Short.MAX_VALUE)
										.addComponent(cbxBank, 0, 151,
												Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(chckbxRecharge).addContainerGap()));
		gl_panel_2
				.setVerticalGroup(gl_panel_2
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panel_2
										.createSequentialGroup()
										.addGroup(
												gl_panel_2
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblToBarrows)
														.addComponent(
																cbxBarrows,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGap(12)
										.addGroup(
												gl_panel_2
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(lblToBank)
														.addComponent(
																cbxBank,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																chckbxRecharge))
										.addGap(14, 30, Short.MAX_VALUE)));
		panel_2.setLayout(gl_panel_2);

		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)),
				"Advanced Settings", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));

		JLabel lblTunnelFood = new JLabel("Min Food:");

		JLabel lblTunnelDoses = new JLabel("Min Doses:");

		spnDoses = new JSpinner();
		spnDoses.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		spnDoses.setEnabled(true);

		spnFood = new JSpinner();
		spnFood.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		spnFood.setEnabled(true);

		spinner = new JSpinner();
		spinner.setEnabled(true);

		JLabel label = new JLabel("Kill Count:");
		GroupLayout gl_panel_6 = new GroupLayout(panel_6);
		gl_panel_6
				.setHorizontalGroup(gl_panel_6
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panel_6
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panel_6
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																Alignment.TRAILING,
																gl_panel_6
																		.createSequentialGroup()
																		.addComponent(
																				label,
																				GroupLayout.DEFAULT_SIZE,
																				39,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				spinner,
																				GroupLayout.PREFERRED_SIZE,
																				43,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																gl_panel_6
																		.createSequentialGroup()
																		.addGroup(
																				gl_panel_6
																						.createParallelGroup(
																								Alignment.TRAILING,
																								false)
																						.addComponent(
																								lblTunnelFood,
																								Alignment.LEADING,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								lblTunnelDoses,
																								Alignment.LEADING,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE))
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addGroup(
																				gl_panel_6
																						.createParallelGroup(
																								Alignment.TRAILING)
																						.addComponent(
																								spnDoses,
																								GroupLayout.PREFERRED_SIZE,
																								30,
																								Short.MAX_VALUE)
																						.addComponent(
																								spnFood,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))))
										.addContainerGap()));
		gl_panel_6
				.setVerticalGroup(gl_panel_6
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panel_6
										.createSequentialGroup()
										.addGroup(
												gl_panel_6
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblTunnelDoses)
														.addComponent(
																spnDoses,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGap(12)
										.addGroup(
												gl_panel_6
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblTunnelFood)
														.addComponent(
																spnFood,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addGroup(
												gl_panel_6
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(label)
														.addComponent(
																spinner,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		panel_6.setLayout(gl_panel_6);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (int index = 0; index < 6; index++) {
					String s = modelSelected.getElementAt(index);
					switch (s) {
					case "Dharok":
						Brother.Brothers.Dharok.setKillOrder(index);
						break;
					case "Karil":
						Brother.Brothers.Karil.setKillOrder(index);
						break;
					case "Verac":
						Brother.Brothers.Verac.setKillOrder(index);
						break;
					case "Guthan":
						Brother.Brothers.Guthan.setKillOrder(index);
						break;
					case "Torag":
						Brother.Brothers.Torag.setKillOrder(index);
						break;
					case "Ahrim":
						Brother.Brothers.Ahrim.setKillOrder(index);
						break;
					}
				}

				Var.food = (Food.Edibles) cbxFood.getSelectedItem();

				Var.killCount = (int) spinner.getValue();
				Var.nextRunDoses = (int) spnDoses.getValue();
				Var.nextRunFood = (int) spnFood.getValue();
				Var.foodAmount = Integer.parseInt(txtFood.getText());
				Var.superAttack = Integer.parseInt(txtSA.getText());
				Var.superStrength = Integer.parseInt(txtSS.getText());
				Var.superDefence = Integer.parseInt(txtSD.getText());
				Var.rangingPotion = Integer.parseInt(txtRP.getText());
				Var.prayerPotion = Integer.parseInt(txtPP.getText());
				Var.arrowCount = Integer.parseInt(txtArrows.getText());
				Var.spellCount = Integer.parseInt(txtCasts.getText());

				Var.bankPath = (Pathing.PathBank) cbxBank.getSelectedItem();
				Var.barrowsPath = (Pathing.PathBarrows) cbxBarrows
						.getSelectedItem();

				Var.arrowId = Equipment.getEquipmentID(Equipment.Gear.ARROW);
				try {
					Var.fileName = JOptionPane.showInputDialog("Filename",
							"Enter your file name");
					GUISave.save();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ArrayList<String> results = new ArrayList<String>();
					File[] files = new File(Util.getWorkingDirectory()
							+ File.separator + "barrows").listFiles();
					for (File file : files) {
						if (file.isFile() && file.getName().endsWith(".ini")) {
							results.add(file.getName().replace(".ini", ""));
						}
					}
					JFrame frame = new JFrame("FileOptions");
					String[] fileNames = results.toArray(new String[results
							.size()]);
					Var.fileName = (String) JOptionPane.showInputDialog(frame,
							"Which file would you like to load?",
							"File Selection", JOptionPane.QUESTION_MESSAGE,
							null, fileNames, fileNames[0]);

				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					GUISave.load();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					GUISave.load();
				} catch (IOException e3) {
					e3.printStackTrace();
				}
				currentBrother = (Brother.Brothers) cbxBrother
						.getSelectedItem();
				cbxSpell.setSelectedItem(currentBrother.getSpell());
				chckbxUsePrayer.setSelected(!currentBrother.getPrayer().equals(
						Prayer.Prayers.None));
				chckbxUsePotions.setSelected(currentBrother.usePotions());
				chckbxRecharge.setSelected(Var.recharge);
				// if (!imageLoader.isAlive()) {
				//imageLoader.start();
				// }
				modelSelected.setSize(6);
				modelSelected.set(Brother.Brothers.Dharok.killOrder(), "Dharok");
				modelSelected.set(Brother.Brothers.Karil.killOrder(), "Karil");
				modelSelected.set(Brother.Brothers.Guthan.killOrder(), "Guthan");
				modelSelected.set(Brother.Brothers.Ahrim.killOrder(), "Ahrim");
				modelSelected.set(Brother.Brothers.Verac.killOrder(), "Verac");
				modelSelected.set(Brother.Brothers.Torag.killOrder(), "Torag");

				cbxFood.setSelectedItem(Var.food);

				spinner.setValue(Var.killCount);
				spnFood.setValue(Var.nextRunFood);
				spnDoses.setValue(Var.nextRunDoses);
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
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								panel,
																								GroupLayout.DEFAULT_SIZE,
																								122,
																								Short.MAX_VALUE)
																						.addComponent(
																								panel_6,
																								Alignment.TRAILING,
																								0,
																								0,
																								Short.MAX_VALUE))
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addComponent(
																				panel_1,
																				GroupLayout.PREFERRED_SIZE,
																				495,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								panel_4,
																								GroupLayout.PREFERRED_SIZE,
																								193,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								panel_3,
																								GroupLayout.PREFERRED_SIZE,
																								193,
																								GroupLayout.PREFERRED_SIZE)))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				panel_2,
																				GroupLayout.DEFAULT_SIZE,
																				492,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addComponent(
																				panel_5,
																				GroupLayout.PREFERRED_SIZE,
																				121,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addComponent(
																												btnSave,
																												GroupLayout.PREFERRED_SIZE,
																												88,
																												GroupLayout.PREFERRED_SIZE)
																										.addGap(18)
																										.addComponent(
																												btnLoad,
																												GroupLayout.PREFERRED_SIZE,
																												87,
																												GroupLayout.PREFERRED_SIZE))
																						.addComponent(
																								btnStart,
																								GroupLayout.PREFERRED_SIZE,
																								193,
																								GroupLayout.PREFERRED_SIZE))))
										.addGap(22)));
		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createParallelGroup(
																				Alignment.BASELINE)
																		.addComponent(
																				panel_1,
																				GroupLayout.PREFERRED_SIZE,
																				349,
																				GroupLayout.PREFERRED_SIZE)
																		.addGroup(
																				groupLayout
																						.createSequentialGroup()
																						.addComponent(
																								panel,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addPreferredGap(
																								ComponentPlacement.RELATED,
																								8,
																								Short.MAX_VALUE)
																						.addComponent(
																								panel_6,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				panel_3,
																				GroupLayout.PREFERRED_SIZE,
																				98,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(5)
																		.addComponent(
																				panel_4,
																				0,
																				0,
																				Short.MAX_VALUE)))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																panel_2,
																GroupLayout.PREFERRED_SIZE,
																93,
																Short.MAX_VALUE)
														.addComponent(
																panel_5,
																GroupLayout.DEFAULT_SIZE,
																93,
																Short.MAX_VALUE)
														.addGroup(
																Alignment.LEADING,
																groupLayout
																		.createSequentialGroup()
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING,
																								false)
																						.addComponent(
																								btnLoad,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								btnSave,
																								GroupLayout.DEFAULT_SIZE,
																								38,
																								Short.MAX_VALUE))
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addComponent(
																				btnStart,
																				GroupLayout.DEFAULT_SIZE,
																				44,
																				Short.MAX_VALUE)))
										.addGap(33)));
		getContentPane().setLayout(groupLayout);
	}

	private static Image getIcon(int id) {
		String url = "http://www.runelocus.com/img/items/" + id + ".png";
		try {
			return ImageIO.read(new URL(url));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	Thread imageLoader = new Thread(new Runnable() {
		public void run() {
			while (Var.gui.isVisible()) {
				try {
					if (currentBrother.equals(Brother.Brothers.Dharok)) {
						if (picBro != null) {
							picBro.setIcon(new ImageIcon(dharok));
						} else {
							picBro = new JLabel(new ImageIcon(dharok));
						}
					} else if (currentBrother.equals(Brother.Brothers.Ahrim)) {
						if (picBro != null) {
							picBro.setIcon(new ImageIcon(ahrim));
						} else {
							picBro = new JLabel(new ImageIcon(ahrim));
						}
					} else if (currentBrother.equals(Brother.Brothers.Torag)) {
						if (picBro != null) {
							picBro.setIcon(new ImageIcon(torag));
						} else {
							picBro = new JLabel(new ImageIcon(torag));
						}
					} else if (currentBrother.equals(Brother.Brothers.Guthan)) {
						if (picBro != null) {
							picBro.setIcon(new ImageIcon(guthan));
						} else {
							picBro = new JLabel(new ImageIcon(guthan));
						}
					} else if (currentBrother.equals(Brother.Brothers.Verac)) {
						if (picBro != null) {
							picBro.setIcon(new ImageIcon(verac));
						} else {
							picBro = new JLabel(new ImageIcon(verac));
						}
					} else if (currentBrother.equals(Brother.Brothers.Karil)) {
						if (picBro != null) {
							picBro.setIcon(new ImageIcon(karil));
						} else {
							picBro = new JLabel(new ImageIcon(karil));
						}
					}
					if (currentBrother.getEquipmentIds() != null
							&& currentBrother.getEquipmentIds().length > 0) {
						if (currentBrother.getEquipmentIds()[0][0] != -1) {
							if (picHelm != null) {
								picHelm.setIcon(new ImageIcon(
										getIcon(currentBrother
												.getEquipmentIds()[0][0])));
							} else {
								picHelm = new JLabel(new ImageIcon(
										getIcon(currentBrother
												.getEquipmentIds()[0][0])));
							}
						} else {
							if (picHelm != null) {
								picHelm.setIcon(new ImageIcon(helm));
							} else {
								picHelm = new JLabel(new ImageIcon(helm));
							}
						}
						if (currentBrother.getEquipmentIds()[1][0] != -1) {
							if (picCape != null) {
								picCape.setIcon(new ImageIcon(
										getIcon(currentBrother
												.getEquipmentIds()[1][0])));
							} else {
								picCape = new JLabel(new ImageIcon(
										getIcon(currentBrother
												.getEquipmentIds()[1][0])));
							}
						} else {
							if (picCape != null) {
								picCape.setIcon(new ImageIcon(cape));
							} else {
								picCape = new JLabel(new ImageIcon(cape));
							}
						}
						if (currentBrother.getEquipmentIds()[2][0] != -1) {
							if (picNeck != null) {
								picNeck.setIcon(new ImageIcon(
										getIcon(currentBrother
												.getEquipmentIds()[2][0])));
							} else {
								picNeck = new JLabel(new ImageIcon(
										getIcon(currentBrother
												.getEquipmentIds()[2][0])));
							}
						} else {
							if (picNeck != null) {
								picNeck.setIcon(new ImageIcon(necklace));
							} else {
								picNeck = new JLabel(new ImageIcon(necklace));
							}
						}
						if (currentBrother.getEquipmentIds()[3][0] != -1) {
							if (picSword != null) {
								picSword.setIcon(new ImageIcon(
										getIcon(currentBrother
												.getEquipmentIds()[3][0])));
							} else {
								picSword = new JLabel(new ImageIcon(
										getIcon(currentBrother
												.getEquipmentIds()[3][0])));
							}
						} else {
							if (picSword != null) {
								picSword.setIcon(new ImageIcon(weapon));
							} else {
								picSword = new JLabel(new ImageIcon(weapon));
							}
						}
						if (currentBrother.getEquipmentIds()[4][0] != -1) {
							if (picBody != null) {
								picBody.setIcon(new ImageIcon(
										getIcon(currentBrother
												.getEquipmentIds()[4][0])));
							} else {
								picBody = new JLabel(new ImageIcon(
										getIcon(currentBrother
												.getEquipmentIds()[4][0])));
							}
						} else {
							if (picBody != null) {
								picBody.setIcon(new ImageIcon(body));
							} else {
								picBody = new JLabel(new ImageIcon(body));
							}
						}
						if (currentBrother.getEquipmentIds()[5][0] != -1) {
							if (picShield != null) {
								picShield.setIcon(new ImageIcon(
										getIcon(currentBrother
												.getEquipmentIds()[5][0])));
							} else {
								picShield = new JLabel(new ImageIcon(
										getIcon(currentBrother
												.getEquipmentIds()[5][0])));
							}
						} else {
							if (picShield != null) {
								picShield.setIcon(new ImageIcon(shield));
							} else {
								picShield = new JLabel(new ImageIcon(shield));
							}
						}
						if (currentBrother.getEquipmentIds()[6][0] != -1) {
							if (picLegs != null) {
								picLegs.setIcon(new ImageIcon(
										getIcon(currentBrother
												.getEquipmentIds()[6][0])));
							} else {
								picLegs = new JLabel(new ImageIcon(
										getIcon(currentBrother
												.getEquipmentIds()[6][0])));
							}
						} else {
							if (picLegs != null) {
								picLegs.setIcon(new ImageIcon(legs));
							} else {
								picLegs = new JLabel(new ImageIcon(legs));
							}
						}
						if (currentBrother.getEquipmentIds()[7][0] != -1) {
							if (picGloves != null) {
								picGloves.setIcon(new ImageIcon(
										getIcon(currentBrother
												.getEquipmentIds()[7][0])));
							} else {
								picGloves = new JLabel(new ImageIcon(
										getIcon(currentBrother
												.getEquipmentIds()[7][0])));
							}
						} else {
							if (picGloves != null) {
								picGloves.setIcon(new ImageIcon(gloves));
							} else {
								picGloves = new JLabel(new ImageIcon(gloves));
							}
						}
						if (currentBrother.getEquipmentIds()[8][0] != -1) {
							if (picBoots != null) {
								picBoots.setIcon(new ImageIcon(
										getIcon(currentBrother
												.getEquipmentIds()[8][0])));
							} else {
								picBoots = new JLabel(new ImageIcon(
										getIcon(currentBrother
												.getEquipmentIds()[8][0])));
							}
						} else {
							if (picBoots != null) {
								picBoots.setIcon(new ImageIcon(boots));
							} else {
								picBoots = new JLabel(new ImageIcon(boots));
							}
						}
						if (currentBrother.getEquipmentIds()[9][0] != -1) {
							if (picRing != null) {
								picRing.setIcon(new ImageIcon(
										getIcon(currentBrother
												.getEquipmentIds()[9][0])));
							} else {
								picRing = new JLabel(new ImageIcon(
										getIcon(currentBrother
												.getEquipmentIds()[9][0])));
							}
						} else {
							if (picRing != null) {
								picRing.setIcon(new ImageIcon(ring));
							} else {
								picRing = new JLabel(new ImageIcon(ring));
							}
						}
						if (currentBrother.getEquipmentIds()[10][0] != -1) {
							if (picArrow != null) {
								picArrow.setIcon(new ImageIcon(
										getIcon(currentBrother
												.getEquipmentIds()[10][0])));
							} else {
								picArrow = new JLabel(new ImageIcon(
										getIcon(currentBrother
												.getEquipmentIds()[10][0])));
							}
						} else {
							if (picArrow != null) {
								picArrow.setIcon(new ImageIcon(arrow));
							} else {
								picArrow = new JLabel(new ImageIcon(arrow));
							}
						}
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	});

	private static Image getImage(String url) {
		try {
			return ImageIO.read(new URL(url));
		} catch (IOException e) {
			return null;
		}
	}
}