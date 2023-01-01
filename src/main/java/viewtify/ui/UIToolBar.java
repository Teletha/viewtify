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

import javafx.geometry.Orientation;
import javafx.scene.control.ToolBar;
import viewtify.ui.helper.ContextMenuHelper;

public class UIToolBar extends UserInterface<UIToolBar, ToolBar> implements ContextMenuHelper<UIToolBar> {

    /**
     * @param view
     */
    public UIToolBar(View view) {
        super(new ToolBar(), view);
    }

    public final UIToolBar set(Orientation direction) {
        ui.setOrientation(direction);
        return this;
    }

}