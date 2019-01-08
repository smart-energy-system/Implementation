
package com.github.smartenergysystem.dynamicPricing.xml;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the xml package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: xml
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PublicationMarketDocument }
     * 
     */
    public PublicationMarketDocument createPublicationMarketDocument() {
        return new PublicationMarketDocument();
    }

    /**
     * Create an instance of {@link PublicationMarketDocument.TimeSeries }
     * 
     */
    public PublicationMarketDocument.TimeSeries createPublicationMarketDocumentTimeSeries() {
        return new PublicationMarketDocument.TimeSeries();
    }

    /**
     * Create an instance of {@link PublicationMarketDocument.TimeSeries.Period }
     * 
     */
    public PublicationMarketDocument.TimeSeries.Period createPublicationMarketDocumentTimeSeriesPeriod() {
        return new PublicationMarketDocument.TimeSeries.Period();
    }

    /**
     * Create an instance of {@link PublicationMarketDocument.SenderMarketParticipantMRID }
     * 
     */
    public PublicationMarketDocument.SenderMarketParticipantMRID createPublicationMarketDocumentSenderMarketParticipantMRID() {
        return new PublicationMarketDocument.SenderMarketParticipantMRID();
    }

    /**
     * Create an instance of {@link PublicationMarketDocument.ReceiverMarketParticipantMRID }
     * 
     */
    public PublicationMarketDocument.ReceiverMarketParticipantMRID createPublicationMarketDocumentReceiverMarketParticipantMRID() {
        return new PublicationMarketDocument.ReceiverMarketParticipantMRID();
    }

    /**
     * Create an instance of {@link PublicationMarketDocument.PeriodTimeInterval }
     * 
     */
    public PublicationMarketDocument.PeriodTimeInterval createPublicationMarketDocumentPeriodTimeInterval() {
        return new PublicationMarketDocument.PeriodTimeInterval();
    }

    /**
     * Create an instance of {@link PublicationMarketDocument.TimeSeries.InDomainMRID }
     * 
     */
    public PublicationMarketDocument.TimeSeries.InDomainMRID createPublicationMarketDocumentTimeSeriesInDomainMRID() {
        return new PublicationMarketDocument.TimeSeries.InDomainMRID();
    }

    /**
     * Create an instance of {@link PublicationMarketDocument.TimeSeries.OutDomainMRID }
     * 
     */
    public PublicationMarketDocument.TimeSeries.OutDomainMRID createPublicationMarketDocumentTimeSeriesOutDomainMRID() {
        return new PublicationMarketDocument.TimeSeries.OutDomainMRID();
    }

    /**
     * Create an instance of {@link PublicationMarketDocument.TimeSeries.Period.TimeInterval }
     * 
     */
    public PublicationMarketDocument.TimeSeries.Period.TimeInterval createPublicationMarketDocumentTimeSeriesPeriodTimeInterval() {
        return new PublicationMarketDocument.TimeSeries.Period.TimeInterval();
    }

    /**
     * Create an instance of {@link PublicationMarketDocument.TimeSeries.Period.Point }
     * 
     */
    public PublicationMarketDocument.TimeSeries.Period.Point createPublicationMarketDocumentTimeSeriesPeriodPoint() {
        return new PublicationMarketDocument.TimeSeries.Period.Point();
    }

}
