package org.example.propertymanagement.restcontroller;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.ArrayList;

import org.example.propertymanagement.dto.request.AuthRequest;
import org.example.propertymanagement.dto.request.ChangePasswordRequest;
import org.example.propertymanagement.dto.request.ForgotPasswordRequest;
import org.example.propertymanagement.dto.request.RegisterRequest;
import org.example.propertymanagement.dto.response.TokenResponse;
import org.example.propertymanagement.entity.Member;
import org.example.propertymanagement.entity.type.MemberStatus;
import org.example.propertymanagement.service.AuthService;
import org.example.propertymanagement.service.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AuthRestController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AuthRestControllerTest {
    @Autowired
    private AuthRestController authRestController;

    @MockBean
    private AuthService authService;

    @MockBean
    private MemberService memberService;

    @Test
    void testRegisterOwner() throws Exception {
        Member member = new Member();
        member.setAddress("42 Main St");
        member.setCity("Oxford");
        member.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        member.setEmail("jane.doe@example.org");
        member.setId(1L);
        member.setName("Name");
        member.setOffers(new ArrayList<>());
        member.setPassword("iloveyou");
        member.setPhone("6625550144");
        member.setProperties(new ArrayList<>());
        member.setRoles(new ArrayList<>());
        member.setState("MD");
        member.setStatus(MemberStatus.ACTIVE);
        member.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        member.setZip("21654");
        when(authService.registerOwner(Mockito.<RegisterRequest>any())).thenReturn(member);

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("jane.doe@example.org");
        registerRequest.setName("Name");
        registerRequest.setPassword("iloveyou");
        registerRequest.setRole("Role");
        registerRequest.setStatus(MemberStatus.ACTIVE);
        String content = (new ObjectMapper()).writeValueAsString(registerRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/auth/owner/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authRestController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"email\":\"jane.doe@example.org\",\"name\":\"Name\",\"city\":\"Oxford\",\"address\":\"42 Main St\",\"state\":"
                                        + "\"MD\",\"zip\":\"21654\",\"status\":\"ACTIVE\",\"phone\":\"6625550144\",\"roles\":[]}"));
    }

    @Test
    void testRegisterCustomer() throws Exception {

        Member member = new Member();
        member.setAddress("42 Main St");
        member.setCity("Oxford");
        member.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        member.setEmail("jane.doe@example.org");
        member.setId(1L);
        member.setName("Name");
        member.setOffers(new ArrayList<>());
        member.setPassword("iloveyou");
        member.setPhone("6625550144");
        member.setProperties(new ArrayList<>());
        member.setRoles(new ArrayList<>());
        member.setState("MD");
        member.setStatus(MemberStatus.ACTIVE);
        member.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        member.setZip("21654");
        when(authService.registerCustomer(Mockito.<RegisterRequest>any())).thenReturn(member);

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("jane.doe@example.org");
        registerRequest.setName("Name");
        registerRequest.setPassword("iloveyou");
        registerRequest.setRole("Role");
        registerRequest.setStatus(MemberStatus.ACTIVE);
        String content = (new ObjectMapper()).writeValueAsString(registerRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/auth/customer/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authRestController)
                .build()
                .perform(requestBuilder);


        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"name\":\"Name\",\"email\":\"jane.doe@example.org\",\"phone\":\"6625550144\",\"address\":\"42 Main"
                                        + " St\",\"city\":\"Oxford\",\"state\":\"MD\",\"zip\":\"21654\",\"password\":\"iloveyou\",\"roles\":[],\"status\":\"ACTIVE\","
                                        + "\"offers\":[],\"createdAt\":[1970,1,1,0,0],\"updatedAt\":[1970,1,1,0,0]}"));
    }


    @Test
    void testChangePassword() throws Exception {

        when(memberService.changePassword(Mockito.<ChangePasswordRequest>any())).thenReturn("iloveyou");

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setConfirmNewPassword("iloveyou");
        changePasswordRequest.setNewPassword("iloveyou");
        changePasswordRequest.setOldPassword("iloveyou");
        changePasswordRequest.setToken("ABC123");
        String content = (new ObjectMapper()).writeValueAsString(changePasswordRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/auth/change-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);


        MockMvcBuilders.standaloneSetup(authRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("iloveyou"));
    }


    @Test
    void testForgotPassword() throws Exception {

        when(memberService.forgotPassword(Mockito.<ForgotPasswordRequest>any())).thenReturn("iloveyou");

        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
        forgotPasswordRequest.setEmail("jane.doe@example.org");
        String content = (new ObjectMapper()).writeValueAsString(forgotPasswordRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/auth/forgot-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);


        MockMvcBuilders.standaloneSetup(authRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("iloveyou"));
    }

    @Test
    void testToken() throws Exception {

        TokenResponse buildResult = TokenResponse.builder().accessToken("ABC123").refreshToken("ABC123").build();
        when(authService.issueAccessToken(Mockito.<AuthRequest>any())).thenReturn(buildResult);

        AuthRequest authRequest = new AuthRequest();
        authRequest.setEmail("jane.doe@example.org");
        authRequest.setPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(authRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/auth/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        MockMvcBuilders.standaloneSetup(authRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"accessToken\":\"ABC123\",\"refreshToken\":\"ABC123\"}"));
    }
}
