package com.project.zoopiter.domain.bbsc.dao;

import com.project.zoopiter.domain.entity.Bbsc;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ToString
@Slf4j
@Repository
@RequiredArgsConstructor
public class BbscDAOImpl implements BbscDAO{

  private final NamedParameterJdbcTemplate template;

  /**
   * 글 작성
   *
   * @param bbsc
   * @return 글번호
   */
  @Override
  public Long save(Bbsc bbsc) {
    StringBuffer sql = new StringBuffer();
    sql.append("insert into bbsc (bbsc_id , bc_title, bc_content, pet_type, bc_public, bc_gubun, user_nick) ");
    sql.append("  values(bbsc_bbsc_id_seq.nextval, :bcTitle, :bcContent, :petType, :bcPublic, :bcGubun, :userNick) ");

    KeyHolder keyHolder = new GeneratedKeyHolder();
    SqlParameterSource param = new BeanPropertySqlParameterSource(bbsc);

    template.update(sql.toString(),param, keyHolder, new String[]{"bbsc_id"});
    long bbscId = keyHolder.getKey().longValue(); //게시글 번호

    return bbscId;
  }

  /**
   * 목록
   *
   * @return
   */
  @Override
  public List<Bbsc> findAll() {
    StringBuffer sql = new StringBuffer();
    sql.append("select * from bbsc ");

    List<Bbsc> list = template.query(sql.toString(), new BeanPropertyRowMapper<>(Bbsc.class));
    return list;
  }

  /**
   * 검색
   * @param petType 펫태그(강아지,고양이,소동물,기타)
   * @return
   */
  @Override
  public List<Bbsc> findByPetType(String petType) {
    StringBuffer sql = new StringBuffer();
    sql.append("select * from bbsc where pet_type = :petType ");

    Map<String, String> param = Map.of("petType", petType);

    List<Bbsc> findLists = template.query(sql.toString(), param, new BeanPropertyRowMapper<>(Bbsc.class));

    return findLists;
  }

  /**
   * 상세조회
   *
   * @param id 게시글 번호
   * @return
   */
  @Override
  public Optional<Bbsc> findByBbscId(Long id) {
    StringBuffer sql = new StringBuffer();
    sql.append("select * from bbsc where bbsc_id = :bbscId ");
    try{
      Map<String, Long> param = Map.of("bbscId", id);
      Bbsc bbsc = template.queryForObject(
          sql.toString(),
          param,
          BeanPropertyRowMapper.newInstance(Bbsc.class)
      );
      return Optional.of(bbsc);
    }catch (EmptyResultDataAccessException e){
      return Optional.empty();
    }
  }

  /**
   * 삭제
   *
   * @param id 게시글 번호
   * @return 삭제건수
   */
  @Override
  public int deleteByBbscId(Long id) {
    String sql = "delete from bbsc where bbsc_id = :bbscId ";
    return template.update(sql,Map.of("bbscId",id));
  }

  /**
   * 수정
   *
   * @param id   게시글 번호
   * @param bbsc 수정내용
   * @return 수정건수
   */
  @Override
  public int updateByBbscId(Long id, Bbsc bbsc) {
    StringBuffer sql = new StringBuffer();
    sql.append("update bbsc ");
    sql.append("set bc_title = :bcTitle, ");
    sql.append("bc_content= :bcContent, pet_type = :petType, ");
    sql.append("bc_public = :bcPublic, bc_udate = systimestamp ");
    sql.append("where bbsc_id = :bbscId ");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("bcTitle", bbsc.getBcTitle())
        .addValue("bcContent", bbsc.getBcContent())
        .addValue("petType", bbsc.getPetType())
        .addValue("bcPublic", bbsc.getBcPublic())
        .addValue("bbscId", id);

   return template.update(sql.toString(),param);
  }

  /**
   * 조회수 증가
   *
   * @param id 게시글 번호
   * @return 수정건수
   */
  @Override
  public int increaseHitCount(Long id) {
    StringBuffer sql = new StringBuffer();
    sql.append("update bbsc  ");
    sql.append("set bc_hit = bc_hit + 1 ");
    sql.append("where bbsc_id = :bbscId ");

    Map<String, Long> param = Map.of("bbscId", id);

    return template.update(sql.toString(), param);
  }

  /**
   * 전체건수
   *
   * @return 게시글 전체건수
   */
  @Override
  public int totalCount() {
    String sql = "select count(*) from bbsc ";
    SqlParameterSource param = new EmptySqlParameterSource();
    Integer cnt = template.queryForObject(sql, param, Integer.class);
    return cnt;
  }

  @Override
  public int totalCount(String petType) {
    String sql = "select count(*) from bbsc where pet_type = :petType ";
    Map<String,String> param = Map.of("petType",petType);

    Integer cnt = template.queryForObject(sql, param, Integer.class);

    return cnt;
  }

  @Override
  public int totalCount(BbscFilterCondition filterCondition) {
    StringBuffer sql = new StringBuffer();
    sql.append("select count(*) from bbsc where ");


    return 0;
  }
}