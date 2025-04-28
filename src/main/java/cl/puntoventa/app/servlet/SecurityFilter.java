/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cl.puntoventa.app.servlet;


import cl.puntoventa.app.entity.Usuarios;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecurityFilter implements Filter {

    private static String URL_LOGIN = "/login.hsm";
    private static String URL_ROOT_DESARROLLO = "/puntoventa/";
    private static String URL_HOME = "/view/index.hsm";
    private static String URL_ROOT = "/puntoventa/";

    private List<String> excludeUrl;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String excludePattern = filterConfig.getInitParameter("excludePatterns");
        this.excludeUrl = new ArrayList<>(Arrays.asList(excludePattern.split(",")));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        
        request.setCharacterEncoding("UTF-8");
        
        Usuarios sgaUsers = (Usuarios) httpServletRequest.getSession().getAttribute("userSession");
        
        String url = httpServletRequest.getRequestURI();
        
        String path = httpServletRequest.getServletPath();
        
        if(sgaUsers != null){
            if(url.contains(URL_LOGIN) || url.endsWith(URL_ROOT) || url.endsWith(URL_ROOT_DESARROLLO) || url.contains("error")){
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + URL_HOME);
            }else{
                filterChain.doFilter(request, response);
            }

        }else{
            String[] paths = path.split("/");
            
            if(!url.contains(URL_LOGIN)
                    && !url.endsWith("/")
                    && !excludeUrl.contains(paths[1])){
                System.out.println("Redirecciono a login");
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + URL_LOGIN + "?error=true&faces-redirect=true");
            }else{
                filterChain.doFilter(request, response);
            }
        }
        
    }
}

