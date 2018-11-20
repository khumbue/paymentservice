package com.paymentservice.paymentservice.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Class represents the information stored in the internal xml converted from a SWIFT MT101 message that is understood by the system.
 * The class contains selected fields are mapped from a valid SWIFT MT101 message as well as other fields required in the process flow
 * such as status.
 */

@XmlRootElement
public class Payment implements Serializable {

    /**
     * Tag 20 – Sender Reference
     */
    String senderReference;

    /**
     * Tag 50F or 50G or 50H – Ordering Customer
     */
    String orderingCustomer;

    /**
     * Tag 50H – Ordering Customer
     * Only handling 50H for simplicity
     */
    String orderingCustomerAccountNumber;

    /**
     * Tag 52A or 52C – Account Servicing Institution
     */
    String sendingInstitution;

    /**
     * Tag 30 – Requested Execution Date
     */
    Date requestedExecutionDate;

    /**
     * Tag 21 – Transaction Reference
     */
    String transactionReference;

    /**
     * Tag 32B – Currency / Transaction Amount
     */
    String currency;

    /**
     * Tag 32B – Currency / Transaction Amount
     */
    BigDecimal transactionAmount;

    /**
     * Tag 59 – Ordering Customer
     * Only handling 59 for simplicity
     */
    String beneficiaryCustomer;

    /**
     * Tag 59 – Ordering Customer Account Number
     * Only handling 59 for simplicity
     */
    String beneficiaryAccountNumber;

    /**
     * Tag 71A – Details of Charges
     */
    String detailsOfCharges;

    /**
     * Payment Message Status
     */
    String status;

    /**
     * Target Engine
     */
    private String targetEngine;

    public String getSenderReference() {
        return senderReference;
    }

    public void setSenderReference(String senderReference) {
        this.senderReference = senderReference;
    }

    public Date getRequestedExecutionDate() {
        return requestedExecutionDate;
    }

    public void setRequestedExecutionDate(Date requestedExecutionDate) {
        this.requestedExecutionDate = requestedExecutionDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getOrderingCustomer() {
        return orderingCustomer;
    }

    public void setOrderingCustomer(String orderingCustomer) {
        this.orderingCustomer = orderingCustomer;
    }

    public String getBeneficiaryAccountNumber() {
        return beneficiaryAccountNumber;
    }

    public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) {
        this.beneficiaryAccountNumber = beneficiaryAccountNumber;
    }

    public String getBeneficiaryCustomer() {
        return beneficiaryCustomer;
    }

    public void setBeneficiaryCustomer(String beneficiaryCustomer) {
        this.beneficiaryCustomer = beneficiaryCustomer;
    }

    public String getDetailsOfCharges() {
        return detailsOfCharges;
    }

    public void setDetailsOfCharges(String detailsOfCharges) {
        this.detailsOfCharges = detailsOfCharges;
    }

    public String getOrderingCustomerAccountNumber() {
        return orderingCustomerAccountNumber;
    }

    public void setOrderingCustomerAccountNumber(String orderingCustomerAccountNumber) {
        this.orderingCustomerAccountNumber = orderingCustomerAccountNumber;
    }

    public String getSendingInstitution() {
        return sendingInstitution;
    }

    public void setSendingInstitution(String sendingInstitution) {
        this.sendingInstitution = sendingInstitution;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTargetEngine() {
        return targetEngine;
    }

    public void setTargetEngine(String targetEngine) {
        this.targetEngine = targetEngine;
    }

    @Override
    public String toString() {
        return "Payment [senderReference=" + senderReference + ", bankOperationCode=" + ", requestedExecutionDate=" + requestedExecutionDate + ", currency=" + currency + ", transactionAmount=" + transactionAmount + ", orderingCustomer=" + orderingCustomer + ", beneficiaryAccountNumber=" + beneficiaryAccountNumber + ", beneficiaryCustomer=" + beneficiaryCustomer + ", detailsOfCharges="
                + detailsOfCharges + "]";
    }
}
