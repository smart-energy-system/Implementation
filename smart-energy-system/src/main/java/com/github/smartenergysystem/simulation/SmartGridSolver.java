package com.github.smartenergysystem.simulation;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;

import java.util.Arrays;

public class SmartGridSolver {


    int UB;
    int LB;
    public SmartGridSolver(int maxBound){
        UB = maxBound;
        LB = -maxBound;

    }
    private static final int CONVERT_TO_KW = 1000;

    public SmartGridSolverSolution solve(int[] supplerSummedForEachHour,
                                                int[] consumer1, int demandFlexibilityAsPartsOfHundred1,
                                                int[] consumer2, int demandFlexibilityAsPartsOfHundred2,
                                                int[] exportPricePerUnit, int[] importCostPerUnit, Battery batterie,
                                                int efficiencyChargingAsPartsOfHundred) {

        //Inputs
        //int[] supplerSummedForEachHour = new int[]{50, 50, 50,50};
        //int[] consumer1 = new int[]{10, 10, 10, 10};
        //int[] consumer2 = new int[]{20, 20, 20, 20};
        //int[] exportPricePerUnit = new int[]{5,5,5,5};
        //int[] importCostPerUnit = new int[]{4, 4, 4, 4};
/*            int[] supplerSummedForEachHour = new int[]{50, 50, 50,50};
            int[] consumer1 = new int[]{10, 10, 10, 10};
            int[] consumer2 = new int[]{20, 20, 20, 20};
            int[] exportPricePerUnit = new int[]{5,5,5,5};
            int[] importCostPerUnit = new int[]{4, 4, 4, 4};*/

/*            int[] supplerSummedForEachHour = new int[]{50, 50, 50,50};
            int[] consumer1 = new int[]{10, 10, 10, 10};
            int[] consumer2 = new int[]{20, 20, 20, 20};
            int[] exportPricePerUnit = new int[]{5,5,5,5};
            int[] importCostPerUnit = new int[]{5, 5, 5, 5};*/


        //Er sollte so viel wie möglich weg schiften
            /*int[] supplerSummedForEachHour = new int[]{50, 50, 50,50};
            int[] consumer1 = new int[]{10, 10, 10, 10};
            int[] consumer2 = new int[]{20, 20, 20, 20};
            int[] exportPricePerUnit = new int[]{10,5,5,5};
            int[] importCostPerUnit = new int[]{5, 5, 5, 5};*/

            /*int[] supplerSummedForEachHour = new int[]{50, 50, 50,50};
            int[] consumer1 = new int[]{10, 10, 10, 10};
            int[] consumer2 = new int[]{20, 20, 20, 20};
            int[] exportPricePerUnit = new int[]{20,5,5,5};
            int[] importCostPerUnit = new int[]{1, 5, 5, 5};*/

            /*int[] supplerSummedForEachHour = new int[]{50, 29, 50,50};
            int[] consumer1 = new int[]{10, 10, 10, 10};
            int[] consumer2 = new int[]{20, 20, 20, 20};
            int[] exportPricePerUnit = new int[]{5,5,5,5};
            int[] importCostPerUnit = new int[]{5, 5, 5, 5};+/

            /*int[] supplerSummedForEachHour = new int[]{50, 50, 50,50};
            int[] consumer1 = new int[]{10, 10, 10, 10};
            int[] consumer2 = new int[]{20, 20, 20, 20};
            int[] exportPricePerUnit = new int[]{20,5,5,5};
            int[] importCostPerUnit = new int[]{1, 5, 5, 5};*/

            /*int[] supplerSummedForEachHour = new int[]{50, 50, 50,0};
            int[] consumer1 = new int[]{10, 10, 10, 10};
            int[] consumer2 = new int[]{20, 20, 20, 20};
            int[] exportPricePerUnit = new int[]{20,5,5,5};
            int[] importCostPerUnit = new int[]{1, 5, 5, 100};*/

            /*int[] supplerSummedForEachHour = new int[]{50, 50, 50,0};
            int[] consumer1 = new int[]{10, 10, 10, 10};
            int[] consumer2 = new int[]{10, 10, 10, 10};
            int[] exportPricePerUnit = new int[]{5,5,5,5};
            int[] importCostPerUnit = new int[]{80, 80, 80, 80};*/


        int profitMultiplicator = Math.max(Arrays.stream(exportPricePerUnit).max().getAsInt(), Arrays.stream(importCostPerUnit).max().getAsInt());

        int inputSize = supplerSummedForEachHour.length;

        int maximumStoredEnergy = (int) batterie.getMaximumStoredEnergy() / CONVERT_TO_KW; //20;
        int maximumChargingRate = (int) batterie.getMaximumChargingRate() / CONVERT_TO_KW;// 5;
        int maximumDischargingRate = (int) batterie.getMaximumDischargingRate() / CONVERT_TO_KW;
        //int efficiencyFractionsOfHundred = batterie.get;

        Model smartGridModel = new Model("smartGridModel");
        IntVar[] supplerSummdOfEachHourConstants = new IntVar[inputSize];
        IntVar[] consum1OfEachHourConstants = new IntVar[inputSize];
        IntVar[] consum2OfEachHourConstants = new IntVar[inputSize];
        IntVar[] Pg = new IntVar[inputSize];
        IntVar[] Pb = new IntVar[inputSize];
        IntVar[] Pdb = new IntVar[inputSize];
        IntVar[] exportProfitPerHour = new IntVar[inputSize];
        IntVar[] importCostPerHour = new IntVar[inputSize];
        IntVar[] PgAbsolutValues = new IntVar[inputSize];
        IntVar[] PposShift1 = new IntVar[inputSize];
        IntVar[] PnegShift1 = new IntVar[inputSize];

        IntVar[] PposShift2 = new IntVar[inputSize];
        IntVar[] PnegShift2 = new IntVar[inputSize];

        Constraint[] isEnergyImportConstraints = new Constraint[inputSize];

        //One battery for now
        IntVar[] batteryFillLevelPerHour = new IntVar[inputSize];
        IntVar[] chargeRatePerHour = new IntVar[inputSize];
        IntVar[] dischargeRatePerHour = new IntVar[inputSize];
        IntVar[] realchargeRatePerHour = new IntVar[inputSize];
        IntVar zero = smartGridModel.intVar("Zero", 0);

        //Shift only once
        IntVar[] isShiftingPos1 = new IntVar[inputSize];
        IntVar[] isShifitingNeg1 = new IntVar[inputSize];
        IntVar[] isShiftingPos2 = new IntVar[inputSize];
        IntVar[] isShifitingNeg2 = new IntVar[inputSize];

        //init Batteries
        for (int index = 0; index < supplerSummedForEachHour.length; index++) {
            chargeRatePerHour[index] = smartGridModel.intVar("chargeRatePerHour_" + index, 0, maximumChargingRate);
            realchargeRatePerHour[index] = smartGridModel.intVar("realchargeRatePerHour_" + index, 0, maximumChargingRate);
            dischargeRatePerHour[index] = smartGridModel.intVar("dischargeRatePerHour_" + index, 0, maximumDischargingRate);
            batteryFillLevelPerHour[index] = smartGridModel.intVar("batteryFillLevelPerHour_" + index, 0, maximumStoredEnergy);
            batteryFillLevelPerHour[index].sub(dischargeRatePerHour[index]).ge(0).post();
        }
        batteryFillLevelPerHour[0].eq(0).post();
        for (int index = 0; index < supplerSummedForEachHour.length - 1; index++) {
            System.out.println(index);
            realchargeRatePerHour[index].eq(chargeRatePerHour[index].mul(efficiencyChargingAsPartsOfHundred).div(100)).post();
            Constraint dischargeBattery = smartGridModel.arithm(batteryFillLevelPerHour[index + 1], "=", batteryFillLevelPerHour[index], "-", dischargeRatePerHour[index]);
            Constraint chargeBattery = smartGridModel.arithm(batteryFillLevelPerHour[index + 1], "=", batteryFillLevelPerHour[index], "+", realchargeRatePerHour[index]);

            Constraint noDischarge = smartGridModel.arithm(dischargeRatePerHour[index], "=", 0);
            Constraint noCharge = smartGridModel.arithm(chargeRatePerHour[index], "=", 0);
            smartGridModel.ifThenElse(smartGridModel.arithm(chargeRatePerHour[index], "!=", 0), chargeBattery, dischargeBattery);
            smartGridModel.ifThenElse(smartGridModel.arithm(chargeRatePerHour[index], "!=", 0), noDischarge, noCharge);
        }

        for (int index = 0; index < supplerSummedForEachHour.length; index++) {

            Pb[index] = smartGridModel.intVar("Pb_" + index, LB, UB);
            Pdb[index] = smartGridModel.intVar("Pb_" + index, LB, UB);

            Pb[index].eq(dischargeRatePerHour[index]).post();
            Pdb[index].eq(chargeRatePerHour[index]).post();

            //Init arrays
            supplerSummdOfEachHourConstants[index] = smartGridModel.intVar("Ps_" + index, supplerSummedForEachHour[index]);
            consum1OfEachHourConstants[index] = smartGridModel.intVar("Pd_1_" + index, consumer1[index]);
            consum2OfEachHourConstants[index] = smartGridModel.intVar("Pd_2_" + index, consumer2[index]);
            Pg[index] = smartGridModel.intVar("Pg_" + index, LB, UB * 10);
            exportProfitPerHour[index] = smartGridModel.intVar("exportProfitPerHour_" + index, 0, UB * profitMultiplicator * inputSize);
            importCostPerHour[index] = smartGridModel.intVar("importCostPerHour_" + index, 0, UB * profitMultiplicator * inputSize);

            PposShift1[index] = smartGridModel.intVar("PposShift_1_" + index, new int[]{0, (int) ((consumer1[index] * demandFlexibilityAsPartsOfHundred1) / 100)});
            PnegShift1[index] = smartGridModel.intVar("PnegShift_1_" + index, new int[]{0, (int) ((consumer1[index] * demandFlexibilityAsPartsOfHundred1) / 100)});

            Constraint PposShift1Zero1 = smartGridModel.arithm(PposShift1[index], "=", 0);
            Constraint PnegShift1Zero1 = smartGridModel.arithm(PnegShift1[index], "=", 0);
            Constraint isPosShifitingConstraint1 = smartGridModel.arithm(PposShift1[index], "!=", 0);
            Constraint isNegShifitingConstraint1 = smartGridModel.arithm(PnegShift1[index], "!=", 0);

            smartGridModel.ifThenElse(isPosShifitingConstraint1, PnegShift1Zero1, PposShift1Zero1);

            isShiftingPos1[index] = smartGridModel.intVar("isShiftingPos1_" + index, 0, 1);
            isShifitingNeg1[index] = smartGridModel.intVar("isShifitingNeg1_" + index, 0, 1);

            smartGridModel.ifThenElse(isPosShifitingConstraint1, smartGridModel.arithm(isShiftingPos1[index], "=", 1), smartGridModel.arithm(isShiftingPos1[index], "=", 0));
            smartGridModel.ifThenElse(isNegShifitingConstraint1, smartGridModel.arithm(isShifitingNeg1[index], "=", 1), smartGridModel.arithm(isShifitingNeg1[index], "=", 0));
            //zweites shifting

            PposShift2[index] = smartGridModel.intVar("PposShift_2_" + index, new int[]{0, (int) ((consumer2[index] * demandFlexibilityAsPartsOfHundred2) / 100)}); //Shift only in Blocks
            PnegShift2[index] = smartGridModel.intVar("PnegShift_2_" + index, new int[]{0, (int) ((consumer2[index] * demandFlexibilityAsPartsOfHundred2) / 100)});

            Constraint PposShift1Zero2 = smartGridModel.arithm(PposShift2[index], "=", 0);
            Constraint PnegShift1Zero2 = smartGridModel.arithm(PnegShift2[index], "=", 0);
            Constraint isPosShifitingConstraint2 = smartGridModel.arithm(PposShift2[index], "!=", 0);
            Constraint isNegShifitingConstraint2 = smartGridModel.arithm(PnegShift2[index], "!=", 0);
            smartGridModel.ifThenElse(isPosShifitingConstraint2, PnegShift1Zero2, PposShift1Zero2);

            isShiftingPos2[index] = smartGridModel.intVar("isShiftingPos2_" + index, 0, 1);
            isShifitingNeg2[index] = smartGridModel.intVar("isShifitingNeg2_" + index, 0, 1);

            smartGridModel.ifThenElse(isPosShifitingConstraint2, smartGridModel.arithm(isShiftingPos2[index], "=", 1), smartGridModel.arithm(isShiftingPos2[index], "=", 0));
            smartGridModel.ifThenElse(isNegShifitingConstraint2, smartGridModel.arithm(isShifitingNeg2[index], "=", 1), smartGridModel.arithm(isShifitingNeg2[index], "=", 0));


            //Balance equation
            supplerSummdOfEachHourConstants[index].add(Pg[index]).add(Pb[index]).eq(
                    consum1OfEachHourConstants[index].add(PposShift1[index]).sub(PnegShift1[index]).add(
                            consum2OfEachHourConstants[index].add(PposShift2[index]).sub(PnegShift2[index])
                    ).add(Pdb[index])).post();

            PgAbsolutValues[index] = smartGridModel.intVar("PgAbsolutValues_" + index, LB, UB);
            PgAbsolutValues[index].eq(Pg[index].abs()).post();

            //Profit Constraints
            //Pg If Pg positiv than export, If Pg negativ than Import
            Constraint isEnergyImport = smartGridModel.arithm(Pg[index], ">=", 0);
            isEnergyImportConstraints[index] = isEnergyImport;
            Constraint energyImportCostConstaint = smartGridModel.times(PgAbsolutValues[index], importCostPerUnit[index], importCostPerHour[index]);
            Constraint energyExportProfitConstaint = smartGridModel.times(PgAbsolutValues[index], exportPricePerUnit[index], exportProfitPerHour[index]);
            Constraint exportProfitPerHourIsZero = smartGridModel.arithm(exportProfitPerHour[index], "=", 0);
            Constraint importCostPerHourIsZero = smartGridModel.arithm(importCostPerHour[index], "=", 0);

            smartGridModel.ifThenElse(isEnergyImport, energyImportCostConstaint, energyExportProfitConstaint);
            //ifVar => thenCstr | Watch out 0 => 1 == 1
            smartGridModel.ifThenElse(isEnergyImport, exportProfitPerHourIsZero, importCostPerHourIsZero);

        }

        // Nur einmal shiften
        smartGridModel.sum(isShiftingPos1, "<=", 1).post();
        smartGridModel.sum(isShifitingNeg1, "<=", 1).post();
        smartGridModel.sum(isShiftingPos2, "<=", 1).post();
        smartGridModel.sum(isShifitingNeg2, "<=", 1).post();

        //Sum shifitng
        IntVar sumPposShift1 = smartGridModel.intVar("sumPposShift1", 0, UB * inputSize);
        IntVar sumPnegShift1 = smartGridModel.intVar("sumPnegShift1", 0, UB * inputSize);
        smartGridModel.sum(PposShift1, "=", sumPposShift1).post();
        smartGridModel.sum(PnegShift1, "=", sumPnegShift1).post();
        sumPposShift1.eq(sumPnegShift1).post();

        IntVar sumPposShift2 = smartGridModel.intVar("sumPposShift2", 0, UB * inputSize);
        IntVar sumPnegShift2 = smartGridModel.intVar("sumPnegShift2", 0, UB * inputSize);
        smartGridModel.sum(PposShift2, "=", sumPposShift2).post();
        smartGridModel.sum(PnegShift2, "=", sumPnegShift2).post();
        sumPposShift2.eq(sumPnegShift2).post();


        int summedSize = UB * profitMultiplicator * inputSize * 10;
        IntVar hourlySummedProfit = smartGridModel.intVar("hourlySummedProfit", 0, summedSize);
        IntVar hourlySummedCost = smartGridModel.intVar("hourlySummedCost", 0, summedSize);

        smartGridModel.sum(exportProfitPerHour, "=", hourlySummedProfit).post();
        smartGridModel.sum(importCostPerHour, "=", hourlySummedCost).post();
        IntVar totalProfit = smartGridModel.intVar("totalProfit", -summedSize, summedSize);
        totalProfit.eq(hourlySummedProfit.sub(hourlySummedCost)).post();
        smartGridModel.setObjective(Model.MAXIMIZE, totalProfit);
        org.chocosolver.solver.Solver solver = smartGridModel.getSolver();
        SmartGridSolverSolution smartGridSolverSolution = null;
        while (solver.solve()) {
            smartGridSolverSolution = new SmartGridSolverSolution();

            System.out.println("==============================================================================");
            System.out.println("Step\t\t\tPs\t\t\tPd\t\t\tPg\t\t\tPposShift\t\t PnegShift\t\t\texportProfit\t\t\texportProfitPerUnit\t\timportCost\timportCostPerUnit\tbFill\tdiscarge\tcharge");
            for (int index = 0; index < supplerSummedForEachHour.length; index++) {
                System.out.print(index + "\t\t");
                System.out.printf("%1$12s", supplerSummdOfEachHourConstants[index].getValue() + "\t");
                System.out.printf("%1$12s", consum1OfEachHourConstants[index].getValue() + "," + consum2OfEachHourConstants[index].getValue() + "\t");
                System.out.printf("%1$12s", Pg[index].getValue() + "\t");
                System.out.printf("%1$12s", PposShift1[index].getValue() + "," + PposShift2[index].getValue() + "\t\t\t");
                System.out.printf("%1$12s", PnegShift1[index].getValue() + "," + PnegShift2[index].getValue() + "\t\t\t");
                System.out.printf("%1$12s", exportProfitPerHour[index].getValue() + "\t\t\t\t");
                System.out.printf("%1$12s", exportPricePerUnit[index] + "\t\t\t\t");
                System.out.printf("%1$12s", importCostPerHour[index].getValue() + "\t");
                System.out.printf("%1$16s", importCostPerUnit[index] + "\t");
                System.out.printf("%1$12s", batteryFillLevelPerHour[index].getValue() + "\t");
                System.out.printf("%1$12s", dischargeRatePerHour[index].getValue() + "\t");
                System.out.printf("%1$12s", chargeRatePerHour[index].getValue() + "\t");
                System.out.print("\n");
                int[] PposShift = new int[2];
                PposShift[0] = PposShift1[index].getValue();
                PposShift[1] = PposShift2[index].getValue();
                int[] PnegShift = new int[2];
                PnegShift[0] = PnegShift1[index].getValue();
                PnegShift[1] = PnegShift2[index].getValue();
                SmartGridSolverSolutionStep smartGridSolverSolutionStep = new SmartGridSolverSolutionStep(Pg[index].getValue(),
                        PposShift, PnegShift, exportProfitPerHour[index].getValue(), importCostPerHour[index].getValue(),
                        batteryFillLevelPerHour[index].getValue(), dischargeRatePerHour[index].getValue(), chargeRatePerHour[index].getValue());
                smartGridSolverSolution.addSmartGridSolverSolutionStep(smartGridSolverSolutionStep);
            }
            System.out.println("---------------------------------------------");
            System.out.println("hourlySummedProfit:" + hourlySummedProfit);
            System.out.println("hourlySummedCost:" + hourlySummedCost);
            System.out.println("totalProfit:" + totalProfit);
            smartGridSolverSolution.setTotalExportProfit(hourlySummedProfit.getValue());
            smartGridSolverSolution.setTotalImportCost(hourlySummedCost.getValue());
            smartGridSolverSolution.setTotalProfit(totalProfit.getValue());

        }
        System.out.println("Close");
        return smartGridSolverSolution;
    }


}
