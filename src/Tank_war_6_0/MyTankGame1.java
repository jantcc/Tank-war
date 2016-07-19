/*
 * ���Է����ӵ�  ���ҵ�̹�˿��������ƶ��������ҵ�̹��һ�ο�������5���ӵ���
 * �ӵ����ез�̹�ˣ��з�̹����ʧ
 * �����ҷ��ӵ����ез�̹�ˣ��з�̹����ʧ��ը��Ч��
 * ̹���ƶ���Χ����
 * �з�̹��Ҳ�ɷ����ӵ�������ֻ��һ��ֻ������3��
 * ���ӵз��ӵ������ҷ�̹�ˣ��ҷ�̹����ʧ��ը��Ч��
 * ���ӹ���:�з�̹�˲����ص�
 * ���ӹ���:���Կ�ʼ��Ϸ����ͣ��Ϸ��������Ϸ���˳���Ϸ
 * ���ӹ���:����������ĵ���̹�������������Զ�ȡ�������
 * ���ӹ���:������ŵĵ���̹�˵����꼰����
 */
package Tank_war_6_0;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

import javax.imageio.*;
import javax.swing.*;
public class MyTankGame1 extends JFrame implements ActionListener{
	MyPanel mp=null;
	StagePanel sp = null;
	JMenuBar jmb=null;
	JMenu Game=null;
	JMenu Function=null;
	JMenuItem newGame=null;
	JMenuItem stopGame=null;
	JMenuItem goGame=null;
	JMenuItem exitGame=null;
	JMenuItem saveExit=null;
	JMenuItem conGame=null;
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		MyTankGame1 mg= new MyTankGame1();
	}
	public MyTankGame1(){
		//�����ҵĲ˵���
		jmb= new JMenuBar();
		Game=new JMenu("��Ϸ(O)");
		Game.setMnemonic('O');
		Function= new JMenu("����(F)");
		Function.setMnemonic('F');
		newGame=new JMenuItem("��ʼ����Ϸ(G)");
		newGame.setMnemonic('G');
		stopGame=new JMenuItem("��ͣ��Ϸ(P)");
		stopGame.setMnemonic('P');
		goGame=new JMenuItem("������Ϸ(N)");
		goGame.setMnemonic('N');
		exitGame=new JMenuItem("�˳���Ϸ(E)");
		exitGame.setMnemonic('E');
		saveExit= new JMenuItem("�����˳�(S)");
		saveExit.setMnemonic('S');
		conGame=new JMenuItem("�����Ͼ���Ϸ(E)");
		conGame.setMnemonic('E');
		this.setJMenuBar(jmb);
		jmb.add(Game);
		jmb.add(Function);
		Game.add(newGame);
		Function.add(stopGame);
		Function.add(goGame);
		Game.add(exitGame);
		Game.add(saveExit);
		Game.add(conGame);
		newGame.addActionListener(this);
		stopGame.addActionListener(this);
		goGame.addActionListener(this);
		exitGame.addActionListener(this);
		saveExit.addActionListener(this);
		conGame.addActionListener(this);
		sp = new StagePanel();
		this.add(sp);
		Thread tsp= new Thread(sp);
		tsp.start();
		this.setSize(600, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
		 
		if(e.getSource().equals(newGame)){//��ʼ����Ϸ
			mp = new MyPanel(1);
			Thread t0= new Thread(mp);
			t0.start();
			this.remove(sp);
		    this.add(mp);
			this.addKeyListener(mp);
			this.setVisible(true);
		}
		else if(e.getSource().equals(stopGame)){//��ͣ��Ϸ
			removeKeyListener(mp);
			for(int i=0;i<mp.emy.size();i++){
				Enemy em =mp.emy.get(i);
				em.speed=0;
				em.move=0;
				for(int j=0;j<em.vst1.size();j++){
					Shot emshot = em.vst1.get(j);
					emshot.speed=0;
				}
				
			}
		}
		 else if(e.getSource().equals(goGame)){//������Ϸ
			addKeyListener(mp);
			for(int i=0;i<mp.emy.size();i++){
				Enemy em =mp.emy.get(i);
				em.speed=1;
				em.move=1;
				for(int j=0;j<em.vst1.size();j++){
					Shot emshot = em.vst1.get(j);
					emshot.speed=2;
				}
				
			}
		}
		 else if(e.getSource().equals(exitGame)){//�˳���Ϸ�����Ա��������������
				Record.resPreservation();
				System.exit(0);
			}
		 else if(e.getSource().equals(saveExit)){//�����˳�
			new Record().enemyInfosave(mp.emy);
			 System.exit(0);
		 }
		 else if(e.getSource().equals(conGame)){//������һ����Ϸ
			 mp = new MyPanel(0);
				Thread t0= new Thread(mp);
				t0.start();
				this.remove(sp);
			    this.add(mp);
				this.addKeyListener(mp);
				this.setVisible(true);
			}
	}
}
//����һ���ؿ����
class StagePanel extends JPanel implements Runnable{
	int times =0;
	public void paint(Graphics g){
		super.paint(g);
		g.fillRect(0,0,400,300);
		g.setColor(Color.yellow);
		Font myfont= new Font("������κ",Font.BOLD,30);
		g.setFont(myfont);
		if(times%2==0){
		g.drawString("stage 1", 150, 150);
		}
	}

