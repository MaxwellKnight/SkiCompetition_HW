package gui;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import game.Interfaces.IArena;
import game.Interfaces.ICompetitor;
import game.entities.sportsman.WinterSportsman;
import utilities.Point;

public class InfoFrame extends JFrame implements Observer {
	private final DefaultTableModel model;

	public InfoFrame() {
		setPreferredSize(new Dimension(500, 300));
		setTitle("Competitors Information");
		setLayout(new BorderLayout());

		String[] columnNames = { "Id", "Name", "Speed", "Max Speed", "Location", "Finished" };
		model = new DefaultTableModel(columnNames, 0);

		JTable table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);

		pack();
	}

	public void updateCompetitorInfo(HashMap<ICompetitor, JLabel> racersLabels, IArena arena) {
		model.setRowCount(0); // Clear the table before updating
		racersLabels.forEach((key, value) -> {
			WinterSportsman racer = (WinterSportsman) key;

			String id = Integer.toString(racer.getId());
			String name = racer.getName();
			double speed = racer.getSpeed();
			double maxSpeed = racer.getMaxSpeed();
			Point location = racer.getLocation();
			String status = getStatus(racer, arena);

			// Convert location to a readable string format
			String locationString = String.format("(%.2f, %.2f)", location.getX(), location.getY());

			// Add competitor info to the table
			model.addRow(new Object[] { id, name, speed, maxSpeed, locationString, status });
		});
	}

	private String getStatus(WinterSportsman racer, IArena arena) {
		return "TODO";
	}

	@Override
	public void update(Observable o, Object arg) {
	}
}
