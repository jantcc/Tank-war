/*
 * ���Է����ӵ�  ���ҵ�̹�˿��������ƶ��������ҵ�̹��һ�ο�������5���ӵ���
 * �ӵ����ез�̹�ˣ��з�̹����ʧ
 * �����ҷ��ӵ����ез�̹�ˣ��з�̹����ʧ��ը��Ч��
 * ̹���ƶ���Χ����
 * �з�̹��Ҳ�ɷ����ӵ�������ֻ��һ��ֻ������3��
 */
package Tank_war_3_0;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
public class MyTankGame1 extends JFrame{
	MyPanel mp=null;
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		MyTankGame1 mg= new MyTankGame1();
	}
	public MyTankGame1(){
		mp = new MyPanel();
		this.add(mp);
		//ע�����
		Thread t0= new Thread(mp);
		t0.start();
		this.addKeyListener(mp);
		this.setSize(416, 339);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
//����һ���ҵ���壨̹�������棩
class MyPanel extends JPanel implements KeyListener,Runnable{
	//����һ���ҵ�̹��
	Hero hero = null;
	//����һ�����˵�̹����
	Vector<Enemy> emy= new Vector<Enemy>();
	int emynum=3;
	//����һ��ը����
	Vector<Boom> booms= new Vector<Boom>();
	//�������ű�ըͼƬ
	Image image1=null;
	Image image2=null;
	Image image3=null;
	public MyPanel(){
		//��ʼ���ҵ�̹��
		hero = new Hero(200,100);
		//��ʼ�����˵�̹��
		for(int i=0;i<emynum;i++){
			Enemy em = new Enemy((i+1)*50,0);
			em.setColor(0);
			em.setDirect(2);
			//��ʼ������̹�˵�ʱ��Ϳ�ʼ�����ӵ�
			Shot s = new Shot(em.x+10,em.y+30,2);
			em.vst1.add(s);
			Thread t2 =new Thread(s);
			t2.start();
			Thread t1= new Thread(em);
			t1.start();
			emy.add(em);
			
		}
		//��ʼ����ըͼƬ
		image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
		image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
		image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
	}
	//�ж��ӵ��Ƿ���ез�̹��
	public void killEnemy(Shot s,Enemy em){
		switch(em.direct){
		case 1:
		case 2: if(s.x>em.x&&s.x<em.x+20&&s.y>em.y&&s.y<em.y+30){
				s.islive=false;
				em.islive=false;
				Boom boom = new Boom(em.x,em.y);
				booms.add(boom);
				}
				break;
		case 3:
		case 4:if(s.x>em.x&&s.x<em.x+30&&s.y>em.y&&s.y<em.y+20){
			s.islive=false;
			em.islive=false;
			Boom boom = new Boom(em.x,em.y);
			booms.add(boom);
			}
			break;
		}
	}
	//��дpaint
	public void paint(Graphics g){
		super.paint(g);
		g.fillRect(0,0,400,300);
		//���ҵ�̹��
		this.drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
		//�����˵�̹��
		for(int i=0;i<emy.size();i++){
			Enemy em =emy.get(i);
			if(em.islive){
			this.drawTank(em.getX(),em.getY() , g, em.getDirect(), 0);
			//�����˵��ӵ�
			for(int j=0;j<em.vst1.size();j++){
				Shot EnemyShot = em.vst1.get(j);
				if(EnemyShot.islive){
					g.draw3DRect(EnemyShot.x,EnemyShot.y, 1, 1, false);
				}else{
					em.vst1.remove(EnemyShot);
				}
				
			}
		}
	  }		
		//��ը����������ʾ��ըЧ��
		for(int i=0;i<booms.size();i++){
			Boom boom = booms.get(i);
			if(boom.life>4){
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
		
		//���ҵ�̹�˵��ӵ�
		for(int i=0;i<hero.vst.size();i++){
			Shot myShot=hero.vst.get(i);
		if(myShot.islive){
			g.setColor(Color.white);
			g.draw3DRect(myShot.x, myShot.y, 1, 1, false);
		}
		//�ӵ������߽磨������ �ʹӼ�������ɾ������ӵ� �Ա���Է������ӵ�����Ϊһ��ֻ������5���ӵ�
		if(myShot.islive==false){
			hero.vst.remove(myShot);
		}
	  }

	}
	//����һ����̹�˵ĺ���
	public void drawTank(int x,int y,Graphics g,int direct,int type){
		//�ж�̹�˵�����
		switch(type){
		case 0://����
			g.setColor(Color.cyan);break;
		case 1://�Լ�
			g.setColor(Color.yellow);break;
		}
		//�ж�̹�˵ķ���
		switch(direct){
		case 1://̹������
			g.fill3DRect(x,y,5,30,false);
			g.fill3DRect(x+15, y, 5, 30,false);
			g.fill3DRect(x+5, y+5, 10, 20,false);
			g.fillOval(x+5, y+10, 9, 9);
			g.drawLine(x+10, y+15, x+10, y);
			break;
		case 2://̹������
			g.fill3DRect(x,y,5,30,false);
			g.fill3DRect(x+15, y, 5, 30,false);
			g.fill3DRect(x+5, y+5, 10, 20,false);
			g.fillOval(x+5, y+10, 9, 9);
			g.drawLine(x+10, y+15, x+10, y+30);
			break;
		case 3://̹������
			g.fill3DRect(x,y,30,5,false);
			g.fill3DRect(x, y+15, 30, 5,false);
			g.fill3DRect(x+5, y+5, 20, 10,false);
			g.fillOval(x+10, y+5, 9, 9);
			g.drawLine(x+15, y+10, x, y+10);
			break;
		case 4://̹������
			g.fill3DRect(x,y,30,5,false);
			g.fill3DRect(x, y+15, 30, 5,false);
			g.fill3DRect(x+5, y+5, 20, 10,false);
			g.fillOval(x+10, y+5, 9, 9);
			g.drawLine(x+15, y+10, x+30, y+10);
			break;
		}
	}
	//�¼�����
	

		public void keyPressed (KeyEvent e)  {
			// TODO �Զ����ɵķ������
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
			//����  ��A
			if(e.getKeyCode()==KeyEvent.VK_A){
				if(this.hero.vst.size()<5){
				this.hero.shotEnemy();
			}
		  }
			this.repaint();
		}

		public void keyReleased(KeyEvent arg0) {
			// TODO �Զ����ɵķ������
			
		}

		public void keyTyped(KeyEvent arg0) {
			// TODO �Զ����ɵķ������
			
		}
		public void run() {
			// TODO �Զ����ɵķ������
			while(true){
			try{
				Thread.sleep(100);
			}catch(Exception e){
				e.printStackTrace();
			}
			//�����ж��Ƿ����̹�˵ĺ���
			for(int i=0;i<hero.vst.size();i++){
				Shot myShot=hero.vst.get(i);
				if(myShot.islive){
					for(int j=0;j<emy.size();j++){
						Enemy em= emy.get(j);
						if(em.islive){
							this.killEnemy(myShot, em);
						}
					}
				}
			}
			
			this.repaint();
		}
	}		
}
