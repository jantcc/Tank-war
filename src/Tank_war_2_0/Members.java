package Tank_war_2_0;

//定义一个坦克类
class Tank{
	int x=0;//坦克横坐标
	int y=0;//坦克纵坐标
	int direct=1;//坦克方向，1上，2下，3左，4右.
	int speed=1;//坦克的速度
	int color;//坦克的颜色
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
	Shot s=null;
	public Hero(int x,int y){
		super(x,y);
	}
	public void moveUp(){
		y-=speed;
	}
	public void moveDown(){
		y+=speed;
	}
	public void moveLeft(){
		x-=speed;
	}
	public void moveRight(){
		x+=speed;
	}
	public void shotEnemy(){
		switch(this.direct){
		case 1:
			s= new Shot(x+10,y,1);
			break;
		case 2:
			s= new Shot(x+10,y+30,2);
			break;
		case 3:
			s=new Shot(x,y+10,3);
		    break;
		case 4:
			s=new Shot(x+30,y+10,4);
			break;
		}
		Thread t= new Thread(s);
		t.start();
	}
}
//敌人的坦克
class Enemy extends Tank{
	public Enemy(int x, int y) {
		super(x, y);
	}
}

//子弹类

class Shot implements Runnable{
	int x=0;
	int y=0;
	int direct;//子弹方向(同坦克方向)
	int speed=2;//子弹速度
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
		if(x>400||x<0||y>300||y<0){
			break;
		}
	  }	
	}
}