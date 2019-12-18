package com.faos7.demo.controller;

import com.faos7.demo.model.Response;
import com.faos7.demo.utils.CryptoUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/")
public class MainController {

    private static Logger LOGGER = Logger.getLogger(MainController.class.getName());

    @Autowired
    private CryptoUtils cryptoUtils;

    @PostMapping
    public ResponseEntity<Response> check(@RequestBody Map<String, Object> req) throws Exception{
        LOGGER.info(new Date());
        LOGGER.info("=== request original: " + req);
        String cipherText = cryptoUtils.encode(req.toString());
        LOGGER.info("=== request encryption: " + cipherText);
        LOGGER.info("=== request decryption: " + cryptoUtils.decode(cipherText));
        try {
            int id = Integer.parseInt(req.get("id").toString());
            if (1==id){
                Response resp = new Response("Test Testov");
                LOGGER.info("=== result original: " + resp.toString());
                String cipherText2 = cryptoUtils.encode(resp.toString());
                LOGGER.info("=== result encryption: " + cipherText2);
                LOGGER.info("=== result decryption: " + cryptoUtils.decode(cipherText2));
                return ResponseEntity.ok(resp);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

}
