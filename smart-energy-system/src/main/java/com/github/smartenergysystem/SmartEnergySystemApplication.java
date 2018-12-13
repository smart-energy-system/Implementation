package com.github.smartenergysystem;

import com.github.smartenergysystem.simulation.SmartGridSolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartEnergySystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartEnergySystemApplication.class, args);
		//SmartGridSolver.solve();
		/*
		Model m = new Model("my first problem");

		IntVar[] buy = { m.intVar("buy", 0, 100000), m.intVar("buy", 0, 100000), m.intVar("buy", 0, 100000) };
		IntVar[] solar = { m.intVar("solar", 30), m.intVar("solar", 90), m.intVar("solar", 30) };
		IntVar[] wind = { m.intVar("wind", 70), m.intVar("wind", 20), m.intVar("wind", 30) };
		IntVar[] sell = { m.intVar("sell", 0, 100000), m.intVar("sell", 0, 100000), m.intVar("sell", 0, 100000) };
		IntVar[] batteryCharge = { m.intVar("batteryCharge", 0), m.intVar("batteryCharge", 0, 100000),
				m.intVar("batteryCharge", 0, 100000) };
		IntVar[] batteryChargeRate = { m.intVar("batteryChargeRate", 0, 100000),
				m.intVar("batteryChargeRate", 0, 100000), m.intVar("batteryChargeRate", 0, 100000) };
		IntVar[] batteryDischargeRate = { m.intVar("batteryDischargeRate", 0, 100000),
				m.intVar("batteryDischargeRate", 0, 100000), m.intVar("batteryDischargeRate", 0, 100000) };
		IntVar[] home = { m.intVar("home", 30), m.intVar("home", 30), m.intVar("home", 30) };
		IntVar[] commercial = { m.intVar("commercial", 30), m.intVar("commercial", 30), m.intVar("commercial", 90) };
		IntVar[] left1 = { m.intVar("left1", 0, 100000), m.intVar("left1", 0, 100000), m.intVar("left1", 0, 100000) };
		IntVar[] right1 = { m.intVar("right1", 0, 100000), m.intVar("right1", 0, 100000),
				m.intVar("right1", 0, 100000) };
		IntVar[] left2 = { m.intVar("left2", 0, 100000), m.intVar("left2", 0, 100000), m.intVar("left2", 0, 100000) };
		IntVar[] right2 = { m.intVar("right2", 0, 100000), m.intVar("right2", 0, 100000),
				m.intVar("right2", 0, 100000) };
		IntVar[] left3 = { m.intVar("left3", 0, 100000), m.intVar("left3", 0, 100000), m.intVar("left3", 0, 100000) };
		IntVar[] right3 = { m.intVar("right3", 0, 100000), m.intVar("right3", 0, 100000),
				m.intVar("right3", 0, 100000) };
		IntVar[] left = { m.intVar("left", 0, 100000), m.intVar("left", 0, 100000), m.intVar("left", 0, 100000) };
		IntVar[] right = { m.intVar("right", 0, 100000), m.intVar("right", 0, 100000), m.intVar("right", 0, 100000) };
		IntVar[] realBatteryChargeRate = { m.intVar("realChargeRate", 0, 100000), m.intVar("realChargeRate", 0, 100000), m.intVar("realChargeRate", 0, 100000) };
		//InitBatteryCharge
		IntVar initBatteryCharge = m.intVar("initBatteryCharge", 0);
		IntVar[] batteryEfficiencyRate = { m.intVar("bEfficiencyRate", 0, 100000),m.intVar("bEfficiencyRate", 0, 100000),m.intVar("bEfficiencyRate", 0, 100000)};
		IntVar batteryEfficiencyCounter = m.intVar("bEfficiencyCounter", 90);
		IntVar batteryEfficiencyDivisor= m.intVar("bEfficiencyDivisor", 100);
		// Prices
		IntVar[] price = { m.intVar("price", 1), m.intVar("price", 1), m.intVar("price", 1) };
		IntVar[] cost = { m.intVar("cost", 1), m.intVar("cost", 1), m.intVar("cost", 1) };
		IntVar[] balance = { m.intVar("balance", -100000, 100000), m.intVar("balance", -100000, 100000),
				m.intVar("balance", -100000, 100000) };
		IntVar[] batteryBalance = { m.intVar("batteryBalance", -100000, 100000),
				m.intVar("batteryBalance", -100000, 100000), m.intVar("batteryBalance", -100000, 100000) };
		// Profit and loss
		IntVar[] profit = { m.intVar("profit", 0, 100000), m.intVar("profit", 0, 100000),
				m.intVar("profit", 0, 100000) };
		IntVar[] loss = { m.intVar("loss", 0, 100000), m.intVar("loss", 0, 100000), m.intVar("loss", 0, 100000) };

		//Demand shifting
		IntVar[] demandDecrease = { m.intVar("demandDecrease", 0 ,100000),m.intVar("demandDecrease", 0 ,100000),m.intVar("demandDecrease", 0 ,100000)};
		IntVar[] demandIncrease = { m.intVar("demandIncrease", 0 ,100000),m.intVar("demandIncrease", 0 ,100000),m.intVar("demandIncrease", 0 ,100000)};
		
		// sum result
		IntVar sum = m.intVar("sum", -100000, 100000);
		IntVar demandIncreaseSum = m.intVar("demandIncreaseSum", 0, 100000);
		IntVar demandDecreaseSum = m.intVar("demandDecreaseSum", 0, 100000);
		IntVar[] shiftDecreaseBool = { m.intVar("shiftDecreaseBool", 0, 1),m.intVar("shiftDecreaseBool", 0, 1),m.intVar("shiftDecreaseBool", 0, 1)};
		IntVar[] shiftIncreaseBool = { m.intVar("shiftIncreaseBool", 0, 1),m.intVar("shiftIncreaseBool", 0, 1),m.intVar("shiftIncreaseBool", 0, 1)};
		IntVar[] demandShiftRate = { m.intVar("demandIncrease", 0 ,100000),m.intVar("demandIncrease", 0 ,100000),m.intVar("demandIncrease", 0 ,100000)};
		IntVar demandShiftCounter = m.intVar("sum", 0, 100000);
		IntVar demandShiftDivisor = m.intVar("sum", 0, 100000);
		
		
		for (int i = 0; i < 3; i++) {
			m.arithm(left1[i], "=", buy[i], "+", solar[i]).post();
			m.arithm(right1[i], "=", sell[i], "+", home[i]).post();
			m.arithm(left2[i], "=", batteryDischargeRate[i], "+", wind[i]).post();
			m.arithm(right2[i], "=", batteryChargeRate[i], "+", commercial[i]).post();
			m.arithm(left3[i], "=", left2[i], "+", demandDecrease[i]).post();
			m.arithm(right3[i], "=", right2[i], "+", demandIncrease[i]).post();
			m.arithm(left[i], "=", left1[i], "+", left3[i]).post();
			m.arithm(right[i], "=", right1[i], "+", right3[i]).post();
			m.arithm(left[i], "=", right[i]).post();
			// profit and loss
			m.arithm(profit[i], "=", sell[i], "*", price[i]).post();
			m.arithm(loss[i], "=", buy[i], "*", cost[i]).post();
			// // balance
			m.arithm(balance[i], "=", profit[i], "-", loss[i]).post();
			
			//Im- and Export regulation
			m.ifThen(m.arithm(batteryChargeRate[i],  ">", 0), m.arithm(batteryDischargeRate[i], "=", 0));
			m.ifThen(m.arithm(batteryDischargeRate[i], ">", 0),m.arithm(batteryChargeRate[i], "=", 0));
			m.ifThen(m.arithm(buy[i],  ">", 0), m.arithm(sell[i], "=", 0));
			m.ifThen(m.arithm(sell[i], ">", 0),m.arithm(buy[i], "=", 0));
			
			//BatteryCharging Inefficiency constraints
			m.arithm(batteryEfficiencyRate[i], "=", batteryChargeRate[i] ,"*", batteryEfficiencyCounter).post();
			m.arithm(realBatteryChargeRate[i], "=", batteryEfficiencyRate[i], "/", batteryEfficiencyDivisor ).post();
			m.arithm(batteryBalance[i], "=", realBatteryChargeRate[i], "-", batteryDischargeRate[i]).post();
			
			//Increase decrease shift regulation
			m.ifThen(m.arithm(demandIncrease[i],  ">", 0), m.arithm(demandDecrease[i], "=", 0));
			m.ifThen(m.arithm(demandDecrease[i], ">", 0),m.arithm(demandIncrease[i], "=", 0));
			m.ifThen(m.arithm(shiftIncreaseBool[i],  ">", 0), m.arithm(shiftDecreaseBool[i], "=", 1));
			m.ifThen(m.arithm(shiftDecreaseBool[i], ">", 0),m.arithm(shiftIncreaseBool[i], "=", 1));
			m.arithm(demandDecrease[i], "<=", home[i]).post();
			
			//Demand Shift Rate
			m.arithm(demandDecrease[i], "<", demandShiftRate[i], "/", demandShiftDivisor).post();
			m.arithm(demandShiftRate[i], "=", home[i] , "*", demandShiftCounter).post();
		}

		for (int i = 1; i < 3; i++) {
			m.arithm(batteryCharge[i], "=", batteryCharge[i - 1], "+", batteryBalance[i]).post();
		}
		
		m.arithm(batteryCharge[0], "=", initBatteryCharge , "+", batteryBalance[0]).post();

		m.sum(demandIncrease, "=" ,demandIncreaseSum).post();
		m.sum(demandDecrease, "=" ,demandDecreaseSum).post();;
		m.arithm(demandDecreaseSum, "=", demandIncreaseSum).post();

		m.sum(shiftIncreaseBool, "<=", 1).post();;
		m.sum(shiftDecreaseBool, "<=", 1).post();;
		
		m.sum(balance, "=", sum).post();
		m.setObjective(Model.MAXIMIZE, sum);

		Solver solver = m.getSolver();
		
		long millisStart = System.currentTimeMillis();
		Solution solution = new Solution(m);
		while (solver.solve()) {

			solution.record();

			System.out.println(sum);

			for (int i = 0; i < 3; i++) {
				System.out.println("Timestep: " + i);
				System.out.print(home[i]);
				System.out.print(", ");
				System.out.print(commercial[i]);
				System.out.print(", ");
				System.out.print(demandIncrease[i]);
				System.out.print(", ");
				System.out.print(demandDecrease[i]);
				System.out.print(", ");
				System.out.print(solar[i]);
				System.out.print(", ");
				System.out.print(wind[i]);
				System.out.print(", ");
				System.out.print(buy[i]);
				System.out.print(", ");
				System.out.print(sell[i]);
				System.out.print(", ");
				System.out.print(profit[i]);
				System.out.print(", ");
				System.out.print(loss[i]);
				System.out.print(", ");
				System.out.print(batteryCharge[i]);
				System.out.print(", ");
				System.out.print(batteryChargeRate[i]);
				System.out.print(", ");
				System.out.print(batteryDischargeRate[i]);
				System.out.print(", ");
				System.out.print(batteryBalance[i]);
				System.out.print(", ");
				System.out.println(balance[i]);

			}
			System.out.println("-----------------------------------");
		}
		System.out.println("FINISH! FINNISH SAUNA!");
		System.out.println("Time: " + (System.currentTimeMillis() - millisStart) / 1000);
		*/
	}
}
