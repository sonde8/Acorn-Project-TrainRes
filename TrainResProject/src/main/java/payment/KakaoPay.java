package payment;
 
 

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 서블릿 환경에서 사용 가능한 카카오페이 서비스 클래스 (스프링 의존성 제거)
 * - ready: next_redirect_pc_url 반환
 * - approve: 승인 결과 JSON 반환
 */
public class KakaoPay {

    private static final String HOST = "https://kapi.kakao.com";
    // ★ 보안상 환경변수/설정파일로 분리 권장 (여기선 데모용)
    private static final String ADMIN_KEY = "KakaoAK 5f91f4c01cf24278d02a076fd7ddd21f";

    // ready 응답 보관용(TID 등)
    private String readyTid;

    /** 결제 준비(ready): PC 리다이렉트 URL 반환 */
    public String kakaoPayReady(OrderDTO order) {
        try {
            Map<String, String> params = new LinkedHashMap<>();
            params.put("cid", "TC0ONETIME");
            params.put("partner_order_id", order.getPartner_order_id());
            params.put("partner_user_id",  order.getPartner_user_id());
            params.put("item_name",        order.getItem_name());
            params.put("quantity",         String.valueOf(order.getQuantity()));
            params.put("total_amount",     String.valueOf(order.getTotal_amount()));
            params.put("tax_free_amount",  "0");

            // 콜백 URL은 서블릿에서 만들어 넘겨도 되고, 고정값을 써도 됩니다.
            params.put("approval_url", "http://localhost:8080/kakao/kakaoPaySuccess");
            params.put("cancel_url",   "http://localhost:8080/kakao/kakaoPayCancel");
            params.put("fail_url",     "http://localhost:8080/kakao/kakaoPaySuccessFail");

            String json = postForm(HOST + "/v1/payment/ready", params);

            // 최소 파싱 (라이브러리 없이 문자열로)
            String tid  = pick(json, "\"tid\":\"", "\"");
            String next = pick(json, "\"next_redirect_pc_url\":\"", "\"");

            this.readyTid = tid; // approve에서 사용
            System.out.println("[KakaoPay][ready] tid=" + tid + ", next=" + next);

            return next != null ? next : "/pay"; // 실패 대비
        } catch (Exception e) {
            e.printStackTrace();
            return "/pay";
        }
    }

    /**
     * 결제 승인(approve)
     * - 스프링 없이 쓰기 쉽도록 승인 결과 원본 JSON을 그대로 반환
     * - 기존 KakaoPayApprovalVO를 쓰고 싶다면, 여기서 JSON 파싱하여 VO에 매핑하세요.
     */
    public String kakaoPayInfo(String pg_token, OrderDTO order) {
        try {
            if (readyTid == null) {
                throw new IllegalStateException("ready TID가 없습니다. kakaoPayReady() 이후 호출하세요.");
            }

            Map<String, String> params = new LinkedHashMap<>();
            params.put("cid", "TC0ONETIME");
            params.put("tid", readyTid);
            params.put("partner_order_id", order.getPartner_order_id());
            params.put("partner_user_id",  order.getPartner_user_id());
            params.put("pg_token", pg_token);
            params.put("total_amount", String.valueOf(order.getTotal_amount()));

            String json = postForm(HOST + "/v1/payment/approve", params);
            System.out.println("[KakaoPay][approve] " + json);

            // ★ KakaoPayApprovalVO 를 유지하려면:
            // KakaoPayApprovalVO vo = new KakaoPayApprovalVO();
            // vo.setAid(pick(json, "\"aid\":\"", "\"")); ... 필요한 필드 매핑
            // return vo;
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return null; // 실패
        }
    }

    /* ====================== 공통 유틸 ====================== */

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
        InputStream is = (code >= 200 && code < 300) ? conn.getInputStream() : conn.getErrorStream();
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
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line; while ((line = br.readLine()) != null) sb.append(line);
            return sb.toString();
        }
    }

    // 아주 단순한 토큰 뽑기(라이브러리 없이)
    private String pick(String json, String startToken, String endToken) {
        int s = json.indexOf(startToken);
        if (s < 0) return null;
        s += startToken.length();
        int e = json.indexOf(endToken, s);
        if (e < 0) return null;
        return json.substring(s, e).replace("\\/", "/");
    }
}
