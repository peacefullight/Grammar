import java.util.*;
import java.util.regex.*;

public class IdentifierLexer {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        input.useDelimiter(";"); // 将输入流的分隔符设置为';'，表示每个表达式以分号结束

        Pattern pattern = Pattern.compile("[A-Za-z]([A-Za-z]|[0-9])*" // 标识符
                + "|[0-9]+" // 整数
                + "|=" +    // 运算符和标点符号
                "|\\+" +
                "|\\*{2}" +
                "|\\*" +
                "|," +
                "|\\(" +
                "|\\)" +
                "|;" +
                "|[^A-Za-z0-9=+*,();]"); // 匹配任意字符，但排除字母、数字、运算符、标点符号

        while(input.hasNext()) { // 循环读入每个表达式
            String expression = input.next().trim(); // 读入并去除表达式两端的空格
            if(expression.length() > 0) { // 若表达式不为空，则进行分词处理
                Matcher matcher = pattern.matcher(expression);
                while(matcher.find()) { // 搜索字符串中所有匹配的单词
                    String token = matcher.group();
                    if(token.matches("[A-Za-z]([A-Za-z]|[0-9])*")) { // 判断是否为标识符
                        System.out.println(token + " identifier"); // 输出标识符
                    } else if(token.matches("[0-9]+")) { // 判断是否为整数
                        System.out.println(token + " integer"); // 输出整数
                    } else if(token.equals("=")) { // 判断是否为赋值符号
                        System.out.println(token + " operator"); // 输出赋值符号
                    } else if(token.equals("+")) { // 判断是否为加号
                        System.out.println(token + " operator"); // 输出加号
                    } else if(token.matches("\\*{2}|\\*")) { // 判断是否为乘方符号
                        System.out.println(token + " operator"); // 输出乘方符号
                    } else if(token.equals(",")) { // 判断是否为逗号
                        System.out.println(token + " mark"); // 输出逗号
                    } else if(token.equals("(")) { // 判断是否为左括号
                        System.out.println(token + " mark"); // 输出左括号
                    } else if(token.equals(")")) { // 判断是否为右括号
                        System.out.println(token + " mark"); // 输出右括号
                    } else if(token.equals(";")) { // 判断是否为分号
                        System.out.println(token + " mark"); // 输出分号
                        System.out.println(); // 输出一个空行，以便与下一个表达式分隔开
                    } else {
                        System.out.println(token + " invalid token"); // 输出无效的单词
                    }
                }
            }
        }
        input.close();
    }
}
