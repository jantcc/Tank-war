package Tank_war_5_0;

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
	Vector<Enemy> vst2= new Vector<Enemy>();//定义一个向量，可以访问到MyPanel上所以敌人坦克
	public Enemy(int x, int y) {
		super(x, y);
	}
	//获得其他敌人坦克的向量
	public void getotherEnemyvector(Vector<Enemy> vv){
		this.vst2=vv;
	}
	//判断是否与其他敌人的坦克
	public boolean istouchotherEnemy(){
		boolean b = false;
		switch(this.direct){
		case 1://我的方向向上
				//取出坦克
				for(int i=0;i<vst2.size();i++){
					Enemy em = vst2.get(i);
					if(em!=this){ //不能自己和自己比较
						if(em.direct==1||em.direct==2){ //如果敌人的方向向上或者向下
							if(this.x>=em.x&&this.x<=em.x+20&&this.y>=em.y&&this.y<=em.y+30){
								return true;
							}
							if(this.x+20>=em.x&&this.x+20<=em.x+20&&this.y>=em.y&&this.y<=em.y+30){
								return true;
							}
						}
						if(em.direct==3||em.direct==4){//如果敌人的方向向左或者向右
							if(this.x>=em.x&&this.x<=em.x+30&&this.y>=em.y&&this.y<=em.y+20){
								return true;
							}
							if(this.x+20>=em.x&&this.x+20<=em.x+30&&this.y>=em.y&&this.y<=em.y+20){
								return true;
							}
						}
					}
				}
			break;
		case 2://我的方向向下
			for(int i=0;i<vst2.size();i++){
				Enemy em = vst2.get(i);
				if(em!=this){ //不能自己和自己比较
					if(em.direct==1||em.direct==2){ //如果敌人的方向向上或者向下
						if(this.x>=em.x&&this.x<=em.x+20&&this.y+30>=em.y&&this.y+30<=em.y+30){
							return true;
						}
						if(this.x+20>=em.x&&this.x+20<=em.x+20&&this.y+30>=em.y&&this.y+30<=em.y+30){
							return true;
						}
					}
					if(em.direct==3||em.direct==4){//如果敌人的方向向左或者向右
						if(this.x>=em.x&&this.x<=em.x+30&&this.y+30>=em.y&&this.y+30<=em.y+20){
							return true;
						}
						if(this.x+20>=em.x&&this.x+20<=em.x+30&&this.y+30>=em.y&&this.y+30<=em.y+20){
							return true;
						}
					}
				}
			}
			break;
		case 3://我的方向向左
			for(int i=0;i<vst2.size();i++){
				Enemy em = vst2.get(i);
				if(em!=this){ //不能自己和自己比较
					if(em.direct==1||em.direct==2){ //如果敌人的方向向上或者向下
						if(this.x>=em.x&&this.x<=em.x+20&&this.y>=em.y&&this.y<=em.y+30){
							return true;
						}
						if(this.x>=em.x&&this.x<=em.x+20&&this.y+20>=em.y&&this.y+20<=em.y+30){
							return true;
						}
					}
					if(em.direct==3||em.direct==4){//如果敌人的方向向左或者向右
						if(this.x>=em.x&&this.x<=em.x+30&&this.y>=em.y&&this.y<=em.y+20){
							return true;
						}
						if(this.x>=em.x&&this.x<=em.x+30&&this.y+20>=em.y&&this.y+20<=em.y+20){
							return true;
						}
					}
				}
			}
			break;
		case 4://我的方向向右
			for(int i=0;i<vst2.size();i++){
				Enemy em = vst2.get(i);
				if(em!=this){ //不能自己和自己比较
					if(em.direct==1||em.direct==2){ //如果敌人的方向向上或者向下
						if(this.x+30>=em.x&&this.x+30<=em.x+20&&this.y>=em.y&&this.y<=em.y+30){
							return true;
						}
						if(this.x+30>=em.x&&this.x+30<=em.x+20&&this.y+20>=em.y&&this.y+20<=em.y+30){
							return true;
						}
					}
					if(em.direct==3||em.direct==4){//如果敌人的方向向左或者向右
						if(this.x+30>=em.x&&this.x+30<=em.x+30&&this.y>=em.y&&this.y<=em.y+20){
							return true;
						}
						if(this.x+30>=em.x&&this.x+30<=em.x+30&&this.y+20>=em.y&&this.y+20<=em.y+20){
							return true;
						}
					}
				}
			}
			break;
		}
		
		
		return b;
	}
 	public void run() {
		while(true){
			
			switch(direct){
			case 1:
				for(int i=0;i<30;i++)  {
					if(y>0&&!this.istouchotherEnemy()){
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
					if(y<270&&!this.istouchotherEnemy()){//坦克长30 ，要减去
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
				if(x>0&&!this.istouchotherEnemy()){
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
				if(x<370&&!this.istouchotherEnemy()){  //坦克长30 ，要减去
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