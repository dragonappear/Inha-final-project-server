package com.dragonappear.inha.api.controller.buying;


import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.auctionitem.value.AuctionitemStatus;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserPoint;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.service.auctionitem.AuctionItemService;
import com.dragonappear.inha.service.payment.PaymentService;
import com.dragonappear.inha.service.user.UserPointService;
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
    private final UserPointService userPointService;

    public static final String IMPORT_TOKEN_URL = "https://api.iamport.kr/users/getToken";
    public static final String IMPORT_CANCEL_URL = "https://api.iamport.kr/payments/cancel";
    public static final String KEY = "5132626560989602";
    public static final String SECRET = "76b263a820b4d8645755eb3f51f7afb500027545cbe027a24bdf8751e53ba34a9190b36a12508e46";



    @ApiOperation(value = "결제 정보 저장 API", notes = "결제 정보 저장")
    @PostMapping("/payments/new")
    public Result savePayment(@RequestBody PaymentDto dto) {
        String validate = validate(dto);
        if (validate != null) {
            Result.builder()
                    .result(putResult("isPaySuccess", false, "Status", validate))
                    .build();
        }
        Auctionitem auctionitem = auctionItemService.findById(dto.getAuctionitemId());
        User user = userService.findOneById(dto.getBuyerId());
        // 결제 생성
        Payment payment = dto.toEntity(user, auctionitem,new Money(dto.getPoint()));
        paymentService.save(payment);
        //포인트 차감
        userPointService.subtract(user.getId(), new Money(dto.getPoint()));

        return Result.builder()
                .result(putResult("isPaySuccess", true, "Status", "결제가 완료하였습니다."))
                .build();
    }

    @ApiOperation(value = "결제 취소 API", notes = "결제 취소")
    @GetMapping("/payments/cancel/{paymentId}")
    public Result cancelPayment(@PathVariable("paymentId") Long paymentId) {
        try {
            Payment payment = paymentService.findById(paymentId);
            userPointService.accumulate(payment.getUser().getId(), new Money(payment.getPoint().getAmount())); // 유저 포인트 복구
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
     *  검증로직
     */
    // dto 검증
    private String validate(PaymentDto dto) {
        if (validateAuctionitemAndUser(dto)) {
            if (validateCancel(dto)){
                return  "유저 혹은 해당 상품이 존재하지 않아서, 결제취소를 시도하였으나 실패하였습니다.";
            }
            return "유저 혹은 해당 상품이 존재하지 않아서 결제를 취소하였습니다.";
        }
        Auctionitem auctionitem = auctionItemService.findById(dto.getAuctionitemId());
        User user = userService.findOneById(dto.getBuyerId());

        // 사용 포인트 검증
        if (validatePoint(dto, user)) {
            if (validateCancel(dto)){
                return "포인트 사용이 올바르지 않아, 결제취소를 시도하였으나 실패하였습니다.";
            }
            return "포인트 사용이 올바르지 않아서 결제를 취소하였습니다.";
        }
        // 경매아이템 결제 금액 검증
        if (validatePrice(dto, auctionitem)) {
            if (validateCancel(dto)){
                return "결제금액이 올바르지 않아서, 결제취소를 시도하였으나 실패하였습니다.";
            }
            return "결제 금액이 올바르지 않이서 결제를 취소하였습니다.";
        }

        // 경매상품 상태 검증
        if (validateAuctionitemStatus(auctionitem)) {
            if (validateCancel(dto)) {
                return "상품이 이미 판매되어서, 결제취소를 시도하였으나 실패하였습니다.";
            }
            return "상품이 이미 판매되어서, 결제취소를 하였습니다.";
        }
        return null;
    }

    // 결제 취소 검증
    private boolean validateCancel(PaymentDto dto) {
        try {
            cancelPayment(CancelDto.builder()
                    .token(getImportToken())
                    .impId(dto.getImpId())
                    .merchantId(dto.getMerchantId())
                    .amount(dto.getPaymentPrice().toString())
                    .checksum(dto.getPaymentPrice().toString())
                    .build());
        } catch (Exception e2) {
            return true;
        }
        return false;
    }

    // 경매아이템, 유저 검증
    private boolean validateAuctionitemAndUser(PaymentDto dto) {
        try {
            auctionItemService.findById(dto.getAuctionitemId());
            userService.findOneById(dto.getBuyerId());
        } catch (Exception e) {
            return true;
        }
        return false;
    }
    // 경매아이템 상태 검증
    public boolean validateAuctionitemStatus(Auctionitem auctionitem) {
        if(auctionitem.getAuctionitemStatus()!= 경매중){
            return true;
        }
        return false;
    }

    // 포인트 검증
    private boolean validatePoint(PaymentDto dto, User user) {
        try {
            Money amount = new Money(dto.getPoint());
            UserPoint point = userPointService.findLatestPoint(user.getId());
            if (amount.isLessThan(Money.wons(0L))) {
                throw new IllegalStateException("잘못된 포인트 입력입니다.");
            }
            if(point.getTotal().isLessThan(amount) ){
                throw new IllegalStateException("잘못된 포인트 차감입니다.");
            }
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    // 경매아이템 금액 검증
    private boolean validatePrice(PaymentDto dto,Auctionitem auctionitem) {
        try {
            Money price = auctionitem.getPrice();
            Money validate = new Money(dto.getPaymentPrice()).plus(new Money(dto.getPoint()));
            if (!price.equals(validate)) {
                return true;
            }
        } catch (Exception e) {
            return true;
        }
        return false;
    }


    /**
     * 결제취소 서비스 코드
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
    public void cancelPayment(CancelDto dto) throws Exception{
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
        catch (Exception e) {throw new IllegalStateException("결제 취소가 실패하였습니다."); }
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
        private BigDecimal point;
        private Long buyerId;
        private Long auctionitemId;
        private Long addressId;

        @Builder
        public PaymentDto(String pgName, String impId, String merchantId, BigDecimal paymentPrice, BigDecimal point, Long buyerId, Long auctionitemId, Long addressId) {
            this.pgName = pgName;
            this.impId = impId;
            this.merchantId = merchantId;
            this.paymentPrice = paymentPrice;
            this.point = point;
            this.buyerId = buyerId;
            this.auctionitemId = auctionitemId;
            this.addressId = addressId;
        }

        Payment toEntity(User user, Auctionitem auctionitem,Money point) {
            return Payment.builder()
                    .paymentPrice(new Money(this.getPaymentPrice()))
                    .user(user)
                    .auctionitem(auctionitem)
                    .impId(this.getImpId())
                    .merchantId(this.merchantId)
                    .point(point)
                    .pgName(this.pgName)
                    .addressId(this.addressId)
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
