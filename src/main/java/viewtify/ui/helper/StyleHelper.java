/*
 * Copyright (C) 2018 viewtify Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package viewtify.ui.helper;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.css.Styleable;

import kiss.Variable;
import stylist.Style;
import viewtify.Viewtify;

/**
 * @version 2017/12/02 18:19:15
 */
public interface StyleHelper<Self extends StyleHelper, S extends Styleable> {

    /**
     * Return the associated user interface.
     * 
     * @return
     */
    S ui();

    /**
     * Apply class name;
     * 
     * @param className
     */
    default Self style(String className) {
        Viewtify.inUI(() -> {
            ObservableList<String> classes = ui().getStyleClass();

            if (!classes.contains(className)) {
                classes.add(className);
            }
        });
        return (Self) this;
    }

    /**
     * Apply class name;
     * 
     * @param className
     */
    default Self style(Style... styles) {
        Viewtify.inUI(() -> {
            ObservableList<String> classes = ui().getStyleClass();

            for (Style style : styles) {
                if (!classes.contains(style.name())) {
                    classes.add(style.name());
                }
            }
        });
        return (Self) this;
    }

    /**
     * Apply class name;
     * 
     * @param className
     */
    default <E extends Enum<E>> Self style(E... states) {
        Viewtify.inUI(() -> {
            ObservableList<String> classes = ui().getStyleClass();

            for (E state : states) {
                String name = state.name();

                if (!classes.contains(name)) {
                    classes.add(name);
                }
            }
        });
        return (Self) this;
    }

    /**
     * Apply single state class by the specified enum.
     * 
     * @param state
     */
    default <E extends Enum<E>> Self styleOnly(E state) {
        if (state != null) {
            Viewtify.inUI(() -> {
                ObservableList<String> classes = ui().getStyleClass();

                for (Enum value : state.getClass().getEnumConstants()) {
                    String name = value.name();

                    if (state == value) {
                        if (!classes.contains(name)) {
                            classes.add(name);
                        }
                    } else {
                        classes.remove(name);
                    }
                }
            });
        }
        return (Self) this;
    }

    /**
     * Apply single state class by the specified enum.
     * 
     * @param node
     * @param state
     */
    default <E extends Enum<E>> Self styleOnly(Variable<E> state) {
        return styleOnly(Viewtify.calculate(state));
    }

    /**
     * Apply single state class by the specified enum.
     * 
     * @param node
     * @param state
     */
    default <E extends Enum<E>> Self styleOnly(ObservableValue<E> state) {
        state.addListener(o -> styleOnly(state.getValue()));
        return (Self) this;
    }

    /**
     * Clear all style for the specified enum type.
     * 
     * @param class1
     */
    default <E extends Enum<E>> Self unstyle(Class<E> style) {
        if (style != null) {
            Viewtify.inUI(() -> {
                ObservableList<String> classes = ui().getStyleClass();

                for (Enum value : style.getEnumConstants()) {
                    classes.remove(value.name());
                }
            });
        }
        return (Self) this;
    }

    /**
     * Create temporary {@link StyleHelper}.
     * 
     * @param styleable
     * @return
     */
    static StyleHelper of(Styleable styleable) {
        return new StyleHelper() {

            /**
             * {@inheritDoc}
             */
            @Override
            public Styleable ui() {
                return styleable;
            }
        };
    }
}
