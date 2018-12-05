package com.github.smartenergysystem;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.IntVar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartEnergySystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartEnergySystemApplication.class, args);
		Model m = new Model("my first problem");
		// variables
		IntVar[] batteryProduce = new IntVar[24];
		IntVar[] gridProduce = new IntVar[24];
		IntVar[] batteryDemand = new IntVar[24];
		IntVar[] gridDemand = new IntVar[24];
		// static values
		IntVar[] solar = new IntVar[24];
		IntVar[] wind = new IntVar[24];
		IntVar[] homeDemand = new IntVar[24];
		IntVar[] commercial = new IntVar[24];
		for(int i = 0; i < 24; ++i) {
			// variables
			batteryProduce[i] = m.intVar("batteryProduce" + i, 0, 10000);
			batteryDemand[i] = m.intVar("batteryDemand" + i, 0, 10000);
			gridProduce[i] = m.intVar("gridProduce" + i, 0, 10000);
			gridDemand[i] = m.intVar("gridDemand" + i, 0, 10000);
			// static values
			solar[i] = m.intVar("solar" + i, 34);
			wind[i] = m.intVar("wind" + i, 34);
			homeDemand[i] = m.intVar("homeDemand" + i, 34);
			commercial[i] = m.intVar("commercial" + i, 34);
		}

		homeDemand[10] = m.intVar("homeDemand10", 10);

		for (int i = 0; i<24;i++) {
			// constraints for each time step
			IntVar leftPart1 = m.intVar("leftsidepart1", 0,10000);
			IntVar leftPart2 = m.intVar("leftsidepart2", 0, 10000);
			IntVar leftSide = m.intVar("leftside", 0, 10000);
			m.arithm(leftPart1, "=", solar[i], "+", wind[i]).post();
			m.arithm(leftPart2, "=", batteryProduce[i], "+", gridProduce[i]).post();
			m.arithm(leftSide, "=", leftPart1, "+", leftPart2).post();
			IntVar rightPart1 = m.intVar("rightsidepart1", 0,10000);
			IntVar rightPart2 = m.intVar("rightsidepart2", 0, 10000);
			IntVar rightSide = m.intVar("rightside", 0, 10000);
			m.arithm(rightPart1, "=", homeDemand[i], "+", commercial[i]).post();
			m.arithm(rightPart2, "=", batteryDemand[i], "+", gridDemand[i]).post();
			m.arithm(rightSide, "=", rightPart1, "+", rightPart2).post();
			m.arithm(leftSide, "=", rightSide);
		}


		// IntVar x = m.intVar("X", 0, 5);
		// IntVar y = m.intVar("Y", new int[]{2, 3, 8});

		// m.arithm(x, "+", y, "<", 5).post();
		// m.times(x, y, 4).post();

		m.getSolver().solve();
        for (IntVar i : gridProduce) {
			System.out.println("gridP" + i + ": " + i.getValue());
		}
		for (IntVar i : gridDemand) {
			System.out.println("gridD" + i + ": " + i.getValue());
		}
		for (IntVar i : batteryProduce) {
			System.out.println("batteryP" + i + ": " + i.getValue());
		}
		for (IntVar i : batteryDemand) {
			System.out.println("batteryD " + i + ": " + i.getValue());
		}
		// System.out.println(x);
		// System.out.println(y);
	}
}
