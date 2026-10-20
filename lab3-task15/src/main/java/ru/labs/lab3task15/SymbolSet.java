package ru.labs.lab3task15;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public final class SymbolSet {
    private final SortedSet<Integer> symbols = new TreeSet<>();

    public SymbolSet(String text) {
        Objects.requireNonNull(text).codePoints().forEach(symbols::add);
    }

    private SymbolSet(SortedSet<Integer> source) {
        symbols.addAll(source);
    }

    public boolean contains(int symbol) {
        return symbols.contains(symbol);
    }

    public int size() {
        return symbols.size();
    }

    public int get(int index) {
        return new ArrayList<>(symbols).get(index);
    }

    public SymbolSet union(SymbolSet other) {
        SortedSet<Integer> result = new TreeSet<>(symbols);
        result.addAll(other.symbols);
        return new SymbolSet(result);
    }

    public SymbolSet intersection(SymbolSet other) {
        SortedSet<Integer> result = new TreeSet<>(symbols);
        result.retainAll(other.symbols);
        return new SymbolSet(result);
    }

    public SymbolSet difference(SymbolSet other) {
        SortedSet<Integer> result = new TreeSet<>(symbols);
        result.removeAll(other.symbols);
        return new SymbolSet(result);
    }

    public SymbolSet add(SymbolSet other) {
        return union(other);
    }

    public SymbolSet subtract(SymbolSet other) {
        return difference(other);
    }

    public SymbolSet multiply(SymbolSet other) {
        return intersection(other);
    }

    public void assign(SymbolSet other) {
        Objects.requireNonNull(other);
        if (this != other) {
            symbols.clear();
            symbols.addAll(other.symbols);
        }
    }

    public List<Integer> elements() {
        return List.copyOf(symbols);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof SymbolSet set && symbols.equals(set.symbols);
    }

    @Override
    public int hashCode() {
        return symbols.hashCode();
    }

    @Override
    public String toString() {
        return symbols.stream()
                .map(code -> new String(Character.toChars(code)))
                .collect(Collectors.joining(", ", "{", "}"));
    }
}
