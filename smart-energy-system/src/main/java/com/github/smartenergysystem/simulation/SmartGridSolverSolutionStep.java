package com.github.smartenergysystem.simulation;

public class SmartGridSolverSolutionStep {

    private int Pg;
    private int[] PposShift;
    private int[] PnegShift;
    private int exportProfit;
    private int importCost;
    private int batteryFillLevel;
    private int discargeRate;
    private int chargeRate;

    public SmartGridSolverSolutionStep(int pg, int[] pposShift, int[] pnegShift, int exportProfit, int importCost, int batteryFillLevel, int discargeRate, int chargeRate) {
        Pg = pg;
        PposShift = pposShift;
        PnegShift = pnegShift;
        this.exportProfit = exportProfit;
        this.importCost = importCost;
        this.batteryFillLevel = batteryFillLevel;
        this.discargeRate = discargeRate;
        this.chargeRate = chargeRate;
    }



    public int getPg() {
        return Pg;
    }

    public void setPg(int pg) {
        Pg = pg;
    }

    public int[] getPposShift() {
        return PposShift;
    }

    public void setPposShift(int[] pposShift) {
        PposShift = pposShift;
    }

    public int[] getPnegShift() {
        return PnegShift;
    }

    public void setPnegShift(int[] pnegShift) {
        PnegShift = pnegShift;
    }

    public int getExportProfit() {
        return exportProfit;
    }

    public void setExportProfit(int exportProfit) {
        this.exportProfit = exportProfit;
    }

    public int getImportCost() {
        return importCost;
    }

    public void setImportCost(int importCost) {
        this.importCost = importCost;
    }

    public int getBatteryFillLevel() {
        return batteryFillLevel;
    }

    public void setBatteryFillLevel(int batteryFillLevel) {
        this.batteryFillLevel = batteryFillLevel;
    }

    public int getDiscargeRate() {
        return discargeRate;
    }

    public void setDiscargeRate(int discargeRate) {
        this.discargeRate = discargeRate;
    }

    public int getChargeRate() {
        return chargeRate;
    }

    public void setChargeRate(int chargeRate) {
        this.chargeRate = chargeRate;
    }
}
