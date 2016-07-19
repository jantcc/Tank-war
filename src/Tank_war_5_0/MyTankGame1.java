/*
 * 可以发射子弹  ，我的坦克可以自由移动，并且我的坦克一次可以连发5颗子弹；
 * 子弹击中敌方坦克，敌方坦克消失
 * 增加我方子弹击中敌方坦克，敌方坦克消失爆炸的效果
 * 坦克移动范围限制
 * 敌方坦克也可发射子弹，但是只能一次只能连发3个
 * 增加敌方子弹击中我方坦克，我方坦克消失爆炸的效果
 * 增加功能:敌方坦克不能重叠
 */
package Tank_war_5_0;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import javax.imageio.*;
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
		this.setSize(416, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
//定义一个我的面板（坦克在里面）
class MyPanel extends JPanel implements KeyListener,Runnable{
	static int num=0;
	//定义一个我的坦克
	Hero hero = null;
	//定义一个敌人的坦克组
	Vector<Enemy> emy= new Vector<Enemy>();
	int emynum=8;
	//定义一个炸弹组
	Vector<Boom> booms= new Vector<Boom>();
	//定义三张爆炸图片
	Image image1=null;
	Image image2=null;
	Image image3=null;
	public MyPanel(){
		//初始化我的坦克
		hero = new Hero(200,100);
		//初始化敌人的坦克组
		for(int i=0;i<emynum;i++){
			Enemy em = new Enemy(i*50,0);
			em.setColor(0);
			em.setDirect(2);
			em.getotherEnemyvector(emy);//**->敌人坦克类里就可以判断坦克不能重叠
			//初始化敌人坦克的时候就开始发射子弹
			Shot s = new Shot(em.x+10,em.y+30,2);
			em.vst1.add(s);
			Thread t2 =new Thread(s);
			t2.start();
			Thread t1= new Thread(em);
			t1.start();
			emy.add(em);
			
		}
		//初始化爆炸图片
		try {
			image1 = ImageIO.read(new File("src/bomb_1.gif"));
			image2 = ImageIO.read(new File("src/bomb_2.gif"));
			image3 = ImageIO.read(new File("src/bomb_3.gif"));
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
//		image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
//		image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
//		image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
	}
	//判断我的子弹是否击中敌方坦克
	public void killEnemyTank(){
		for(int i=0;i<hero.vst.size();i++){
			Shot myShot=hero.vst.get(i);
			if(myShot.islive){
				for(int j=0;j<emy.size();j++){
					Enemy em= emy.get(j);
					if(em.islive){
						this.killTank(myShot, em);
					}
				}
			}
		}
	}
	//判断敌方子弹是否击中我方坦克
	public void killHeroTank(){
		for(int i=0;i<emy.size();i++){
			Enemy em =emy.get(i);//取出坦克
			//再取出子弹
			for(int j=0;j<em.vst1.size();j++){
				Shot EnemyShot =em.vst1.get(j);
				this.killTank(EnemyShot, hero);
			}
		}
	}
	//判断子弹是否击中坦克
	public void killTank(Shot s,Tank tank){
		switch(tank.direct){
		case 1:
		case 2: if(s.x>tank.x&&s.x<tank.x+20&&s.y>tank.y&&s.y<tank.y+30){
				s.islive=false;
				tank.islive=false;
				Boom boom = new Boom(tank.x,tank.y);
				booms.add(boom);
				}
				break;
		case 3:
		case 4:if(s.x>tank.x&&s.x<tank.x+30&&s.y>tank.y&&s.y<tank.y+20){
			s.islive=false;
			tank.islive=false;
			Boom boom = new Boom(tank.x,tank.y);
			booms.add(boom);
			}
			break;
		}
	}
	//复写paint
	public void paint(Graphics g){
		super.paint(g);
		g.fillRect(0,0,400,300);
		//画我的坦克
		if(hero.islive){
		this.drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
		}
		//画敌人的坦克
		for(int i=0;i<emy.size();i++){
			Enemy em =emy.get(i);
			if(em.islive){
			this.drawTank(em.getX(),em.getY() , g, em.getDirect(), 0);
			//画敌人的子弹
			for(int j=0;j<em.vst1.size();j++){
				Shot EnemyShot = em.vst1.get(j);
				if(EnemyShot.islive){
					g.setColor(Color.white);
					g.draw3DRect(EnemyShot.x,EnemyShot.y, 1, 1, false);
				}else{
					em.vst1.remove(EnemyShot);
				}
				
			}
		}
	  }		
		//画炸弹，让其显示爆炸效果
		for(int i=0;i<booms.size();i++){
			Boom boom = booms.get(i);
			if(boom.life>7){
				g.drawImage(image1, boom.x, boom.y, 30, 30, this);
			}
			else if(boom.life>3){
				g.drawImage(image2, boom.x, boom.y, 30, 30, this);
				}
			else {
				g.drawImage(image3, boom.x, boom.y, 30, 30, this);
				}
			boom.lifeDown();
			if(boom.life==0){
				booms.remove(boom);
			}
			}
		
		//画我的坦克的子弹
		for(int i=0;i<hero.vst.size();i++){
			Shot myShot=hero.vst.get(i);
		if(myShot.islive){
			g.setColor(Color.white);
			g.draw3DRect(myShot.x, myShot.y, 1, 1, false);
		}
		//子弹碰到边界（死亡） 就从集合里面删除这颗子弹 以便可以发射新子弹，因为一次只能连发5颗子弹
		if(myShot.islive==false){
			hero.vst.remove(myShot);
		}
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
			//开火  按A
			if(e.getKeyCode()==KeyEvent.VK_A){
				if(this.hero.vst.size()<5){
				this.hero.shotEnemy();
			}
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
			
			this.killEnemyTank();
			this.killHeroTank();
			this.repaint();
		}
	}		
}
