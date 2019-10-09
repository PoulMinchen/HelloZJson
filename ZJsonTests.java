

import java.util.*;
import java.io.*;

class ZJsonTests
{
   ZJson zJsonTestingObject;
   public void ZJsonInitializationTesting(ZJson zjs){
	try{ zJsonTestingObject = zjs;}
	catch(Exception ex){}}
 
   public void ZJsonAddingObjectsTest(){
	try{
	System.out.println("Adding object starts executing!");
	zJsonTestingObject.addItem("Something");
	zJsonTestingObject.addItem("Hey you","Hello");
	}
	catch(Exception ex){ System.out.println(ex);}
	} 
}
