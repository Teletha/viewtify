/*
 * Copyright (C) 2023 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package viewtify.update;

import kiss.I;
import stylist.Style;
import stylist.StyleDSL;
import viewtify.Viewtify;
import viewtify.ui.UILabel;
import viewtify.ui.UIProgressBar;
import viewtify.ui.View;
import viewtify.ui.ViewDSL;

public class UpdaterView extends View {

    /** The all tasks. */
    private final UpdateTasks tasks = I.make(UpdateTasks.class).restore();

    UILabel message;

    UIProgressBar bar;

    class UI extends ViewDSL {
        {
            $(vbox, style.root, () -> {
                $(message);
                $(bar, style.bar);
            });
        }
    }

    interface style extends StyleDSL {

        Style root = () -> {
            padding.size(10, px);
        };

        Style bar = () -> {
            display.width.fill();
            margin.top(5, px);
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {
        message.text("Updating...");
        bar.value(0d);
    }

    /**
     */
    public void update(String archive) {
    }

    /**
     * Entry point for updater.
     * 
     * @param args
     */
    public static void main(String[] args) {
        Viewtify.application().title("Updater").size(400, 150).activate(UpdaterView.class);
    }
}
