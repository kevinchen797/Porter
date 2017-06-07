package io.github.dreamfish.kuaichuan.utils;

import android.net.wifi.ScanResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DreamFish on 2016/11/28.
 */
public class ListUtils {
    public static final String NO_PASSWORD = "[ESS]";
    public static final String NO_PASSWORD_WPS = "[WPS][ESS]";

    /**
     * 过滤有密码的Wifi扫描结果集合
     * @param scanResultList
     * @return
     */
    public static List<ScanResult> filterWithNoPassword(List<ScanResult> scanResultList){
        if(scanResultList == null || scanResultList.size() == 0){
            return scanResultList;
        }

        List<ScanResult> resultList = new ArrayList<>();
        for(ScanResult scanResult : scanResultList){
            if(scanResult.capabilities != null && scanResult.capabilities.equals(NO_PASSWORD) || scanResult.capabilities != null && scanResult.capabilities.equals(NO_PASSWORD_WPS)){
                resultList.add(scanResult);
            }
        }

        return resultList;
    }
}
