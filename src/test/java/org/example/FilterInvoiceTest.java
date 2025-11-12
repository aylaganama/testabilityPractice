package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FilterInvoiceTest {

    @Test
    void filterInvoiceTest() {
        FilterInvoice filter = new FilterInvoice();
        assertNotNull(filter.lowValueInvoices(), "Result should not be null");
    }
}
