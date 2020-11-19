package com.example.test.demo.model.resource;

import com.example.test.demo.model.service.DocumentService;
import com.example.test.demo.model.service.DocumentServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("api")
public class Controller {
    private DocumentService service = new DocumentServiceImpl();

    @RequestMapping(value = "calculateSha256", method = POST)
    public void calculateSha256(@RequestParam(name = "input") String path_to_folder, @RequestParam String destination) throws IOException {

        System.out.println("Start");

        service.search(new File(path_to_folder));

        System.out.println("already calculate sha-sum");
        File from = new File(path_to_folder);
        File to = new File(destination);

        boolean success = service.moveDir(from.toPath(), to.toPath());
        if (success) {
            System.out.println("File Moved Successfully");
        } else {
            System.out.println("File Moved Failed");
        }
    }

}
