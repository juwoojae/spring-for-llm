package spring_for_llm.service;

import spring_for_llm.model.Answer;
import spring_for_llm.model.Question;

public interface OpenAIService {

    String getAnswer(String question);

    Answer getAnswer(Question question);
}