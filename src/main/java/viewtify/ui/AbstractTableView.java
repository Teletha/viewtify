/*
 * Copyright (C) 2019 viewtify Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package viewtify.ui;

import java.util.function.Function;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.text.Text;

import viewtify.bind.CalculatedList;
import viewtify.ui.helper.ContextMenuHelper;

/**
 * @version 2018/09/09 11:54:03
 */
public abstract class AbstractTableView<Self extends AbstractTableView, W extends Control, T> extends UserInterface<Self, W>
        implements ContextMenuHelper<Self> {

    /** The selection model. */
    private final CalculatedList<T> selection;

    /**
     * @param ui
     * @param view
     */
    protected AbstractTableView(W ui, View view, Function<W, CalculatedList<T>> selection) {
        super(ui, view);

        this.selection = selection.apply(ui);
    }

    /**
     * Get all selected values.
     * 
     * @return
     */
    public final CalculatedList<T> selection() {
        return selection;
    }

    /**
     * Specify placeholder property.
     * 
     * @return
     */
    protected abstract ObjectProperty<Node> placeholder();

    /**
     * Set placeholder text.
     * 
     * @param text A explaination.
     * @return
     */
    public final Self placeholder(String explaination) {
        return placeholder(new Text(explaination));
    }

    /**
     * Set placeholder.
     * 
     * @param node A explaination.
     * @return
     */
    public final Self placeholder(Node explaination) {
        placeholder().set(explaination);
        return (Self) this;
    }

    /**
     * Specify placeholder property.
     * 
     * @return
     */
    protected abstract TableSelectionModel selectionModel();

    /**
     * <p>
     * Specifies the selection mode to use in this selection model. The selection mode specifies how
     * many items in the underlying data model can be selected at any one time.
     * <p>
     */
    public final Self selectMultipleRows() {
        selectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        return (Self) this;
    }

    /**
     * <p>
     * Specifies the selection mode to use in this selection model. The selection mode specifies how
     * many items in the underlying data model can be selected at any one time.
     * <p>
     */
    public final Self selectSingleRow() {
        selectionModel().setSelectionMode(SelectionMode.SINGLE);
        return (Self) this;
    }
}
