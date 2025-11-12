package org.example;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Mock-based test using Mockito
class FilterInvoiceMockTest {

    @Test
    void filterInvoiceTest_withMockDAO() {
        // 1. create mock DAO
        QueryInvoicesDAO mockDao = mock(QueryInvoicesDAO.class);

        // 2. set up fake return list
        List<Invoice> invoices = List.of(
                new Invoice("INV1", 20),
                new Invoice("INV2", 130),
                new Invoice("INV3", 80)
        );
        when(mockDao.all()).thenReturn(invoices);

        // 3. inject mock into FilterInvoice
        FilterInvoice filter = new FilterInvoice(mockDao);

        // 4. run and assert results
        List<Invoice> low = filter.lowValueInvoices();

        assertEquals(2, low.size());
        assertTrue(low.stream().allMatch(i -> i.getValue() < 100));

        // 5. verify interaction
        verify(mockDao, times(1)).all();
    }
}
