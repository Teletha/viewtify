/*
 * Copyright (C) 2019 viewtify Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package viewtify.bind;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.junit.jupiter.api.Test;

import kiss.Variable;
import viewtify.Viewtify;

/**
 * @version 2018/04/11 23:41:49
 */
class CalculatedListTest {

    @Test
    void calculateList() {
        ObservableList<String> source = FXCollections.observableArrayList("one", "two", "three");
        CalculatedList<String> result = Viewtify.calculate(source);
        assert result.isValid() == false;
        assert result.getValue().size() == 3;
        assert result.getValue().get(0).equals("one");
        assert result.getValue().get(1).equals("two");
        assert result.getValue().get(2).equals("three");
        assert result.isValid() == true;

        // add
        source.add("four");
        assert result.isValid() == false;
        assert result.getValue().size() == 4;
        assert result.getValue().get(0).equals("one");
        assert result.getValue().get(1).equals("two");
        assert result.getValue().get(2).equals("three");
        assert result.getValue().get(3).equals("four");
        assert result.isValid() == true;

        // remove
        source.remove(0);
        assert result.isValid() == false;
        assert result.getValue().size() == 3;
        assert result.getValue().get(0).equals("two");
        assert result.getValue().get(1).equals("three");
        assert result.getValue().get(2).equals("four");
        assert result.isValid() == true;

        // replace
        source.set(1, "replace");
        assert result.isValid() == false;
        assert result.getValue().size() == 3;
        assert result.getValue().get(0).equals("two");
        assert result.getValue().get(1).equals("replace");
        assert result.getValue().get(2).equals("four");
        assert result.isValid() == true;
    }

    @Test
    void map() {
        ObservableList<String> source = FXCollections.observableArrayList("one", "two", "three");
        CalculatedList<String> result = Viewtify.calculate(source).map(String::toUpperCase);
        assert result.isValid() == false;
        assert result.getValue().size() == 3;
        assert result.getValue().get(0).equals("ONE");
        assert result.getValue().get(1).equals("TWO");
        assert result.getValue().get(2).equals("THREE");
        assert result.isValid() == true;

        // add
        source.add("four");
        assert result.isValid() == false;
        assert result.getValue().size() == 4;
        assert result.getValue().get(0).equals("ONE");
        assert result.getValue().get(1).equals("TWO");
        assert result.getValue().get(2).equals("THREE");
        assert result.getValue().get(3).equals("FOUR");
        assert result.isValid() == true;

        // remove
        source.remove(0);
        assert result.isValid() == false;
        assert result.getValue().size() == 3;
        assert result.getValue().get(0).equals("TWO");
        assert result.getValue().get(1).equals("THREE");
        assert result.getValue().get(2).equals("FOUR");
        assert result.isValid() == true;

        // replace
        source.set(1, "replace");
        source.set(2, "replace");
        assert result.isValid() == false;
        assert result.getValue().size() == 3;
        assert result.getValue().get(0).equals("TWO");
        assert result.getValue().get(1).equals("REPLACE");
        assert result.getValue().get(2).equals("REPLACE");
        assert result.isValid() == true;
    }

    @Test
    void mapWithMultiCheck() {
        Value<String> v1 = Value.of("one");
        Value<String> v2 = Value.of("two");
        Value<String> v3 = Value.of("three");
        CalculatedList<String> result = Viewtify.calculate(FXCollections.observableArrayList(v1, v2, v3))
                .observe(o -> o.property)
                .observeVariable(o -> o.variable)
                .map(Value<String>::text);

        assert result.isValid() == false;
        assert result.getValue().size() == 3;
        assert result.getValue().get(0).equals("one one");
        assert result.getValue().get(1).equals("two two");
        assert result.getValue().get(2).equals("three three");
        assert result.isValid() == true;

        // change on property
        v1.property.set("property");
        assert result.isValid() == false;
        assert result.getValue().get(0).equals("property one");
        assert result.isValid() == true;

        // change on variable
        v1.variable.set("variable");
        assert result.isValid() == false;
        assert result.getValue().get(0).equals("property variable");
        assert result.isValid() == true;
    }

