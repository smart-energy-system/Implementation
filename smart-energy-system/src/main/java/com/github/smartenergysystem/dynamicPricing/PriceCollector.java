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
        String isoFormattedTimespan = getTimespan(day);
        System.out.println(isoFormattedTimespan);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://iop-transparency.entsoe.eu/api");
        builder.queryParam("securityToken",securityToken)
                .queryParam("in_Domain",area)
                .queryParam("out_Domain",area)
                .queryParam("documentType", documentType.toString())
        .queryParam("TimeInterval",isoFormattedTimespan);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<PublicationMarketDocument> response
                = restTemplate.getForEntity(builder.toUriString() , PublicationMarketDocument.class);
        PublicationMarketDocument publicationMarketDocument = response.getBody();
        System.out.println(response.getBody().getMRID());
        //Do what you want with
    }

    private String getTimespan(Date day) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        Date startDate=calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        Date endDate=calendar.getTime();
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        isoFormat.setTimeZone(tz);
        String startDateISOString = isoFormat.format(startDate);
        String endDateISOString = isoFormat.format(endDate);
        return startDateISOString+ "/" + endDateISOString;
    }
}
