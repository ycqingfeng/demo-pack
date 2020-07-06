package cn.ycsin.client.demo.API;

import cn.ycsin.client.demo.service.impl.HelloServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoAPI {

    @Autowired
    HelloServiceImpl helloService;

    @RequestMapping(value = "/hi")
    public String hi(@RequestParam String name){
        return helloService.sayHi(name);
    }
}
