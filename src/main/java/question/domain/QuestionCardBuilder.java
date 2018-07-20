package question.domain;

import java.util.HashMap;
import java.util.Map;

public class QuestionCardBuilder {

    private Map<DetailType, String> deatils = new HashMap<>();



    public void addDetail(DetailType type, String content){
        this.deatils.put(type, content);
    }

    public QuestionCard toQuestionCard(){
        return new QuestionCard();
    }
}
