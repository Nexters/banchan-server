package com.banchan.service.question;

import com.banchan.model.domain.question.DetailType;
import com.banchan.model.entity.Questions;
import com.banchan.model.vo.QuestionCard;
import com.banchan.model.vo.VoteCount;
import com.banchan.repository.QuestionsRepository;
import one.util.streamex.EntryStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class QuestionsService {

    @Autowired private QuestionsRepository questionsRepository;

    @Autowired private ImageUploader imageUploader;
    @Autowired private QuestionDetailsService questionDetailsService;
    @Autowired private VotesService votesService;

    @Transactional
    public Questions add(QuestionCard questionCard){

        // questionCard 필요 조건 명시

        Questions question = questionsRepository.save(
                Questions.builder()
                        .userId(questionCard.getUserId())
                        .randomOrder(new Random().nextInt(Integer.MAX_VALUE))
                        .writeTime(LocalDateTime.now())
                        .build());

        questionDetailsService.add(
                question.getId(),
                EntryStream.of(questionCard.getDetails())
                        .peek(detail -> detail.setValue(
                                detail.getKey().isImgType() ?
                                        imageUploader.upload(
                                                "" + question.getId() + detail.getKey(),
                                                Base64.getDecoder().decode(detail.getValue()))
                                        : detail.getValue()))
                        .toMap());

        return question;
    }

    public List<QuestionCard> findNotSelectedQuestionCard(int randomOrder, int userId, int count){
        List<Questions> questions = questionsRepository.findNotSelectedQuestions(randomOrder, userId, count);

        return findQuestionCardByQuestions(questions);
    }

    private List<QuestionCard> findQuestionCardByQuestions(List<Questions> questions){
        List<Integer> questionIds = questions.stream().map(Questions::getId).collect(Collectors.toList());

        try {

            CompletableFuture<Map<Integer, Map<DetailType, String>>> cfDetailMap =
                    questionDetailsService.findQuestionDetails(questionIds);

            CompletableFuture<Map<Integer, VoteCount>> cfVoteCountMap =
                    votesService.findVoteCount(questionIds);

            return CompletableFuture.allOf(cfDetailMap, cfVoteCountMap)
                    .thenApply(ignoredVoid ->
                            this.toQuestionCards(questions, cfDetailMap.join(), cfVoteCountMap.join()))
                    .get();

        } catch (InterruptedException e) { throw new RuntimeException(e);
        } catch (ExecutionException e) { throw new RuntimeException(e); }
    }

    private List<QuestionCard> toQuestionCards(
            List<Questions> questions,
            Map<Integer, Map<DetailType, String>> detailMap,
            Map<Integer, VoteCount> voteCountMap){

        return questions.stream()
                .map(question -> QuestionCard.builder()
                        .id(question.getId())
                        .order(question.getRandomOrder())
                        .userId(question.getUserId())
                        .details(detailMap.get(question.getId()))
                        .voteCount(voteCountMap.get(question.getId()))
                        .build())
                .collect(Collectors.toList());
    }
}
