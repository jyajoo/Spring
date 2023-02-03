package com.example.app.app.home.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class HomeController {

  public String main() {
    return "home/main";
  }
}
