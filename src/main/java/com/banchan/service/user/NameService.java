package com.banchan.service.user;

import com.banchan.model.entity.NameWords;
import com.banchan.repository.NameWordsRepository;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NameService {
    @Autowired private NameWordsRepository nameWordsRepository;

    public List<NameWords> find(){
        return ImmutableList.of(nameWordsRepository.findByType("O"), nameWordsRepository.findByType("R"));
    }
}
