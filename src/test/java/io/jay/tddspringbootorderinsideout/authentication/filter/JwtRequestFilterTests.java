package io.jay.tddspringbootorderinsideout.authentication.filter;

import io.jay.tddspringbootorderinsideout.authentication.domain.User;
import io.jay.tddspringbootorderinsideout.authentication.domain.UserRole;
import io.jay.tddspringbootorderinsideout.authentication.service.DefaultUserService;
import io.jay.tddspringbootorderinsideout.share.token.EndpointAccessTokenGenerator;
import io.jay.tddspringbootorderinsideout.share.token.JwtEndpointAccessTokenGenerator;
import io.jay.tddspringbootorderinsideout.share.token.JwtSecretKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JwtRequestFilterTests {

    private BCryptPasswordEncoder passwordEncoder;
    private MockHttpServletRequest mockRequest;
    private MockHttpServletResponse mockResponse;
    private MockFilterChain mockFilterChain;

    private User user;
    private UserDetailsService mockUserDetailsService;

    private JwtSecretKey jwtSecretKey;
    private JwtRequestFilter filterToTest;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
        mockRequest = new MockHttpServletRequest();
        mockResponse = new MockHttpServletResponse();
        mockFilterChain = new MockFilterChain();

        user = User.builder()
                .email("user@email.com")
                .password(passwordEncoder.encode("password"))
                .roles(Collections.singletonList(UserRole.ROLE_USER))
                .build();

        mockUserDetailsService = mock(DefaultUserService.class);

        jwtSecretKey = new JwtSecretKey("magicSecret");
        EndpointAccessTokenGenerator tokenGenerator = new JwtEndpointAccessTokenGenerator(jwtSecretKey);
        String tokenValue = "Bearer " + tokenGenerator.createAccessToken("user@email.com", Collections.singletonList(UserRole.ROLE_USER.name()));
        mockRequest.addHeader("Authorization", tokenValue);

        filterToTest = new JwtRequestFilter(mockUserDetailsService, jwtSecretKey);

        SecurityContextHolder.clearContext();
    }

    @Test
    void test_filter_continuesToNextFilter() throws ServletException, IOException {
        when(mockUserDetailsService.loadUserByUsername(anyString()))
                .thenReturn(user);
        MockFilterChain mockFilterChainSpy = spy(mockFilterChain);


        filterToTest.doFilter(mockRequest, mockResponse, mockFilterChainSpy);


        verify(mockFilterChainSpy, times(1)).doFilter(mockRequest, mockResponse);
    }

    @Test
    void test_filter_loadsUserFromToken() throws ServletException, IOException {
        when(mockUserDetailsService.loadUserByUsername(anyString()))
                .thenReturn(user);


        filterToTest.doFilter(mockRequest, mockResponse, mockFilterChain);


        ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockUserDetailsService, times(1)).loadUserByUsername(emailCaptor.capture());
        assertThat(emailCaptor.getValue(), equalTo("user@email.com"));
    }

    @Test
    void test_filter_setsAuthenticationInSecurityContext() throws ServletException, IOException {
        when(mockUserDetailsService.loadUserByUsername(anyString()))
                .thenReturn(user);


        filterToTest.doFilter(mockRequest, mockResponse, mockFilterChain);


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) authentication.getPrincipal();
        assertThat(principal.getEmail(), equalTo("user@email.com"));
        assertThat(principal.getRoles().get(0), equalTo(UserRole.ROLE_USER));
        assertThat(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")), equalTo(true));
    }

    @Test
    void test_filter_continuesToNextFilter_whenRequestHasNoToken() throws ServletException, IOException {
        MockFilterChain mockFilterChainSpy = spy(mockFilterChain);
        MockHttpServletRequest requestWithoutToken = new MockHttpServletRequest();


        filterToTest.doFilter(requestWithoutToken, mockResponse, mockFilterChainSpy);


        verify(mockFilterChainSpy, times(1)).doFilter(requestWithoutToken, mockResponse);
    }
}