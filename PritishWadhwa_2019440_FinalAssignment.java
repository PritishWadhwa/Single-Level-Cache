
/******************************************************************************************
	Name: 			Pritish Wadhwa
  	Roll Number: 	2019440
  	Section: 		A
	Group: 			8
 *****************************************************************************************/

import java.util.Scanner;

/*Class for the fully associative memory*/
class fullyAssociativeCache {
	Scanner input = new Scanner(System.in);
	int cacheLines;
	int blockSize;
	int[][] cacheFA; // 2D array to store the data and the tags in the cache
	int[][] addressFA; // 2D array to store the addresses corresponding to the data
	int[] priority; // 1D array to store the priority of each instruction and maintain the LRU
	// scheme

	fullyAssociativeCache(int cacheLines, int blockSize) {
		this.cacheLines = cacheLines;
		this.blockSize = blockSize;
		cacheFA = new int[cacheLines][blockSize + 1];
		addressFA = new int[cacheLines][blockSize + 1];
		priority = new int[cacheLines];
		for (int i = 0; i < cacheLines; i++) {
			priority[i] = cacheLines;
			for (int j = 0; j < blockSize + 1; j++) {
				cacheFA[i][j] = -1;
				addressFA[i][j] = -1;
			}
		}
	}

	public void run() {
		int choice;
		do {
			System.out.println();
			System.out.println("What do you want to do?");
			System.out.println("1.) Write to address.");
			System.out.println("2.) Read from address.");
			System.out.println(
					"3.) Search from data(all the data must be distinct, otherwise the output will be the first occurance of the value).");
			System.out.println("4.) Print complete cache.");
			System.out.println("5.) Exit");
			System.out.print("Please enter your choice: ");
			choice = input.nextInt();
			switch (choice) {
			case 1:
				writeToAddress();
				break;

			case 2:
				readFromAddress();
				break;

			case 3:
				searchFromData();
				break;

			case 4:
				printCache();
				break;

			default:
				break;
			}
		} while (choice != 5);
	}

	private void searchFromData() {
		System.out.print("Please enter the value whose address is to be searched: ");
		int data = input.nextInt();
		int i;
		int flag = 0;
		for (i = 0; i < cacheLines; i++) {
			for (int j = 1; j < blockSize; j++) {
				if (cacheFA[i][j] == data) {
					System.out.println("The given value is present at address: " + addressFA[i][j]);
					flag = 1;
					break;
				}
			}
			if (flag == 1) {
				break;
			}
		}
		if (i == cacheLines) {
			System.out.println("No such address exists such that it has the given value as its data!");
		}
	}

	private void readFromAddress() {
		System.out.print("Please input the Address: ");
		int actualAddress = input.nextInt();
		int offset = actualAddress % blockSize;
		int tag = actualAddress / blockSize;
		int i;
		for (i = 0; i < cacheLines; i++) {
			if (cacheFA[i][0] == tag) {
				System.out.println("The value at address " + actualAddress + " is " + cacheFA[i][offset + 1]);
				break;
			}
		}
		if (i == cacheLines) {
			System.out.println("The given address is not present in the cache. It was a miss!");
		}
		for (i = 0; i < cacheLines; i++) {
			priority[i]--;
		}
	}

	private void printCache() {
		System.out.println("The current cache is: ");
		int i;
		for (i = 0; i < cacheLines; i++) {
			if (cacheFA[i][0] == -1) {
				break;
			} else {
				for (int j = 1; j < blockSize + 1; j++) {
					if (cacheFA[i][j] != -1) {
						System.out.println("Address: " + addressFA[i][j] + "\tData: " + cacheFA[i][j]);
					}
				}
			}
		}
		if (i == 0) {
			System.out.println("Cache is Empty!");
		}
	}

	private void writeToAddress() {
		System.out.print("Please input the Address: ");
		int actualAddress = input.nextInt();
		System.out.print("Please input the Data: ");
		/* Extracting various components out of the address */
		int data = input.nextInt();
		int offset = actualAddress % blockSize;
		int tag = actualAddress % blockSize;
		int minP = priority[0];
		int itr = 0;
		int i;
		for (i = 1; i < cacheLines; i++) {
			if (minP < priority[i]) {
				minP = priority[i];
				itr = i;
			}
		}
		for (i = 0; i < cacheLines; i++) {
			if (cacheFA[i][0] == -1 || cacheFA[i][0] == tag) {
				break;
			}
		}
		if (i == cacheLines) {
			cacheFA[itr][0] = tag;
			cacheFA[itr][offset + 1] = data;
			addressFA[itr][offset + 1] = actualAddress;
			priority[itr] = cacheLines;
		} else {
			cacheFA[i][0] = tag;
			cacheFA[i][offset + 1] = data;
			addressFA[i][offset + 1] = actualAddress;
			priority[i] = cacheLines;
		}
		for (i = 0; i < cacheLines; i++) {
			priority[i]--;
		}
		System.out.println("Value added to Cache successfully!");
	}
}

