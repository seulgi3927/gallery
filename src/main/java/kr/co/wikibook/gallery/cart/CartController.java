package kr.co.wikibook.gallery.cart;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.wikibook.gallery.account.etc.AccountConstants;
import kr.co.wikibook.gallery.cart.model.CartDeleteReq;
import kr.co.wikibook.gallery.cart.model.CartGetRes;
import kr.co.wikibook.gallery.cart.model.CartPostReq;
import kr.co.wikibook.gallery.common.util.HttpUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<?> save(HttpServletRequest httpReq, @RequestBody CartPostReq req) {
        log.info("req:", req);
        int logginedMemberId = (int) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        req.setMemberId(logginedMemberId);
        int result = cartService.save(req);
        return ResponseEntity.ok(result);
    }
    @GetMapping
    public ResponseEntity<?> findAll(HttpServletRequest httpReq){ // 유저값은 프론트로부터 받지 않는다
        int logginedMemberId = (int) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        List<CartGetRes> result = cartService.findAll(logginedMemberId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping // 리퀘스트 파람 받았으면 세터를 사용하지 않아도 된다. (객체생성을 하면 되니까)
    public ResponseEntity<?> remove(@RequestParam int itemId, HttpServletRequest httpReq){
        int logginedMemberId = (int) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);

        CartDeleteReq req = new CartDeleteReq();
        req.setItemId(itemId);
        req.setMemberId(logginedMemberId);

        int result = cartService.remove(req);
        return ResponseEntity.ok(result);

    }
      // 아래처럼도 가능,
//    @DeleteMapping
//    public ResponseEntity<?> delete(@ModelAttribute CartGetRes req, HttpServletRequest httpReq){
//        int logginedMemberId = (int) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
//        req.setMemberId(logginedMemberId);
//        int result = cartService.remove(req);
//        return ResponseEntity.ok(result);
//    }

}
