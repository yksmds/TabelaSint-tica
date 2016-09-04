package br.com.compiladores;

public class Teste{
   
   private static String s2;

   public static String[] organizaArray(String producao){
	  String[] da = producao.split(">|<");
	  String[] f = new String[da.length/2];
	  String[] arrayfinal = new String[da.length/2];
	  
       int notIncrement = 0;
	  for(int i=0;i<da.length;i++){
		 String a = da[i];
		 if(!"".equals(a)){
			f[notIncrement] = a;
			System.out.println(a);
			notIncrement++;
		 }
	  }
	  
	  return arrayfinal;
   }
   
   public static void main(String[] args) {

      
      s2="<id><+><id><*><id><+><id>";
      String[] teste = organizaArray(s2);
      for(int i=0;i < teste.length; i++)
    	 System.out.println(teste[i]);

   }
}
