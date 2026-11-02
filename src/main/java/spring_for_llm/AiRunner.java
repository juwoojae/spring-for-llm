package spring_for_llm;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * CommandLineRunner는 Spring Boot 실행 후 run()을 자동으로 호출하기 위한 인터페이스
 */
@Component
public class AiRunner implements CommandLineRunner {

    private final ChatClient chatClient;

    public AiRunner(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }


    @Override
    //prompt -> user/system -> call/stream -> content/entity
    public void run(String... args) throws Exception {
        // LLM에 전달할 프롬프트 생성
        String answer = chatClient.prompt()
                .user("Spring AI 를 한문장으로 정의해줘")  // 사용자 메시지 설정
                .call()  // LLM에 요청을 보내고 응답을 받음
                .content();  // 응답의 텍스트 추출
        System.out.println(answer);
    }
}