    @Test
    void observeObservable() {
        Value<String> v1 = Value.of("one");
        Value<String> v2 = Value.of("two");
        Value<String> v3 = Value.of("three");
        ObservableList<Value<String>> source = FXCollections.observableArrayList(v1, v2, v3);

        // normal validation
        CalculatedList<Value<String>> wrapped = Viewtify.calculate(source);
        assert wrapped.isValid() == false;
        assert wrapped.getValue().size() == 3;
        assert wrapped.isValid() == true;

        // change on property, never invalidate
        v1.property.set("never invalidate");
        assert wrapped.isValid() == true;
        assert wrapped.getValue().get(0).property.get().equals("never invalidate");
        assert wrapped.isValid() == true;

        // observe property changing
        wrapped.observe(v -> v.property);

        // change on property, invalidate
        v1.property.set("will invalidate");
        assert wrapped.isValid() == false;
        assert wrapped.getValue().get(0).property.get().equals("will invalidate");
        assert wrapped.isValid() == true;
    }

    @Test
    void observeVariable() {
        Value<String> v1 = Value.of("one");
        Value<String> v2 = Value.of("two");
        Value<String> v3 = Value.of("three");
        ObservableList<Value<String>> source = FXCollections.observableArrayList(v1, v2, v3);

        // normal validation
        CalculatedList<Value<String>> wrapped = Viewtify.calculate(source);
        assert wrapped.isValid() == false;
        assert wrapped.getValue().size() == 3;
        assert wrapped.isValid() == true;

        // change on variable, never invalidate
        v1.variable.set("never invalidate");
        assert wrapped.isValid() == true;
        assert wrapped.getValue().get(0).variable.get().equals("never invalidate");
        assert wrapped.isValid() == true;

        // observe variable changing
        wrapped.observeVariable(v -> v.variable);

        // change on variable, invalidate
        v1.variable.set("will invalidate");
        assert wrapped.isValid() == false;
        assert wrapped.getValue().get(0).variable.get().equals("will invalidate");
        assert wrapped.isValid() == true;
    }

    @Test
    void observeVariables() {
        Value<String> value = Value.of("one");
        ObservableList<Value<String>> source = FXCollections.observableArrayList(value);

        // normal validation
        CalculatedList<String> wrapped = Viewtify.calculate(source)
                .observeVariable(v -> v.variable)
                .observeVariable(v -> v.variableOther)
                .map(v -> v.variable.v + " is " + v.variableOther.v);
        assert wrapped.isValid() == false;
        assert wrapped.getValue().size() == 1;
        assert wrapped.isValid() == true;

        // change on other
        value.variableOther.set("changed");
        assert wrapped.isValid() == false;
        assert wrapped.getValue().get(0).equals("one is changed");
        assert wrapped.isValid() == true;

        // change on variable
        value.variable.set("item");
        assert wrapped.isValid() == false;
        assert wrapped.getValue().get(0).equals("item is changed");
        assert wrapped.isValid() == true;

        // remove item
        source.remove(value);
        assert wrapped.isValid() == false;
        assert wrapped.getValue().size() == 0;
        assert wrapped.isValid() == true;
    }

    @Test
    void flatObservable() {
        Value<String> v1 = Value.of("one");
        Value<String> v2 = Value.of("two");
        Value<String> v3 = Value.of("three");
        ObservableList<Value<String>> source = FXCollections.observableArrayList(v1, v2, v3);
        CalculatedList<String> result = Viewtify.calculate(source).flatObservable(v -> v.property);
        assert result.isValid() == false;
        assert result.getValue().size() == 3;
        assert result.getValue().get(0).equals("one");
        assert result.getValue().get(1).equals("two");
        assert result.getValue().get(2).equals("three");
        assert result.isValid() == true;

        // add to source list
        Value<String> v4 = Value.of("four");
        source.add(v4);
        assert result.isValid() == false;
        assert result.getValue().size() == 4;
        assert result.getValue().get(0).equals("one");
        assert result.getValue().get(1).equals("two");
        assert result.getValue().get(2).equals("three");
        assert result.getValue().get(3).equals("four");
        assert result.isValid() == true;

        // change on source list
        source.set(0, Value.of("change"));
        assert result.isValid() == false;
        assert result.getValue().size() == 4;
        assert result.getValue().get(0).equals("change");
        assert result.getValue().get(1).equals("two");
        assert result.getValue().get(2).equals("three");
        assert result.getValue().get(3).equals("four");
        assert result.isValid() == true;

        // change on source item
        v2.property.set("change on item");
        assert result.isValid() == false;
        assert result.getValue().size() == 4;
        assert result.getValue().get(0).equals("change");
        assert result.getValue().get(1).equals("change on item");
        assert result.getValue().get(2).equals("three");
        assert result.getValue().get(3).equals("four");
        assert result.isValid() == true;

        // change on source item
        v2.property.set("change again");
        assert result.isValid() == false;
        assert result.getValue().size() == 4;
        assert result.getValue().get(0).equals("change");
        assert result.getValue().get(1).equals("change again");
        assert result.getValue().get(2).equals("three");
        assert result.getValue().get(3).equals("four");
        assert result.isValid() == true;

        // change on added source item
        v4.property.set("change on item");
        assert result.isValid() == false;
        assert result.getValue().size() == 4;
        assert result.getValue().get(0).equals("change");
        assert result.getValue().get(1).equals("change again");
        assert result.getValue().get(2).equals("three");
        assert result.getValue().get(3).equals("change on item");
        assert result.isValid() == true;

        // change on added source item again
        v4.property.set("change again");
        assert result.isValid() == false;
        assert result.getValue().size() == 4;
        assert result.getValue().get(0).equals("change");
        assert result.getValue().get(1).equals("change again");
        assert result.getValue().get(2).equals("three");
        assert result.getValue().get(3).equals("change again");
        assert result.isValid() == true;

        // dispose
        result.dispose();
        v2.property.set("No Effect");
        assert result.isValid() == true;
        source.remove(0);
        assert result.isValid() == true;
        source.add(0, v1);
        assert result.isValid() == true;
    }

