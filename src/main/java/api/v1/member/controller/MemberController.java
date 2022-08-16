package api.v1.member.controller;

import api.v1.member.dto.MultiResponseDto;
import api.v1.member.dto.SingleResponseDto;
import api.v1.member.dto.MemberDto;
import api.v1.member.entity.Member;
import api.v1.member.mapper.MemberMapper;
import api.v1.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@RestController
@Validated
@RequestMapping("v1/members")
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper mapper;

    public MemberController(MemberService memberService, MemberMapper mapper){
        this.memberService = memberService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post requestBody){
        Member member = mapper.memberPostToMember(requestBody);
        Member createdMember = memberService.createMember(member);
        MemberDto.response response = mapper.memberToMemberResponse(createdMember);

        return new ResponseEntity<>(
                new SingleResponseDto(response),
                HttpStatus.CREATED);
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@PathVariable("member-id") @Positive long memberId,
                                      @Valid @RequestBody MemberDto.Patch requestBody){
        requestBody.setMemberId(memberId);
        Member member = memberService.updateMember(mapper.memberPatchToMember(requestBody));
        MemberDto.response response = mapper.memberToMemberResponse(member);

        return new ResponseEntity<>(
                new SingleResponseDto<>(response),HttpStatus.OK
        );
    }

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") @Positive long memberId){
        Member member = memberService.findMember(memberId);
        MemberDto.response response = mapper.memberToMemberResponse(member);
        return new ResponseEntity<>(
                new SingleResponseDto<>(response),
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity getMembers(@Positive @RequestParam int page,
                                     @Positive @RequestParam int size){
        Page<Member> pageMembers = memberService.findMembers(page -1,size);
        List<Member> members = pageMembers.getContent();
        List<MemberDto.response> responses = mapper.membersToMemberResponses(members);
        return new ResponseEntity<>(
                new MultiResponseDto<>(responses,pageMembers),
                        HttpStatus.OK
        );
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") @Positive long memberId){
        memberService.deleteMember(memberId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
