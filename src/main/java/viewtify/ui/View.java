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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.function.Predicate;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.css.Styleable;
import javafx.scene.Node;
import javafx.scene.control.TableColumnBase;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

import kiss.Disposable;
import kiss.Extensible;
import kiss.I;
import kiss.Model;
import kiss.Signal;
import kiss.Variable;
import viewtify.Viewtify;

public abstract class View implements Extensible, UserInterfaceProvider<Node>, Disposable {

    /** The human-readable ID separator. */
    public static final String IDSeparator = " ➝ ";

    /** The associated root node. */
    private Node root;

    /** The parent view. */
    private View parent;

    /**
     * Use class name as view name.
     */
    protected View() {
    }

    /**
     * Declare user interface.
     * 
     * @return
     */
    protected ViewDSL declareUI() {
        // auto detect UI definition
        for (Class<?> member : getClass().getDeclaredClasses()) {
            if (ViewDSL.class.isAssignableFrom(member)) {
                if (Modifier.isStatic(member.getModifiers())) {
                    return I.make((Class<ViewDSL>) member);
                } else {
                    for (Constructor constructor : member.getDeclaredConstructors()) {
                        Class[] paramTypes = constructor.getParameterTypes();

                        if (paramTypes.length == 1 && paramTypes[0] == getClass()) {
                            try {
                                constructor.setAccessible(true);
                                return (ViewDSL) constructor.newInstance(this);
                            } catch (Exception e) {
                                throw I.quiet(e);
                            }
                        }
                    }
                }
            }
        }
        throw I.quiet(new ClassNotFoundException(getClass() + " don't have UI definition. Define member class which is subclassed by " + ViewDSL.class
                .getName() + "."));
    }

    /**
     * Initialize {@link View}.
     */
    protected abstract void initialize();

    /**
     * Compute human-readable name for this view. Default is simple class name.
     * 
     * @return
     */
    protected String name() {
        return getClass().getSimpleName();
    }

