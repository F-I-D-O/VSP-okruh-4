
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author david_000
 */
public class Program {
    
    private static final int CAPACITY = 1000000;
    
    private static final int NUMBER_OF_TEST_SAMPLES = 1000;
    
    private static ArrayList<Integer> arrayList;
    
    private static LinkedList<Integer> linkedList;
    
    private static long[] arrayListResults;
    
    private static long[] linkedListResults;
    
    private static HashMap<Long,Integer> arrayListResultsHistogram;
    
    private static HashMap<Long,Integer> linkedListResultsHistogram;
    
    
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        arrayListResults = new long[NUMBER_OF_TEST_SAMPLES];
        linkedListResults = new long[NUMBER_OF_TEST_SAMPLES];
        for(int sample = 0; sample < NUMBER_OF_TEST_SAMPLES; sample++){
            doTestSmple(sample);
        }        
        writeReults();
    }
    
    public static void writeReults() throws FileNotFoundException, UnsupportedEncodingException
    {
        PrintWriter writer = new PrintWriter("results.txt", "UTF-8");
        arrayListResultsHistogram = new HashMap<>();
        linkedListResultsHistogram = new HashMap<>();
        for(int sample = 0; sample < NUMBER_OF_TEST_SAMPLES; sample++){
            long roundArrayListTime = Math.round(TimeUnit.NANOSECONDS.toMicros(arrayListResults[sample]) / 100) * 100;
            arrayListResultsHistogram.put(roundArrayListTime, arrayListResultsHistogram.get(roundArrayListTime) == null 
                    ? 1 : arrayListResultsHistogram.get(roundArrayListTime) + 1);
            long roundLinkedListTime = Math.round(TimeUnit.NANOSECONDS.toMicros(linkedListResults[sample]) / 100) * 100;
            linkedListResultsHistogram.put(roundLinkedListTime, linkedListResultsHistogram.get(roundLinkedListTime) == null
                    ? 1 : linkedListResultsHistogram.get(roundLinkedListTime) + 1);
        }
        for (Map.Entry<Long, Integer> entrySet : arrayListResultsHistogram.entrySet()) {
            Long key = entrySet.getKey();
            Integer value = entrySet.getValue();
            writer.println(key + "," + value);
        }
        
        writer.println();
        writer.println();
        writer.println();
        writer.println();
        
        for (Map.Entry<Long, Integer> entrySet : linkedListResultsHistogram.entrySet()) {
            Long key = entrySet.getKey();
            Integer value = entrySet.getValue();
            writer.println(key + "," + value);
        }
        
        
        writer.close();
    }

    private static void doTestSmple(int sample) {
        arrayList = new ArrayList<>(CAPACITY);
        linkedList = new LinkedList<>();
        Random r = new Random(sample);
        for(int i = 0; i < CAPACITY; i++){
            Integer number = r.nextInt();
            arrayList.add(number);
            linkedList.add(number);
        }
        long startTime = System.nanoTime();
        Object[] arrayListArray = arrayList.toArray();
        long stopTime = System.nanoTime();
//        long arrayListTime = TimeUnit.NANOSECONDS.toMillis(stopTime - startTime);
        long arrayListTime = stopTime - startTime;
        startTime = System.nanoTime();
        Object[] linkedListArray = linkedList.toArray();
        stopTime = System.nanoTime();
//        long linkedListTime = TimeUnit.NANOSECONDS.toMillis(stopTime - startTime);
        long linkedListTime = stopTime - startTime;
        System.out.println("AL time: " + arrayListTime + " LL time: " + linkedListTime);
        arrayListResults[sample] = arrayListTime;
        linkedListResults[sample] = linkedListTime;
    }
}
