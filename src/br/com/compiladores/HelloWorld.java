
package br.com.compiladores;


import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ViewScoped
@ManagedBean(name="hello")
public class HelloWorld{
   
   private String teste;
   @PostConstruct
   public void init() {
	  System.out.println(" Bean executado! ");
   }

   public String getMessage() {
	  return "";
   }
}
