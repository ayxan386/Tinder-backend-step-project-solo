package aykhan.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MessagesGetFilter implements Filter {
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;

    if (req.getMethod().equalsIgnoreCase("POST"))
      chain.doFilter(request, response);

    String[] paths = req.getPathInfo().replace("/", "").split("/");
    if (paths.length > 0) {
      try {
        Integer.parseInt(paths[0]);
        chain.doFilter(request, response);
        return;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    ((HttpServletResponse) response).sendRedirect("/liked");
  }

  @Override
  public void destroy() {

  }
}
