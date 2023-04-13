package com.project.zoopiter.domain.bbsc.svc;

import com.project.zoopiter.domain.entity.Bbsc;

import java.util.List;

public interface BbscSVC {
  /**
   * 글 작성
   * @param bbsc
   * @return 글번호
   */
  Long saveWrite(Bbsc bbsc);

  /**
   * 목록
   * @return
   */
  List<Bbsc> findAll();

  /**
   * 검색
   * @param petType 펫태그(강아지,고양이,소동물,기타)
   * @return
   */
  List<Bbsc> findByPetType(String petType);

  /**
   * 상세조회
   * @param id 게시글 번호
   * @return
   */
  Bbsc  findByBbscId(Long id);

  /**
   * 삭제
   * @param id 게시글 번호
   * @return 삭제건수
   */
  int deleteByBbscId(Long id);

  /**
   * 수정
   * @param id 게시글 번호
   * @param bbsc 수정내용
   * @return
   */
  void updateByBbscId(Long id, Bbsc bbsc);

  /**
   * 조회수 증가
   * @param id 게시글 번호
   * @return
   */
  int increaseHitCount(Long id);

}
