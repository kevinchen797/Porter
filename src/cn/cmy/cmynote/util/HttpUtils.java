package cn.cmy.cmynote.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 封装联网操作
 */
public class HttpUtils {
	
	/**
	 * 发送get请求 获取服务端返回的输入流
	 * @param url
	 * @return
	 * @throws IOExceptionException 
	 */
	public static InputStream get(String path) throws IOException{
		URL url = new URL(path);
		HttpURLConnection conn=(HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
//		conn.setRequestProperty("apikey",  "83b5616945de5ed04ab53e56bcd77ed2");
		conn.connect();
		InputStream is = conn.getInputStream();
		return is;
	}
	
	/**
	 * 发送post请求  
	 * @param path  请求资源路径
	 * @param paramMap 参数集合  如果没有参数 则为null
	 * @return  服务端返回的输入流
	 */
	public static InputStream post(String path, Map<String, String> paramMap) throws IOException {
		URL url = new URL(path);
		HttpURLConnection conn=(HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		//设置参数
		conn.setDoOutput(true);
		StringBuilder params = new StringBuilder();
		if(paramMap != null){
			//把paramMap集合中的参数都拼接到params中
			Set<String> keys=paramMap.keySet();
			Iterator<String> ite = keys.iterator();
			while(ite.hasNext()){
				String key = ite.next();
				String value = paramMap.get(key);
				//把key=value拼接到params字符串中
				params.append(key+"="+value+"&");
			}
			//key=value&key=value&key=value&
			params.deleteCharAt(params.length()-1);
			//把params作为参数输出给服务端
			OutputStream os = conn.getOutputStream();
			os.write(params.toString().getBytes("utf-8"));
			os.flush();
		}
		//获取输入流
		InputStream is = conn.getInputStream();
		return is;
	}
	
	/**
	 * 把输入流 按照utf-8编码解析为字符串
	 * @param is
	 * @return 解析成功的字符串
	 */
	public static String isToString(InputStream is)throws IOException{
		StringBuilder sb = new StringBuilder();
		String line = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		while((line = reader.readLine()) != null){
			sb.append(line);
		}
		return sb.toString();
	}
	
}
