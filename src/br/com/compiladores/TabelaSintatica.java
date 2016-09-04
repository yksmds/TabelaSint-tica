package br.com.compiladores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.CellEditEvent;



@ViewScoped
@ManagedBean(name="tabelaSintatica")
public class TabelaSintatica implements Serializable{
   
   /**
    * 
    */
   private static final long serialVersionUID=1L;
   private String simbolo;
   private String variavel;
   private String sentenca;
   private List<String> simbolos;
   private List<String> variaveis;
   
   private List<Registro> registros;
   
   public String getSimbolo() {
      return simbolo;
   }

   public void setSimbolo(String simbolo) {
      this.simbolo=simbolo;
   }

   public String getVariavel() {
      return variavel;
   }

   public void setVariavel(String variavel) {
      this.variavel=variavel;
   }

   public List<String> getSimbolos() {
	  if (simbolos == null)
		 simbolos = new ArrayList<String>();
      return simbolos;
   }

   public void setSimbolos(List<String> simbolos) {
      this.simbolos=simbolos;
   }

   public List<String> getVariaveis() {
	  if(variaveis == null){
		 variaveis = new ArrayList<String>();
	  }
      return variaveis;
   }

   public void setVariaveis(List<String> variaveis) {
      this.variaveis=variaveis;
   }
   
   public List<Registro> getRegistros() {
	  if (registros == null) {
		 registros = new ArrayList<Registro>();
	  }
      return registros;
   }

   public void setRegistros(List<Registro> registros) {
      this.registros=registros;
   }
   
   public String getSentenca() {
      return sentenca;
   }

   public void setSentenca(String sentenca) {
      this.sentenca=sentenca;
   }

   public void quebraSimbolos() {
	  simbolo = simbolo.trim();
	  String[] simbolos=simbolo.split(";");
	  for(String s : simbolos) {
		 getSimbolos().add(s);
	  }
	  getSimbolos().add("$");
   }
   
   public void quebraVariaveis(){
	  variavel = variavel.trim();
	  String[] vars=variavel.split(";");
	  for(String s : vars) {
		 getVariaveis().add(s);
	  }
   }
   
   public List<String> listaQuebrada(String s){
	  List<String> lista = new ArrayList<String>();
	  String[] array = s.split("");
	  for (String aux : array){
		 lista.add(aux);
	  }
	  lista.add("$");
	  return lista;
   }
   
   public int getSimbolosCount(){
	  return getSimbolos().size();
   }
   
   public void montaEstruturaTabela(){
	  quebraVariaveis();
	  quebraSimbolos();
	  for(int i=0; i < getVariaveis().size(); i++) {
		 Registro linha=new Registro(getVariaveis().get(i), addProducao());
		 for (int j=0; j < simbolos.size(); j++){
			linha.getListProducoes().add("");
		 }
		 getRegistros().add(linha);
	  }
   }
   
   private Map<String, String> addProducao() {
	  Map<String, String> stats=new LinkedHashMap<String, String>();
	  for(int i=0; i < simbolos.size(); i++) {
		stats.put(simbolos.get(i) , "");
	  }
	  return stats;
   }
   
   @SuppressWarnings("unused")
   public void onCellEdit(CellEditEvent event) {
	  Object oldValue=event.getOldValue();
	  Object newValue=event.getNewValue();
	  
	  int linha=event.getRowIndex();
	  
	  if(newValue != null && !newValue.equals(oldValue)) {
		 FacesMessage msg=new FacesMessage(FacesMessage.SEVERITY_INFO, "Celula Alterada", "Valor Antigo: " + oldValue + ", Novo Valor:" + newValue);
		 FacesContext.getCurrentInstance().addMessage(null , msg);
	  }
	  
	  int cont = 0;
	  boolean alterou = false;
	  for (Registro linhaTabela : getRegistros()){
		 if(linha == cont){ //verifico se a linha (variavel) que irei alterar a producao é o mesmo que o usuário escolheu.
			for(String l : linhaTabela.getListProducoes()){
			   	
			   Set<String> keys = linhaTabela.getProducoes().keySet(); //pega CHAVES
			   String[] arreyKeys = keys.toArray(new String[keys.size()]);
			   
			   for (int i=0; i < arreyKeys.length; i++){
			       if (colunaDaImagem.equals(arreyKeys[i])){
			    	  oldValue = linhaTabela.getProducoes().get(arreyKeys[i]);
			    	  linhaTabela.getProducoes().put(arreyKeys[i] , (String) newValue);
			    	  alterou = true;
			    	  break;
			       }
			    }
			   getRegistros().get(linha).setProducao(linhaTabela.getProducao());
			  
			   if(alterou)
				  break;
			 }
		 }
		 else if(alterou){
			break;
		 }cont++;
	  }
   }
   
