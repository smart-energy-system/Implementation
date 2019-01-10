package com.github.smartenergysystem;

import com.github.smartenergysystem.model.WindTurbineWithIdDTO;
import com.github.smartenergysystem.simulation.ISimulationControllerService;
import com.github.smartenergysystem.simulation.SmartGridSolverSolution;
import com.github.smartenergysystem.simulation.WindTurbine;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@Api(produces ="application/json",tags = { SwaggerConfig.SOLVER_TAG_NAME})
@RequestMapping()
public class SolverController {

    Logger logger = LoggerFactory.getLogger(SupplierWindTurbineSimulationController.class);

    @Autowired
    ISimulationControllerService simulationControllerService;

    @GetMapping("/solver")
    public SmartGridSolverSolution solver(@RequestParam(name = "calculationBound", defaultValue = "100") int calculationBound,
    @RequestParam(name = "exportPrice", defaultValue = "4") int exportPrice,
    @RequestParam(name = "efficiencyChargingAsPartsOfHundred", defaultValue = "80") int efficiencyChargingAsPartsOfHundred) {

       return simulationControllerService.solve(calculationBound,exportPrice,efficiencyChargingAsPartsOfHundred);
    }
}
