package com.project.zoopiter.domain.bbscReply.svc;

import com.project.zoopiter.domain.bbscReply.dao.BbscReplyDAO;
import com.project.zoopiter.domain.entity.BbscReply;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BbscReplySVCImpl implements BbscReplySVC{

  private final BbscReplyDAO bbscReplyDAO;

  /**
   * 댓글작성
   *
   * @param bbscReply
   * @return 댓글번호
   */
  @Override
  public Long save(BbscReply bbscReply) {
    return bbscReplyDAO.save(bbscReply);
  }

  /**
   * 댓글목록
   *
   * @param bbscId 글 번호
   * @return
   */
  @Override
  public Optional<List<BbscReply>> findByBbscId(Long bbscId) {
    return bbscReplyDAO.findByBbscId(bbscId);
  }

  /**
   * 댓글 수정
   *
   * @param bbscId    글번호
   * @param bbscReply
   * @return 수정건수
   */
  @Override
  public int updateByCcid(Long bbscId, BbscReply bbscReply) {
    return bbscReplyDAO.updateByCcid(bbscId,bbscReply);
  }

  /**
   * 삭제
   *
   * @param ccId 댓글번호
   * @return 삭제건수
   */
  @Override
  public int deleteByCcid(Long ccId) {
    return bbscReplyDAO.deleteByCcid(ccId);
  }

  /**
   * 댓글 갯수
   *
   * @param bbscId 게시글 번호
   * @return 댓글 갯수
   */
  @Override
  public int countOfReplies(Long bbscId) {
    return bbscReplyDAO.countOfReplies(bbscId);
  }
}