    @Test
    void flatObservableAndMap() {
        Value<String> v1 = Value.of("one");
        Value<String> v2 = Value.of("two");
        Value<String> v3 = Value.of("three");
        ObservableList<Value<String>> source = FXCollections.observableArrayList(v1, v2, v3);
        CalculatedList<String> result = Viewtify.calculate(source).flatObservable(v -> v.property).map(String::toUpperCase);
        assert result.getValue().size() == 3;
        assert result.getValue().get(0).equals("ONE");
        assert result.getValue().get(1).equals("TWO");
        assert result.getValue().get(2).equals("THREE");
        assert result.isValid() == true;

        // add to source list
        Value<String> v4 = Value.of("four");
        source.add(v4);
        assert result.isValid() == false;
        assert result.getValue().size() == 4;
        assert result.getValue().get(0).equals("ONE");
        assert result.getValue().get(1).equals("TWO");
        assert result.getValue().get(2).equals("THREE");
        assert result.getValue().get(3).equals("FOUR");
        assert result.isValid() == true;

        // change on source list
        source.set(0, Value.of("change"));
        assert result.isValid() == false;
        assert result.getValue().size() == 4;
        assert result.getValue().get(0).equals("CHANGE");
        assert result.getValue().get(1).equals("TWO");
        assert result.getValue().get(2).equals("THREE");
        assert result.getValue().get(3).equals("FOUR");
        assert result.isValid() == true;

        // change on source item
        v2.property.set("property");
        assert result.isValid() == false;
        assert result.getValue().size() == 4;
        assert result.getValue().get(0).equals("CHANGE");
        assert result.getValue().get(1).equals("PROPERTY");
        assert result.getValue().get(2).equals("THREE");
        assert result.getValue().get(3).equals("FOUR");
        assert result.isValid() == true;

        // dispose
        result.dispose();
        v2.property.set("No Effect");
        assert result.isValid() == true;
        source.remove(0);
        assert result.isValid() == true;
        source.add(0, v1);
        assert result.isValid() == true;
    }

    @Test
    void flatObservableAndReduce() {
        Value<Integer> v1 = Value.of(1);
        Value<Integer> v2 = Value.of(2);
        Value<Integer> v3 = Value.of(3);
        ObservableList<Value<Integer>> source = FXCollections.observableArrayList(v1, v2, v3);
        Calculated<Integer> result = Viewtify.calculate(source).flatObservable(v -> v.property).reduce(0, (p, q) -> p + q);
        assert result.getValue() == 6;

        // add to source list
        Value<Integer> v4 = Value.of(4);
        source.add(v4);
        assert result.getValue() == 10;

        // change on source list
        source.set(0, Value.of(10));
        assert result.getValue() == 19;

        // change on source item
        v2.property.set(5);
        assert result.getValue() == 22;

        // dispose
        result.dispose();
        v2.property.set(1000);
        assert result.isValid() == true;
        source.remove(0);
        assert result.isValid() == true;
        source.add(0, v1);
        assert result.isValid() == true;
    }

