package sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Sudoku {

	public static void solve(Integer[][] table){
		int brojac=0;
		for(int i=0;i<table[0].length;i++){
			for(int j=0;j<table.length;j++){
				if(table[i][j]!=0) brojac++;
			}
		}
		ArrayList<Boolean> end=new ArrayList<Boolean>();
		end.add(false);
		solve(table,brojac,end);
	}
	
	//vraca sve vrednosti iz vrste osim table[i][j]
	public static HashSet<Integer> getSameRow(int i,int j,Integer[][] table){
		HashSet<Integer>set=new HashSet<Integer>();
		for(int ii=0;ii<9;ii++)
			if(ii!=j) set.add(table[i][ii]);
		return set;
	}
	
	//vraca sve vrednosti iz kolone osim table[i][j]
	public static HashSet<Integer> getSameColumn(int i,int j,Integer[][] table){
		HashSet<Integer>set=new HashSet<Integer>();
		for(int ii=0;ii<9;ii++)
			if(ii!=i) set.add(table[ii][j]);
		return set;
	}
	
	//0-2,3-5,6-8 su koordinate
	//potrebno je vratiti listu vrednosti iz 3x3 grida osim table[i][j]
	public static HashSet<Integer> getSame3X3Grid(int i,int j,Integer[][] table){
		
		if(i>=0 && i<=2 && j>=0 && j<=2){
			HashSet<Integer>set=new HashSet<Integer>();
			for(int ii=0;ii<9;ii++) 
				if(!(ii/3==i && ii%3==j)) set.add(table[ii/3][ii%3]);
			return set;
		}
		else if(i>=0 && i<=2 && j>=3 && j<=5){
			HashSet<Integer>set=new HashSet<Integer>();
			for(int ii=0;ii<9;ii++) 
				if(!(ii/3==i && ii%3+3==j)) set.add(table[ii/3][ii%3+3]);
			return set;
		}
		else if(i>=0 && i<=2 && j>=6 && j<=8){
			HashSet<Integer>set=new HashSet<Integer>();
			for(int ii=0;ii<9;ii++) 
				if(!(ii/3==i && ii%3+6==j)) set.add(table[ii/3][ii%3+6]);
			return set;
		}
		else if(i>=3 && i<=5 && j>=0 && j<=2){
			HashSet<Integer>set=new HashSet<Integer>();
			for(int ii=0;ii<9;ii++) 
				if(!(ii/3+3==i && ii%3==j)) set.add(table[ii/3+3][ii%3]);
			return set;
		}
		else if(i>=3 && i<=5 && j>=3 && j<=5){
			HashSet<Integer>set=new HashSet<Integer>();
			for(int ii=0;ii<9;ii++) 
				if(!(ii/3+3==i && ii%3+3==j)) set.add(table[ii/3+3][ii%3+3]);
			return set;
		}
		else if(i>=3 && i<=5 && j>=6 && j<=8){
			HashSet<Integer>set=new HashSet<Integer>();
			for(int ii=0;ii<9;ii++) 
				if(!(ii/3+3==i && ii%3+6==j)) set.add(table[ii/3+3][ii%3+6]);
			return set;
		}
		else if(i>=6 && i<=8 && j>=0 && j<=2){
			HashSet<Integer>set=new HashSet<Integer>();
			for(int ii=0;ii<9;ii++) 
				if(!(ii/3+6==i && ii%3==j)) set.add(table[ii/3+6][ii%3]);
			return set;
		}
		else if(i>=6 && i<=8 && j>=3 && j<=5){
			HashSet<Integer>set=new HashSet<Integer>();
			for(int ii=0;ii<9;ii++) 
				if(!(ii/3+6==i && ii%3+3==j)) set.add(table[ii/3+6][ii%3+3]);
			return set;
		}
		else{
			HashSet<Integer>set=new HashSet<Integer>();
			for(int ii=0;ii<9;ii++) 
				if(!(ii/3+6==i && ii%3+6==j)) set.add(table[ii/3+6][ii%3+6]);
			return set;
		}
		
	}
	
	
	public static void solve(Integer[][] table,int brojac,ArrayList<Boolean> end){
		
		Boolean b=true;
		if(brojac==81) {
			end.remove(0);
			end.add(true);
		}
		if(end.get(0)) return;
		else{
			for(int i=0;i<table[0].length;i++){
				for(int j=0;j<table.length;j++){
					if(table[i][j]==0) {
						b=true;
						HashSet<Integer>set=new HashSet<Integer>();
						Collections.addAll(set,1,2,3,4,5,6,7,8,9);
						HashSet<Integer>h=getSame3X3Grid(i,j,table);
						HashSet<Integer>h2=getSameRow(i,j,table);
						HashSet<Integer>h3=getSameColumn(i,j,table);
						h.addAll(h2);
						h.addAll(h3);
						set.removeAll(h);
						Integer[] temp=set.toArray(new Integer[set.size()]);
						if(temp.length==0 && !end.get(0)) return;
						for(int k=0;k<temp.length;k++){
							table[i][j]=temp[k];
							brojac++;
							solve(table,brojac,end);
							if(end.get(0))return;
							else{
								b=false;
								brojac--;
							}
							if(k==temp.length-1 && b==false && !end.get(0)) {
								table[i][j]=0;
								brojac--;
								return;
							}
						}
						
					}
					
				}
			}
			
		}
		
	}
	
	public static void main(String[] args) {
		
		Integer[][] table={
				{8,0,0,0,0,0,0,0,0},
				{0,0,3,6,0,0,0,0,0},
				{0,7,0,0,9,0,2,0,0},
				{0,5,0,0,0,7,0,0,0},
				{0,0,0,0,4,5,7,0,0},
				{0,0,0,1,0,0,0,3,0},
				{0,0,1,0,0,0,0,6,8},
				{0,0,8,5,0,0,0,1,0},
				{0,9,0,0,0,0,4,0,0}
		};
		solve(table);
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				System.out.print(table[i][j]+" ");
				if(j%3==2) System.out.print("|");
			}
			System.out.println("");
			if(i%3==2) System.out.println("---------------------");
		}
	}

}
