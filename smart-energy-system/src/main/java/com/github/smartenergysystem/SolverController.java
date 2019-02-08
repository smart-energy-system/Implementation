package com.github.smartenergysystem;

import com.github.smartenergysystem.services.ISimulationControllerService;
import com.github.smartenergysystem.simulation.SmartGridSolverSolution;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static com.github.smartenergysystem.SupplierWindTurbineSimulationController.DATE_PATTERN;

@RestController
@Api(produces ="application/json",tags = { SwaggerConfig.SOLVER_TAG_NAME})
@RequestMapping()
public class SolverController {

    Logger logger = LoggerFactory.getLogger(SupplierWindTurbineSimulationController.class);

    @Autowired
    ISimulationControllerService simulationControllerService;

    @GetMapping("/solver")
    public SmartGridSolverSolution solver(@RequestParam(name = "calculationBound", defaultValue = "100") int calculationBound,
    @RequestParam(name = "exportPrice", defaultValue = "4") int exportPrice,@ApiParam(name = "startDate", value = "The start date", defaultValue = "2019-01-07T00:00Z")
                                              @RequestParam("startDate") @DateTimeFormat(pattern = DATE_PATTERN) Date startDate,
                                          @ApiParam(name = "endDate", value = "The end date", defaultValue = "2019-01-07T23:00Z")
                                              @RequestParam("endDate") @DateTimeFormat(pattern = DATE_PATTERN) Date endDate,
                                          @RequestParam(name = "batteryFillLevelinWatt", defaultValue = "2000") int batteryFillLevelinWatt,
                                        @RequestParam(name = "maxSteps", defaultValue = "4") int maxSteps,
                                          @RequestParam(name = "timeoutInS", defaultValue = "4") int timeout) {

       return simulationControllerService.solve(calculationBound,exportPrice, startDate,endDate, batteryFillLevelinWatt,maxSteps,timeout);
    }
}
