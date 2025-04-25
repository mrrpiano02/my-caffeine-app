package my_caffeine;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JSlider;

public class TimerEvt {

	List<TimerEventListener> tels = new ArrayList<>();
	public void addListener(TimerEventListener c) {
		// TODO Auto-generated method stub
		tels.add(c);
	}

	public boolean checkIfDone(JSlider timerVal, JCheckBox infinite, JButton stopCaffeine, JButton toggle) {
		for (TimerEventListener c : tels) {
			if (c.onTimerDone(timerVal, infinite, stopCaffeine, toggle)) return true;
		}
		return false;
	}
}
