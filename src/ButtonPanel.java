import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * ButtonPanel.java
 * 
 * @version 1.1 1 January 2013
 * 
 * @author Iman Rastkhadiv
 * 
 */
public class ButtonPanel extends JPanel {

	
	private static final long serialVersionUID = 1L;
	private QueryListener query;

	public ButtonPanel() {
		add(makeButton("graphs"));
		add(makeButton("temperature"));
		add(makeButton("pressure"));
		add(makeButton("weather"));
		add(makeButton("wind"));
		add(makeButton("total precipitation"));
		add(makeButton("average temperature"));
		add(makeButton("pressure trend"));
		add(makeButton("quit"));
		this.setBackground(Color.gray);
	}

	public void setQuery(QueryListener query) {
		this.query = query;
	}

	public JButton makeButton(String name) {
		JButton btn = new JButton(name);
		btn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String btnName = e.getActionCommand();
				if (query != null)
					query.changeQuery(btnName);
			}
		});
		return btn;
	}

}
