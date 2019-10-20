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
    public void isCharInClass() {
	    assertTrue(ZJson.isCharInClass(65,"A")==true);
    }
	
    @Test
    public void isCharInClassTest(){
	    assertFalse(ZJson.isCharInClass(65,"B")==true);
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
        //Right string for parsing
        rightJSONStrings = new String[]{
                "{\"name\": \"\\u0123\"}",
                "{\"name\": \"a\"}",
                "{\"name\": \"\"}",
                "{\"name\": \"true\"}",
                "{\"name\": true }",
                "{\"name\": \"1231\" }",
                "{\"name\": [\"Hello\",true,false,123 ]}"
        };

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
        // Wrong strings for parsing
        wrongJSONStrings = new String[]{
                "{\"name\": \"\\u90xx\"}",
                "{\"name\": asdsasd }",
                "{\"name\": tru }",
                "{[\"name\": \"a\"]}",
                "{\"name\": true ",
                "{\"name\": [\"Hello\",{true},false,123 ]}"
        };

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
    public void readJString() throws IOException {
        Reader jsonReader = new StringReader("hll\\\\g");
        StringWriter sbw = new StringWriter();
        StringBuffer sb = sbw.getBuffer();

        int c = ZJson.readJString(jsonReader,sb);
        System.out.println(sbw);
        assertTrue(c==-1);
    }

    @Test
    public void readJLiteral() throws IOException {
        Reader jsonReader = new StringReader("s1\"llen");
        StringWriter sbw = new StringWriter();
        StringBuffer sb = sbw.getBuffer();

        int c = ZJson.readJLiteral(jsonReader,sb);

        System.out.println(sb.toString());

        assertTrue(c == 34);
    }

    @Test
    public void literal2value() {
        Boolean s = true;
        Boolean s1 = false;
        Long k = 51551425156L;
        double j = 2152.5;
        Object d = null;
        Object d1 = " ";

        Object[] testingObjects = {
                ZJson.literal2value("true"),
                ZJson.literal2value("false"),
                ZJson.literal2value("51551425156"),
                ZJson.literal2value("2152.5F"),
                ZJson.literal2value("null"),
                ZJson.literal2value(" ")
        };

        assertEquals(s, testingObjects[0]);
        assertEquals(s1, testingObjects[1]);
        assertEquals(k, testingObjects[2]);
        assertEquals(j, testingObjects[3]);
        assertEquals(d, testingObjects[4]);
        assertEquals(d1, testingObjects[5]);

    }

    @Test
    public void setType() {
        zjson.setType(111);

        assertTrue(zjson.getType()>=0 && zjson.getType()<=4 );
    }

    @Test
    public void setPrintMode() {
        zjson.setPrintMode(20);

        assertTrue(zjson.getPrintMode()>=0 && zjson.getPrintMode()<=1 );
    }
}
