package org.example;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Requirement 1 – Task 3
 * Combines both Integration and Stubbed tests for FilterInvoice.
 * Demonstrates difference between testing with real vs. fake dependency.
 */
class FilterInvoiceAllTests {

    // ------------------ Integration Test ------------------
    // Uses real QueryInvoicesDAO and Database (slower, full system)
    @Test
    void filterInvoiceTest_integration() {
        FilterInvoice filter = new FilterInvoice(); // real DB path
        List<Invoice> result = filter.lowValueInvoices();

        assertNotNull(result, "Result should not be null");
        assertTrue(result.stream().allMatch(i -> i.getValue() < 100),
                "Integration test: all invoices should be low value");
    }

    // ------------------ Stubbed Test ------------------
    // Uses fake DAO to isolate FilterInvoice logic
    static class StubQueryInvoicesDAO extends QueryInvoicesDAO {
        StubQueryInvoicesDAO() { super(null); }

        @Override
        public List<Invoice> all() {
            return Arrays.asList(
                    new Invoice("A1", 40),
                    new Invoice("A2", 120),
                    new Invoice("A3", 60)
            );
        }
    }

    @Test
    void filterInvoiceTest_stubbed() {
        FilterInvoice filter = new FilterInvoice(new StubQueryInvoicesDAO());
        List<Invoice> result = filter.lowValueInvoices();

        assertEquals(2, result.size(), "Stubbed test: expected 2 low-value invoices");
        assertTrue(result.stream().allMatch(i -> i.getValue() < 100));
    }
}
