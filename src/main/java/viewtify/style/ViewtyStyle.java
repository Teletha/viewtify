/*
 * Copyright (C) 2023 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package viewtify.style;

import stylist.StyleDSL;

/**
 * Built-in CSS.
 */
public interface ViewtyStyle extends StyleDSL {

    /**
     * Shorthand helper to make font anti-aliased.
     */
    static void useAntiAliasedFont() {
        $.descendant(".text", () -> {
            font.smooth.grayscale();
        });
    }
}