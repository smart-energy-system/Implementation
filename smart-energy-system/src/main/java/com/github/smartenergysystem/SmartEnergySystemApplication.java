package com.github.smartenergysystem;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartEnergySystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartEnergySystemApplication.class, args);
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
		IntVar[] left = { m.intVar("left", 0, 100000), m.intVar("left", 0, 100000), m.intVar("left", 0, 100000) };
		IntVar[] right = { m.intVar("right", 0, 100000), m.intVar("right", 0, 100000), m.intVar("right", 0, 100000) };
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

		// sum result
		IntVar sum = m.intVar("sum", 0, 100000);

		for (int i = 0; i < 3; i++) {
			m.arithm(left1[i], "=", buy[i], "+", solar[i]).post();
			m.arithm(right1[i], "=", sell[i], "+", home[i]).post();
			m.arithm(left2[i], "=", batteryDischargeRate[i], "+", wind[i]).post();
			m.arithm(right2[i], "=", batteryChargeRate[i], "+", commercial[i]).post();
			m.arithm(left[i], "=", left1[i], "+", left2[i]).post();
			m.arithm(right[i], "=", right1[i], "+", right2[i]).post();
			m.arithm(left[i], "=", right[i]).post();
			// profit and loss

			m.arithm(profit[i], "=", sell[i], "*", price[i]).post();
			m.arithm(loss[i], "=", buy[i], "*", cost[i]).post();
			// // balance
			m.arithm(balance[i], "=", profit[i], "-", loss[i]).post();

			m.or(m.and(m.or(m.arithm(batteryChargeRate[i], ">", 0), m.arithm(batteryDischargeRate[i], ">", 0)),
					m.not(m.and(m.arithm(batteryChargeRate[i], ">", 0), m.arithm(batteryDischargeRate[i], ">", 0)))),
					m.and(m.arithm(batteryChargeRate[i], "=", 0), m.arithm(batteryChargeRate[i], "=", 0))).post();
		}

		for (int i = 1; i < 3; i++) {
			m.arithm(batteryCharge[i], "=", batteryCharge[i - 1], "+", batteryBalance[i]).post();
			m.arithm(batteryBalance[i], "=", batteryChargeRate[i], "-", batteryDischargeRate[i]).post();

		}

		m.sum(balance, "=", sum).post();
		m.setObjective(Model.MAXIMIZE, sum);

		Solver solver = m.getSolver();

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
	}
}
