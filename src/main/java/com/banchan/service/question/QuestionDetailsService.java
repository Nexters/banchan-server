package com.banchan.service.question;

import com.banchan.model.domain.question.DetailType;
import com.banchan.model.entity.QuestionDetails;
import com.banchan.model.entity.QuestionDetailsSingular;
import com.banchan.model.entity.Questions;
import com.banchan.repository.QuestionDetailsRepository;
import com.banchan.repository.QuestionDetailsSingularRepository;
import one.util.streamex.EntryStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class QuestionDetailsService {

    @Autowired QuestionDetailsRepository questionDetailsRepository;
    @Autowired QuestionDetailsSingularRepository questionDetailsSingularRepository;

    public List<QuestionDetails> add(int questionId, Map<DetailType, String> details){

        return questionDetailsRepository.saveAll(
                EntryStream.of(details)
                        .map(detail -> QuestionDetails.builder()
                                .questionId(questionId)
                                .type(detail.getKey())
                                .content(detail.getValue())
                                .build())
                        .collect(Collectors.toList()));

    }

    public Map<Questions, List<QuestionDetailsSingular>> findQuestionDetails(List<Questions> questionsList){

        try {

            return questionDetailsSingularRepository.findALLByQuestionInOrderByQuestionAsc(questionsList)
                    .get().stream()
                    .collect(Collectors.groupingBy(
                            QuestionDetailsSingular::getQuestion));

        } catch (InterruptedException e) { throw new RuntimeException(e);
        } catch (ExecutionException e) { throw new RuntimeException(e); }
    }
}
