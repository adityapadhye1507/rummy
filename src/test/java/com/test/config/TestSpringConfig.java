package com.test.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.web.util.ConstantsDao;

public class TestSpringConfig {
	public static void main(String[] args) 
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("WEB-INF/spring-config.xml");
          
        //OR this will also work
         
        //GameController controller = (GameController) context.getBean("gameController");
         
        //System.out.println(controller.login().getMessage());
        
        
        //controller.getWinner(request, response);
        
        
        
       
        ConstantsDao dao = (ConstantsDao) context.getBean("constantsDao");
        System.out.println(dao.fullSequence.get(0).toString());
        
        List<Integer> test = new ArrayList<Integer>();
        test.add(1);
        test.add(2);
        test.add(3);
        test.add(4);
        test.add(5);
        test.add(6);
        test.add(7);
        
        System.out.println(test);
        
        if(test.equals(dao.fullSequence.get(0))){
        	System.out.println("YES");
        }
        
        ((ClassPathXmlApplicationContext) context).close();
        
    }
}
