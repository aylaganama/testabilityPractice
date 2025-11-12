package org.example;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;


class FilterInvoiceStubTest {

    // ---- Stub DAO ----
    static class StubQueryInvoicesDAO extends QueryInvoicesDAO {
        StubQueryInvoicesDAO() { super(null); }

        @Override
        public List<Invoice> all() {
            return Arrays.asList(
                    new Invoice("INV1", 40),
                    new Invoice("INV2", 110),
                    new Invoice("INV3", 60)
            );
        }
    }

    @Test
    void filterInvoiceTest_withStubbedDAO() {
        // Inject the stub instead of real DAO
        FilterInvoice filter = new FilterInvoice(new StubQueryInvoicesDAO());

        List<Invoice> result = filter.lowValueInvoices();

        assertEquals(2, result.size(), "Should return 2 low-value invoices");
        assertTrue(result.stream().allMatch(i -> i.getValue() < 100));
    }
}
