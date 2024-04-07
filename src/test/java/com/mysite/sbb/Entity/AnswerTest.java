package com.mysite.sbb.Entity;

import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.QuestionRepository;
import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.question.Question;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AnswerTest {

  @Autowired
  AnswerRepository answerRepository;

  @Autowired
  QuestionRepository questionRepository;

  @Test
  void 답변인서트테스트() {
    Optional<Question> oq = this.questionRepository.findById(2);
    assertTrue(oq.isPresent());
    Question q = oq.get();

    Answer a = new Answer();
    a.setContent("네 자동으로 생성됩니다.");
    a.setQuestion(q);
    a.setCreateDate(LocalDateTime.now());
    this.answerRepository.save(a);

  }

  @Test
  void 파인드테스트() {
    Optional<Answer> oa = this.answerRepository.findById(1);
    assertTrue(oa.isPresent());
    Answer a = oa.get();

    assertEquals(2, a.getQuestion().getId());

  }
}