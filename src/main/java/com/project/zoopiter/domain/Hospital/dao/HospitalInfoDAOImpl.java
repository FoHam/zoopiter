package com.project.zoopiter.domain.Hospital.dao;

import com.project.zoopiter.domain.entity.HospitalInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class HospitalInfoDAOImpl implements HospitalInfoDAO {

  private final NamedParameterJdbcTemplate template;
  private static final Logger LOGGER = LoggerFactory.getLogger(HospitalDAOImpl.class);
  /**
   * 전체 조회
   *
   * @return
   */
  @Override
  public List<HospitalInfo> findAllInfo() {
    StringBuffer sql = new StringBuffer();
    sql.append("select * from HOSPITAL_INFO order by H_NAME desc ");

    List<HospitalInfo> hospitalsinfo = template.query(
            sql.toString(),
            BeanPropertyRowMapper.newInstance(HospitalInfo.class)
    );

    LOGGER.info("Fetched {} animal hospitalsinfo from the database", hospitalsinfo.size());

    return hospitalsinfo;
  }



}
