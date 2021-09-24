package com.decathlon.alert.mechanism.system.feign;

import com.decathlon.alert.mechanism.system.feign.request.SendSmsRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "smsService", url = "${smsService.hostUrl}")
public interface SmsServiceFeign {

    @PostMapping("/v3/fd99c100-f88a-4d70-aaf7-393dbbd5d99f")
    ResponseEntity<String> sendSms(@RequestBody SendSmsRequest sendSmsRequest);

}
