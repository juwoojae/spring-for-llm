package spring_for_llm.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import spring_for_llm.model.Answer;
import spring_for_llm.model.Question;
import spring_for_llm.service.OpenAIService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class QuestionControllerTest {

    private OpenAIService openAIService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        openAIService = mock(OpenAIService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new QuestionController(openAIService)).build();
    }

    @Test
    void askReturnsServiceAnswerAsJson() throws Exception {
        Question question = new Question("테스트 질문");
        when(openAIService.getAnswer(question)).thenReturn(new Answer("테스트 답변"));

        mockMvc.perform(post("/ask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"question\":\"테스트 질문\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.answer").value("테스트 답변"));

        verify(openAIService).getAnswer(question);
    }

    @Test
    void askRejectsMissingRequestBody() throws Exception {
        mockMvc.perform(post("/ask").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
