package ru.labs.lab5task3;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Account {
    public enum OperationType {
        DEPOSIT,
        WITHDRAWAL,
        PAYMENT
    }

    private final String owner;
    private final Clock clock;
    private BigDecimal balance = new BigDecimal("0.00");
    private final List<Operation> operations = new ArrayList<>();

    public Account(String owner) {
        this(owner, Clock.systemUTC());
    }

    public Account(String owner, Clock clock) {
        if (Objects.requireNonNull(owner).isBlank()) {
            throw new IllegalArgumentException("Владелец не указан");
        }
        this.owner = owner;
        this.clock = Objects.requireNonNull(clock);
    }

    public String getOwner() {
        return owner;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public List<Operation> getOperations() {
        return List.copyOf(operations);
    }

    public Operation deposit(BigDecimal amount, String description) {
        return register(OperationType.DEPOSIT, amount, description);
    }

    public Operation withdraw(BigDecimal amount, String description) {
        return register(OperationType.WITHDRAWAL, amount, description);
    }

    public Operation pay(BigDecimal amount, String recipient) {
        return register(OperationType.PAYMENT, amount, recipient);
    }

    private Operation register(OperationType type, BigDecimal amount, String description) {
        BigDecimal money = Objects.requireNonNull(amount).setScale(2, RoundingMode.UNNECESSARY);
        if (money.signum() <= 0) {
            throw new IllegalArgumentException("Сумма должна быть больше нуля");
        }
        if (Objects.requireNonNull(description).isBlank()) {
            throw new IllegalArgumentException("Пустое описание операции");
        }
        if (type != OperationType.DEPOSIT && balance.compareTo(money) < 0) {
            throw new IllegalStateException("Недостаточно средств");
        }
        BigDecimal nextBalance =
                type == OperationType.DEPOSIT ? balance.add(money) : balance.subtract(money);

        Operation operation =
                new Operation(
                        operations.size() + 1,
                        type,
                        money,
                        description,
                        clock.instant(),
                        nextBalance);
        operations.add(operation);
        balance = nextBalance;
        return operation;
    }

    public final class Operation {
        private final int id;
        private final OperationType type;
        private final BigDecimal amount;
        private final String description;
        private final Instant timestamp;
        private final BigDecimal balanceAfter;

        private Operation(
                int id,
                OperationType type,
                BigDecimal amount,
                String description,
                Instant timestamp,
                BigDecimal balanceAfter) {
            this.id = id;
            this.type = type;
            this.amount = amount;
            this.description = description;
            this.timestamp = timestamp;
            this.balanceAfter = balanceAfter;
        }

        public int getId() {
            return id;
        }

        public OperationType getType() {
            return type;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public String getDescription() {
            return description;
        }

        public Instant getTimestamp() {
            return timestamp;
        }

        public BigDecimal getBalanceAfter() {
            return balanceAfter;
        }

        public String getAccountOwner() {
            return Account.this.owner;
        }

        @Override
        public String toString() {
            return id
                    + ": "
                    + timestamp
                    + " "
                    + type
                    + " "
                    + amount
                    + " ("
                    + description
                    + "), остаток="
                    + balanceAfter;
        }
    }
}
