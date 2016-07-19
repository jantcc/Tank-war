/*
 * 可以发射子弹  ，我的坦克可以自由移动，并且我的坦克一次可以连发5颗子弹；
 * 子弹击中敌方坦克，敌方坦克消失
 * 增加我方子弹击中敌方坦克，敌方坦克消失爆炸的效果
 * 坦克移动范围限制
 * 敌方坦克也可发射子弹，但是只能一次只能连发3个
 * 增加敌方子弹击中我方坦克，我方坦克消失爆炸的效果
 * 增加功能:敌方坦克不能重叠
 * 增加功能:可以开始游戏，暂停游戏，继续游戏，退出游戏
 * 增加功能:保存我消灭的敌人坦克数量，还可以读取这个数量
 * 增加功能:保存活着的敌人坦克的坐标及方向
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
		// TODO 自动生成的方法存根
		MyTankGame1 mg= new MyTankGame1();
	}
	public MyTankGame1(){
		//设置我的菜单栏
		jmb= new JMenuBar();
		Game=new JMenu("游戏(O)");
		Game.setMnemonic('O');
		Function= new JMenu("功能(F)");
		Function.setMnemonic('F');
		newGame=new JMenuItem("开始新游戏(G)");
		newGame.setMnemonic('G');
		stopGame=new JMenuItem("暂停游戏(P)");
		stopGame.setMnemonic('P');
		goGame=new JMenuItem("继续游戏(N)");
		goGame.setMnemonic('N');
		exitGame=new JMenuItem("退出游戏(E)");
		exitGame.setMnemonic('E');
		saveExit= new JMenuItem("存盘退出(S)");
		saveExit.setMnemonic('S');
		conGame=new JMenuItem("继续上局游戏(E)");
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
		// TODO 自动生成的方法存根
		 
		if(e.getSource().equals(newGame)){//开始新游戏
			mp = new MyPanel(1);
			Thread t0= new Thread(mp);
			t0.start();
			this.remove(sp);
		    this.add(mp);
			this.addKeyListener(mp);
			this.setVisible(true);
		}
		else if(e.getSource().equals(stopGame)){//暂停游戏
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
		 else if(e.getSource().equals(goGame)){//继续游戏
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
		 else if(e.getSource().equals(exitGame)){//退出游戏，可以保存消灭敌人数量
				Record.resPreservation();
				System.exit(0);
			}
		 else if(e.getSource().equals(saveExit)){//存盘退出
			new Record().enemyInfosave(mp.emy);
			 System.exit(0);
		 }
		 else if(e.getSource().equals(conGame)){//继续上一局游戏
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
//定义一个关卡面板
class StagePanel extends JPanel implements Runnable{
	int times =0;
	public void paint(Graphics g){
		super.paint(g);
		g.fillRect(0,0,400,300);
		g.setColor(Color.yellow);
		Font myfont= new Font("华文新魏",Font.BOLD,30);
		g.setFont(myfont);
		if(times%2==0){
		g.drawString("stage 1", 150, 150);
		}
	}

	public void run() {
		// TODO 自动生成的方法存根
		while(true){
			try {
				Thread.sleep(100);
				
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			this.repaint();
			times++;
		}
	}
}
//定义一个我的面板（坦克在里面）
class MyPanel extends JPanel implements KeyListener,Runnable{
	static int num=0;
	//定义一个我的坦克
	Hero hero = null;
	//定义一个敌人的坦克组
	Vector<Enemy> emy= new Vector<Enemy>();
	int emynum=3;
	//定义一个炸弹组
	Vector<Boom> booms= new Vector<Boom>();
	//定义一个Nodes(里面存有上一局的敌人坦克坐标及方向)
	Vector<Nodes> nodes = new Vector<Nodes>();
	//定义三张爆炸图片
	Image image1=null;
	Image image2=null;
	Image image3=null;
	public MyPanel(int flag){	//定义一个判断新游戏或继续上一局的标志
		//恢复成绩
		Record.getRecord();
		//初始化我的坦克
		hero = new Hero(150,270);
		//播放开战声音
			AePlayWave apw=new AePlayWave("src/111.wav");
			apw.start();
		//判断是新游戏还是继续上一局游戏
		//初始化敌人的坦克组
		if(flag==1){
		for(int i=0;i<emynum;i++){
			Enemy em = new Enemy(i*50,0);
			Thread t1= new Thread(em);
			t1.start();
			em.setColor(0);
			em.setDirect(2);
			em.getotherEnemyvector(emy);//**->敌人坦克类里就可以判断坦克不能重叠
			//初始化敌人坦克的时候就开始发射子弹
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
			em.getotherEnemyvector(emy);//**->敌人坦克类里就可以判断坦克不能重叠
			//初始化敌人坦克的时候就开始发射子弹
			Shot s = new Shot(em.x+10,em.y+30,2);
			em.vst1.add(s);
			Thread t2 =new Thread(s);
			t2.start();
			emy.add(em);	
		}
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
						
					}else{
						if(Record.getEnemeynums()>0){
							//继续初始化出现敌人坦克
						}
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
				if(hero.islive){
				this.killTank(EnemyShot, hero);
			}else{
				if(Record.getHeronums()>0){//如果还有命的话，死了就重新初始化一辆我的坦克
				hero = new Hero(150,270);
				}
			}
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
				if(hero.islive==false){//若击中时候死的坦克是我的坦克,那么我的生命减1
					Record.reduceHeronums();
				}else{//否则击中时候死亡的就是敌人坦克,那么敌人坦克数目减1，我击中的数目加1
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
	//复写paint
	public void paint(Graphics g){
		super.paint(g);
		g.fillRect(0,0,400,300);
		//画出提示信息坦克(该坦克不参与战斗)
		this.ShowInfo(g);
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

	//定义一个画出提示信息坦克的函数
	public void ShowInfo(Graphics g){
		//画出提示信息坦克(该坦克不参与战斗)
		this.drawTank(80,330, g, 1, 0);
		g.setColor(Color.black);
		g.drawString(String.valueOf(Record.getEnemeynums()), 105, 350);
		this.drawTank(120, 330, g, 1, 1);
		g.setColor(Color.black);
		g.drawString(String.valueOf(Record.getHeronums()), 145, 350);
		//画出提示玩家总成绩的信息
		Font myfont= new Font("宋体",Font.BOLD,20);
		g.setFont(myfont);
		g.drawString("您的总成绩:", 430, 20);
		this.drawTank(430, 30, g, 1, 0);
		g.setColor(Color.black);
		g.drawString(String.valueOf(Record.getAllres()), 470, 50);
		
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
