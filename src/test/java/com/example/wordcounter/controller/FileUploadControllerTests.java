package com.example.wordcounter.controller;

import com.example.wordcounter.helper.FileHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class FileUploadControllerTests {

    private MockMvc mockMvc;

    @InjectMocks
    private FileUploadController fileUploadController;

    @BeforeEach
    private void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(fileUploadController).build();
    }

    @Test
    public void test_uploadPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("upload"));
    }

    @Test
    public void test_upload() throws Exception {

        MockMultipartFile mockFile = new MockMultipartFile("file", "test.txt", "text/plain", "123 456 789".getBytes());

        FileHelper fileHelper = new FileHelper();
        fileHelper.readFile(mockFile);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/upload")
        .file(mockFile))
        .andExpect(status().isOk())
        .andExpect(view().name("result"))
        .andExpect(model().attribute("wordCount", fileHelper.getWordCount()))
        .andExpect(model().attribute("averageWordLength", fileHelper.getAverageWordLength()))
        .andExpect(model().attribute("wordLengths", fileHelper.getWordLengths()))
        .andExpect(model().attribute("mostFrequent", fileHelper.getMostFrequent()));
    }

    @Test
    public void test_checkFile() throws Exception {
        mockMvc.perform(get("/check-file").param("extension", "text/plain"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("true"));
    }
}
