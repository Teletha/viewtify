/*
 * Copyright (C) 2018 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package viewtify.ui.dock;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.skin.TabPaneSkin;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;

import kiss.I;
import kiss.Variable;

/**
 * Describes a logical view area which displays the views within a tab pane.
 */
class TabArea extends ViewArea<TabPane> {

    public List<String> ids = new ArrayList();

    /**
     * Create a new tab area.
     */
    TabArea() {
        super(new TabPane());

        node.getStyleClass().add("stop-anime");
        node.addEventHandler(DragEvent.DRAG_OVER, e -> DockSystem.onDragOver(e, this));
        node.addEventHandler(DragEvent.DRAG_ENTERED, e -> DockSystem.onDragEntered(e, this));
        node.addEventHandler(DragEvent.DRAG_EXITED, e -> DockSystem.onDragExited(e, this));
        node.addEventHandler(DragEvent.DRAG_DONE, e -> DockSystem.onDragDone(e, this));
        node.addEventHandler(DragEvent.DRAG_DROPPED, e -> DockSystem.onDragDropped(e, this));
        node.addEventHandler(MouseEvent.DRAG_DETECTED, e -> {
            I.signal(node.lookupAll(".tab"))
                    .take(tab -> tab.localToScene(tab.getBoundsInLocal()).contains(e.getSceneX(), e.getSceneY()))
                    .first()
                    .to(tab -> {
                        DockSystem.onDragDetected(e, this, node.getSelectionModel().getSelectedItem());
                    });
        });

        // Since TabPane implementation delays the initialization of Skin and internal nodes
        // are not generated. So we should create Skin eagerly.
        TabPaneSkin skin = new TabPaneSkin(node);
        node.setSkin(skin);

        Node header = node.lookup(".tab-header-area");
        header.addEventHandler(DragEvent.DRAG_ENTERED, e -> DockSystem.onHeaderDragEntered(e, this));
        header.addEventHandler(DragEvent.DRAG_EXITED, e -> DockSystem.onHeaderDragExited(e, this));
        header.addEventHandler(DragEvent.DRAG_DROPPED, e -> DockSystem.onHeaderDragDropped(e, this));
        header.addEventHandler(DragEvent.DRAG_OVER, e -> DockSystem.onHeaderDragOver(e, this));
    }

    /**
     * Remove a view from this area. If this area is empty it will also be removed.
     *
     * @param tab The view to remove
     */
    void remove(Tab tab) {
        remove(tab, true);
    }

    /**
     * Remove a view from this area. If checkEmpty is true it checks if this area is empty and
     * remove this area.
     *
     * @param tab The view to remove.
     * @param checkEmpty Should this area be removed if it is empty?
     */
    void remove(Tab tab, boolean checkEmpty) {
        ids.remove(tab.getId());
        node.getTabs().remove(tab);
        if (checkEmpty) {
            handleEmpty();
        }
    }

    /**
     * Check if this area is empty, so remove it.
     */
    void handleEmpty() {
        if (node.getTabs().isEmpty()) {
            parent.remove(this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void add(Tab tab, int position) {
        switch (position) {
        case DockPosition.TOP:
        case DockPosition.BOTTOM:
        case DockPosition.LEFT:
        case DockPosition.RIGHT:
            super.add(tab, position);
            break;

        // case DockPosition.CENTER:
        // break;

        default:
            node.getTabs().add(position == DockPosition.CENTER ? node.getTabs().size() : position, tab);
            tab.setOnCloseRequest(e -> remove(tab));

            if (!ids.contains(tab.getId())) {
                ids.add(tab.getId());
            }
            break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Variable<ViewArea> findAreaBy(String id) {
        return Variable.of(ids.contains(id) ? this : null);
    }
}