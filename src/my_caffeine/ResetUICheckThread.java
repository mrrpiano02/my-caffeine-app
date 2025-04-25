package my_caffeine;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.SwingWorker;

public class ResetUICheckThread {

	TimerEvt t;
	private boolean kill = false;
	
	public ResetUICheckThread(TimerEvt t) {
		this.t = t;
	}
	
	public void check(JSlider timerVal, JCheckBox infinite, JButton stopCaffeine, JButton toggle) {
		SwingWorker<Object, Object> s = new SwingWorker<Object, Object>() {
			@Override
			public Integer doInBackground() {
				
				while (!t.checkIfDone(timerVal, infinite, stopCaffeine, toggle) && !kill==true) {
					
					try {
						Thread.sleep(60000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return 0;
			}
		};
		
		s.execute();
	}
	
	void kill() {
		kill = true;
	}
}
