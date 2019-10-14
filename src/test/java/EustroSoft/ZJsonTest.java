package EustroSoft;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

public class ZJsonTest {

    ZJson zjson;

    public static final String TESTING_OBJECT = new String("Hello!");
    public static final String TESTING_STRING = new String("String");

    public ZJsonTest()
    {
        zjson = new ZJson();
    }

    @Test
    public void setItem() {

        zjson.addItem(TESTING_OBJECT,TESTING_STRING);

        zjson.setItem(TESTING_STRING,TESTING_OBJECT);

        assert (zjson.getItem(TESTING_STRING) != null);
    }

    @Test
    public void testSetItem() {
    }

    @Test
    public void getItemValues() {
    }

    @Test
    public void getItemName() {
    }

    @Test
    public void getNameIndex() {
    }

    @Test
    public void getValueType() {
    }

    @Test
    public void getItem() {
    }

    @Test
    public void testGetItem() {
    }

    @Test
    public void getItemType() {
    }

    @Test
    public void testGetItemType() {
    }

    @Test
    public void getItemString() {
    }

    @Test
    public void testGetItemString() {
    }

    @Test
    public void getItemLong() {
    }

    @Test
    public void testGetItemLong() {
    }

    @Test
    public void getItemZJson() {
    }

    @Test
    public void testGetItemZJson() {
    }

    @Test
    public void testToString() {
    }

    @Test
    public void toJSONString() {
    }

    @Test
    public void testToJSONString() {
    }

    @Test
    public void writeJSONString() {
    }

    @Test
    public void testWriteJSONString() {
    }

    @Test
    public void isCharInClass() {
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
