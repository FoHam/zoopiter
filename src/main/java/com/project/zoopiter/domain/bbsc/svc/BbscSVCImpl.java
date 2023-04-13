package com.project.zoopiter.domain.bbsc.svc;

import com.project.zoopiter.domain.bbsc.dao.BbscDAO;
import com.project.zoopiter.domain.entity.Bbsc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BbscSVCImpl implements BbscSVC{

  private final BbscDAO bbscDAO;
  /**
   * 글 작성
   *
   * @param bbsc
   * @return 글번호
   */
  @Override
  public Long saveWrite(Bbsc bbsc) {
    return bbscDAO.saveWrite(bbsc);
  }

  /**
   * 목록
   *
   * @return
   */
  @Override
  public List<Bbsc> findAll() {
    return bbscDAO.findAll();
  }

  /**
   * 검색
   *
   * @param petType 펫태그(강아지,고양이,소동물,기타)
   * @return
   */
  @Override
  public List<Bbsc> findByPetType(String petType) {
    return bbscDAO.findByPetType(petType);
  }

  /**
   * 상세조회
   *
   * @param id 게시글 번호
   * @return
   */
  @Override
  public Bbsc findByBbscId(Long id) {
    return bbscDAO.findByBbscId(id);
  }

  /**
   * 삭제
   *
   * @param id 게시글 번호
   * @return 삭제건수
   */
  @Override
  public int deleteByBbscId(Long id) {
    return bbscDAO.deleteByBbscId(id);
  }

  /**
   * 수정
   *
   * @param id   게시글 번호
   * @param bbsc 수정내용
   * @return
   */
  @Override
  public void updateByBbscId(Long id, Bbsc bbsc) {

  }

  /**
   * 조회수 증가
   *
   * @param id 게시글 번호
   * @return
   */
  @Override
  public int increaseHitCount(Long id) {
    return bbscDAO.increaseHitCount(id);
  }
}
