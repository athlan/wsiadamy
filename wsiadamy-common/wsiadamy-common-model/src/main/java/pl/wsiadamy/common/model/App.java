package pl.wsiadamy.common.model;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import pl.wsiadamy.common.model.bo.UserBO;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        ApplicationContext appContext = 
          	  new ClassPathXmlApplicationContext("config/BeanLocations.xml");
        
        UserBO userBo = (UserBO) appContext.getBean("userBO");
        
        User user = new User();
        user.setName("testuser");
        
        userBo.save(user);
        System.out.println(user);
    }
}
