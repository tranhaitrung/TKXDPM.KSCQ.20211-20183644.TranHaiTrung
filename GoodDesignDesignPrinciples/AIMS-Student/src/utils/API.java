package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Class cung cấp các phương thức gửi request lên server và nhân dữ liệu trả về
 * Date: 
 * @author THT
 * @version 0.0.1
 */
public class API {

	public static DateFormat DATE_FORMATER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private static Logger LOGGER = Utils.getLogger(Utils.class.getName());

	/**
	 * Phương thức giúp gọi tới các API dạng GET
	 * @author Tran Hai Trung
	 * @param url: đường dẫn tới server cần request
	 * @param token: đoạn mã giúp xác thực người dùng
	 * @return response: phản hồi từ server dạng String
	 * @throws Exception
	 */
	public static String get(String url, String token) throws Exception {
		
		//Phần 1: setup
		HttpURLConnection conn = setupConnection(url, "GET", token);
		
		//Phần 2: đọc dữ liệu trả về từ server
		String response = readResponse(conn);
		return response;
	}

	int var;

	/**
	 * Phương thức giúp gọi các API dạng POST
	 * @author 
	 * @param url : đường dẫn tới server cần request
	 * @param data : dữ liệu gửi lên server xử lý dạng json
	 * @return response: phản hồi từ server dạng String
	 * @throws Exception 
	 */
	public static String post(String url, String data, String token) throws Exception {
		//Cho phép PATCH protocol
		allowMethods("PATCH");
		
		//Phần 1: setup
		HttpURLConnection conn = setupConnection(url, "PATCH", token);
		
		//Phần 2: gửi dữ liệu
		Writer writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		writer.write(data);
		writer.close();
		
		//Phần 3: Đọc dữ liệu trả về từ server
		String response = readResponse(conn);
		return response;
	}

	/**
	 * Phương thức cho phép gọi các loại giao thức API khác nhau (PATCH, PUT, GET,...) chỉ hoạt động với java11
	 * @deprecated chỉ hoạt động với java <= 11
	 * @param methods giao thức cần xử lý
	 */
	private static void allowMethods(String... methods) {
		try {
			Field methodsField = HttpURLConnection.class.getDeclaredField("methods");
			methodsField.setAccessible(true);

			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

			String[] oldMethods = (String[]) methodsField.get(null);
			Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
			methodsSet.addAll(Arrays.asList(methods));
			String[] newMethods = methodsSet.toArray(new String[0]);

			methodsField.set(null/* static field */, newMethods);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}
	
	/**
	 * Thiết lập connection tới server
	 * @param url: đường dẫn tới server cần request
	 * @param method: giao thức API
	 * @param token: đoạn mã cần cup cấp để xác thực người dùng
	 * @return connection
	 * @throws Exception
	 */
	private static HttpURLConnection setupConnection(String url, String method, String token) throws Exception {
		//Phần 1: setup
		LOGGER.info("Request URL: " + url + "\n");
		URL line_api_url = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) line_api_url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod(method);
		conn.setRequestProperty("Content-Type", "application/json");
		//conn.setRequestProperty("Authorization", "Bearer " + token);
		return conn;
	}
	
	/**
	 * Đọc dữ liệu trả về từ server
	 * @param conn: connection to server
	 * @return response: phản hồi trả về từ server
	 * @throws Exception
	 */
	private static String readResponse(HttpURLConnection conn) throws Exception {
		//Phần 2: đọc dữ liệu trả về từ server
		BufferedReader in;
		String inputLine;
		if (conn.getResponseCode() / 100 == 2) {
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder respone = new StringBuilder(); // ising StringBuilder for the sake of memory and performance
		while ((inputLine = in.readLine()) != null){
			respone.append(inputLine + "\n");
			System.out.println(inputLine);

		}
		in.close();
		LOGGER.info("Respone: " + respone.toString());
		LOGGER.info("Respone Info: " + respone.substring(0, respone.length() - 1).toString());
		return respone.substring(0, respone.length() - 1).toString();
	}

}
