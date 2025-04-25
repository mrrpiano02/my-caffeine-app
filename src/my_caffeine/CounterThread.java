package my_caffeine;
import javax.swing.SwingWorker;
import java.awt.Robot;
import java.awt.AWTException;
import javax.swing.JLabel;

public class CounterThread {

	public volatile boolean pause;
	public volatile boolean killSignal; //terminates thread
	
	// creates new thread + resets flags
	public CounterThread() {
		pause = false;
		killSignal = false;
	}
	
	public void countDown(Clock c, JLabel timeDisplay) {
		try {
			Robot keepAwake = new Robot(); // object simulates key presses
			SwingWorker<Object, Object> s = new SwingWorker<Object, Object>() {
				
				@Override
				protected Integer doInBackground() throws Exception {
					while (!c.isDone()) {
						
						if (killSignal) {
							continue;
						}
						if (!pause) {
							if (c.getTimeLimit()) {
								
								// decrement time
								
								c.decrementTime();
								timeDisplay.setText(c.displayedTime);
								
							}
							
							// time increases if execution has no time limit
							else {
								c.incrementTime();
							}
							
							// press F15 at start of every minute (rawTime % 60 = 0)
							if (c.pressVirtualKey()) {
								keepAwake.keyPress(0x7e);
								keepAwake.keyRelease(0x7e);
							}
						}

						// sleep thread for 1 second
						Thread.sleep(1000);
					}

					return c.isDone() ? 0 : 1;
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
