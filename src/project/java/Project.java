/*
 * Copyright (C) 2019 viewtify Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
public class Project extends bee.api.Project {

    {
        product("com.github.teletha", "viewtify", "1.0");

        require("com.github.teletha", "sinobu");
        require("com.github.teletha", "psychopath");
        require("com.github.teletha", "stylist");
        require("com.github.teletha", "transcript");
        require("com.github.teletha", "antibug").atTest();
        require("net.bytebuddy", "byte-buddy");
        require("net.bytebuddy", "byte-buddy-agent");
        require("org.openjfx", "javafx-controls");
        require("org.openjfx", "javafx-media");
        require("org.openjfx", "javafx-web");
        require("org.controlsfx", "controlsfx", "9.0.0");

        versionControlSystem("https://github.com/teletha/viewtify");
    }
}
