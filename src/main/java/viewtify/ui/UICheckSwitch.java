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

import javafx.beans.property.Property;
import javafx.beans.value.ObservableBooleanValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

import org.controlsfx.control.ToggleSwitch;

import viewtify.Viewtify;
import viewtify.ui.helper.ContextMenuHelper;
import viewtify.ui.helper.LabelHelper;
import viewtify.ui.helper.ValueHelper;

public class UICheckSwitch extends UserInterface<UICheckSwitch, ToggleSwitch>
        implements ValueHelper<UICheckSwitch, Boolean>, ContextMenuHelper<UICheckSwitch>, LabelHelper<UICheckSwitch> {

    /** The default label */
    private final String[] labels = {"ON", "OFF"};

    /**
     * Enchanced view.
     * 
     * @param view A {@link View} to which the widget belongs.
     */
    public UICheckSwitch(View view) {
        super(new ToggleSwitch(), view);

        Viewtify.observing(valueProperty()).to(x -> {
            text(x ? labels[0] : labels[1]);
        }, this);

        Viewtify.observe(ui.skinProperty()).map(skin -> skin.getNode().lookup(".label")).as(Label.class).to(skin -> {
            skin.setAlignment(Pos.CENTER_RIGHT);
            skin.setTextAlignment(TextAlignment.RIGHT);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Property<Boolean> valueProperty() {
        return ui.selectedProperty();
    }

    /**
     * Return true when this checkbox is selected.
     * 
     * @return
     */
    public ObservableBooleanValue isSelected() {
        return ui.selectedProperty();
    }

    /**
     * Return true when this checkbox is NOT selected.
     * 
     * @return
     */
    public ObservableBooleanValue isNotSelected() {
        return ui.selectedProperty().not();
    }

    /**
     * Set the state labels.
     * 
     * @param on
     * @param off
     * @return
     */
    public UICheckSwitch labels(String on, String off) {
        labels[0] = on;
        labels[1] = off;
        return this;
    }
}