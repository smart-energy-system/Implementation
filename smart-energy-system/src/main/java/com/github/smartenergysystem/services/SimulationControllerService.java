package com.github.smartenergysystem.services;

import java.util.*;

import com.github.smartenergysystem.model.EnergyForecastPoint;
import com.github.smartenergysystem.simulation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.smartenergysystem.model.EnergyForecast;

@Service
public class SimulationControllerService implements ISimulationControllerService {

    //private static final int NUMBER_OF_HOUR_TO_CALCULATE = 4;
    private static final int CONSTANT_IMPORT_CONST_PER_UNIT = 35;
    private static final int CONSTANT_EXPORT_PRICE_PER_UNIT = 30;

    Logger logger = LoggerFactory.getLogger(SimulationControllerService.class);


    @Autowired
    OfficeBuildingService officeBuildingService;

    @Autowired
    HomesService homesService;

    @Autowired
    PhotovoltaicPanelsService photovoltaicPanelsService;

    @Autowired
    WindTurbineService windTurbineService;

    @Autowired
    BatteryService batteryService;

    @Autowired
    PriceService priceService;


    @Override
    @Transactional(readOnly = true)
    public synchronized SmartGridSolverSolution solve(int calculationBound, int exportPrice, Date startDate, Date endDate, int batteryFillLevelinWatt,int maxSteps,int timeout) {
        startDate = EntityService.roundDownToNearestHour(startDate);
        endDate = EntityService.roundDownToNearestHour(endDate);
        logger.info("Solving for :"+ startDate + " end:"+ endDate);
        //LinkedList<Integer> summedSuppler = new LinkedList<>();
        System.out.println("Solve");
        int[] summedSupplier = new int[maxSteps];
        LinkedList<EnergyForecast> forecasts = new LinkedList<>();
        Map<Long, PhotovoltaicPanel> photovoltaicPanels = photovoltaicPanelsService.getPhotovoltaicPanels();
        //Calendar calendarSupplier = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        //Date startDate = new Date();
        //calendarSupplier.setTime(startDate);
        //calendarSupplier.add(Calendar.HOUR_OF_DAY,NUMBER_OF_HOUR_TO_CALCULATE);
        //Date endDate = calendarSupplier.getTime();
        for (Map.Entry<Long, PhotovoltaicPanel> entry : photovoltaicPanels.entrySet()) {
            EnergyForecast energyForecast = photovoltaicPanelsService.computeEnergyGenerateForecastPhotovoltaicPanel(entry.getKey(), startDate,endDate);
            forecasts.add(energyForecast);
        }
        Map<Long, WindTurbine> windTurbines = windTurbineService.getWindTurbines();
        for (Map.Entry<Long, WindTurbine> entry : windTurbines.entrySet()) {
            EnergyForecast energyForecast = windTurbineService.computeEnergyGenerateForecastWindTurbine(entry.getKey(), startDate,endDate);
            forecasts.add(energyForecast);
        }

        for (EnergyForecast energyForecast : forecasts) {
            int counter = 0;
            System.out.println(energyForecast.getForecast().size());
            for (EnergyForecastPoint forecastpoint : energyForecast.getForecast()) {
                summedSupplier[counter] = summedSupplier[counter] + (int) forecastpoint.getValue().intValue();
                System.out.println(forecastpoint.getValue());
                if (counter == summedSupplier.length - 1) {
                    break;
                }
                counter++;
            }
        }
        //Convert to kW
        for (int i = 0; i < summedSupplier.length; i++) {
            summedSupplier[i] = summedSupplier[i] / 1000;
        }

        //Consumers are already in kW <-not longer true
        //Get first consumer
        int[] homeConsumersSummed = new int[maxSteps]; //4 hour solutions
        double homeConsmerSmallestDemandFlexibility = Double.MAX_VALUE;
        Calendar calendar = Calendar.getInstance();
        Map<Long,Home> homeBuildings = homesService.getHomeBuildings();
//        for (Map.Entry<Long, Home> homeBuilding : homeBuildings.entrySet()) {
//            for (int i = 0; i < homeConsumersSummed.length; i++) {
//                System.out.println("i:" + i + " Consumer:" + homesService.computeHomeBuildingDemand(homeBuilding.getKey(), calendar.get(Calendar.HOUR_OF_DAY)) + "Hour:" + calendar.get(Calendar.HOUR_OF_DAY));
//                homeConsumersSummed[i] = homeConsumersSummed[i] + (int) (homesService.computeHomeBuildingDemand(homeBuilding.getKey(), calendar.get(Calendar.HOUR_OF_DAY)) / 1000);
//                calendar.add(Calendar.HOUR_OF_DAY, 1);
//                if (homeBuilding.getValue().getDemandFlexibility() < homeConsmerSmallestDemandFlexibility) {
//                    homeConsmerSmallestDemandFlexibility = homeBuilding.getValue().getDemandFlexibility();
//                }
//            }
//        }
        for (Map.Entry<Long, Home> homeBuilding : homeBuildings.entrySet()) {
            EnergyForecast energyForecast = homesService.getDemandForecast(homeBuilding.getValue(),startDate,endDate);
            int counter =0;
            for(EnergyForecastPoint energyForecastPoint : energyForecast.getForecast()){
                homeConsumersSummed[counter] = homeConsumersSummed[counter] +(energyForecastPoint.getValue().intValue() /1000);
                if (counter == homeConsumersSummed.length - 1) {
                    break;
                }
                counter++;
            }
            if (homeBuilding.getValue().getDemandFlexibility() < homeConsmerSmallestDemandFlexibility) {
                    homeConsmerSmallestDemandFlexibility = homeBuilding.getValue().getDemandFlexibility();
            }
        }


        int[] officeBuildingConsumersSummed = new int[maxSteps];
        calendar = Calendar.getInstance();
        double officeBuildingSmallestDemandFlexibility = Double.MAX_VALUE;
        Map<Long,OfficeBuilding> officeBuildings = officeBuildingService.getOfficeBuildings();
/*        for (Map.Entry<Long, OfficeBuilding> officeBuilding : officeBuildings.entrySet()) {
            for (int i = 0; i < officeBuildingConsumersSummed.length; i++) {
                System.out.println("i:" + i + "OfficeConsumer:" + officeBuildingService.computeOfficeBuildingDemand(officeBuilding.getKey(), calendar.get(Calendar.HOUR_OF_DAY)) + "Hour:" + calendar.get(Calendar.HOUR_OF_DAY));
                officeBuildingConsumersSummed[i] = officeBuildingConsumersSummed[i] + (int) (officeBuildingService.computeOfficeBuildingDemand(officeBuilding.getKey(), calendar.get(Calendar.HOUR_OF_DAY)) /1000);
                calendar.add(Calendar.HOUR_OF_DAY, 1);
                if (officeBuilding.getValue().getDemandFlexibility() < officeBuildingSmallestDemandFlexibility) {
                    officeBuildingSmallestDemandFlexibility = officeBuilding.getValue().getDemandFlexibility();
                }
            }
        }*/

        for (Map.Entry<Long, OfficeBuilding> officeBuilding : officeBuildings.entrySet()) {
            EnergyForecast energyForecast = homesService.getDemandForecast(officeBuilding.getValue(),startDate,endDate);
            int counter =0;
            for(EnergyForecastPoint energyForecastPoint : energyForecast.getForecast()){
                officeBuildingConsumersSummed[counter] = officeBuildingConsumersSummed[counter] +(energyForecastPoint.getValue().intValue()/1000);
                if (counter == officeBuildingConsumersSummed.length - 1) {
                    break;
                }
                counter++;
            }
            if (officeBuilding.getValue().getDemandFlexibility() < officeBuildingSmallestDemandFlexibility) {
                homeConsmerSmallestDemandFlexibility = officeBuilding.getValue().getDemandFlexibility();
            }
        }

        logger.info("Summed Supplier for each hour:");
        logger.info(Arrays.toString(summedSupplier));
        logger.info("Summed home consumer for each hour:");
        logger.info(Arrays.toString(homeConsumersSummed));

        logger.info("Summed officeBuildingConsumersSummed for each hour:");
        logger.info(Arrays.toString(officeBuildingConsumersSummed));

        logger.info("OfficeBuildingConsumers flex:"+officeBuildingSmallestDemandFlexibility);
        logger.info("homeConsumers flex:"+homeConsmerSmallestDemandFlexibility);
        int[] importCostPerUnit = priceService.requestPrices(maxSteps);
        int[] exportPricePerUnit = new int[maxSteps];
        for (int i = 0; i < exportPricePerUnit.length; i++) {
            exportPricePerUnit[i] = exportPrice;
        }

//        int[] summedConsumers = new int[NUMBER_OF_HOUR_TO_CALCULATE];
//        for(int i = 0;i<summedConsumers.length;i++){
//            summedConsumers[i] = officeBuildingConsumersSummed[i]+homeConsumersSummed[i];
//
//        }
//        logger.info("All consumers summed for each hour:");
//        logger.info(Arrays.toString(summedConsumers));
        SmartGridSolver solver = new SmartGridSolver(calculationBound,timeout);
        Map<Long,Battery> batteries = batteryService.getBatterys();
        return solver.solve(summedSupplier,
                homeConsumersSummed, (int) (homeConsmerSmallestDemandFlexibility * 100),
                officeBuildingConsumersSummed, (int) (officeBuildingSmallestDemandFlexibility * 100),
                exportPricePerUnit, importCostPerUnit,batteries.get(1L),(int)(batteries.get(1L).getChargingEfficiency()*100),batteryFillLevelinWatt/1000);
    }



}
