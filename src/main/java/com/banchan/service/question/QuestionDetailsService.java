package com.banchan.service.question;

import com.banchan.model.domain.question.DetailType;
import com.banchan.model.entity.QuestionDetails;
import com.banchan.repository.QuestionDetailsRepository;
import one.util.streamex.EntryStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class QuestionDetailsService {

    @Autowired private QuestionDetailsRepository questionDetailsRepository;

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

    public CompletableFuture<Map<Integer, Map<DetailType, String>>> findQuestionDetails(List<Integer> questionIds){

        return questionDetailsRepository.findALLByQuestionIdInOrderByQuestionIdAsc(questionIds)
                .thenApply(details ->
                        EntryStream.of(details.stream().collect(Collectors.groupingBy(QuestionDetails::getQuestionId)))
                                .mapValues(questionDetailsSingulars -> questionDetailsSingulars.stream()
                                        .collect(Collectors.toMap(
                                                QuestionDetails::getType,
                                                QuestionDetails::getContent)))
                                .toMap());
    }
}
