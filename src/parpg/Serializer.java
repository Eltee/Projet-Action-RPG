package parpg;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
 
public class Serializer {
 
   public static void main (String args[]) {
 
	   Serializer serializer = new Serializer();
	   Tileset tileset = new Tileset(null, null);
	   serializer.serializeTileset(tileset);
   }
 
   public void serializeTileset(Tileset tileset){
	   try{
		FileOutputStream fout = new FileOutputStream("tilesets/default.tst");
		ObjectOutputStream oos = new ObjectOutputStream(fout);   
		oos.writeObject(tileset);
		oos.close();
		System.out.println("Done");
	   }
	   catch(Exception ex){
		   ex.printStackTrace();
	   }
   }
}