	public void run() {
		// TODO �Զ����ɵķ������
		while(true){
			try {
				Thread.sleep(100);
				
			} catch (InterruptedException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			this.repaint();
			times++;
		}
	}
}
//����һ���ҵ���壨̹�������棩
class MyPanel extends JPanel implements KeyListener,Runnable{
	static int num=0;
	//����һ���ҵ�̹��
	Hero hero = null;
	//����һ�����˵�̹����
	Vector<Enemy> emy= new Vector<Enemy>();
	int emynum=3;
	//����һ��ը����
	Vector<Boom> booms= new Vector<Boom>();
	//����һ��Nodes(���������һ�ֵĵ���̹�����꼰����)
	Vector<Nodes> nodes = new Vector<Nodes>();
	//�������ű�ըͼƬ
	Image image1=null;
	Image image2=null;
	Image image3=null;
	public MyPanel(int flag){	//����һ���ж�����Ϸ�������һ�ֵı�־
		//�ָ��ɼ�
		Record.getRecord();
		//��ʼ���ҵ�̹��
		hero = new Hero(150,270);
		//���ſ�ս����
			AePlayWave apw=new AePlayWave("src/111.wav");
			apw.start();
		//�ж�������Ϸ���Ǽ�����һ����Ϸ
		//��ʼ�����˵�̹����
		if(flag==1){
		for(int i=0;i<emynum;i++){
			Enemy em = new Enemy(i*50,0);
			Thread t1= new Thread(em);
			t1.start();
			em.setColor(0);
			em.setDirect(2);
			em.getotherEnemyvector(emy);//**->����̹������Ϳ����ж�̹�˲����ص�
			//��ʼ������̹�˵�ʱ��Ϳ�ʼ�����ӵ�
			Shot s = new Shot(em.x+10,em.y+30,2);
			em.vst1.add(s);
			Thread t2 =new Thread(s);
			t2.start();
			emy.add(em);	
		}
	}else{
		nodes=new Record().getenemyInfo();
		for(int i=0;i<nodes.size();i++){
			Nodes node = nodes.get(i);
			Enemy em = new Enemy(node.x,node.y);
			Thread t1= new Thread(em);
			t1.start();
			em.setColor(0);
			em.setDirect(node.direct);
			em.getotherEnemyvector(emy);//**->����̹������Ϳ����ж�̹�˲����ص�
			//��ʼ������̹�˵�ʱ��Ϳ�ʼ�����ӵ�
			Shot s = new Shot(em.x+10,em.y+30,2);
			em.vst1.add(s);
			Thread t2 =new Thread(s);
			t2.start();
			emy.add(em);	
		}
	}
	 
		//��ʼ����ըͼƬ
		try {
			image1 = ImageIO.read(new File("src/bomb_1.gif"));
			image2 = ImageIO.read(new File("src/bomb_2.gif"));
			image3 = ImageIO.read(new File("src/bomb_3.gif"));
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	 

	}
	//�ж��ҵ��ӵ��Ƿ���ез�̹��
	public void killEnemyTank(){
		for(int i=0;i<hero.vst.size();i++){
			Shot myShot=hero.vst.get(i);
			if(myShot.islive){
				for(int j=0;j<emy.size();j++){
					Enemy em= emy.get(j);
					if(em.islive){
						this.killTank(myShot, em);
						
					}else{
						if(Record.getEnemeynums()>0){
							//������ʼ�����ֵ���̹��
						}
					}
				}
			}
		}
	}
	//�жϵз��ӵ��Ƿ�����ҷ�̹��
	public void killHeroTank(){
		for(int i=0;i<emy.size();i++){
			Enemy em =emy.get(i);//ȡ��̹��
			//��ȡ���ӵ�
			for(int j=0;j<em.vst1.size();j++){
				Shot EnemyShot =em.vst1.get(j);
				if(hero.islive){
				this.killTank(EnemyShot, hero);
			}else{
				if(Record.getHeronums()>0){//����������Ļ������˾����³�ʼ��һ���ҵ�̹��
				hero = new Hero(150,270);
				}
			}
		  }		
		}
	}
	//�ж��ӵ��Ƿ����̹��
	public void killTank(Shot s,Tank tank){
		switch(tank.direct){
		case 1:
		case 2: if(s.x>tank.x&&s.x<tank.x+20&&s.y>tank.y&&s.y<tank.y+30){
				s.islive=false;
				tank.islive=false;
				if(hero.islive==false){//������ʱ������̹�����ҵ�̹��,��ô�ҵ�������1
					Record.reduceHeronums();
				}else{//�������ʱ�������ľ��ǵ���̹��,��ô����̹����Ŀ��1���һ��е���Ŀ��1
					Record.reduceEnemynums();
					Record.addres();
				}
			
				Boom boom = new Boom(tank.x,tank.y);
				booms.add(boom);
				}
				break;
		case 3:
		case 4:if(s.x>tank.x&&s.x<tank.x+30&&s.y>tank.y&&s.y<tank.y+20){
			s.islive=false;
			tank.islive=false;
			if(hero.islive==false){
				Record.reduceHeronums();
			}else{
				Record.reduceEnemynums();
				Record.addres();
			}
			Boom boom = new Boom(tank.x,tank.y);
			booms.add(boom);
			}
			break;
		}
	}
	//��дpaint
	public void paint(Graphics g){
		super.paint(g);
		g.fillRect(0,0,400,300);
		//������ʾ��Ϣ̹��(��̹�˲�����ս��)
		this.ShowInfo(g);
		//���ҵ�̹��
		if(hero.islive){
		this.drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
		}
		//�����˵�̹��
		for(int i=0;i<emy.size();i++){
			Enemy em =emy.get(i);
			if(em.islive){
			this.drawTank(em.getX(),em.getY() , g, em.getDirect(), 0);
			//�����˵��ӵ�
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
		//��ը����������ʾ��ըЧ��
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

	//����һ��������ʾ��Ϣ̹�˵ĺ���
	public void ShowInfo(Graphics g){
		//������ʾ��Ϣ̹��(��̹�˲�����ս��)
		this.drawTank(80,330, g, 1, 0);
		g.setColor(Color.black);
		g.drawString(String.valueOf(Record.getEnemeynums()), 105, 350);
		this.drawTank(120, 330, g, 1, 1);
		g.setColor(Color.black);
		g.drawString(String.valueOf(Record.getHeronums()), 145, 350);
		//������ʾ����ܳɼ�����Ϣ
		Font myfont= new Font("����",Font.BOLD,20);
		g.setFont(myfont);
		g.drawString("�����ܳɼ�:", 430, 20);
		this.drawTank(430, 30, g, 1, 0);
		g.setColor(Color.black);
		g.drawString(String.valueOf(Record.getAllres()), 470, 50);
		
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
			
			this.killEnemyTank();
			this.killHeroTank();
			this.repaint();
		}
	}		
}