class directMappedCache {
	Scanner input = new Scanner(System.in);
	int cacheLines;
	int blockSize;
	int[][] cacheDM;
	int[][] addressDM;

	directMappedCache(int cacheLines, int blockSize) {
		this.cacheLines = cacheLines;
		this.blockSize = blockSize;
		cacheDM = new int[cacheLines][blockSize + 1];
		addressDM = new int[cacheLines][blockSize + 1];
		for (int i = 0; i < cacheLines; i++) {
			for (int j = 0; j < blockSize + 1; j++) {
				cacheDM[i][j] = -1;
				addressDM[i][j] = -1;
			}
		}
	}

	public void run() {
		int choice;
		do {
			System.out.println();
			System.out.println("What do you want to do?");
			System.out.println("1.) Write to address.");
			System.out.println("2.) Read from address.");
			System.out.println(
					"3.) Search from data(all the data must be distinct, otherwise the output will be the first occurance of the value).");
			System.out.println("4.) Print complete cache.");
			System.out.println("5.) Exit");
			System.out.print("Please enter your choice: ");
			choice = input.nextInt();
			switch (choice) {
			case 1:
				writeToAddress();
				break;

			case 2:
				readFromAddress();
				break;

			case 3:
				searchFromData();
				break;

			case 4:
				printCache();
				break;

			default:
				break;
			}
		} while (choice != 5);
	}

	private void searchFromData() {
		System.out.print("Please enter the value whose address is to be searched: ");
		int data = input.nextInt();
		int i;
		int flag = 0;
		for (i = 0; i < cacheLines; i++) {
			for (int j = 1; j < blockSize; j++) {
				if (cacheDM[i][j] == data) {
					System.out.println("The given value is present at address: " + addressDM[i][j]);
					flag = 1;
					break;
				}
			}
			if (flag == 1) {
				break;
			}
		}
		if (i == cacheLines) {
			System.out.println("No such address exists such that it has the given value as its data!");
		}
	}

	private void readFromAddress() {
		System.out.print("Please input the Address: ");
		int actualAddress = input.nextInt();
		int offset = actualAddress % blockSize;
		int addressWithoutOffset = actualAddress / blockSize;
		int index = addressWithoutOffset % cacheLines;
		int tag = addressWithoutOffset / cacheLines;
		int flag = 0;
		if (cacheDM[index][0] == tag && cacheDM[index][offset + 1] != -1) {
			System.out.println("The value at address " + actualAddress + " is " + cacheDM[index][offset + 1]);
			flag = 1;
		}
		if (flag == 0) {
			System.out.println("The given address is not present in the cache. It was a miss!");
		}
	}

	private void printCache() {
		System.out.println("The current cache is: ");
		int flag = 0;
		int i;
		for (i = 0; i < cacheLines; i++) {
			if (cacheDM[i][0] != -1) {
				for (int j = 1; j < blockSize + 1; j++) {
					if (cacheDM[i][j] != -1) {
						System.out.println("Address: " + addressDM[i][j] + "\tData: " + cacheDM[i][j]);
						flag = 1;
					}
				}
			}
		}

		if (flag == 0) {
			System.out.println("Cache is Empty!");
		}
	}

	private void writeToAddress() {
		System.out.print("Please input the Address: ");
		int actualAddress = input.nextInt();
		System.out.print("Please input the Data: ");
		int data = input.nextInt();
		int offset = actualAddress % blockSize;
		int addressWithoutOffset = actualAddress / blockSize;
		int index = addressWithoutOffset % cacheLines;
		int tag = addressWithoutOffset / cacheLines;
		cacheDM[index][0] = tag;
		cacheDM[index][offset + 1] = data;
		addressDM[index][offset + 1] = actualAddress;
		System.out.println("Value added to Cache successfully!");
	}

}

class setAssociativeCache {

	Scanner input = new Scanner(System.in);
	int cacheLines;
	int blockSize;
	int n;
	int[][] cacheSA;
	int[][] addressSA;

	setAssociativeCache(int cacheLines, int blockSize, int n) {
		this.cacheLines = cacheLines;
		this.blockSize = blockSize;
		this.n = n;
		cacheSA = new int[cacheLines][blockSize + 1];
		addressSA = new int[cacheLines][blockSize + 1];
		for (int i = 0; i < cacheLines; i++) {
			for (int j = 0; j < blockSize + 1; j++) {
				cacheSA[i][j] = -1;
				addressSA[i][j] = -1;
			}
		}
	}

