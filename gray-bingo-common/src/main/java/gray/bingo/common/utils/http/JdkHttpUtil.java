package gray.bingo.common.utils.http;

import gray.bingo.common.exceptions.BingoException;
import gray.bingo.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Objects;

/**
 * JDK HttpURLConnection发送请求工具类
 *
 * @author pengjunlee
 */
@Slf4j
public class JdkHttpUtil {

    public static String doGet(String urlStr) throws Exception {
        return sendRequest(urlStr, RequestMethod.GET.name(), null, null);
    }

    public static String doGet(String urlStr, Map<String, String> headers) throws Exception {
        return sendRequest(urlStr, RequestMethod.GET.name(), null, headers);
    }

    public static <T> T doGet(String urlStr, Class<T> c) throws Exception {
        String string = sendRequest(urlStr, RequestMethod.GET.name(), null, null);
        return JsonUtil.toObj(string, c);
    }

    public static <T> T doGet(String urlStr, Map<String, String> headers, Class<T> c) throws Exception {
        String string = sendRequest(urlStr, RequestMethod.GET.name(), null, headers);
        return JsonUtil.toObj(string, c);
    }

    public static String doPost(String urlStr, String data) throws Exception {
        return sendRequest(urlStr, RequestMethod.POST.name(), data, null);
    }


    public static String doPost(String urlStr, String data, Map<String, String> headers) throws Exception {
        return sendRequest(urlStr, RequestMethod.POST.name(), data, headers);
    }

    public static <T> T doPost(String urlStr, String data, Class<T> c) throws Exception {
        String string = sendRequest(urlStr, RequestMethod.POST.name(), data, null);
        return JsonUtil.toObj(string, c);
    }

    public static <T> T doPost(String urlStr, String data, Map<String, String> headers, Class<T> c) throws Exception {
        String string = sendRequest(urlStr, RequestMethod.POST.name(), data, headers);
        return JsonUtil.toObj(string, c);
    }

    public static String sendRequest(String urlStr, String method, String data, Map<String, String> headers) throws Exception {
        HttpURLConnection connection = getURLConnection(urlStr, method, data, headers);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();
        } else {
            throw new BingoException("HTTP request failed with response code: " + responseCode);
        }
    }

    private static HttpURLConnection getURLConnection(String urlStr, String method, String data, Map<String, String> headers) throws IOException {
        if (null == urlStr || urlStr.isEmpty()) {
            throw new BingoException("Target Url is null!");
        }
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod(method);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        if (Objects.isNull(headers) || headers.isEmpty()) {
            connection.setRequestProperty("Content-Type", "application/json");
        } else {
            headers.forEach(connection::setRequestProperty);
        }


        if (data != null) {
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(data);
            outputStream.flush();
            outputStream.close();
        }
        return connection;
    }

    public static void main(String[] args) {
        // String url = "https://api.btstu.cn/sjbz/api.php?lx=dongman&format=json";
        String url = "https://api.7585.net.cn/wyy/api.php?type=1&id=1357374736&return=json";
        String method = RequestMethod.GET.name();
        String data = "{}";

        try {
            String response = doGet(url);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}