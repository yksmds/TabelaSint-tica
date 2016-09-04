package br.com.compiladores;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Registro{
   
   private String producao;
   private String variavel;
   private Map<String,String> producoes;
   private List<String> listProducoes;
   
   public Registro() {

   }
   
   public Registro(String variavel, Map<String, String> producoes) {
	  this.variavel = variavel;
	  this.producoes = producoes;
   }
   
   public String getProducao() {
      return producao;
   }

   public void setProducao(String producao) {
      this.producao=producao;
   }

   public String getVariavel() {
      return variavel;
   }

   public void setVariavel(String variavel) {
      this.variavel=variavel;
   }

   public Map<String, String> getProducoes() {
      return producoes;
   }

   public void setProducoes(Map<String, String> producoes) {
      this.producoes=producoes;
   }
   
   public String getProducao(String simbolo){
	  return producoes.get(simbolo);
   }

   public List<String> getListProducoes() {
	  if(listProducoes == null)
		 listProducoes = new ArrayList<String>();
      return listProducoes;
   }

   public void setListProducoes(List<String> listProducoes) {
      this.listProducoes=listProducoes;
   }
   
   
   
}