	public void run() {
		int choice;
		do {
			System.out.println();
			System.out.println("What do you want to do?");
			System.out.println("1.) Write to address.");
			System.out.println("2.) Read from address.");
			System.out.println(
					"3.) Search from data(all the data must be distinct, otherwise the output will be the first occurance of the value).");
			System.out.println("4.) Print complete cache.");
			System.out.println("5.) Exit");
			System.out.print("Please enter your choice: ");
			choice = input.nextInt();
			switch (choice) {
			case 1:
				writeToAddress();
				break;

			case 2:
				readFromAddress();
				break;

			case 3:
				searchFromData();
				break;

			case 4:
				printCache();
				break;

			default:
				break;
			}
		} while (choice != 5);
	}

	private void searchFromData() {
		System.out.print("Please enter the value whose address is to be searched: ");
		int data = input.nextInt();
		int i;
		int flag = 0;
		for (i = 0; i < cacheLines; i++) {
			for (int j = 1; j < blockSize; j++) {
				if (cacheSA[i][j] == data) {
					System.out.println("The given value is present at address: " + addressSA[i][j]);
					flag = 1;
					break;
				}
			}
			if (flag == 1) {
				break;
			}
		}
		if (i == cacheLines) {
			System.out.println("No such address exists such that it has the given value as its data!");
		}
	}

	private void readFromAddress() {
		System.out.print("Please input the Address: ");
		int actualAddress = input.nextInt();
		int offset = actualAddress % blockSize;
		int addressWithoutOffset = actualAddress / blockSize;
		int index = addressWithoutOffset % (cacheLines / n);
		int addressWithoutOffsetAndIndex = addressWithoutOffset / (cacheLines / n);
		int number = addressWithoutOffsetAndIndex % n;
		int tag = addressWithoutOffset / n;
		int flag = 0;
		if (cacheSA[index * n + number][0] == tag && cacheSA[index * n + number][offset + 1] != -1) {
			System.out.println(
					"The value at address " + actualAddress + " is " + cacheSA[index * n + number][offset + 1]);
			flag = 1;
		}
		if (flag == 0) {
			System.out.println("The given address is not present in the cache. It was a miss!");
		}
	}

	private void printCache() {
		System.out.println("The current cache is: ");
		int flag = 0;
		int i;
		for (i = 0; i < cacheLines; i++) {
			if (cacheSA[i][0] != -1) {
				for (int j = 1; j < blockSize + 1; j++) {
					if (cacheSA[i][j] != -1) {
						System.out.println("Address: " + addressSA[i][j] + "\tData: " + cacheSA[i][j]);
						flag = 1;
					}
				}
			}
		}

		if (flag == 0) {
			System.out.println("Cache is Empty!");
		}
	}

	private void writeToAddress() {
		System.out.print("Please input the Address: ");
		int actualAddress = input.nextInt();
		System.out.print("Please input the Data: ");
		int data = input.nextInt();
		int offset = actualAddress % blockSize;
		int addressWithoutOffset = actualAddress / blockSize;
		int index = addressWithoutOffset % (cacheLines / n);
		int addressWithoutOffsetAndIndex = addressWithoutOffset / (cacheLines / n);
		int number = addressWithoutOffsetAndIndex % n;
		int tag = addressWithoutOffset / n;
		cacheSA[index * n + number][0] = tag;
		cacheSA[index * n + number][offset + 1] = data;
		addressSA[index * n + number][offset + 1] = actualAddress;
		System.out.println("Value added to Cache successfully!");
	}
}

public class PritishWadhwa_2019440_FinalAssignment {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int cacheSize;
		int cacheLines;
		int blockSize;
		int n; // for n-way set associative cache
		System.out.print("Please input the number of Cache Lines: ");
		cacheLines = input.nextInt();
		System.out.print("Please input the block Size: ");
		blockSize = input.nextInt();
		cacheSize = cacheLines * blockSize;
		int cacheNo;
		System.out.println("The following types of caches are availaible:");
		System.out.println("1.) Direct Mapped Cache");
		System.out.println("2.) Fully Associative Cache");
		System.out.println("3.) N-Way Set Associative Cache");
		System.out.print("Please select one of the above: ");
		cacheNo = input.nextInt();
		switch (cacheNo) {

		case 1:
			directMappedCache cacheDM = new directMappedCache(cacheLines, blockSize);
			cacheDM.run();
			break;

		case 2:
			fullyAssociativeCache cacheFA = new fullyAssociativeCache(cacheLines, blockSize);
			cacheFA.run();
			break;

		case 3:
			System.out.print("Please enter the value of n: ");
			n = input.nextInt();
			setAssociativeCache cacheSA = new setAssociativeCache(cacheLines, blockSize, n);
			cacheSA.run();
			break;

		default:
			break;
		}
		input.close();
	}
}