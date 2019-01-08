
package com.github.smartenergysystem.price.collector.priceCollector.xml;

import javax.annotation.Generated;
import javax.xml.bind.annotation.*;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java-Klasse für anonymous complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mRID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="revisionNumber" type="{http://www.w3.org/2001/XMLSchema}byte"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sender_MarketParticipant.mRID">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="codingScheme" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="sender_MarketParticipant.marketRole.type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="receiver_MarketParticipant.mRID">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="codingScheme" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="receiver_MarketParticipant.marketRole.type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="createdDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="period.timeInterval">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="start" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="end" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="TimeSeries" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="mRID" type="{http://www.w3.org/2001/XMLSchema}byte"/>
 *                   &lt;element name="businessType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="in_Domain.mRID">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                           &lt;attribute name="codingScheme" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="out_Domain.mRID">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                           &lt;attribute name="codingScheme" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="currency_Unit.name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="price_Measure_Unit.name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="curveType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="Period">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="timeInterval">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="start" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="end" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="resolution" type="{http://www.w3.org/2001/XMLSchema}duration"/>
 *                             &lt;element name="Point" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="position" type="{http://www.w3.org/2001/XMLSchema}byte"/>
 *                                       &lt;element name="price.amount" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "mrid",
    "revisionNumber",
    "type",
    "senderMarketParticipantMRID",
    "senderMarketParticipantMarketRoleType",
    "receiverMarketParticipantMRID",
    "receiverMarketParticipantMarketRoleType",
    "createdDateTime",
    "periodTimeInterval",
    "timeSeries"
})
@XmlRootElement(name = "Publication_MarketDocument", namespace = "urn:iec62325.351:tc57wg16:451-3:publicationdocument:7:0")
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class PublicationMarketDocument {

    @XmlElement(name = "mRID", namespace = "urn:iec62325.351:tc57wg16:451-3:publicationdocument:7:0", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String mrid;
    @XmlElement(namespace = "urn:iec62325.351:tc57wg16:451-3:publicationdocument:7:0")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected byte revisionNumber;
    @XmlElement(namespace = "urn:iec62325.351:tc57wg16:451-3:publicationdocument:7:0", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String type;
    @XmlElement(name = "sender_MarketParticipant.mRID", namespace = "urn:iec62325.351:tc57wg16:451-3:publicationdocument:7:0", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected PublicationMarketDocument.SenderMarketParticipantMRID senderMarketParticipantMRID;
    @XmlElement(name = "sender_MarketParticipant.marketRole.type", namespace = "urn:iec62325.351:tc57wg16:451-3:publicationdocument:7:0", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String senderMarketParticipantMarketRoleType;
    @XmlElement(name = "receiver_MarketParticipant.mRID", namespace = "urn:iec62325.351:tc57wg16:451-3:publicationdocument:7:0", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected PublicationMarketDocument.ReceiverMarketParticipantMRID receiverMarketParticipantMRID;
    @XmlElement(name = "receiver_MarketParticipant.marketRole.type", namespace = "urn:iec62325.351:tc57wg16:451-3:publicationdocument:7:0", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String receiverMarketParticipantMarketRoleType;
    @XmlElement(namespace = "urn:iec62325.351:tc57wg16:451-3:publicationdocument:7:0", required = true)
    @XmlSchemaType(name = "dateTime")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected XMLGregorianCalendar createdDateTime;
    @XmlElement(name = "period.timeInterval", namespace = "urn:iec62325.351:tc57wg16:451-3:publicationdocument:7:0", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected PublicationMarketDocument.PeriodTimeInterval periodTimeInterval;
    @XmlElement(name = "TimeSeries", namespace = "urn:iec62325.351:tc57wg16:451-3:publicationdocument:7:0")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected List<TimeSeries> timeSeries;

    /**
     * Ruft den Wert der mrid-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getMRID() {
        return mrid;
    }

    /**
     * Legt den Wert der mrid-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setMRID(String value) {
        this.mrid = value;
    }

    /**
     * Ruft den Wert der revisionNumber-Eigenschaft ab.
     *
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public byte getRevisionNumber() {
        return revisionNumber;
    }

    /**
     * Legt den Wert der revisionNumber-Eigenschaft fest.
     *
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setRevisionNumber(byte value) {
        this.revisionNumber = value;
    }

    /**
     * Ruft den Wert der type-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getType() {
        return type;
    }

    /**
     * Legt den Wert der type-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Ruft den Wert der senderMarketParticipantMRID-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link PublicationMarketDocument.SenderMarketParticipantMRID }
     *
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public PublicationMarketDocument.SenderMarketParticipantMRID getSenderMarketParticipantMRID() {
        return senderMarketParticipantMRID;
    }

    /**
     * Legt den Wert der senderMarketParticipantMRID-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link PublicationMarketDocument.SenderMarketParticipantMRID }
     *
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSenderMarketParticipantMRID(PublicationMarketDocument.SenderMarketParticipantMRID value) {
        this.senderMarketParticipantMRID = value;
    }

    /**
     * Ruft den Wert der senderMarketParticipantMarketRoleType-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getSenderMarketParticipantMarketRoleType() {
        return senderMarketParticipantMarketRoleType;
    }

    /**
     * Legt den Wert der senderMarketParticipantMarketRoleType-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSenderMarketParticipantMarketRoleType(String value) {
        this.senderMarketParticipantMarketRoleType = value;
    }

    /**
     * Ruft den Wert der receiverMarketParticipantMRID-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link PublicationMarketDocument.ReceiverMarketParticipantMRID }
     *
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public PublicationMarketDocument.ReceiverMarketParticipantMRID getReceiverMarketParticipantMRID() {
        return receiverMarketParticipantMRID;
    }

    /**
     * Legt den Wert der receiverMarketParticipantMRID-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link PublicationMarketDocument.ReceiverMarketParticipantMRID }
     *
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setReceiverMarketParticipantMRID(PublicationMarketDocument.ReceiverMarketParticipantMRID value) {
        this.receiverMarketParticipantMRID = value;
    }

    /**
     * Ruft den Wert der receiverMarketParticipantMarketRoleType-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getReceiverMarketParticipantMarketRoleType() {
        return receiverMarketParticipantMarketRoleType;
    }

    /**
     * Legt den Wert der receiverMarketParticipantMarketRoleType-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setReceiverMarketParticipantMarketRoleType(String value) {
        this.receiverMarketParticipantMarketRoleType = value;
    }

    /**
     * Ruft den Wert der createdDateTime-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public XMLGregorianCalendar getCreatedDateTime() {
        return createdDateTime;
    }

    /**
     * Legt den Wert der createdDateTime-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setCreatedDateTime(XMLGregorianCalendar value) {
        this.createdDateTime = value;
    }

    /**
     * Ruft den Wert der periodTimeInterval-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link PublicationMarketDocument.PeriodTimeInterval }
     *
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public PublicationMarketDocument.PeriodTimeInterval getPeriodTimeInterval() {
        return periodTimeInterval;
    }

    /**
     * Legt den Wert der periodTimeInterval-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link PublicationMarketDocument.PeriodTimeInterval }
     *
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPeriodTimeInterval(PublicationMarketDocument.PeriodTimeInterval value) {
        this.periodTimeInterval = value;
    }

    /**
     * Gets the value of the timeSeries property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the timeSeries property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTimeSeries().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PublicationMarketDocument.TimeSeries }
     *
     *
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public List<TimeSeries> getTimeSeries() {
        if (timeSeries == null) {
            timeSeries = new ArrayList<TimeSeries>();
        }
        return this.timeSeries;
    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     *
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="start" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="end" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "start",
        "end"
    })
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public static class PeriodTimeInterval {

        @XmlElement(namespace = "urn:iec62325.351:tc57wg16:451-3:publicationdocument:7:0", required = true)
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        protected String start;
        @XmlElement(namespace = "urn:iec62325.351:tc57wg16:451-3:publicationdocument:7:0", required = true)
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        protected String end;

        /**
         * Ruft den Wert der start-Eigenschaft ab.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public String getStart() {
            return start;
        }

        /**
         * Legt den Wert der start-Eigenschaft fest.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public void setStart(String value) {
            this.start = value;
        }

        /**
         * Ruft den Wert der end-Eigenschaft ab.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public String getEnd() {
            return end;
        }

        /**
         * Legt den Wert der end-Eigenschaft fest.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public void setEnd(String value) {
            this.end = value;
        }

    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     *
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="codingScheme" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public static class ReceiverMarketParticipantMRID {

        @XmlValue
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        protected String value;
        @XmlAttribute(name = "codingScheme")
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        protected String codingScheme;

        /**
         * Ruft den Wert der value-Eigenschaft ab.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public String getValue() {
            return value;
        }

        /**
         * Legt den Wert der value-Eigenschaft fest.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Ruft den Wert der codingScheme-Eigenschaft ab.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public String getCodingScheme() {
            return codingScheme;
        }

        /**
         * Legt den Wert der codingScheme-Eigenschaft fest.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public void setCodingScheme(String value) {
            this.codingScheme = value;
        }

    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     *
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="codingScheme" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public static class SenderMarketParticipantMRID {

        @XmlValue
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        protected String value;
        @XmlAttribute(name = "codingScheme")
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        protected String codingScheme;

        /**
         * Ruft den Wert der value-Eigenschaft ab.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public String getValue() {
            return value;
        }

        /**
         * Legt den Wert der value-Eigenschaft fest.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Ruft den Wert der codingScheme-Eigenschaft ab.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public String getCodingScheme() {
            return codingScheme;
        }

        /**
         * Legt den Wert der codingScheme-Eigenschaft fest.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public void setCodingScheme(String value) {
            this.codingScheme = value;
        }

    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     *
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="mRID" type="{http://www.w3.org/2001/XMLSchema}byte"/>
     *         &lt;element name="businessType" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="in_Domain.mRID">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                 &lt;attribute name="codingScheme" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="out_Domain.mRID">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                 &lt;attribute name="codingScheme" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="currency_Unit.name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="price_Measure_Unit.name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="curveType" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="Period">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="timeInterval">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="start" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="end" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="resolution" type="{http://www.w3.org/2001/XMLSchema}duration"/>
     *                   &lt;element name="Point" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="position" type="{http://www.w3.org/2001/XMLSchema}byte"/>
     *                             &lt;element name="price.amount" type="{http://www.w3.org/2001/XMLSchema}float"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "mrid",
        "businessType",
        "inDomainMRID",
        "outDomainMRID",
        "currencyUnitName",
        "priceMeasureUnitName",
        "curveType",
        "period"
    })
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public static class TimeSeries {

        @XmlElement(name = "mRID", namespace = "urn:iec62325.351:tc57wg16:451-3:publicationdocument:7:0")
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        protected byte mrid;
        @XmlElement(namespace = "urn:iec62325.351:tc57wg16:451-3:publicationdocument:7:0", required = true)
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        protected String businessType;
        @XmlElement(name = "in_Domain.mRID", namespace = "urn:iec62325.351:tc57wg16:451-3:publicationdocument:7:0", required = true)
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        protected PublicationMarketDocument.TimeSeries.InDomainMRID inDomainMRID;
        @XmlElement(name = "out_Domain.mRID", namespace = "urn:iec62325.351:tc57wg16:451-3:publicationdocument:7:0", required = true)
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        protected PublicationMarketDocument.TimeSeries.OutDomainMRID outDomainMRID;
        @XmlElement(name = "currency_Unit.name", namespace = "urn:iec62325.351:tc57wg16:451-3:publicationdocument:7:0", required = true)
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        protected String currencyUnitName;
        @XmlElement(name = "price_Measure_Unit.name", namespace = "urn:iec62325.351:tc57wg16:451-3:publicationdocument:7:0", required = true)
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        protected String priceMeasureUnitName;
        @XmlElement(namespace = "urn:iec62325.351:tc57wg16:451-3:publicationdocument:7:0", required = true)
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        protected String curveType;
        @XmlElement(name = "Period", namespace = "urn:iec62325.351:tc57wg16:451-3:publicationdocument:7:0", required = true)
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        protected PublicationMarketDocument.TimeSeries.Period period;

        /**
         * Ruft den Wert der mrid-Eigenschaft ab.
         *
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public byte getMRID() {
            return mrid;
        }

        /**
         * Legt den Wert der mrid-Eigenschaft fest.
         *
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public void setMRID(byte value) {
            this.mrid = value;
        }

        /**
         * Ruft den Wert der businessType-Eigenschaft ab.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public String getBusinessType() {
            return businessType;
        }

        /**
         * Legt den Wert der businessType-Eigenschaft fest.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public void setBusinessType(String value) {
            this.businessType = value;
        }

        /**
         * Ruft den Wert der inDomainMRID-Eigenschaft ab.
         *
         * @return
         *     possible object is
         *     {@link PublicationMarketDocument.TimeSeries.InDomainMRID }
         *
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public PublicationMarketDocument.TimeSeries.InDomainMRID getInDomainMRID() {
            return inDomainMRID;
        }

        /**
         * Legt den Wert der inDomainMRID-Eigenschaft fest.
         *
         * @param value
         *     allowed object is
         *     {@link PublicationMarketDocument.TimeSeries.InDomainMRID }
         *
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public void setInDomainMRID(PublicationMarketDocument.TimeSeries.InDomainMRID value) {
            this.inDomainMRID = value;
        }

        /**
         * Ruft den Wert der outDomainMRID-Eigenschaft ab.
         *
         * @return
         *     possible object is
         *     {@link PublicationMarketDocument.TimeSeries.OutDomainMRID }
         *
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public PublicationMarketDocument.TimeSeries.OutDomainMRID getOutDomainMRID() {
            return outDomainMRID;
        }

        /**
         * Legt den Wert der outDomainMRID-Eigenschaft fest.
         *
         * @param value
         *     allowed object is
         *     {@link PublicationMarketDocument.TimeSeries.OutDomainMRID }
         *
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public void setOutDomainMRID(PublicationMarketDocument.TimeSeries.OutDomainMRID value) {
            this.outDomainMRID = value;
        }

        /**
         * Ruft den Wert der currencyUnitName-Eigenschaft ab.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public String getCurrencyUnitName() {
            return currencyUnitName;
        }

        /**
         * Legt den Wert der currencyUnitName-Eigenschaft fest.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public void setCurrencyUnitName(String value) {
            this.currencyUnitName = value;
        }

        /**
         * Ruft den Wert der priceMeasureUnitName-Eigenschaft ab.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public String getPriceMeasureUnitName() {
            return priceMeasureUnitName;
        }

        /**
         * Legt den Wert der priceMeasureUnitName-Eigenschaft fest.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public void setPriceMeasureUnitName(String value) {
            this.priceMeasureUnitName = value;
        }

        /**
         * Ruft den Wert der curveType-Eigenschaft ab.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public String getCurveType() {
            return curveType;
        }

        /**
         * Legt den Wert der curveType-Eigenschaft fest.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public void setCurveType(String value) {
            this.curveType = value;
        }

        /**
         * Ruft den Wert der period-Eigenschaft ab.
         *
         * @return
         *     possible object is
         *     {@link PublicationMarketDocument.TimeSeries.Period }
         *
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public PublicationMarketDocument.TimeSeries.Period getPeriod() {
            return period;
        }

        /**
         * Legt den Wert der period-Eigenschaft fest.
         *
         * @param value
         *     allowed object is
         *     {@link PublicationMarketDocument.TimeSeries.Period }
         *
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public void setPeriod(PublicationMarketDocument.TimeSeries.Period value) {
            this.period = value;
        }


        /**
         * <p>Java-Klasse für anonymous complex type.
         *
         * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
         *
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *       &lt;attribute name="codingScheme" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public static class InDomainMRID {

            @XmlValue
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected String value;
            @XmlAttribute(name = "codingScheme")
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected String codingScheme;

            /**
             * Ruft den Wert der value-Eigenschaft ab.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public String getValue() {
                return value;
            }

            /**
             * Legt den Wert der value-Eigenschaft fest.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public void setValue(String value) {
                this.value = value;
            }

            /**
             * Ruft den Wert der codingScheme-Eigenschaft ab.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public String getCodingScheme() {
                return codingScheme;
            }

            /**
             * Legt den Wert der codingScheme-Eigenschaft fest.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public void setCodingScheme(String value) {
                this.codingScheme = value;
            }

        }


        /**
         * <p>Java-Klasse für anonymous complex type.
         *
         * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
         *
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *       &lt;attribute name="codingScheme" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public static class OutDomainMRID {

            @XmlValue
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected String value;
            @XmlAttribute(name = "codingScheme")
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected String codingScheme;

            /**
             * Ruft den Wert der value-Eigenschaft ab.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public String getValue() {
                return value;
            }

            /**
             * Legt den Wert der value-Eigenschaft fest.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public void setValue(String value) {
                this.value = value;
            }

            /**
             * Ruft den Wert der codingScheme-Eigenschaft ab.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public String getCodingScheme() {
                return codingScheme;
            }

            /**
             * Legt den Wert der codingScheme-Eigenschaft fest.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public void setCodingScheme(String value) {
                this.codingScheme = value;
            }

        }


        /**
         * <p>Java-Klasse für anonymous complex type.
         *
         * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
         *
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="timeInterval">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="start" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="end" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="resolution" type="{http://www.w3.org/2001/XMLSchema}duration"/>
         *         &lt;element name="Point" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="position" type="{http://www.w3.org/2001/XMLSchema}byte"/>
         *                   &lt;element name="price.amount" type="{http://www.w3.org/2001/XMLSchema}float"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "timeInterval",
            "resolution",
            "point"
        })
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public static class Period {

            @XmlElement(namespace = "urn:iec62325.351:tc57wg16:451-3:publicationdocument:7:0", required = true)
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected PublicationMarketDocument.TimeSeries.Period.TimeInterval timeInterval;
            @XmlElement(namespace = "urn:iec62325.351:tc57wg16:451-3:publicationdocument:7:0", required = true)
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected Duration resolution;
            @XmlElement(name = "Point", namespace = "urn:iec62325.351:tc57wg16:451-3:publicationdocument:7:0")
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected List<Point> point;

            /**
             * Ruft den Wert der timeInterval-Eigenschaft ab.
             *
             * @return
             *     possible object is
             *     {@link PublicationMarketDocument.TimeSeries.Period.TimeInterval }
             *
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public PublicationMarketDocument.TimeSeries.Period.TimeInterval getTimeInterval() {
                return timeInterval;
            }

            /**
             * Legt den Wert der timeInterval-Eigenschaft fest.
             *
             * @param value
             *     allowed object is
             *     {@link PublicationMarketDocument.TimeSeries.Period.TimeInterval }
             *
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public void setTimeInterval(PublicationMarketDocument.TimeSeries.Period.TimeInterval value) {
                this.timeInterval = value;
            }

            /**
             * Ruft den Wert der resolution-Eigenschaft ab.
             *
             * @return
             *     possible object is
             *     {@link Duration }
             *
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public Duration getResolution() {
                return resolution;
            }

            /**
             * Legt den Wert der resolution-Eigenschaft fest.
             *
             * @param value
             *     allowed object is
             *     {@link Duration }
             *
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public void setResolution(Duration value) {
                this.resolution = value;
            }

            /**
             * Gets the value of the point property.
             *
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the point property.
             *
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getPoint().add(newItem);
             * </pre>
             *
             *
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link PublicationMarketDocument.TimeSeries.Period.Point }
             *
             *
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public List<Point> getPoint() {
                if (point == null) {
                    point = new ArrayList<Point>();
                }
                return this.point;
            }


            /**
             * <p>Java-Klasse für anonymous complex type.
             * 
             * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="position" type="{http://www.w3.org/2001/XMLSchema}byte"/>
             *         &lt;element name="price.amount" type="{http://www.w3.org/2001/XMLSchema}float"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "position",
                "priceAmount"
            })
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public static class Point {

                @XmlElement(namespace = "urn:iec62325.351:tc57wg16:451-3:publicationdocument:7:0")
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected byte position;
                @XmlElement(name = "price.amount", namespace = "urn:iec62325.351:tc57wg16:451-3:publicationdocument:7:0")
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected float priceAmount;

                /**
                 * Ruft den Wert der position-Eigenschaft ab.
                 * 
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public byte getPosition() {
                    return position;
                }

                /**
                 * Legt den Wert der position-Eigenschaft fest.
                 * 
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setPosition(byte value) {
                    this.position = value;
                }

                /**
                 * Ruft den Wert der priceAmount-Eigenschaft ab.
                 * 
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public float getPriceAmount() {
                    return priceAmount;
                }

                /**
                 * Legt den Wert der priceAmount-Eigenschaft fest.
                 * 
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setPriceAmount(float value) {
                    this.priceAmount = value;
                }

            }


            /**
             * <p>Java-Klasse für anonymous complex type.
             * 
             * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="start" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="end" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "start",
                "end"
            })
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public static class TimeInterval {

                @XmlElement(namespace = "urn:iec62325.351:tc57wg16:451-3:publicationdocument:7:0", required = true)
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected String start;
                @XmlElement(namespace = "urn:iec62325.351:tc57wg16:451-3:publicationdocument:7:0", required = true)
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected String end;

                /**
                 * Ruft den Wert der start-Eigenschaft ab.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public String getStart() {
                    return start;
                }

                /**
                 * Legt den Wert der start-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setStart(String value) {
                    this.start = value;
                }

                /**
                 * Ruft den Wert der end-Eigenschaft ab.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public String getEnd() {
                    return end;
                }

                /**
                 * Legt den Wert der end-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2019-01-07T06:55:08+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setEnd(String value) {
                    this.end = value;
                }

            }

        }

    }

}
