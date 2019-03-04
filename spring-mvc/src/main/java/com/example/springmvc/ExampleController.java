package com.example.springmvc;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;


@RequestMapping(method = RequestMethod.GET)
@RestController
public class ExampleController {
    @RequestMapping(value = "/example1")
    public List<String> example1(ModelMap model) {

        List<String> list = Arrays.asList("test1", "test2");

        return list;
    }
}
