package spring_for_llm.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import spring_for_llm.model.Answer;
import spring_for_llm.model.Question;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OpenAIServiceImplTest {

    private ChatModel chatModel;
    private OpenAIServiceImpl service;

    @BeforeEach
    void setUp() {
        chatModel = mock(ChatModel.class);
        service = new OpenAIServiceImpl(chatModel);
    }

    @Test
    void returnsAnswerForQuestionObject() {
        when(chatModel.call(org.mockito.ArgumentMatchers.any(Prompt.class)))
                .thenReturn(responseWith("Spring AI 응답"));

        Answer answer = service.getAnswer(new Question("Spring AI란?"));

        assertThat(answer.answer()).isEqualTo("Spring AI 응답");
        assertPromptContent("Spring AI란?");
    }

    @Test
    void returnsTextAnswerForStringQuestion() {
        when(chatModel.call(org.mockito.ArgumentMatchers.any(Prompt.class)))
                .thenReturn(responseWith("문자열 응답"));

        String answer = service.getAnswer("LLM이란?");

        assertThat(answer).isEqualTo("문자열 응답");
        assertPromptContent("LLM이란?");
    }

    private ChatResponse responseWith(String content) {
        return new ChatResponse(List.of(new Generation(new AssistantMessage(content))));
    }

    private void assertPromptContent(String expected) {
        ArgumentCaptor<Prompt> promptCaptor = ArgumentCaptor.forClass(Prompt.class);
        verify(chatModel).call(promptCaptor.capture());
        assertThat(promptCaptor.getValue().getContents()).isEqualTo(expected);
    }
}
