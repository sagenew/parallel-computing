package com.parallelism;

import com.parallelism.calc.SerialCalc;
import com.parallelism.calc.ThreadCalc;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter n :");
		int n = scanner.nextInt();
		System.out.println("Enter m :");
		int m = scanner.nextInt();
		System.out.println("Enter number of threads :");
		int threadsCount = scanner.nextInt();

		int [][] array = getArray(n, m);
		System.out.println("Array initialised.");

		SerialCalc serialCalc = new SerialCalc(array, n ,m);
		System.out.println("Serial calculation of sum : ");
		serialCalc.calc();
		long serialSum = serialCalc.getSum();
		long serialTime = serialCalc.getCalcTime();
		serialCalc.print();

		ThreadCalc threadCalc = new ThreadCalc(array, n, m, threadsCount);
		System.out.println("Parallel calculation of sum : ");
		threadCalc.calc();
		long threadSum = threadCalc.getSum();
		long threadTime = threadCalc.getCalcTime();
		threadCalc.print();

		System.out.println("Results are equal : " + (serialSum == threadSum));

		double acceleration = (double)serialTime / (double)threadTime;
		System.out.println("Coefficient of acceleration : " + acceleration);
		System.out.println("Coefficient of efficiency : " + acceleration/threadsCount);
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

}
