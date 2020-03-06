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

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TabPane.TabDragPolicy;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import kiss.Disposable;
import kiss.I;
import transcript.Transcript;
import viewtify.ui.helper.Actions;
import viewtify.ui.helper.CollectableHelper;
import viewtify.ui.helper.ContextMenuHelper;
import viewtify.ui.helper.SelectableHelper;
import viewtify.ui.helper.User;

public class UITabPane extends UserInterface<UITabPane, TabPane>
        implements ContextMenuHelper<UITabPane>, SelectableHelper<UITabPane, UITab>, CollectableHelper<UITabPane, UITab> {

    /** The model disposer. */
    private Disposable disposable = Disposable.empty();

    /**
     * Enchanced view.
     * 
     * @param view A {@link View} to which the widget belongs.
     */
    public UITabPane(View view) {
        super(new TabPane(), view);

        // FUNCTIONALITY : wheel scroll will change selection.
        when(User.Scroll).take(Actions.inside(() -> ui.lookup(".tab-header-background"))).to(Actions.traverse(ui.getSelectionModel()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Property<ObservableList<UITab>> itemsProperty() {
        return new SimpleObjectProperty(ui.getTabs());
    }

    /**
     * Set initial selected index.
     * 
     * @param initialSelectedIndex
     * @return
     */
    public UITabPane initial(int initialSelectedIndex) {
        restore(ui.getSelectionModel().selectedIndexProperty(), v -> ui.getSelectionModel().select(v.intValue()), initialSelectedIndex);
        return this;
    }

    /**
     * Load tab with the specified view.
     * 
     * @param label Specify the label of the tab. This is used as a temporary label until the
     *            contents of the tab are read, as tab loading is delayed until needed actually.
     * @param loadingViewType A view type to load.
     * @return
     */
    public <V extends View> UITabPane load(String label, Class<V> loadingViewType) {
        return load(label, tab -> I.make(loadingViewType));
    }

    /**
     * Load tab with the specified view.
     * 
     * @param label Specify the label of the tab. This is used as a temporary label until the
     *            contents of the tab are read, as tab loading is delayed until needed actually.
     * @param loadingViewType A view type to load.
     * @return
     */
    public <V extends View> UITabPane load(String label, Function<UITab, View> viewBuilder) {
        UITab tab = new UITab(view, viewBuilder);
        tab.text(label).context(c -> {
            c.menu().text(Transcript.en("Tile")).when(User.Action, () -> tile(tab));
            c.menu().text(Transcript.en("Detach")).when(User.Action, () -> detach(tab));
        });

        ui.getTabs().add(tab);
        return this;
    }

    /**
     * The closing policy for the tabs.
     * 
     * @param policy The closing policy for the tabs.
     * @return Chainable API.
     */
    public UITabPane policy(TabClosingPolicy policy) {
        if (policy != null) {
            ui.setTabClosingPolicy(policy);
        }
        return this;
    }

    /**
     * detach The closing policy for the tabs.
     * 
     * @param policy The closing policy for the tabs.
     * @return Chainable API.
     */
    public UITabPane policy(TabDragPolicy policy) {
        if (policy != null) {
            ui.setTabDragPolicy(policy);
        }
        return this;
    }

    /**
     * Detach the specified tab.
     * 
     * @param tab
     */
    private void detach(UITab tab) {
        int originalIndex = ui.getTabs().indexOf(tab);

        Pane content = (Pane) tab.getContent();
        tab.setContent(null);

        Scene scene = new Scene(content, content.getPrefWidth(), content.getPrefHeight());
        scene.getStylesheets().addAll(ui.getScene().getStylesheets());

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.getIcons().addAll(((Stage) ui.getScene().getWindow()).getIcons());
        stage.setTitle(tab.getText());
        stage.setOnShown(e -> ui.getTabs().remove(tab));
        stage.setOnCloseRequest(e -> {
            stage.close();
            tab.setContent(content);
            ui.getTabs().add(originalIndex, tab);
        });
        stage.show();
    }

    /**
     * Tile the specified tab.
     * 
     * @param tab
     */
    private void tile(UITab tab) {
        System.out.println("tiling");
    }
}
