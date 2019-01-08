package com.github.smartenergysystem;

import com.github.smartenergysystem.dynamicPricing.PriceCollector;
import com.github.smartenergysystem.simulation.WindTurbine;
import org.junit.Test;

import java.net.URISyntaxException;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class TestPriceCollector {

    @Test
    public void testCollector() throws Exception {
        PriceCollector priceCollector = new PriceCollector("");
        priceCollector.collectPrices(PriceCollector.DocumentTypes.DAY_AHEAD_PRICES,"10Y1001A1001A82H",new Date());
    }
}
