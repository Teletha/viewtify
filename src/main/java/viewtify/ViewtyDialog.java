/*
 * Copyright (C) 2023 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package viewtify;

import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;

import kiss.Disposable;
import kiss.I;
import kiss.Variable;
import kiss.WiseConsumer;
import viewtify.ui.UIButton;
import viewtify.ui.View;

/**
 * The specialized dialog builder.
 */
public final class ViewtyDialog<T> {

    /** The actual dialog. */
    private final Dialog<ButtonType> dialog;

    /** The actual dialog. */
    private final DialogPane dialogPane;

    /** The actual stage. */
    private final Stage dialogStage;

    /** The diposer. */
    private final Disposable disposer = Disposable.empty();

    /**
     * Hide constructor.
     */
    ViewtyDialog(Stage stage) {
        dialog = new Dialog();
        dialog.initOwner(stage);
        dialogPane = dialog.getDialogPane();
        dialogStage = (Stage) dialogPane.getScene().getWindow();
    }

    /**
     * Configure title of this dialog.
     * 
     * @param title A title.
     * @return Chainable API.
     */
    public ViewtyDialog<T> title(String title) {
        dialog.setTitle(title);
        return this;
    }

    /**
     * Configure title of this dialog.
     * 
     * @param title A title.
     * @return Chainable API.
     */
    public ViewtyDialog<T> title(Variable<String> title) {
        title.observing().to(this::title, disposer);
        return this;
    }

    /**
     * Configure the button set of this dialog.
     * 
     * @param buttons
     * @return
     */
    public ViewtyDialog<T> button(ButtonType... buttons) {
        ObservableList<ButtonType> list = dialogPane.getButtonTypes();
        list.addAll(buttons);
        return this;
    }

    /**
     * Configure the button set of this dialog.
     * 
     * @param buttonOK
     * @param buttonOthers
     * @return
     */
    public ViewtyDialog<T> button(String buttonOK, String... buttonOthers) {
        I.signal(buttonOthers)
                .map(x -> new ButtonType(x, ButtonData.CANCEL_CLOSE))
                .startWith(new ButtonType(buttonOK, ButtonData.OK_DONE))
                .toCollection(dialogPane.getButtonTypes());

        return this;
    }

    /**
     * Configure automatic translation of this dialog.
     * 
     * @return
     */
    public ViewtyDialog<T> translateButtons() {
        for (ButtonType type : dialogPane.getButtonTypes()) {
            Button button = (Button) dialogPane.lookupButton(type);
            I.translate(type.getText()).observing().on(Viewtify.UIThread).to(button::setText, disposer);
        }
        return this;
    }

    /**
     * Configure the button order.
     * 
     * @return
     */
    public ViewtyDialog<T> disableSystemButtonOrder() {
        ButtonBar buttonBar = (ButtonBar) dialogPane.lookup(".button-bar");
        buttonBar.setButtonOrder(ButtonBar.BUTTON_ORDER_NONE);
        return this;
    }

    /**
     * Show and wait dialog.
     * 
     * @param <V>
     * @param view
     * @return
     */
    public <V, D extends DialogView<V>> Variable<V> show(Class<D> view) {
        return show(view, null);
    }

    /**
     * Show and wait dialog.
     * 
     * @param <V>
     * @param view
     * @return
     */
    public <V, D extends DialogView<V>> Variable<V> show(D view) {
        return show(view, null);
    }

    /**
     * Show and wait dialog.
     * 
     * @param <V>
     * @param view
     * @return
     */
    public <V, D extends DialogView<V>> Variable<V> show(Class<D> view, WiseConsumer<D> initializer) {
        return show(I.make(view), initializer);
    }

    /**
     * Show and wait dialog.
     * 
     * @param <V>
     * @param view
     * @return
     */
    public <V, D extends DialogView<V>> Variable<V> show(D view, WiseConsumer<D> initializer) {
        if (view != null) {
            view.injectButtons(dialogPane);

            Node ui = view.ui();
            if (initializer != null) {
                initializer.accept(view);
            }

            dialogPane.setContent(ui);
        }

        dialog.setOnCloseRequest(e -> {
            System.out.println("CLOSE");
        });
        dialogStage.setOnCloseRequest(e -> {
            System.out.println("CLOSE WINDOW");
        });

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isEmpty() || view == null) {
            return Variable.empty();
        }

        ButtonData data = result.get().getButtonData();

        if (data == ButtonData.OK_DONE) {
            return Variable.of(view.value);
        } else {
            return Variable.empty();
        }
    }

    /**
     * Specialized view for dialog.
     */
    public static abstract class DialogView<V> extends View {

        /** The value holder. */
        public V value;

        /** The button for OK. */
        public UIButton buttonOK;

        /**
         * Inject dialog's buttons.
         * 
         * @param pane The actual dialog pane.
         */
        void injectButtons(DialogPane pane) {
            I.signal(pane.getButtonTypes())
                    .take(x -> x.getButtonData() == ButtonData.OK_DONE)
                    .map(x -> pane.lookupButton(x))
                    .as(Button.class)
                    .first()
                    .to(x -> buttonOK = new UIButton(x, this));
        }
    }
}