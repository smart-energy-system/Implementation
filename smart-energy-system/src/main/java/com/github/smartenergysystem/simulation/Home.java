package com.github.smartenergysystem.simulation;

public class Home extends Consumer {
	public Home(double floorAreaSize, double averageDailyOccupancy) {
		super(floorAreaSize, averageDailyOccupancy);
		this.setHourlyBaseDemandPerSquareMeter(new double[]{ 0.003321709 ,0.00262819, 0.002343779, 0.002292816, 0.002354617, 0.002551405,
				0.002834671, 0.004321225, 0.005343769, 0.005596205, 0.006084645, 0.005859718, 0.006483234, 0.006688364,
				0.005575756, 0.005267263, 0.005505535, 0.005298151, 0.006413157, 0.00754156, 0.007825452, 0.007409206,
				0.005974084, 0.004756613 });

	}

}
