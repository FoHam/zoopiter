package com.project.zoopiter.domain.BBSH.svc;

import com.project.zoopiter.domain.BBSH.dao.BBSHFilter;
import com.project.zoopiter.domain.entity.BBSH;
import com.project.zoopiter.domain.entity.UploadFile;
import com.project.zoopiter.web.common.AttachFileType;

import java.util.List;
import java.util.Optional;

public interface BBSHSVC {

  // 등록
  Long save(BBSH bbsh);
  Long save(BBSH bbsh, List<UploadFile> uploadFiles);

  // 조회
  Optional<BBSH> findById(Long bbshId);

  // 수정
  int update(Long bbshId, BBSH bbsh);
  int update(Long bbshId, BBSH bbsh, List<UploadFile> uploadFiles);

  // 삭제
  int delete(Long bbshId);
  int delete(Long bbshId, AttachFileType attachFileType);

  // 목록
  List<BBSH> findAll();

  List<BBSH> findAllPaging(int startRec, int endRec);

  List<BBSH> findAll(BBSHFilter filterCondition, int startRec, int endRec);

  /**
   * 검색
   * @param bbshFilter 분류,시작레코드번호,종료레코드번호,검색유형,검색어
   * @return
   */
  List<BBSH>  findByPetType(BBSHFilter filterCondition);

  /**
   * 필터 검색
   * @param filterCondition 조회수, 최신순, 좋아요
   * @return
   */
  List<BBSH> findByFilter(BBSHFilter filterCondition);

  //조회수증가
  int increaseHit(Long bbshId);

  /**
   * 전체건수
   * @return 게시글 전체건수
   */
  int totalCount();
//  int totalCount(String bcategory);
  int totalCount(BBSHFilter filterCondition);
}

