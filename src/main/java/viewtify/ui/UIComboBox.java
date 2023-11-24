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

import java.util.function.Function;
import java.util.function.Supplier;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import kiss.Disposable;
import kiss.I;
import kiss.Signal;
import kiss.Signaling;
import kiss.WiseTriFunction;
import viewtify.Viewtify;
import viewtify.ui.helper.Actions;
import viewtify.ui.helper.CollectableValuedItemRenderingHelper;
import viewtify.ui.helper.EditableHelper;
import viewtify.ui.helper.User;

public class UIComboBox<T> extends AbstractComboBox<T, UIComboBox<T>, ComboBox<T>>
        implements CollectableValuedItemRenderingHelper<UIComboBox<T>, T>, EditableHelper<UIComboBox<T>> {

    /**
     * Builde {@link ComboBox}.
     * 
     * @param view A {@link View} to which the widget belongs.
     */
    public UIComboBox(View view) {
        super(new ComboBox(), view);

        // FUNCTIONALITY : wheel scroll will change selection.
        when(User.Scroll, Actions.traverse(ui.getSelectionModel()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Property<T> valueProperty() {
        return ui.valueProperty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Property<ObservableList<T>> itemsProperty() {
        return ui.itemsProperty();
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
    public UIComboBox<T> renderSelectedWhen(Function<Signal<T>, Signal<String>> text) {
        ListCell<T> cell = ui.getButtonCell();

        // clear previous cell
        if (cell instanceof DynamicLabel) {
            ((DynamicLabel) cell).disposer.dispose();
        }

        // assign new cell
        ui.setButtonCell(text == null ? null : new DynamicLabel(text));

        // API definition
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ComboBox<T> comboBox() {
        return ui;
    }

    /**
     * Append the empty value which represents no selection.
     * 
     * @return
     */
    public final UIComboBox<T> nullable() {
        addItemAtFirst(null);
        if (comboBox().getPlaceholder() == null) {
            placeholder(I.translate("No Selection"));
        }
        return this;
    }

    /**
     * Dynamic label.
     */
    private static class DynamicLabel<T> extends ListCell<T> {

        private final Signaling<T> signal = new Signaling();

        private final Disposable disposer;

        private DynamicLabel(Function<Signal<T>, Signal<String>> text) {
            disposer = signal.expose.plug(text).on(Viewtify.UIThread).to(this::setText);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void updateItem(T item, boolean empty) {
            if (empty) {
                setText("");
            } else {
                signal.accept(item);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <C> UIComboBox<T> renderByNode(Supplier<C> context, WiseTriFunction<C, T, Disposable, ? extends Node> renderer) {
        ui.setCellFactory(view -> new UIListView.GenericListCell<C, T>(context, renderer));
        return this;
    }
}