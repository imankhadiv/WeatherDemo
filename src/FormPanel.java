import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * FormPanel.java
 * 
 * This class creates a form with 4 different drop down boxes allowing the user
 * to choose a specific airport, year, month and day. These data are used to
 * construct an url.
 * 
 * @version 1.1 1 January 2013
 * 
 * @author Iman Rastkhadiv
 * 
 */
public class FormPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final String[] airports = { "London Heathrow Airport",
			"Birmingham Airport", "Manchester Airport" };
	private final String[] days = FormPanel.makeArray(1, 31);
	private final String[] months = FormPanel.makeArray(1, 12);
	private final String[] years = FormPanel.makeArray(2000, 2015);
	private String day = "31";
	private String month = "12";
	private String year = "2015";
	private String airport = "EGLL";
	public String url;
	private URLListener urlListener;

	public FormPanel() {

		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.VERTICAL;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 0;
		gc.weighty = 0;
		gc.ipady = 20;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		add(new JLabel("Airport:  "), gc);

		gc.gridx = 1;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;

		add(makeComboBox(airports, "airport"), gc);

		gc.gridx = 0;
		gc.gridy = 1;
		gc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("Year:  "), gc);

		gc.gridx = 1;
		gc.gridy = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		add(makeComboBox(years, "year"), gc);

		gc.gridx = 0;
		gc.gridy = 2;
		gc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("Month:  "), gc);

		gc.gridx = 1;
		gc.gridy = 2;
		gc.anchor = GridBagConstraints.LINE_START;
		add(makeComboBox(months, "month"), gc);

		gc.gridx = 0;
		gc.gridy = 3;
		gc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("Day:  "), gc);

		gc.gridx = 1;
		gc.gridy = 3;
		gc.anchor = GridBagConstraints.LINE_START;
		add(makeComboBox(days, "day"), gc);

		this.setBackground(Color.gray);

	}

	public void setURLListener(URLListener url) {
		this.urlListener = url;
	}

	public void setURL() {
		url = "http://www.wunderground.com/history/airport/" + airport + "/"
				+ year + "/" + month + "/" + day
				+ "/DailyHistory.html?HideSpecis=1&format=1";

	}

	private JComboBox<String> makeComboBox(String[] input, String name) {
		JComboBox<String> comboBox = new JComboBox<>(input);
		comboBox.setName(name);
		comboBox.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				JComboBox<String> box = (JComboBox<String>) e.getSource();
				String name = box.getName();
				if (name.equals("airport"))
					airport = getICAOCode((String) box.getSelectedItem());
				else if (name.equals("year"))
					year = (String) box.getSelectedItem();
				else if (name.equals("month"))
					month = (String) box.getSelectedItem();
				else
					day = (String) box.getSelectedItem();
				setURL();
				if (urlListener != null)
					urlListener.changeURL(url);
			}
		});
		return comboBox;
	}

	private static String[] makeArray(int first, int last) {
		int length = last - first + 1;
		String[] s = new String[length];
		for (int i = 0, val = last; i < length; i++, val--) {
			s[i] = String.valueOf(val);
		}
		return s;
	}

	private String getICAOCode(String name) {
		if (name.equals("London Heathrow Airport")) {
			return "EGLL";
		} else if (name.equals("Birmingham Airport")) {
			return "EGBB";
		} else
			return "EGCB";
	}
}
