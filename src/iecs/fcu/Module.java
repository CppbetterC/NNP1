package iecs.fcu;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Module {
	/***********
	這是實做一個簡單類神經網路 java code
	function y=a*x^2+b*x+c
	input x;
	output y;
	module use 1 to 2 to 1 
	***********/
	
	//declare the used class name;
	ReadFile rf = new ReadFile();
	
	private int trInputMin , trInputMax ,trOutputMin ,trOutputMax;
	private int teInputMin , teInputMax ,teOutputMin ,teOutputMax;
	private double [][] W1 = new double[2][1];
	private double [][] b = new double[2][1];
	private double [][] W2 = new double[1][2];
	private double [][] tempW2 = new double[1][2];
	private double [][] tempW1 = new double[2][1];
	private double op,fq1,fq2,oq1,oq2,or,temptt1,temptt2;
	private int [] trInput = new int[800];
	private int [] trOutput = new int[800];
	private int [] teInput = new int[140];
	private int [] teOutput = new int[140];
	
	public void initialize(){
		rf.readfile();
		
		trInput = rf.getTrainInput();
		trOutput = rf.getTrainOutput();
		teInput = rf.getTestInput();
		teOutput = rf.getTestOutput();
		
		trInputMin = rf.findMin(trInput);
		trInputMax = rf.findMax(trInput);
		trOutputMin = rf.findMin(trOutput);
		trOutputMax = rf.findMax(trOutput);
		
		teInputMin = rf.findMin(teInput);
		teInputMax = rf.findMax(teInput);
		teOutputMin = rf.findMin(teOutput);
		teOutputMax = rf.findMax(teOutput);
		
		for(int i =0 ; i<2 ; i++){
			for(int j=0;j<1;j++){
				W1[i][j] = (double) (Math.random())*2-1;
				b[i][j] = (double) (Math.random())*2-1;
			}
		}
		for(int i=0;i<1;i++){
			for(int j=0;j<2;j++){
				W2[i][j] = (double) (Math.random())*2-1;
			}
		}
	}
	//在 train forword 時，將input的資料做正規化
	public double trxNormalization(double xinput){
		return (2 * (xinput-trInputMin)/(trInputMax-trInputMin))-1;		
	}
	//在 train backword 時，將output的資料做正規化
	public double tryNormalization(double xoutput){
		double noutput = (2 *((xoutput-trOutputMin)/(trOutputMax-trOutputMin)))-1;
		return noutput;
	}
	public double texNormalization(double xinput){
		return (2 * (xinput-teInputMin)/(teInputMax-teInputMin))-1;		
	}
	//在test時，將output的資料作反正規化(資料還原原本數值)
	public double inverse_ynormalization(double a){
		return (( (a+1) / 2 ) * ( teOutputMax - teOutputMin ) ) + teOutputMin;
	}
	public double  forWord(double argc){
		//layer 1 -> op = u(input);
		//layer 2 -> fq = wu + d;
		//			 Oq = (e^x+e^(-x))/(e^x+e^(-x));
		//layer 3 -> Or = w2 * Oq;
		//X input  Youtput
		
		op = argc;
		//System.out.println("op="+op);
		fq1 = op * W1[0][0];
		fq2 = op * W1[1][0];
		
		fq1 = fq1 + b[0][0];
		fq2 = fq2 + b[1][0];

		oq1 = sigmoid(fq1);
		oq2 = sigmoid(fq2);
		
		or = oq1 * W2[0][0] + oq2 * W2[0][1];
		
		//System.out.println("temp3 = "+temp3);
		
		return or;
		/*
		System.out.println(xx);
		System.out.println("temp :"+oq1);
		System.out.println("temp2 :"+oq2);
		System.out.println("temp3 :"+or);
		System.out.println("-------------------------------");
		*/				
	}
	//backword 時要使用先前紀錄的W1,W2值
	public void tempData(){
		tempW1[0][0] = W1[0][0];
		tempW1[1][0] = W1[1][0];

		tempW2[0][0] = W2[0][0];
		tempW2[0][1] = W2[0][1];
		
		temptt1 = fq1;
		temptt2 = fq2;
	}
	public void backWord(double yy){
		tempData();
		double diff = yy - or;
		
		//displacement 0.0001;
		double disp = 0.0001;
		W2[0][0] = W2[0][0] + disp * diff * oq1;
		W2[0][1] = W2[0][1] + disp * diff * oq2;
		
		b[0][0]=b[0][0] + disp * diff * tempW2[0][0] * (4 / (Math.pow((Math.exp(temptt1) + Math.exp(-temptt1)) , 2)));
		b[1][0]=b[1][0] + disp * diff * tempW2[0][1] * (4 / (Math.pow((Math.exp(temptt2) + Math.exp(-temptt2)) , 2)));
		
		W1[0][0] = W1[0][0] + disp * diff * tempW2[0][0] * (4 / (Math.pow((Math.exp(temptt1) + Math.exp(-temptt1)) , 2))) * op;
		W1[1][0] = W1[1][0] + disp * diff * tempW2[0][1] * (4 / (Math.pow((Math.exp(temptt2) + Math.exp(-temptt2)) , 2))) * op;
	}
	public void trainNN(){
		initialize();
		for (int kk=0; kk<100000; kk++){
			//System.out.println("kk: " + kk);
			for(int i=0;i<800;i++){
				//System.out.println("rf.gettrainInput(i)="+rf.gettrainInput(i));
				forWord(trxNormalization(trInput[i]));
				backWord(tryNormalization(trOutput[i]));
				//System.out.println(xnormalization(rf.gettrainInput(i)));
			}
		}

		/*
		for(int i=0;i<1000;i++){
			constructmodel(1);
			backWord(2);
		}
		System.out.println("Final");
		constructmodel(1);
		*/
	}
	public void testNN(){
		for(int i=0;i<140;i++){
			double aa = forWord(texNormalization(teInput[i]));
			
			System.out.println("原本的output:");
			System.out.println(teOutput[i]);
			System.out.println("NN算出的output:");
			System.out.println(inverse_ynormalization(aa)+"\n");
			
			//System.out.println("-------------------------------------");
			//System.out.println(Math.pow(aa-rf.gettrainOutput(i),2) / 2 );
			//System.out.println("  ");	
		}
	}
	private double sigmoid(double x){
		double Oq = (Math.exp(x)-Math.exp(-x))/(Math.exp(x)+Math.exp(-x));
		//double y = x ;
		return Oq;
	}
}
