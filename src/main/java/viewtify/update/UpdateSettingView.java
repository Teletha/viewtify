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

import kiss.Variable;
import viewtify.Viewtify;
import viewtify.ui.UIButton;
import viewtify.ui.View;
import viewtify.ui.ViewDSL;

public class UpdateSettingView extends View {

    private UIButton confirm;

    /**
     * {@inheritDoc}
     */
    @Override
    protected ViewDSL declareUI() {
        return new ViewDSL() {
            {
                $(vbox, () -> {
                    form(en("Confirn update"), confirm);
                });
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Variable<String> title() {
        return en("Update");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {
        confirm.text(en("Confirm")).action(() -> {
            Update.apply(Viewtify.application().updateSite(), false);
        });
    }
}
