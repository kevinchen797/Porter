package cn.cmy.cmynote.util;

import java.text.SimpleDateFormat;

public class DateUtils {

	/** 日期格式yyyy-MM-dd字符串常量 */
	private static final String DATE_FORMAT = "yyyy-MM-dd";

	private static SimpleDateFormat sdf_date_format = new SimpleDateFormat(DATE_FORMAT);

	public static String getDateTime(long time) {  
          
            return sdf_date_format.format(time);  
	}
}
