package pl.swietek.springbootapi.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;
import pl.swietek.springbootapi.repositories.TokenRepository;
import pl.swietek.springbootapi.services.JWTService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTAuthFilter  extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    private void makeResponseUnAuth(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"message\": \"Unauthorized\"}");
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            if (request.getServletPath().contains("/api/v1/auth")) {
                filterChain.doFilter(request, response);
                return;
            }
            final String authenticationHeader = request.getHeader("Authorization");
            final String jwt;
            final String email;

            if (authenticationHeader == null || !authenticationHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            //  Token Extraction
            jwt = authenticationHeader.substring(7);
            email = this.jwtService.extractUsername(jwt);

            // Token Validation
            // Email was extracted and user is not logged in just yet
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
                var isTokenValid = tokenRepository.findByToken(jwt)
                        .map(t -> !t.isExpired() && !t.isRevoked())
                        .orElse(false);

                // Check if token is valid
                if (this.jwtService.isTokenValid(jwt, userDetails) && isTokenValid){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    // Update Security Context Holder
                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            this.makeResponseUnAuth(response);
        }
    }
}
