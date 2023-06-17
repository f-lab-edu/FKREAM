package com.flab.fkream.elasticsearch;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MemberSearchRepository extends ElasticsearchRepository<MemberEntity, Long> {

    List<MemberEntity> findByAge(int age);

    List<MemberEntity> findByNickname(String nickname, Pageable pageable);

    List<MemberEntity> searchByNickname(String nickname, Pageable pageable);

    List<MemberEntity> searchByName(String name, Pageable pageable);

}
