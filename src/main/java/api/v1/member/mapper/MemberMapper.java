package api.v1.member.mapper;

import api.v1.member.dto.MemberDto;
import api.v1.member.entity.Member;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member memberPostToMember(MemberDto.Post post);
    Member memberPatchToMember(MemberDto.Patch patch);
    MemberDto.response memberToMemberResponse(Member member);
    List<MemberDto.response> membersToMemberResponses(List<Member> members);
}
