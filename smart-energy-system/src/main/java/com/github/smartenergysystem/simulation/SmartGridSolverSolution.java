package com.github.smartenergysystem.simulation;

import java.util.LinkedList;

public class SmartGridSolverSolution {

    LinkedList<SmartGridSolverSolutionStep> solutionSteps = new LinkedList<>();
    int totalExportProfit;
    int totalImportCost;
    int totalProfit;

    public SmartGridSolverSolution(int totalExportProfit, int totalImportCost, int totalProfit) {
        this.totalExportProfit = totalExportProfit;
        this.totalImportCost = totalImportCost;
        this.totalProfit = totalProfit;
    }

    public SmartGridSolverSolution() {
    }

    public void addSmartGridSolverSolutionStep(SmartGridSolverSolutionStep smartGridSolverSolutionStep){
        solutionSteps.add(smartGridSolverSolutionStep);
    }

    public LinkedList<SmartGridSolverSolutionStep> getSolutionSteps() {
        return solutionSteps;
    }

    public void setSolutionSteps(LinkedList<SmartGridSolverSolutionStep> solutionSteps) {
        this.solutionSteps = solutionSteps;
    }

    public int getTotalExportProfit() {
        return totalExportProfit;
    }

    public void setTotalExportProfit(int totalExportProfit) {
        this.totalExportProfit = totalExportProfit;
    }

    public int getTotalImportCost() {
        return totalImportCost;
    }

    public void setTotalImportCost(int totalImportCost) {
        this.totalImportCost = totalImportCost;
    }

    public int getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(int totalProfit) {
        this.totalProfit = totalProfit;
    }
}
