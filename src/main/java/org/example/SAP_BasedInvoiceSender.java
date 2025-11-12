package org.example;

import java.util.List;

// Class responsible for sending low-valued invoices to the SAP system
public class SAP_BasedInvoiceSender {

    private final FilterInvoice filter;  // Dependency for filtering invoices
    private final SAP sap;  // Dependency for sending invoices to the SAP system

    // Constructor that uses dependency injection to initialize the filter and sap objects
    public SAP_BasedInvoiceSender(FilterInvoice filter, SAP sap) {
        this.filter = filter;
        this.sap = sap;
    }

    // Method to send all low-valued invoices to the SAP system
    public void sendLowValuedInvoices() {
        List<Invoice> lowValuedInvoices = filter.lowValueInvoices();
        for (Invoice invoice : lowValuedInvoices) {
            try {
                sap.send(invoice);
            } catch (Exception e) {
                // Just print the invoice itself instead of invoice.getId()
                System.err.println("Failed to send invoice: " + invoice + " | Reason: " + e.getMessage());
            }
        }
    }

}
