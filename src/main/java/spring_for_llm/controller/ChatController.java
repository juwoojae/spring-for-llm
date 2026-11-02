package spring_for_llm.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/ai")
public class ChatController {
    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/ask")
    public String ask(@RequestParam String question) {
        return chatClient.prompt().user(question).call().content();
    }

    /**
     * 사용자 질문에 대한 AI 응답을 server-sent-event 방식으로 실시간 스트리밍한다.
     *
     * @param question 사용자 질문
     * @return AI 응답을 비동기 스트림으로 전달하는 Flux
     */
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> stream(@RequestParam String question) {
        return chatClient.prompt()
                .user(question)  // 사용자 프롬프트 설정
                .stream()        // AI 응답을 스트리밍 방식으로 요청
                .content();      // 생성된 텍스트를 Flux<String>으로 반환
    }

}