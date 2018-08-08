package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import professor.Professor;


public class SessionFilter implements Filter {
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		System.out.println("TESTE");
		HttpSession session = ((HttpServletRequest)request).getSession();
		Professor professor = (Professor)session.getAttribute("professor");

		if(professor==null){
			System.out.println("REDIRECIONANDO...");
			//http://localhost:8080/horarioaula/faces/professor/lista.xhtml
			((HttpServletResponse)response).sendRedirect(((HttpServletRequest)request).getContextPath()+"/faces/login/login.xhtml");
		}else{

			if(((HttpServletRequest) request).getRequestURI().contains("professor/lista.xhtml") && !professor.isAdministrador()){
				((HttpServletResponse)response).sendRedirect(((HttpServletRequest)request).getContextPath()+"/faces/professor/indisponibilidade.xhtml");	
			}
			
			chain.doFilter(request, response);
		}

	}

	@Override
	public void init(FilterConfig fc) throws ServletException {

	}
}
