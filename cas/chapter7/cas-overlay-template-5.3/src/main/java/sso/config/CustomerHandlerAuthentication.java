package sso.config;

import org.apereo.cas.authentication.*;
import org.apereo.cas.authentication.handler.support.AbstractPreAndPostProcessingAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import sso.entity.CustomCredential;
import sso.entity.User;

import javax.security.auth.login.AccountException;
import javax.security.auth.login.FailedLoginException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author wutao
 * @email wutao56789@gmail.com
 * @date 2019/11/3 17:36
 * 自定义认证策略
 */
public class CustomerHandlerAuthentication extends AbstractPreAndPostProcessingAuthenticationHandler {

    public CustomerHandlerAuthentication(String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order) {
        super(name, servicesManager, principalFactory, order);
    }

    @Override
    protected AuthenticationHandlerExecutionResult doAuthentication(Credential credential) throws GeneralSecurityException, PreventedException {
        CustomCredential customCredential = (CustomCredential) credential;

        String username = customCredential.getUsername();
        String password = customCredential.getPassword();
        String email = customCredential.getEmail();
        String telephone = customCredential.getTelephone();

        System.out.println("username : " + username);
        System.out.println("password : " + password);
        System.out.println("email : " + email);
        System.out.println("telephone : " + telephone);


        // JDBC模板依赖于连接池来获得数据的连接，所以必须先要构造连接池
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/cas");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");

        // 创建JDBC模板
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);

        String sql = "SELECT * FROM user WHERE username = ?";

        User info = (User) jdbcTemplate.queryForObject(sql, new Object[]{username}, new BeanPropertyRowMapper<>(User.class));

        System.out.println("database username : "+ info.getUserName());
        System.out.println("database password : "+ info.getPassword());


        if (info == null) {
            throw new AccountException("Sorry, username not found!");
        }

        if (!info.getPassword().equals(password)) {
            throw new FailedLoginException("Sorry, password not correct!");
        } else {

            final List<MessageDescriptor> list = new ArrayList<>();

            return createHandlerResult(customCredential,
                    this.principalFactory.createPrincipal(username, Collections.emptyMap()), list);
        }
    }

    @Override
    public boolean supports(Credential credential) {
        return credential instanceof CustomCredential;
    }
}
