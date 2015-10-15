package hello;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.*;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestValidator;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.*;

/**
 * Created by Eric on 10/15/2015.
 */
@Controller
public class TokenController {
    @Autowired
    private TokenEndpoint tokenEndpoint;




    @RequestMapping(value = "/oauth/lala", method= RequestMethod.GET)
    public ResponseEntity<OAuth2AccessToken> getAccessToken(){
        List<GrantedAuthority> list =  new ArrayList<GrantedAuthority>();
       list.add( new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "Blaat";
            }
        });
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("client_id", "clientapp");
        parameters.put("client_secret", "123456");
        parameters.put("grant_type", "password");
        parameters.put("password", "lol123");
        parameters.put("scope", "read write");
        parameters.put("username", "eric");

        try {
            ResponseEntity<OAuth2AccessToken> oAuth2AccessTokenResponseEntity = tokenEndpoint.postAccessToken(new UsernamePasswordAuthenticationToken("clientapp","123456",list ), parameters);
            System.out.println(oAuth2AccessTokenResponseEntity.getBody().getValue());
            return oAuth2AccessTokenResponseEntity;
        } catch (HttpRequestMethodNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
