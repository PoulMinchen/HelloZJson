package EustroSoft;

import java.util.*;
import java.io.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

public class ZJsonTest {

    private ZJson zjson;
    private ZJson zjsonTwo;

    private int stringCounter;
    private int errorString;
    private String [] rightJSONStrings;
    private String [] wrongJSONStrings;

    public ZJsonTest()
    {
        zjson = new ZJson();
        zjsonTwo = new ZJson();

        stringCounter = 1;
        errorString=0;
        rightJSONStrings = new String[]{
                "{\"name\": \"\\u0123\"}",
                "{\"name\": \"a\"}",
                "{\"name\": \"\"}",
                "{\"name\": \"true\"}",
                "{\"name\": true }",
                "{\"name\": \"1231\" }"
        };
        wrongJSONStrings = new String[]{
                "{\"name\": \"\\u90xx\"}",
                "{\"name\": asdsasd }",
                "{\"name\": tru }",
                "{[\"name\": \"a\"]}",
                "{\"name\": true "
        };
    }

    @Test
    public void clearTest() throws ArrayIndexOutOfBoundsException
    {
        ArrayIndexOutOfBoundsException ae = null;
        zjson.addItem("NameItem",1);
        zjson.clear();

        try{
         assertTrue((
                 zjson.getItemName(0) == null) &&
                 (zjson.getItemName(0)==null));
        }
        catch(ArrayIndexOutOfBoundsException ex) { ae = ex;}

        assertNotNull(ae);
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
	    assertTrue(ZJson.isCharInClass(65,"B")==true);
    }


    @Test
    public void isCharSpaceAny() {
	    assertTrue(ZJson.isCharSpaceAny('\n'));
        assertTrue(ZJson.isCharSpaceAny('\t'));
        assertTrue(ZJson.isCharSpaceAny('\r'));
        assertTrue(ZJson.isCharSpaceAny(' '));
    }

    @Test
    public void isCharSpaceOnly() {
        assertTrue(ZJson.isCharSpaceOnly('\t'));
    }

    @Test
    public void parseException() {
        IOException ex = ZJson.parseException(1,'a');
        assertNotNull(ex);
    }

    // Parsint JSON
    @Test
    public void parseJSONString() throws IOException {
        zjson.parseJSONString("{ \"name\" : false }");

        assertTrue(zjson.getItemName(0)!="exception");
    }
    /**
     * There are right JSON format strings
     * All of them need to be parsed
     * Also with no any error
     * @throws IOException
     */
    @Test
    public void parseJSONReaderRight() throws IOException {
        StringReader jsonReader;
        int endCode = 0;

        for(String testingString : rightJSONStrings) {
            try {
                jsonReader = new StringReader(testingString);
                endCode = zjson.parseJSONReader(jsonReader);

                System.out.println("String number " + stringCounter +
                        " : " + testingString);
            }
            catch (IOException ex) {
                System.out.println("String number " + stringCounter +
                        " : " + testingString +" "+ex.getMessage());
                errorString++;
            }
            stringCounter++;
        }
        System.out.println("How many strings have not been passed : " + errorString);
        assertTrue(errorString==0);
    }
    /**
     * There are wrong JSON format strings
     * All of them not need to be parsed
     * @throws IOException
     */
    @Test
    public void parseJSONReaderWrong() throws IOException {
        StringReader jsonReader;
        int endCode = 0;

        for(String testingString : wrongJSONStrings) {
            try {
                jsonReader = new StringReader(testingString);
                endCode = zjson.parseJSONReader(jsonReader);

                System.out.println("String number " + stringCounter +
                                   " : " + testingString);

                errorString++;
            }
            catch (IOException ex) {
                System.out.println("String number " + stringCounter +
                                   " : " + testingString +" "+ex.getMessage());
            }
            stringCounter++;
        }
        System.out.println("How many strings have been passed : " + errorString);
        assertTrue(errorString==0);
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
