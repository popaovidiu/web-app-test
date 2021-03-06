package ro.sci.ems.cfg;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ro.sci.ems.dao.EmployeeDAO;
import ro.sci.ems.dao.db.JdbcTemplateEmployeeDAO;
import ro.sci.ems.mvc.SecurityFilter;

@Configuration
public class ApplicationConfiguration {


    @Bean
    public FilterRegistrationBean securityFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(createSecurityFilter());
        registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
        registration.addUrlPatterns("/*");
        return registration;
    }
	
    
    @Bean
    public SecurityFilter createSecurityFilter() {
    	return new SecurityFilter();
    }
    
//	@Bean
//	public DataSource dataSource() {
//		BasicDataSource dataSource = new BasicDataSource();
//		dataSource.setDriverClassName("org.postgresql.Driver");
//		
//		String url = new StringBuilder()
//				.append("jdbc:")
//				.append("postgresql")
//				.append("://")
//				.append("localhost")
//				.append(":")
//				.append("5432")
//				.append("/")
//				.append("ems").toString();
//		
//		dataSource.setUrl(url);
//		dataSource.setUsername("postgres");
//		dataSource.setPassword("q1w2e3r4");
//		return dataSource;
//		
//	}
	
    @Bean
    public BasicDataSource dataSource() {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        String username = System.getenv("JDBC_DATABASE_USERNAME");
        String password = System.getenv("JDBC_DATABASE_PASSWORD");

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(dbUrl);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);

        return basicDataSource;
    }
    
    
	@Bean
	public EmployeeDAO employeeDAO() {
		//return new IMEmployeeDAO();
		
		return new JdbcTemplateEmployeeDAO(dataSource());
		
		
		
//		return new JDBCEmployeeDAO("localhost", 
//				"5432", 
//				"ems", 
//				"sebi", 
//				"sebi");
	}
}
