/*
 * Copyright (C) 2022 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package viewtify.ui.anime;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import kiss.WiseRunnable;

public interface SwapAnime {

    /** Built-in swap animation. */
    SwapAnime FadeOutIn = (parent, before, after, action) -> {
        new AnimeDefinition(action) {

            @Override
            public void initialize() {
                after.setOpacity(0);
            }

            @Override
            public void before() {
                effect(before.opacityProperty(), 0);
            }

            @Override
            public void after() {
                effect(after.opacityProperty(), 1);
            }
        };
    };

    /** Built-in swap animation. */
    SwapAnime ZoomIn = (parent, before, after, action) -> {
        double scale = 0.15;
        int index = parent.getChildren().indexOf(before);

        new AnimeDefinition(action) {

            @Override
            public void initialize() {
                parent.getChildren().add(index, new StackPane(after, before));

                after.setOpacity(0);
                after.setScaleX(1 + scale);
                after.setScaleY(1 + scale);
            }

            @Override
            public void before() {
                effect(before.opacityProperty(), 0);
                effect(before.scaleXProperty(), 1 - scale);
                effect(before.scaleYProperty(), 1 - scale);

                effect(after.opacityProperty(), 1);
                effect(after.scaleXProperty(), 1);
                effect(after.scaleYProperty(), 1);
            }

            @Override
            public void cleanup() {
                parent.getChildren().set(index, after);
            }
        };
    };

    /** Built-in swap animation. */
    SwapAnime ZoomOut = (parent, before, after, action) -> {
        double scale = 0.15;
        int index = parent.getChildren().indexOf(before);

        new AnimeDefinition(action) {

            @Override
            public void initialize() {
                parent.getChildren().add(index, new StackPane(after, before));

                after.setOpacity(0);
                after.setScaleX(1 - scale);
                after.setScaleY(1 - scale);
            }

            @Override
            public void before() {
                effect(before.opacityProperty(), 0);
                effect(before.scaleXProperty(), 1 + scale);
                effect(before.scaleYProperty(), 1 + scale);

                effect(after.opacityProperty(), 1);
                effect(after.scaleXProperty(), 1);
                effect(after.scaleYProperty(), 1);
            }

            @Override
            public void cleanup() {
                parent.getChildren().set(index, after);
            }
        };
    };

    void run(Pane parent, Node before, Node after, WiseRunnable action);

    /**
     * Start animation.
     * 
     * @param animes
     * @param action
     */
    public static void play(SwapAnime[] animes, Pane parent, Node before, Node after, WiseRunnable action) {
        if (animes == null || animes.length == 0 || animes[0] == null) {
            action.run();
        } else {
            animes[0].run(parent, before, after, action);
        }
    }
}
