/*
 * Copyright (C) 2017 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package viewtify.ui;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoZonedDateTime;
import java.util.function.Supplier;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.scene.control.DatePicker;

import viewtify.View;
import viewtify.ui.helper.EditableHelper;
import viewtify.ui.helper.PreferenceHelper;
import viewtify.ui.helper.User;

/**
 * @version 2018/08/05 0:28:49
 */
public class UIDatePicker extends UserInterface<UIDatePicker, DatePicker>
        implements PreferenceHelper<UIDatePicker, LocalDate>, EditableHelper<UIDatePicker>, Comparable<UIDatePicker> {

    /**
     * Enchanced view.
     * 
     * @param ui
     */
    protected UIDatePicker(DatePicker ui, View view) {
        super(ui, view);

        // FUNCTIONALITY : wheel scroll will change selection.
        when(User.Scroll, e -> {
            if (e.getDeltaY() < 0) {
                value(value().minusDays(1));
            } else {
                value(value().plusDays(1));
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BooleanProperty edit() {
        return ui.editableProperty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Property<LocalDate> model() {
        return ui.valueProperty();
    }

    public final ZonedDateTime zoned() {
        return value().atStartOfDay(ZoneId.of("UTC"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(UIDatePicker o) {
        return 0;
    }

    /**
     * See {@link #initial(LocalDate)}.
     */
    public final UIDatePicker initial(ZonedDateTime initialValue) {
        return initial(initialValue.toLocalDate());
    }

    /**
     * Compare this {@link LocalDate} with the specified date.
     * 
     * @param date A date to compare.
     * @return A result.
     */
    public final boolean isAfter(ChronoLocalDate date) {
        return 0 < value().compareTo(date);
    }

    /**
     * Compare this {@link LocalDate} with the specified date.
     * 
     * @param date A date to compare.
     * @return A result.
     */
    public final boolean isAfter(ChronoZonedDateTime date) {
        return isAfter(date.toLocalDate());
    }

    /**
     * Compare this {@link LocalDate} with the specified date.
     * 
     * @param date A date to compare.
     * @return A result.
     */
    public final boolean isAfter(Supplier<LocalDate> date) {
        return isAfter(date.get());
    }

    /**
     * Compare this {@link LocalDate} with the specified date.
     * 
     * @param date A date to compare.
     * @return A result.
     */
    public final boolean isAfterOrSame(ChronoLocalDate date) {
        return 0 <= value().compareTo(date);
    }

    /**
     * Compare this {@link LocalDate} with the specified date.
     * 
     * @param date A date to compare.
     * @return A result.
     */
    public final boolean isAfterOrSame(ChronoZonedDateTime date) {
        return isAfterOrSame(date.toLocalDate());
    }

    /**
     * Compare this {@link LocalDate} with the specified date.
     * 
     * @param date A date to compare.
     * @return A result.
     */
    public final boolean isBefore(ChronoLocalDate date) {
        return value().compareTo(date) < 0;
    }

    /**
     * Compare this {@link LocalDate} with the specified date.
     * 
     * @param date A date to compare.
     * @return A result.
     */
    public final boolean isBefore(ChronoZonedDateTime date) {
        return isBefore(date.toLocalDate());
    }

    /**
     * Compare this {@link LocalDate} with the specified date.
     * 
     * @param date A date to compare.
     * @return A result.
     */
    public final boolean isBeforeOrSame(ChronoLocalDate date) {
        return value().compareTo(date) <= 0;
    }

    /**
     * Compare this {@link LocalDate} with the specified date.
     * 
     * @param date A date to compare.
     * @return A result.
     */
    public final boolean isBeforeOrSame(ChronoZonedDateTime date) {
        return isBeforeOrSame(date.toLocalDate());
    }

    /**
     * Compare this {@link LocalDate} with the specified date.
     * 
     * @param date A date to compare.
     * @return A result.
     */
    public final boolean isBeforeOrSame(Supplier<LocalDate> date) {
        return isBeforeOrSame(date.get());
    }

    /**
     * Compare this {@link LocalDate} with the specified date.
     * 
     * @param date A date to compare.
     * @return A result.
     */
    public final boolean isSame(ChronoLocalDate date) {
        return value().compareTo(date) == 0;
    }

    /**
     * Compare this {@link LocalDate} with the specified date.
     * 
     * @param date A date to compare.
     * @return A result.
     */
    public final boolean isSame(ChronoZonedDateTime date) {
        return isSame(date.toLocalDate());
    }
}
