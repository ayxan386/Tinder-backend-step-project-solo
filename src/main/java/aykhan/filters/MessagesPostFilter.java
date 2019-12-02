package aykhan.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MessagesPostFilter implements Filter {
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;

    if (req.getMethod().equalsIgnoreCase("GET"))
      chain.doFilter(request, response);

    String receiver = req.getParameter("receiver");
    String message = req.getParameter("message");
    if (receiver != null && message != null && message.length() > 0)
      try {
        Integer.parseInt(receiver);
        chain.doFilter(request, response);
        return;
      } catch (Exception e) {
        e.printStackTrace();
      }

    ((HttpServletResponse) response).sendRedirect("/liked");
  }

  @Override
  public void destroy() {

  }
}
