package Tank_war_1_0;

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
}

class Enemy extends Tank{

	public Enemy(int x, int y) {
		super(x, y);
		
	}
	
}