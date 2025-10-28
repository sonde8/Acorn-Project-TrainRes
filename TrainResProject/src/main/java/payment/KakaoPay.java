package payment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class KakaoPay {

    private static final String HOST = "https://kapi.kakao.com";

    private static final String ADMIN_KEY = "KakaoAK 5f91f4c01cf24278d02a076fd7ddd21f";

    private String readyTid;

    public String getReadyTid() {
        return readyTid;
    }

    public String kakaoPayReady(OrderDTO order, String baseUrl) {
        try {
            Map<String, String> params = new LinkedHashMap<>();
            params.put("cid", "TC0ONETIME");
            params.put("partner_order_id", order.getPartner_order_id());
            params.put("partner_user_id",  order.getPartner_user_id());
            params.put("item_name",        order.getItem_name());
            params.put("quantity",         order.getQuantity());
            params.put("total_amount",     order.getTotal_amount());
            params.put("tax_free_amount",  "0");

            params.put("approval_url", baseUrl + "/kakaoPaySuccess");
            params.put("cancel_url",   baseUrl + "/kakaoPayCancel");
            params.put("fail_url",     baseUrl + "/kakaoPaySuccessFail");

            String json = postForm(HOST + "/v1/payment/ready", params);

            String tid  = pick(json, "\"tid\":\"", "\"");
            String next = pick(json, "\"next_redirect_pc_url\":\"", "\"");

            this.readyTid = tid;
            System.out.println("[KakaoPay][ready] tid=" + tid + ", next=" + next);

            return next;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String kakaoPayApprove(String pgToken, OrderDTO order) throws IOException {
        if (readyTid == null || readyTid.isEmpty()) {
            throw new IllegalStateException("readyTid가 없습니다. kakaoPayReady()를 먼저 호출해야 합니다.");
        }

        Map<String, String> params = new LinkedHashMap<>();
        params.put("cid", "TC0ONETIME");
        params.put("tid", readyTid);
        params.put("partner_order_id", order.getPartner_order_id());
        params.put("partner_user_id",  order.getPartner_user_id());
        params.put("pg_token", pgToken);
        params.put("total_amount", String.valueOf(order.getTotal_amount()));

        String json = postForm(HOST + "/v1/payment/approve", params);

        System.out.println("[KakaoPay][approve] " + json);

        return json;
    }

    private String postForm(String url, Map<String, String> form) throws IOException {
        byte[] body = buildForm(form).getBytes(StandardCharsets.UTF_8);

        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(15000);
        conn.setDoOutput(true);

        conn.setRequestProperty("Authorization", ADMIN_KEY);
        conn.setRequestProperty("Accept", "application/json;charset=UTF-8");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        conn.setRequestProperty("Content-Length", String.valueOf(body.length));

        try (OutputStream os = conn.getOutputStream()) {
            os.write(body);
        }

        int code = conn.getResponseCode();
        InputStream is = (code >= 200 && code < 300)
                ? conn.getInputStream()
                : conn.getErrorStream();

        String resp = readAll(is);
        conn.disconnect();

        if (code < 200 || code >= 300) {
            throw new IOException("Kakao API error (" + code + "): " + resp);
        }
        return resp;
    }

    private String buildForm(Map<String, String> form) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> e : form.entrySet()) {
            if (sb.length() > 0) sb.append('&');
            sb.append(URLEncoder.encode(e.getKey(), StandardCharsets.UTF_8))
              .append('=')
              .append(URLEncoder.encode(String.valueOf(e.getValue()), StandardCharsets.UTF_8));
        }
        return sb.toString();
    }

    private String readAll(InputStream is) throws IOException {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8))) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        }
    }

    private String pick(String json, String startToken, String endToken) {
        int s = json.indexOf(startToken);
        if (s < 0) return null;
        s += startToken.length();
        int e = json.indexOf(endToken, s);
        if (e < 0) return null;
        return json.substring(s, e).replace("\\/", "/");
    }
}
