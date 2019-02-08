package com.github.smartenergysystem.services;

import com.github.smartenergysystem.simulation.SmartGridSolverSolution;

import java.util.Date;

public interface ISimulationControllerService {
	public SmartGridSolverSolution solve(int calculationBound, int exportPrice, Date startDate, Date endDate, int batteryFillLevelinWatt, int maxSteps, int timeout);
}
