package ru.labs.lab5task3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class Main {
    private Main() {}

    public static void main(String[] args) {
        Account account = new Account("Иван Иванов");
        account.deposit(new BigDecimal("10000.00"), "Зарплата");
        account.withdraw(new BigDecimal("1500.00"), "Наличные");
        account.pay(new BigDecimal("2500.50"), "Магазин");
        System.out.println("Владелец: " + account.getOwner());
        System.out.println("Все операции:");
        account.getOperations().forEach(System.out::println);

        Comparator<Account.Operation> byAmount =
                new Comparator<>() {
                    @Override
                    public int compare(Account.Operation first, Account.Operation second) {
                        int result = second.getAmount().compareTo(first.getAmount());
                        return result != 0
                                ? result
                                : Integer.compare(first.getId(), second.getId());
                    }
                };
        List<Account.Operation> sorted = new ArrayList<>(account.getOperations());
        sorted.sort(byAmount);
        System.out.println("Операции по убыванию суммы:");
        sorted.forEach(System.out::println);
        System.out.println("Баланс: " + account.getBalance());
        try {
            account.withdraw(new BigDecimal("99999"), "Слишком большое снятие");
        } catch (IllegalStateException exception) {
            System.out.println("Операция отклонена: " + exception.getMessage());
        }
    }
}
