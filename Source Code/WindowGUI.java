import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

public class WindowGUI extends JFrame implements ActionListener {
	private Font font1 = new Font("Sans-serif", Font.PLAIN, 20);
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem quitMenuItem, fileMenuItem;
	private JFileChooser fc;
	private JPanel radioButtonPanel;
	private ButtonGroup radioButtons;
	private JRadioButton radioButtonSrc, radioButtonDes;
	public static JComboBox<String> comboBox;
	
	static DefaultComboBoxModel<String> srcModel;
	static DefaultComboBoxModel<String> destModel;
	public static String currentIP;
	private static File file;
	double timestamp;
	int ipPacketSize;
	
	public static Graph g;
	
	
	public WindowGUI() {
		super("Network Packet Transmission Visualizer");

		menuBar();
		radioButtonPanel();
		comboBox();
		g = new Graph();
		add(g);
		setSize(1000, 500);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void menuBar() {
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		fileMenu.setFont(font1); fileMenu.setMnemonic('F');
		quitMenuItem = new JMenuItem("Quit"); fileMenuItem = new JMenuItem("Open trace file");
		fileMenu.add(fileMenuItem); fileMenuItem.addActionListener(WindowGUI.this);
		fileMenu.add(quitMenuItem); quitMenuItem.addActionListener(WindowGUI.this);
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);
	}

	public void radioButtonPanel() {
		radioButtonPanel = new JPanel();
		radioButtonPanel.setLayout(new GridBagLayout());
		radioButtonPanel.setBounds(0, 0, 200, 100);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = GridBagConstraints.RELATIVE;
		c.anchor = GridBagConstraints.WEST;
		
		radioButtonSrc = new JRadioButton("Source hosts"); radioButtonSrc.setFont(font1);
		radioButtonDes = new JRadioButton("Destination hosts"); radioButtonSrc.setSelected(true); radioButtonDes.setFont(font1);
	
		radioButtons = new ButtonGroup();
		radioButtons.add(radioButtonDes); 
		radioButtons.add(radioButtonSrc);
		radioButtonSrc.addActionListener(WindowGUI.this);
		radioButtonDes.addActionListener(WindowGUI.this);
		radioButtonPanel.add(radioButtonSrc, c);
		radioButtonPanel.add(radioButtonDes, c);
		add(radioButtonPanel);
	}
	
	public void comboBox() {
		comboBox = new JComboBox<String>();
		comboBox.setBounds(300, 25, 300, 30);
		comboBox.addActionListener(WindowGUI.this);
		comboBox.setVisible(false);
		add(comboBox);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == quitMenuItem) {
			System.exit(0);
		}
		if(e.getSource() == fileMenuItem) {
			fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(WindowGUI.this);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				file = fc.getSelectedFile();
				new fileReadLine(file);
				comboBox.setVisible(true);
				if(radioButtonSrc.isSelected() & file != null) {
					comboBox.setModel(srcModel);
				}
				if(radioButtonDes.isSelected() & file != null) {
					comboBox.setModel(destModel);
				}

			}
		}
		if(comboBox.isShowing()) {
		currentIP = (String) comboBox.getItemAt(0); 
		new getGraphData(file);
		g.redrawGraph();
		}
		if(e.getSource() == radioButtonSrc & file != null) {
			comboBox.setModel(srcModel);
		}
		if(e.getSource() == radioButtonDes & file != null) {
			comboBox.setModel(destModel);
		}
		if(e.getSource() == comboBox) {
			currentIP = (String) comboBox.getSelectedItem(); 
			new getGraphData(file);
			g.redrawGraph();
		}
	}
}
