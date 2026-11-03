package spring_for_llm.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ModelInfoControllerTest {

    @Test
    void returnsConfiguredNameForGenericChatModel() {
        ChatModel chatModel = mock(ChatModel.class);
        ModelInfoController controller = new ModelInfoController(chatModel);
        ReflectionTestUtils.setField(controller, "modelName", "configured-model");

        Map<String, String> result = controller.getModelInfo();

        assertThat(result)
                .containsEntry("chatModelClass", chatModel.getClass().getName())
                .containsEntry("modelName", "configured-model");
    }

    @Test
    void returnsModelNameFromOpenAiRuntimeOptions() {
        OpenAiChatModel chatModel = mock(OpenAiChatModel.class);
        OpenAiChatOptions options = mock(OpenAiChatOptions.class);
        when(chatModel.getOptions()).thenReturn(options);
        when(options.getModel()).thenReturn("gpt-runtime");
        ModelInfoController controller = new ModelInfoController(chatModel);

        Map<String, String> result = controller.getModelInfo();

        assertThat(result)
                .containsEntry("chatModelClass", OpenAiChatModel.class.getName())
                .containsEntry("modelName", "gpt-runtime");
    }
}
