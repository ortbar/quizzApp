package com.quizzapp.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/method")
public class TestAuthController {

    @GetMapping("hello")
    public String hello(){
        return "hello world";
    }

    @GetMapping("hello-secured")
    public String helloSecured(){
        return "hello world Secured";
    }

    @GetMapping("hello-secured2")
    public String helloSecured2(){
        return "hello world Secured2";
    }


    @GetMapping("/admin")
    public String admin() {
        return "Hola, este es un endpoint solo para ADMIN";
    }

    @GetMapping("/user")
    public String user() {
        return "Hola, este es un endpoint para USER y ADMIN";
    }

    @GetMapping("/play")
    public String play() {
        return "Hola, este es un endpoint para jugar (requiere el permiso JUGAR)";
    }

    @PostMapping ("/post")
    public String helloPost(){
        return "Hello World - POST ";
    }

    @PutMapping("/update")
    public String helloPut(){
        return "Hello World - PUT";
    }

    @DeleteMapping("/delete")
    public String helloDelete(){
        return "Hello World - DELETE";
    }

    @PatchMapping("/patch")
    public String helloPatch(){
        return "Hello World - PATCH";
    }
}
