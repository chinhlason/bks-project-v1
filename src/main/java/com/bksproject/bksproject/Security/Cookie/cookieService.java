package com.bksproject.bksproject.Security.Cookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

@Component
public class cookieService {
    public ResponseCookie createCookieWithJwt(String value) {
        ResponseCookie cookie = ResponseCookie.from("jwtCookie", value)
                .path("/api")
                .maxAge(cookieConstant.COOKIE_EXPIRATION)
                .httpOnly(true)
                .build();
        return cookie;
    }

    public ResponseCookie createCookieWithRefreshToken(String value) {
        ResponseCookie cookie = ResponseCookie.from("refreshTokenCookie", value)
                .path("/api")
                .maxAge(cookieConstant.COOKIE_EXPIRATION)
                .httpOnly(true)
                .build();
        return cookie;
    }

    public ResponseCookie clearJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from("jwtCookie", "")
                .path("/api")
                .maxAge(-1) // Đặt thời gian sống thành giá trị âm để xóa cookie
                .httpOnly(true)
                .build();
        return cookie;
    }

    public ResponseCookie clearRefreshTokenCookie() {
        ResponseCookie cookie = ResponseCookie.from("refreshTokenCookie", "")
                .path("/api")
                .maxAge(-1) // Đặt thời gian sống thành giá trị âm để xóa cookie
                .httpOnly(true)
                .build();
        return cookie;
    }

    private String getCookieValueByName(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    public String getJwtFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, "jwtCookie");
    }

    public String getRefreshTokenFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, "refreshTokenCookie");
    }
}
