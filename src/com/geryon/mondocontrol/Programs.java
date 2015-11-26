package com.geryon.mondocontrol;

public class Programs {
	String nev = "";
	String Helyszin = "";
	int kezdOra = 0;
	int kezdPerc= 0;
	int vegOra =0;
	int vegPerc=0;
	String desc = "";
	
	
public void setNev(String s){
	nev = s;
}
public void setHelyszin(String s){
	Helyszin = s;
}
public void setkezdOra(int i){
	kezdOra = i;
}
public void setkezdPerc(int i){
	kezdPerc = i;
}
public void setvegOra(int i){
	vegOra = i;
}
public void setvegPerc(int i){
	vegPerc = i;
}
public void setDesc(String s){
	desc = s;
}

public String getNev(){
	return nev;
}
public String getHelyszin(){
	return Helyszin;
}
public int getkezdOra(){
	return kezdOra;
}
public int getkezdPerc(){
	return kezdPerc;
}
public int getvegOra(){
	return vegOra;
}
public int getvegPerc(){
	return vegPerc;
}

public String getDesc(){
	return desc;
}







}
	

