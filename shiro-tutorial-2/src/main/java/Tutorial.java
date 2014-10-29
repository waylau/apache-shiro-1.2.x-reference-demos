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
	
	        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
	        SecurityManager securityManager = factory.getInstance();
	        SecurityUtils.setSecurityManager(securityManager);
	
	
	        // 获取当前执行用户:
	        Subject currentUser = SecurityUtils.getSubject();
	
	        // 做点跟 Session 相关的事
	        Session session = currentUser.getSession();
	        session.setAttribute("someKey", "aValue");
	        String value = (String) session.getAttribute("someKey");
	        if (value.equals("aValue")) {
	            log.info("Retrieved the correct value! [" + value + "]");
	        }
	
	        // 登录当前用户检验角色和权限
	        if (!currentUser.isAuthenticated()) {
	            UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
	            token.setRememberMe(true);
	            try {
	                currentUser.login(token);
	            } catch (UnknownAccountException uae) {
	                log.info("There is no user with username of " + token.getPrincipal());
	            } catch (IncorrectCredentialsException ice) {
	                log.info("Password for account " + token.getPrincipal() + " was incorrect!");
	            } catch (LockedAccountException lae) {
	                log.info("The account for username " + token.getPrincipal() + " is locked.  " +
	                        "Please contact your administrator to unlock it.");
	            }
	            // ... 捕获更多异常
	            catch (AuthenticationException ae) {
	                //无定义?错误?
	            }
	        }
	
	        //说出他们是谁:
	        //打印主要识别信息 (本例是 username):
	        log.info("User [" + currentUser.getPrincipal() + "] logged in successfully.");
	
	        //测试角色:
	        if (currentUser.hasRole("schwartz")) {
	            log.info("May the Schwartz be with you!");
	        } else {
	            log.info("Hello, mere mortal.");
	        }
	
	        //测试一个权限 (非（instance-level）实例级别)
	        if (currentUser.isPermitted("lightsaber:weild")) {
	            log.info("You may use a lightsaber ring.  Use it wisely.");
	        } else {
	            log.info("Sorry, lightsaber rings are for schwartz masters only.");
	        }
	
	        //一个(非常强大)的实例级别的权限:
	        if (currentUser.isPermitted("winnebago:drive:eagle5")) {
	            log.info("You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  " +
	                    "Here are the keys - have fun!");
	        } else {
	            log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
	        }
	
	        //完成 - 退出t!
	        currentUser.logout();
	
	        System.exit(0);
	    }
	}