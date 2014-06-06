import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/**
 * TextPanel.java
 * 
 * This class adds a TextArea to a panel to show different data when the user
 * clicks a button
 * 
 * @version 1.1 1 January 2013
 * 
 * @author Iman Rastkhadiv
 * 
 */
@SuppressWarnings("serial")
public class TextPanel extends JPanel {
	JTextArea textArea;

	public TextPanel() {

		textArea = new JTextArea();
		setLayout(new BorderLayout());
		textArea.setBackground(new Color(44, 155, 144));
		JScrollPane scroller = new JScrollPane(textArea);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scroller, BorderLayout.CENTER);
	}

	public void appendText(String text) {
		textArea.append(text);

	}

}
