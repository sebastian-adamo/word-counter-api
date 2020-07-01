package com.example.wordcounter.controller;

import com.example.wordcounter.helper.FileHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FileUploadController {

    @GetMapping("/")
    public String uploadPage() {
        return "upload";
    }

    @PostMapping("/upload")
    public String upload(Model model, @RequestParam MultipartFile file) throws IOException {

        FileHelper fileHelper = new FileHelper();
        fileHelper.readFile(file);

        model.addAttribute("wordCount", fileHelper.getWordCount());
        model.addAttribute("averageWordLength", fileHelper.getAverageWordLength());
        model.addAttribute("wordLengths", fileHelper.getWordLengths());
        model.addAttribute("mostFrequent", fileHelper.getMostFrequent());

        return "result";
    }

    @GetMapping("/check-file")
    @ResponseBody
    public boolean checkFile(@RequestParam String extension) {
        return extension.equals("text/plain");
    }

}
