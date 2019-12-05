/*
 * Copyright (C) 2019 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package viewtify.ui.helper;

import java.util.List;
import java.util.stream.IntStream;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import kiss.I;

class CollectableHelperTest {

    @Test
    void items() {
        SimpleList<Integer> list = new SimpleList(1, 2, 3);
        Assertions.assertIterableEquals(List.of(1, 2, 3), list.items());
    }

    @Test
    void setArray() {
        SimpleList<Integer> list = new SimpleList();
        list.items(1, 2, 3);
        Assertions.assertIterableEquals(List.of(1, 2, 3), list.items());
    }

    @Test
    void setList() {
        SimpleList<Integer> list = new SimpleList();
        list.items(List.of(1, 2, 3));
        Assertions.assertIterableEquals(List.of(1, 2, 3), list.items());
    }

    @Test
    void setIterable() {
        SimpleList<Integer> list = new SimpleList();
        list.items((Iterable<Integer>) List.of(1, 2, 3));
        Assertions.assertIterableEquals(List.of(1, 2, 3), list.items());
    }

    @Test
    void setStream() {
        SimpleList<Integer> list = new SimpleList();
        list.items(IntStream.range(1, 4).boxed());
        Assertions.assertIterableEquals(List.of(1, 2, 3), list.items());
    }

    @Test
    void setSignal() {
        SimpleList<Integer> list = new SimpleList();
        list.items(I.signal(1, 2, 3));
        Assertions.assertIterableEquals(List.of(1, 2, 3), list.items());
    }

    /**
     * Simple Implementation.
     */
    private static class SimpleList<T> implements CollectableHelper<SimpleList<T>, T> {

        /** The actual list. */
        private final ObjectProperty<ObservableList<T>> property = new SimpleObjectProperty(FXCollections.observableArrayList());

        private SimpleList(T... initial) {
            property.getValue().addAll(initial);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Property<ObservableList<T>> itemProperty() {
            return property;
        }
    }
}
