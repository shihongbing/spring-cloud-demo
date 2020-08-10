package com.saltfishsoft.springclouddemo.auth.controller;

import com.saltfishsoft.springclouddemo.baseserver.controller.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Shihongbing on 2020/7/15.
 */
@RestController
public class KeyPairController extends BaseController{

//    @Autowired
//    private KeyPair keyPair;
//
//    @GetMapping("/rsa/publicKey")
//    public Map<String, Object> getKey() {
//        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
//        RSAKey key = new RSAKey.Builder(publicKey).build();
//        return new JWKSet(key).toJSONObject();
//    }


    /**
     * gateway降级测试
     * @return
     */
    @GetMapping("/timeout")
    public String timeout() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("休眠了4秒");
        return "timeout test";
    }
}
