/*
 * Purpose: Testing, refactoring and improvement JZson.java from the ConcepTIS project
 *
 * LICENSE: use BSD, MIT, ISC or BALES license (on your choise)
 *
 * (c) 2019 EustroSoft.org
 * (c) 2019 Pavel Seleznev <yadzuka@eustrosoft.org>
 *
 */

package EustroSoft;

import java.util.*;
import java.io.*;

class HelloZJson {

    public static void main(String[] args) {
        try {
            System.out.println("Hello ZJson!");
            ZJson zjson = new ZJson();


        } catch (Exception ex) {
            System.out.println("File does not found");
        } finally {
        }
    }
}