    @Test
    void flatVariable() {
        Value<String> v1 = Value.of("one");
        Value<String> v2 = Value.of("two");
        Value<String> v3 = Value.of("three");
        ObservableList<Value<String>> source = FXCollections.observableArrayList(v1, v2, v3);
        CalculatedList<String> result = Viewtify.calculate(source).flatVariable(v -> v.variable);
        assert result.isValid() == false;
        assert result.getValue().size() == 3;
        assert result.getValue().get(0).equals("one");
        assert result.getValue().get(1).equals("two");
        assert result.getValue().get(2).equals("three");
        assert result.isValid() == true;

        // add to source list
        Value<String> v4 = Value.of("four");
        source.add(v4);
        assert result.isValid() == false;
        assert result.getValue().size() == 4;
        assert result.getValue().get(0).equals("one");
        assert result.getValue().get(1).equals("two");
        assert result.getValue().get(2).equals("three");
        assert result.getValue().get(3).equals("four");
        assert result.isValid() == true;

        // change on source list
        source.set(0, Value.of("change"));
        assert result.isValid() == false;
        assert result.getValue().size() == 4;
        assert result.getValue().get(0).equals("change");
        assert result.getValue().get(1).equals("two");
        assert result.getValue().get(2).equals("three");
        assert result.getValue().get(3).equals("four");
        assert result.isValid() == true;

        // change on source item
        v2.variable.set("change on item");
        assert result.isValid() == false;
        assert result.getValue().size() == 4;
        assert result.getValue().get(0).equals("change");
        assert result.getValue().get(1).equals("change on item");
        assert result.getValue().get(2).equals("three");
        assert result.getValue().get(3).equals("four");
        assert result.isValid() == true;

        // change on source item again
        v2.variable.set("change again");
        assert result.isValid() == false;
        assert result.getValue().size() == 4;
        assert result.getValue().get(0).equals("change");
        assert result.getValue().get(1).equals("change again");
        assert result.getValue().get(2).equals("three");
        assert result.getValue().get(3).equals("four");
        assert result.isValid() == true;

        // change on added source item
        v4.variable.set("change on item");
        assert result.isValid() == false;
        assert result.getValue().size() == 4;
        assert result.getValue().get(0).equals("change");
        assert result.getValue().get(1).equals("change again");
        assert result.getValue().get(2).equals("three");
        assert result.getValue().get(3).equals("change on item");
        assert result.isValid() == true;

        // change on added source item again
        v4.variable.set("change again");
        assert result.isValid() == false;
        assert result.getValue().size() == 4;
        assert result.getValue().get(0).equals("change");
        assert result.getValue().get(1).equals("change again");
        assert result.getValue().get(2).equals("three");
        assert result.getValue().get(3).equals("change again");
        assert result.isValid() == true;

        // dispose
        result.dispose();
        v2.variable.set("No Effect");
        assert result.isValid() == true;
        source.remove(0);
        assert result.isValid() == true;
        source.add(0, v1);
        assert result.isValid() == true;
    }

    @Test
    void flatVariableAndMap() {
        Value<String> v1 = Value.of("one");
        Value<String> v2 = Value.of("two");
        Value<String> v3 = Value.of("three");
        ObservableList<Value<String>> source = FXCollections.observableArrayList(v1, v2, v3);
        CalculatedList<String> result = Viewtify.calculate(source).flatVariable(v -> v.variable).map(String::toUpperCase);
        assert result.getValue().size() == 3;
        assert result.getValue().get(0).equals("ONE");
        assert result.getValue().get(1).equals("TWO");
        assert result.getValue().get(2).equals("THREE");
        assert result.isValid() == true;

        // add to source list
        Value<String> v4 = Value.of("four");
        source.add(v4);
        assert result.isValid() == false;
        assert result.getValue().size() == 4;
        assert result.getValue().get(0).equals("ONE");
        assert result.getValue().get(1).equals("TWO");
        assert result.getValue().get(2).equals("THREE");
        assert result.getValue().get(3).equals("FOUR");
        assert result.isValid() == true;

        // change on source list
        source.set(0, Value.of("change"));
        assert result.isValid() == false;
        assert result.getValue().size() == 4;
        assert result.getValue().get(0).equals("CHANGE");
        assert result.getValue().get(1).equals("TWO");
        assert result.getValue().get(2).equals("THREE");
        assert result.getValue().get(3).equals("FOUR");
        assert result.isValid() == true;

        // change on source item
        v2.variable.set("variable");
        assert result.isValid() == false;
        assert result.getValue().size() == 4;
        assert result.getValue().get(0).equals("CHANGE");
        assert result.getValue().get(1).equals("VARIABLE");
        assert result.getValue().get(2).equals("THREE");
        assert result.getValue().get(3).equals("FOUR");
        assert result.isValid() == true;

        // dispose
        result.dispose();
        v2.variable.set("No Effect");
        assert result.isValid() == true;
        source.remove(0);
        assert result.isValid() == true;
        source.add(0, v1);
        assert result.isValid() == true;
    }

