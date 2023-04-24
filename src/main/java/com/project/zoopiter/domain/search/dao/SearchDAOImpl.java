package com.project.zoopiter.domain.search.dao;

import com.project.zoopiter.domain.entity.BBSH;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SearchDAOImpl implements SearchDAO {

  private final NamedParameterJdbcTemplate template;


  //검색-텍스트 입력
  @Override
  public List<BBSH> searchWord(String keyword, int startRec, int endRec) {
    StringBuffer sql = new StringBuffer();
    sql.append(" select t2.* ");
    sql.append(" from(select rownum no, BBSH_ID, ");
    sql.append(" BH_TITLE, BH_CONTENT, ");
    sql.append(" PET_TYPE, BH_ATTACH, BH_star, ");
    sql.append(" BH_HNAME, BH_HIT, BH_GUBUN, ");
    sql.append(" USER_NICK, BH_CDATE, BH_UDATE ");
    sql.append(" from BBSH ");
    sql.append(" where BH_CONTENT like '%'||:keyword||'%'");
    sql.append(" order by BBSH_ID DESC) t2 ");
    sql.append(" where no between :startRec and :endRec ");

    try {
      SqlParameterSource param = new MapSqlParameterSource()
        .addValue("keyword", keyword)
        .addValue("startRec", startRec)
        .addValue("endRec", endRec);

      List<BBSH> searchWordAll = template.query(
        sql.toString(),
        param,
        BeanPropertyRowMapper.newInstance(BBSH.class)
      );

      return searchWordAll;
    } catch (EmptyResultDataAccessException e) {

      return null;
    }
  }



}
