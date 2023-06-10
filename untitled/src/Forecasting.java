import java.util.*;

//预测分析程序实现
//i+i*i#
//i*i+i#
public class Forecasting {

    private static Table table = new Table();

    private static Stack<Character> analysisStack = new Stack<>();

    private static Stack<Character> inputStack = new Stack<>();

    public static void main(String[] args) {
        Forecasting.InitData();
        Forecasting.AnaDO();
    }

    public static void InitData() {
        Analysis analysis = new Analysis('A');
        analysis.add('i', "CB");
        analysis.add('(', "CB");
        table.add(analysis);

        Analysis analysis1 = new Analysis('B');
        analysis1.add('+', "+CB");
        analysis1.add(')', "null");
        analysis1.add('#', "null");
        table.add(analysis1);

        Analysis analysis2 = new Analysis('C');
        analysis2.add('i', "ED");
        analysis2.add('(', "ED");
        table.add(analysis2);

        Analysis analysis3 = new Analysis('D');
        analysis3.add('+', "null");
        analysis3.add('*', "*ED");
        analysis3.add(')', "null");
        analysis3.add('#', "null");
        table.add(analysis3);

        Analysis analysis4 = new Analysis('E');
        analysis4.add('i', "i");
        analysis4.add('(', "(A)");
        table.add(analysis4);

        Scanner input = new Scanner(System.in);
        String expression = input.next().trim(); // 读入并去除表达式两端的空格
        char[] chars = expression.toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            inputStack.push(chars[i]);
        }

        analysisStack.push('#');
        analysisStack.push(table.getAnalysis(0).getFirstSymbol());

        System.out.println("步骤" + "\t" + "分析栈" + "\t" + "剩余字符串" + "\t" + "推导所用产生式或匹配");
    }

    public static void AnaDO() {
        int count = 0;
        while (!analysisStack.empty() && !inputStack.empty()) {
            count++;
            Character first = analysisStack.peek();
            Character inputFirst = inputStack.peek();

            if (first.equals(inputFirst)) {
                output(count, "'" + first + "'" + "匹配");
                analysisStack.pop();
                inputStack.pop();
                continue;
            }

            Analysis analysis = table.getAnalysisByFirst(first);
            output(count, first + "->" + analysis.getRight(inputFirst));

            if (!analysis.getRight(inputFirst).equals("null")) {
                char[] replace = analysis.getRight(inputFirst).toCharArray();
                analysisStack.pop();
                for (int i = replace.length - 1; i >= 0; i--) {
                    analysisStack.push(replace[i]);
                }
            } else {
                analysisStack.pop();
            }

        }
        if (analysisStack.empty() && inputStack.empty()) {
            System.out.println("此输入串匹配");
        } else {
            System.out.println("此输入串不匹配");
        }
    }

    public static void output(int count, String method) {
        StringBuilder inputs = new StringBuilder();
        StringBuilder anaS = new StringBuilder();
        for (Character character : inputStack) {
            inputs.append(character);
        }
        for (Character character : analysisStack) {
            anaS.append(character);
        }

        System.out.println(count + "\t\t" + anaS + "\t\t" + inputs + "\t\t" + method);
    }
}

class Analysis {
    private Character firstSymbol;
    private Map<Character, String> map = new HashMap<>();

    public Analysis(Character firstSymbol) {
        this.firstSymbol = firstSymbol;
    }

    public void add(Character terminal, String right) {
        map.put(terminal, right);
    }

    public String getRight(Character terminal) {
        if (map.get(terminal) != null) {
            if (map.containsKey(terminal) && !map.get(terminal).equals("null")) {
                return map.get(terminal);
            } else if (map.get(terminal).equals("null")){
                return "null";
            } else {
                System.out.println("右部输入错误");
                return null;
            }
        } else {
            System.out.println("无法查找到对应的终结符" + terminal);
            System.exit(0);
            return null;
        }
    }

    public Character getFirstSymbol() {
        return firstSymbol;
    }
}

class Table {
    private List<Analysis> analysisList = new ArrayList<>();

    public void add(Analysis analysis) {
        analysisList.add(analysis);
    }

    public boolean contain(Analysis analysis) {
        return analysisList.contains(analysis);
    }

    public Analysis getAnalysis(int i) {
        return analysisList.get(i);
    }

    public Analysis getAnalysisByFirst(Character first) {
        for (Analysis analysis : analysisList) {
            if (analysis.getFirstSymbol().equals(first))
                return analysis;
        }
        System.out.println("无法找到左部为" + first + "的产生式");
        System.exit(0);
        return null;
    }
}



