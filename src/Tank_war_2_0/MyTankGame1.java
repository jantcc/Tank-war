/*
 * ���Է����ӵ�  ���ҵ�̹�˿��������ƶ�
 */
package Tank_war_2_0;
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
		this.setSize(400, 300);
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
	public MyPanel(){
		//��ʼ���ҵ�̹��
		hero = new Hero(200,100);
		//��ʼ�����˵�̹��
		for(int i=0;i<emynum;i++){
			Enemy em = new Enemy((i+1)*50,0);
			em.setColor(0);
			em.setDirect(2);
			emy.add(em);
		}

	}
	//��дpaint
	public void paint(Graphics g){
		super.paint(g);
		g.fillRect(0, 0,400,300);
		//���ҵ�̹��
		this.drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
		//�����˵�̹��
		for(int i=0;i<emy.size();i++){
			this.drawTank(emy.get(i).getX(),emy.get(i).getY() , g, emy.get(i).getDirect(), 0);
		}
		//���ҵ�̹�˵��ӵ�
		if(hero.s!=null){
			g.setColor(Color.white);
			g.draw3DRect(hero.s.x, hero.s.y, 1, 1, false);
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
			if(e.getKeyCode()==KeyEvent.VK_A){
				this.hero.shotEnemy();
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
			this.repaint();
		}
	}		
}
