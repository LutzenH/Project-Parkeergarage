package parkeersimulator.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import java.awt.Dimension;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MainFrame extends JFrame {

	public MainFrame(String string, JPanel controlPanel, JPanel[] graphPanels, JPanel simulationPanel) {
		super(string);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setJMenuBar(createMenuBar());
		
		JSplitPane splitPane = new JSplitPane();
		this.getContentPane().add(splitPane);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setLeftComponent(splitPane_1);
		
		splitPane_1.setRightComponent(controlPanel);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		tabbedPane.setPreferredSize(new Dimension(300, 200));
		tabbedPane.setMinimumSize(new Dimension(300, 200));
		
		splitPane_1.setLeftComponent(tabbedPane);
		
		for(JPanel panel : graphPanels)
		{
			tabbedPane.addTab(panel.getName(), null, panel, null);
		}
		
		splitPane.setRightComponent(simulationPanel);
	}

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmExport = new JMenuItem("Export");
		mnFile.add(mntmExport);
		
		JMenuItem mntmQuit = new JMenuItem("Quit");
		mnFile.add(mntmQuit);
		
		JMenu mnSettings = new JMenu("Settings");
		menuBar.add(mnSettings);
		
		JMenuItem mntmReset = new JMenuItem("Reset");
		mnSettings.add(mntmReset);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		
		return menuBar;
	}
}