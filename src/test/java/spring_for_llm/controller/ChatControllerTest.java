package spring_for_llm.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ChatControllerTest {

    private ChatClient chatClient;
    private ChatClient.ChatClientRequestSpec requestSpec;
    private ChatController controller;

    @BeforeEach
    void setUp() {
        ChatClient.Builder builder = mock(ChatClient.Builder.class);
        chatClient = mock(ChatClient.class);
        requestSpec = mock(ChatClient.ChatClientRequestSpec.class);

        when(builder.build()).thenReturn(chatClient);
        when(chatClient.prompt()).thenReturn(requestSpec);
        controller = new ChatController(builder);
    }

    @Test
    void askReturnsChatClientContent() {
        ChatClient.CallResponseSpec callResponse = mock(ChatClient.CallResponseSpec.class);
        when(requestSpec.user("질문")).thenReturn(requestSpec);
        when(requestSpec.call()).thenReturn(callResponse);
        when(callResponse.content()).thenReturn("답변");

        String result = controller.ask("질문");

        assertThat(result).isEqualTo("답변");
        verify(requestSpec).user("질문");
    }

    @Test
    void streamReturnsAllChatClientChunksInOrder() {
        ChatClient.StreamResponseSpec streamResponse = mock(ChatClient.StreamResponseSpec.class);
        when(requestSpec.user("질문")).thenReturn(requestSpec);
        when(requestSpec.stream()).thenReturn(streamResponse);
        when(streamResponse.content()).thenReturn(Flux.just("첫 ", "두 번째"));

        List<String> chunks = controller.stream("질문").collectList().block();

        assertThat(chunks).containsExactly("첫 ", "두 번째");
        verify(requestSpec).user("질문");
    }
}
