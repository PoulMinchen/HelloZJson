package EustroSoft;

import java.util.*;
import java.io.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

public class ZJsonTest {

    ZJson zjson;
    Writer testingWriter;
    public static final String TESTING_OBJECT = new String("Hello!");
    public static final String TESTING_STRING = new String("String");

    public ZJsonTest()
    {
        zjson = new ZJson();
    }

    @Test
    public void testAddItem(){
	zjson.addItem("String"); 
	assertEquals(zjson.getItem(0),"String");
 	assertNull(zjson.getItemName(0));
    }	

    @Test
    public void writeJSONString(){
	try{
	zjson.addItem("a",Boolean.TRUE);
        StringWriter sbw = new StringWriter();
        StringBuffer sb = sbw.getBuffer();
	zjson.writeJSONString(sbw);
	ZJson zjson_out = new ZJson();
        zjson_out.parseJSONString(sbw.toString());
	assertEquals(zjson.getItem(0), zjson_out.getItem(0));
	//Writer out_writer = new StringWriter(System.out);
	//zjson.setDebug = out_writer;
	//System.out.println(zjson.writeJSONString(out_writer));
	}
	catch(IOException ex) { System.out.println(ex.getMessage());}
    }

    @Test
    public void testWriteJSONString() {
	
    }

    @Test
    public void isCharInClass() {
	assertTrue(ZJson.isCharInClass(65,"A")==true);
    }
	
    @Test
    public void isCharInClassTest(){
	arrertTrue(ZJson.isCharInClass(65,"B")==true);
    }


    @Test
    public void isCharSpaceAny() {
	
    }

    @Test
    public void isCharSpaceOnly() {
    }

    @Test
    public void parseException() {
    }

    @Test
    public void parseJSONString() {
    }

    @Test
    public void parseJSONReader() {
    }

    @Test
    public void testParseJSONReader() {
    }

    @Test
    public void readJString() {
    }

    @Test
    public void testReadJString() {
    }

    @Test
    public void readJLiteral() {
    }

    @Test
    public void literal2value() {
    }

    @Test
    public void setType() {
    }

    @Test
    public void getType() {
    }

    @Test
    public void setPrintMode() {
    }

    @Test
    public void getPrintMode() {
    }

    @Test
    public void setDebug() {
    }

    @Test
    public void startExecuting() {
    }
}
