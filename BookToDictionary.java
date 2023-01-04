import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;

public class BookToDictionary {

    private static final int LOWER_A = (int) 'a';
    private static final int LOWER_Z = (int) 'z';

    /**
     * Produces a Scanner connected to a text file accessible via the web.
     * <p>
     * The method expects a link to a text file. When accessing material from
     * Project Gutenberg it is import to use the plain text version of a book.
     * <p>
     * DO NOT MODIFY THIS METHOD.
     *
     * @param link String with URL to text file.
     * @return a Scanner for the file or null if connection cannot be made.
     */
    public final static Scanner browseTextFile(final String link) {
        // Declare the return variable
        Scanner fileOnline;
        // Use try/catch to prevent the program from crashing.
        try {
            // Create a URL object from the link provided
            URL url = new URL(link);
            // Turn the URL into a Scanner
            fileOnline = new Scanner(url.openStream());
        } catch (IOException e) {
            // If something goes wrong, prepare to return null Scanner.
            fileOnline = null;
        }
        return fileOnline;
    }   // method browseTextFile

    /**
     * @desc    This method checks whether a duplicate is inside
     *          the dict
     * @param   //String s - the sourced text file will be used as the
     *          word input for count
     *          //String[] - the dictionary directory
     * @return  bool - returns true if it detects is String has a duplicate
     *                 in the dict
     */
    public static Boolean checkDuplicate(final String s, String[] dict) {
        // Declare the return variable
        Boolean duplicate = false;
        // for loop to iterate through array
        for (int i = 0; i < dict.length; i++) {
            // if the string at current index is equal to the word being checked
            if (dict[i] == s) {
                // set duplicate to true
                duplicate = true;
                // exits the for loop
                i = dict.length + 1;
            }
        }
        return duplicate;
    }   // method checkDuplicate

    /**
     * @desc    This method gets the number of spaced elements of the text by
     *          breaking each line and counting each word of the line.
     * @param   //Scanner sc - the sourced text file will be used as the
     *          word input for count
     * @return  int - returns the size of the word directory of the text file
     */
    public static String sanitize(final String s) {
        String result = "";
        // Declare new String, make contents {string s} to lowercase, put in {string sLC}
        String sLow = s.toLowerCase();
        // for loop to scan each character sequentially
        for (int i = 0; i < sLow.length(); i++) {
            // new int asciiCode, set equal to index of sLC (cast as int to return nearest whole number)
            int asciiCode = (int) sLow.charAt(i);
            // for charAt(i), if letter add to {string result}
            if (asciiCode >= LOWER_A && asciiCode <= LOWER_Z)
                // add chars in sequential order in which you received if they are an alphabetical letter
                result = result + sLow.charAt(i);
        }
        return result;
    }   // method sanitize

    /**
     * @desc    This method gets the number of spaced elements of the text by
     *          breaking each line and counting each word of the line.
     * @param   //Scanner sc - the sourced text file will be used as the
     *          word input for count
     * @return  int - returns the size of the word directory of the text file
     */
    public static int getDictNum(Scanner sc) {
        int wordNum = 0;
        //Breaks each line and counts each word
        while (sc.hasNextLine()) {
            Scanner s2 = new Scanner(sc.nextLine());
            while (s2.hasNext()) {
                s2.next();
                wordNum++;
            }
        }
        return wordNum;
    }

    /**
     * @desc    This method builds the dictionary array by scraping the words
     *          and deleting the duplicates.
     * @param   //Scanner sc - the sourced text file will be used as the
     *          word input
     * @return  String[] - returns the word directory of the text file
     *          with no duplicates in the form of a String array.
     */
    public static String[] buildDict(String book) {
        Scanner sc1 = browseTextFile(book);
        Scanner sc2 = browseTextFile(book);
        //Gets wordNum by breaking down a copy of the text file
        int wordNum = getDictNum(sc1);
        int dictCount = 0;
        String[] dict = new String[wordNum];
        //Breaks each line and scrapes each word and inputs it
        //into the dictionary array
        while (sc2.hasNextLine()) {
            Scanner s2 = new Scanner(sc2.nextLine());
            while (s2.hasNext()) {
                String s = s2.next();
                dict[dictCount] = s;
                dictCount++;
            }
        }
        //Formats words
        lowerCase(dict);
        //Marks duplicates
        removeDuplicate(dict);
        //Get newDict size
        int nullNum = nullCount(dict);
        int newNum = wordNum - nullNum;
        String[] newDict = new String[newNum];
        //Takes unique words from oldDict and inputs into newDict
        nullDelete(dict, newDict);
        dict = newDict;
        return dict;
    }

    /**
     * @desc    This method prints the elements of a String array
     * @param   //String[] str - the array being printed
     */
    public static void printDict(String[] str) {
        for (int i = 0; i < str.length; i++) {  //Checks each word in the Array
            //Prints element
            System.out.println(str[i]);
        }
    }   // method printDict

    /**
     * @desc    This method deletes the null values in a String[] (String array)
     *
     * @param   //String[] dict1 - the array with the null values
     *          //String[] dict2 - the array without the null values
     */
    public static void nullDelete(String[] dict1, String[] dict2) {
        int dict2Count = 0;
        for (int i = 0; i < dict1.length; i++) {   //Checks each word in larger Array
            if (dict1[i] != null) {   //If the value isn't duplicate add it to the clean Array
                dict2[dict2Count] = dict1[i];
                dict2Count++;
            }
        }
    }   // method nullDelete

    /**
     * @desc    This method counts the number of null elements in a String[]
     * @param   //String[] str - dictionary word array
     * @return  int - returns the number of null elements in the array
     */
    public static int nullCount(String[] str) {
        int nullNum = 0;
        for (int i = 0; i < str.length; i++) {  //Checks each word in Array
            if (str[i] == null) {   //If value is null add to counter
                nullNum++;
            }
        }
        return nullNum;
    }   // method nullCount

    /**
     * @desc    This method converts each word in a String[] to lowercase format
     *          while also scraping each char of each from anything that isn't
     *          part of the english alphabet
     * @param   //String[] str - dictionary word array
     */
    public static void lowerCase(String str[]) {
        for (int i = 0; i < str.length; i++) {  //Check each word in Array
            //Get word, clean word, convert to lowercase, replace unformatted
            //word with formatted word
            String word = str[i];
            word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
            str[i] = word;
        }
    }   // method lowerCase

    /**
     * @desc    This method replaces the duplicates in a String[] with
     *          null values
     * @param   //String[] str - dictionary word array
     */
    public static void removeDuplicate(String[] str) {
        for (int i = 0; i < str.length - 1; i++) {  //Checks each word in Array
            if (str[i] != null) {   //Checks if word has been removed before
                if (str[i].length() < 2) { //Marks word as null if it's one character
                    str[i] = null;
                }
                else {
                    for (int j = i + 1; j < str.length - 1; j++) {  //Checks each word that is ahead of the word currently being checked
                        if (str[i].equals(str[j])) { //If there is a word that matches mark the duplicate as null
                            str[j] = null;
                        }
                    }
                }
            }
        }
    }   // method removeDuplicate

    /**
     * Use main() to call other methods; don't put all your code in main.
     */
    public static void main(String[] args) {
        // Link to A Tale of Two Cities
        String book = "https://www.gutenberg.org/files/98/98-0.txt";
        String[] dict = buildDict(book);
        printDict(dict);
    }  // method main

}  // class BookToDictionary