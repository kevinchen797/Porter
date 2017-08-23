package io.github.dreamfish.porter.utils;

import java.io.IOException;

/**
 * Created by DreamFish on 2016/12/1.
 */
public class NetUtils {

    /**
     * 判断指定的ipAddress是否可以ping
     * @param ipAddress
     * @return
     */
    public static boolean pingIpAddress(String ipAddress) {
        try {
            Process process = Runtime.getRuntime().exec("/system/bin/ping -c 1 -w 100 " + ipAddress);
            int status = process.waitFor();
            if (status == 0) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
