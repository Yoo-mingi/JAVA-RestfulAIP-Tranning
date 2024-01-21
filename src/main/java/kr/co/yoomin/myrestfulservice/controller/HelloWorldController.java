package kr.co.yoomin.myrestfulservice.controller;

import kr.co.yoomin.myrestfulservice.bean.HelloWorldBean;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {
    private MessageSource messageSource;

    public HelloWorldController(MessageSource messageSource){
        this.messageSource=messageSource;
    }
    // GET
    //URI = /hello-world
    // @RequestMapping(method=RequestMetuod.GET, path=/hello-world)

    //GetMapping 어노테이션만으로 하고자 하는것이 GET 임을 선언
    @GetMapping(path="/hello-world")
    public String helloworld(){
        return "Hello world";
    }

    @GetMapping(path="/hello-world-bean")
    public HelloWorldBean helloworldBean(){
        return new HelloWorldBean("Hello World!");
    }

    @GetMapping(path="/hello-world-bean/path-variable/{name}")
    public HelloWorldBean helloworldBeanPathVariable(@PathVariable String name){
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }
    @GetMapping(path="/hello-world-internalized")
    public String helloworlInternalized(
        @RequestHeader(name = "Accept-Language", required = false)Locale locale) {
        return messageSource.getMessage("greeting.message", null,locale);
    }
}
