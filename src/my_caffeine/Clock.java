package my_caffeine;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JCheckBox;
import javax.swing.JButton;

public class Clock implements TimerEventListener {
	
	private int rawTime, oldRawTime;
	private boolean timeLimit, isRunning = false, started = false;
	private int rawHour, rawMinute, rawSecond;
	public String displayedTime;
	private CounterThread ct;
	private TimerEvt t = new TimerEvt();
	private ResetUICheckThread r;
	
	Clock() {
		rawTime = 1800;
		oldRawTime = rawTime;
		timeLimit = true;
		
		rawHour = rawTime / 3600;
		rawMinute = (rawTime-(3600*rawHour)) / 60;
		rawSecond = rawTime % 60;
		displayedTime = "00 30 00";
	}
	
	// time limit checkbox is toggled
	public void toggleTimeLimit() {
		timeLimit = !timeLimit;
		setRawTime(timeLimit);
		updateTime();
		displayedTime = timeLimit ? rawToDisplay() : "[/]";
	}
	
	// takes raw time value (seconds only) and converts it to friendly hh:mm:ss format
	private String rawToDisplay() {
		return String.format("%02d %02d %02d", rawHour, rawMinute, rawSecond);
	}
	
	public void updateTime() {
		rawHour = rawTime / 3600;
		rawMinute = (rawTime - (3600*rawHour)) / 60;
		rawSecond = rawTime % 60;
	}
	
	public void updateDisplayTime(JLabel timeDisplay) {
		displayedTime = rawToDisplay();
		timeDisplay.setText(displayedTime);
	}
	
	// decreases time + updates display
	public void decrementTime() {
		rawTime--;
		updateTime();
		displayedTime = rawToDisplay();
	}
	
	// increases time (no display update necessary since infinite-only)
	public void incrementTime() {
		rawTime++;
	}
	
	// should CounterThread press F15
	public boolean pressVirtualKey() {
		return rawTime % 60 == 0;
	}
	
	// setter used by toggleTimeLimit
	private void setRawTime(boolean timeLimit) {
		if (timeLimit) {
			rawTime = oldRawTime;
		}
		else {
			oldRawTime = rawTime;
			rawTime = 1;
		}
	}
	
	// on timerVal slider change
	public void changeTime(int minutes) {
		rawTime = 60*minutes;
		oldRawTime = rawTime;
		updateTime();
	}
	
	// start new CounterThread if none exist or resume current thread
	public void startTimer(JLabel timeDisplay, JSlider timerVal, JCheckBox infinite, JButton stopCaffeine, JButton toggleCaffeine, Clock c) {
		toggleRun();
		if (!started) {
			ct = new CounterThread();
			ct.countDown(c, timeDisplay);
			
			t = new TimerEvt();
			t.addListener(c);
			
			r = new ResetUICheckThread(t);
			r.check(timerVal, infinite, stopCaffeine, toggleCaffeine);
			started = true;
		}
		else {
			ct.resume();
		}
	}
	
	// stall CounterThread; prevent counting up/down
	public void pauseTimer() {
		toggleRun();
		ct.pause();
	}
	
	// destroy old CounterThread, reset display and program state to before timer started
	public void endTimer() {
		isRunning = false;
		rawTime = oldRawTime;
		updateTime();
		displayedTime = rawToDisplay();
		
		ct.kill();
		ct = null;
		r.kill();
		r = null;
		
		System.gc();
		
		started = false;
	}
	
	public boolean getTimeLimit() {
		return timeLimit;
	}
	
	public void toggleRun() {
		isRunning = !isRunning;
	}
	
	public boolean running() {
		return isRunning;
	}
	
	public boolean isDone() {
		return (rawTime <= 0);
	}

	// stops timer and resets UI back to state upon launch
	@Override
	public boolean onTimerDone(JSlider timerVal, JCheckBox infinite, JButton stopCaffeine, JButton toggleCaffeine) {
		// TODO Auto-generated method stub
		if (isDone()) {
			endTimer();
			timerVal.setEnabled(true);
			infinite.setEnabled(true);
			stopCaffeine.setEnabled(false);
			toggleCaffeine.setText("Caffeinate");
			return true;
		}
		return false;
	}
}
