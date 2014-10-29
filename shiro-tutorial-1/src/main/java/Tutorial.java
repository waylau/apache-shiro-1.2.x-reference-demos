import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tutorial {

    private static final transient Logger log = LoggerFactory.getLogger(Tutorial.class);

    public static void main(String[] args) {
        log.info("My First Apache Shiro Application");


		//1.
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");

		//2.
		SecurityManager securityManager = factory.getInstance();

		//3.
		SecurityUtils.setSecurityManager(securityManager);

        System.exit(0);
    }
}