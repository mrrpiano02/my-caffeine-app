package my_caffeine;
import javax.swing.JLabel;

public class Clock {
	
	private int rawTime, oldRawTime;
	private boolean timeLimit, isRunning = false, started = false;
	public String displayedTime;
	private CounterThread ct;
	
	Clock() {
		rawTime = 1800;
		oldRawTime = rawTime;
		timeLimit = true;
		displayedTime = "00 30 00";
	}
	
	// time limit checkbox is toggled
	public void toggleTimeLimit() {
		timeLimit = !timeLimit;
		setRawTime(timeLimit);
		displayedTime = timeLimit ? rawToDisplay(rawTime) : "[/]";
	}
	
	// takes raw time value (seconds only) and converts it to friendly hh:mm:ss format
	private String rawToDisplay(int rawTime) {
		int rawHour = rawTime / 3600;
		int rawMinute = (rawTime - (3600*rawHour)) / 60;
		int rawSecond = rawTime % 60;
		
		String hour = String.format("%02d", rawHour);
		String minute = String.format("%02d", rawMinute);
		String second = String.format("%02d", rawSecond);
		
		return hour + " " + minute + " " + second;
	}
	
	// decreases time + updates display
	public void decrementTime() {
		rawTime--;
		displayedTime = rawToDisplay(rawTime);
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
			rawTime = 0;
		}
	}
	
	// on timerVal slider change
	public void changeTime(int minutes) {
		rawTime = 60*minutes;
		displayedTime = rawToDisplay(rawTime);
		oldRawTime = rawTime;
	}
	
	// start new CounterThread if none exist or resume current thread
	public void startTimer(JLabel timeDisplay, Clock c) {
		toggleRun();
		if (!started) {
			ct = new CounterThread();

			ct.countDown(c, timeDisplay);
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
		displayedTime = rawToDisplay(rawTime);
		
		ct.kill();
		ct = null;
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
		return (rawTime < 0);
	}
}
