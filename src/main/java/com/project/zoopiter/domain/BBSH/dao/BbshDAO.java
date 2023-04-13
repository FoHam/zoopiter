package com.project.zoopiter.domain.BBSH.dao;

import com.project.zoopiter.domain.entity.BBSH;

import java.util.List;
import java.util.Optional;

public interface BbshDAO {

  // 등록
  Long save(BBSH bbsh);

  // 조회
  Optional<BBSH> findById(Long bbshId);

  // 수정
  int update(Long bbshId, BBSH bbsh);

  // 삭제
  int delete(Long bbshId);

  // 목록
  List<BBSH> findAll();

  // 조회수
  int updateHit(Long bbshId);

}
