package com.project.zoopiter.domain.BBSH.dao;


import com.project.zoopiter.domain.entity.BBSH;
import lombok.RequiredArgsConstructor;
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
@Slf4j
@Repository
@RequiredArgsConstructor
public class BBSHDAOImpl implements BBSHDAO {

  private  final NamedParameterJdbcTemplate template;



  /**
   * 등록
   *
   * @param bbsh
   * @return
   */
  @Override
  public Long save(BBSH bbsh) {

    StringBuffer sb = new StringBuffer();
    sb.append("insert into BBSH(BBSH_ID, BH_TITLE, BH_CONTENT, BH_STAR, PET_TYPE ) ");
    sb.append("values(BBSH_BBSH_ID_seq.nextval, :bhTitle, :bhContent, :bhStar, :petType ) ");

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
    sb.append("select BBSH_ID, BH_TITLE, BH_CONTENT, BH_STAR, BH_HIT, PET_TYPE, BH_CDATE, BH_UDATE  ");
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
    sb.append("       BH_CONTENT = :bhContent, ");
    sb.append("       PET_TYPE = :petType,   ");
    sb.append("       BH_STAR = :bhStar,  ");
    sb.append("       BH_CDATE = :bhCdate, ");
    sb.append("       BH_UDATE = :bhUdate ");
    sb.append(" where BBSH_ID = :bbshId ");
//    sb.append("       author = :author, ");
//    sb.append("       hit = :hit");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("bhTitle", bbsh.getBhTitle())
        .addValue("bhContent", bbsh.getBhContent())
        .addValue("petType", bbsh.getPetType())
        .addValue("bhStar", bbsh.getBhStar())
        .addValue("bhHit", bbsh.getBhHit())
        .addValue("bhCdate", bbsh.getBhCdate())
        .addValue("bhUdate", bbsh.getBhUdate())
        .addValue("bbshId", bbshId);
//            .addValue("author", BBSH.getAuthor())

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
    sb.append("select BBSH_ID, BH_CONTENT, BH_HIT, PET_TYPE, BH_CDATE, BH_UDATE ");
    sb.append("  from bbsh ");

    List<BBSH> list = template.query(
        sb.toString(),
        BeanPropertyRowMapper.newInstance(BBSH.class)  // 레코드 컬럼과 자바객체 멤버필드가 동일한 이름일경우, camelcase지원
    );

    return list;
  }

//  // 카테고리별 목록
//  @Override
//  public List<BBSH> findAll(String category) {
//    StringBuffer sb = new StringBuffer();
//    sb.append("select ");
//    sb.append("       BH_TITLE ");
//    sb.append("       BH_CONTENT ");
//    sb.append("       PET_TYPE   ");
//    sb.append("       BH_HIT  ");
//    sb.append("       USER_NICK  ");
//    sb.append("       BH_CDATE  ");
//    sb.append("       BH_UDATE  ");
//    sb.append("from ");
//    sb.append(" BBSH  ");
//    sb.append("where PET_TYPE = ? ");
//    sb.append("Order by ");
//    sb.append("  BH_CDATE DESC"); // 예시로 BH_CDATE 기준으로 내림차순 정렬
//
//
//    List<BBSH> list = template.query(
//        sb.toString(),
//        BeanPropertyRowMapper.newInstance(BBSH.class)  // 레코드 컬럼과 자바객체 멤버필드가 동일한 이름일경우, camelcase지원
//    );
//
//    return list;
//  }

  @Override
  public List<BBSH> findAllPaging(int startRec, int endRec) {
    StringBuffer sb = new StringBuffer();
    sb.append("select t1.* ");
    sb.append("from( ");
    sb.append("    SELECT ");
    sb.append("    rownum no, ");
    sb.append("       BBSH_ID, ");
    sb.append("       BH_TITLE, ");
    sb.append("       BH_CONTENT, ");
    sb.append("       PET_TYPE,   ");
    sb.append("       BH_HIT,  ");
    sb.append("       USER_NICK,  ");
    sb.append("       BH_UDATE  ");
    sb.append("    FROM BBSH order by BBSH_ID DESC) t1 ");
    sb.append("where no between :startRec and :endRec ");

    Map<String, Integer> param = Map.of("startRec", startRec, "endRec", endRec);
    List<BBSH> listPaging = template.query(
        sb.toString(),
        param,
        BeanPropertyRowMapper.newInstance(BBSH.class)
    );
    return listPaging;
  }

