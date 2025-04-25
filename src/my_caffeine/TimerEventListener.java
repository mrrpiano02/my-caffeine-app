package my_caffeine;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JSlider;

public interface TimerEventListener {
	boolean onTimerDone(JSlider timerVal, JCheckBox infinite, JButton stopCaffeine, JButton toggleCaffeine);
}
