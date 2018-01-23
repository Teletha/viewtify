/*
 * Copyright (C) 2017 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package viewtify.ui;

import javafx.scene.control.Button;

import viewtify.View;
import viewtify.ui.helper.LabelHelper;

/**
 * @version 2017/11/15 9:54:15
 */
public class UIButton extends UserInterface<UIButton, Button> implements LabelHelper<UIButton, Button> {

    /**
     * Enchanced view.
     * 
     * @param ui
     */
    private UIButton(Button ui, View view) {
        super(ui, view);
    }
}
