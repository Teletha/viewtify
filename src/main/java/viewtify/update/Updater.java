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

import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;

import kiss.I;
import kiss.Managed;
import kiss.Signal;
import kiss.Singleton;
import kiss.Variable;
import psychopath.Directory;
import psychopath.File;
import psychopath.Locator;

@Managed(Singleton.class)
public class Updater {

    /** The application home. */
    private final Directory home = Locator.directory("").absolutize();

    /** The current JVM. */
    private final Directory jvm = Locator.directory(System.getProperty("java.home"));

    /** The loaded libraries. */
    private final Set<File> libraries = detectLibraries("jdk.module.path").concat(detectLibraries("java.class.path")).toSet();

    /** The timestamp. */
    private final long lastModified = libraries.stream().mapToLong(File::lastModifiedMilli).max().orElse(0);

    /** The configurable option. */
    private LocalSite local;

    /** The configurable option. */
    private final Set<UpdateSite> updateSites = new LinkedHashSet();

    /**
     * Add update site.
     * 
     * @param site
     * @return
     */
    public Updater addUpdateSite(String site) {
        if (site != null && !site.isBlank()) {
            if (site.startsWith("http")) {

            } else {
                addUpdateSite(Locator.directory(site));
            }
        }
        return this;
    }

    /**
     * @param site
     */
    public Updater addUpdateSite(Directory site) {
        if (site != null) {
            updateSites.add(new LocalSite(site));
        }
        return this;
    }

    /**
     * @param site
     */
    public Updater addUpdateSite(Path site) {
        if (site != null) {
            addUpdateSite(Locator.directory(site));
        }
        return this;
    }

    /**
     * Configure the root directory.
     * 
     * @return
     */
    public Updater setRoot(Directory directory) {
        if (directory != null) {
            this.local = new LocalSite(directory);
        }
        return this;
    }

    /**
     * Configure the root directory.
     * 
     * @return
     */
    public Updater setRoot(Path directory) {
        if (directory != null) {
            setRoot(Locator.directory(directory));
        }
        return this;
    }

    /**
     * Check whether this updater is updatable or not.
     * 
     * @return
     */
    public UpdateResult canUpdate() {
        if (local == null) {
            return new UpdateResult(false, "The directory to be updated is not registered.");
        }

        Variable<UpdateSite> site = selectUpdateSite();

        if (site.isAbsent()) {
            return new UpdateResult(false, "The update site is not registered.");
        } else {
            return new UpdateResult(true, "");
        }
    }

    /**
     * @return
     */
    private Variable<UpdateSite> selectUpdateSite() {
        return I.signal(updateSites).take(site -> local.lastModified() < site.lastModified()).first().to();
    }

    public void detectEnvironment() {
        System.out.println(home);
        System.out.println(jvm);
        System.out.println(libraries);
    }

    public UpdateResult updateByZip(String path) {
        File zip = Locator.file(path).absolutize();

        if (zip.isAbsent()) {
            return new UpdateResult(false, "Archive file [" + zip + "] is not found.");
        }

        if (zip.lastModifiedMilli() <= lastModified) {
            return new UpdateResult(false, "The latest version is used.");
        }

        return null;
    }

    /**
     * Detect loaded libraries.
     * 
     * @param key
     * @return
     */
    private Signal<File> detectLibraries(String key) {
        return I.signal(System.getProperty(key))
                .skipNull()
                .flatArray(value -> value.split(java.io.File.pathSeparator))
                .take(path -> path.endsWith(".jar"))
                .map(path -> Locator.file(path));
    }
}
