package cn.shiwensama.token;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author: shiwensama
 * @create: 2020-04-18
 * @description:
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class JwtToken extends UsernamePasswordToken implements AuthenticationToken {

    private static final long serialVersionUID = 1L;
    /**
     * token
     */
    private String token;

    /**
     * 用户类型
     */
    private Integer state;

    public JwtToken(final String username, final String password,
                    final Integer state) {
        super(username, password);
        this.state = state;
    }

    public JwtToken(String token) {
        this.token = token;
    }


    @Override
    public Object getPrincipal() {
        return null;
    }


    @Override
    public Object getCredentials() {
        return null;
    }
}
