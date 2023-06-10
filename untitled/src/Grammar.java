import java.util.*;

public class Grammar {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入文法（多行输入，以“end”结束）：");

        // 从控制台获取多行输入
        StringBuilder input = new StringBuilder();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.equals("end")) {
                break;
            }
            input.append(line).append("\n");
        }
        String grammar = input.toString().trim();

        // 小写字母为终结符
        String patternTerminal = "[a-z]";
        // 非小写字母为非终结符
        String patternNonTerminal = "[A-Z]";

        //增加一句注释
        //增加一个注释
        // 获取终结符
        Set<Character> terminals = new HashSet<>();
        for (char c : grammar.toCharArray()) {
            if (String.valueOf(c).matches(patternTerminal)) {
                terminals.add(c);
            }
        }
        System.out.println("终结符：" + terminals);

        // 获取非终结符
        Set<Character> nonTerminals = new HashSet<>();
        for (char c : grammar.toCharArray()) {
            if (String.valueOf(c).matches(patternNonTerminal)) {
                nonTerminals.add(c);
            }
        }
        System.out.println("非终结符：" + nonTerminals);

        // 判断文法类型

        StringBuilder isType0 = new StringBuilder(); // 0型文法，任意产生式
        StringBuilder isType1 = new StringBuilder(); // 1型文法，上下文相关文法
        StringBuilder isType2 = new StringBuilder(); // 2型文法，上下文无关文法
        StringBuilder isType3 = new StringBuilder(); // 3型文法，正则文法

        String[] grammars = grammar.split("\n");
        for (String s : grammars) {
            for (String rule : s.split("\\|")) {
                String[] parts = rule.split(":");
                String left = parts[0];
                String right = parts[1];
                // 判断0型文法
                if (left.length() > 0 && right.length() > 0
                        && left.matches("[A-Za-z]*")
                        && right.matches("[A-Za-z]*")) {
                    isType0.append("true  ");
                    // 判断1型文法,左部的长度大于等于右部，并且左部至少有一个非终结符
                    if (left.length() <= right.length() && isContain(left, nonTerminals)) {
                        isType1.append("true  ");
                        // 判断2型文法,左部的长度大于等于右部，并且左部不能有终结符
                        if (!isContain(left, terminals)) {
                            isType2.append("true  ");
                            // 判断3型文法,左部的长度必须为1并且只能是非终结符
                            if (left.length() == 1 && !isContain(left, terminals)) {
                                isType3.append("true  ");
                            } else {
                                isType3.append("false ");
                            }
                        } else {
                            isType2.append("false ");
                            isType3.append("false ");
                        }
                    } else {
                        isType1.append("false ");
                        isType2.append("false ");
                        isType3.append("false ");
                    }
                } else {
                    isType0.append("false ");
                    isType1.append("false ");
                    isType2.append("false ");
                    isType3.append("false ");
                }

            }
        }
        System.out.println("isType0: " + isType0);
        System.out.println("isType1: " + isType1);
        System.out.println("isType2: " + isType2);
        System.out.println("isType3: " + isType3);
        // 输出文法类型
        if (!isType3.toString().contains("false")) {
            System.out.println("该文法为3型文法");
        } else if (!isType2.toString().contains("false")) {
            System.out.println("该文法为2型文法");
        } else if (!isType1.toString().contains("false")) {
            System.out.println("该文法为1型文法");
        } else if (!isType0.toString().contains("false")) {
            System.out.println("该文法为0型文法");
        } else {
            System.out.println("错误输入");
        }
    }

    public static boolean isContain (String language, Set set) {
        char[] chars = language.toCharArray();
        for (int i = 0; i < chars.length; i++){
            if (set.contains(chars[i]))
                return true;
        }
        return false;
    }
}
