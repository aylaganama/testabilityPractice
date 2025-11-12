package org.example;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import static org.mockito.Mockito.*;

class SAP_BasedInvoiceSenderTest {

    @Test
    void testWhenLowInvoicesSent() {
        // Arrange: mock dependencies
        FilterInvoice mockFilter = Mockito.mock(FilterInvoice.class);
        SAP mockSAP = Mockito.mock(SAP.class);

        // Stub lowValueInvoices() to return 2 invoices
        List<Invoice> mockInvoices = List.of(
                new Invoice("INV1", 40),
                new Invoice("INV2", 60)
        );
        when(mockFilter.lowValueInvoices()).thenReturn(mockInvoices);

        // Act
        SAP_BasedInvoiceSender sender = new SAP_BasedInvoiceSender(mockFilter, mockSAP);
        sender.sendLowValuedInvoices();

        // Assert: verify send() called twice
        verify(mockSAP, times(2)).send(any(Invoice.class));
    }

    @Test
    void testWhenNoInvoices() {
        // Arrange: mock dependencies
        FilterInvoice mockFilter = Mockito.mock(FilterInvoice.class);
        SAP mockSAP = Mockito.mock(SAP.class);

        // Stub to return an empty list
        when(mockFilter.lowValueInvoices()).thenReturn(List.of());

        // Act
        SAP_BasedInvoiceSender sender = new SAP_BasedInvoiceSender(mockFilter, mockSAP);
        sender.sendLowValuedInvoices();

        // Assert: verify send() never called
        verify(mockSAP, never()).send(any(Invoice.class));
    }
}
