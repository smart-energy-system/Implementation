package com.github.smartenergysystem.dynamicPricing;

import com.github.smartenergysystem.dynamicPricing.xml.PublicationMarketDocument;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class PriceCollector {


    public enum DocumentTypes {
        DAY_AHEAD_PRICES("A44");

        private final String documentType;

        DocumentTypes(final String documentType) {
            this.documentType = documentType;
        }

        @Override
        public String toString() {
            return documentType;
        }
    }

    String securityToken;


    public PriceCollector(String securityToken) {
        this.securityToken = securityToken;
    }

    public void collectPrices(DocumentTypes documentType,String area, Date day) throws URISyntaxException {
        TimeZone utc = TimeZone.getTimeZone("UTC");
        DateFormat customFormat = new SimpleDateFormat("yyyyMMdd");
        customFormat.setTimeZone(utc);
        String formatedDay = customFormat.format(day);
        String startDate = formatedDay + "0000";
        String endDate = formatedDay + "2300";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://transparency.entsoe.eu/api");
        builder.queryParam("securityToken",securityToken)
                .queryParam("in_Domain",area)
                .queryParam("out_Domain",area)
                .queryParam("documentType", documentType.toString())
/*                .queryParam("periodStart","201512312300")
                .queryParam("periodEnd","201612312300");*/
                .queryParam("periodStart",startDate)
                .queryParam("periodEnd",endDate);
 //       .queryParam("TimeInterval",isoFormattedTimespan);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<PublicationMarketDocument> response
                = restTemplate.getForEntity(builder.toUriString() , PublicationMarketDocument.class);
        PublicationMarketDocument publicationMarketDocument = response.getBody();
        publicationMarketDocument.getTimeSeries().get(0).getPeriod().getPoint().forEach(point -> System.out.println(point.getPosition()+ " : "+ point.getPriceAmount()));
    }

}
