import java.util.HashSet;
import java.util.Set;

/**
 * @author zxm
 * @date 2018/7/20 10:25
 * @name BBTest
 * @description description
 */
public class BBTest {
    public static void main(String[] args){
        Set<String> sensitiveWordSet=new HashSet<String>();
        //初始化敏感词库
        sensitiveWordSet.add("三级");
        sensitiveWordSet.add("傻X");
        sensitiveWordSet.add("钱多人傻");
        sensitiveWordSet.add("码农");
        sensitiveWordSet.add("阿里巴巴");
        sensitiveWordSet.add("杭州");
        sensitiveWordSet.add("马云");
        SensitiveWordUtil.addSensitiveWordToHashMap(sensitiveWordSet);
        System.out.println("需要过滤的敏感词数量：[" + SensitiveWordUtil.sensitiveWordMap.size()+"]");
        String string="杭州想着想着想着想着，阿里巴巴与四十大盗，小马云的委屈委屈委屈委屈问问对方给返回结果见过好几个吃不吃VB从VBVC，东方大饭店被告人，码农是的撒旦";
        Set<String> set =  new HashSet<String>();
        set.addAll(SensitiveWordUtil.getSensitiveWord(string,1));
        System.out.println("语句：["+string+"]的长度为：["+string.length()+"],包含["+set.size()+"]个敏感词为："+set);

    }

}
