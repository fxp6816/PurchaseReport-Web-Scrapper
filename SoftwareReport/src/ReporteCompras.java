import java.util.*;
//jsoup imports
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ReporteCompras{
    private final Profile profile = new Profile();
    private final String user  = profile.getUser();
    protected final String pass  = profile.getPassword();
    protected Map<String,String> login_cookies;
    
    //private final String ID_URL = "https://www.misionhuascaran.org.pe/wp-admin/edit.php?post_type=shop_order";

    private HashMap<String,String> comprasIds;
    private ArrayList<Compra> compras;

    public ReporteCompras(){
        this.comprasIds =  new HashMap<String,String>();
        this.compras = new ArrayList<Compra>();
        
        try{
        
         final String login_URL = "https://www.misionhuascaran.org.pe/wp-login.php";
         Response res = Jsoup.connect(login_URL).data("log", this.user, "pwd", this.pass).method(Method.POST).execute();
         this.login_cookies = res.cookies();
         
        }catch(Exception e){
         e.printStackTrace();
        }

    }//CONSTRUCTOR END
    

    public HashMap<String,String> getIds(){
      
      final String ID_URL = "https://www.misionhuascaran.org.pe/wp-admin/edit.php?post_type=shop_order";
      
      try{
         
         Document doc = Jsoup.connect(ID_URL).cookies(this.login_cookies).get();
         
         Elements listIdsTable = doc.body().select("td.order_number");
         
         for(Element item: listIdsTable){

             String[] tokenInfo = item.text().split("#");
             for(String token : tokenInfo){
                 //System.out.println("unrevised token:" + token);
                 char tokenCheckFirstChar = token.toCharArray()[0];
                 if(Character.isDigit(tokenCheckFirstChar)){
                     String compraID = token.substring(0,token.indexOf(" "));
                     String compraNombre = token.substring(token.indexOf(" "), token.length());
                     //System.out.println("ID: " + compraID);
                     //System.out.println("Nombre: " + compraNombre);
                     this.comprasIds.put(compraID,compraNombre);
                 }

             }
         }
         
      }catch(Exception ex){
         ex.printStackTrace();
      }
      
      return this.comprasIds;
    }//GETIDS() - END

    //might need to output the length of the number
    public int findNumberIndex(String str){
        boolean firstNumber = false;
        int currentIndex = 0;
        int numLength = 0;
        int recordedIndex = 0;
        char[] strCH = str.toCharArray();
        for(char c : strCH){
            if (Character.isDigit(c) && firstNumber == false) {
                recordedIndex = currentIndex;
                firstNumber = true;
            }else if(Character.isDigit(c) && firstNumber == true){
                numLength++;
            }else{
                numLength = 0;

            }
            currentIndex++;
        }
        return currentIndex;
    }



    public ArrayList<Compra> getCompras(String compraId,String nombre){
    
    
      String INFO_URL = "https://www.misionhuascaran.org.pe/wp-admin/post.php?post="+compraId + "&action=edit";
      
      try{
         Document doc = Jsoup.connect(INFO_URL).cookies(this.login_cookies).get();
         Elements tableInfo = doc.body().select("div.order_data_column");
         
         
         //processing the text

          String preText = tableInfo.get(1).text();
          String firstClean = preText.substring(preText.indexOf(nombre),preText.length());
          String secondClean = firstClean.substring(nombre.length());
          //System.out.println("First: " + firstClean);
          //System.out.println("Second: " + secondClean);
          System.out.println("ID: " + compraId);
          System.out.println("Nombre: " + nombre);
          String direccion = secondClean.substring(0,secondClean.indexOf("Dirección de correo electrónico:"));
          System.out.println("Direccion: " + direccion);
          String thirdClean = secondClean.substring(secondClean.indexOf("Dirección de correo electrónico:"));
          //System.out.println("Third: " + thirdClean);
          //find a better way to do this
          int correoIndexEnd = thirdClean.indexOf(" Teléfono");
          String correo = thirdClean.substring(33,correoIndexEnd);
          System.out.println("Correo: " + correo);
          String telefono = thirdClean.substring(correoIndexEnd + 10,thirdClean.indexOf(" Nombre"));
          System.out.println("Telefono:" + telefono);

          String fourthClean = thirdClean.substring(thirdClean.indexOf("DNI o ID::"));
          System.out.println("Fourth: " + fourthClean);


      }catch(Exception ex){
         ex.printStackTrace();
      }      
      return this.compras;
    }//GETCOMPRAS - ENDS
    
    
    
    public void writeReport(){
      
      // write compras arraylist into a CSV file
      
      
    }//WRITEREPORT() - END
    
    
    
    public static void main(String[] args){
      ReporteCompras RC = new ReporteCompras();
      HashMap<String, String> ids = RC.getIds();
      
      //test with one item onl
      for(String ID: ids.keySet()){
          RC.getCompras(ID,ids.get(ID));
      }

      
    }//MAIN - ENDS


}