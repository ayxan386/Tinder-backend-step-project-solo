package filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PeoplesFilter implements Filter {
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;

    if (req.getMethod().equalsIgnoreCase("POST")) {

      String id = request.getParameter("id");
      String liked = request.getParameter("liked");

      if (id != null && liked != null) {
        try {
          Integer.parseInt(id);
          Boolean.parseBoolean(liked);
          chain.doFilter(request, response);
          return;
        } catch (Exception e) {
//        e.printStackTrace();
        }
      }
      ((HttpServletResponse) response).sendRedirect("/users");
      return;
    }
    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {

  }
}
