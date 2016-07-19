package Tank_war_2_0;

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
//���˵�̹��
class Enemy extends Tank{
	public Enemy(int x, int y) {
		super(x, y);
	}
}

//�ӵ���

class Shot implements Runnable{
	int x=0;
	int y=0;
	int direct;//�ӵ�����(̹ͬ�˷���)
	int speed=2;//�ӵ��ٶ�
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
	//	System.out.println("�ӵ�x="+x+"�ӵ�y="+y);
		if(x>400||x<0||y>300||y<0){
			break;
		}
	  }	
	}
}