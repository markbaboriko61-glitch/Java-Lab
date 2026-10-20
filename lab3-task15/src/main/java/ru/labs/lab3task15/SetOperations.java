package ru.labs.lab3task15;

public final class SetOperations {
    private SetOperations() {}

    public static SymbolSet symmetricDifference(SymbolSet first, SymbolSet second) {
        return first.difference(second).union(second.difference(first));
    }
}
