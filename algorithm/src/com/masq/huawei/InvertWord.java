package com.masq.huawei;

/**
 * 单词倒叙<br/>
 * 题目描述<br/>
 * 输入单行英文句子， 里面包含英文字母， 空格以及,.?三种标点符号， 请将句子内每个单词进行倒序， 并输出倒序后的语句<br/>
 * 输入描述<br/>
 * 输入字符串 S， S 的长度 1≤N≤100<br/>
 * 输出描述<br/>
 * 输出逆序后的字符串<br/>
 */
public class InvertWord {

    public static void main(String[] args) {
        InvertWordSolution solution = new InvertWordSolution();
        String sentence = "woh era uoy ? I ma enif.";
        System.out.println(solution.solution(sentence));
    }
}

class InvertWordSolution {

    public String solution(String sentence) {
        StringBuilder result = new StringBuilder();
        // 字符插入位置
        int start = 0;
        for (int i = 0; i < sentence.length(); i++) {
            // 如果是普通字符，插入到指定位置，如果是指定标点符号，则追加到后面
            char c = sentence.charAt(i);
            if (c == ',' || c == '.' || c == '?' || c == ' ') {
                start = i+1;
                result.append(c);
                continue;
            }
            result.insert(start, c);
        }
        return result.toString();
    }
}
