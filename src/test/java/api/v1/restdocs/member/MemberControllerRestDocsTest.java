package api.v1.restdocs.member;

import api.v1.member.controller.MemberController;
import api.v1.member.dto.MemberDto;
import api.v1.member.entity.Member;
import api.v1.member.mapper.MemberMapper;
import api.v1.member.service.MemberService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class MemberControllerRestDocsTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;

    @MockBean
    private MemberService memberService;
    @MockBean
    private MemberMapper mapper;

    @Test
    public void postMemberTest() throws Exception {
        //given
        MemberDto.Post post = new MemberDto.Post("?????????","s4goodbye!","m",
                "??????????????????",5,1);
        String content = gson.toJson(post);
        MemberDto.response responseDto =
                new MemberDto.response(1L,"?????????","s4goodbye!","m",
                        "??????????????????",5,1);
        given(mapper.memberPostToMember(Mockito.any(MemberDto.Post.class))).willReturn(new Member());
        given(memberService.createMember(Mockito.any(Member.class))).willReturn(new Member());
        given(mapper.memberToMemberResponse(Mockito.any(Member.class))).willReturn(responseDto);

        //when
        ResultActions actions =
                mockMvc.perform(
                        post("/v1/members")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        //then
        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value(post.getName()))
                .andExpect(jsonPath("$.data.sex").value(post.getSex()))
                .andExpect(jsonPath("$.data.company_name").value(post.getCompany_name()))
                .andDo(document(
                        "post-member",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                List.of(
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("????????????"),
                                        fieldWithPath("sex").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("company_name").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("company_type").type(JsonFieldType.NUMBER).description("??????"),
                                        fieldWithPath("company_location").type(JsonFieldType.NUMBER).description("??????")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("data.password").type(JsonFieldType.STRING).description("????????????"),
                                        fieldWithPath("data.sex").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("data.company_name").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("data.company_type").type(JsonFieldType.NUMBER).description("??????"),
                                        fieldWithPath("data.company_location").type(JsonFieldType.NUMBER).description("??????")
                                )
                        )
                ));
    }

    @Test
    public void patchMemberTest() throws Exception{
        //given
        long memberId = 1L;
        MemberDto.Patch patch = new MemberDto.Patch(memberId,"?????????","m",
                "??????????????????",5,1);
        String content = gson.toJson(patch);

        MemberDto.response responseDto =
                new MemberDto.response(1L,"?????????","s4goodbye!","m",
                        "??????????????????",5,1);
        given(mapper.memberPatchToMember(Mockito.any(MemberDto.Patch.class))).willReturn(new Member());
        given(memberService.updateMember(Mockito.any(Member.class))).willReturn(new Member());
        given(mapper.memberToMemberResponse(Mockito.any(Member.class))).willReturn(responseDto);

        //when
        ResultActions actions =
                mockMvc.perform(
                        patch("/v1/members/{member-id}",memberId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.memberId").value(patch.getMemberId()))
                .andExpect(jsonPath("$.data.name").value(patch.getName()))
                .andExpect(jsonPath("$.data.company_name").value(patch.getCompany_name()))
                .andDo(document(
                        "patch-member",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                            parameterWithName("member-id").description("?????? ?????????")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("?????? ?????????").ignored(),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("??????").optional(),
                                        fieldWithPath("sex").type(JsonFieldType.STRING).description("??????").optional(),
                                        fieldWithPath("company_name").type(JsonFieldType.STRING).description("?????????").optional(),
                                        fieldWithPath("company_type").type(JsonFieldType.NUMBER).description("??????").optional(),
                                        fieldWithPath("company_location").type(JsonFieldType.NUMBER).description("??????").optional()
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("data.password").type(JsonFieldType.STRING).description("????????????"),
                                        fieldWithPath("data.sex").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("data.company_name").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("data.company_type").type(JsonFieldType.NUMBER).description("??????"),
                                        fieldWithPath("data.company_location").type(JsonFieldType.NUMBER).description("??????")
                                )
                        )
                ));
    }
    @Test
    public void getMemberTest() throws Exception{
        //given
        long memberId = 1L;
        MemberDto.response responseDto =
                new MemberDto.response(1L,"?????????","s4goodbye!","m",
                        "??????????????????",5,1);
        given(memberService.findMember(Mockito.anyLong())).willReturn(new Member());
        given(mapper.memberToMemberResponse(Mockito.any(Member.class))).willReturn(responseDto);

        //when
        ResultActions actions =
                mockMvc.perform(
                        get("/v1/members/{member-id}",memberId)
                                .accept(MediaType.APPLICATION_JSON)
                );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(document(
                        "get-member",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("member-id").description("?????? ?????????")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("data.password").type(JsonFieldType.STRING).description("????????????"),
                                        fieldWithPath("data.sex").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("data.company_name").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("data.company_type").type(JsonFieldType.NUMBER).description("??????"),
                                        fieldWithPath("data.company_location").type(JsonFieldType.NUMBER).description("??????")
                                )
                        )
                ));
    }


    @Test
    public void getMembersTest() throws Exception{
        //given
        Member member1 = new Member(1L,"?????????","s4goodbye!","m",
                "??????????????????",5,1);
        Member member2 = new Member(2L,"?????????","1q2w3e4r!","m",
                "???????????????",4,2);
        Member member3 = new Member(3L,"?????????","spring123@","w",
                "?????????",3,6);

        Page<Member> pageMembers = new PageImpl<>(List.of(member1,member2,member3), PageRequest.of(1,10, Sort.by("memberId").descending()),3);
        List<MemberDto.response> responses = List.of(
                new MemberDto.response(1L,"?????????","s4goodbye!","m",
                        "??????????????????",5,1),
                new MemberDto.response(2L,"?????????","1q2w3e4r!","m",
                        "???????????????",4,2),
                new MemberDto.response(3L,"?????????","spring123@","w",
                        "?????????",3,6)
        );

        given(memberService.findMembers(Mockito.anyInt(),Mockito.anyInt())).willReturn(new PageImpl<>(List.of()));
        given(mapper.membersToMemberResponses(Mockito.anyList())).willReturn(responses);

        String page = "1";
        String size = "10";
        MultiValueMap<String,String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page",page);
        queryParams.add("size",size);


        //when
        ResultActions actions =
                mockMvc.perform(
                        get("/v1/members")
                                .accept(MediaType.APPLICATION_JSON)
                                .params(queryParams)
                );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(document(
                        "get-members",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("page").description("?????????"),
                                parameterWithName("size").description("???????????? ?????? ???")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("?????? ?????????"),
                                        fieldWithPath("data[].memberId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data[].name").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("data[].password").type(JsonFieldType.STRING).description("????????????"),
                                        fieldWithPath("data[].sex").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("data[].company_name").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("data[].company_type").type(JsonFieldType.NUMBER).description("??????"),
                                        fieldWithPath("data[].company_location").type(JsonFieldType.NUMBER).description("??????"),

                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("????????? ??????"),
                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("?????? ?????? ???"),
                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("?????? ????????? ???")
                                )
                        )
                ));
    }

    @Test
    public void deleteMemberTest() throws Exception {
        //given
        long memberId = 1L;
        doNothing().when(memberService).deleteMember(Mockito.anyLong());

        //when
        ResultActions actions =
                mockMvc.perform(
                        delete("/v1/members/{member-id}",memberId)
                                .accept(MediaType.APPLICATION_JSON)
                );

        //then
        actions
                .andExpect(status().isNoContent())
                .andDo(document(
                        "delete-member",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("member-id").description("?????? ?????????")
                        )
                ));
    }
}
