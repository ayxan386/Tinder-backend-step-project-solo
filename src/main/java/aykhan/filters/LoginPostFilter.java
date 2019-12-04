package aykhan.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginPostFilter implements Filter {
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;

    if (req.getMethod().equalsIgnoreCase("GET")) {
      chain.doFilter(request, response);
      return;
    }
    String email = req.getParameter("email");
    String pass = req.getParameter("password");
    if (email != null && pass != null && pass.length() > 0) {
      chain.doFilter(request, response);
      return;
    }
    ((HttpServletResponse) response).sendRedirect("/login");
  }

  @Override
  public void destroy() {

  }
}
