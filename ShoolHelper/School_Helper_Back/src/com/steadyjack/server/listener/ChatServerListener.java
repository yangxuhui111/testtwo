package com.steadyjack.server.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class ChatServerListener
 *
 */
@WebListener
public class ChatServerListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public ChatServerListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
         ChatServer chatServer = new ChatServer();
         chatServer.invoke();
    }
	
}
