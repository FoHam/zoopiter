package com.project.zoopiter.domain.member.dao;

import com.project.zoopiter.domain.entity.PetInfo;
import lombok.RequiredArgsConstructor;
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

@Slf4j
@Repository
@RequiredArgsConstructor
public class PetInfoDAOImpl implements PetInfoDAO{
  private final NamedParameterJdbcTemplate template;

  /**
   * 등록
   *
   * @param petInfo
   * @return
   */
  @Override
  public String saveInfo(PetInfo petInfo) {
    StringBuffer sb = new StringBuffer();
    sb.append(
        "insert into pet_info(pet_num, user_id, pet_img, pet_name, pet_type, pet_gender, pet_birth, pet_yn, pet_date, pet_vac, pet_info)");
    sb.append("values(");
    sb.append("pet_info_pet_num_seq.nextval,");
    sb.append(":userId,");
    sb.append(":petImg,");
    sb.append(":petName,");
    sb.append(":petType,");
    sb.append(":petGender,");
    sb.append(":petBirth,");
    sb.append(":petYn,");
    sb.append(":petDate,");
    sb.append(":petVac,");
    sb.append(":petInfo)");

    SqlParameterSource param = new BeanPropertySqlParameterSource(petInfo);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    template.update(sb.toString(), param, keyHolder,new String[]{"pet_num"});

    Long key = keyHolder.getKey().longValue();
    return String.valueOf(key);

//    insert into pet_info(user_id, pet_img, pet_name, pet_type, pet_gender, pet_birth, pet_yn, pet_date, pet_vac, pet_info)
//    values(pet_info_pet_num_seq.nextval, :petImg, :petName, :petType, :petGender, :petBirth, :petYn, :petDate, :petVac, :petInfo)
//    @Insert("insert into pet_info (pet_num, user_id, pet_type, pet_name, pet_birth, pet_gender, pet_yn, pet_date, pet_vac, pet_info) values (PET_INFO_SEQ.nextval, :userId, :petType, :petName, :petBirth, :petGender, :petYn, :petDate, :petVac, :petInfo)")
//    @Options(useGeneratedKeys=true, keyProperty="petNum", keyColumn="pet_num")
//    Long saveInfo(PetInfo petInfo);

  }

  /**
   * 조회
   * @param petNum
   * @return
   */
  @Override
  public Optional<PetInfo> findInfo(Long petNum) {
    StringBuffer sb = new StringBuffer();
    sb.append(" select *");
    sb.append("from pet_info");
    sb.append(" where pet_num = :id");

    try{
    Map<String,Long> param = Map.of("id",petNum);
    PetInfo petInfo = template.queryForObject(sb.toString(),param,
        BeanPropertyRowMapper.newInstance(PetInfo.class));
      return Optional.of(petInfo);
    } catch (EmptyResultDataAccessException e){
      // 조회 결과가 없을때
      return Optional.empty();
    }
  }

  /** 수정
   * @param PetNum  펫아이디
   * @param petInfo 수정할 펫정보
   * @return
   */
  @Override
  public int updateInfo(Long PetNum, PetInfo petInfo) {
    StringBuffer sb = new StringBuffer();

    sb.append("update pet_info ");
    sb.append("set ");
    sb.append("    pet_img = :petImg ,");
    sb.append("    pet_name = :petName,");
    sb.append("    pet_type= :petType,");
    sb.append("    pet_gender= :petGender,");
    sb.append("    pet_birth= :petBirth,");
    sb.append("    pet_yn= :petYn ,");
    sb.append("    pet_date= :petDate,");
    sb.append("    pet_vac= :petVac,");
    sb.append("    pet_info= :petInfo ");
    sb.append("where pet_num = :id ");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("petImg",petInfo.getPetImg())
        .addValue("petName",petInfo.getPetName())
        .addValue("petType",petInfo.getPetType())
        .addValue("petGender",petInfo.getPetGender())
        .addValue("petBirth",petInfo.getPetBirth())
        .addValue("petYn",petInfo.getPetYn())
        .addValue("petDate",petInfo.getPetDate())
        .addValue("petVac",petInfo.getPetVac())
        .addValue("petInfo",petInfo.getPetInfo())
        .addValue("id", PetNum);

    return template.update(sb.toString(),param);
  }


  /** 삭제
   * @param PetNum
   * @return
   */
  @Override
  public int deleteInfo(Long PetNum) {
    String sql = "delete from pet_info where pet_num = :petNum";
    return template.update(sql,Map.of("petNum",PetNum));
  }

  /** 목록
   * @return
   */
  @Override
  public List<PetInfo> findAll(String userId) {
    StringBuffer sb = new StringBuffer();
    sb.append("select * ");
    sb.append("  from pet_info");
    sb.append(" where user_id = :userId");

    Map<String, String> param = Map.of("userId", userId);
    List<PetInfo> list = template.query(
        sb.toString(),
        param,
        BeanPropertyRowMapper.newInstance(PetInfo.class)
    );
    log.info("list={}", list);
    return list;
  }
}
