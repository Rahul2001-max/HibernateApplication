package com;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;



public class DemoMain {
	private static SessionFactory factory;
	static {
	    try {
	        factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	    } catch (Throwable ex) {
	        throw new ExceptionInInitializerError(ex);
	    }
	}
	public static void main(String[] args) {
		
		Emp emp1 = new Emp(11,"dubey");
		System.out.println(emp1.getName());
		System.out.println(emp1.getId());
		
		
		
		
		// saving object in DB ( create)
		Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(emp1);
        tx.commit();
       
    	// List all data from DB (retrieve) 
        List<Emp> list = session.createQuery("FROM Emp", Emp.class).list();
        
        for (Emp emp : list) {
			System.out.println(emp.getName()+", "+ emp.getId());
			
		}
        
        
    	// Get Emp by id from DB (retrieve) 
        String name = session.get(Emp.class,11).getName();
        System.out.println(name);
        
        
        
     // Update Emp details by id from DB (update) 
       if(session.get(Emp.class, 11) != null)
       {
    	   try {
    		   tx = session.beginTransaction();
    		   Emp obj = session.get(Emp.class, 11); // id 11 data needs to be changed.
        	   obj.setName("rohan");
        	   session.update(obj);
        	   tx.commit();
    	   }
    	   catch(IllegalStateException e)
    	   {
    		   e.printStackTrace();
    	   }
    	 
       }
        
       //--------------------after updated data------------------
        name = session.get(Emp.class,11).getName();
        System.out.println(name);
        
        
     // delete Emp by id from DB (delete) 
        
        if(session.get(Emp.class, 11) != null)
        {
     	   try {
     		   tx = session.beginTransaction();
     		   Emp obj = session.get(Emp.class, 11);
         	   
         	   session.delete(obj);
         	   tx.commit();
     	   }
     	   catch(IllegalStateException e)
     	   {
     		   e.printStackTrace();
     	   }
     	 
        }
        
        
        //--------------------------------list-----------------------------
        List<Emp> list1 = session.createQuery("FROM Emp", Emp.class).list();
        System.out.println("After delete");
        for (Emp emp : list1) {
			System.out.println(emp.getName()+", "+ emp.getId());
			
		}
        
        session.close();
        
        
		
	}
}
