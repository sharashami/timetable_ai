package filters;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter(servletNames = { "Faces Servlet" })
public class JPAFilter implements Filter {
   private EntityManagerFactory entityManagerFactory;

   @Override
   public void destroy() {
       this.entityManagerFactory.close();
   }

   //http://programandojava.wordpress.com/2012/06/06/crud-parte2/?relatedposts_exclude=584
	 //  http://dev.mysql.com/downloads/file.php?id=414474
   @Override
   public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc)
           throws IOException, ServletException {
	   
       EntityManager entityManager = this.entityManagerFactory.createEntityManager();
       req.setAttribute("entityManager", entityManager);

	   entityManager.getTransaction().begin();

	   fc.doFilter(req, res);
       try {

    	   if(entityManager.getTransaction().isActive() && !entityManager.getTransaction().getRollbackOnly())
    		   entityManager.getTransaction().commit();
       } catch (Throwable e) {
    	   if(entityManager.getTransaction().isActive() && !entityManager.getTransaction().getRollbackOnly())
    		   entityManager.getTransaction().rollback();
    	   throw new ServletException(e);
       }finally {
           entityManager.close();
       }
   }

   @Override
   public void init(FilterConfig fc) throws ServletException {
       this.entityManagerFactory = Persistence
               .createEntityManagerFactory("horario");
   }
}
