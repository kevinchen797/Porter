package io.github.dreamfish.porter.micro_server;

/**
 * Created by DreamFish on 2016/12/14.
 */
public interface ResUriHandler {

    /**
     * is matches the specify uri
     * @param uri
     * @return
     */
    boolean matches(String uri);


    /**
     * handler the request which matches the uri
     * @param request
     */
    void handler(Request request);

    /**
     * releas some resource when finish the handler
     */
    void destroy();
}