    /**
     * Compute computer-awarable identifier for this view.
     * 
     * @return
     */
    public String id() {
        return parent == null ? name() : parent.name() + IDSeparator + name();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Node ui() {
        initializeLazy(parent);

        if (root != null) {
            return root;
        }

        if (parent != null) {
            return parent.ui();
        }
        throw new Error();
    }

    /**
     * Returns the root {@link View}.
     * 
     * @return The root view.
     */
    public final View findRootView() {
        return findAncestorView(view -> view.parent == null).v;
    }

    /**
     * Returns the nearest ancestor typed {@link View}.
     * 
     * @return The typed {@link View}.
     */
    public final <V extends View> Variable<V> findAncestorView(Class<V> viewType) {
        return (Variable<V>) findAncestorView(view -> viewType.isInstance(view));
    }

    /**
     * Finds the nearest ancestor view that meets the specified criteria.
     * 
     * @param condition A condition to match.
     * @return A found {@link View}.
     */
    public final Variable<View> findAncestorView(Predicate<View> condition) {
        View view = this;

        while (view != null) {
            if (condition.test(view)) {
                return Variable.of(view);
            }
            view = view.parent;
        }
        return Variable.empty();
    }

    /**
     * Finds all declared UI by the specified type.
     * 
     * @param <T>
     * @param type
     * @return
     */
    public final <T> Signal<T> findUI(Class<T> type) {
        return I.signal(getClass().getDeclaredFields()).take(f -> type.isAssignableFrom(f.getType())).map(f -> {
            f.setAccessible(true);
            return (T) f.get(this);
        });
    }

    /**
     * Returns the {@link Screen} which this {@link View} is displayed.
     * 
     * @return The {@link Screen} which this {@link View} is displayed.
     */
    public final Screen screen() {
        Window window = findRootView().ui().getScene().getWindow();

        for (Screen screen : Screen.getScreens()) {
            if (screen.getBounds().contains(window.getX(), window.getY())) {
                return screen;
            }
        }
        return Screen.getPrimary();
    }

    /**
     * Find {@link Stage} of this {@link View}.
     * 
     * @return
     */
    public final Variable<Stage> stage() {
        Window window = ui().getScene().getWindow();

        if (window instanceof Stage) {
            return Variable.of((Stage) window);
        } else {
            return Variable.empty();
        }
    }

    /**
     * Force to show the current application window which this {@link View} is displayed.
     */
    public final void show() {
        I.signal(stage()).skipNull().skip(Stage::isAlwaysOnTop).on(Viewtify.UIThread).to(e -> {
            if (e.isIconified()) {
                e.setIconified(false);
            }
            e.setAlwaysOnTop(true);
            e.setAlwaysOnTop(false);
        });
    }

    /**
     * Force to blink the current application window which this {@link View} is displayed.
     */
    public final void blink() {
        I.signal(stage()).skipNull().on(Viewtify.UIThread).to(Stage::toFront);
    }

    /**
     * Config view visibility.
     * 
     * @param visibility
     */
    public final void visible(boolean visibility) {
        ui().setVisible(visibility);
        ui().setManaged(visibility);
    }

    /** The initialization state. */
    private boolean initialized;

    /**
     * Initialize myself.
     */
    synchronized void initializeLazy(View parent) {
        if (initialized == false) {
            initialized = true;
            this.parent = parent;

            // initialize user system lazily
            try {
                buildUI();

                this.root = declareUI().ui();
            } catch (Exception e) {
                e.printStackTrace();
                throw I.quiet(e);
            }
        }
    }

    private void buildUI() {
        try {
            // Inject various types
            for (Field field : getClass().getDeclaredFields()) {
                Class<?> type = field.getType();
                field.setAccessible(true);

                if (Modifier.isAbstract(type.getModifiers())) {
                    continue;
                }

                Object assigned = field.get(this);

                if (View.class.isAssignableFrom(type)) {
                    if (assigned != null) {
                        ((View) assigned).initializeLazy(this);
                    } else {
                        Class<View> viewType = (Class<View>) type;
                        field.set(this, findAncestorView(viewType).or(() -> {
                            View sub = I.make(viewType);
                            sub.initializeLazy(this);
                            return sub;
                        }));
                    }
                } else if (Node.class.isAssignableFrom(type)) {
                    if (assigned == null) {
                        Constructor constructor = Model.collectConstructors(type)[0];
                        constructor.setAccessible(true);

                        Node node = (Node) constructor.newInstance(this);

                        assignId(node, field.getName());
                        field.set(this, node);
                    }
                } else if (UserInterfaceProvider.class.isAssignableFrom(type)) {
                    if (assigned == null) {
                        Constructor constructor = Model.collectConstructors(type)[0];
                        constructor.setAccessible(true);

                        Parameter[] params = constructor.getParameters();
                        UserInterfaceProvider provider = null;

                        if (params.length == 1) {
                            provider = (UserInterfaceProvider) constructor.newInstance(this);
                        } else if (params.length == 2 && params[1].getType() == Class.class) {
                            provider = (UserInterfaceProvider) constructor
                                    .newInstance(this, Model.collectParameters(field.getGenericType(), field.getType())[0]);
                        } else if (params.length == 3 && params[1].getType() == Class.class && params[2].getType() == Class.class) {
                            Type[] types = Model.collectParameters(field.getGenericType(), field.getType());
                            provider = (UserInterfaceProvider) constructor.newInstance(this, types[0], types[1]);
                        } else {
                            throw new UnsupportedOperationException("Unknown constructor type. [" + constructor + "]");
                        }

                        assignId(provider.ui(), field.getName());
                        field.set(this, provider);
                    }
                }
            }

            Platform.runLater(this::initialize);
        } catch (Exception e) {
            throw I.quiet(e);
        }
    }

    /**
     * Assign id to {@link Styleable}.
     * 
     * @param ui
     * @param id
     */
    private void assignId(Object ui, String id) {
        if (ui instanceof Node) {
            ((Node) ui).setId(id);
        } else if (ui instanceof TableColumnBase) {
            ((TableColumnBase) ui).setId(id);
        }
    }

    /**
     * Create the localized text. (base lang is english)
     * 
     * @param text Your english text.
     * @return Localized text.
     */
    protected final Variable<String> en(String text, Object... context) {
        return I.translate(this, text, context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void vandalize() {
        findUI(Disposable.class).to(Disposable::dispose);

        if (root instanceof Pane pane) {
            deconstruct(pane);
        }
        root = null;
    }

    /**
     * Deconstruct UI.
     * 
     * @param pane
     */
    private void deconstruct(Pane pane) {
        ObservableList<Node> children = pane.getChildren();
        for (Node child : children) {
            if (child instanceof Pane childPane) {
                deconstruct(childPane);
            }
        }
        children.clear();
    }
}