import javax.swing.SwingUtilities;

public class RunWindowGUI implements Runnable {
		public void run() {
			new WindowGUI();
        }
		public static void main(String[] args) {
			SwingUtilities.invokeLater(new RunWindowGUI());
	    }
}