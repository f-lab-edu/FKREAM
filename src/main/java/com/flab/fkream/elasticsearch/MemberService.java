package com.flab.fkream.elasticsearch;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberSearchRepository memberSearchRepository;
    private final MemberSearchQueryRepository memberSearchQueryRepository;

    @Transactional
    public void saveAllMember(List<MemberEntity> memberDocumentList) {
        memberSearchRepository.saveAll(memberDocumentList);
    }

    public void saveMember(MemberEntity member) {
        memberSearchRepository.save(member);
    }

    public List<MemberEntity> findByNickname(String nickname, Pageable pageable) {
        return memberSearchRepository.findByNickname(nickname, pageable);
    }

    public List<MemberEntity> searchByName(String name, Pageable pageable) {
        return memberSearchRepository.searchByName(name, pageable);
    }

    public List<MemberEntity> findByAge(int age) {
        return memberSearchRepository.findByAge(age);
    }

    public List<MemberEntity> searchByCondition(SearchCondition searchCondition,
        Pageable pageable) {
        return memberSearchQueryRepository.findByCriteria(searchCondition, pageable);
    }

    public void delete(Long id) {
        memberSearchRepository.deleteById(id);
    }
}
