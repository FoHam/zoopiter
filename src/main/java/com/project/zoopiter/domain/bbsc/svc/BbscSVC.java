package com.project.zoopiter.domain.bbsc.svc;

import com.project.zoopiter.domain.bbsc.dao.BbscFilterCondition;
import com.project.zoopiter.domain.entity.Bbsc;
import com.project.zoopiter.domain.entity.UploadFile;
import com.project.zoopiter.web.common.AttachFileType;

import java.util.List;
import java.util.Optional;

public interface BbscSVC {

  /**
   * 등록
   * @param bbsc
   * @return
   */
  Long save(Bbsc bbsc);

  /**
   * 등록(업로드 파일)
   * @param bbsc
   * @param uploadFiles
   * @return
   */
  Long save(Bbsc bbsc, List<UploadFile> uploadFiles);

  /**
   * 검색
   * @param filterCondition 펫태그(강아지,고양이,소동물,기타)
   * @return
   */
  List<Bbsc> findByPetType(BbscFilterCondition filterCondition);

  /**
   * 필터 검색
   * @param filterCondition 조회수, 최신순, 좋아요
   * @return
   */
  List<Bbsc> findByFilter(BbscFilterCondition filterCondition);


  /**
   * 조회
   * @param bbscId
   * @return
   */
  Optional<Bbsc> findById(Long bbscId);



  /**
   * 수정
   * @param bbscId
   * @param bbsc
   * @return
   */
  int update(Long bbscId, Bbsc bbsc);

  /**
   * 수정(업로드 파일)
   * @param bbscId
   * @param bbsc
   * @param uploadFiles
   * @return
   */
  int update(Long bbscId, Bbsc bbsc, List<UploadFile>uploadFiles);

  /**
   * 삭제
   * @param bbscId
   * @return
   */
  int delete(Long bbscId);

  /**
   * 삭제(업로드 파일)
   * @param bbscId
   * @param attachFileType
   * @return
   */
  int delete(Long bbscId, AttachFileType attachFileType);

  /**
   * 목록
   * @return
   */
  List<Bbsc> findAll();
  List<Bbsc> findAll(int startRec, int endRec);
  List<Bbsc> findAll(BbscFilterCondition filterCondition, int startRec, int endRec);
  /**
   * 조회수 증가
   * @param bbscId
   * @return
   */
  int increaseHit(Long bbscId);

  /**
   * 전체건수
   * @return 게시글 전체건수
   */
  int totalCount();

  int totalCount(BbscFilterCondition filterCondition);

}
