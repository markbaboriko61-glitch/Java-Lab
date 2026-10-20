package ru.labs.lab5task3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

class AccountTest {
    @Test
    void storesAllOperationTypesAndBalances() {
        Instant now = Instant.parse("2026-01-01T00:00:00Z");
        Account account = new Account("Иван", Clock.fixed(now, ZoneOffset.UTC));
        Account.Operation deposit = account.deposit(new BigDecimal("100"), "Зачисление");
        Account.Operation withdrawal = account.withdraw(new BigDecimal("20"), "Наличные");
        Account.Operation payment = account.pay(new BigDecimal("30.55"), "Магазин");
        assertEquals(new BigDecimal("49.45"), account.getBalance());
        assertEquals(new BigDecimal("100.00"), deposit.getBalanceAfter());
        assertEquals(Account.OperationType.DEPOSIT, deposit.getType());
        assertEquals(Account.OperationType.WITHDRAWAL, withdrawal.getType());
        assertEquals(Account.OperationType.PAYMENT, payment.getType());
        assertEquals(now, payment.getTimestamp());
        assertEquals(3, payment.getId());
        assertEquals("Иван", payment.getAccountOwner());
        assertEquals("Иван", account.getOwner());
        assertEquals("Магазин", payment.getDescription());
        assertEquals(new BigDecimal("30.55"), payment.getAmount());
        assertTrue(payment.toString().contains("49.45"));
        assertThrows(UnsupportedOperationException.class, () -> account.getOperations().clear());
    }

    @Test
    void rejectsInvalidOperationsWithoutChangingState() {
        Account account = new Account("Иван");
        assertThrows(IllegalArgumentException.class, () -> account.deposit(BigDecimal.ZERO, "x"));
        assertThrows(
                IllegalArgumentException.class, () -> account.deposit(new BigDecimal("-1"), "x"));
        assertThrows(IllegalArgumentException.class, () -> account.deposit(BigDecimal.ONE, " "));
        assertThrows(
                ArithmeticException.class, () -> account.deposit(new BigDecimal("1.001"), "x"));
        assertThrows(IllegalStateException.class, () -> account.withdraw(BigDecimal.ONE, "x"));
        assertThrows(IllegalStateException.class, () -> account.pay(BigDecimal.ONE, "x"));
        assertEquals(new BigDecimal("0.00"), account.getBalance());
        assertTrue(account.getOperations().isEmpty());
        assertThrows(IllegalArgumentException.class, () -> new Account(" "));
    }

    @Test
    void permitsSpendingExactBalance() {
        Account account = new Account("Иван");
        account.deposit(BigDecimal.ONE, "x");
        account.pay(BigDecimal.ONE, "y");
        assertEquals(new BigDecimal("0.00"), account.getBalance());
    }

    @Test
    void demoRuns() {
        assertDoesNotThrow(() -> Main.main(new String[0]));
    }
}
