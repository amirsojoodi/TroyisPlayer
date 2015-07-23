package org.troyisPlayer.main;

/*
============================================================================
Author      : Amir Hossein Sojoodi, Amir Hossein Shahriari
Version     : 0.0.1
Year        : 2011
Copyright   : GNU
Description : TroyisPlayer in Java
============================================================================
*/



public class horseBrain {
	private static int n,m;
	private static int[][] ldis;
	private static int[][][][] dis;
	//private static int m1[]={-2,-2,-1,-1,1,1,2,2};
	//private static int m2[]={-1,1,-2,2,-2,2,-1,1};
	private static Node[][] b;
	private static Node[] lb;
	private static Node target,cur;
	public horseBrain(Node[][] board){
		b=board;
		target=null;
		cur=b[0][0];
		n=b.length;
		m=b[0].length;
		lb=new Node[n*m];
		dis=new int[n][n][n][n];
		/*for (int i = 0; i < dis.length; i++) {
			dis[i]=new int[n][][];
			for (int j = 0; j < dis[i].length; j++) {
				dis[i][j]=new int[n][];
				for (int k = 0; k < dis[i][j].length; k++) {
					dis[i][j][k]=new int[n];
					for (int p = 0; p < dis[i][j][k].length; p++) {
						dis[i][j][k][p]=0;
					}
				}
			}
		}*/
		for (int i = 0 , k = 0; i < b.length; i++)
			for (int j = 0; j < b[0].length; j++ , k++)
				lb[k]=b[i][j];
		ldis=new int[lb.length][lb.length];
		for (int i = 0; i < ldis.length; i++)
			for (int j = 0; j < ldis.length; j++)
				ldis[i][j]=1<<26;
		for (int i = 0; i < lb.length; i++) {
			if (lb[i].state==State.full)
				continue;
			Node[] neighs=neighbors(lb[i]);
			for (int j = 0; j < neighs.length; j++)
				if (neighs[j]!=null){
					ldis[i][neighs[j].x*m+neighs[j].y]=1;
					dis[lb[i].x][lb[i].y][neighs[j].x][neighs[j].y]=1;
				}
		}
		for (int i = 0; i < lb.length; i++) {
			dis[lb[i].x][lb[i].y][lb[i].x][lb[i].y]=0;
			ldis[i][i]=0;
		}
		for (int k = 0; k < lb.length; k++) 
			for (int i = 0; i < lb.length; i++) 
				for (int j = 0; j < lb.length; j++) 
					//if (ldis[i][k]!=0 && ldis[k][j]!=0)
						if (ldis[i][j]>ldis[i][k]+ldis[k][j]/* || ldis[i][j]==0*/){
							ldis[i][j]=ldis[i][k]+ldis[k][j];
							dis[lb[i].x][lb[i].y][lb[j].x][lb[j].y]=ldis[i][j];
						}
	}
	/*private int abs(int x){
		return x<0?-x:x;
	}*/
	private Node findEmpty(){
		for (int i = 0; i < lb.length; i++)
			if (lb[i].state==State.unseen)
				return lb[i];
		return null;
	}
	private Node[] neighbors(Node u){
		Node[] neigh=new Node[8];
		for (int i=0,z=0;i<2;i++)
			for (int j=0;j<2;j++){
				int k=-2+i*4,p=-1+j*2;
				try{
					if (b[u.x+k][u.y+p].state!=State.full)
						neigh[z]=b[u.x+k][u.y+p];
				}catch(Exception e){neigh[z]=null;}
				z++;
				try{
					if (b[u.x+p][u.y+k].state!=State.full)
						neigh[z]=b[u.x+p][u.y+k];
				}catch(Exception e){neigh[z]=null;}
				z++;
			}
		return neigh;
			/*if (i==0)continue;
			int p;
			if (abs(i)==2)p=1;
			else p=2;
			for (int j=0;j<2;j++){
				int k=p-2*j*p;
				board[j][k];
			}
		}
		for (int i = 0; i < m1.length; i++) {
			try{
				if (b[x+m1[i]][y+m2[i]].state!=0)
					neigh[i]=b[x+m1[i]][y+m2[i]];
			}catch(Exception e){}
		}*/
	}
	public Node nextMove(){
		if (target==GP.CurrentNode || target==null){
			target=findEmpty();
			if (target==null)
				return null;
		}
		System.out.println("target x: "+target.x+" target y: "+target.y);
		Node next=null;
		Node[] myNeighbors=neighbors(GP.CurrentNode);
		for (int i = 0; i < myNeighbors.length; i++) {
			if (myNeighbors[i]!=null && myNeighbors[i].state==State.unseen)
				return myNeighbors[i];
		}
		int distarget=1<<24;
		System.out.println("my fucking neighbors are:");
		for (int i = 0; i < myNeighbors.length; i++) {
			if (myNeighbors[i]!=null/* && myNeighbors[i]!=GP.CurrentNode*/){
				System.out.println("x: "+myNeighbors[i].x+" y: "+myNeighbors[i].y+" distance to target is: "+dis[myNeighbors[i].x][myNeighbors[i].y][target.x][target.y]);
				if (distarget>dis[myNeighbors[i].x][myNeighbors[i].y][target.x][target.y]){
					distarget=dis[myNeighbors[i].x][myNeighbors[i].y][target.x][target.y];
					next=myNeighbors[i];
				}
			}
		}
		cur=next;
		return next;
	}
}

