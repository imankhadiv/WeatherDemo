/**
 * 
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
/**
 * MainFram.java
 * 
 * @version 1.1 1 January 2013
 * 
 * @author Iman Rastkhadiv
 * 
 */
public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	JTextField urlField;
	JButton showButton;
	URLListener urlListener;
  
	public MainFrame() {
		super("MainFrame Window");
		FormPanel form = new FormPanel();
		showButton = new JButton("Show");
		form.setURL();
		urlField = new JTextField(form.url);

		getContentPane().add(form, BorderLayout.CENTER);
		getContentPane().add(urlField, BorderLayout.NORTH);

		form.setURLListener(new URLListener() {
			@Override
			public void changeURL(String url) {
				urlField.setText(url);
			}

		});
		showButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				urlListener.changeURL(urlField.getText());//this pass the url to the WeatherURLService constructor in the TestDrive class
			}
		});
		add(showButton, BorderLayout.SOUTH);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		//urlField.setEditable(false);
		setVisible(true);
		setSize(750, 250);
		setLocation((dim.width - this.getWidth()) / 2, 0);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void setURLListener(URLListener url) {
		this.urlListener = url;
	}
}
