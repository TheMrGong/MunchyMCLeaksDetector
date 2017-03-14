package me.gong.hiddenbot.util;

import java.io.IOException;

/**
 * An object that saves data to file
 */
public interface ISavable {

    /**
     * Loads data from specified file
     *
     * @throws IOException If loading from file was unsuccessful
     */
    void load() throws IOException;

    /**
     * Saves data to specified file
     *
     * @throws IOException If saving to file was unsuccessful
     */
    void save() throws IOException;

    /**
     * Sets whether or not the cache'd data is dirty or not
     *
     * @param dirty Is the data dirty
     */
    void setDirty(boolean dirty);

    /**
     * Returns if the data is dirty; the data in RAM doesn't match the data in disk
     *
     * @return Is the data currently dirty
     */
    boolean isDirty();


}
