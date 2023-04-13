package com.project.zoopiter.domain.common.file.dao;

import com.project.zoopiter.domain.entity.UploadFile;
import com.project.zoopiter.web.common.AttachFileType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UploadFileDAOImpl implements UploadFileDAO{

  private final NamedParameterJdbcTemplate template;

//  public UploadFileDAOImpl(NamedParameterJdbcTemplate template) {
//    this.template = template;
//  }

  //업로드파일 등록-단건
  @Override
  public Long addFile(UploadFile uploadFile) {

    StringBuffer sql = makeAddFileSql();
    SqlParameterSource param = new BeanPropertySqlParameterSource(uploadFile);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    template.update(sql.toString(),param,keyHolder,new String[]{"UPLOADFILE_ID"});

    return keyHolder.getKey().longValue();
  }

  //업로드파일 등록-여러건
  @Override
  public void addFiles(List<UploadFile> uploadFiles) {

    StringBuffer sql = makeAddFileSql();
    if(uploadFiles.size() == 1){
      addFile(uploadFiles.get(0));
//      SqlParameterSource param = new BeanPropertySqlParameterSource(uploadFiles.get(0));
//      template.update(sql.toString(),param);
    }else {
      SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(uploadFiles);
      //배치 처리 : 여러건의 갱신작업을 한꺼번에 처리하므로 단건처리할때보다 성능이 좋다.
      template.batchUpdate(sql.toString(), params);
    }
  }

  private StringBuffer makeAddFileSql() {
    StringBuffer sql = new StringBuffer();
    sql.append("insert into UPLOADFILE ( ");
    sql.append("  UPLOADFILE_ID, ");
    sql.append("  CODE, ");
    sql.append("  RID, ");
    sql.append("  STORE_FILENAME, ");
    sql.append("  UPLOAD_FILENAME, ");
    sql.append("  FSIZE, ");
    sql.append("  FTYPE ");
    sql.append(") values ( ");
    sql.append("  UPLOADFILE_UPLOADFILE_ID_seq.nextval, ");
    sql.append("  :code, ");
    sql.append("  :rid, ");
    sql.append("  :store_filename, ");
    sql.append("  :upload_filename, ");
    sql.append("  :fsize, ");
    sql.append("  :ftype ");
    sql.append(") ");
    return sql;
  }

  //조회
  @Override
  public List<UploadFile> findFilesByCodeWithRid(AttachFileType attachFileType, Long rid) {
    StringBuffer sql = new StringBuffer();

    sql.append("select  ");
    sql.append("   UPLOADFILE_ID, ");
    sql.append("   CODE, ");
    sql.append("   RID,  ");
    sql.append("   STORE_FILENAME, ");
    sql.append("   UPLOAD_FILENAME,  ");
    sql.append("   FSIZE,  ");
    sql.append("   FTYPE,  ");
    sql.append("   CDATE,  ");
    sql.append("   UDATE ");
    sql.append("  from  UPLOADFILE  ");
    sql.append(" where CODE = :code  ");
    sql.append("   and RID = :rid  ");

    return template.query(
        sql.toString(),
        Map.of("code",attachFileType.name(),"rid",rid),
        BeanPropertyRowMapper.newInstance(UploadFile.class));
  }

  //첨부파일 조회
  @Override
  public Optional<UploadFile> findFileByUploadFileId(Long uploadfileId) {
    StringBuffer sql = new StringBuffer();
    sql.append(" select * ");
    sql.append("  from UPLOADFILE ");
    sql.append(" where UPLOADFILE_ID = :uploadfileId ");

    UploadFile uploadFile = null;
    try {
      Map<String, Long> param = Map.of("uploadfileId", uploadfileId);
      uploadFile = template.queryForObject(sql.toString(),param,BeanPropertyRowMapper.newInstance(UploadFile.class));
      return Optional.of(uploadFile);
    }catch (EmptyResultDataAccessException e){
      return Optional.empty();
    }
  }

  // 첨부파일 삭제 by uplaodfileId
  @Override
  public int deleteFileByUploadFildId(Long uploadfileId) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from UPLOADFILE ");
    sql.append(" where UPLOADFILE_ID = :uploadfileId ");

    return template.update(sql.toString(), Map.of("uploadfileId",uploadfileId));
  }

  // 첨부파일 삭제 by code, rid
  @Override
  public int deleteFileByCodeWithRid(AttachFileType attachFileType, Long rid) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from UPLOADFILE ");
    sql.append(" where CODE = :code ");
    sql.append("   and RID = :rid ");

    return template.update(sql.toString(),Map.of("code",attachFileType.name(),"rid",rid));
  }
}