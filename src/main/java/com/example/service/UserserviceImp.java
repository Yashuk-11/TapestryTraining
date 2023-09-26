package com.example.service;

import java.util.List;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.example.model.User;

public class UserserviceImp implements Userservice{

	 private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	 public UserserviceImp(Session session) {
        this.session = session;
	 }
	 
	 Session session = sessionFactory.openSession();

    @Override
    @CommitAfter
    public void insertUsers(List<User> users) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            for (User user : users) {
                session.save(user); // Use save instead of native SQL query
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace(); 
       }
    }
    
    @Override
    @CommitAfter
    public void DeleteUserByEmail(String email) {
    	try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User userToDelete = getUserByEmail(email);
            if (userToDelete != null) {
                session.delete(userToDelete);
            }
            transaction.commit();
    	}catch(Exception e) {
    		e.printStackTrace();    
    	}
    }
    
    @Override
    @CommitAfter
    public void updateUserByEmail(List<User> users) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            for (User user : users) {
                User existingUser = getUserByEmail(user.getEmail());
                if (existingUser != null) {
                    // Update the user fields
                    existingUser.setBdate(user.getBdate());
                    existingUser.setCnumber(user.getCnumber());
                    existingUser.setUrl(user.getUrl());
                    existingUser.setFilepath(user.getFilepath());                   
                    session.update(existingUser);
                }              
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
   
    @Override
    @CommitAfter
    public User getUserByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            return (User) session.createQuery("SELECT u FROM User u WHERE u.email = :email")
                .setParameter("email", email)
                .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return null;
    }
    
    @Override
    @CommitAfter
    public String checkCredentials(String email, String password) {
        User user = getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user.getName(); 
        }
        return null;
    }
}
