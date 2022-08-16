package api.v1.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class MemberDto {
    @Getter
    @AllArgsConstructor
    public static class Post{
        @NotBlank(message = "이름은 공백이 아니어야 합니다.")
        private String name;

        @NotBlank(message = "비밀번호는 공백이 아니어야 합니다.")
        private String password;

        @Pattern(regexp = "[mw]", message = "m : 남자, w : 여자 둘중 하나를 입력하세요")
        private String sex;

        @NotBlank(message = "비밀번호는 공백이 아니어야 합니다.")
        private String company_name;

        private int company_type;

        private int company_location;
    }

    @Getter
    @AllArgsConstructor
    public static class Patch{
        private long memberId;
        @NotBlank(message = "이름은 공백이 아니어야 합니다.")
        private String name;
        @Pattern(regexp = "[mw]", message = "m : 남자, w : 여자 둘중 하나를 입력하세요")
        private String sex;

        @NotBlank(message = "비밀번호는 공백이 아니어야 합니다.")
        private String company_name;

        private int company_type;

        private int company_location;

        public void setMemberId(long memberId){
            this.memberId = memberId;
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class response{
        private Long memberId;
        private String name;
        private String password;
        private String sex;
        private String company_name;
        private int company_type;
        private int company_location;
    }
}
