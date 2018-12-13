package com.github.smartenergysystem;

import com.github.smartenergysystem.simulation.*;
import org.junit.Test;

import java.util.List;

import static com.github.smartenergysystem.simulation.SmartGridSolver.CONVERT_TO_KW;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TestSmartGridSolver {

	private static final int SOLVER_BOUND = 100;

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
		battery.setMaximumStoredEnergy(0);
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

	@Test
	public void testBattery() {
		//In the last step the supply equals the demand
		//but it is most profitable to sell the energy.
		int[] supplerSummedForEachHour = new int[]{50, 50, 50, 30}; //longer if last number is 0
		int[] consumer1 = new int[]{10, 10, 10, 10};
		int[] consumer2 = new int[]{20, 20, 20, 20};
		int[] exportPricePerUnit = new int[]{1, 1, 1, 50};
		int[] importCostPerUnit = new int[]{2, 2, 2, 2};
		int maxChargeRateInkW = 5;//It takes multiple steps to charge the battery
		Battery battery = getBattery(maxChargeRateInkW);
		SmartGridSolver solver = new SmartGridSolver(100);
		SmartGridSolverSolution solution = solver.solve(supplerSummedForEachHour, consumer1, 30,
				consumer2, 40, exportPricePerUnit, importCostPerUnit, battery, 100);
		//Should charge every step
		List<SmartGridSolverSolutionStep> steps = solution.getSolutionSteps();
		//Should charge the whole time, except for the last step
		for (int stepCount = 0; stepCount < steps.size()-1; stepCount++) {
			assertEquals(maxChargeRateInkW, steps.get(stepCount).getChargeRate());
		}
		assertThat(solution.getSolutionSteps().get(steps.size()-1).getPnegShift()[0],
				greaterThan(0));
		assertThat(solution.getSolutionSteps().get(steps.size()-1).getPnegShift()[1],
				greaterThan(0));
	}

	@Test
	public void testImport() {
		//Should never use battery
		int[] supplerSummedForEachHour = new int[]{0, 0, 0, 0};
		int[] consumer1 = new int[]{10, 10, 10, 10};
		int[] consumer2 = new int[]{20, 20, 20, 20};
		int[] exportPricePerUnit = new int[]{1, 1, 1, 1};
		int[] importCostPerUnit = new int[]{2, 2, 2, 2};
		int maxChargeRateInkW = 5;
		Battery battery = getBattery(maxChargeRateInkW);
		SmartGridSolver solver = new SmartGridSolver(SOLVER_BOUND,true);
		SmartGridSolverSolution solution = solver.solve(supplerSummedForEachHour, consumer1, 30,
				consumer2, 40, exportPricePerUnit, importCostPerUnit, battery, 80);
		//Should charge every step
		List<SmartGridSolverSolutionStep> steps = solution.getSolutionSteps();
		checkBatteryIsNeverUsed(steps);
	}

	private void checkBatteryIsNeverUsed(List<SmartGridSolverSolutionStep> steps) {
		String msg = "Should never use battery";
		steps.forEach(step -> assertEquals(msg,0,step.getChargeRate()));
		steps.forEach(step -> assertEquals(msg,0,step.getBatteryFillLevel()));
		steps.forEach(step -> assertEquals(msg,0,step.getDiscargeRate()));
	}

	@Test
	public void testExport() {
		//Should never use battery
		int Ps = 5; //Power supply summed
		int[] supplerSummedForEachHour = new int[]{Ps, Ps, Ps, Ps};
		int[] consumer1 = new int[]{0, 0, 0, 0};
		int[] consumer2 = new int[]{0, 0, 0, 0};
		int[] exportPricePerUnit = new int[]{1, 1, 1, 1};
		int[] importCostPerUnit = new int[]{2, 2, 2, 2};
		int maxChargeRateInkW = 5;
		Battery battery = getBattery(maxChargeRateInkW);
		SmartGridSolver solver = new SmartGridSolver(SOLVER_BOUND);
		SmartGridSolverSolution solution = solver.solve(supplerSummedForEachHour, consumer1, 30,
				consumer2, 40, exportPricePerUnit, importCostPerUnit, battery, 80);
		//Should charge every step
		List<SmartGridSolverSolutionStep> steps = solution.getSolutionSteps();
		checkBatteryIsNeverUsed(steps);
		steps.forEach(step->assertEquals("Should never import energy",0,step.getImportCost()));
		steps.forEach(step->assertEquals("Grid export Pg should be the same as Ps",Math.abs(step.getPg()),Ps));
		//Should never import
	}

	@Test
	public void testFirstChargeThanDischarge() {
		//Should never use battery
		int[] supplerSummedForEachHour = new int[]{6, 6, 50, 2};
		int[] consumer1 = new int[]{3, 3, 1, 1};
		int[] consumer2 = new int[]{3, 3, 1, 1};
		int[] exportPricePerUnit = new int[]{1, 1, 1, 50};
		int[] importCostPerUnit = new int[]{2, 2, 2, 2};
		//in step 3 we have an excess of energy but the exportPrice is low.
		//in the last step the exportPrice is high so it would make sense to save energy in step 3
		//and sell in step 4.

		int maxChargeRateInkW = 5;
		Battery battery = getBattery(maxChargeRateInkW);
		SmartGridSolver solver = new SmartGridSolver(SOLVER_BOUND);
		SmartGridSolverSolution solution = solver.solve(supplerSummedForEachHour, consumer1, 30,
				consumer2, 40, exportPricePerUnit, importCostPerUnit, battery, 80);
		List<SmartGridSolverSolutionStep> steps = solution.getSolutionSteps();
		for (int stepCount = 0; stepCount < steps.size()-1; stepCount++) {
			assertEquals("Should only discharge in the last step",0, steps.get(stepCount).getDiscargeRate());
		}
		assertEquals(12, steps.get(3).getDiscargeRate());
	}

	@Test
	public void testShiftDemantToLastStep() {
		//Should never use battery
		int[] supplerSummedForEachHour = new int[]{20, 20, 20, 20};
		int[] consumer1 = new int[]{10, 20, 10, 10};
		int[] consumer2 = new int[]{10, 21, 10, 10};
		int[] exportPricePerUnit = new int[]{1, 1, 1, 1};
		int[] importCostPerUnit = new int[]{1, 50, 1, 1};
		//in step 3 we have an excess of energy but the exportPrice is low.
		//in the last step the exportPrice is high so it would make sense to save energy in step 3
		//and sell in step 4.

		int maxChargeRateInkW = 5;
		Battery battery = getBatteryZero();
		SmartGridSolver solver = new SmartGridSolver(SOLVER_BOUND,true);
		SmartGridSolverSolution solution = solver.solve(supplerSummedForEachHour, consumer1, 30,
				consumer2, 40, exportPricePerUnit, importCostPerUnit, battery, 80);
		List<SmartGridSolverSolutionStep> steps = solution.getSolutionSteps();
		checkBatteryIsNeverUsed(steps);
		assertThat("Should reduce import in Step 1 because importing is expensive",steps.get(1).getPnegShift()[0],
				greaterThan(0));
		assertThat("Should reduce import in Step 1 because importing is expensive",steps.get(1).getPnegShift()[1],
				greaterThan(0));
	}


	private Battery getBattery(int maxChargeRateInkW) {
		return getBattery(maxChargeRateInkW, 20 * CONVERT_TO_KW);
	}

	private Battery getBatteryZero() {
		Battery battery = new Battery();
		battery.setMaximumChargingRate(0);
		battery.setMaximumDischargingRate(0);
		battery.setMaximumStoredEnergy(0); //It takes multiple steps to charge the battery
		return battery;
	}

	private Battery getBattery(int maxChargeRateInkW, int capacity) {
		Battery battery = new Battery();
		battery.setMaximumChargingRate(maxChargeRateInkW * CONVERT_TO_KW);
		battery.setMaximumDischargingRate(20 * CONVERT_TO_KW);
		battery.setMaximumStoredEnergy(20 * CONVERT_TO_KW); //It takes multiple steps to charge the battery
		return battery;
	}

}
