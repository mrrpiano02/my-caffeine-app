package my_caffeine;
import javax.swing.SwingWorker;
import java.awt.Robot;
import java.awt.AWTException;
import javax.swing.JLabel;
import java.time.LocalTime;

public class CounterThread {

	static boolean pause;
	static boolean killSignal; //terminates thread
	
	// creates new thread + resets flags
	public CounterThread() {
		pause = false;
		killSignal = false;
	}
	
	public void countDown(Clock c, JLabel timeDisplay) {
		try {
			Robot keepAwake = new Robot(); // object simulates key presses
			SwingWorker<Object, Object> s = new SwingWorker<Object, Object>() {
				
				LocalTime t1 = LocalTime.now();
				LocalTime t2 = LocalTime.now();
				
				@Override
				protected String doInBackground() throws Exception {
					while (!c.isDone()) {
						
						if (killSignal) {
							continue;
						}
						if (!pause) {
							if (c.getTimeLimit()) {
								t2 = LocalTime.now();
								
								// decrement time if one second since last decrement has passed
								if (t2.isAfter(t1.plusSeconds(1))) {
									c.decrementTime();
									timeDisplay.setText(c.displayedTime);
									
									t1 = LocalTime.now();
								}
							}
							
							// time increases if execution has no time limit
							else {
								c.incrementTime();
							}
							
							// press F15 at start of every minute (rawTime % 60 = 0)
							if (c.pressVirtualKey()) {
								keepAwake.keyPress(0x7e);
								keepAwake.keyRelease(0x7e);
								System.out.println("signal sent");
							}
						}

						Thread.sleep(1000);
					}

					return "done";
				}
			};
			
			s.execute();
		}
		catch (AWTException e) {
			e.printStackTrace();
			return;
		}
		
	}

	public void pause() {
		pause = true;
	}
	
	public synchronized void resume() {
		pause = false;
	}
	
	public void kill() {
		killSignal = true;
	}
}
