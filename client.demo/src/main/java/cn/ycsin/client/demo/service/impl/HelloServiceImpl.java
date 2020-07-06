package cn.ycsin.client.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloServiceImpl {

    @Autowired
    RestTemplate restTemplate;

    public String sayHi(String name){
        return restTemplate.getForObject("http://PROVIDER-DEMO/hi?name="+name, String.class);
    }
}
