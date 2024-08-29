package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.HashMap;

import game.Interfaces.IArena;
import game.Interfaces.ICompetitor;
import game.entities.sportsman.WinterSportsman;
import utilities.Point;

public class InfoPanel extends JFrame {
	private final DefaultTableModel model;

	public InfoPanel() {
		setPreferredSize(new Dimension(500, 300));
		setTitle("Competitors information");
		setLayout(new BorderLayout());

		String[] columnNames = { "Name", "Speed", "Max speed", "Location", "Finished" };
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

			String name = racer.getName();
			double speed = racer.getSpeed();
			double maxSpeed = racer.getMaxSpeed();
			Point location = racer.getLocation();
			boolean finished = arena.isFinished(racer);

			// Convert location to a readable string format
			String locationString = String.format("(%.2f, %.2f)", location.getX(), location.getY());

			// Add competitor info to the table
			model.addRow(new Object[] { name, speed, maxSpeed, locationString, finished });
		});
	}
}
