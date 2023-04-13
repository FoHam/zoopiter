package com.project.zoopiter.domain.common.code;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@ToString
@Slf4j
@Repository
@RequiredArgsConstructor
public class CodeDAOImpl implements CodeDAO{

  private final NamedParameterJdbcTemplate template;

  /**
   * 상위코드를 입력하면 하위코드 반환
   *
   * @param pcodeId 상위코드
   * @return 하위코드&디코드
   */
  @Override
  public List<Code> code(String pcodeId) {
    StringBuffer sql = new StringBuffer();
    sql.append("select t1.code_id code, t1.decode decode  ");
    sql.append("from code t1, code t2 ");
    sql.append("where t1.pcode_id = t2.code_id  ");
    sql.append("and t1.useyn = 'Y'  ");
    sql.append("and t1.pcode_id = :pcodeId ");

    Map<String,String> param = Map.of("pcodeId",pcodeId);

    List<Code> codes = template.query(sql.toString(), param, new BeanPropertyRowMapper<>(Code.class));

    return codes;
  }

  /**
   * 모든 코드 반환
   *
   * @return
   */
  @Override
  public List<CodeAll> codeAll() {
    StringBuffer sql = new StringBuffer();
    sql.append("select t1.pcode_id pcode, t2.decode pdecode, t1.code_id ccode, t1.decode cdecode  ");
    sql.append("  from code t1, code t2 ");
    sql.append(" where t1.pcode_id = t2.code_id  ");
    sql.append("   and t1.useyn = 'Y' ");

    List<CodeAll> codeAll = template.query(sql.toString(), new BeanPropertyRowMapper<>(CodeAll.class));

    return codeAll;
  }
}
