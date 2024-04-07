package com.mysite.sbb.Entity;

import com.mysite.sbb.question.QuestionRepository;
import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest

class QuestionTest {


  @Autowired
  private QuestionRepository questionRepository;

  @Autowired
  private QuestionService questionService;

  @Test
  void contextLoads() {
    Question q1 = new Question();
    q1.setSubject("sbb가 무엇인가요?");
    q1.setContent("sbb에 대해서 알고 싶습니다.");
    q1.setCreateDate(LocalDateTime.now());
    this.questionRepository.save(q1); // 수정된 부분

    Question q2 = new Question();
    q2.setSubject("sbb가 무엇인가요?");
    q2.setContent("sbb에 대해서 알고 싶습니다.");
    q2.setCreateDate(LocalDateTime.now());
    this.questionRepository.save(q2); // 수정된 부분
  }


  @Test
  void testJpa(){
    List<Question> all = this.questionRepository.findAll();
    assertEquals(2, all.size());

    Question q = all.get(0);
    assertEquals("sbb가 무엇인가요?", q.getSubject());
  }

  @Test
  void findbyid테스트(){
    Optional<Question> oq = this.questionRepository.findById(1);
    if(oq.isPresent()){
      Question q = oq.get();
      assertEquals("sbb가 무엇인가요?", q.getSubject());
    }
  }

  @Test
  void findby서브젝트테스트(){
    Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?");
    assertEquals(1,q.getId());
  }

  @Test
  void  findby서브젝트콘텐츠(){
    Question q = this.questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
    assertEquals(1,q.getId());


  }

  @Test
  void 라이크테스트(){
    List<Question> questionList = this.questionRepository.findBySubjectLike("sbb%");
    Question q = questionList.get(0);
    assertEquals("sbb가 무엇인아교?",q.getSubject());
  }

  @Test
  void 수정테스트(){
    Optional<Question> oq = this.questionRepository.findById(1);
    assertTrue(oq.isPresent());
    Question q = oq.get();
    q.setSubject("수정된 제목");
    this.questionRepository.save(q);
  }

  @Test
  void 삭제테스트(){
    assertEquals(2, this.questionRepository.count());
    Optional<Question> oq = this.questionRepository.findById(1);
    assertTrue(oq.isPresent());
    Question q = oq.get();
    this.questionRepository.delete(q);
    assertEquals(1,this.questionRepository.count());
  }

  @Test
  @Transactional
  void 질문에서답변객체찾기테스트(){
    Optional<Question> oq = this.questionRepository.findById(2);
    assertTrue(oq.isPresent());
    Question q = oq.get();


    List<Answer> answerList = q.getAnswerList();

    assertEquals(1, answerList.size());
    assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());


  }


  @Test
  void 질문여러개추가(){
    for (int i= 1; i<=300; i++){
      String subject = String.format("테스트 데이터입니다:[%03d]", i);
      String content = "내용없음";
      this.questionService.create(subject, content, null);
    }
  }
}