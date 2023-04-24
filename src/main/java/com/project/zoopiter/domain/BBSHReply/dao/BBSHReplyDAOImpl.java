package com.project.zoopiter.domain.BBSHReply.dao;

import com.project.zoopiter.domain.entity.BBSHReply;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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

@ToString
@Slf4j
@Repository
@RequiredArgsConstructor
public class BBSHReplyDAOImpl implements BBSHReplyDAO {

  private final NamedParameterJdbcTemplate template;

  /**
   * 댓글작성
   *
   * @param bbshReply
   * @return 댓글번호
   */
  @Override
  public Long save(BBSHReply bbshReply) {
    StringBuffer sql = new StringBuffer();
    sql.append("insert into C_BBSH (HC_ID, BBSH_ID, HC_CONTENT, USER_NICK) ");
    sql.append("values(C_BBSH_HC_ID_SEQ.nextval, :bbshId, :hcContent, :userNick) ");

    KeyHolder keyHolder = new GeneratedKeyHolder();
    SqlParameterSource param = new BeanPropertySqlParameterSource(bbshReply);

    template.update(sql.toString(),param, keyHolder, new String[]{"HC_ID"});  //댓글번호
    long hcId = keyHolder.getKey().longValue();

    return hcId;
  }


  /**
   * 댓글목록
   *
   * @param bbshId 글 번호
   * @return
   */
  @Override
  public Optional<List<BBSHReply>> findByBBSHId(Long bbshId) {
    String sql = "select * from C_BBSH where bbsh_id = :bbshId";
    try{
      Map<String,Long> param = Map.of("bbshId",bbshId);
      List<BBSHReply> bbscReplyList = template.query(sql, param, BeanPropertyRowMapper.newInstance(BBSHReply.class));
      return Optional.of(bbscReplyList);
    }catch (EmptyResultDataAccessException e){
      return Optional.empty();
    }
  }

  /**
   * 댓글 수정
   *
   * @param bbshId  글 번호
   * @param bbshReply
   * @return 수정건수
   */
  @Override
  public int updateByBcid(Long bbshId, BBSHReply bbshReply) {
    StringBuffer sql = new StringBuffer();
    sql.append("update C_BBSH ");
    sql.append("set HC_CONTENT = :bcContent, BH_UDATE = systimestamp ");
    sql.append("where HC_ID = :hcId and BBSH_ID = :bbshId ");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("bcContent",bbshReply.getBcContent())
        .addValue("hcId",bbshReply.getBbshId())
        .addValue("bbshId",bbshReply);

    return template.update(sql.toString(),param);
  }

  /**
   * 삭제
   *
   * @param bcId  댓글번호
   * @return 삭제건수
   */
  @Override
  public int deleteByBcid(Long bcId) {
    String sql = "delete from C_BBSH where HC_ID = :hcId";
    return template.update(sql, Map.of("hcId",bcId));
  }

  /**
   * 댓글 갯수
   *
   * @param bbshId 게시글 번호
   * @return 댓글 갯수
   */
  @Override
  public int countOfReplies(Long bbshId) {
    String sql = "select count(*) from C_BBSH where bbsh_id = :bbshId";
    Map<String, Long> param = Map.of("bbshId", bbshId);
    Integer cntOfReplies = template.queryForObject(sql, param, Integer.class);
    return cntOfReplies;
  }
}
