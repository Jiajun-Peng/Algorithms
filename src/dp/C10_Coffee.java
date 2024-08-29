package dp;

import java.util.Arrays;
import java.util.PriorityQueue;

public class C10_Coffee {

    public static int coffee(int n, int[] coffeeMachines, int timeA, int timeB) {
        if (coffeeMachines == null || coffeeMachines.length == 0 || n < 0) {
            return 0;
        }

        int[] cupFinishDrinkTimes = coffeeMakeTask(n, coffeeMachines);

        return washCup(cupFinishDrinkTimes, timeA, timeB);
    }

    private static int[] coffeeMakeTask(int n, int[] coffeeMachines) {
        PriorityQueue<CoffeeMachine> machines = new PriorityQueue<>(new CoffeeMachine.Comparator());
        for (int coffeeMachine : coffeeMachines) {
            machines.add(new CoffeeMachine(0, coffeeMachine));
        }

        int[] cupFinishDrinkTimes = new int[n];
        for (int i = 0; i < n; i++) {
            CoffeeMachine coffeeMachine = machines.poll();
            int getCoffeeTime = coffeeMachine.makeNextCoffeeTime();
            cupFinishDrinkTimes[i] = getCoffeeTime;
            machines.add(coffeeMachine);
        }
        return cupFinishDrinkTimes;
    }

    private static int washCup(int[] cupFinishDrinkTimes, int timeA, int timeB) {
        return washCupByRecursive(cupFinishDrinkTimes, timeA, timeB, 0, 0);
    }

    private static int washCupByRecursive(int[] cupFinishDrinkTimes, int machineTime, int manualTime, int curCupIndex,
                                          int nextWashMachineFreeTime) {
        if (curCupIndex == cupFinishDrinkTimes.length) {
            return 0;
        }

        // 最后一杯洗完的时间
//        if (curCupIndex == cupFinishDrinkTimes.length - 1) {
//            return Math.min(Math.max(nextWashMachineFreeTime, cupFinishDrinkTimes[curCupIndex]) + machineTime,
//                    cupFinishDrinkTimes[curCupIndex] + manualTime);
//        }

        int finishDrinkTime = cupFinishDrinkTimes[curCupIndex];
        // 选择使用机器洗
        int machineWashOverTime = Math.max(finishDrinkTime, nextWashMachineFreeTime) + machineTime;
        int machineContinueWashOverTime = washCupByRecursive(cupFinishDrinkTimes, machineTime, manualTime, curCupIndex + 1, machineWashOverTime);
        // 因为这里时间是时间线上的时间点，所以取最大值，而不是两个时间相加，因为可能当前杯子比后面的杯子还要后洗完
        int overTime1 = Math.max(machineWashOverTime, machineContinueWashOverTime);

        // 选择人工洗
        int manualWashOverTime = finishDrinkTime + manualTime;
        int manualContinueWashOverTime = washCupByRecursive(cupFinishDrinkTimes, machineTime, manualTime, curCupIndex + 1,
                nextWashMachineFreeTime);
        int overTime2 = Math.max(manualWashOverTime, manualContinueWashOverTime);

        return Math.min(overTime1, overTime2);
    }

    public static int coffeeByDp(int n, int[] coffeeMachines, int timeA, int timeB) {
        if (coffeeMachines == null || coffeeMachines.length == 0 || n < 0) {
            return 0;
        }
        int[] cupFinishDrinkTimes = coffeeMakeTask(n, coffeeMachines);

        return washCupByDp(cupFinishDrinkTimes, timeA, timeB);
    }

    public static int washCupByDp(int[] cupFinishDrinkTimes, int machineTime, int manualTime) {
        int maxWashFreeTime = 0;
        for (int finishDrinkTime : cupFinishDrinkTimes) {
            maxWashFreeTime = Math.max(finishDrinkTime, maxWashFreeTime) + machineTime;
        }

        int length = cupFinishDrinkTimes.length;
        int[][] dp = new int[length + 1][maxWashFreeTime + 1];
//        for (int i = 0; i <= maxWashFreeTime; i++) {
//            dp[length - 1][i] = Math.min(Math.max(maxWashFreeTime, cupFinishDrinkTimes[length - 1]) + machineTime,
//                    cupFinishDrinkTimes[length - 1] + manualTime);
//        }

        for (int cupIndex = length - 1; cupIndex >= 0; cupIndex--) {
            // freeTime 的遍历顺序并不重要，这里只需要列出所有的可能性，因为在递归中，freeTime 的值也是浮动变化的
            for (int freeTime = 0; freeTime <= maxWashFreeTime; freeTime++) {
                int finishDrinkTime = cupFinishDrinkTimes[cupIndex];
                // 选择使用机器洗
                int machineWashOverTime = Math.max(finishDrinkTime, freeTime) + machineTime;
                if (machineWashOverTime > maxWashFreeTime) continue;
                int restTime = dp[cupIndex + 1][machineWashOverTime];
                int overTime1 = Math.max(machineWashOverTime, restTime);

                // 选择人工洗
                int handWashOverTime = finishDrinkTime + manualTime;
                int restTime1 = dp[cupIndex + 1][freeTime];
                int overTime2 = Math.max(handWashOverTime, restTime1);

                dp[cupIndex][freeTime] = Math.min(overTime1, overTime2);
            }
        }
        return dp[0][0];
    }

    static class CoffeeMachine {
        int nextFreeTime; // 下一次空闲的时间
        int workTime; // 制作一杯咖啡所需的时间

        public CoffeeMachine(int nextFreeTime, int workTime) {
            this.nextFreeTime = nextFreeTime;
            this.workTime = workTime;
        }

        public int makeNextCoffeeTime() {
            int nextCupCoffeeTime = nextFreeTime + workTime;
            nextFreeTime = nextCupCoffeeTime;
            return nextCupCoffeeTime;
        }

        static class Comparator implements java.util.Comparator<CoffeeMachine> {
            @Override
            public int compare(CoffeeMachine o1, CoffeeMachine o2) {
                return (o1.nextFreeTime + o1.workTime) - (o2.nextFreeTime + o2.workTime);
            }
        }
    }


    public static void main(String[] args) {
        int[] coffeeMachines = {5, 6, 8, 8, 4};
        int n = 4;
        int timeA = 5;
        int timeB = 6;
        System.out.println(coffee(n, coffeeMachines, timeA, timeB)); // 13
        System.out.println(coffeeByDp(n, coffeeMachines, timeA, timeB));
    }


}
