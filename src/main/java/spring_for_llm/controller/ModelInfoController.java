
package spring_for_llm.controller;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ModelInfoController {

    /**
     * Spring AI의 ChatModel 인터페이스.
     * LLM과 상호작용하기 위한 공통 추상화로,
     * OpenAI, Anthropic 등 다양한 모델 구현체를 사용할 수 있다.
     *
     * 현재 설정된 ChatModel 구현체가 Spring Bean으로 주입된다.
     */
    private final ChatModel chatModel;

    /**
     * application.yml에 설정된 OpenAI 모델 이름을 주입받는다.
     *
     * ${spring.ai.openai.chat.model:기본값 사용}
     * - 설정값이 존재하면 해당 값을 사용한다.
     * - 설정값이 없으면 "기본값 사용"을 사용한다.
     */
    @Value("${spring.ai.openai.chat.model:기본값 사용}")
    private String modelName;

    /**
     * Spring AI가 자동 구성한 ChatModel Bean을 생성자 주입받는다.
     *
     * @param chatModel 현재 애플리케이션에서 사용하는 ChatModel 구현체
     */
    public ModelInfoController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    /**
     * 현재 애플리케이션에서 사용하는 AI 모델 정보를 반환한다.
     *
     * OpenAiChatModel은 ChatModel 인터페이스의 OpenAI 전용 구현체다.
     *
     * instanceof 패턴 매칭으로 OpenAiChatModel 여부를 확인한 후,
     * getOptions()를 통해 모델의 설정 정보를 조회한다.
     *
     * getModel()은 설정된 OpenAI 모델 식별자를 반환한다.
     * 실제 API 요청을 보내는 메서드는 아니다.
     *
     * @return ChatModel 구현 클래스 이름과 설정된 모델 이름
     */
    @GetMapping("/ai/model")
    public Map<String, String> getModelInfo() {

        if (chatModel instanceof OpenAiChatModel openAiChatModel) {

            // OpenAI ChatModel의 기본 옵션에서 모델 이름을 조회
            modelName = openAiChatModel.getOptions().getModel();

            System.out.println("현재 실제 사용 모델: " + modelName);
        }

        return Map.of(
                "chatModelClass", chatModel.getClass().getName(),
                "modelName", modelName
        );
    }
}
