package com.project.zoopiter.domain.BBSH.dao;


import com.project.zoopiter.domain.entity.BBSH;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BbshDAOImpl implements BbshDAO {

  private final NamedParameterJdbcTemplate template;

  /**
   * 등록
   *
   * @param bbsh
   * @return
   */
  @Override
  public Long save(BBSH bbsh) {

    StringBuffer sb = new StringBuffer();
    sb.append("insert into BBSH(BBSH_ID, BH_TITLE, BH_CONTENT, PET_TYPE, BH_ATTACH) ");
    sb.append("values(BBSH_BBSH_ID_seq.nextval, :bhTitle, :bhContent, :petType, :bhAttach) ");

    SqlParameterSource param = new BeanPropertySqlParameterSource(bbsh);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    template.update(sb.toString(), param, keyHolder, new String[]{"BBSH_ID"});

    long bbshId = keyHolder.getKey().longValue(); //상품아이디
//    BBSH.setId(id);

    return bbshId;
  }

  /**
   * @param bbshId
   * @return
   */
  @Override
  public Optional<BBSH> findById(Long bbshId) {
    StringBuffer sb = new StringBuffer();
    sb.append("select BBSH_ID, BH_TITLE, BH_CONTENT, BH_HIT ");
    sb.append("  from bbsh ");
    sb.append(" where BBSH_ID = :bbsh_id ");

    try {
      Map<String, Long> param = Map.of("bbsh_id", bbshId);

      BBSH bbsh = template.queryForObject(
          sb.toString(), param, BeanPropertyRowMapper.newInstance(BBSH.class)
      );
      return Optional.of(bbsh);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  /**
   * @param bbshId
   * @param bbsh
   * @return
   */
  @Override
  public int update(Long bbshId, BBSH bbsh) {
    StringBuffer sb = new StringBuffer();
    sb.append("update BBSH ");
    sb.append("   set BH_TITLE = :bhTitle, ");
    sb.append("       BH_CONTENT = :bhContent ");
//    sb.append("       author = :author, ");
//    sb.append("       hit = :hit");
//    sb.append("       cdate = :cdate");
//    sb.append("       udate = :udate");
    sb.append(" where BBSH_ID = :bbshId ");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("bhTitle", bbsh.getBhTitle())
        .addValue("bhContent", bbsh.getBhContent())
        .addValue("bbshId", bbshId);
//            .addValue("author", BBSH.getAuthor())
//            .addValue("hit", BBSH.getHit())
//            .addValue("cdate", BBSH.getCDate())
//            .addValue("udate", BBSH.getUDate());

    return template.update(sb.toString(), param);
  }

  /**
   * @param bbshId
   * @return
   */
  @Override
  public int delete(Long bbshId) {
    String sql = "delete from BBSH where BBSH_ID = :bbsh_id ";
    return template.update(sql, Map.of("bbsh_id", bbshId));
  }

  /**
   * @return 공지목록
   */
  @Override
  public List<BBSH> findAll() {

    StringBuffer sb = new StringBuffer();
    sb.append("select BBSH_ID, BH_CONTENT, BH_HIT ");
    sb.append("  from bbsh ");

    List<BBSH> list = template.query(
        sb.toString(),
        BeanPropertyRowMapper.newInstance(BBSH.class)  // 레코드 컬럼과 자바객체 멤버필드가 동일한 이름일경우, camelcase지원
    );

    return list;
  }

  //수동 매핑
  private RowMapper<BBSH> bbshRowMapper() {
    return (rs, rowNum) -> {
      BBSH bbsh = new BBSH();
      bbsh.setBbshId(rs.getLong("bbshId"));
      bbsh.setBhTitle(rs.getString("bhTitle"));
      bbsh.setBhContent(rs.getString("bhContent"));
      bbsh.setPetType(rs.getString("petType"));
      bbsh.setBhHname(rs.getString("bhHname"));
      bbsh.setBhHit(rs.getLong("bhHit"));
//      bbsh.setBhCdate(rs.getLong("cdate"));
//      bbsh.setBhUdate(rs.getLong("udate"));
      return bbsh;
    };
  }

  //  조회수증가
  @Override
  public int updateHit(Long bbshId) {
    String sql = "UPDATE BBSH SET BH_HIT = NVL(BH_HIT, 0) + 1 WHERE BBSH_ID = :bbsh_id ";
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("bbsh_id", bbshId);

// bbshId에 해당하는 레코드가 BBSH 테이블에 존재하는지 확인
    String checkSql = "SELECT COUNT(*) FROM BBSH WHERE BBSH_ID = :bbsh_id ";
    int count = template.queryForObject(checkSql, params, Integer.class);
    if (count == 0) {
      throw new IllegalArgumentException("bbshId not found in BBSH table");
    }

    int affectedRows = template.update(sql, params);
    return affectedRows;
  }
}
