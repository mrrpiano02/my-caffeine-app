package my_caffeine;

import java.util.Hashtable;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Caffeine {
	private JFrame window;
	private JLabel timerLabel;
	private JSlider timerVal;
	private JPanel timerContainer;
	private JPanel timeDisplayContainer;
	private JLabel timeDisplay;
	private JPanel buttonContainer;
	private JButton toggleCaffeine;
	private JButton stopCaffeine;
	private JCheckBox infinite;
	private JPanel checkboxContainer;
	
	private Clock c;
	
	// creates labels for timerVal slider element
	private Hashtable createLabelTable() {
		Hashtable<Integer, JLabel> map = new Hashtable<>();
		for (int i=0; i<=120; i+=30) {
			map.put((i), new JLabel(String.valueOf(i)));
		}
		
		return map;
	}
	
	public void init() {
		// declarations
		c = new Clock();
		
		window = new JFrame("my-caffeine Control Center");
		
		timerContainer = new JPanel();
		timerLabel = new JLabel("Time (mins): ");
		timerVal = new JSlider(JSlider.HORIZONTAL, 0, 120, 30);
		
		timeDisplayContainer = new JPanel();
		timeDisplay = new JLabel(c.displayedTime);
		
		checkboxContainer = new JPanel();
		infinite = new JCheckBox("no time limit", false);
		
		buttonContainer = new JPanel();
		toggleCaffeine = new JButton("Caffeinate");
		stopCaffeine = new JButton("Decaffeinate");
		
		// define GridBagConstraints to center elements
		GridBagConstraints center = new GridBagConstraints();
		center.gridwidth = GridBagConstraints.REMAINDER;
		center.fill = GridBagConstraints.HORIZONTAL;
		center.insets = new Insets(0 ,0, 10, 0);
		
		// settings
		window.setSize(300,400);
		window.setLayout(new BoxLayout(window.getContentPane(), BoxLayout.PAGE_AXIS));
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		timerVal.setMajorTickSpacing(15);
		timerVal.setMinorTickSpacing(5);
		timerVal.setPaintLabels(true);
		timerVal.setPaintTicks(true);
		timerVal.setLabelTable(createLabelTable());
		
		timeDisplay.setForeground(Color.red);
		timeDisplay.setSize(new Dimension(200, 80));
		timeDisplay.setFont(new Font("Segment7", Font.BOLD, 48));
		
		stopCaffeine.setEnabled(false);
		
		timeDisplayContainer.setBackground(Color.black);
		timeDisplayContainer.setSize(new Dimension(270, 100));
		timeDisplayContainer.setLayout(new GridBagLayout());
		
		checkboxContainer.setLayout(new FlowLayout(FlowLayout.LEFT));
		checkboxContainer.setMaximumSize(new Dimension(270, 10));
		
		timerContainer.setLayout(new FlowLayout());
		buttonContainer.setLayout(new GridBagLayout());
		
		// place elements in containers
		timerContainer.add(timerLabel);
		timerContainer.add(timerVal);
		buttonContainer.add(toggleCaffeine, center);
		timeDisplayContainer.add(timeDisplay, center);
		buttonContainer.add(Box.createRigidArea(new Dimension(0,10)));
		buttonContainer.add(stopCaffeine, center);
		checkboxContainer.add(infinite);

		window.add(Box.createVerticalStrut(20));
		window.add(timeDisplayContainer);
		window.add(Box.createVerticalStrut(20));
		window.add(timerContainer);
		window.add(checkboxContainer);
		window.add(Box.createVerticalStrut(20));
		window.add(buttonContainer);
		
		// add event listeners
		timerVal.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				JSlider source = (JSlider)e.getSource();
				if (source.getValueIsAdjusting() && source.isEnabled()) {
					GUIControls.changeTimerValue(timerVal.getValue(), c);
					if (c.getTimeLimit()) GUIControls.updateTimer(timeDisplay, c);
				}
			}
		});
		
		infinite.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				GUIControls.infiniteToggle(c);
				GUIControls.updateTimer(timeDisplay, c);
				timerVal.setEnabled(c.getTimeLimit());
			}
		});
		
		toggleCaffeine.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GUIControls.toggleTimer(timeDisplay, toggleCaffeine, c);
				timerVal.setEnabled(false);
				infinite.setEnabled(false);
				stopCaffeine.setEnabled(true);
			}
		});
		
		stopCaffeine.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				GUIControls.reset(timeDisplay, toggleCaffeine, c);
				timerVal.setEnabled(true);
				infinite.setEnabled(true);
				stopCaffeine.setEnabled(false);
			}
		});
		
		// finally, display window
		window.setVisible(true);
	}
	
	public Caffeine() {
		init();
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Caffeine();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
