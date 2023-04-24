package com.project.zoopiter.domain.BBSHReply.svc;

import com.project.zoopiter.domain.BBSHReply.dao.BBSHReplyDAO;
import com.project.zoopiter.domain.entity.BBSHReply;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BBSHReplySVCImpl implements BBSHReplySVC {

  private final BBSHReplyDAO bbshReplyDAO;

  /**
   * 댓글작성
   *
   * @param bbshReply
   * @return 댓글번호
   */
  @Override
  public Long save(BBSHReply bbshReply) {
    return bbshReplyDAO.save(bbshReply);
  }


  /**
   * 댓글목록
   *
   * @param bbshId 글 번호
   * @return
   */
  @Override
  public Optional<List<BBSHReply>> findByBBSHId(Long bbshId) {
    return bbshReplyDAO.findByBBSHId(bbshId);
  }

  /**
   * 댓글 수정
   *
   * @param bbshId    글번호
   * @param bbshReply
   * @return 수정건수
   */
  @Override
  public int updateByHcid(Long bbshId, BBSHReply bbshReply) {
    return bbshReplyDAO.updateByBcid(bbshId,bbshReply);
  }


  @Override
  public int deleteByHcid(Long hcId) {
    return bbshReplyDAO.deleteByBcid(hcId);
  }

  @Override
  public int countOfReplies(Long bbshId) {
    return bbshReplyDAO.countOfReplies(bbshId);
  }
}

