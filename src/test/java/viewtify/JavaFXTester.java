/*
 * Copyright (C) 2024 The VIEWTIFY Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package viewtify;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;

import org.junit.jupiter.api.BeforeAll;

public abstract class JavaFXTester {

    static {
        Viewtify.inHeadless();
        Viewtify.checkHeadlessMode();
    }

    private static boolean started = false;

    @BeforeAll
    static synchronized void initialize() {
        if (started == false) {
            started = true;

            Platform.startup(() -> {
            });
        }
    }

    /**
     * Helper method to retrieve {@link Hyperlink}.
     * 
     * @param node
     * @return
     */
    protected final <T extends Node> T as(Node node, Class<T> type) {
        assert type.isInstance(node);

        return (T) node;
    }
}