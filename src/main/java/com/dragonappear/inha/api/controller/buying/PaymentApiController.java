package com.dragonappear.inha.api.controller.buying;


import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.service.auctionitem.AuctionItemService;
import com.dragonappear.inha.service.payment.PaymentService;
import com.dragonappear.inha.service.user.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import java.util.Map;
import java.util.HashMap;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

import static com.dragonappear.inha.domain.auctionitem.value.AuctionitemStatus.*;

@Api(tags = {"구매 결제 정보 저장 API"})
@RestController
@RequiredArgsConstructor
public class PaymentApiController {
    private final PaymentService paymentService;
    private final AuctionItemService auctionItemService;
    private final UserService userService;

    public static final String IMPORT_TOKEN_URL = "https://api.iamport.kr/users/getToken";
    public static final String IMPORT_CANCEL_URL = "https://api.iamport.kr/payments/cancel";
    public static final String KEY = "5132626560989602";
    public static final String SECRET = "76b263a820b4d8645755eb3f51f7afb500027545cbe027a24bdf8751e53ba34a9190b36a12508e46";

    @ApiOperation(value = "결제 정보 저장 API", notes = "결제 정보 저장하기")
    @PostMapping("/payments/new")
    public Result savePayment(@RequestBody PaymentDto dto) {
        try {
            Auctionitem auctionitem = auctionItemService.findById(dto.getAuctionitemId());
            User user = userService.findOneById(dto.getBuyerId());
            if(auctionitem.getAuctionitemStatus()!= 경매중){
                throw new IllegalStateException("해당 경매상품은 이미 판매되었습니다");
            }
            Payment payment = dto.toEntity(user, auctionitem);
            paymentService.save(payment);
        } catch (Exception e) {
            cancelPayment(CancelDto.builder()
                    .token(getImportToken())
                    .impId(dto.getImpId())
                    .merchantId(dto.getMerchantId())
                    .amount(dto.getPaymentPrice().toString())
                    .checksum(dto.getPaymentPrice().toString())
                    .build());
            return Result.builder()
                    .result(putResult("isPaySuccess", false, "Status", e.getMessage()))
                    .build();
        }

        return Result.builder()
                .result(putResult("isPaySuccess", true, "Status", "결제가 성공하였습니다."))
                .build();
    }

    @ApiOperation(value = "결제 취소 API", notes = "결제 취소하기")
    @GetMapping("/payments/cancel/{paymentId}")
    public Result cancelTest(@PathVariable("paymentId") Long paymentId) {
        try {
            Payment payment = paymentService.findById(paymentId);
            cancelPayment(CancelDto.builder()
                    .token(getImportToken())
                    .impId(payment.getImpId())
                    .merchantId(payment.getMerchantId())
                    .amount(payment.getPaymentPrice().getAmount().toString())
                    .checksum(payment.getPaymentPrice().getAmount().toString())
                    .build());
        } catch (Exception e) {
            return Result.builder()
                    .result(putResult("isCancelSuccess", false, "Status", e.getMessage()))
                    .build();
        }
        return Result.builder()
                .result(putResult("isCancelSuccess", true, "Status", "결제가 취소되었습니다"))
                .build();
    }

    /**
     * 결제취소 로직
     */
    // 아임포트 인증(토큰)을 받아주는 함수
    public String getImportToken() {
        String result = "";
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(IMPORT_TOKEN_URL);
        Map<String,String> m =new HashMap<String,String>();
        m.put("imp_key", KEY);
        m.put("imp_secret", SECRET);
        try {
            post.setEntity(new UrlEncodedFormEntity(convertParameter(m)));
            HttpResponse res = client.execute(post);
            ObjectMapper mapper = new ObjectMapper();
            String body = EntityUtils.toString(res.getEntity());
            JsonNode rootNode = mapper.readTree(body);
            JsonNode resNode = rootNode.get("response");
            result = resNode.get("access_token").asText();
        } catch (Exception e) {
            throw new IllegalStateException("아임포트 토큰을 받아올 수 없습니다."); }
        return result;
    }


    // 결제취소
    public void cancelPayment(CancelDto dto){
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(IMPORT_CANCEL_URL);
        Map<String, String> map = new HashMap<String, String>();
        post.setHeader("Authorization", dto.getToken());
        map.put("imp_uid", dto.getImpId());
        map.put("merchant_uid", dto.getMerchantId());
        map.put("amount", dto.getAmount());
        map.put("checksum", dto.getChecksum());
        String result = "";
        try {
            post.setEntity(new UrlEncodedFormEntity(convertParameter(map)));
            HttpResponse res = client.execute(post);
            ObjectMapper mapper = new ObjectMapper();
            String entry = EntityUtils.toString(res.getEntity());
            JsonNode rootNode = mapper.readTree(entry);
            result = rootNode.get("response").asText(); }
        catch (Exception e) { e.printStackTrace(); }
        if (result.equals("null")) {
            throw new IllegalStateException("결제 취소가 실패하였습니다.");
        }
    }

    //  Http요청 파라미터를 만들어 주는 메서드
    private List<NameValuePair> convertParameter(Map<String,String> paramMap){
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        Set<Map.Entry<String,String>> entries = paramMap.entrySet();
        for(Map.Entry<String,String> entry : entries) {
            paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        } return paramList;
    }

    /**
     * DTO
     */
    @NoArgsConstructor
    @Data
    static class PaymentDto {
        private String pgName;
        private String impId;
        private String merchantId;
        private BigDecimal paymentPrice;
        private Long buyerId;
        private Long auctionitemId;

        @Builder
        public PaymentDto(String pgName, String impId, String merchantId, BigDecimal paymentPrice, Long buyerId, Long auctionitemId) {
            this.pgName = pgName;
            this.impId = impId;
            this.merchantId = merchantId;
            this.paymentPrice = paymentPrice;
            this.buyerId = buyerId;
            this.auctionitemId = auctionitemId;
        }

        Payment toEntity(User user, Auctionitem auctionitem) {
            return Payment.builder()
                    .paymentPrice(new Money(this.getPaymentPrice()))
                    .user(user)
                    .auctionitem(auctionitem)
                    .impId(this.getImpId())
                    .merchantId(this.merchantId)
                    .pgName(this.pgName)
                    .build();
        }
    }


    @NoArgsConstructor
    @Data
    static class CancelDto {
        private String token;
        private String reason;
        private String impId;
        private String merchantId;
        private String amount;
        private String checksum;

        @Builder
        public CancelDto(String token, String reason, String impId, String merchantId, String amount, String checksum) {
            this.token = token;
            this.reason = reason;
            this.impId = impId;
            this.merchantId = merchantId;
            this.amount = amount;
            this.checksum = checksum;
        }
    }

    @NoArgsConstructor
    @Data
    static class Result {
        private Map<String, Object> result;
        @Builder
        public Result(Map<String, Object> result) {
            this.result = result;
        }
    }

    /**
     * 리턴메서드
     */
    public Map<String, Object> putResult(String insert, Boolean bool, String status, String content) {
        Map<String, Object> result = new HashMap<>();
        result.put(insert, bool);
        result.put(status, content);
        return result;
    }


}
