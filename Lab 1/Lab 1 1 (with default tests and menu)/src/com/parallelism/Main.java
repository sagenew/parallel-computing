package com.parallelism;

import com.parallelism.calc.SerialCalc;
import com.parallelism.calc.ThreadCalc;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//    	menu :
//    	while (true) {
//			System.out.printf("%s%n%s%n%s%n%s%n%s%n",
//					"-----------------------------",
//					"1 - Run default tests.",
//					"2 - Enter values.",
//					"3 - Exit.",
//					"-----------------------------");
			Scanner scanner = new Scanner(System.in);
//			int menuCount = scanner.nextInt();
//			switch (menuCount) {
//				case 1 :
//					runDefaultTests();
//					break;
//				case 2 : {
					System.out.println("Enter n :");
					int n = scanner.nextInt();
					System.out.println("Enter m :");
					int m = scanner.nextInt();
					System.out.println("Enter the number of threads :");
					int threadCount = scanner.nextInt();

					int[][] array = getArray(n, m);
					System.out.println("Array initialised.");
					runCalcs(array, n, m, threadCount);
//					break;
//				}
//				case 3 :
//					System.out.println("Гуменчук Артур Олегович, ІП-71");
//					break menu;
//			}
//		}
    }

	public static int [][] getArray(int n, int m) {
		int [][] array = new int[n][m];
		for (int i = 0; i < n ; i++) {
			for (int j = 0; j < m; j++) {
				array[i][j] = ((int)(Math.random() * 10));
			}
		}
		return array;
	}

	public static void runDefaultTests() {
    	int [] testN = {10, 100, 1000, 10000, 20000};
		int [] testM = {10, 100, 1000, 10000, 20000};
		int [] testThreadCount = {2, 8, 16};

		for (int n : testN)
			for (int m : testM)
				for (int threadCount : testThreadCount)
					runCalcs(getArray(n,m), n, m, threadCount);

	}

	public static void runCalcs(int[][] array,int n, int m, int threadCount) {

		System.out.println("\n" + "N : " + n + ", M : " + m + ", Thread quantity : " + threadCount);
		SerialCalc serialCalc = new SerialCalc(array, n ,m);
		System.out.println("Serial calculation of sum : ");
		serialCalc.calc();
		long serialSum = serialCalc.getSum();
		long serialTime = serialCalc.getCalcTime();
		serialCalc.print();

		ThreadCalc threadCalc = new ThreadCalc(array, n, m, 16);
		System.out.println("Parallel calculation of sum : ");
		threadCalc.calc();
		long threadSum = threadCalc.getSum();
		long threadTime = threadCalc.getCalcTime();
		threadCalc.print();

		System.out.println("Results are equal : " + (serialSum == threadSum));

		double acceleration = (double)serialTime / (double)threadTime;
		System.out.println("Coefficient of acceleration : " + acceleration);

		System.out.println("Coefficient of efficiency : " + acceleration / threadCount);
		System.out.println();
	}

}
