package br.com.compiladores;

import java.util.LinkedList;

public class Pilha<T>{
   
   private LinkedList<T> objetos = new LinkedList<T>();

   public void insere(T t) {
     this.objetos.add(t);
   }

   @SuppressWarnings("unchecked")
   public T remove() {
	  try{
		 return this.objetos.remove(this.objetos.size() - 1);
	  }catch (Exception e) {
		 e.getMessage();
		 return (T) "ERRO!!!!";
	  }
	  
   }

   public boolean vazia() {
     return this.objetos.size() == 0;
   }
   
   @SuppressWarnings("unchecked")
   public T topo() {
	  try{
		 return this.objetos.get(this.objetos.size()-1);
	  }catch (Exception e) {
		 e.getMessage();
		 return (T) "ERRO!!!!";
	  }
   }
   
   public void imprime(){
	  try{
		 if(!this.objetos.isEmpty() || this.objetos != null){
			for(int i=this.objetos.size()-1; i >= 0; i--)
			   System.out.println(this.objetos.get(i));
		 }
	  }catch (Exception e) {
		 e.getMessage();
		 System.out.println("pilha vazia");
	  }
   }
}
