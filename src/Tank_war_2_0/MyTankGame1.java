/*
 * 可以发射子弹  ，我的坦克可以自由移动
 */
package Tank_war_2_0;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
public class MyTankGame1 extends JFrame{
	MyPanel mp=null;
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		MyTankGame1 mg= new MyTankGame1();
	}
	public MyTankGame1(){
		mp = new MyPanel();
		this.add(mp);
		//注册监听
		Thread t0= new Thread(mp);
		t0.start();
		this.addKeyListener(mp);
		this.setSize(400, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
//定义一个我的面板（坦克在里面）
class MyPanel extends JPanel implements KeyListener,Runnable{
	//定义一个我的坦克
	Hero hero = null;
	//定义一个敌人的坦克组
	Vector<Enemy> emy= new Vector<Enemy>();
	int emynum=3;
	public MyPanel(){
		//初始化我的坦克
		hero = new Hero(200,100);
		//初始化敌人的坦克
		for(int i=0;i<emynum;i++){
			Enemy em = new Enemy((i+1)*50,0);
			em.setColor(0);
			em.setDirect(2);
			emy.add(em);
		}

	}
	//复写paint
	public void paint(Graphics g){
		super.paint(g);
		g.fillRect(0, 0,400,300);
		//画我的坦克
		this.drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
		//画敌人的坦克
		for(int i=0;i<emy.size();i++){
			this.drawTank(emy.get(i).getX(),emy.get(i).getY() , g, emy.get(i).getDirect(), 0);
		}
		//画我的坦克的子弹
		if(hero.s!=null){
			g.setColor(Color.white);
			g.draw3DRect(hero.s.x, hero.s.y, 1, 1, false);
		}
	}
	//定义一个画坦克的函数
	public void drawTank(int x,int y,Graphics g,int direct,int type){
		//判断坦克的类型
		switch(type){
		case 0://敌人
			g.setColor(Color.cyan);break;
		case 1://自己
			g.setColor(Color.yellow);break;
		}
		//判断坦克的方向
		switch(direct){
		case 1://坦克向上
			g.fill3DRect(x,y,5,30,false);
			g.fill3DRect(x+15, y, 5, 30,false);
			g.fill3DRect(x+5, y+5, 10, 20,false);
			g.fillOval(x+5, y+10, 9, 9);
			g.drawLine(x+10, y+15, x+10, y);
			break;
		case 2://坦克向下
			g.fill3DRect(x,y,5,30,false);
			g.fill3DRect(x+15, y, 5, 30,false);
			g.fill3DRect(x+5, y+5, 10, 20,false);
			g.fillOval(x+5, y+10, 9, 9);
			g.drawLine(x+10, y+15, x+10, y+30);
			break;
		case 3://坦克向左
			g.fill3DRect(x,y,30,5,false);
			g.fill3DRect(x, y+15, 30, 5,false);
			g.fill3DRect(x+5, y+5, 20, 10,false);
			g.fillOval(x+10, y+5, 9, 9);
			g.drawLine(x+15, y+10, x, y+10);
			break;
		case 4://坦克向右
			g.fill3DRect(x,y,30,5,false);
			g.fill3DRect(x, y+15, 30, 5,false);
			g.fill3DRect(x+5, y+5, 20, 10,false);
			g.fillOval(x+10, y+5, 9, 9);
			g.drawLine(x+15, y+10, x+30, y+10);
			break;
		}
	}
	//事件监听
	

		public void keyPressed (KeyEvent e)  {
			// TODO 自动生成的方法存根
			if(e.getKeyCode()==KeyEvent.VK_DOWN){
				hero.setDirect(2);
				hero.moveDown();
				
			}
			else if(e.getKeyCode()==KeyEvent.VK_LEFT){
				hero.setDirect(3);
				hero.moveLeft();
			
			}
			else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
				hero.setDirect(4);
				hero.moveRight();
				
			}
			else if(e.getKeyCode()==KeyEvent.VK_UP){
				hero.setDirect(1);
				hero.moveUp();
				
			}
			if(e.getKeyCode()==KeyEvent.VK_A){
				this.hero.shotEnemy();
			}
			this.repaint();
		}

		public void keyReleased(KeyEvent arg0) {
			// TODO 自动生成的方法存根
			
		}

		public void keyTyped(KeyEvent arg0) {
			// TODO 自动生成的方法存根
			
		}
		public void run() {
			// TODO 自动生成的方法存根
			while(true){
			try{
				Thread.sleep(100);
			}catch(Exception e){
				e.printStackTrace();
			}
			this.repaint();
		}
	}		
}