    @Test
    void flatVariableAndReduce() {
        Value<Integer> v1 = Value.of(1);
        Value<Integer> v2 = Value.of(2);
        Value<Integer> v3 = Value.of(3);
        ObservableList<Value<Integer>> source = FXCollections.observableArrayList(v1, v2, v3);
        Calculated<Integer> result = Viewtify.calculate(source).flatVariable(v -> v.variable).reduce(0, (p, q) -> p + q);
        assert result.getValue() == 6;
        assert result.isValid() == true;

        // add to source list
        Value<Integer> v4 = Value.of(4);
        source.add(v4);
        assert result.isValid() == false;
        assert result.getValue() == 10;
        assert result.isValid() == true;

        // change on source list
        source.set(0, Value.of(10));
        assert result.isValid() == false;
        assert result.getValue() == 19;
        assert result.isValid() == true;

        // change on source item
        v2.variable.set(5);
        assert result.isValid() == false;
        assert result.getValue() == 22;
        assert result.isValid() == true;

        // dispose
        result.dispose();
        v2.variable.set(10000);
        assert result.isValid() == true;
        source.remove(0);
        assert result.isValid() == true;
        source.add(0, v1);
        assert result.isValid() == true;
    }

    @Test
    void isNot() {
        ObservableList<String> source = FXCollections.observableArrayList("value1", "value2", "value3");
        Calculated<Boolean> result = Viewtify.calculate(source).isNot("NG");
        assert result.isValid() == false;
        assert result.getValue() == true;
        assert result.isValid() == true;

        // change to NG
        source.set(0, "NG");
        assert result.isValid() == false;
        assert result.getValue() == false;
        assert result.isValid() == true;
    }

    @Test
    void concat() {
        ObservableList<String> source1 = FXCollections.observableArrayList("a", "b", "c");
        ObservableList<String> source2 = FXCollections.observableArrayList("A", "B", "C");
        CalculatedList<String> result = Viewtify.calculate(source1).concat(source2);

        assert result.item(0).get().equals("a");
        assert result.item(1).get().equals("b");
        assert result.item(2).get().equals("c");
        assert result.item(3).get().equals("A");
        assert result.item(4).get().equals("B");
        assert result.item(5).get().equals("C");

        source1.add("d");
        assert result.item(0).get().equals("a");
        assert result.item(1).get().equals("b");
        assert result.item(2).get().equals("c");
        assert result.item(3).get().equals("d");
        assert result.item(4).get().equals("A");
        assert result.item(5).get().equals("B");
        assert result.item(6).get().equals("C");
    }

    @Test
    void concatIterator() {
        ObservableList<String> source1 = FXCollections.observableArrayList("a", "b", "c");
        ObservableList<String> source2 = FXCollections.observableArrayList("A", "B", "C");
        CalculatedList<String> result = Viewtify.calculate(source1).concat(source2);

        List<String> list = StreamSupport.stream(result.spliterator(), false).collect(Collectors.toList());
        assert list.size() == 6;
        assert list.get(5).equals("C");
    }

    /**
     * @version 2018/04/02 16:42:46
     */
    private static class Value<T> {

        public final Variable<T> variable = Variable.empty();

        public final Variable<T> variableOther = Variable.empty();

        public final ObjectProperty<T> property = new SimpleObjectProperty();

        /**
         * Create new Value<String>.
         * 
         * @param Value<String>
         * @return
         */
        public static <T> Value<T> of(T value) {
            Value<T> v = new Value<T>();
            v.variable.set(value);
            v.property.set(value);

            return v;
        }

        private String text() {
            return property.get() + " " + variable.get();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "Value [variable=" + variable + ", property=" + property + "]";
        }
    }
}