  @Override
  public List<BBSH> findAll(BBSHFilter filterCondition, int startRec, int endRec) {
    StringBuffer sb = new StringBuffer();
    sb.append("select t1.* ");
    sb.append("from( ");
    sb.append("    select ");
    sb.append("    ROW_NUMBER() OVER (ORDER BY PET_TYPE DESC , step ASC) no, ");
    sb.append("       BBSH_ID, ");
    sb.append("       BH_TITLE, ");
    sb.append("       BH_CONTENT, ");
    sb.append("       PET_TYPE,   ");
    sb.append("       BH_HIT,  ");
    sb.append("       USER_NICK,  ");
    sb.append("       BH_CDATE  ");
    sb.append("       BH_UDATE  ");
    sb.append("     FROM BBSH  ");
    sb.append("   where PET_TYPE in ( ");
    sb = dynamicQuery(filterCondition,sb);
    sb.append( ") t1");
    sb.append("where t1.no between :startRc and :endRc ");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("startRc", startRec)
        .addValue("endRc", endRec);

    List<BBSH> list = template.query(sb.toString(), param, new BeanPropertyRowMapper<>(BBSH.class));
    return list;
  }

  @Override
  public List<BBSH> findByPetType(BBSHFilter filterCondition) {
    StringBuffer sql = new StringBuffer();
    sql.append("select * from BBSH where PET_TYPE in ( ");
    sql = dynamicQuery(filterCondition, sql);

    List<BBSH> list = null;

    list =template.query(sql.toString(), new BeanPropertyRowMapper<>(BBSH.class));


    return list;
  }

  /**
   * 필터 검색
   *
   * @param filterCondition 조회수, 최신순, 좋아요
   * @return
   */
  @Override
  public List<BBSH> findByFilter(BBSHFilter filterCondition) {
    StringBuffer sql = new StringBuffer();
    sql.append("select * from BBSH order by ");
    sql = dynamicQuery(filterCondition, sql);

    List<BBSH> list = null;

    list = template.query(sql.toString(),new BeanPropertyRowMapper<>(BBSH.class));

    return list;
  }


//
//  //수동 매핑
//  private RowMapper<BBSH> bbshRowMapper() {
//    return (rs, rowNum) -> {
//      BBSH bbsh = new BBSH();
//      bbsh.setBbshId(rs.getLong("bbshId"));
//      bbsh.setBhTitle(rs.getString("bhTitle"));
//      bbsh.setBhContent(rs.getString("bhContent"));
//      bbsh.setPetType(rs.getString("petType"));
//      bbsh.setBhHname(rs.getString("bhHname"));
//      bbsh.setBhHit(rs.getLong("bhHit"));
////      bbsh.setBhCdate(rs.getTimestamp("bhCdate").getTime());
////      bbsh.setBhUdate(rs.getTimestamp("bhUdate").getTime());
//      return bbsh;
//    };
//  }

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

  @Override
  public int totalCount() {
    String sb = "select count(*) from BBSH";
    MapSqlParameterSource params = new MapSqlParameterSource();
    Integer cnt = template.queryForObject(sb, params, Integer.class);
    log.info("public int totalCount()={}",cnt);
    return cnt;
  }

//  @Override
//  public int totalCount(String bcategory) {
//    String sql = "select count(*) from trouble_board where t_category = :tcategory";
//    MapSqlParameterSource params = new MapSqlParameterSource();
//    params.addValue("tcategory", bcategory);
//    Integer cnt = template.queryForObject(sql, params, Integer.class);
//
//    return cnt;
//  }

  @Override
  public int totalCount(BBSHFilter filterCondition) {
    StringBuffer sql = new StringBuffer();
    sql.append("select count(*) from BBSH where PET_TYPE in ( ");
    sql = dynamicQuery(filterCondition, sql);
    SqlParameterSource param = new EmptySqlParameterSource();
    Integer cntOfFindedBypetType = template.queryForObject(sql.toString(), param, Integer.class);

    return cntOfFindedBypetType;
  }

  private StringBuffer dynamicQuery(BBSHFilter filterCondition, StringBuffer sql) {
    String[] petTypes = filterCondition.getCategory();
    if (petTypes.length > 0) {
      for (int i = 0; i < petTypes.length; i++) {
        sql.append(" '" + petTypes[i] + "' ");
        if (i != petTypes.length - 1) {
          sql.append(", ");
        }
      }
      sql.append(" ) ");
    }

    String searchType = filterCondition.getSearchType();
    if (searchType == "bhHit") {
      sql.append("BH_HIT desc ");
    } else if (searchType == "bhUdate") {
      sql.append("BH_UDATE desc ");
    }
    return sql;
  }

//    //검색유형
//    switch (filterCondition.getSearchType()){
//      case "TC":  //제목 + 내용
//        sb.append("    (  BH_TITLE    like '%"+ filterCondition.getKeyword()+"%' ");
//        sb.append("    or BH_CONTENT like '%"+ filterCondition.getKeyword()+"%' )");
//        break;
//      case "T":   //제목
//        sb.append("       BH_TITLE    like '%"+ filterCondition.getKeyword()+"%' ");
//        break;
//      case "C":   //내용
//        sb.append("       BH_CONTENT like '%"+ filterCondition.getKeyword()+"%' ");
//        break;
//      default:
//    }
//    return sb;
//  }
}
