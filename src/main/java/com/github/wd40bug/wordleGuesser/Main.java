package com.github.wd40bug.wordleGuesser;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    static ArrayList<String> options =  new ArrayList<>();
    static String[] permOptions = new String[12972];
    static HashMap<String, Integer> charMap = new HashMap<>();
    @SuppressWarnings({"CommentedOutCode", "RedundantSuppression"})
    public static void main(String[] args) throws IOException {
//        int times = Integer.parseInt(new Scanner(System.in).nextLine());
        var gWords = new File("src/main/resources/Guessable Words.csv");
        var fileIn = new Scanner(gWords);
        var i = 0;
        while (fileIn.hasNextLine()){
            permOptions[i] = fileIn.nextLine();
            i++;
        }
        int times = 2315;
        System.out.println(ANSI.Magenta+test(times)+ANSI.Reset);
//        getAverages();
//        getBetterAverages();
    }
    public static int evaluateString(String s, int round){
        char[] chars = s.toCharArray();
        int x = 0;
        for(int i = 0; i< chars.length; i++){
            var ch = chars[i];
            var key = ch+","+i;
            x+= charMap.get(key);
        }
        if(hasRepeatChars(s)&&round<=3){
            x*=0;
        }
        return x;
    }
    public static String generateStr(char[] badChars, KnownChar[] unknownChars, KnownChar[] knownChars, int round) {
        var strMap = new TreeMap<Integer,String>();
        int max = 0;
        int b = 0;
        for(Iterator<String> data = options.iterator(); data.hasNext();){
            var value = data.next();
            if(containsCharArrayAny(value,badChars)){
                data.remove();
                continue;
            }
            if(!containsCharArrayAll(value, unknownChars)){
                data.remove();
                continue;
            }
            if(!containsKnownChars(value,knownChars)){
                data.remove();
                continue;
            }
            b = evaluateString(value,round);
            strMap.put(b, value);
            max = Math.max(max, b);
        }
        String best = strMap.get(max);
        System.out.print(best+", "+b);
        options.remove(best);
        return best;
    }
    public static boolean hasRepeatChars(String string){
        var map = new HashMap<Character,Integer>();
        for(char c : string.toCharArray()){
            if(map.containsKey(c)){
                return true;
            } else{
                map.put(c,1);
            }
        }
        return false ;
    }
    public static boolean containsKnownChars(String string,KnownChar[] KnownChars){
        boolean result = true;
        for (KnownChar knownChar : KnownChars) {
            if (string.charAt(knownChar.getPlace()) != knownChar.getCharacter()) {
                result = false;
                break;
            }
        }
        return result;
    }
    public static boolean containsCharArrayAny(String string, char[] chars){
        boolean result = false;
        for(int i = 0; i<chars.length && !result; i++){
            CharSequence seq = new StringBuilder().append(chars[i]);
            if(string.contains(seq)){
                result = true;
            }
        }
        return result;
    }
    public static boolean containsCharArrayAll(String string, KnownChar[] chars){
        boolean result = true;
        for(int i = 0; i<chars.length && result; i++){
            CharSequence seq = new StringBuilder().append(chars[i].getCharacter());
            if(!string.contains(seq)||string.charAt(chars[i].getPlace())!=chars[i].getCharacter()){
                result = false;
            }
        }
        return result;
    }
    public static void getCharMap() throws IOException {
        File file = new File("src/main/resources/LettersByPercent.csv");
        var in = new BufferedReader(new FileReader(file.getPath()));
        var data = in.readLine();
        while(data!=null){
            var x = data.split(",");
            charMap.put(x[0]+","+x[1], Integer.parseInt(x[2]));
            data = in.readLine();
        }
        in.close();
    }
    public static ArrayList<String> getWordList() throws FileNotFoundException {
        File file = new File("src/main/resources/Mystery Words.csv");
        Scanner in = new Scanner(file);
        var words = new ArrayList<String>();
        while(in.hasNextLine()){
            words.add(in.nextLine());
        }
        return words;
    }
    public static float test(int times) throws IOException {
        long startTime = System.currentTimeMillis();
        var summary = new ArrayList<RoundSummary>();
        getCharMap();
        var wordList = getWordList();
        float succeeded = 0;
        for(float i = 0; i<times; i++){
            var word = wordList.get((int) i/*(new Random().nextInt() & Integer.MAX_VALUE) % wordList.size()*/);
            var roundSummary = new RoundSummary();
            roundSummary.setAnswer(word);
            roundSummary.setAnswerValue(evaluateString(word, 10));
            System.out.println(ANSI.Red+word+", "+evaluateString(word, 10)+ANSI.Reset);
            var badChars= new ArrayList<Character>();
            var unknownChars = new ArrayList<KnownChar>();
            var knownChars = new ArrayList<KnownChar>();
            String guess = "UR MOM LMAO";
            options = new ArrayList<>(Arrays.asList(permOptions));
            for(int j = 0; j<6; j++){
                guess = generateStr(convert(badChars),convertKnownChar(unknownChars),convertKnownChar(knownChars),j);
                roundSummary.addGuess(guess);
                roundSummary.addValue(evaluateString(guess,j ));
                if(guess.equals(word)){
                    break;
                }
                knownChars.addAll(getKnownChars(word,guess));
                for(var e : knownChars){
                    guess = guess.replace(new StringBuilder().append(e.getCharacter()),"");
                }
                unknownChars.addAll(getUnknownChars(word,guess));
                for(var e : unknownChars){
                    guess = guess.replace(new StringBuilder().append(e.getCharacter()),"");
                }
                badChars.addAll(convert(guess.toCharArray()));
                System.out.println(ANSI.Cyan+guess+ANSI.Reset);
            }
            int percent = (int) ((i/times)*100);
            if(guess.equals(word)){
                System.out.println();
                roundSummary.setVictory(true);
                System.out.println(ANSI.Blue+"Succeeded!"+ANSI.Green+(percent)+ANSI.Reset);
                System.out.println();
                succeeded++;
            } else{
                roundSummary.setVictory(false);
                System.out.println();
                int x = 0;
                try {
                    while(true){
                        x++;
                        guess = generateStr(convert(badChars),convertKnownChar(unknownChars),convertKnownChar(knownChars),x);
                        if(guess.equals(word)){
                            break;
                        }
                        knownChars.addAll(getKnownChars(word,guess));
                        for(var e : knownChars){
                            guess = guess.replace(new StringBuilder().append(e.getCharacter()),"");
                        }
                        unknownChars.addAll(getUnknownChars(word,guess));
                        for(var e : unknownChars){
                            guess = guess.replace(new StringBuilder().append(e.getCharacter()),"");
                        }
                        badChars.addAll(convert(guess.toCharArray()));
                        System.out.println(ANSI.Cyan+" "+guess+ANSI.Reset);
                    }
                } catch (NullPointerException e) {
                    System.err.println("Never Completes");
                }
                System.out.println();
                System.out.println(ANSI.Blue+"Failed! "+(x+6)+" "+ANSI.Green+(percent)+ANSI.Reset);
                System.out.println();
            }
            summary.add(roundSummary);
        }
        File logFile = new File("src/main/resources/logs/"+ new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss'.psv'").format(new Date()));
        //noinspection ResultOfMethodCallIgnored
        logFile.createNewFile();
        var myWriter = new FileWriter(logFile);
        myWriter.write("answer"+"| "+"answerValue"+"| "+"victory"+"| "+ "guesses" +"| "+ "values"+"| "+"rounds"+System.getProperty("line.separator"));
        for(var v : summary){
            myWriter.write(v+System.getProperty("line.separator"));
        }
        myWriter.close();
        long endTime = System.currentTimeMillis();
        System.out.println(ANSI.Bright_Red+"Total time is: "+(endTime-startTime)+"ms"+ANSI.Reset);
        float x = succeeded/times;
        return (x*100);
    }
    public static ArrayList<KnownChar> getKnownChars(String word, String guess){
        var result = new ArrayList<KnownChar>();
        for(int i = 0; i<word.length(); i++){
            if(word.charAt(i)==guess.charAt(i)){
                result.add(new KnownChar(word.charAt(i),i));
            }
        }
        return result;
    }
    public static ArrayList<KnownChar> getUnknownChars(String word, String guess){
        var result = new ArrayList<KnownChar>();
        for(var c : word.toCharArray()){
            if (guess.contains(new StringBuilder().append(c))){
                result.add(new KnownChar(c,word.indexOf(c)));
            }
        }
        return result;
    }
    public static char[] convert(ArrayList<Character> chars){
        var result = new char[chars.size()];
        for(int i = 0; i< chars.size(); i++){
            result[i] = chars.get(i);
        }
        return result;
    }
    public static ArrayList<Character> convert(char[] chars){
        var result = new ArrayList<Character>();
        for(var c : chars){
            result.add(c);
        }
        return result;
    }
    public static KnownChar[] convertKnownChar(ArrayList<KnownChar> chars){
        var result = new KnownChar[chars.size()];
        for(int i = 0; i< chars.size(); i++){
            result[i] = chars.get(i);
        }
        return result;
    }
    @SuppressWarnings("unused")
    public static void getAverages() throws FileNotFoundException {
        var file = new File("src/main/resources/Mystery Words.csv");
        var in = new Scanner(file);
        var map = new HashMap<Character,Integer>();
        while(in.hasNextLine()){
            var data = in.nextLine();
            for(var c : data.toCharArray()) {
                if (map.containsKey(c)) {
                    //noinspection ConstantConditions
                    map.compute(c, (k, v) -> v + 1);
                } else {
                    map.put(c, 1);
                }
            }
        }
        float x = 0;
        for(var key:map.keySet()){
            x+=map.get(key);
        }
        for(var key:map.keySet()){
            System.out.println(key+","+map.get(key));
        }
    }
    @SuppressWarnings({"unused", "ConstantConditions"})
    public static void getBetterAverages() throws FileNotFoundException{
        var file = new File("src/main/resources/Guessable Words.csv");
        var in = new Scanner(file);
        var map = new HashMap<String,Integer>();
        while(in.hasNextLine()){
            var data = in.nextLine();
            for(int i = 0; i<data.length();i++){
                var ch = data.charAt(i);
                if(map.containsKey(ch+","+i)){
                    map.compute(ch+","+i, (k,v)-> v+1);
                }else{
                    map.put(ch+","+i,1);
                }
            }
        }
        file = new File("src/main/resources/Mystery Words.csv");
        in = new Scanner(file);
        var mapTwo = new HashMap<String,Integer>();
        while(in.hasNextLine()){
            var data = in.nextLine();
            for(int i = 0; i<data.length();i++){
                var ch = data.charAt(i);
                if(mapTwo.containsKey(ch+","+i)){
                    mapTwo.compute(ch+","+i, (k,v)-> v+1);
                }else{
                    mapTwo.put(ch+","+i,1);
                }
            }
        }
        for(var key: mapTwo.keySet()){
            map.compute(key,(k,v)-> mapTwo.get(key) + map.get(key));
        }
        for(var key: map.keySet()){
            System.out.println(key+","+map.get(key));
        }
    }
}
