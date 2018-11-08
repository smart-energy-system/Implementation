package com.github.smartenergysystem.simulation;

public class OfficeBuilding extends Consumer {
	public OfficeBuilding(double floorAreaSize, double averageDailyOccupancy) {
		super(floorAreaSize, averageDailyOccupancy);
		this.setHourlyBaseDemandPerSquareMeter(new double[] { 0.169545431, 0.163050214, 0.158839031, 0.156693376,
				0.152934311, 0.1513018, 0.149894943, 0.149245014, 0.152816952, 0.180625, 0.214732906, 0.323429533,
				0.261487252, 0.291452432, 0.285970644, 0.259325284, 0.265544979, 0.251969489, 0.232843443, 0.216922425,
				0.207198214, 0.195555357, 0.180615708, 0.171428935 });
	}
}
