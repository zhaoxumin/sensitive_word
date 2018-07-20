import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author zxm
 * @date 2018/7/20 10:23
 * @name SensitiveWordUtil
 * @description 敏感词过滤
 * 在实现文字过滤的算法中，DFA是唯一比较好的实现算法。DFA即Deterministic Finite Automaton，也就是确定有穷自动机，它是是通过event和当前的state得到下一个state，即event+state=nextstate。
 */
public class SensitiveWordUtil {
    private String ENCODING = "GBK";    //
    public static HashMap sensitiveWordMap;
    public static int minMatchTYpe = 1;      //
    public static int maxMatchType = 2;
    public SensitiveWordUtil(){
        super();
    }

    /**
     * @author
     * @date
     * @version 1.0
     */
    @SuppressWarnings("rawtypes")
    public Map initKeyWord(){
        try {
            Set<String> keyWordSet = readSensitiveWordFile();
            addSensitiveWordToHashMap(keyWordSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sensitiveWordMap;
    }

    /**
     * @author
     * @date
     * @param keyWordSet
     * @version 1.0
     */
    public static void addSensitiveWordToHashMap(Set<String> keyWordSet) {
        sensitiveWordMap = new HashMap(keyWordSet.size());     //³
        String key = null;
        Map nowMap = null;
        Map<String, String> newWorMap = null;
        //µü´úkeyWordSet
        Iterator<String> iterator = keyWordSet.iterator();
        while(iterator.hasNext()){
            key = iterator.next();    //
            nowMap = sensitiveWordMap;
            for(int i = 0 ; i < key.length() ; i++){
                char keyChar = key.charAt(i);       //
                Object wordMap = nowMap.get(keyChar);       //¡

                if(wordMap != null){        //
                    nowMap = (Map) wordMap;
                }
                else{     //²
                    newWorMap = new HashMap<String,String>();
                    newWorMap.put("isEnd", "0");     //
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }

                if(i == key.length() - 1){
                    nowMap.put("isEnd", "1");    //
                }
            }
        }
    }


    /**
     *
     * @return
     * @throws Exception
     */
    private Set<String> readSensitiveWordFile() throws Exception{
        Set<String> set = null;

        File file = new File("D:\\SensitiveWord.txt");    //¶ÁÈ¡ÎÄ¼þ
        InputStreamReader read = new InputStreamReader(new FileInputStream(file),ENCODING);
        try {
            if(file.isFile() && file.exists()){      //
                set = new HashSet<String>();
                BufferedReader bufferedReader = new BufferedReader(read);
                String txt = null;
                while((txt = bufferedReader.readLine()) != null){    //
                    set.add(txt);
                }
            }
            else{         //
                throw new Exception("");
            }
        } catch (Exception e) {
            throw e;
        }finally{
            read.close();     //¹Ø±ÕÎÄ¼þÁ÷
        }
        return set;
    }

    public static int CheckSensitiveWord(String txt, int beginIndex, int matchType){
        boolean  flag = false;    //
        int matchFlag = 0;     //
        char word = 0;
        Map nowMap = sensitiveWordMap;
        for(int i = beginIndex; i < txt.length() ; i++){
            word = txt.charAt(i);
            nowMap = (Map) nowMap.get(word);
            if(nowMap != null){
                matchFlag++;
                if("1".equals(nowMap.get("isEnd"))){       //
                    flag = true;       //
                    if(SensitiveWordUtil.minMatchTYpe == matchType){    //
                        break;
                    }
                }
            }
            else{     //
                break;
            }
        }
        if(matchFlag < 2 || !flag){        //
            matchFlag = 0;
        }
        return matchFlag;
    }

    public static Set<String> getSensitiveWord(String txt , int matchType){
        Set<String> sensitiveWordList = new HashSet<String>();

        for(int i = 0 ; i < txt.length() ; i++){
            int length = CheckSensitiveWord(txt, i, matchType);
            if(length > 0){
                sensitiveWordList.add(txt.substring(i, i+length));
                i = i + length - 1;
            }
        }

        return sensitiveWordList;
    }
}
