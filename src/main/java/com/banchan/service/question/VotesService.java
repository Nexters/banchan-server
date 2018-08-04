package com.banchan.service.question;

import com.banchan.model.entity.Votes;
import com.banchan.repository.VotesRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class VotesService {

    @Autowired VotesRepository votesRepository;

    public Votes add(Votes vote){
        return votesRepository.save(vote);
    }
}
