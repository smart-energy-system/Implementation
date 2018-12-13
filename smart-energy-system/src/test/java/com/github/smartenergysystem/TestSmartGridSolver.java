package com.github.smartenergysystem;

import com.github.smartenergysystem.simulation.*;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TestSmartGridSolver {
	
	@Test
	public void testSmartGridSolver() {
		int[] supplerSummedForEachHour = new int[]{50,50,50,50};
            int[] consumer1 = new int[]{10, 10, 10, 10};
            int[] consumer2 = new int[]{20, 20, 20, 20};
            int[] exportPricePerUnit = new int[]{10,5,5,5};
            int[] importCostPerUnit = new int[]{5, 5, 5, 5};
		Battery battery = new Battery();
		battery.setMaximumChargingRate(5);
		battery.setMaximumDischargingRate(10);
		battery.setMaximumStoredEnergy(20);
		SmartGridSolver solver = new SmartGridSolver(100);
		long millis = System.currentTimeMillis();
		SmartGridSolverSolution solution = solver.solve(supplerSummedForEachHour,consumer1,30,
				consumer2,40,exportPricePerUnit,importCostPerUnit,battery,80);
		//Solver should shift demand away form first step
		assertThat(solution.getSolutionSteps().get(0).getPnegShift()[0],
				greaterThan(0));
		assertThat(solution.getSolutionSteps().get(0).getPnegShift()[1],
				greaterThan(0));
		int PnegShiftSum0 = 0;
		int PposShiftSum0 = 0;
		int PnegShiftSum1 = 0;
		int PposShiftSum1 = 0;
		for(SmartGridSolverSolutionStep step : solution.getSolutionSteps()){
			PnegShiftSum0 = PnegShiftSum0 + step.getPnegShift()[0];
			PposShiftSum0 = PposShiftSum0 + step.getPposShift()[0];
			PnegShiftSum1 = PnegShiftSum1 + step.getPnegShift()[1];
			PposShiftSum1 = PposShiftSum1 + step.getPposShift()[1];
		}
		//Pos und neg shift should be the same
		assertTrue(PnegShiftSum0 == PposShiftSum0);
		assertTrue(PnegShiftSum1 == PposShiftSum1);
	}

}
