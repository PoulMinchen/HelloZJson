// ConcepTIS project
// (c) Alex V Eustrop 2009-2010
// see LICENSE at the project's root directory
//
// $Id$
//

/*
package ru.mave.ConcepTIS.dao; //package org.EustroSoft.ZJson;
*/
import java.util.*;
import java.io.*;

/** Set of named&numbered values where name is String or Long & value is one of {String,Long,ZJson}
 * can be used as store of JSON
 * can be used to parse JSON
 * depends on nothing except java.lang,java.io,java.util
 */
public class ZJson
{

private int type = ZJSON_TYPE_OBJECT; // ZJSON_TYPE_OBJECT,  ZJSON_TYPE_ARRAY, ZJSON_TYPE_PARSER?
private int print_mode = ZJSON_PRINT_MODE_COLUMN; // ZJSON_PRINT_MODE_ROW, ZJSON_PRINT_MODE_COLUMN
private Vector names;
private Vector items; 
//private Object[] items; // [[String name,Object value, int type],...] ?
//private ht_index

public static final int ZJSON_TYPE_NULL = 0; // 
public static final int ZJSON_TYPE_OBJECT = 1;
public static final int ZJSON_TYPE_ARRAY = 2;
public static final int ZJSON_TYPE_PARSER = 3; // 
public static final int ZJSON_TYPE_VALUE = 4; // one-value container

public static final int ZJSON_ITEM_TYPE_NULL = 0; // null value
public static final int ZJSON_ITEM_TYPE_ZJSON = 1;
public static final int ZJSON_ITEM_TYPE_STRING = 2;
public static final int ZJSON_ITEM_TYPE_LONG = 3;
public static final int ZJSON_ITEM_TYPE_NUMBER = 4; //literal (String) of number (for float,decimal and so on)

public static final int ZJSON_PRINT_MODE_COLUMN = 0;
public static final int ZJSON_PRINT_MODE_ROW = 1;
//

public void clear(){items = new Vector(); names=new Vector(); }

//
private Object value2zvalue(Object value){return(value);} // check value is {null, String, Long, ZJson } or can be converted toString(). null if failed
private String name2zname(String name){return(name);} // can be used to return String object.equals(name) from dictionary (to minimize memory usage)
private static void not_implemented(){ throw(new UnsupportedOperationException()); } // use it in not implemented methods

public int size(){return(items.size());}
public void addItem(Object value){names.add(null);items.add(value2zvalue(value));}
public void addItem(String name,Object value){names.add(name2zname(name));items.add(value2zvalue(value));}
public void setItem(String name,Object value){int i=getNameIndex(name); setItem(i,value2zvalue(value));} // latest value
public void setItem(int index,Object value){items.set(index,value2zvalue(value));} // latest value

public Vector getItemValues(String name){Vector v = new Vector(); int i=0; while((i = names.indexOf(name,i)) > 0){v.add(items.get(i));} return(v); } // all values for "name"

public String getItemName(int index){return( (String)names.get(index) ); }
public int getNameIndex(String name){return(names.indexOf(name));}
public String[] listNames(){not_implemented();return(null);}
public static int getValueType(Object value){not_implemented();return(-1);}

public Object getItem(String name){return(getItem(getNameIndex(name)));}
public Object getItem(int index){return(items.get(index));}
public int getItemType(String name){not_implemented();return(-1);}
public int getItemType(int number){not_implemented();return(-1);}

public String getItemString(String name){return((String)getItem(name));}
public String getItemString(int index){return((String)getItem(index));}

public Long getItemLong(String name){return((Long)getItem(name));}
public Long getItemLong(int index){return((Long)getItem(index));}

public ZJson getItemZJson(String name){return((ZJson)getItem(name));}
public ZJson getItemZJson(int index){return((ZJson)getItem(index));}

//
public String toString(){return(toJSONString(0));} // overload Object.toString()
public String toJSONString(){return(toJSONString(1));}
public String toJSONString(int level)
{
 StringWriter sbw = new StringWriter();
 StringBuffer sb = sbw.getBuffer();
 try{ writeJSONString(sbw,level); } catch(IOException ioe)  {sb.append(ioe.toString());}
 return(sbw.toString());
}

public int writeJSONString(Writer out) throws IOException { return (writeJSONString(out,0)); }

public int writeJSONString(Writer out, int level) throws IOException
{
 String close_char = "";
 String close_item = "";
 int count = items.size();
 //int type = getType(); // use if getType() !== type
 if(print_mode == ZJSON_PRINT_MODE_COLUMN)
 {
  int i = level;
  while(i-- > 0){close_item = close_item + " "; }
  close_item = "\n" + close_item;
 }
 if(type == ZJSON_TYPE_OBJECT)  { out.write("{" + close_item); close_char = "}"; }
 else if(type == ZJSON_TYPE_ARRAY) { out.write("[" + close_item); close_char = "]"; }
 else if(type == ZJSON_TYPE_NULL) {out.write("null"); return(0); }
 for(int i = 0; i < count; i++)
 {
  print_item(out,i, level);
  if(i!=(count-1)) out.write("," + close_item);
 }
 out.write(close_item);
 out.write(close_char);
 out.flush();
 return(0);
} // writeJSONString(Writer out)

private void print_item(Writer out, int index, int level)
 throws IOException
{
 if(type == ZJSON_TYPE_OBJECT) { write_jstr(out,(String)names.get(index)); out.write(" : "); }
 Object item = items.get(index);
 if(item == null){ out.write("null"); return; }
 ZJson json = null;
 try{ json = (ZJson)item; } catch(Exception e) {}
 if(json != null ){ json.writeJSONString(out, (level == 0) ? 0 : level + 1); return;}
 try{ Boolean bool = (Boolean)item; write_jlit(out,bool.toString()); return; } catch(Exception e) {}
 try{ Long num = (Long)item; write_jlit(out,num.toString()); return; } catch(Exception e) {}
 try{ Double num = (Double)item; write_jlit(out,num.toString()); return; } catch(Exception e) {}
 write_jstr(out,(String)(item));
} // //print_item(out,i);

private static void write_jstr(Writer out, String str) throws IOException
{
if(str == null) {out.write("null"); return; }
int length = str.length();
int i=0;
out.write("\"");
while(i < length)
{
 char c = str.charAt(i);
 switch(c){
 case '\n':
  out.write("\\n"); break;
 case '\r':
  out.write("\\r"); break;
 case '"':
  out.write("\\\""); break;
 case '\\':
  out.write("\\\\"); break;
 default:
  out.write(c);
 }
i++;
}
out.write("\"");
} // write_jstr(Writer out, String str)

private static void write_jlit(Writer out, String literal) throws IOException
{
 out.write(literal); //as is
}


// START PARSING SECTION
public static final String SZ_CLASS_SPACE_ANY = " \n\r\t";
public static final String SZ_CLASS_CRLF = "\n\r";
public static final String SZ_CLASS_SPACE_ONLY = " \t";
public static final String SZ_CLASS_DIGITS = "0123456789";
public static final String SZ_CLASS_DIGITS_HEX = "0123456789ABCDEFabscef";
public static final String SZ_CLASS_STRING_STOP = "\"" + SZ_CLASS_CRLF;
public static final String SZ_CLASS_LITERAL_STOP = SZ_CLASS_SPACE_ANY + ",/?\\\"'[]{}";
public static final String SZ_CLASS_LITERAL = SZ_CLASS_DIGITS_HEX + "truefalsn.Ex+-";
public static final String SZ_CLASS_LITERAL_NUMBER = SZ_CLASS_DIGITS+ ".Ee+-";
public static final String[] SZ_LITERALS_ALLOWED = {"true","false","null"};
public static final String LITERAL_TRUE = "true";
public static final String LITERAL_FALSE = "false";
public static final String LITERAL_NULL = "null";

private static final String[] CONTEXT_NAMES = {"","GLOBAL","OBJECT","ARRAY", "ARRAY_VALUE", "NAME","NAME_AFTER","VALUE","VALUE_STRING", "VALUE_LITERAL"};
private static final int CONTEXT_GLOBAL = 1;
private static final int CONTEXT_OBJECT = 2;
private static final int CONTEXT_ARRAY = 3;
private static final int CONTEXT_ARRAY_VALUE = 4;
private static final int CONTEXT_NAME = 5;
private static final int CONTEXT_NAME_AFTER = 6;
private static final int CONTEXT_VALUE = 7;
private static final int CONTEXT_VALUE_STRING = 8;
private static final int CONTEXT_VALUE_LITERAL = 9;

public static boolean isCharInClass(int c, String char_class) { return(char_class.indexOf(c) != -1);}
public static boolean isCharSpaceAny(int c) { return(isCharInClass(c,SZ_CLASS_SPACE_ANY));}
public static boolean isCharSpaceOnly(int c) { return(isCharInClass(c,SZ_CLASS_SPACE_ONLY));}

public static IOException parseException(int context,int c)
 { return (new IOException("JSON parsing error: invalid character \"" + (char)c + "\"[" + c + "] for context : " + CONTEXT_NAMES[context])); }

public void parseJSONString(String json) throws IOException
{
 StringReader sr = new StringReader(json);
 try{ parseJSONReader(sr); } catch(IOException ioe)  {addItem("exception",ioe.toString());}
} // parseJSONString

public int parseJSONReader(Reader json) throws IOException {return parseJSONReader(json,CONTEXT_GLOBAL);}
//public int parseJSONReader(Reader json, int context) throws IOException {return(1);}
public int parseJSONReader(Reader json, int context) throws IOException 
{
 int c = 0;
 //dodebug("parseJSONReader:" + CONTEXT_NAMES[context]);
 while( c != -1 )
 {
  StringBuffer sb = new StringBuffer();
  String name = null;
  Object value = null;
  switch(context){
   case CONTEXT_GLOBAL:
    c = skipSpaceAny(json);
    if(c == '{') c=parseJSONReader(json, CONTEXT_OBJECT);
    else if(c == '[') c=parseJSONReader(json, CONTEXT_ARRAY);
    else throw(parseException(context,c));
    return(c);
   case CONTEXT_OBJECT:
    if(c == '}' ) {c=json.read(); return(c); }
    c = skipSpaceAny(json);
    if(c == '}' ) {c=json.read(); return(c); }
    if(c == '"') c = parseJSONReader(json, CONTEXT_NAME);
    else throw(parseException(context,c));
    //dodebug("CONTEXT_OBJECT after NAME : " + c);
    if(isCharSpaceAny(c) || c == '"' ) c = skipSpaceAny(json);
    if(c == ',')  break; // go to next item
    //dodebug("CONTEXT_OBJECT after NAME2 : " + c);
    break;
   case CONTEXT_ARRAY:
    c=parseJSONReader(json, CONTEXT_ARRAY_VALUE);
    if(isCharSpaceAny(c) || c == '"' ) c = skipSpaceAny(json);
    if(c == ',') break; // go to next item
    if(c == ']') {c=json.read(); return(c);}
    throw(parseException(context,c));
   case CONTEXT_NAME:
    c = readJString(json,sb);
    if(isCharInClass(c,SZ_CLASS_CRLF)) throw(parseException(context,c));
    if(c == '"') { name = sb.toString(); sb = new StringBuffer(); }
    //dodebug("NAME : " + name +" len: " +name.length());
    if (name.length() == 0) throw(parseException(context,c)); // empty name
    // break; // next is CONTEXT_NAME_AFTER
   case CONTEXT_NAME_AFTER:
    c = skipSpaceAny(json);
    if(c != ':') throw(parseException(CONTEXT_NAME_AFTER,c));
   case CONTEXT_VALUE:
   case CONTEXT_ARRAY_VALUE:
    c = skipSpaceAny(json);
    if(context == CONTEXT_ARRAY_VALUE && c == ']') return(c);
    if(c == '\"') { c = readJString(json,sb); value = sb.toString(); addItem(name,value); } // value is string
    else if (c == '{' || c == '[') //value is object or array
    {
      ZJson v = new ZJson();
      addItem(name,v); // required to view really parsed items while debugging
      if(c == '{' ) { v.setType(ZJSON_TYPE_OBJECT); c = v.parseJSONReader(json,CONTEXT_OBJECT); }
      else {v.setType(ZJSON_TYPE_ARRAY);  c = v.parseJSONReader(json,CONTEXT_ARRAY); }
      //dodebug("CONTEXT_VALUE_ZJSON:" + (char)c + " " +c );
    } else 
    { // value is literal (true,false, null, number)
     if(!isCharInClass(c,SZ_CLASS_LITERAL)) throw(parseException(CONTEXT_VALUE_LITERAL,c));
     sb.append((char)c); // save char, it is part of value!
     c=readJLiteral(json,sb);
     if(sb.length() == 0) throw(parseException(CONTEXT_VALUE,c));
     addItem(name , literal2value(sb.toString()) );
    }
    break;
  } //switch
  //dodebug("Finish switch on:" + CONTEXT_NAMES[context]);
  if(context == CONTEXT_VALUE) return(c);
  if(context == CONTEXT_ARRAY_VALUE) return(c);
  if(context == CONTEXT_NAME) return(c);
  //dodebug("Continue switch on:" + CONTEXT_NAMES[context] + " with c=" + c);
  //c = json.read();
 } // while( c != -1 )
 return(c);
} // parseJSONReader()

// return next string token from stream
// word, spaces, string, crlf, {, [, },], \
private int skipSpaceAny(Reader json)
throws IOException
{
 int c = json.read();
 //dodebug("skipSpaceAny:enter" );
 while( isCharSpaceAny(c) ) { c=json.read(); }
 //dodebug("skipSpaceAny:exit" );
 return(c);
}
// returns nexh character from json (normally one from stop_class or -1)
public static int readJString(Reader json, StringBuffer sb) throws IOException {return(readJString(json,sb,"\""));}
public static int readJString(Reader json, StringBuffer sb, String stop_class)
throws IOException
{
 int c = json.read();
 //dodebug("readJString:" );
 while( c != -1 )
 {
  if(isCharInClass(c,stop_class)) break;
    if( c == '\\' ){
     c = json.read();
     if(c == -1) throw(parseException(CONTEXT_VALUE_STRING,c));
     switch(c){
      case 'n': sb.append('\n'); break;
      case 'r': sb.append('\r'); break;
      case 't': sb.append('\t'); break;
      case '\\': sb.append('\\'); break;
      case '"': sb.append('\"'); break;
      //case 'u': sb.append(readUChar(rdr)); break;
      default: throw(parseException(CONTEXT_VALUE_STRING,c));
     } // switch
    } //if(c == '\\')
    else {sb.append((char)c); }
  c=json.read();
 }
 //dodebug("readJString:exit" );
 return(c);
} //readJString
public static int readJLiteral(Reader json, StringBuffer sb) throws IOException
{
 int c = json.read();
 //dodebug("readJLiteral:" );
 while( c != -1 )
 {
   if(isCharInClass(c,SZ_CLASS_LITERAL_STOP)) break;
   if(!isCharInClass(c,SZ_CLASS_LITERAL)) throw(parseException(CONTEXT_VALUE_LITERAL,c));
   sb.append((char)c);c=json.read();
 }
 //dodebug("readJLiteral:exit" );
 return(c);
} //readLiteral

public static Object literal2value(String literal)
{
if(LITERAL_NULL.equals(literal)) return(null);
if(LITERAL_TRUE.equals(literal)) return(Boolean.TRUE);
if(LITERAL_FALSE.equals(literal)) return(Boolean.FALSE);
try{Long value = Long.valueOf(literal); return(value);}
 catch(NumberFormatException nfe){} // ignore it and step forward
try{Double value = Double.valueOf(literal); return(value);}
 catch(NumberFormatException nfe){} // ignore it and step forward
//if(!isCharInClass(literal,SZ_CLASS_LITERAL_NUMBER) throw(parseException(CONTEXT_VALUE_LITERAL,c)); //sketch
return(literal);
} // literal2value(String literal)

// END PARSING SECTION

public void setType(int type){this.type=type;}
public int getType(){return(type);}

public void setPrintMode(int mode){print_mode=mode;}
public int getPrintMode(){return(print_mode);}

// ZJson as Factory
// override this methods in children classes to use your implementation of ZJson
// this code sketch reserved to next step of ZJson implementation refactoring&testing
// using separate children classes
/*
public ZJson newZJson() { return(new ZJson()); } 
public ZJson newZJson(int type) { return(new ZJson(type)); } 
public ZJson newZJson(int type, int print_mode) { return(new ZJson(type, print_mode)); } 
public ZJson newZJson(String json) { return(new ZJson(json)); } 
*/

// Constructors
public ZJson() { clear(); setType(ZJSON_TYPE_OBJECT); }
public ZJson(int type) {clear(); setType(type); }
public ZJson(int type, int print_mode) {clear(); setType(type); setPrintMode(print_mode); }
public ZJson(String json) throws IOException { this(ZJSON_TYPE_PARSER); this.parseJSONString(json); }

// DEBUGGING METHODS SECTION
private static Writer debug = null;
public static void setDebug(Writer debug_writer) { debug = debug_writer; }
private static void dodebug(String msg){ if (debug==null) return; try{ debug.write(msg); debug.write("\n"); debug.flush(); }catch(Exception e){} 
}
public static void startExecuting()  
throws IOException
{
ZJson json = new ZJson();
Writer out_writer = new OutputStreamWriter(System.out);
IOException ioe = null;

//json.addItem("start_parsing", "start");

System.out.println("Hello type test JSON here:");
json.setDebug(out_writer);
try {
json.parseJSONReader(new InputStreamReader(System.in));
}
 catch (IOException e) { ioe = e ; }
//json.addItem("end_parsing", "end");
System.out.println("result:");
json.writeJSONString(out_writer,1);
System.out.println("");
if(ioe != null) {System.out.println(ioe);}
else System.out.println("Ok!");
  }
} //ZJson
