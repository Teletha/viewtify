/*
 * Copyright (C) 2024 The VIEWTIFY Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package viewtify.ui;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoZonedDateTime;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import viewtify.ui.helper.ContextMenuHelper;
import viewtify.ui.helper.DnDAssistant;
import viewtify.ui.helper.EditableHelper;
import viewtify.ui.helper.User;
import viewtify.ui.helper.ValueHelper;

public class UIDatePicker extends UserInterface<UIDatePicker, DatePicker>
        implements ValueHelper<UIDatePicker, LocalDate>, EditableHelper<UIDatePicker>, ContextMenuHelper<UIDatePicker> {

    /** The drag and drop copy. */
    private static final DnDAssistant<LocalDate> DateTimeDnD = new DnDAssistant();

    /**
     * Builde {@link ComboBox}.
     * 
     * @param view A {@link View} to which the widget belongs.
     */
    public UIDatePicker(View view) {
        super(new DatePicker(), view);

        // FUNCTIONALITY : wheel scroll will change selection.
        when(User.Scroll, e -> {
            if (value() == null) return;

            if (e.getDeltaY() < 0) {
                value(v -> v.minusDays(1));
            } else if (e.getDeltaY() > 0) {
                value(v -> v.plusDays(1));
            } else if (e.getDeltaX() < 0) {
                value(v -> v.minusMonths(1));
            } else if (e.getDeltaX() > 0) {
                value(v -> v.plusMonths(1));
            }
        });
        DateTimeDnD.source(this).target(this);
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
    public Property<LocalDate> valueProperty() {
        return ui.valueProperty();
    }

    /**
     * Convert to UTC {@link ZonedDateTime}.
     * 
     * @return
     */
    public final ZonedDateTime zoned() {
        return value() == null ? null : value().atStartOfDay(ZoneId.of("UTC"));
    }

    /**
     * @param initialValue
     * @return
     */
    public final UIDatePicker initial(ZonedDateTime initialValue) {
        return initialize(initialValue.toLocalDate());
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