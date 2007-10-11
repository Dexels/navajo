import java.io.*;
import java.net.*;
import javax.swing.*;
import org.apache.batik.swing.*;
public class TestFrame extends JPanel {

	/**
	 * @param args
	 * @throws IOException
	 */ 
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		final JFrame frame = new JFrame("Test");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(600, 400);

		final JSVGCanvas tf = new JSVGCanvas();

		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(tf);
		
		tf.setBounds(100,100, 300, 200 );
		tf.setDocumentState(JSVGCanvas.ALWAYS_DYNAMIC);
		URL resource = TestFrame.class.getClassLoader().getResource("boxes.svg");
		tf.setURI(resource.toString());
		frame.setVisible(true);
		
	}
}
