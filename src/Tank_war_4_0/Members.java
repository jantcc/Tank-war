package Tank_war_4_0;

import java.util.Vector;

//定义一个坦克类
class Tank{
	int x=0;//坦克横坐标
	int y=0;//坦克纵坐标
	int direct=1;//坦克方向，1上，2下，3左，4右.
	int speed=1;//坦克的速度
	int color;//坦克的颜色
	boolean islive=true;
	public  Tank(int x,int y){
		this.x=x;
		this.y=y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getDirect() {
		return direct;
	}
	public void setDirect(int direct) {
		this.direct = direct;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
//定义一个我的坦克类
class Hero extends Tank{
	Vector<Shot> vst= new Vector<Shot>();
	Shot s=null;
	public Hero(int x,int y){
		super(x,y);
	}
	public void moveUp(){
		if(y>0){
		y-=speed;
		}
	}
	public void moveDown(){
		if(y<270){
		y+=speed;
		}
	}
	public void moveLeft(){
		if(x>0){
		x-=speed;
		}
	}
	public void moveRight(){
		if(x<370){
		x+=speed;
		}
	}
	public void shotEnemy(){
		switch(this.direct){
		case 1:
			s= new Shot(x+10,y,1);
			vst.add(s);
			break;
		case 2:
			s= new Shot(x+10,y+30,2);
			vst.add(s);
			break;
		case 3:
			s=new Shot(x,y+10,3);
			vst.add(s);
		    break;
		case 4:
			s=new Shot(x+30,y+10,4);
			vst.add(s);
			break;
		}
		Thread t= new Thread(s);
		t.start();
	}
}
//敌人的坦克
class Enemy extends Tank implements Runnable{
	Vector<Shot> vst1= new Vector<Shot>();
	public Enemy(int x, int y) {
		super(x, y);
	}
	
	public void run() {
		while(true){
			
			switch(direct){
			case 1:
				for(int i=0;i<30;i++)  {
					if(y>0){
				y-=speed;
					}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				}
				break;
			case 2:  
				for(int i=0;i<30;i++)  {
					if(y<270){//坦克长30 ，要减去
				y+=speed;
					}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}}
				break;
			case 3:	
				for(int i=0;i<30;i++)  {
				if(x>0){
					x-=speed;
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}}
				break;
			case 4: 
				for(int i=0;i<30;i++)  {
				if(x<370){  //坦克长30 ，要减去
					x+=speed;
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}}
				break;
			}
			this.direct=(int)(Math.random()*4+1);
			if(this.islive==false){
				break;
			}
			//判断坦克是否需要添加子弹
			
				if(islive){
					if(vst1.size()<3){
						Shot s=null;//没有子弹
						//添加
						switch(direct){
						case 1:
							s= new Shot(x+10,y,1);
							vst1.add(s);
							break;
						case 2:
							s= new Shot(x+10,y+30,2);
							vst1.add(s);
							break;
						case 3:
							s=new Shot(x,y+10,3);
							vst1.add(s);
						    break;
						case 4:
							s=new Shot(x+30,y+10,4);
							vst1.add(s);
							break;
						}
						Thread tt=new Thread(s);
						tt.start();
					}
				}
			}
		}
		
	}


//子弹类

class Shot implements Runnable{
	int x=0;
	int y=0;
	int direct;//子弹方向(同坦克方向)
	int speed=2;//子弹速度
	boolean islive=true;
	public Shot(int x,int y,int direct){
		this.x=x;
		this.y=y;
		this.direct=direct;
	}
	public void run() {
		while(true){
			try{
				Thread.sleep(50);
			}catch(Exception e){
				e.printStackTrace();
			}
		switch(direct){
		case 1:
			y-=speed;
			break;
		case 2:
			y+=speed;
			break;
		case 3:
			x-=speed;
			break;
		case 4:
			x+=speed;
			break;
		}
	//	System.out.println("子弹x="+x+"子弹y="+y);
		if(x>401||x<-1||y>301||y<-1){//子弹的体积为1
			islive=false;
			break;
			
		}
	  }	
	}
}
// 炸弹
class Boom {
	int x,y;
	int life=10;
	boolean islive= true;
	public Boom(int x,int y){
		this.x=x;
		this.y=y;
	}
	public void lifeDown(){
		if(life>0){
			life--;
		}else{
			islive=false;
		}
	}
}