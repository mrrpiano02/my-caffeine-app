package my_caffeine;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JButton;
import javax.swing.JCheckBox;

// wrapper class to keep GUI controls more organized and labeled
public final class GUIControls {
	
	public GUIControls() {
		throw new IllegalStateException("GUIControls is not an instantiable class.");
	}
	
	public static void updateTimer(JLabel timeDisplay, Clock c) {
		timeDisplay.setText(c.displayedTime);
	}
	
	public static void changeTimerValue(int value, Clock c) {
		c.changeTime(value);
	}
	
	public static void changeDisplayTime(JLabel timeDisplay, Clock c) {
		c.updateDisplayTime(timeDisplay);
	}
	
	public static void infiniteToggle(Clock c) {
		c.toggleTimeLimit();
	}
	
	public static void toggleTimer(JLabel timeDisplay, JSlider timerVal, JCheckBox infinite, JButton stopCaffeine, JButton toggle, Clock c) {
		if (c.running()) {
			c.pauseTimer();
			toggle.setText("Resume");
		}
		else {
			c.startTimer(timeDisplay, timerVal, infinite, stopCaffeine, toggle, c);
			toggle.setText("Pause");
		}
	}
	
	public static void reset(JLabel timeDisplay, JButton toggle, Clock c) {
		c.endTimer();
		timeDisplay.setText(c.displayedTime);
		toggle.setText("Caffeinate");
	}
}
