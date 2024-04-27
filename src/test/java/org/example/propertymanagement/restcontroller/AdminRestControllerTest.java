package org.example.propertymanagement.restcontroller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.security.auth.UserPrincipal;

import java.security.Principal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.example.propertymanagement.dto.request.MemberRequest;
import org.example.propertymanagement.dto.response.MemberResponse;
import org.example.propertymanagement.dto.response.PageResponse;
import org.example.propertymanagement.entity.Member;
import org.example.propertymanagement.entity.type.MemberStatus;
import org.example.propertymanagement.repo.MemberRepo;
import org.example.propertymanagement.service.AdminService;
import org.example.propertymanagement.service.impl.AdminServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AdminRestController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AdminRestControllerTest {
    @Autowired
    private AdminRestController adminRestController;

    @MockBean
    private AdminService adminService;

    @Test
    void testFindAll() {

        MemberRepo memberRepo = mock(MemberRepo.class);
        ArrayList<Member> content = new ArrayList<>();
        when(memberRepo.findAll(Mockito.<Specification<Member>>any(), Mockito.<Pageable>any()))
                .thenReturn(new PageImpl<>(content));
        AdminRestController adminRestController = new AdminRestController(new AdminServiceImpl(memberRepo));

        // Act
        PageResponse actualFindAllResult = adminRestController.findAll("Role", null, new UserPrincipal("principal"));

        // Assert
        verify(memberRepo).findAll(isA(Specification.class), (Pageable) isNull());
        assertEquals(0, actualFindAllResult.getPage());
        assertEquals(0L, actualFindAllResult.getSize());
        assertEquals(0L, actualFindAllResult.getTotalElement());
        assertEquals(1L, actualFindAllResult.getTotalPage());
        assertFalse(actualFindAllResult.isHasNext());
        assertFalse(actualFindAllResult.isHasPrevious());
        assertTrue(actualFindAllResult.isEmpty());
        assertTrue(actualFindAllResult.isFirst());
        assertTrue(actualFindAllResult.isLast());
        assertEquals(content, actualFindAllResult.getData());
    }

    /**
     * Method under test:
     * {@link AdminRestController#findAll(String, Pageable, Principal)}
     */
    @Test
    void testFindAll2() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
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

        ArrayList<Member> content = new ArrayList<>();
        content.add(member);
        MemberRepo memberRepo = mock(MemberRepo.class);
        when(memberRepo.findAll(Mockito.<Specification<Member>>any(), Mockito.<Pageable>any()))
                .thenReturn(new PageImpl<>(content));
        AdminRestController adminRestController = new AdminRestController(new AdminServiceImpl(memberRepo));

        // Act
        PageResponse actualFindAllResult = adminRestController.findAll("Role", null, new UserPrincipal("principal"));

        // Assert
        verify(memberRepo).findAll(isA(Specification.class), (Pageable) isNull());
        List<?> data = actualFindAllResult.getData();
        assertEquals(1, data.size());
        assertEquals("21654", ((MemberResponse) data.get(0)).getZip());
        assertEquals("42 Main St", ((MemberResponse) data.get(0)).getAddress());
        assertEquals("6625550144", ((MemberResponse) data.get(0)).getPhone());
        assertEquals("ACTIVE", ((MemberResponse) data.get(0)).getStatus());
        assertEquals("MD", ((MemberResponse) data.get(0)).getState());
        assertEquals("Name", ((MemberResponse) data.get(0)).getName());
        assertEquals("Oxford", ((MemberResponse) data.get(0)).getCity());
        assertEquals("jane.doe@example.org", ((MemberResponse) data.get(0)).getEmail());
        assertEquals(0, actualFindAllResult.getPage());
        assertEquals(1L, ((MemberResponse) data.get(0)).getId());
        assertEquals(1L, actualFindAllResult.getSize());
        assertEquals(1L, actualFindAllResult.getTotalElement());
        assertEquals(1L, actualFindAllResult.getTotalPage());
        assertFalse(actualFindAllResult.isEmpty());
        assertFalse(actualFindAllResult.isHasNext());
        assertFalse(actualFindAllResult.isHasPrevious());
        assertTrue(((MemberResponse) data.get(0)).getRoles().isEmpty());
        assertTrue(actualFindAllResult.isFirst());
        assertTrue(actualFindAllResult.isLast());
    }

    /**
     * Method under test:
     * {@link AdminRestController#findAll(String, Pageable, Principal)}
     */
    @Test
    void testFindAll3() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
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

        Member member2 = new Member();
        member2.setAddress("17 High St");
        member2.setCity("London");
        member2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        member2.setEmail("john.smith@example.org");
        member2.setId(2L);
        member2.setName("org.example.propertymanagement.entity.Member");
        member2.setOffers(new ArrayList<>());
        member2.setPassword("Password");
        member2.setPhone("8605550118");
        member2.setProperties(new ArrayList<>());
        member2.setRoles(new ArrayList<>());
        member2.setState("State");
        member2.setStatus(MemberStatus.INACTIVE);
        member2.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        member2.setZip("OX1 1PT");

        ArrayList<Member> content = new ArrayList<>();
        content.add(member2);
        content.add(member);
        MemberRepo memberRepo = mock(MemberRepo.class);
        when(memberRepo.findAll(Mockito.<Specification<Member>>any(), Mockito.<Pageable>any()))
                .thenReturn(new PageImpl<>(content));
        AdminRestController adminRestController = new AdminRestController(new AdminServiceImpl(memberRepo));

        // Act
        PageResponse actualFindAllResult = adminRestController.findAll("Role", null, new UserPrincipal("principal"));

        // Assert
        verify(memberRepo).findAll(isA(Specification.class), (Pageable) isNull());
        List<?> data = actualFindAllResult.getData();
        assertEquals(2, data.size());
        assertEquals("17 High St", ((MemberResponse) data.get(0)).getAddress());
        assertEquals("21654", ((MemberResponse) data.get(1)).getZip());
        assertEquals("42 Main St", ((MemberResponse) data.get(1)).getAddress());
        assertEquals("6625550144", ((MemberResponse) data.get(1)).getPhone());
        assertEquals("8605550118", ((MemberResponse) data.get(0)).getPhone());
        assertEquals("ACTIVE", ((MemberResponse) data.get(1)).getStatus());
        assertEquals("INACTIVE", ((MemberResponse) data.get(0)).getStatus());
        assertEquals("London", ((MemberResponse) data.get(0)).getCity());
        assertEquals("MD", ((MemberResponse) data.get(1)).getState());
        assertEquals("Name", ((MemberResponse) data.get(1)).getName());
        assertEquals("OX1 1PT", ((MemberResponse) data.get(0)).getZip());
        assertEquals("Oxford", ((MemberResponse) data.get(1)).getCity());
        assertEquals("State", ((MemberResponse) data.get(0)).getState());
        assertEquals("jane.doe@example.org", ((MemberResponse) data.get(1)).getEmail());
        assertEquals("john.smith@example.org", ((MemberResponse) data.get(0)).getEmail());
        assertEquals("org.example.propertymanagement.entity.Member", ((MemberResponse) data.get(0)).getName());
        assertEquals(0, actualFindAllResult.getPage());
        assertEquals(1L, ((MemberResponse) data.get(1)).getId());
        assertEquals(1L, actualFindAllResult.getTotalPage());
        assertEquals(2L, ((MemberResponse) data.get(0)).getId());
        assertEquals(2L, actualFindAllResult.getSize());
        assertEquals(2L, actualFindAllResult.getTotalElement());
        assertFalse(actualFindAllResult.isEmpty());
        assertFalse(actualFindAllResult.isHasNext());
        assertFalse(actualFindAllResult.isHasPrevious());
        assertTrue(((MemberResponse) data.get(0)).getRoles().isEmpty());
        assertTrue(((MemberResponse) data.get(1)).getRoles().isEmpty());
        assertTrue(actualFindAllResult.isFirst());
        assertTrue(actualFindAllResult.isLast());
    }

    /**
     * Method under test: {@link AdminRestController#update(long, MemberRequest)}
     */
    @Test
    void testUpdate() throws Exception {
        // Arrange
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
        when(adminService.update(anyLong(), Mockito.<MemberRequest>any())).thenReturn(member);

        MemberRequest memberRequest = new MemberRequest();
        memberRequest.setAddress("42 Main St");
        memberRequest.setCity("Oxford");
        memberRequest.setEmail("jane.doe@example.org");
        memberRequest.setName("Name");
        memberRequest.setPhone("6625550144");
        memberRequest.setState("MD");
        memberRequest.setStatus(MemberStatus.ACTIVE);
        memberRequest.setZip("21654");
        String content = (new ObjectMapper()).writeValueAsString(memberRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/admins/users/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(adminRestController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("User approved"));
    }

    /**
     * Method under test: {@link AdminRestController#approve(long)}
     */
    @Test
    void testApprove() throws Exception {
        // Arrange
        doNothing().when(adminService).approve(anyLong());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/admins/users/{id}/approve", 1L);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(adminRestController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link AdminRestController#approve(long)}
     */
    @Test
    void testApprove2() throws Exception {
        // Arrange
        doNothing().when(adminService).approve(anyLong());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/admins/users/{id}/approve", 1L);
        requestBuilder.contentType("https://example.org/example");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(adminRestController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
