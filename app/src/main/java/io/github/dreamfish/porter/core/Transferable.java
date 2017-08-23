package io.github.dreamfish.porter.core;

/**
 * Created by DreamFish on 2016/11/10.
 */

public interface Transferable {

    /**
     * @throws Exception
     */
    void init() throws Exception;

    /**
     * @throws Exception
     */
    void parseHeader() throws Exception;

    /**
     * @throws Exception
     */
    void parseBody() throws Exception;

    /**
     * @throws Exception
     */
    void finish() throws Exception;
}
