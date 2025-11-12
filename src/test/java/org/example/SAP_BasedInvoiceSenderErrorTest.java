package org.example;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import static org.mockito.Mockito.*;

class SAP_BasedInvoiceSenderErrorTest {

    @Test
    void testSendContinuesAfterException() {
        FilterInvoice mockFilter = Mockito.mock(FilterInvoice.class);
        SAP mockSAP = Mockito.mock(SAP.class);

        List<Invoice> invoices = List.of(
                new Invoice("A1", 40),
                new Invoice("A2", 60)
        );
        when(mockFilter.lowValueInvoices()).thenReturn(invoices);

        // Simulate first send() throwing an exception
        doThrow(new RuntimeException("Network fail"))
                .doNothing()
                .when(mockSAP)
                .send(any(Invoice.class));

        SAP_BasedInvoiceSender sender = new SAP_BasedInvoiceSender(mockFilter, mockSAP);
        sender.sendLowValuedInvoices();

        // Even if one send() fails, both should be attempted
        verify(mockSAP, times(2)).send(any(Invoice.class));
    }
}
