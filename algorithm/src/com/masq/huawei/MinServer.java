package com.masq.huawei;

/**
 * 公司创新实验室正在研究如何最小化资源成本， 最大化资源利用率， 请你设计算法帮他们解决一个任务混部问题： <br/>
 * 有 taskNum 项任务， 每个任务有开始时间（startTime） ， 结束时间（endTime） ， 并行度(parallelism)三个属性,<br/>
 * 并行度是指这个任务运行时将会占用的服务器数量， 一个服务器在每个时刻可以被任意任务使用但最多被一个任务占用， 任务运行完会立即释放（结束时刻不占用）,<br/>
 * 任务混部问题是指给定一批任务， 让这批任务由同一批服务器承载运行， 请你计算完成这批任务混部最少需要多少服务器， 从而最大化控制资源成本。<br/>
 * 输入描述: <br/>
 * 第一行输入为 taskNum， 表示有 taskNum 项任务<br/>
 * 接下来 taskNum 行， 每行三个整数， 表示每个任务的开始时间（startTime） ， 结束时间（endTime） ， 并行度(parallelism)<br/>
 * 输出描述: <br/>
 * 一个整数， 表示最少需要的服务器数量<br/>
 *
 * eg: <br/>
 * 3 <br/>
 * 2 3 1<br/>
 * 6 9 2<br/>
 * 0 5 1<br/>
 */
public class MinServer {
    // 判断同一个时间段有多少个任务，同时占用了多少服务器，最多的即为最终结果


}

class Solution {
    public int solution(int taskNum, int[][] example) {
        // 找出重复区间，重复区间中的所有任务并行度之和即最终结果
        // 1.只有一个任务，则直接返回并行度
        if (taskNum == 1) {
            return example[0][3];
        }

        return 0;
    }
}

