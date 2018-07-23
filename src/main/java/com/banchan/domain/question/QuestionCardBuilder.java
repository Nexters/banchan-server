package com.banchan.domain.question;

import java.util.HashMap;
import java.util.Map;

public class QuestionCardBuilder {

    private Map<String, String> deatils = new HashMap<>();
    private VoteCount voteCount;

    public void addDetail(DetailType type, String content){
        this.deatils.put(type.name(), content);
    }

    public QuestionCard toQuestionCard(){
        return new QuestionCard();
    }
}
