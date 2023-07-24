/*
 * Copyright (C) 2023 The VIEWTIFY Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package viewtify.ui;

import org.controlsfx.control.CheckComboBox;

import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import viewtify.property.SmartProperty;

public class UIComboCheckBox<T> extends AbstractComboBox<T, UIComboCheckBox<T>, CheckComboBox> {

    /** The item property. */
    private final Property<ObservableList<T>> itemProperty = new SmartProperty();

    /**
     * Builde {@link ComboBox}.
     * 
     * @param view A {@link View} to which the widget belongs.
     */
    public UIComboCheckBox(View view) {
        super(new CheckComboBox(), view);

        itemProperty.setValue(ui.getItems());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Property<ObservableList<T>> itemsProperty() {
        return itemProperty;
    }

    /**
     * Set title.
     * 
     * @param title
     * @return
     */
    public UIComboCheckBox<T> title(CharSequence title) {
        if (title != null) {
            ui.setTitle(title.toString());
            ui.setShowCheckedCount(true);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ComboBox<T> comboBox() {
        return (ComboBox) ui.getChildrenUnmodifiable().get(0);
    }
}