package iecs.fcu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ReadFile {
	
	private int[] trainInput = new int [800];
	private int[] trainOutput = new int [800];
	private int[] testInput = new int[140];
	private int[] testOutput = new int [140];
	
	Scanner scanner = null;
	
	//readfile ues bufferedreader
	/*
	public void readfile() throws FileNotFoundException{
		try {
			FileReader fileReader = new FileReader("src/iecs/fcu/data.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuilder stringBuilder = new StringBuilder();
			
			String line;
			while((line = bufferedReader.readLine())!=null){
				//System.out.println(line);
				//temp[i] = line;
				//System.out.println(data[i]);
				//i++;
				stringBuilder.append(line);
				stringBuilder.append(" ");
			}
			//System.out.println(stringBuilder.toString());
			temp = stringBuilder.toString();
			bufferedReader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	*/
	//read the data of the file use class Scanner
	public void readfile(){
		try {
			scanner = new Scanner(new File("src/iecs/fcu/data.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//此 for loop 決定測資多少(訓練 and 測試)
		for(int i=0;i<800;i++){
			trainInput[i] = scanner.nextInt();
			trainOutput[i] = scanner.nextInt();
		}
		for(int i=0;i<140;i++){
			testInput[i] = scanner.nextInt();
			testOutput[i] = scanner.nextInt();
		}
	}
	public int findMin(int argc[]){
		int temp = 0 ;
		for(int i=0;i<argc.length;i++){
			if(argc[i] < temp){
				temp = argc[i];
			}
		}
		return temp;
	}
	public int findMax(int argc[]){
		int temp = -1;
		for(int i=0;i<argc.length;i++){
			if(argc[i] > temp){
				temp = argc[i];
			}
		}
		return temp;
	}
	
	public int[] getTrainInput(){
		int[] temp = new int[trainInput.length];
		for(int i=0;i<trainInput.length;i++){
			temp[i] = trainInput[i];
		}
		return temp;
	}
	public int[] getTrainOutput(){
		int[] temp = new int[trainOutput.length];
		for(int i=0;i<trainOutput.length;i++){
			temp[i] = trainOutput[i];
		}
		return temp;
	}
	
	public int[] getTestInput(){
		int[] temp = new int[testInput.length];
		for(int i=0;i<testInput.length;i++){
			temp[i] = testInput[i];
		}
		return temp;
	}
	public int[] getTestOutput(){
		int[] temp = new int[testOutput.length];
		for(int i=0;i<testOutput.length;i++){
			temp[i] = testOutput[i];
		}
		return temp;
	}
}
