package com.project.zoopiter.domain.Hospital.dao;


import com.project.zoopiter.domain.entity.Hospital;
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
public class HospitalDAOImpl implements HospitalDAO {

  private final NamedParameterJdbcTemplate template;
  private static final Logger LOGGER = LoggerFactory.getLogger(HospitalDAOImpl.class);
  /**
   * 전체 조회
   *
   * @return
   */
  @Override
  public List<Hospital> findAll() {
    StringBuffer sql = new StringBuffer();
    sql.append("select * from HOSPITAL_DATA order by HD_NAME desc ");

    List<Hospital> hospitals = template.query(
            sql.toString(),
            BeanPropertyRowMapper.newInstance(Hospital.class)
    );

    LOGGER.info("Fetched {} animal hospitals from the database", hospitals.size());

    return hospitals;
  }
  }

