package Tank_war_1_0;

//����һ��̹����
class Tank{
	int x=0;//̹�˺�����
	int y=0;//̹��������
	int direct=1;//̹�˷���1�ϣ�2�£�3��4��.
	int speed=1;//̹�˵��ٶ�
	int color;//̹�˵���ɫ
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
//����һ���ҵ�̹����
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