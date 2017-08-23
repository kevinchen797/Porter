package io.github.dreamfish.porter.core.utils;

/**
 * Created by DreamFish on 2016/12/5.
 */
public class TextUtils {
    /**
     * 判断文本是否Null或者是空白
     * @param str
     * @return
     */
    public static boolean isNullOrBlank(String str){
        return str == null || str.equals("");
    }
}
