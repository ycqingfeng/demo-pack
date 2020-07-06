package cn.ycsin.web.API;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeAPI {

    @RequestMapping("/log")
    public Object doLog(){
        return "";
    }
}
