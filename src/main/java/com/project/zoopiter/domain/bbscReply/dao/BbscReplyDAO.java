package com.project.zoopiter.domain.bbscReply.dao;

import com.project.zoopiter.domain.entity.BbscReply;

import java.util.List;
import java.util.Optional;

public interface BbscReplyDAO {
  /**
   * 댓글작성
   * @param bbscReply
   * @return  댓글번호
   */
  Long save(BbscReply bbscReply);


  /**
   * 댓글목록
   * @param bbscId 글 번호
   * @return
   */
  Optional<List<BbscReply>> findByBbscId(Long bbscId);



  /**
   * 댓글 수정
   * @param bbscId 글번호
   * @param bbscReply
   * @return 수정건수
   */
  int updateByCcid(Long bbscId, BbscReply bbscReply);


  /**
   * 삭제
   * @param ccId 댓글번호
   * @return 삭제건수
   */
  int deleteByCcid(Long ccId);

  /**
   * 댓글 갯수
   * @param bbscId 게시글 번호
   * @return 댓글 갯수
   */
  int countOfReplies(Long bbscId);

}
