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

import java.awt.Label;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javafx.collections.ObservableList;
import javafx.css.Styleable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import kiss.I;
import kiss.Tree;
import kiss.Variable;
import kiss.WiseRunnable;
import stylist.Style;
import transcript.Transcript;
import viewtify.Viewtify;
import viewtify.style.FormStyles;
import viewtify.ui.helper.StyleHelper;
import viewtify.util.TextNotation;

/**
 * Declared user interface.
 */
public class UI extends Tree<UserInterfaceProvider, UI.UINode> {

    /** The horizontal box. */
    protected static final UserInterfaceProvider hbox = new Box(HBox.class);

    /** The vertical box. */
    protected static final UserInterfaceProvider vbox = new Box(VBox.class);

    /** The stack box. */
    protected static final UserInterfaceProvider sbox = new Box(StackPane.class);

    private Style formLabelStyle = FormStyles.FormLabel;

    private Style formControlStyle = FormStyles.FormInput;

    /**
     * 
     */
    protected UI() {
        super(UI.UINode::new, null, (follower, current) -> {
            if (follower instanceof Style && current.node instanceof Styleable) {
                StyleHelper.of((Styleable) current.node).style((Style) follower);
            } else {
                follower.accept(current);
            }
        });
    }

    /**
     * Build node tree.
     * 
     * @return
     */
    final Node build() {
        return (Node) root.get(0).node;
    }

    /**
     * Declara the spcified {@link UserInterfaceProvider}.
     * 
     * @param provider UI provider.
     */
    protected final void $(Variable<? extends UserInterfaceProvider> provider) {
        Pane pane = new Pane();
        provider.observing().to(p -> {
            ObservableList<Node> children = pane.getChildren();

            if (p == null) {
                children.clear();
            } else {
                makeNode(p).to(node -> {
                    if (children.isEmpty()) {
                        children.add(node);
                    } else {
                        children.set(0, node);
                    }
                }, () -> {
                    children.clear();
                });
            }
        });

        $(() -> pane);
    }

    /**
     * Declara the spcified {@link UserInterfaceProvider}.
     * 
     * @param provider UI provider.
     */
    protected final void $(UserInterfaceProvider root, ObservableList<? extends UserInterfaceProvider> providers) {
        Pane pane = (Pane) root.ui();
        Viewtify.observing(providers).to(list -> {
            pane.getChildren().setAll(I.signal(providers).flatVariable(this::makeNode).toList());
        });

        $(() -> pane);
    }

    /**
     * Build node from {@link UserInterfaceProvider}.
     * 
     * @param provider
     * @return
     */
    private Variable<Node> makeNode(UserInterfaceProvider provider) {
        if (provider instanceof View) {
            View.build((View) provider, null);
        }
        Styleable ui = provider.ui();

        if (ui instanceof Node) {
            return Variable.of((Node) ui);
        } else {
            return Variable.empty();
        }
    }

    /**
     * Declare the specified {@link View}.
     * 
     * @param node A {@link View} to compose.
     */
    protected final void $(View view) {
        $(() -> View.build(view).ui());
    }

    /**
     * Declare the specified {@link Node}.
     * 
     * @param node A JavaFX {@link Node} to compose.
     */
    protected final void $(Node node) {
        $(() -> node);
    }

    /**
     * Declare the specified {@link Node}.
     * 
     * @param node A JavaFX {@link Node} to compose.
     */
    protected final void $(Node node, WiseRunnable writer) {
        $(() -> node, writer);
    }

    /**
     * Declare the specified {@link Node}.
     * 
     * @param node A JavaFX {@link Node} to compose.
     */
    protected final void $(Node node, Style style, WiseRunnable writer) {
        $(() -> node, style, writer);
    }

    /**
     * Declare the {@link Label}.
     * 
     * @param text A label text.
     * @param followers
     */
    protected final void label(Object text, Consumer<UINode>... followers) {
        $(() -> TextNotation.parse(String.valueOf(text)), followers);
    }

    /**
     * Declare the {@link Label}.
     * 
     * @param text A label text.
     * @param followers
     */
    protected final void label(Supplier<String> text, Consumer<UINode>... followers) {
        $(() -> TextNotation.parse(text), followers);
    }

    /**
     * Declare the {@link Label}.
     * 
     * @param text A label text.
     * @param followers
     */
    protected final void label(Transcript text, Consumer<UINode>... followers) {
        $(() -> TextNotation.parse(text), followers);
    }

    /**
     * Declare Form UI simply.
     * 
     * @param text
     * @param ui
     * @param followers
     */
    protected final void form(String text, UserInterface... uis) {
        form(() -> TextNotation.parse(text), uis);
    }

    /**
     * Declare Form UI simply.
     * 
     * @param text
     * @param ui
     * @param followers
     */
    protected final void form(Transcript text, UserInterface... uis) {
        form(() -> TextNotation.parse(text), uis);
    }

    /**
     * Declare Form UI simply.
     * 
     * @param text
     * @param ui
     * @param followers
     */
    protected final void form(UserInterfaceProvider text, UserInterface... uis) {
        $(hbox, FormStyles.FormRow, () -> {
            $(text, formLabelStyle);
            for (UserInterface ui : uis) {
                $(ui, ui instanceof UICheckBox ? FormStyles.FormCheck : FormStyles.FormInput);
            }
        });
    }

    protected final void titled(Object text, UserInterfaceProvider content) {
        TitledPane pane = new TitledPane();
        pane.graphicProperty().set(TextNotation.parse(String.valueOf(text)));
        makeNode(content).to(pane::setContent);

        $(pane);
    }

    /**
     * @version 2018/08/29 11:25:30
     */
    public static class UINode implements Consumer<UINode> {

        protected Object node;

        /**
         * @param name
         */
        private UINode(UserInterfaceProvider ui, int id, Object context) {
            this.node = ui.ui();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void accept(UINode context) {
            if (context.node instanceof TableView) {
                ((TableView) context.node).getColumns().add(node);
            } else if (context.node instanceof TreeTableView) {
                ((TreeTableView) context.node).getColumns().add(node);
            } else if (context.node instanceof Group) {
                ((Group) context.node).getChildren().add((Node) node);
            } else if (context.node instanceof Pane) {
                ((Pane) context.node).getChildren().add((Node) node);
            }
        }
    }

    /**
     * General box container.
     */
    private static final class Box<P extends Pane> implements UserInterfaceProvider<P> {

        /** The box type. */
        private final Class<P> type;

        /**
         * The typed box.
         * 
         * @param type A box type.
         */
        private Box(Class<P> type) {
            this.type = Objects.requireNonNull(type);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public P ui() {
            return I.make(type);
        }
    }

    private static final class Area extends Parent {

    }
}
