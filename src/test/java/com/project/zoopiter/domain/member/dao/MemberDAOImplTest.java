package com.project.zoopiter.domain.member.dao;

import com.project.zoopiter.domain.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberDAOImplTest {

  @Autowired
  private MemberDAO memberDAO;

  @Test
  @Order(1)
  @DisplayName("가입")
  void save() {
    Member member = new Member();
    member.setUserId("member2");
    member.setUserPw("member4321");
    member.setUserEmail("member2@gmail.com");
    member.setUserNick("회원4");
//    member.setGubun("M0101");

    Member savedMember = memberDAO.save(member);

    Assertions.assertThat(savedMember.getUserId()).isEqualTo("member2");
    Assertions.assertThat(savedMember.getUserPw()).isEqualTo("member4321");
    Assertions.assertThat(savedMember.getUserEmail()).isEqualTo("member2@gmail.com");
    Assertions.assertThat(savedMember.getUserNick()).isEqualTo("회원4");
//    Assertions.assertThat(savedMember.getGubun()).isEqualTo("M0101");
  }

    @Test
    @Order(2)
    @DisplayName("수정")
    void update(){
      String userId = "member2";
      Member member = new Member();
      member.setUserPw("member9999");
      member.setUserNick("회원9");
      member.setUserEmail("member99@gmail.com");

      memberDAO.update(userId,member);
      Optional<Member> findedMember = memberDAO.findById(userId);
      Assertions.assertThat(findedMember.get().getUserPw()).isEqualTo(member.getUserPw());
      Assertions.assertThat(findedMember.get().getUserNick()).isEqualTo(member.getUserNick());
      Assertions.assertThat(findedMember.get().getUserEmail()).isEqualTo(member.getUserEmail());
    }

    @Test
    @Order(3)
    @DisplayName("조회 by email")
    void findbyEmail(){
      String userEmail = "member99@gmail.com";
      Optional<Member> member = memberDAO.findByEmail(userEmail);
      Member findedMember = member.orElseThrow();
      Assertions.assertThat(findedMember.getUserPw()).isEqualTo("member9999");
      Assertions.assertThat(findedMember.getUserNick()).isEqualTo("회원9");
      Assertions.assertThat(findedMember.getUserEmail()).isEqualTo("member99@gmail.com");
      log.info("findedMember={}",findedMember);

    }

    @Test
    @Order(4)
    @DisplayName("조회 by user_id")
    void findById(){
      String userId = "member2";
      Optional<Member> member = memberDAO.findById(userId);
      Member findedMember = member.orElseThrow();
      Assertions.assertThat(findedMember.getUserPw()).isEqualTo("member9999");
      Assertions.assertThat(findedMember.getUserNick()).isEqualTo("회원9");
      Assertions.assertThat(findedMember.getUserEmail()).isEqualTo("member99@gmail.com");
    }

    @Test
    @Order(5)
    @DisplayName("전체 조회")
    void findAll(){
      List<Member> list = memberDAO.findAll();
      Assertions.assertThat(list.size()).isGreaterThan(0);
    }

    @Test
    @Order(6)
    @DisplayName("탈퇴")
    void delete(){
      String userId = "member2";
      memberDAO.delete(userId);
      Optional<Member> findedMember = memberDAO.findById(userId);
      Assertions.assertThatThrownBy(()->findedMember.orElseThrow())
          .isInstanceOf(NoSuchElementException.class);

    }

    @Test
    @Order(7)
    @DisplayName("이메일유무")
    void isExistEmail(){
      String userEmail = "test1@gamil.com";
      boolean exist = memberDAO.isExistEmail(userEmail);
      Assertions.assertThat(exist).isTrue();
    }

  @Test
  @Order(8)
  @DisplayName("이메일유무2")
  void isExistEmail2(){
    String userEmail = "asd@gmail.com";
    boolean exist = memberDAO.isExistEmail(userEmail);
    Assertions.assertThat(exist).isFalse();
  }

    @Test
    @Order(9)
    @DisplayName("아이디유무")
    void isExistId(){
      String userId = "test1";
      boolean exist = memberDAO.isExistId(userId);
      Assertions.assertThat(exist).isTrue();
    }

  @Test
  @Order(10)
  @DisplayName("아이디유무2")
  void isExistId2(){
    String userId = "asdasd";
    boolean exist = memberDAO.isExistId(userId);
    Assertions.assertThat(exist).isFalse();
  }

  @Test
  @Order(11)
  @DisplayName("닉네임유무1")
  void isExistNick(){
    String userNick = "별칭1";
    boolean exist = memberDAO.isExistNick(userNick);
    Assertions.assertThat(exist).isTrue();
  }

  @Test
  @Order(12)
  @DisplayName("닉네임유무2")
  void isExistNick2(){
    String userNick = "asdasd";
    boolean exist = memberDAO.isExistNick(userNick);
    Assertions.assertThat(exist).isFalse();
  }


    @Test
    @Order(13)
    @DisplayName("로그인")
    void login(){
      String userId="test1";
      String userPw="test1234";
      Optional<Member> member = memberDAO.login(userId,userPw);

      //Optional.isPresent() 메소드는 Optional 객체가 값이 존재하는지를 확인하는 메소드
      //Optional 객체가 값이 존재하면 true를, 값이 존재하지 않으면 false를 반환
      Assertions.assertThat(member.isPresent()).isTrue();
      log.info("member={}",member);

      member = memberDAO.login("asd","asd");
      Assertions.assertThat(member.isPresent()).isFalse();
    }

  @Test
  @Order(14)
  @DisplayName("이메일로 아이디 찾기")
  void findIdByEmail(){
    String userEmail="test1@gamil.com";
    Optional<String> member = memberDAO.findIdByEmail(userEmail);

    //Optional.isPresent() 메소드는 Optional 객체가 값이 존재하는지를 확인하는 메소드
    //Optional 객체가 값이 존재하면 true를, 값이 존재하지 않으면 false를 반환
    Assertions.assertThat(member.isPresent()).isTrue();
    log.info("member={}",member);

    member = memberDAO.findIdByEmail("asd");
    Assertions.assertThat(member.isPresent()).isFalse();
  }


  }
