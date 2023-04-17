package com.project.zoopiter.domain.member.dao;

import com.project.zoopiter.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberDAOImpl implements MemberDAO {

  private final NamedParameterJdbcTemplate template;

  /**
   * 가입
   * @param member 회원정보
   * @return
   */
  @Override
  public Member save(Member member) {
    StringBuffer sql = new StringBuffer();
    sql.append("insert into member( ");
    sql.append(" user_id, ");
    sql.append(" user_pw, ");
    sql.append(" user_nick, ");
    sql.append(" user_email ");
    sql.append(") values( ");
    sql.append(" :userId, ");
    sql.append(" :userPw, ");
    sql.append(" :userNick, ");
    sql.append(" :userEmail ");
    sql.append(" ) ");

    SqlParameterSource param = new BeanPropertySqlParameterSource(member);

    template.update(sql.toString(),param);

    return member;
  }

  @Override
  public Member save2(Member member) {
    StringBuffer sql = new StringBuffer();
    sql.append("insert into member( ");
    sql.append(" user_id, ");
    sql.append(" user_pw, ");
    sql.append(" user_nick, ");
    sql.append(" user_email, ");
    sql.append(" gubun ");
    sql.append(") values( ");
    sql.append(" :userId, ");
    sql.append(" :userPw, ");
    sql.append(" :userNick, ");
    sql.append(" :userEmail, ");
    sql.append(" 'H0101' ");
    sql.append(" ) ");

    SqlParameterSource param = new BeanPropertySqlParameterSource(member);

    template.update(sql.toString(),param);

    return member;
  }
  /**
   * 회원정보수정
   *
   * @param userId 아이디
   * @param member 회원정보
   */
  @Override
  public void update(String userId, Member member) {
    StringBuffer sql = new StringBuffer();
    sql.append("update member set user_email = :userEmail, user_pw = :userPw, user_nick = :userNick ,user_update = systimestamp where user_id = :userId ");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("userEmail",member.getUserEmail())
        .addValue("userPw",member.getUserPw())
        .addValue("userNick",member.getUserNick())
        .addValue("userId",userId);

    template.update(sql.toString(),param);

  }

  /**
   * @param userId 아이디
   * @param member 회원정보
   */
  @Override
  public boolean updateNick(String userId, Member member) {
    StringBuffer sql = new StringBuffer();
    sql.append("update member set user_nick = :userNick ,user_update = systimestamp where user_id = :userId ");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("userNick",member.getUserNick())
        .addValue("userId",userId);

    int updatedRows = template.update(sql.toString(), param);
    if (updatedRows > 0) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * @param userId 아이디
   * @param member 회원정보
   */
  @Override
  public boolean updatePw(String userId, Member member) {
    StringBuffer sql = new StringBuffer();
    sql.append("update member set user_pw = :userPw, ,user_update = systimestamp where user_id = :userId ");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("userPw",member.getUserPw())
        .addValue("userId",userId);

    int updatedRows = template.update(sql.toString(), param);
    if (updatedRows > 0) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 조회 by email
   *
   * @param userEmail 이메일
   * @return
   */
  @Override
  public Optional<Member> findByEmail(String userEmail) {
    StringBuffer sql = new StringBuffer();
    sql.append("select * from member where user_email = :userEmail ");

    try{
      Map<String, String> param = Map.of("userEmail", userEmail);
      Member member = template.queryForObject(
        sql.toString(),
        param,
        BeanPropertyRowMapper.newInstance(Member.class) //자동매핑 sql-자바객체
      );
      return Optional.of(member);
    }catch (EmptyResultDataAccessException e){
      return Optional.empty();
    }
  }

  /**
   * 조회 by user_id
   *
   * @param userId 아이디
   * @return
   */
  @Override
  public Optional<Member> findById(String userId) {
    StringBuffer sql = new StringBuffer();
    sql.append("select * from member where user_id = :userId ");

    try{
      Map<String, String> param = Map.of("userId", userId);
      Member member = template.queryForObject(
          sql.toString(),
          param,
          BeanPropertyRowMapper.newInstance(Member.class)
      );
      return Optional.of(member);
    }catch (EmptyResultDataAccessException e){
      return Optional.empty();
    }
  }


  /**
   * 전체 조회
   *
   * @return
   */
  @Override
  public List<Member> findAll() {
    StringBuffer sql = new StringBuffer();
    sql.append("select * from member order by user_id desc ");

    List<Member> list = template.query(
      sql.toString(),
      BeanPropertyRowMapper.newInstance(Member.class)
    );

    return list;
  }

  /**
   * 탈퇴
   *
   * @param userId 이메일
   */
  @Override
  public void delete(String userId) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from member ");
    sql.append(" where user_id = :userId ");

    Map<String, String> param = Map.of("userId", userId);
    template.update(sql.toString(), param);
  }

  /**
   * @param userEmail 이메일
   * @return
   */
  @Override
  public boolean isExistEmail(String userEmail) {
    boolean flag = false;
    String sql = "select count(USER_EMAIL) from member where USER_EMAIL = :userEmail ";

    Map<String, String> param = Map.of("userEmail", userEmail);

    Integer cnt = template.queryForObject(sql, param, Integer.class);

    return cnt == 1 ? true : false;
  }

  /**
   * @param userId 아이디
   * @return
   */
  @Override
  public boolean isExistId(String userId) {
    boolean flag = false;
    String sql = "select count(USER_ID) from member where USER_ID = :userId ";

    Map<String, String> param = Map.of("userId", userId);

    Integer cnt = template.queryForObject(sql, param, Integer.class);

    return cnt == 1 ? true : false;
  }

  /**
   * @param userNick 닉네임
   * @return
   */
  @Override
  public boolean isExistNick(String userNick) {
    boolean flag = false;
    String sql = "select count(USER_NICK) from member where USER_NICK = :userNick ";

    Map<String, String> param = Map.of("userNick", userNick);

    Integer cnt = template.queryForObject(sql, param, Integer.class);

    return cnt == 1 ? true : false;
  }

  /**
   * 로그인
   *
   * @param userId 아이디
   * @param userPw 비밀번호
   * @return
   */
  @Override
  public Optional<Member> login(String userId, String userPw) {
    StringBuffer sql = new StringBuffer();
    sql.append("select user_id, user_email, user_nick, gubun from member where user_id = :userId and user_pw = :userPw");

    Map<String, String> param = Map.of("userId", userId, "userPw", userPw);
    List<Member> list = template.query(
        sql.toString(),
        param,
        BeanPropertyRowMapper.newInstance(Member.class)
    );

    return list.size() == 1 ? Optional.of(list.get(0)) : Optional.empty();
  }

  /**
   * 아이디 찾기
   *
   * @param userEmail 이메일
   * @return
   */
  @Override
  public Optional<String> findIdByEmail(String userEmail) {
    StringBuffer sql = new StringBuffer();
    sql.append("select user_id from member where user_email = :userEmail ");

    Map<String, String> param = Map.of("userEmail", userEmail);
    List<String> result = template.query(
      sql.toString(),
      param,
        (rs, rowNum)->rs.getNString("user_id")
    );

    return (result.size() == 1) ? Optional.of(result.get(0)) : Optional.empty();
  }
}