   private String colunaDaImagem;
   public void pegaColuna(String d) {
	  setColunaDaImagem(d);
   }
   public String getColunaDaImagem() {
	  return colunaDaImagem;
   }
   public void setColunaDaImagem(String colunaDaImagem) {
	  this.colunaDaImagem=colunaDaImagem;
   }
   
   public void analisar() {
	  int index = 0;
	  Pilha<String> pilha = new Pilha<String>();
	  pilha.insere("$");
	  pilha.insere(getVariaveis().get(index));
	  sentenca = sentenca+"<$>";
	  sentenca = sentenca.trim();
	  String[] sentencaSeparada = trimNoArray(sentenca);
	  
	  boolean trocaToken = false;
	  for(String token : sentencaSeparada){
		 trocaToken = false;
		 
		 while(!trocaToken){
			 if(verificarTopoVariavel(pilha.topo())){
				for (Registro linha : getRegistros()){
				   if(linha.getVariavel().equals(pilha.topo())){
					  String producao = linha.getProducoes().get(token);
					  if (producao == null || "".equals(producao)) {
						FacesMessage msg=new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO!", "Sentença Inválida, ou não existe Produção para o símbolo " + token);
						FacesContext.getCurrentInstance().addMessage(null , msg);
						sentenca = null;
						return;
					  }
					  String[] producaoArray = trimNoArray(producao);
					  producaoArray = inverteArray(producaoArray);
					  pilha.remove();
					  for (String aux : producaoArray){
						 pilha.insere(aux);
					  }
					  System.out.println("========= PILHA ========");
					  pilha.imprime();
					  System.out.println("========= ***** ========\n");
					  break;
				   }
				   else
					  continue;
				}
			 }

			 // TOPO = TOKEN # $
			 if(!token.equals("$") && pilha.topo().equals(token)){
				pilha.remove();
				trocaToken = true;
				break;
			 }
			 
			 // TOPO = TOKEN = $
			 if(token.equals("$") && pilha.topo().equals(token)){
				FacesMessage msg=new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCESSO!", "Fim da Análise");
				FacesContext.getCurrentInstance().addMessage(null , msg);
				sentenca = null;
				trocaToken = true;
				break;
			 }
			 
			 // TOPO = TERMINAL
			 if(verificarTopoTerminal(pilha.topo())){
				pilha.remove();
				trocaToken = true;
				break;
			 }
			 
			 //TOPO == &
			 if(pilha.topo().equals("&")){
				pilha.remove();
				trocaToken = false;
			 }
			 
			 //SE DEVE OU NAO PASSAR PARA O PRÓXIMO TOKEN
			 if(trocaToken)
				break;
		 }

	  }
	  
   }
   
   private boolean verificarTopoVariavel(String topo) {
	  boolean r = false;
	  for (String s : getVariaveis()){
		 if(topo.equals(s)){
			r = true;
			break;
		 }
		 else
			r = false;
	  }
	  return r;
   }
   
   private boolean verificarTopoTerminal(String topo) {
	  boolean r = false;
	  for (String s : getSimbolos()){
		 if(topo.equals(s)){
			r = true;
			break;
		 }
		 else
			r = false;
	  }
	  return r;
   }
   
   public String[] inverteArray(String[] array){
	  int tamanho = array.length;
	  String[] arrayAux = new String[tamanho];

	  for (int i = 0; i < arrayAux.length; i++) {
		    tamanho--;
		    arrayAux[i] = array[tamanho];
		}
	  
	  return arrayAux;
   }
   
   public String[] trimNoArray(String producao){
	  String[] da = producao.split(">|<");
	  String[] arrayfinal = new String[da.length/2];

	  int notIncrement = 0;
	  for(int i=0;i<da.length;i++){
		 String a = da[i];
		 if(!"".equals(a)){
			arrayfinal[notIncrement] = a;
			notIncrement++;
		 }
	  }
	  return arrayfinal;
   }
   
   public void limpar(){
	  setColunaDaImagem(null);
	  setRegistros(null);
	  setSentenca(null);
	  setSimbolo(null);
	  setVariaveis(null);
	  setSimbolos(null);
	  setVariavel(null);
   }
}
