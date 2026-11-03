package spring_for_llm;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AiRunnerTest {

    @Test
    void sendsStartupQuestionThroughChatClient() throws Exception {
        ChatClient.Builder builder = mock(ChatClient.Builder.class);
        ChatClient chatClient = mock(ChatClient.class);
        ChatClient.ChatClientRequestSpec requestSpec = mock(ChatClient.ChatClientRequestSpec.class);
        ChatClient.CallResponseSpec callResponse = mock(ChatClient.CallResponseSpec.class);
        when(builder.build()).thenReturn(chatClient);
        when(chatClient.prompt()).thenReturn(requestSpec);
        when(requestSpec.user("Spring AI 를 한문장으로 정의해줘")).thenReturn(requestSpec);
        when(requestSpec.call()).thenReturn(callResponse);
        when(callResponse.content()).thenReturn("Spring AI 설명");

        new AiRunner(builder).run();

        verify(requestSpec).user("Spring AI 를 한문장으로 정의해줘");
        verify(callResponse).content();
    }
}
