package ru.tuxoft.secure;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

@Service
@Slf4j
public class TokenAuthenticationManager {

    static final String TOKEN_PREFIX = "Bearer ";

    static final String HEADER_STRING = "Authorization";

    static long MAXIMUM_LEEWAY = 15L;

    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    private JWTVerifier verifier;

    public TokenAuthenticationManager() {
    }

    @PostConstruct
    public void postConstruct() {
        try {
            this.getVerifier();
        } catch (Exception var2) {
            log.error(var2.getMessage(), var2);
        }

    }

    private synchronized JWTVerifier getVerifier() throws Exception {
        if(this.verifier == null) {
            PublicKey key = this.retrievePublicKeyFromCertsEndpoint();
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey)key, (RSAPrivateKey)null);
            this.verifier = JWT.require(algorithm).acceptLeeway(this.getAcceptLeeway()).build();
        }

        return this.verifier;
    }

    private String getRealmUrl() {
        return this.authServerUrl + "/realms/" + this.realm;
    }

    private String getRealmCertsUrl() {
        return this.getRealmUrl() + "/protocol/openid-connect/certs";
    }

    private PublicKey retrievePublicKeyFromCertsEndpoint() throws Exception {
        ObjectMapper om = new ObjectMapper();
        InputStream src = (new URL(this.getRealmCertsUrl())).openStream();
        Throwable var3 = null;

        PublicKey var13;
        try {
            Map certInfos = (Map)om.readValue(src, Map.class);
            List keys = (List)certInfos.get("keys");
            Map keyInfo = null;
            Iterator keyFactory = keys.iterator();

            String exponentBase64;
            while(keyFactory.hasNext()) {
                Map modulusBase64 = (Map)keyFactory.next();
                exponentBase64 = (String)modulusBase64.get("alg");
                if("RS256".equalsIgnoreCase(exponentBase64)) {
                    keyInfo = modulusBase64;
                    break;
                }
            }

            if(keyInfo == null) {
                keyFactory = null;
                return null;
            }

            KeyFactory keyFactory1 = KeyFactory.getInstance("RSA");
            String modulusBase641 = (String)keyInfo.get("n");
            exponentBase64 = (String)keyInfo.get("e");
            Base64.Decoder urlDecoder = Base64.getUrlDecoder();
            BigInteger modulus = new BigInteger(1, urlDecoder.decode(modulusBase641));
            BigInteger publicExponent = new BigInteger(1, urlDecoder.decode(exponentBase64));
            var13 = keyFactory1.generatePublic(new RSAPublicKeySpec(modulus, publicExponent));
        } catch (Throwable var23) {
            var3 = var23;
            throw var23;
        } finally {
            if(src != null) {
                if(var3 != null) {
                    try {
                        src.close();
                    } catch (Throwable var22) {
                        var3.addSuppressed(var22);
                    }
                } else {
                    src.close();
                }
            }

        }

        return var13;
    }

    public Authentication getAuthentication(@NonNull HttpServletRequest request) {
        String rawToken = request.getHeader(HEADER_STRING);
        if (rawToken == null) {
            rawToken = request.getParameter(HEADER_STRING);
        }

        if (rawToken == null) {
            this.printRequest(request);
            return null;
        } else {
            return this.getAuthentication(rawToken);
        }
    }

    private void printRequest(HttpServletRequest request) {
        try {
            StringBuilder e = new StringBuilder();
            Enumeration headerNames = request.getHeaderNames();

            while(headerNames.hasMoreElements()) {
                String headerName = (String)headerNames.nextElement();
                e.append(headerName);
                e.append(": ");
                Enumeration headers = request.getHeaders(headerName);

                while(headers.hasMoreElements()) {
                    String headerValue = (String)headers.nextElement();
                    e.append(headerValue);
                    e.append(" ");
                }

                e.append("; ");
            }

            if(request.getServletPath() == null || !request.getServletPath().equals("/health")) {
                this.log.trace("rawToken == null {} {}?{} HEADERS: {}", new Object[]{request.getMethod(), request.getRequestURL().toString(), request.getQueryString() != null?request.getQueryString():"", e.toString()});
            }
        } catch (Exception var7) {
            this.log.trace(var7.getMessage());
        }

    }


    public Authentication getAuthentication(@NonNull String rawToken) {
        String token = rawToken.replace(TOKEN_PREFIX, "");
        try {
            TokenAuthentication ex = this.processAuthentication(token);
            log.trace("token valid login: {}", ex.getName());
            return ex;
        } catch (Exception var4) {
            log.trace(var4.getMessage());
            if (var4 instanceof AuthenticationServiceException) {
                throw var4;
            } else {
                return null;
            }
        }
    }

    private TokenAuthentication processAuthentication(String token) throws AuthenticationException {
        DecodedJWT jwt;
        try {
            jwt = this.getVerifier().verify(token);
        } catch (Exception var4) {
            log.trace(var4.getMessage());
            throw new AuthenticationServiceException(var4.getMessage());
        }

        return this.buildFullTokenAuthentication(jwt);
    }

    private TokenAuthentication buildFullTokenAuthentication(DecodedJWT jwt) {
        String userName = ((Claim)jwt.getClaims().get("preferred_username")).asString();
        List roles = this.getRoles(((Claim)jwt.getClaims().get("realm_access")).asMap());
        String firstName = ((Claim)jwt.getClaims().get("given_name")).asString();
        String lastName = ((Claim)jwt.getClaims().get("family_name")).asString();
        String email = ((Claim)jwt.getClaims().get("email")).asString();
        if(userName != null && !userName.isEmpty()) {
            ArrayList authorities = new ArrayList();
            Iterator fullTokenAuthentication = roles.iterator();

            while(fullTokenAuthentication.hasNext()) {
                String r = (String)fullTokenAuthentication.next();
                authorities.add(new SimpleGrantedAuthority("ROLE_" + r));
            }

            TokenAuthentication fullTokenAuthentication1 = new TokenAuthentication(authorities, true, userName, firstName, lastName, email);
            return fullTokenAuthentication1;
        } else {
            throw new AuthenticationServiceException("Bad token");
        }
    }

    private List<String> getRoles(Map<String, Object> realmAccess) {
        return realmAccess == null?null:(List)realmAccess.get("roles");
    }

    private PublicKey getPublicKey(byte[] keyBytes, String algorithm) {
        PublicKey publicKey = null;

        try {
            KeyFactory e = KeyFactory.getInstance(algorithm);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            publicKey = e.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException var6) {
            log.error("Could not reconstruct the public key, the given algorithm could not be found.");
        } catch (InvalidKeySpecException var7) {
            log.error("Could not reconstruct the public key");
        }

        return publicKey;
    }


    public long getAcceptLeeway() {
        String maximumLeeway = System.getProperty("jwt.maximum.leeway");
        return maximumLeeway != null && !maximumLeeway.isEmpty()?Long.valueOf(maximumLeeway).longValue():MAXIMUM_LEEWAY;
    }

}
