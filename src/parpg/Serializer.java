package parpg;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
 
public class Serializer {
 
   public static void main (String args[]) {
 
	   Serializer serializer = new Serializer();
	   ArrayList empty = new ArrayList();
	   //Tileset tileset = new Tileset("dog", empty);
	   //serializer.serializeTileset(tileset);
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
