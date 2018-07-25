package com.banchan.service.question;

import com.banchan.repository.QuestionDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionDetailsService {

    @Autowired QuestionDetailsRepository questionDetailsRepository;

}
