package com.flab.fkream.elasticsearch;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
@Log4j2
public class MemberController {

    private final MemberService memberService;


    @PostMapping("/members")
    public ResponseEntity<Void> saveAll(@RequestBody List<MemberEntity> memberSaveAllRequest) {
        memberService.saveAllMember(memberSaveAllRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/memberDocuments")
    public void saveMemberDocuments(MemberEntity member) {
        memberService.saveMember(member);
    }

    @GetMapping("/members/age")
    public void searchByName(@RequestParam int age) {
        List<MemberEntity> byAge = memberService.findByAge(age);
        log.info(byAge);
    }


    @GetMapping("/members/nickname")
    public void searchByNickname(@RequestParam String nickname, Pageable pageable) {
        List<MemberEntity> byNickname = memberService.findByNickname(nickname, pageable);
        log.info(byNickname);
    }

    @GetMapping("/members/name")
    public void searchByName(@RequestParam String name, Pageable pageable) {
        List<MemberEntity> byNickname = memberService.searchByName(name, pageable);
        log.info(byNickname.size());
        log.info(byNickname);
    }


    @GetMapping("/members")
    public void searchByCondition(SearchCondition searchCondition, Pageable pageable) {
        List<MemberEntity> memberEntities = memberService.searchByCondition(searchCondition,
            pageable);
        System.out.println("memberEntities = " + memberEntities);
        log.info(memberEntities);
    }

    @DeleteMapping("/members")
    public void deleteMember(Long id) {

    }
}
